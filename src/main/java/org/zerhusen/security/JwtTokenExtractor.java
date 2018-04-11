package org.zerhusen.security;


public interface JwtTokenExtractor {
	public String extract(String payload);
}
