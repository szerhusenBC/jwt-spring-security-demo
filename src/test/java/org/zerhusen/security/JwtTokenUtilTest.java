package org.zerhusen.security;

import org.assertj.core.util.DateUtil;
import org.assertj.core.util.Maps;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ReflectionUtils;
import org.zerhusen.common.utils.TimeProvider;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.Mockito.*;

/**
 * Created by stephan on 10.09.16.
 */
public class JwtTokenUtilTest {

    private static final String TEST_USER = "testUser";

    @Mock
    private TimeProvider timeProviderMock;

    @InjectMocks
    private JwtTokenUtil jwtTokenUtil;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(jwtTokenUtil, "expiration", 3600L); // one hour
        ReflectionTestUtils.setField(jwtTokenUtil, "secret", "mySecret");
    }

    @Test
    public void testGenerateTokenGeneratesDifferentTokensForDifferentCreationDates() throws Exception {
        when(timeProviderMock.now())
                .thenReturn(DateUtil.yesterday())
                .thenReturn(DateUtil.now());

        final String token = createToken();
        final String laterToken = createToken();

        assertThat(token).isNotEqualTo(laterToken);
    }

    @Test
    public void getUsernameFromToken() throws Exception {
        when(timeProviderMock.now()).thenReturn(DateUtil.now());

        final String token = createToken();

        assertThat(jwtTokenUtil.getUsernameFromToken(token)).isEqualTo(TEST_USER);
    }

    @Test
    public void getCreatedDateFromToken() throws Exception {
        final Date now = DateUtil.now();
        when(timeProviderMock.now()).thenReturn(now);

        final String token = createToken();

        assertThat(jwtTokenUtil.getCreatedDateFromToken(token)).hasSameTimeAs(now);
    }

    @Test
    public void getExpirationDateFromToken() throws Exception {
        final Date now = DateUtil.now();
        when(timeProviderMock.now()).thenReturn(now);
        final String token = createToken();

        final Date expirationDateFromToken = jwtTokenUtil.getExpirationDateFromToken(token);
        assertThat(DateUtil.timeDifference(expirationDateFromToken, now)).isCloseTo(3600000L, within(1000L));
    }

    @Test
    public void getAudienceFromToken() throws Exception {
        when(timeProviderMock.now()).thenReturn(DateUtil.now());
        final String token = createToken();

        assertThat(jwtTokenUtil.getAudienceFromToken(token)).isEqualTo(JwtTokenUtil.AUDIENCE_WEB);
    }

    // TODO write tests
//    @Test
//    public void canTokenBeRefreshed() throws Exception {
//    }
//
//    @Test
//    public void refreshToken() throws Exception {
//    }
//
//    @Test
//    public void validateToken() throws Exception {
//    }

    private Map<String, Object> createClaims(String creationDate) {
        Map<String, Object> claims = new HashMap();
        claims.put(JwtTokenUtil.CLAIM_KEY_USERNAME, TEST_USER);
        claims.put(JwtTokenUtil.CLAIM_KEY_AUDIENCE, "testAudience");
        claims.put(JwtTokenUtil.CLAIM_KEY_CREATED, DateUtil.parseDatetime(creationDate));
        return claims;
    }

    private String createToken() {
        final DeviceDummy device = new DeviceDummy();
        device.setNormal(true);

        return jwtTokenUtil.generateToken(new UserDetailsDummy(TEST_USER), device);
    }

}