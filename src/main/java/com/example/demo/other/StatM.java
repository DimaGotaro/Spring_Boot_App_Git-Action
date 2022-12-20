package com.example.demo.other;

public class StatM {

    public static boolean itsNumber(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean prov(String s) {
        String[] split = s.split("\\.");
        if (split.length < 2) {
            return true;
        }
        else return split[1].length() < 3;
    }
}
