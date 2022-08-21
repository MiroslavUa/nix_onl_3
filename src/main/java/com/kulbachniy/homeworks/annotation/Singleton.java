package com.kulbachniy.homeworks.annotation;

import com.kulbachniy.homeworks.service.StockService;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Singleton {
}
