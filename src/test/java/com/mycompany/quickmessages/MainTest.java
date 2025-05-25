/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.quickmessages;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    void testCheckUserName_Valid() {
        assertTrue(Main.checkUserName("kyl_1"));
    }

    @Test
    void testCheckUserName_Invalid() {
        assertFalse(Main.checkUserName("kyl!!!!!")); // no underscore
        assertFalse(Main.checkUserName("kyl!!!!!!!!!!!!"));   // too long
    }

    @Test
    void testCheckPasswordComplexity_Valid() {
        assertTrue(Main.checkPasswordComplexity("Strong1@"));
        assertTrue(Main.checkPasswordComplexity("Ch&&sec@kec99!"));
    }

    @Test
    void testCheckPasswordComplexity_Invalid() {
        assertFalse(Main.checkPasswordComplexity("weak"));          // too short
        assertFalse(Main.checkPasswordComplexity("Password"));    // no special char
        assertFalse(Main.checkPasswordComplexity("nouppercase1!")); // no uppercase
        assertFalse(Main.checkPasswordComplexity("NoNumber!"));     // no number
    }

    @Test
    void testCheckCellPhoneNumber_Valid() {
        assertTrue(Main.checkCellPhoneNumber("+12345678901"));
    }

    @Test
    void testCheckCellPhoneNumber_Invalid() {
        assertFalse(Main.checkCellPhoneNumber("1234567890"));    // missing +
        assertFalse(Main.checkCellPhoneNumber("+123"));          // too short
        assertFalse(Main.checkCellPhoneNumber("+123abc45678"));  // contains letters
    }

    @Test
    void testRegisterUser_Success() {
        assertEquals("Registration successful.", Main.registerUser("ab_c", "Valid1@pass"));
    }

    @Test
    void testRegisterUser_Failure() {
        assertEquals("Registration failed.", Main.registerUser("abc", "weak"));
    }

    @Test
    void testLoginUser_Success() {
        assertTrue(Main.loginUser("user", "pass", "user", "pass"));
    }

    @Test
    void testLoginUser_Failure() {
        assertFalse(Main.loginUser("user", "pass", "wrong", "wrong"));
    }
}
