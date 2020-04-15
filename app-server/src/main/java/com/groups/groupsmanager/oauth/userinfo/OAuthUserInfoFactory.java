package com.groups.groupsmanager.oauth.userinfo;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class OAuthUserInfoFactory {

	public static OAuthUserInfo getOAuthUserInfo(Map<String, Object> attributes) {
		return new GoogleOAuth2User(attributes);

	}
}
