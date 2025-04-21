package com.griddynamics.spingtesting.dirtycontext;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CounterBTest {

    @Autowired
    CounterBean counter;

    @Test
    void testStartsAtZero() {
        // EXPECTED: counter.getCount() == 0
        // ACTUAL:   counter.getCount() == 1  ← because CounterTestA mutated it
        assertThat(counter.getCount())
                .as("Without isolating the context, state from the previous test leaks in")
                .isEqualTo(0);
    }
}