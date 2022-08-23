package com.kulbachniy.homeworks;

import com.kulbachniy.homeworks.annotation.Cash;
import com.kulbachniy.homeworks.annotation.Singleton;
import com.kulbachniy.homeworks.annotation.handler.AutowiredAnnotationHandler;
import com.kulbachniy.homeworks.annotation.handler.SingletonAnnotationHandler;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        //ProgramRun.run();

        Cash cash;

        cash = new SingletonAnnotationHandler();
        cash.handle("com", Singleton.class);
        System.out.println(Cash.classes.size());

        cash = new AutowiredAnnotationHandler();
        cash.handle("com", Singleton.class);
        System.out.println(Cash.classes.size());
    }
}
