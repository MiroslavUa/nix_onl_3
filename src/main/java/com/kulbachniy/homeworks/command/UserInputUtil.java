package com.kulbachniy.homeworks.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInputUtil {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static double doubleValue() throws IOException{
        String input = reader.readLine();
        return Double.parseDouble(input);
    }

    public static int intValue() throws IOException{
        String input = reader.readLine();
        return Integer.parseInt(input);
    }

    public static String stringValue() throws IOException{
        return reader.readLine();
    }
}
