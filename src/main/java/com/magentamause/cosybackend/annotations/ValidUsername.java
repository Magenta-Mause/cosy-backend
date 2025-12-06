package com.magentamause.cosybackend.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
@Pattern(
		regexp = "^[a-zA-Z0-9_-]*$",
		message = "Username can only contain letters, numbers, underscores and hyphens"
)
public @interface ValidUsername {

	String message() default "Invalid username";

	Class<?>[] groups() default {};

	Class<?>[] payload() default {};
}
