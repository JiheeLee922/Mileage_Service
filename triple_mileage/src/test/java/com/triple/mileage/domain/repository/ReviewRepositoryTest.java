package com.triple.mileage.domain.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.triple.mileage.domain.entity.ReviewEntity;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
class ReviewRepositoryTest {

	@Autowired
	private ReviewRepository reviewRepository;
	
	@Test
	void existsByPlaceIdAndDelYn() {
		Boolean returnVal = reviewRepository.existsByPlaceIdAndDelYn("test-place-1", "N");
		assertNotNull(returnVal); 
	}
	
	@Test
	void findByReviewIdAndAttachedPhotoIdsDelYn() {
		ReviewEntity review = reviewRepository.findByReviewIdAndAttachedPhotoIdsDelYn("3023-58-93r93238rjf-32", "N");
		assertNotNull(review); 
	}

}
