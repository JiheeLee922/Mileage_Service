package com.triple.mileage;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO {

	private String type;
	private String action;
	private String reviewId;
	private String content;
	private List<String> attachedPhotoIds;
	private String userId;
	private String placeId;
	
	
}
