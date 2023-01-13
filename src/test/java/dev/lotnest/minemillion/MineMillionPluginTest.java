package dev.lotnest.minemillion;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MineMillionPluginTest {

    private static MineMillionPlugin testee;

    @BeforeAll
    public static void setUp() {
        MockBukkit.mock();
        testee = MockBukkit.load(MineMillionPlugin.class);
    }

    @AfterAll
    public static void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    void onEnable() {
        // WHEN
        testee.onEnable();

        // THEN
        assertThat(System.getProperty("org.jooq.no-logo")).isEqualTo("true");
        assertThat(System.getProperty("org.jooq.no-tips")).isEqualTo("true");
        assertThat(testee.isEnabled()).isTrue();
        assertThat(MineMillionPlugin.getInstance()).isNotNull();
        assertThat(testee.getConfigHandler()).isNotNull();
        assertThat(testee.getLanguageProvider()).isNotNull();
        assertThat(testee.getConnectionHolder()).isNotNull();
        assertThat(testee.getPlayerCache()).isNotNull();
        assertThat(testee.getComponentRegistry()).isNotNull();
        assertThat(testee.getTaskManager()).isNotNull();
        assertThat(testee.getEventManager()).isNotNull();
    }
}
