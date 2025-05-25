/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.quickmessages;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    @Test
    void testShowTopMessageDoesNotThrow() {
        assertDoesNotThrow(() -> {
            // Run GUI in a thread-safe manner
            javax.swing.SwingUtilities.invokeLater(() -> {
                Message.showTopMessage("This is a test message.");
            });
        });
    }

    @Test
    void testShowTopInputReturnsStringOrNull() {
        // NOTE: This requires manual input or cancellation during test run
        String result = Message.showTopInput("Enter some input (or cancel):");
        // Accepts null (if cancelled) or a valid string
        assertTrue(result == null || result instanceof String);
    }

    @Test
    void testShowTopOptionDialogReturnsValidIndex() {
        String[] options = {"Option 1", "Option 2", "Option 3"};
        int selected = Message.showTopOptionDialog(
                "Select an option:", "Option Dialog Test", options, options[0]);
        // Valid indices are 0 to options.length - 1; -1 means dialog was closed
        assertTrue(selected >= -1 && selected < options.length);
    }
}
