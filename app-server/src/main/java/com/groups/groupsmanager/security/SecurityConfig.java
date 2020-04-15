package com.groups.groupsmanager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.groups.groupsmanager.oauth.OAuth2CustomAuthenticationSuccessHandler;
import com.groups.groupsmanager.oauth.OAuth2CustomUrlAuthenticationFailureHandler;
import com.groups.groupsmanager.userDetails.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Autowired
	private OAuth2CustomAuthenticationSuccessHandler oAuth2CustomAuthenticationSuccessHandler;

	@Autowired
	AuthenticationFilter authFilter;
	@Autowired
	private OAuth2CustomUrlAuthenticationFailureHandler oAuth2CustomUrlAuthenticationFailureHandler;

	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf()
				.disable().formLogin().disable().httpBasic().disable().exceptionHandling()
				.authenticationEntryPoint(new RestAuthenticationEntryPoint()).and().authorizeRequests()
				.antMatchers("/", "/error", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg",
						"/**/*.html", "/**/*.css", "/**/*.js")
				.permitAll().antMatchers("/auth/**", "/oauth2/**").permitAll().anyRequest().authenticated().and()
				.oauth2Login().authorizationEndpoint().baseUri("/oauth2/authorize").and().redirectionEndpoint()
				.baseUri("/oauth2/callback/*").and().successHandler(oAuth2CustomAuthenticationSuccessHandler)
				.failureHandler(oAuth2CustomUrlAuthenticationFailureHandler);
		http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
