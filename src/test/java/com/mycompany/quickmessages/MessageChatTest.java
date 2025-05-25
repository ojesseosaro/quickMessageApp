/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.quickmessages;

import org.junit.jupiter.api.*;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MessageChatTest {

    @BeforeEach
    void setUp() throws Exception {
        // Clear the message list and reset counter before each test
        Field listField = MessageChat.class.getDeclaredField("messageList");
        listField.setAccessible(true);
        ((List<?>) listField.get(null)).clear();

        Field counterField = MessageChat.class.getDeclaredField("messageCounter");
        counterField.setAccessible(true);
        counterField.setInt(null, 0);
    }

    @Test
    void testGenerateMessageID() {
        assertEquals("MSG001", MessageChat.generateMessageID());
        assertEquals("MSG002", MessageChat.generateMessageID());
    }

    @Test
    void testCheckInternationalRecipient_Valid() {
        assertTrue(MessageChat.checkInternationalRecipient("+27123456789"));
        assertTrue(MessageChat.checkInternationalRecipient("+123456789012345"));
    }

    @Test
    void testCheckInternationalRecipient_Invalid() {
        assertFalse(MessageChat.checkInternationalRecipient("1234567890"));
        assertFalse(MessageChat.checkInternationalRecipient("+123")); // too short
        assertFalse(MessageChat.checkInternationalRecipient("+1234567890123456")); // too long
    }

    @Test
    void testAddMessageAndHashing() throws Exception {
        String id = MessageChat.generateMessageID();
        String recipient = "+27123456789";
        String body = "Test message";

        MessageChat.addMessage(id, recipient, body);

        Field listField = MessageChat.class.getDeclaredField("messageList");
        listField.setAccessible(true);
        List<?> messageList = (List<?>) listField.get(null);

        assertEquals(1, messageList.size());

        Object msg = messageList.get(0);
        Field hashField = msg.getClass().getDeclaredField("messageHash");
        hashField.setAccessible(true);
        String actualHash = (String) hashField.get(msg);

        String expectedHash = computeSHA256(id + recipient + body);
        assertEquals(expectedHash, actualHash);
    }

    private String computeSHA256(String data) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(data.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) sb.append(String.format("%02x", b));
        return sb.toString();
    }
}
