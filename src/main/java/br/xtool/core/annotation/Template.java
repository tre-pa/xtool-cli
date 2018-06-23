package br.xtool.core.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.shell.standard.ShellComponent;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface Template {

	String path();

}
