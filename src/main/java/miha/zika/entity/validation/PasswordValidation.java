/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miha.zika.entity.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import miha.zika.entity.annotaitions.PassworMatches;
import miha.zika.payload.request.SignUpRequest;



public class PasswordValidation implements ConstraintValidator<PassworMatches, Object>{

    @Override
    public void initialize(PassworMatches constraintAnnotation) {
    
    }

    @Override
    public boolean isValid(Object t, ConstraintValidatorContext cvc) {
       SignUpRequest userSignupRequest = (SignUpRequest) t;
       return userSignupRequest.getPassword().equals(userSignupRequest.getConfirmPassword());
    }
}
