/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miha.zika.entity.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import miha.zika.entity.annotaitions.ValidEmail;

/**
 *
 * @author Miha
 */
public class EmailValidator implements ConstraintValidator<ValidEmail, String>{
  private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

    @Override
    public void initialize(ValidEmail constraintAnnotation) {
    
    }

    @Override
    public boolean isValid(String t, ConstraintValidatorContext cvc) {
    return isValidation(t);
    }

    private boolean isValidation(String s){
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }
}
