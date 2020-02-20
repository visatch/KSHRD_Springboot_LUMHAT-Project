package com.kshrd.controller;
import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.kshrd.model.User;
import com.kshrd.model.classroom.ClassroomClass;
import com.kshrd.service.classroom.classroomclass.ClassroomClassService;
import org.json.JSONObject;
import com.kshrd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/facebook")
@PropertySource("classpath:fblogin.properties")
public class FacebookLoginController {

    @Autowired
    public UserService userService;
    @Autowired
    public ClassroomClassService classroomClassService;

    @Value("${fb.login.appid}")
    private String FB_APP_ID;

    @Value("${fb.login.secret}")
    private String FB_APP_SECRET;

    @Value("${fb.login.domain}")
    private String DOMAIN;

    private static final String CALLBACK_URL = "/facebook/callback";
    Date date=new Date();
    private static final List<String> SCOPES = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            add("public_profile");
            add("email");
//            add("user_gender");
        }
    };

    // Facebook API get user information
    private static final String USER_PROFILE_API_URL = "https://graph.facebook.com/v2.8/me" + "?fields=id,name,first_name,last_name,email";

    @GetMapping("/signin")
    public void signin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String secretState = "secret" + new Random().nextInt(999_999);
        request.getSession().setAttribute("SECRET_STATE", secretState);
        @SuppressWarnings("deprecation")
        OAuth20Service service = new ServiceBuilder()
                .apiKey(FB_APP_ID)
                .apiSecret(FB_APP_SECRET)
                .callback(DOMAIN + CALLBACK_URL)
                .scope(String.join(",", SCOPES))
                .state(secretState)
                .build(FacebookApi.instance());

        String authorizeUrl = service.getAuthorizationUrl();
        response.sendRedirect(authorizeUrl);
    }

    @GetMapping(value = "/callback")
    public String callback(@RequestParam(value = "code", required = false) String code,
                           @RequestParam(value = "state", required = false) String state,
                           HttpServletRequest request,HttpServletResponse response
    ) {
        try {
            @SuppressWarnings("deprecation")
            OAuth20Service service = new ServiceBuilder()
                    .apiKey(FB_APP_ID)
                    .apiSecret(FB_APP_SECRET)
                    .callback(DOMAIN + CALLBACK_URL)
                    .build(FacebookApi.instance());
            final String requestUrl = USER_PROFILE_API_URL;
            final OAuth2AccessToken accessToken = service.getAccessToken(code);
            final OAuthRequest oauthRequest = new OAuthRequest(Verb.GET, requestUrl);
            service.signRequest(accessToken, oauthRequest);
            final Response resourceResponse = service.execute(oauthRequest);
            final JSONObject obj = new JSONObject(resourceResponse.getBody());
            request.getSession().setAttribute("FACEBOOK_ACCESS_TOKEN", accessToken);
            // Sign up new user and create login session
            User user = new User();

            // Create an random password for user
            // Try to set email if user allow email access
            try {
                user.setEmail(obj.getString("email"));
            } catch (Exception e) {
                user.setEmail(null);
            }

            // Try to set gender if user allow gender access
//            try {
//                user.setGender(obj.getString("gender"));
//            } catch (Exception e) {
//                user.setGender("other");
//            }
            user.setGender("other");
            user.setSchool(null);
            user.setStatus(true);
            user.setDob(date);
            user.setFirstName(obj.getString("first_name"));
            user.setLastName(obj.getString("last_name"));
            user.setImageUrl("http://graph.facebook.com/" + obj.getString("id") + "/picture?type=large");
            user.setFacebookId(obj.getString("id"));
            user.setPassword("12345");

            if (!(user.getFacebookId().equalsIgnoreCase(userService.findById(user.getFacebookId())))) {
                userService.add(user);
            }
            int  totalClass = classroomClassService.countUserClass(userService.findOne(user.getFacebookId()).getId());
            user = userService.findOne(user.getFacebookId());
            user.setTotalClass(totalClass);
            String redirectUrl = (String) request.getSession().getAttribute("Success");
            if (redirectUrl == "/error?null" || redirectUrl == null || redirectUrl == "/favicon.ico?null") {
                redirectUrl = "/profile";
            } else {
                redirectUrl = (String) request.getSession().getAttribute("Success");
            }
            Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getRoles());
            SecurityContextHolder.getContext().setAuthentication(auth);
            //add cookie to remember login
            Cookie cookie = new Cookie("userId",user.getFacebookId());
            cookie.setMaxAge(2_000_000_000);
            cookie.setPath("/");
            response.addCookie(cookie);
            return "redirect:" + redirectUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login";
        }
    }
}
