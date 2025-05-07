package com.tonorganisation.check_common_lib.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Entry point for the CheckCommonLibraryApplication.
 *
 * This class is annotated with @SpringBootApplication, indicating it as the primary
 * configuration class used to bootstrap a Spring Boot application. The main method
 * initializes the application by invoking SpringApplication.run.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface  RetryOnfailure {

    /**
     * Specifies the maximum number of retry attempts for the annotated method.
     *
     * @return the maximum number of retry attempts. Defaults to 5 if not provided.
     */
    int maxAttempts() default 3;

    /**
     * Specifies the delay in milliseconds between retry attempts for the annotated method.
     *
     * @return the delay in milliseconds between retry attempts. Defaults to 500 milliseconds.
     */
    long delayMs() default 500;

    /**
     * Specifies a list of exception classes that should trigger retries for the
     * @return an array of exception classes that are included from retry handling.
     *        Defaults to an empty array if not specified.
     * */
    Class<? extends Throwable>[] include() default {};


    /**
     * Specifies a list of exception classes that should not trigger retries for the annotated method.
     * If an exception belonging to any of the classes listed in this array is thrown, the retry
     * mechanism will not be executed.
     *
     * @return an array of exception classes that are excluded from retry handling.
     *         Defaults to an empty array if not specified.
     */
    Class<? extends Throwable>[] exclude() default {};
}
