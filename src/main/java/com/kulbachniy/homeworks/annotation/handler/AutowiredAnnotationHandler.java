package com.kulbachniy.homeworks.annotation.handler;

import com.kulbachniy.homeworks.annotation.Autowired;
import com.kulbachniy.homeworks.annotation.Cash;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class AutowiredAnnotationHandler implements Cash {
    public Set<Constructor<?>> analyze(String pack, Class<? extends Annotation> annotation){
        Set<Class<?>> classes = new SingletonAnnotationHandler().analyze(pack, annotation);
        Set<Constructor<?>> constructors = new HashSet<>();
        classes.stream().flatMap(c -> Arrays.stream(c.getDeclaredConstructors()))
                .filter(constructor -> constructor.getAnnotation(Autowired.class) != null)
                .forEach(constructors::add);
        return constructors;
    }

    @Override
    public void handle(String pack, Class<? extends Annotation> annotation) {
        Set<Class<?>> classes = analyze(pack, annotation).stream()
                .map(Constructor::getDeclaringClass)
                .collect(Collectors.toSet());
        classes.forEach(c -> {
            try {
                Cash.classes.put(c, c.getMethod("getInstance").invoke(null));
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
