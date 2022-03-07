package com.abn.dish.exception;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponse {

	private int code;
	private String message;
	private List<String> details;
	private String path;
	private Date timestamp;

}