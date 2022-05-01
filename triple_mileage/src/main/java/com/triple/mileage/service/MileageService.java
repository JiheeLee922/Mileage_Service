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
 * @description : ������� ���ϸ���(����Ʈ) ����
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
	 * @description : ������� ���� ���ϸ��� ��ȸ
	 * @param userId
	 * @return ������� ��ü ���ϸ���
	 * @throws Exception 
	*/
	public Integer getMileagesByUser(String userId) throws Exception
	{
		List<MileageHistoryEntity> mileageList =  mileageRepository.findByUserId(userId);
		Integer totalMileage = mileageList.stream().mapToInt(m -> m.getMileageAmount() * Integer.parseInt(m.getHistoryType() + 1)).sum();
		
		return totalMileage;
	}
}
