package com.triple.mileage.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.triple.mileage.domain.entity.ReviewEntity;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, String> {

	public boolean existsByPlaceIdAndDelYn(String placeId, String delYn);
	
	@Query(value = "SELECT * FROM review r LEFT JOIN review_photo_map pm ON r.review_id = pm.review_id AND pm.del_yn = :delYn  WHERE r.review_id = :reviewId ", nativeQuery = true)
	public ReviewEntity findByReviewIdAndAttachedPhotoIdsDelYn(@Param("reviewId") String reviewId,@Param("delYn") String delYn);
	
}
