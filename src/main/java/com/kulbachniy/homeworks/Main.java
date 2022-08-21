package com.kulbachniy.homeworks;

import com.kulbachniy.homeworks.annotation.Autowired;
import com.kulbachniy.homeworks.annotation.Cash;
import com.kulbachniy.homeworks.annotation.Singleton;
import com.kulbachniy.homeworks.annotation.handler.AutowiredAnnotationHandler;
import com.kulbachniy.homeworks.annotation.handler.SingletonAnnotationHandler;
import com.kulbachniy.homeworks.command.ProgramRun;
import com.kulbachniy.homeworks.model.derivative.Stock;
import com.kulbachniy.homeworks.service.StockServiceParser;
import javassist.tools.reflect.Reflection;
import org.reflections.Reflections;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) throws IOException {
        //ProgramRun.run();

//        Set<Class<?>> classes = new SingletonAnnotationHandler().analyze("com", Singleton.class);
//        classes.stream().forEach(c -> System.out.println(c.getName()));

//        Set<Constructor<?>> constructors = new AutowiredAnnotationHandler().analyze("com", Singleton.class);
//        constructors.stream().forEach(constructor -> System.out.println(constructor.getName()));

        Cash cash = new SingletonAnnotationHandler();
        System.out.println(Cash.classes.size());
        cash.handle("com", Singleton.class);
        System.out.println(Cash.classes.size());

       AutowiredAnnotationHandler autowiredAnnotationHandler = new AutowiredAnnotationHandler();
       Set<Constructor> constructors = autowiredAnnotationHandler.analyze("com", Autowired.class);
        System.out.println(constructors.size());


    }
}

