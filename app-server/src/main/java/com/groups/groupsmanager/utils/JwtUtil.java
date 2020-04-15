package com.groups.groupsmanager.utils;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groups.groupsmanager.config.ApplicationConfiguration;
import com.groups.groupsmanager.userDetails.CustomUserDetailsPrincipal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class JwtUtil {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
	@Autowired
	private ApplicationConfiguration applicationConfig;

	public String getJwtToken(CustomUserDetailsPrincipal userPrincipal) {
		Date tokenExpoirationTime = new Date(
				new Date().getTime() + applicationConfig.getAuth().getTokenExpirationMsec());
		return Jwts.builder().setSubject(Long.toString(userPrincipal.getId())).setIssuedAt(new Date())
				.setExpiration(tokenExpoirationTime)
				.signWith(SignatureAlgorithm.HS512, applicationConfig.getAuth().getTokenSecret()).compact();

	}

	public Long getUserIdFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(applicationConfig.getAuth().getTokenSecret()).parseClaimsJws(token)
				.getBody();

		return Long.parseLong(claims.getSubject());
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(applicationConfig.getAuth().getTokenSecret()).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			logger.error("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			logger.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			logger.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty.");
		}
		return false;
	}

}
