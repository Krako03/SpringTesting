package com.griddynamics.spingtesting.properties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"classroom.location=usa", "classroom.name=spanish", "classroom.size=150"})
@TestPropertySource(properties = {"test.welcome.message=Welcome to the Test"})
class ClassroomTest {

    @Autowired
    Classroom classroom;

    @Value("${test.welcome.message}")
    private String welcomeMessage;

    @Test
    void getProperties() {
        System.out.println(welcomeMessage);
        assertEquals(150, classroom.getSize());
        assertEquals("spanish", classroom.getName());
        assertEquals("usa", classroom.getLocation());
    }
}