package com.iktpreobuka.validationtwo.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.validationtwo.entities.UserEntity;
import com.iktpreobuka.validationtwo.repositories.UserRepository;
import com.iktpreobuka.validationtwo.utils.UserCustomValidator;

@RestController
@RequestMapping(path = "/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserCustomValidator userCustomValidator;

	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(userCustomValidator);
	}

	@RequestMapping(method = RequestMethod.POST)
	// ako prilikom parsiranja dodje do grešaka, one se upisuju u *BindingResult result*
	// pravimo novi *ResponseEntity* i dajemo mu taj *result*
	public ResponseEntity<?> createUser(@Valid @RequestBody UserEntity user, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} 
		userRepository.save(user);
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}

	// kad nam vrati listu grešaka, od nje pravimo *stream*
	// pa svaki element tog toka mapiramo na *ObjectError*
	// iz *ObjectError* izvlačimo podrazumevanu poruku
	// skupljamo ih i spajamo tako što stavljamo novi red posle svake
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("\n"));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/minAge")
	public ResponseEntity<?> minAge() {
		Integer minAge = 0;
		for (UserEntity user : userRepository.findAll()) {
			if (user.getAge() < minAge)
				minAge = user.getAge();
		}
		return new ResponseEntity<>(minAge, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{username}")
	public ResponseEntity<?> getUserByUserName(@PathVariable String username) {
		for (UserEntity user : userRepository.findAll()) {
			if (user.getUsername() == username)
				return new ResponseEntity<>(user, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/isOver21/{id}")
	public ResponseEntity<?> isOver21(@PathVariable Integer id) {
		boolean americanAdult = false;
		UserEntity user = userRepository.findById(id).get();
		if (user.getAge() >= 21) {
			americanAdult = true;
		}
		// sve iznad nema smisla jer nema ELSE i uvek će postaviti vrednost na FALSE
		americanAdult = false;
		return new ResponseEntity<>(americanAdult, HttpStatus.OK);
	}
}