package com.triple.mileage.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.triple.mileage.domain.entity.ReviewPhotoMapEntity;
import com.triple.mileage.domain.entity.id.ReviewPhotoMapID;

@Repository
public interface ReviewPhotoMapRepository extends JpaRepository<ReviewPhotoMapEntity, ReviewPhotoMapID> {

	@Modifying(clearAutomatically =  true)
	@Query(value = "UPDATE REVIEW_PHOTO_MAP r SET r.DEL_YN = 'Y' WHERE r.REVIEW_ID = :reviewId", nativeQuery = true)
	int updateReviewPhotoMapDelY(@Param("reviewId") String reviewId);
}
