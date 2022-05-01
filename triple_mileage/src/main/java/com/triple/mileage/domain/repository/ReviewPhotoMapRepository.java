package com.triple.mileage.domain.repository;

import javax.transaction.Transactional;

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
	@Transactional
	@Query(value = "UPDATE review_photo_map r SET r.del_yn = 'Y' , del_date = now() WHERE r.review_id = :reviewId", nativeQuery = true)
	int updateReviewPhotoMapDelY(@Param("reviewId") String reviewId);
}
