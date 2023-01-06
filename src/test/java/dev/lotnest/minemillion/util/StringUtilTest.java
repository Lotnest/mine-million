package dev.lotnest.minemillion.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class StringUtilTest {

    @Test
    public void capitalize_blankString() {
        //GIVEN
        String string = "";

        //WHEN
        String result = StringUtil.capitalize(string);

        //THEN
        assertThat(result).isEqualTo(string);
    }

    @Test
    public void capitalize_oneLetter() {
        //GIVEN
        String string = "a";

        //WHEN
        String result = StringUtil.capitalize(string);

        //THEN
        assertThat(result).isEqualTo("A");
    }

    @Test
    public void capitalize_twoLetters() {
        //GIVEN
        String string = "ab";

        //WHEN
        String result = StringUtil.capitalize(string);

        //THEN
        assertThat(result).isEqualTo("Ab");
    }

    @Test
    public void capitalize_twoWords() {
        //GIVEN
        String string = "ab cd";

        //WHEN
        String result = StringUtil.capitalize(string);

        //THEN
        assertThat(result).isEqualTo("Ab cd");
    }

    @Test
    public void capitalizeFully_blankString() {
        //GIVEN
        String string = "";

        //WHEN
        String result = StringUtil.capitalizeFully(string);

        //THEN
        assertThat(result).isEqualTo(string);
    }

    @Test
    public void capitalizeFully_oneLetter() {
        //GIVEN
        String string = "a";

        //WHEN
        String result = StringUtil.capitalizeFully(string);

        //THEN
        assertThat(result).isEqualTo("A");
    }

    @Test
    public void capitalizeFully_twoLetters() {
        //GIVEN
        String string = "ab";

        //WHEN
        String result = StringUtil.capitalizeFully(string);

        //THEN
        assertThat(result).isEqualTo("Ab");
    }

    @Test
    public void capitalizeFully_twoWords() {
        //GIVEN
        String string = "ab cd";

        //WHEN
        String result = StringUtil.capitalizeFully(string);

        //THEN
        assertThat(result).isEqualTo("Ab Cd");
    }

    @Test
    public void join_oneString_defaultSeparator() {
        //GIVEN
        String[] strings = {"a"};

        //WHEN
        String result = StringUtil.join(strings);

        //THEN
        assertThat(result).isEqualTo("a");
    }

    @Test
    public void join_twoStrings_defaultSeparator() {
        //GIVEN
        String[] strings = {"a", "b"};

        //WHEN
        String result = StringUtil.join(strings);

        //THEN
        assertThat(result).isEqualTo("a, b");
    }

    @Test
    public void join_oneString_customSeparator() {
        //GIVEN
        String[] strings = {"a"};
        String separator = ";";

        //WHEN
        String result = StringUtil.join(separator, strings);

        //THEN
        assertThat(result).isEqualTo("a");
    }

    @Test
    public void join_twoStrings_customSeparator() {
        //GIVEN
        String[] strings = {"a", "b"};
        String separator = ";";

        //WHEN
        String result = StringUtil.join(separator, strings);

        //THEN
        assertThat(result).isEqualTo("a;b");
    }
}
