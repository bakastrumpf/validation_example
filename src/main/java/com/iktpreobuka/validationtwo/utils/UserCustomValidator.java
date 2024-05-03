package com.iktpreobuka.validationtwo.utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.iktpreobuka.validationtwo.entities.UserEntity;

@Component
public class UserCustomValidator implements Validator{

	// tip klase nad kojom radi ovaj userCustomValidator je UserEntity
	@Override
	public boolean supports(Class<?> clazz) {
		return UserEntity.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// znamo da radimo sa UserEntity jer smo u metodi *supports* tako definisali
		// ovde radimo eksplicitno kastovanje UserEntity
		UserEntity user = (UserEntity) target;
		if(! user.getPassword().equals(user.getPasswordConfirm())) {
			errors.reject("400", "Passwords must match! Please, type the correct password again.");
		}
	}
}