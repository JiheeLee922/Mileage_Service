package com.triple.mileage.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.triple.mileage.ReviewDTO;
import com.triple.mileage.domain.entity.MileageHistoryEntity;
import com.triple.mileage.domain.entity.ReviewEntity;
import com.triple.mileage.domain.entity.ReviewPhotoMapEntity;
import com.triple.mileage.domain.repository.MileageHistoryRepository;
import com.triple.mileage.domain.repository.ReviewPhotoMapRepository;
import com.triple.mileage.domain.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewRepository reviewRepositroy;
	private final ReviewPhotoMapRepository reviewPhotoMapRepositroy;
	private final MileageHistoryRepository mileageHistoryRepository;
	
	
	public Integer addReview(ReviewDTO review)  throws Exception
	{
		
		// ������ ���ϸ��� ���
		int addMileages = 0;
		
		if(!StringUtils.isBlank(review.getContent()) && review.getContent().length() > 0 ) ++addMileages;
		
		if(review.getAttachedPhotoIds() != null && review.getAttachedPhotoIds().size() > 0 ) ++addMileages;
		
		boolean boolIsFirstReview =  reviewRepositroy.existsByPlaceIdAndDelYn(review.getPlaceId(), "N");
		if(!boolIsFirstReview) ++addMileages; 
		
		review.getAttachedPhotoIds().forEach(p -> {
			ReviewPhotoMapEntity photoEntity = ReviewPhotoMapEntity.builder()
												.reviewId(review.getReviewId())
												.attachedPhotoId(p)
												.delYn("N")
												.build();
			reviewPhotoMapRepositroy.save(photoEntity);
		});
		
		ReviewEntity reviewEntity = ReviewEntity.builder()
										.reviewId(review.getReviewId())
										.placeId(review.getPlaceId())
										.userId(review.getUserId())
										.content(review.getContent())
										.delYn("N")
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
	
	
	public void deleteReview(ReviewDTO review) throws Exception
	{
		
		//���� ����
		ReviewEntity beforeEntity = reviewRepositroy.findByReviewIdAndAttachedPhotoIdsDelYn(review.getReviewId(), "N");
		
		ReviewEntity reviewEntity = ReviewEntity.builder()
				.reviewId(review.getReviewId())
				.placeId(beforeEntity.getPlaceId())
				.userId(beforeEntity.getUserId()) 
				.content(beforeEntity.getContent())
				.delYn("Y")
				.updtDate(beforeEntity.getUpdtDate())
				.delDate(LocalDateTime.now()) 
				.build(); 

		reviewRepositroy.save(reviewEntity);
		
		reviewPhotoMapRepositroy.updateReviewPhotoMapDelY(review.getReviewId());
		
		
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
	
	public void updateReview(ReviewDTO review) throws Exception
	{
		int executeMileages = 0;
		String historyType = "+";
		
		//���ϸ��� �߰�/ȸ�� üũ
		ReviewEntity reviewEntity = reviewRepositroy.findByReviewIdAndAttachedPhotoIdsDelYn(review.getReviewId(), "N");
		
		if(reviewEntity.getAttachedPhotoIds().size() < 1 
				&& review.getAttachedPhotoIds() != null && review.getAttachedPhotoIds().size() > 0) {
			executeMileages = 1;
		}
		else if(reviewEntity.getAttachedPhotoIds().size() > 0 
				&& (review.getAttachedPhotoIds() == null || review.getAttachedPhotoIds().size() < 1 )) {
			executeMileages = 1;
			historyType = "-";
		}
		
		//���� ���� üũ
		List<ReviewPhotoMapEntity> removePhoto = reviewEntity.getAttachedPhotoIds().stream().filter(r -> 
					review.getAttachedPhotoIds().indexOf(r.getAttachedPhotoId()) <  0 ).toList();
		
		
		review.getAttachedPhotoIds().forEach(p -> {
			ReviewPhotoMapEntity photoEntity = ReviewPhotoMapEntity.builder()
												.reviewId(review.getReviewId())
												.attachedPhotoId(p)
												.delYn("N")
												.build();
			reviewPhotoMapRepositroy.save(photoEntity);
		});
		
		//������ ���� ���� ����
		removePhoto.forEach(rp -> {
			ReviewPhotoMapEntity photoEntity = ReviewPhotoMapEntity.builder()
											.reviewId(review.getReviewId())
											.attachedPhotoId(rp.getAttachedPhotoId())
											.delYn("Y")
											.delDate(LocalDateTime.now())
											.build();
			reviewPhotoMapRepositroy.save(photoEntity);
		});
		
		//���� ����
		ReviewEntity reviewSaveEntity = ReviewEntity.builder()
										.reviewId(review.getReviewId())
										.placeId(review.getPlaceId())
										.userId(review.getUserId())
										.content(review.getContent())
										.updtDate(LocalDateTime.now()) 
										.delYn("N")
										.build();
		reviewRepositroy.save(reviewSaveEntity);
		
		
		//���ϸ��� ����
		if(executeMileages != 0 ) {
			
			MileageHistoryEntity mileageEntity = MileageHistoryEntity.builder()
													.userId(review.getUserId())
													.historyType(historyType)
													.mileageAmount(executeMileages)
													.reviewId(review.getReviewId())
													.build();
			mileageHistoryRepository.save(mileageEntity);
		}
		
		
	}
}
