package com.triple.mileage.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BasicResponse<T> {

	private int statusCode;
	private String responseMessage;
	private T data;
	
	public BasicResponse(int statusCode, String responseMessage) {
		this.statusCode = statusCode;
		this.responseMessage = responseMessage;
		this.data = null;
	}
	
	public static<T> BasicResponse<T> response(final int statusCode, String responseMessage){
		return response(statusCode, responseMessage, null);
	}
	
	public static<T> BasicResponse<T> response(final int statusCode, final String responseMessage, final T t)
	{
		return BasicResponse.<T>builder()
				.data(t)
				.statusCode(statusCode)
				.responseMessage(responseMessage)
				.build();
	}
	
	
}
