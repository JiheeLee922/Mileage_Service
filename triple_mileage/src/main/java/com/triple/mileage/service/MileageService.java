package com.triple.mileage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.triple.mileage.domain.entity.MileageHistoryEntity;
import com.triple.mileage.domain.repository.MileageHistoryRepository;

import lombok.RequiredArgsConstructor;



/**
 * @packageName : com.triple.mileage.service
 * @author 		: dhkdn
 * @date		: 2022. 5. 1.
 * @description : 사용자의 마일리지(포인트) 서비스
 *
 */
@Service
@RequiredArgsConstructor
public class MileageService {

	private final MileageHistoryRepository mileageRepository;
	
	
	/** 
	 * @methodName 	: getMileagesByUser 
	 * @author 		: dhkdn
	 * @date 		: 2022.05.01 
	 * @description : 사용자의 현재 마일리지 조회
	 * @param userId
	 * @return 사용자의 전체 마일리지
	 * @throws Exception 
	*/
	public Integer getMileagesByUser(String userId) throws Exception
	{
		List<MileageHistoryEntity> mileageList =  mileageRepository.findByUserId(userId);
		Integer totalMileage = mileageList.stream().mapToInt(m -> m.getMileageAmount() * Integer.parseInt(m.getHistoryType() + 1)).sum();
		
		return totalMileage;
	}
}
