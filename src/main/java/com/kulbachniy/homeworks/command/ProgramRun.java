package com.kulbachniy.homeworks.command;

import java.io.IOException;

public class ProgramRun {
    public static void run() throws IOException {
        final Commands[] values = Commands.values();
        boolean exit = false;

        while(!exit){
            exit = choiceOfAction(values);
        }
    }

    private static boolean choiceOfAction(final Commands[] values) throws IOException {
        int chosenCommand = -1;
        do {
            for (int i = 0; i < values.length; i++) {
                System.out.printf("%d) %s%n", i, values[i].getDescription());
            }
            int input = UserInputUtil.intValue();
            if (input >= 0 && input < values.length) {
                chosenCommand = input;
            }
        } while (chosenCommand == -1);
        final Command command = values[chosenCommand].getCommand();
        if (command == null) {
            return true;
        } else {
            command.execute();
            return false;
        }
    }
}
