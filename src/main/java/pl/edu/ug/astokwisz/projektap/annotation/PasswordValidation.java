package pl.edu.ug.astokwisz.projektap.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import pl.edu.ug.astokwisz.projektap.validator.PasswordValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordValidation {

    String message() default "Hasło musi zawierać minimum 10 znaków, w tym 1 wielką literę, 1 cyfrę oraz 1 znak specjalnu";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
