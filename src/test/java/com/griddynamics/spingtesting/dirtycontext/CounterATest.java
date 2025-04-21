package com.griddynamics.spingtesting.dirtycontext;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CounterATest {

    @Autowired
    CounterBean counter;

    @Test
    void testIncrement() {
        // Initial state should be 0
        assertThat(counter.getCount()).isEqualTo(0);
        // Mutate state
        counter.increment();
        assertThat(counter.getCount()).isEqualTo(1);
    }
}