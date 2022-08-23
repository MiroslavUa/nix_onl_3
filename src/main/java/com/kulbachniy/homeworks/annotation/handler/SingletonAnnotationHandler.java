package com.kulbachniy.homeworks.annotation.handler;

import com.kulbachniy.homeworks.annotation.Cash;
import org.reflections.Reflections;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class SingletonAnnotationHandler implements Cash {
    public Set<Class<?>> analyze(String pack, Class<? extends Annotation> annotation){
        Reflections reflection = new Reflections(pack);
        return reflection.getTypesAnnotatedWith(annotation);
    }

    public void handle(String pack, Class<? extends Annotation> annotation) {
        Set<Class<?>> classes = analyze(pack, annotation);
        classes.forEach(c -> {
                    try {
                        Cash.classes.put(c, c.getMethod("getInstance").invoke(null));
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
