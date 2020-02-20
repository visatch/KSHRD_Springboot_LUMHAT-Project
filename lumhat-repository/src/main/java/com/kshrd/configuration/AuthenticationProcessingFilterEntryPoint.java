package com.kshrd.configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class AuthenticationProcessingFilterEntryPoint extends LoginUrlAuthenticationEntryPoint {
    public AuthenticationProcessingFilterEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }
    @Override
    protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        String url = super.determineUrlToUseForThisRequest(request, response, exception);
        return url + "?" + request.getQueryString();
    }

}
