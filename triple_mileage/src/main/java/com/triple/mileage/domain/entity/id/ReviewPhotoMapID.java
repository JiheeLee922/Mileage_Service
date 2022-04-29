package com.triple.mileage.domain.entity.id;

import java.io.Serializable;

import com.triple.mileage.domain.entity.ReviewEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewPhotoMapID implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//private ReviewEntity review;
	private String reviewId;
	private String attachedPhotoId;
}
