package com.triple.mileage.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.triple.mileage.ReviewDTO;
import com.triple.mileage.domain.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewRepository reviewRepositroy;
	
	
	public Integer addReview(ReviewDTO review) {
		
		int addMileages = 0;
		
		if(review.getContent() != null && review.getContent().length() > 0 ) ++addMileages;
		//Optional.of(review).map(ReviewDTO::getContent).isPresent();
		
		if(review.getAttachedPhotoIds().length > 0 ) ++addMileages;
		
		boolean boolIsFirstReview =  reviewRepositroy.existsByPlaceIdAndDelYn(review.getPlaceId(), "N");
		if(!boolIsFirstReview) ++addMileages;  
		
		return addMileages;
	}
}
