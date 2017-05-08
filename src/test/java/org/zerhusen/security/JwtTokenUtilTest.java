package org.zerhusen.security;

import org.assertj.core.util.DateUtil;
import org.junit.Before;
import org.junit.Test;
import org.zerhusen.properties.jwt.JwtProperties;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by stephan on 10.09.16.
 */
public class JwtTokenUtilTest {

    private JwtTokenUtil jwtTokenUtil;
    private JwtProperties jwtProperties;

    @Before
    public void init() {
        jwtProperties = mock(JwtProperties.class);
        jwtTokenUtil = new JwtTokenUtil(jwtProperties);
        when(jwtProperties.getExpiration()).thenReturn(3600000L);
        when(jwtProperties.getSecret()).thenReturn("mySecret");
    }

    @Test
    public void testGenerateTokenGeneratesDifferentTokensForDifferentCreationDates() throws Exception {
        final Map<String, Object> claims = createClaims("2016-09-08T03:00:00");
        final String token = jwtTokenUtil.generateToken(claims);

        final Map<String, Object> claimsForLaterToken = createClaims("2016-09-08T08:00:00");
        final String laterToken = jwtTokenUtil.generateToken(claimsForLaterToken);

        assertThat(token).isNotEqualTo(laterToken);
    }

    private Map<String, Object> createClaims(String creationDate) {
        Map<String, Object> claims = new HashMap();
        claims.put(JwtTokenUtil.CLAIM_KEY_USERNAME, "testUser");
        claims.put(JwtTokenUtil.CLAIM_KEY_AUDIENCE, "testAudience");
        claims.put(JwtTokenUtil.CLAIM_KEY_CREATED, DateUtil.parseDatetime(creationDate));
        return claims;
    }

}