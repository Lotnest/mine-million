package dev.lotnest.minemillion.language;

import be.seeseemelk.mockbukkit.MockBukkit;
import dev.lotnest.minemillion.MineMillionPlugin;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LanguageProviderTest {

    private static final String ERROR_NO_KEY_PROVIDED = "ERROR_NO_KEY_PROVIDED";
    private static final String ERROR_NO_MESSAGE_VALUE_FOUND = "ERROR_NO_MESSAGE_VALUE_FOUND";
    private static final String NON_EXISTENT_KEY = "this.key.does.not.exist";
    private static final String[] TEST_PLACEHOLDERS = {"placeholder", "another-placeholder"};
    private static final String TEST_KEY = "test.someKey";
    private static final String TEST_KEY_VALUE = "testValue {0} {1}";

    private static MineMillionPlugin plugin;
    private static LanguageProvider testee;

    @BeforeAll
    public static void setUp() {
        MockBukkit.mock();
        plugin = MockBukkit.load(MineMillionPlugin.class);
        testee = new LanguageProvider(plugin, Language.ENGLISH_US);
    }

    @BeforeEach
    public void beforeEach() {
        testee = new LanguageProvider(plugin, Language.ENGLISH_US);
    }

    @AfterAll
    public static void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    void constructor_nullLanguage() {
        //THEN
        assertThat(testee.getLanguage()).isEqualTo(Language.ENGLISH_US);
    }

    @Test
    void constructor_polishLanguage() {
        // GIVEN
        Language result = Language.POLISH;

        //WHEN
        testee = new LanguageProvider(plugin, result);

        //THEN
        assertThat(testee.getLanguage()).isEqualTo(result);
    }

    @Test
    void get_nullKey_noPlaceholders() {
        // WHEN
        String result = testee.get(null);

        // THEN
        assertThat(result).isEqualTo(ERROR_NO_KEY_PROVIDED);
    }

    @Test
    void get_nullKey_withPlaceholders() {
        // WHEN
        String result = testee.get(null, TEST_PLACEHOLDERS);

        // THEN
        assertThat(result).isEqualTo(ERROR_NO_KEY_PROVIDED);
    }

    @Test
    void get_blankKey_noPlaceholders() {
        // WHEN
        String result = testee.get(StringUtils.EMPTY);

        // THEN
        assertThat(result).isEqualTo(ERROR_NO_KEY_PROVIDED);
    }

    @Test
    void get_blankKey_withPlaceholders() {
        // WHEN
        String result = testee.get(StringUtils.EMPTY, TEST_PLACEHOLDERS);

        // THEN
        assertThat(result).isEqualTo(ERROR_NO_KEY_PROVIDED);
    }

    @Test
    void get_nonExistentKey_noPlaceholders() {
        // WHEN
        String result = testee.get(NON_EXISTENT_KEY);

        // THEN
        assertThat(result).isEqualTo(ERROR_NO_MESSAGE_VALUE_FOUND);
    }

    @Test
    void get_nonExistentKey_WithPlaceholders() {
        // WHEN
        String result = testee.get(NON_EXISTENT_KEY, TEST_PLACEHOLDERS);

        // THEN
        assertThat(result).isEqualTo(ERROR_NO_MESSAGE_VALUE_FOUND);
    }

    @Test
    void get_existingKey_noPlaceholders() {
        // WHEN
        String result = testee.get(TEST_KEY);

        // THEN
        assertThat(result).isEqualTo(TEST_KEY_VALUE);
    }

    @Test
    void get_existingKey_withPlaceholders() {
        // WHEN
        String result = testee.get(TEST_KEY, TEST_PLACEHOLDERS);

        // THEN
        assertThat(result).isEqualTo("testValue placeholder another-placeholder");
    }
}
