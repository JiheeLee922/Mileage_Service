package com.triple.mileage.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.triple.mileage.domain.entity.ReviewEntity;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, String> {

	public boolean existsByPlaceIdAndDelYn(String placeId, String delYn);
	
}
