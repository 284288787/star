package com.star.truffle.core.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class RestAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private String defaultFailureUrl;
	
    public RestAuthenticationFailureHandler(String defaultFailureUrl){
    	this.defaultFailureUrl = defaultFailureUrl;
    }
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
    	if (exception instanceof InternalAuthenticationServiceException || exception instanceof UsernameNotFoundException) {
			exception = new InternalAuthenticationServiceException("帐号不存在");
    	}else if (exception instanceof BadCredentialsException) {
    		exception = new BadCredentialsException("帐号或密码错误1");
    	}else if (exception instanceof AccountExpiredException) {
    		exception = new AccountExpiredException("帐号已过期");
    	}else if (exception instanceof LockedException) {
    		exception = new LockedException("帐号已锁定");
    	}else if (exception instanceof DisabledException) {
    		exception = new DisabledException("帐号被禁用");
		}else if (exception instanceof CredentialsExpiredException) {
			exception = new CredentialsExpiredException("凭据已过期");
		}
    	HttpSession session = request.getSession(false);
    	if (null != session) {
    		request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
		}
    	getRedirectStrategy().sendRedirect(request, response, defaultFailureUrl);
    }
}
