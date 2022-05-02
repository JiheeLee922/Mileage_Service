package com.triple.mileage.domain.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.triple.mileage.domain.entity.ReviewPhotoMapEntity;
import com.triple.mileage.domain.entity.id.ReviewPhotoMapID;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ReviewPhotoMapRepositoryTest {

	@Autowired
	ReviewPhotoMapRepository photoRepository;
	
	@Test
	void updateReviewPhotoMapDelYTest() {
		String reviewId = "3023-58-93r93238rjf-32";
		photoRepository.updateReviewPhotoMapDelY(reviewId);
		
		boolean check =photoRepository.findByReviewId(reviewId).stream().allMatch(p -> "Y".equals(p.getDelYn()));
		assertTrue(check); 
	}
	
	@Test
	void saveTest() {
		ReviewPhotoMapEntity photoEntity = ReviewPhotoMapEntity.builder()
											.reviewId("review-save-test-review-id")
											.attachedPhotoId("review-save-test-photo-id")
											.delYn("N")
											.build();
		photoRepository.save(photoEntity);
		
		assertTrue(photoRepository.findByReviewId(photoEntity.getReviewId()).size() > 0);
	}

}
