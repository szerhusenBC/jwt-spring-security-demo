package org.zerhusen.security;

import org.springframework.stereotype.Component;

@Component
public class JwtHeaderTokenExtractor implements JwtTokenExtractor{
    static final String HEADER_PREFIX = "Bearer ";
    
    @Override
    public String extract(String header) {
        if (isBlank(header)) {
        	// create a new exception
        	return "";
        }

        if (header.length() < HEADER_PREFIX.length()) {
        	// create a new exception
        	return "";
        }

        return header.substring(HEADER_PREFIX.length(), header.length());
    }
    
    // Available possibility to use org.apache.commons.lang3.StringUtils
    private boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    } 
    
}