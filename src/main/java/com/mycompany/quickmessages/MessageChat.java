/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickmessages;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * MessageChat manages message sending, validation, saving to JSON, and display.
 */
public class MessageChat {
    static List<Message> messageList = new ArrayList<>();
    static int messageCounter = 0;
    static final String JSON_FILE_PATH = "messages.json";

    public static String generateMessageID() {
        messageCounter++;
        return "MSG" + String.format("%03d", messageCounter);
    }

    public static boolean checkInternationalRecipient(String number) {
        return number.matches("^\\+\\d{10,15}$");
    }

    public static void addMessage(String messageID, String recipient, String messageText) {
        Message msg = new Message(messageID, recipient, messageText);
        messageList.add(msg);
        saveMessagesToJson();
    }

    public static void saveMessagesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Message msg : messageList) {
            JSONObject jsonMsg = new JSONObject();
            jsonMsg.put("messageID", msg.messageID);
            jsonMsg.put("recipient", msg.recipient);
            jsonMsg.put("body", msg.body);
            jsonMsg.put("messageHash", msg.messageHash);
            jsonArray.put(jsonMsg);
        }
        try (FileWriter file = new FileWriter(JSON_FILE_PATH)) {
            file.write(jsonArray.toString(4));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Apologise, Failed to save messages.");
        }
    }

    public static void printMessages() {
        if (messageList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Coming Soon.");
            return;
        }

        StringBuilder sb = new StringBuilder("Stored Messages:\n");
        for (Message msg : messageList) {
            sb.append("ID: ").append(msg.messageID)
              .append(", To: ").append(msg.recipient)
              .append("\nText: ").append(msg.body)
              .append("\nHash: ").append(msg.messageHash)
              .append("\n\n");
        }

        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));
        JOptionPane.showMessageDialog(null, scrollPane, "Messages", JOptionPane.INFORMATION_MESSAGE);
    }

    static class Message {
        String messageID, recipient, body, messageHash;

        public Message(String messageID, String recipient, String body) {
            this.messageID = messageID;
            this.recipient = recipient;
            this.body = body;
            this.messageHash = hashMessage();
        }

        private String hashMessage() {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest((messageID + recipient + body).getBytes());
                StringBuilder sb = new StringBuilder();
                for (byte b : hash) sb.append(String.format("%02x", b));
                return sb.toString();
            } catch (NoSuchAlgorithmException e) {
                return "HashError";
            }
        }
    }
}
