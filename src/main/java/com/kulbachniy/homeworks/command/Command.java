package com.kulbachniy.homeworks.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public interface Command {
    void execute() throws IOException;

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
}
