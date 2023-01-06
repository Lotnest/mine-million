package dev.lotnest.minemillion;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class MineMillionPluginTest {

    private MineMillionPlugin testee;

    @Before
    public void setUp() {
        MockBukkit.mock();
        testee = MockBukkit.load(MineMillionPlugin.class);
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void onEnable() {
        // WHEN
        testee.onEnable();

        // THEN
        assertThat(MineMillionPlugin.getInstance()).isNotNull();
        assertThat(testee.getLanguageProvider()).isNotNull();
    }
}
