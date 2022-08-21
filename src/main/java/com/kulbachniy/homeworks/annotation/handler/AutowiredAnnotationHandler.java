package com.kulbachniy.homeworks.annotation.handler;

import com.kulbachniy.homeworks.annotation.Cash;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.*;

public class AutowiredAnnotationHandler implements Cash {
    public Set<Constructor> analyze(String pack, Class<? extends Annotation> annotation){
//        Set<Class<?>> classes = new SingletonAnnotationHandler().analyze(pack, annotation);
//        Set<Constructor<?>> constructors = new HashSet<>();
//        classes.stream().flatMap(c -> Arrays.stream(c.getDeclaredConstructors()))
//                .filter(constructor -> constructor.getAnnotation(Autowired.class) != null)
//                .forEach(constructors::add);
//        return constructors;
        Reflections reflection = new Reflections(pack);
        return reflection.getConstructorsAnnotatedWith(annotation);


    }

    @Override
    public void handle(String pack, Class<? extends Annotation> annotation) {

    }
}
