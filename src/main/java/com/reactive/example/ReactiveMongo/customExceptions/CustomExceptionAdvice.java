package com.reactive.example.ReactiveMongo.customExceptions;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.reactive.example.ReactiveMongo.entities.ErrorDto;

import reactor.core.publisher.Mono;

@ControllerAdvice
public class CustomExceptionAdvice {

	private static final String CUSTOM_ERROR_MESSAGE_NAME = "A problem with the service has ocurred";
	
	@ExceptionHandler({Exception.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Mono<ResponseEntity<ErrorDto>> handleConflict(Exception exception) throws IOException {
		
		return Mono.just(new ResponseEntity<ErrorDto>(new ErrorDto(CUSTOM_ERROR_MESSAGE_NAME,
				exception.getMessage()), HttpStatus.BAD_REQUEST));

	}
}