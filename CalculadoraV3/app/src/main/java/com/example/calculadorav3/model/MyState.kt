package com.example.calculadorav3.model

class MyState {

    fun add(num1: Double?, num2: Double?): Double? {
        if (num1 == null || num2 == null) return null;
        return num1 + num2
    }

    fun sustract(num1: Double?, num2: Double?): Double? {
        if (num1 == null || num2 == null) return null;
        return num1 - num2
    }

    fun multiply(num1: Double?, num2: Double?): Double? {
        if (num1 == null || num2 == null) return null;
        return num1 * num2
    }

    fun divide(num1: Double?, num2: Double?): Double? {
        if (num1 == null || num2 == null) return null;
        if (num1 == 0.0 && num2 == 0.0) return null;
        if (num2 == 0.0) return null;
        return num1 / num2
    }
}