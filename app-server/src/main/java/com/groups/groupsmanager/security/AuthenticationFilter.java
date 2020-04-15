package com.groups.groupsmanager.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.groups.groupsmanager.userDetails.CustomUserDetailsService;
import com.groups.groupsmanager.utils.JwtUtil;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorizationHeader = request.getHeader("Authorization");
		if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
			String token = authorizationHeader.substring(7, authorizationHeader.length());
			if (StringUtils.hasText(token) && jwtUtil.validateToken(token)) {
				UserDetails userPrincipal = customUserDetailsService.loadUserId(jwtUtil.getUserIdFromToken(token));
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userPrincipal, null, userPrincipal.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);

			}
		}
		filterChain.doFilter(request, response);
	}

}
