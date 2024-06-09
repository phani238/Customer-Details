package com.maybank.customerDetails.config;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

import jakarta.servlet.http.HttpServletRequest;

public class AuthenticationService {
	private static final String AUTH_TOKEN_HEADER_NAME = "api-key";
	private static final String AUTH_TOKEN = "maybank";

	public static Authentication getAuthentication(HttpServletRequest request) {
		String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
		if (apiKey == null || !apiKey.equals(AUTH_TOKEN)) {
			throw new BadCredentialsException("Authentication Failed");
		}

		return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
	}
}
