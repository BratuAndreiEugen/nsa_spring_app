package com.nsa.nsa_spring_app.config.security.annot;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@PreAuthorize("hasAuthority('ADMIN')")
public @interface AllowAdminOnly {
}
