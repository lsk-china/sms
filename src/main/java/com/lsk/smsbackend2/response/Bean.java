package com.lsk.smsbackend2.response;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * This annotation is used to mark a class as a java Bean so that Response#objectToMap can detect if it should
 * change it into a map.
 * I write it because I've just met a strange bug, and I can't make it happen again. In order to avoid it happen
 * again in production environment, I decide to create this annotation.
 * Perhaps this is not a good design...
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {
}
