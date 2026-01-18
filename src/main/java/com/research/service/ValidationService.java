package com.research.service;

import com.research.exception.BusinessRuleViolationException;
import com.research.exception.DuplicateIdException;
import com.research.exception.InputMismatchException;
import com.research.exception.MyNullPointerException;

/**
 * Reusable validation utility for various business and input rules.
 * Keeps services clean and DRY.
 */
public class ValidationService {

    public static void validateNotNull(Object obj, String message) {
        if (obj == null) {
            throw new MyNullPointerException(message);
        }
    }

    public static void validateStringNotEmpty(String str, String message) {
        if (str == null || str.isEmpty()) {
            throw new InputMismatchException(message);
        }
    }

    public static void validatePositive(int value, String message) {
        if (value <= 0) {
            throw new InputMismatchException(message);
        }
    }

    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new BusinessRuleViolationException(message);
        }
    }

    public static void assertUnique(boolean alreadyExists, String message) {
        if (alreadyExists) {
            throw new DuplicateIdException(message);
        }
    }
}