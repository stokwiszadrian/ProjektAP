package pl.edu.ug.astokwisz.projektap.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pl.edu.ug.astokwisz.projektap.annotation.PasswordValidation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<PasswordValidation, String> {

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        if (value.length() < 10) return false;
        Pattern upperCase = Pattern.compile(".?[A-Z]+.?");
        Pattern digits = Pattern.compile(".?[0-9]+.?");
        Pattern specials = Pattern.compile(".?[$&+,:;=?@#|'<>.^*()%!-]+.?");
        Matcher mUpperCase = upperCase.matcher(value);
        Matcher mDigits = digits.matcher(value);
        Matcher mSpecials = specials.matcher(value);
        while (mUpperCase.find()) {
            if (mUpperCase.group(1) == null) return false;
        }
        while (mDigits.find()) {
            if (mDigits.group(1) == null) return false;
        }
        while (mSpecials.find()) {
            if (mSpecials.group(1) == null) return false;
        }
        return true;
    }

    public void initialize(PasswordValidation constraintAnnotation) {
    }
}