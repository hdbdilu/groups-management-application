package com.groups.groupsmanager.oauth;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.groups.groupsmanager.config.ApplicationConfiguration;

@Component
public class OAuth2CustomUrlAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	@Autowired
	private ApplicationConfiguration appConfig;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		String targetUrl = Optional.of(appConfig.getOauth2().getAuthorizedRedirectUri()).orElse("/");

		targetUrl = UriComponentsBuilder.fromUriString(targetUrl).queryParam("error", exception.getLocalizedMessage())
				.build().toUriString();

		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}
}
