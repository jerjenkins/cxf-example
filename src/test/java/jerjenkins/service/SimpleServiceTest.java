package jerjenkins.service;


import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class SimpleServiceTest {
    private SimpleService sut;

    @Before
    public void before() {
        sut = new SimpleService();
    }

    @Test
    public void should_stringify_single_digits() {
        assertThat(sut.stringify(3), is(Optional.of("three")));
    }

    @Test
    public void should_stringify_2_digit_numbers() {
        assertThat(sut.stringify(30), is(Optional.of("thirty")));
    }

    @Test
    public void should_stringify_teens() {
        assertThat(sut.stringify(13), is(Optional.of("thirteen")));
    }

    @Test
    public void should_stringify_2_digit_numbers_with_single_digits() {
        assertThat(sut.stringify(33), is(Optional.of("thirty three")));
    }

    @Test
    public void should_stringify_3_digit_numbers() {
        assertThat(sut.stringify(300), is(Optional.of("three hundred")));
    }

    @Test
    public void should_stringify_3_digit_numbers_with_teens() {
        assertThat(sut.stringify(313), is(Optional.of("three hundred thirteen")));
    }

    @Test
    public void should_stringify_3_digit_numbers_with_2_digit_numbers() {
        assertThat(sut.stringify(333), is(Optional.of("three hundred thirty three")));
    }

    @Test
    public void should_stringify_4_digit_numbers() {
        assertThat(sut.stringify(3333),
                is(Optional.of(
                        "three thousand " +
                        "three hundred thirty three")));
    }

    @Test
    public void should_stringify_5_digit_numbers() {
        assertThat(sut.stringify(33333), is(Optional.of(
                "thirty three thousand " +
                "three hundred thirty three")));
    }

    @Test
    public void should_stringify_6_digit_numbers() {
        assertThat(sut.stringify(333333),
                is(Optional.of(
                        "three hundred thirty three thousand " +
                        "three hundred thirty three")));
    }

    @Test
    public void should_stringify_7_digit_numbers() {
        assertThat(sut.stringify(3333333),
                is(Optional.of(
                        "three million " +
                                "three hundred thirty three thousand " +
                                "three hundred thirty three")));
    }

    @Test
    public void should_stringify_8_digit_numbers() {
        assertThat(sut.stringify(33333333),
                is(Optional.of(
                        "thirty three million " +
                        "three hundred thirty three thousand " +
                        "three hundred thirty three")));
    }

    @Test
    public void should_stringify_9_digit_numbers() {
        assertThat(sut.stringify(333333333),
                is(Optional.of(
                        "three hundred thirty three million " +
                        "three hundred thirty three thousand " +
                        "three hundred thirty three")));
    }

    @Test
    public void should_stringify_10_digit_numbers() {
        assertThat(sut.stringify(1100000000), is(Optional.of(
                "one billion " +
                "one hundred million")));
    }
    @Test
    public void should_stringify_max_integer() {
        assertThat(sut.stringify(Integer.MAX_VALUE), is(Optional.of(
                "two billion " +
                        "one hundred forty seven million " +
                        "four hundred eighty three thousand " +
                        "six hundred forty seven")));
    }

    @Test
    public void should_stringify_110000000000000() {
        assertThat(sut.stringify(new BigInteger("110000000000000")), is(Optional.of("one hundred ten trillion")));
    }

    @Test
    public void should_fail_past_trillions() {
        assertThat(sut.stringify(1000000000000000L), is(Optional.<String>absent()));
    }
}
