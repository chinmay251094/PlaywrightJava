package com.annotations;

import com.enums.TestCategory;
import com.enums.Tester;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)
public @interface TestInformation {
    public Tester[] author();

    public TestCategory[] category();
}
