package com.triple.mileage.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.triple.mileage.ReviewDTO;
import com.triple.mileage.domain.entity.ReviewEntity;
import com.triple.mileage.domain.repository.ReviewRepository;
import com.triple.mileage.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MileageController {

	private final ReviewService reviewService;
	private final ReviewRepository reviewRepositroy;
	//private final ReviewPhotoMapRepository reviewPhotoRepositroy;
	
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
	
	@GetMapping("/test")
	public ResponseEntity<List<ReviewEntity>> test(){
		List<ReviewEntity> list = reviewRepositroy.findAll();
		return new ResponseEntity<List<ReviewEntity>>(list, HttpStatus.OK);
	}
}
