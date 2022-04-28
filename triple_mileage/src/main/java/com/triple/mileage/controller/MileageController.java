package com.triple.mileage.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.triple.mileage.ReviewDTO;
import com.triple.mileage.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MileageController {

	private final ReviewService reviewService;
	
	@PostMapping("/events")
	public ResponseEntity<String> reviewEvents(@RequestBody ReviewDTO reviewEvents){
		
		String returnMsg  = "success";
		if("ADD".equals(reviewEvents.getAction())){
			returnMsg = reviewService.addReview(reviewEvents).toString();
		}

		if("DELETE".equals(reviewEvents.getAction())){
			reviewService.deleteReview(reviewEvents);
		}
		return new ResponseEntity<String>(returnMsg ,HttpStatus.OK);
	}
}
