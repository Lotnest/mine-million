package dev.lotnest.minemillion.util;

import be.seeseemelk.mockbukkit.MockBukkit;
import dev.lotnest.minemillion.MineMillionPlugin;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VersionUtilTest {

    private static final String NON_EXISTENT_VERSION = "0.0.0";
    private static final String VERY_HIGH_VERSION = "999.999.999";

    @BeforeAll
    public static void setUp() {
        MockBukkit.mock();
        MockBukkit.load(MineMillionPlugin.class);
    }

    @AfterAll
    public static void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    void getVersion() {
        // WHEN
        String result = VersionUtil.getVersion();

        // THEN
        assertThat(result).isNotBlank();
    }

    @Test
    void isTheSameVersion_nullVersion() {
        // WHEN
        boolean result = VersionUtil.isTheSameVersion(null);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    void isTheSameVersion_blankVersion() {
        // WHEN
        boolean result = VersionUtil.isTheSameVersion(StringUtils.EMPTY);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    void isTheSameVersion_sameVersion() {
        // WHEN
        boolean result = VersionUtil.isTheSameVersion(VersionUtil.getVersion());

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    void isTheSameVersion_differentVersion() {
        // WHEN
        boolean result = VersionUtil.isTheSameVersion(NON_EXISTENT_VERSION);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    void isNewerVersion_nullVersion() {
        // WHEN
        boolean result = VersionUtil.isNewerVersion(null);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    void isNewerVersion_blankVersion() {
        // WHEN
        boolean result = VersionUtil.isNewerVersion(StringUtils.EMPTY);

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    void isNewerVersion_sameVersion() {
        // WHEN
        boolean result = VersionUtil.isNewerVersion(VersionUtil.getVersion());

        // THEN
        assertThat(result).isFalse();
    }

    @Test
    void isNewerVersion_newerVersion() {
        // WHEN
        boolean result = VersionUtil.isNewerVersion(VERY_HIGH_VERSION);

        // THEN
        assertThat(result).isTrue();
    }
}
