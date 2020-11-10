package fr.mistergoliath.votes.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface APIInfos {
	String baseUrl() default "";
}
