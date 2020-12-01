package br.edu.iff.projetoFarmaceutico.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValidation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
       if(value==null) return false;
       if(value.contains(" ")) return false;
       return value.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#])[0-9a-zA-Z$*&@#]{8,}$");
       
    }  
    
}
