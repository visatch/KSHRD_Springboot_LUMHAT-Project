package com.kshrd.configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@Component
public class CustomSucessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        HttpSession session=httpServletRequest.getSession();
        session.setMaxInactiveInterval(2_000_000_000);
        httpServletRequest.getSession();
        String redirectUrl=( String)httpServletRequest.getSession().getAttribute("Success");
        if(redirectUrl==null) {
            for (GrantedAuthority auth : authentication.getAuthorities()) {
                if (auth.getAuthority().equals("ADMIN")) {
                    redirectUrl = "/admin";
                } else
                    redirectUrl = "/home";
            }
        }
        System.out.println("REDIRECT URL : "+redirectUrl);
        httpServletRequest.getSession().setAttribute("cURL", httpServletRequest.getRequestURI());
            httpServletResponse.sendRedirect(redirectUrl);
        }
}
