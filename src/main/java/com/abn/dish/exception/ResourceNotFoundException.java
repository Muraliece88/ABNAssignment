package com.abn.dish.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;


@ResponseStatus(HttpStatus.NOT_FOUND)
@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2381336079355466521L;

	public ResourceNotFoundException(String message)
	{
		super(message);
	}

}
