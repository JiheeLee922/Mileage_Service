package com.triple.mileage.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.triple.mileage.ReviewDTO;
import com.triple.mileage.domain.entity.MileageHistoryEntity;
import com.triple.mileage.domain.entity.ReviewEntity;
import com.triple.mileage.domain.entity.ReviewPhotoMapEntity;
import com.triple.mileage.domain.repository.MileageHistoryRepository;
import com.triple.mileage.domain.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewRepository reviewRepositroy;
	private final MileageHistoryRepository mileageHistoryRepository;
	
	
	public Integer addReview(ReviewDTO review) {
		
		// ������ ���ϸ��� ���
		int addMileages = 0;
		
		if(!StringUtils.isBlank(review.getContent()) && review.getContent().length() > 0 ) ++addMileages;
		//Optional.of(review).map(ReviewDTO::getContent).isPresent();
		
		if(review.getAttachedPhotoIds().size() > 0 ) ++addMileages;
		
		boolean boolIsFirstReview =  reviewRepositroy.existsByPlaceIdAndDelYn(review.getPlaceId(), "N");
		if(!boolIsFirstReview) ++addMileages; 
		
		//ModelMapper modelMapper = new ModelMapper();
		//ReviewEntity reviewEntity = modelMapper.map(review, ReviewEntity.class);
		List<ReviewPhotoMapEntity> photoMapEntity =  review.getAttachedPhotoIds().stream().map(p -> {
			ReviewPhotoMapEntity photoEntity = ReviewPhotoMapEntity.builder()
												.reviewId(review.getReviewId())
												.attachedPhotoId(p)
												.delYn("N")
												.build();
			return photoEntity;
		}).collect(Collectors.toList()); 
		
		ReviewEntity reviewEntity = ReviewEntity.builder()
										.reviewId(review.getReviewId())
										.placeId(review.getPlaceId())
										.userId(review.getUserId())
										.content(review.getContent())
										.delYn("N")
										.attachedPhotoIds(photoMapEntity) 
										.build();
		
		
		//���� ����
		reviewRepositroy.save(reviewEntity);
		
		
		//���ϸ��� ����
		MileageHistoryEntity mileageEntity = MileageHistoryEntity.builder()
												.userId(review.getUserId())
												.historyType("+")
												.mileageAmount(addMileages)
												.reviewId(review.getReviewId())
												.build();
		mileageHistoryRepository.save(mileageEntity);
		
		
		return addMileages;
	}
	
	
	public void deleteReview(ReviewDTO review)
	{
		
		//���� ����
		ReviewEntity reviewEntity = ReviewEntity.builder()
				.reviewId(review.getReviewId())
				.placeId(review.getPlaceId())
				.userId(review.getUserId()) 
				.content(review.getContent())
				.delYn("Y")
				.build();

		reviewRepositroy.save(reviewEntity);
		
		
		//���ϸ��� ȸ��
		List<MileageHistoryEntity> mileageList =  mileageHistoryRepository.findByReviewIdAndUserId(review.getReviewId(), review.getUserId());
		Integer totalMileage = mileageList.stream().mapToInt(m -> m.getMileageAmount() * Integer.parseInt(m.getHistoryType() + 1)).sum();
		
		MileageHistoryEntity mileageEntity = MileageHistoryEntity.builder()
											.userId(review.getUserId())
											.historyType("-")
											.mileageAmount(totalMileage)
											.reviewId(review.getReviewId())
											.build();
		mileageHistoryRepository.save(mileageEntity);
		
	}
}
