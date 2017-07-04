package org.zerhusen.common.utils;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

/**
 * Created by stephan on 04.07.17.
 */
public class TimeProviderTest {
    @Test
    public void now() throws Exception {
        assertThat(new TimeProvider().now()).isCloseTo(new Date(), 1000);
    }

}