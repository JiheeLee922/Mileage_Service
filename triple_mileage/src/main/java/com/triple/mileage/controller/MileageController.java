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



/**
 * @packageName : com.triple.mileage.controller
 * @author 		: dhkdn
 * @date		: 2022. 5. 1.
 * @description : 마일리지 지급, 마일리지 조회 컨트롤러
 *
 */
@RestController
@RequiredArgsConstructor
public class MileageController 
{

	private final ReviewService reviewService;
	private final MileageService mileageService;

	
	/** 
	 * @methodName 	: reviewEvents 
	 * @author 		: dhkdn
	 * @date 		: 2022.04.28 
	 * @description : 마일리지 지급 & 리뷰 저장 
	 * @param reviewEvents
	 * @return ResponseEntity
	*/
	@PostMapping("/events")
	public ResponseEntity<BasicResponse> reviewEvents(@RequestBody ReviewDTO reviewEvents) 
	{
		Map<String,Object> resultMap = new HashMap<>();
		String returnMsg  = "Success";
		int responseCode = 200;
		int executeMileages = 0;
		
		try {
			
			if("ADD".equals(reviewEvents.getAction()))
			{
				executeMileages = reviewService.addReview(reviewEvents);
			}
	
			if("UPDATE".equals(reviewEvents.getAction()))
			{
				executeMileages = reviewService.updateReview(reviewEvents);
			}
			
			if("DELETE".equals(reviewEvents.getAction()))
			{
				executeMileages = reviewService.deleteReview(reviewEvents);
			}
			
			resultMap.put("executeMileages", executeMileages);
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
		
		return new ResponseEntity<BasicResponse>(BasicResponse.response(responseCode, returnMsg, resultMap) ,HttpStatus.OK);
	}
	
	
	
	/** 
	 * @methodName 	: getMileages 
	 * @author 		: dhkdn
	 * @date 		: 2022.05.01 
	 * @description : 마일리지 조회
	 * @param userId
	 * @return 
	*/
	@GetMapping("/mileage/{userId}")
	public ResponseEntity<BasicResponse> getMileages(@PathVariable("userId") String userId)
	{
		Map<String,Object> resultMap = new HashMap<>();
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
		
		
		resultMap.put("userId", userId);
		resultMap.put("mileage", userMileages);
		
		return new ResponseEntity<BasicResponse>(BasicResponse.response(responseCode, returnMsg,resultMap) ,HttpStatus.OK);
	}
	
	
}
