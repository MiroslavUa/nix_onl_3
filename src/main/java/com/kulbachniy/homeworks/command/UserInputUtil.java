package com.kulbachniy.homeworks.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class UserInputUtil {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public static int chooseCommand(List<String> names){
        int userType = -1;
        do{
            userType = getUserInput(names);            
        } while (userType == -1);
        return userType;
    }

    private static int getUserInput(List<String> names) {
        try{
            System.out.printf("Enter number between 0 and " + names.size() + ":\n");
            for(int i = 0; i < names.size(); i++){
                System.out.printf("%d) %s%n", i, names.get(i));
            }
            int input = Integer.parseInt(READER.readLine());
            if(input >= 0 && input < names.size()){
                return input;
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Input is not correct");
        }
        return -1;
    }

    public static double doubleValue() throws IOException {
        String value = READER.readLine();
        return Double.parseDouble(value);
    }

    public static int intValue() throws IOException {
        String value = READER.readLine();
        return Integer.parseInt(value);
    }

    public static String stringValue() throws IOException {
        return READER.readLine();
    }

}
