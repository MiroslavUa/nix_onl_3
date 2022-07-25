package com.kulbachniy.homeworks.command;

public enum Commands {
    CREATE("Create derivative", new Create()),
    FIND("Find derivative by ticker", new Find()),
//    FIND_ALL("Find all derivatives", new FindAll()),
//    UPDATE("Update derivative info", new Update()),
//    DELETE("Delete derivative by ticker", new Delete()),
//    PRINT("Get derivative info", new Print()),
    EXIT("Exit", null);

    private final String description;
    private final Command command;

    Commands(String description, Command command){
        this.description = description;
        this.command = command;
    }

    public String getDescription(){
        return this.description;
    }

    public Command getCommand(){
        return this.command;
    }


}
