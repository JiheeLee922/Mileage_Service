package com.triple.mileage.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.triple.mileage.domain.entity.MileageHistoryEntity;

@Repository
public interface MileageHistoryRepository extends JpaRepository<MileageHistoryEntity, Long> {

	public List<MileageHistoryEntity> findByReviewIdAndUserId(String reviewId, String userId);

	public List<MileageHistoryEntity> findByUserId(String userId);
}
