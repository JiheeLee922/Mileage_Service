package com.triple.mileage.controller;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.triple.mileage.ReviewDTO;
import com.triple.mileage.common.BasicResponse;
import com.triple.mileage.service.MileageService;
import com.triple.mileage.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MileageController 
{

	private final ReviewService reviewService;
	private final MileageService mileageService;

	
	@PostMapping("/events")
	public ResponseEntity<BasicResponse> reviewEvents(@RequestBody ReviewDTO reviewEvents) 
	{
		
		String returnMsg  = "Success";
		int responseCode = 200;
		
		try {
			
			if("ADD".equals(reviewEvents.getAction()))
			{
				reviewService.addReview(reviewEvents).toString();
			}
	
			if("UPDATE".equals(reviewEvents.getAction()))
			{
				reviewService.updateReview(reviewEvents);
			}
			
			if("DELETE".equals(reviewEvents.getAction()))
			{
				reviewService.deleteReview(reviewEvents);
			}
			
		}
		catch (PersistenceException e) 
		{
			returnMsg = "JPA_Exception";
			responseCode = 400;
			e.printStackTrace();
		}
		catch (Exception e) 
		{
			returnMsg = "Fail";
			responseCode = 500;
			e.printStackTrace();
		}
		
		return new ResponseEntity<BasicResponse>(BasicResponse.response(responseCode, returnMsg) ,HttpStatus.OK);
	}
	
	
	
	@GetMapping("/mileage/{userId}")
	public ResponseEntity<BasicResponse> test(@PathVariable("userId") String userId)
	{
		
		Integer userMileages = null;
		String returnMsg  = "Success";
		int responseCode = 200;
	
		try 
		{
			userMileages = mileageService.getMileagesByUser(userId);
		}
		catch (PersistenceException e) 
		{
			returnMsg = "JPA_Exception";
			responseCode = 400;
			userMileages = null;
			
			e.printStackTrace();
		}
		catch (Exception e) 
		{
			returnMsg = "Fail";
			responseCode = 500;
			userMileages = null;
			
			e.printStackTrace();
		}
		
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("userId", userId);
		resultMap.put("mileage", userMileages);
		
		return new ResponseEntity<BasicResponse>(BasicResponse.response(responseCode, returnMsg,resultMap) ,HttpStatus.OK);
	}
	
	
}
