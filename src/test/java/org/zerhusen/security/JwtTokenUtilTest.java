package org.zerhusen.security;

import java.util.Date;

import io.jsonwebtoken.Clock;
import io.jsonwebtoken.ExpiredJwtException;
import org.assertj.core.util.DateUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JwtTokenUtilTest {

    private static final String TEST_USERNAME = "testUser";

    @Mock
    private Clock clockMock;

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
        when(clockMock.now())
            .thenReturn(DateUtil.yesterday())
            .thenReturn(DateUtil.now());

        final String token = createToken();
        final String laterToken = createToken();

        assertThat(token).isNotEqualTo(laterToken);
    }

    @Test
    public void getUsernameFromToken() throws Exception {
        when(clockMock.now()).thenReturn(DateUtil.now());

        final String token = createToken();

        assertThat(jwtTokenUtil.getUsernameFromToken(token)).isEqualTo(TEST_USERNAME);
    }

    @Test
    public void getCreatedDateFromToken() throws Exception {
        final Date now = DateUtil.now();
        when(clockMock.now()).thenReturn(now);

        final String token = createToken();

        assertThat(jwtTokenUtil.getIssuedAtDateFromToken(token)).isInSameMinuteWindowAs(now);
    }

    @Test
    public void getExpirationDateFromToken() throws Exception {
        final Date now = DateUtil.now();
        when(clockMock.now()).thenReturn(now);
        final String token = createToken();

        final Date expirationDateFromToken = jwtTokenUtil.getExpirationDateFromToken(token);
        assertThat(DateUtil.timeDifference(expirationDateFromToken, now)).isCloseTo(3600000L, within(1000L));
    }

    @Test(expected = ExpiredJwtException.class)
    public void expiredTokenCannotBeRefreshed() throws Exception {
        when(clockMock.now())
            .thenReturn(DateUtil.yesterday());
        String token = createToken();
        jwtTokenUtil.canTokenBeRefreshed(token, DateUtil.tomorrow());
    }

    @Test
    public void changedPasswordCannotBeRefreshed() throws Exception {
        when(clockMock.now())
            .thenReturn(DateUtil.now());
        String token = createToken();
        assertThat(jwtTokenUtil.canTokenBeRefreshed(token, DateUtil.tomorrow())).isFalse();
    }

    @Test
    public void notExpiredCanBeRefreshed() {
        when(clockMock.now())
            .thenReturn(DateUtil.now());
        String token = createToken();
        assertThat(jwtTokenUtil.canTokenBeRefreshed(token, DateUtil.yesterday())).isTrue();
    }

    @Test
    public void canRefreshToken() throws Exception {
        when(clockMock.now())
            .thenReturn(DateUtil.now())
            .thenReturn(DateUtil.tomorrow());
        String firstToken = createToken();
        String refreshedToken = jwtTokenUtil.refreshToken(firstToken);
        Date firstTokenDate = jwtTokenUtil.getIssuedAtDateFromToken(firstToken);
        Date refreshedTokenDate = jwtTokenUtil.getIssuedAtDateFromToken(refreshedToken);
        assertThat(firstTokenDate).isBefore(refreshedTokenDate);
    }

    @Test
    public void canValidateToken() throws Exception {
        when(clockMock.now())
            .thenReturn(DateUtil.now());
        UserDetails userDetails = mock(JwtUser.class);
        when(userDetails.getUsername()).thenReturn(TEST_USERNAME);

        String token = createToken();
        assertThat(jwtTokenUtil.validateToken(token, userDetails)).isTrue();
    }

    private String createToken() {
        return jwtTokenUtil.generateToken(new UserDetailsDummy(TEST_USERNAME));
    }
}
