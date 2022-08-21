package com.kulbachniy.homeworks.annotation;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.*;

public interface Cash {
    Map<Class<?>, Object> classes = new HashMap<>();

    Set<?> analyze(String pack, Class<? extends Annotation> annotation);

    void handle(String pack, Class<? extends Annotation> annotation);
}
