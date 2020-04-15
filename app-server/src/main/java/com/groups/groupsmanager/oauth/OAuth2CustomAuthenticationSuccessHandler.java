package com.groups.groupsmanager.oauth;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.groups.groupsmanager.config.ApplicationConfiguration;
import com.groups.groupsmanager.model.User;
import com.groups.groupsmanager.oauth.userinfo.OAuthUserInfo;
import com.groups.groupsmanager.oauth.userinfo.OAuthUserInfoFactory;
import com.groups.groupsmanager.repository.UserRepository;
import com.groups.groupsmanager.userDetails.CustomUserDetailsPrincipal;
import com.groups.groupsmanager.utils.JwtUtil;

@Component
public class OAuth2CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private JwtUtil jwtTokenCreater;

	@Autowired
	private ApplicationConfiguration appConfig;
	@Autowired
	UserRepository userRepo;

	@Autowired
	OAuth2CustomAuthenticationSuccessHandler(JwtUtil jwtTokenCreater) {
		this.jwtTokenCreater = jwtTokenCreater;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		if (response.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect ");
			return;
		}
		User user = createUser(authentication);
		String token = CreateJwtToken(user);
		String targetUrl = Optional.of(appConfig.getOauth2().getAuthorizedRedirectUri()).orElse(getDefaultTargetUrl());
		getRedirectStrategy().sendRedirect(request, response,
				UriComponentsBuilder.fromUriString(targetUrl).queryParam("token", token).build().toUriString());
	}

	private String CreateJwtToken(User user) {
		CustomUserDetailsPrincipal customUserPrincipal = CustomUserDetailsPrincipal.create(user);
		String token = jwtTokenCreater.getJwtToken(customUserPrincipal);
		return token;
	}

	private User createUser(Authentication authentication) {
		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
		OAuthUserInfo userInfo = OAuthUserInfoFactory.getOAuthUserInfo(oAuth2User.getAttributes());
		User user;
		if (userRepo.existsByEmail(userInfo.getEmail())) {
			user = userRepo.findByEmail(userInfo.getEmail()).get();
			user.setName(userInfo.getName());
		} else {
			user = new User();
			user.setEmail(userInfo.getEmail());
			user.setName(userInfo.getName());

		}
		return userRepo.save(user);

	}

}
