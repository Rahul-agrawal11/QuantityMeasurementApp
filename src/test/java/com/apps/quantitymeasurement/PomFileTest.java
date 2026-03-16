package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class PomFileTest {

    @Test
    void testPomFileExists() {

        File pom = new File("pom.xml");

        assertTrue(pom.exists());
    }
}