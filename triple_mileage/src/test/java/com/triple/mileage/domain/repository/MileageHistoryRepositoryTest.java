package com.triple.mileage.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.triple.mileage.domain.entity.MileageHistoryEntity;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class MileageHistoryRepositoryTest {
	
	@Autowired
	private MileageHistoryRepository mileageRepository;

	@Test
	void findByReviewIdAndUserIdTest() {
		List<MileageHistoryEntity> listMileage= mileageRepository.findByReviewIdAndUserId("333333", "333g4j");
		assertThat(!listMileage.isEmpty());
	}

	@Test
	void findByUserIdTest() {
		List<MileageHistoryEntity> listMileage= mileageRepository.findByUserId("leejh-3g3g3");
		assertThat(!listMileage.isEmpty());
	}
	
	@Test
	void saveTest() {
		MileageHistoryEntity mileageEntity = MileageHistoryEntity.builder()
											.userId("mileage-save-test-user-id")
											.historyType("+")
											.mileageAmount(100)
											.reviewId("mileage-save-test-review-id")
											.build();
		mileageRepository.save(mileageEntity);
		
		assertThat(mileageRepository.findByUserId(mileageEntity.getUserId()).get(0).getMileageAmount() == 100);
	}

}
