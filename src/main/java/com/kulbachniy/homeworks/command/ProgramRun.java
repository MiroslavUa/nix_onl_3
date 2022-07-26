package com.kulbachniy.homeworks.command;

import java.io.IOException;
import java.util.Arrays;

public class ProgramRun {
    public static void run() throws IOException {
        final Commands[] values = Commands.values();
        boolean exit = false;

        while(!exit){
            exit = userAction(values);
        }
    }

    private static boolean userAction(final Commands[] values) throws IOException {
        int chosenCommand = -1;
        do {
            chosenCommand = UserInputUtil.chooseCommand(Arrays.stream(values).map(Commands::getDescription).toList());
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
