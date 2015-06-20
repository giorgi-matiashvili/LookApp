package com.lookapp.api.request;

/**
 * Created by Giorgi on 6/20/2015.
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PropertyOrder {

    String[] value();
}
