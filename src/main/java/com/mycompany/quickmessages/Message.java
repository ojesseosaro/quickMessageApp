/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickmessages;

import javax.swing.*;

public class Message {

    // Helper method to show a top-most message dialog
    public static void showTopMessage(String message) {
        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JOptionPane.showMessageDialog(frame, message);
    }

    // Helper method to show a top-most input dialog
    public static String showTopInput(String prompt) {
        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        return JOptionPane.showInputDialog(frame, prompt);
    }

    // Helper method to show a top-most option dialog
    public static int showTopOptionDialog(String message, String title, String[] options, String defaultOption) {
        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        return JOptionPane.showOptionDialog(
                frame, message, title,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, options, defaultOption
        );
    }

    public static void launchGuiMenu(String firstName, String lastName, String username, String password) {
        showTopMessage("*****Login Successful*****\nWelcome back, Quick Message " + firstName + " " + lastName + "!");

        while (true) {
            String[] options = {"Send Messages", "Display SENT Messages", "Quit"};
            int choice = showTopOptionDialog("Choose an option:", "Main Menu", options, options[0]);

            if (choice == 0) {
                int numberOfMessages = 0;
                while (true) {
                    String input = showTopInput("How many messages do you want to send?");
                    if (input == null) return;
                    try {
                        numberOfMessages = Integer.parseInt(input);
                        if (numberOfMessages > 0) break;
                    } catch (NumberFormatException ignored) {}
                    showTopMessage("Please enter a valid number.");
                }

                for (int i = 1; i <= numberOfMessages; i++) {
                    showTopMessage("Message " + i + " of " + numberOfMessages);

                    String recipient;
                    while (true) {
                        recipient = showTopInput("Enter recipient number (e.g., +27123456789):");
                        if (recipient == null) return;
                        if (MessageChat.checkInternationalRecipient(recipient)) break;
                        showTopMessage("Invalid phone number format.");
                    }

                    String messageText;
                    while (true) {
                        messageText = showTopInput("Enter message (max 250 characters):");
                        if (messageText != null && !messageText.trim().isEmpty() && messageText.length() <= 250) break;
                        showTopMessage("Invalid message.");
                    }

                    String messageID = MessageChat.generateMessageID();
                    String[] messageOptions = {"Send Message", "Store Messages to send later", "Discard Message"};
                    int msgChoice = showTopOptionDialog(
                            "What do you want to do with this message?",
                            "Message Options", messageOptions, messageOptions[0]);

                    if (msgChoice == 2 || msgChoice == JOptionPane.CLOSED_OPTION) continue;

                    MessageChat.addMessage(messageID, recipient, messageText);
                    showTopMessage("Message sent/stored.");
                }

            } else if (choice == 1) {
                MessageChat.printMessages();
            } else {
                showTopMessage("Exiting. Goodbye!");
                break;
            }
        }
    }
}
