package com.triple.mileage.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.triple.mileage.ReviewDTO;
import com.triple.mileage.domain.entity.MileageHistoryEntity;
import com.triple.mileage.domain.entity.ReviewEntity;
import com.triple.mileage.domain.entity.ReviewPhotoMapEntity;
import com.triple.mileage.domain.repository.MileageHistoryRepository;
import com.triple.mileage.domain.repository.ReviewPhotoMapRepository;
import com.triple.mileage.domain.repository.ReviewRepository;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

	private ReviewRepository reviewRepositroy = Mockito.mock(ReviewRepository.class); 
	private ReviewPhotoMapRepository reviewPhotoMapRepositroy = Mockito.mock(ReviewPhotoMapRepository.class); ;
	private MileageHistoryRepository mileageHistoryRepository = Mockito.mock(MileageHistoryRepository.class); 
	
	@InjectMocks
	private ReviewService reviewService =  new ReviewService(reviewRepositroy, reviewPhotoMapRepositroy, mileageHistoryRepository);
	
	
	@Test
	void testAddReview() 
	{
		try 
		{
			ReviewDTO review = new ReviewDTO();
			review.setAction("ADD");
			review.setType("REVIEW");
			review.setContent("content");
			review.setPlaceId("test-place-1");
			review.setUserId("test-user-1"); 
			review.setReviewId("test-review-1"); 
			List<String> listPhotos = new ArrayList <>();
			review.setAttachedPhotoIds(listPhotos);
			
			//1. 해당 장소에 첫번째 리뷰 & 내용 1자이상
			Integer addMileage = reviewService.addReview(review);
			assertThat(addMileage == 2);
			
			
			//2. 해당 장소에 첫번째 리뷰 & 내용 1자이상 & 포토 1개 이상
			listPhotos.add("test-photo-1");
			review.setAttachedPhotoIds(listPhotos); 
			
			addMileage = reviewService.addReview(review);
			assertThat(addMileage == 3);
			
			//3. 내용 1자이상 
			when(reviewRepositroy.existsByPlaceIdAndDelYn(review.getPlaceId(), "N")).thenReturn(true);
			review.setAttachedPhotoIds(new ArrayList<>()); 
			addMileage = reviewService.addReview(review);
			assertThat(addMileage == 1);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			fail("Failed");
		}
	}

	@Test
	void testUpdateReview() 
	{
		try 
		{
			ReviewDTO review = new ReviewDTO();
			review.setAction("UPDATE");
			review.setType("REVIEW");
			review.setContent("content");
			review.setPlaceId("test-place-1");
			review.setUserId("test-user-1"); 
			review.setReviewId("test-review-1"); 
			List<String> listPhotos = new ArrayList <>();
			review.setAttachedPhotoIds(listPhotos);
			
			
			//1. 기존에도 포토리뷰 없고 update 데이터에도 포토리뷰 없는 경우 
			List<ReviewPhotoMapEntity> listPhoto = new ArrayList<>();
			ReviewEntity reviewSaveEntity = ReviewEntity.builder()
											.reviewId(review.getReviewId())
											.attachedPhotoIds(listPhoto)
											.build();
			
			when(reviewRepositroy.findByReviewIdAndAttachedPhotoIdsDelYn(review.getReviewId(), "N")).thenReturn(reviewSaveEntity);
			
			Integer addMileage = reviewService.updateReview(review);
			assertThat(addMileage == 0);
			
			
			//2. 기존에 포토리뷰 없고 update 데이터에는 포토리뷰 있는 경우 
			listPhotos.add("test-photo-1");
			review.setAttachedPhotoIds(listPhotos); 
			addMileage = reviewService.updateReview(review);
			assertThat(addMileage == 1);
			
			
			//3. 기존에 포토리뷰 있고 update 데이터에는 포토리뷰 없는 경우 
			ReviewPhotoMapEntity photoEntity = ReviewPhotoMapEntity.builder()
					.reviewId(review.getReviewId())
					.attachedPhotoId("test-photo-1")
					.delYn("N")
					.build();
			listPhoto.add(photoEntity);
			reviewSaveEntity = ReviewEntity.builder()
											.reviewId(review.getReviewId())
											.attachedPhotoIds(listPhoto)
											.build();
			when(reviewRepositroy.findByReviewIdAndAttachedPhotoIdsDelYn(review.getReviewId(), "N")).thenReturn(reviewSaveEntity);
			
			review.setAttachedPhotoIds(new ArrayList<>()); 
			addMileage = reviewService.updateReview(review);
			assertThat(addMileage == -1);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			fail("Failed");
		}
	}
	
	
	@Test
	void testDeleteReview() {
		try 
		{
			//해당 리뷰로 3점받고 1점 소멸된 경우의 삭제
			ReviewDTO review = new ReviewDTO();
			review.setAction("ADD");
			review.setType("REVIEW");
			review.setContent("content");
			review.setPlaceId("test-place-1");
			review.setUserId("test-user-1"); 
			review.setReviewId("test-review-1"); 
			
			MileageHistoryEntity mileageEntity = MileageHistoryEntity.builder()
												.userId(review.getUserId())
												.historyType("-")
												.mileageAmount(1)
												.reviewId(review.getReviewId())
												.build();
			MileageHistoryEntity mileageEntity2 = MileageHistoryEntity.builder()
												.userId(review.getUserId())
												.historyType("+")
												.mileageAmount(3)
												.reviewId(review.getReviewId())
												.build();
			List<MileageHistoryEntity> listMileage = new ArrayList<>();
			listMileage.add(mileageEntity2);
			listMileage.add(mileageEntity);
			
			when(mileageHistoryRepository.findByReviewIdAndUserId(review.getReviewId(), review.getUserId())).thenReturn(listMileage);
			
			
			ReviewEntity reviewEntity = ReviewEntity.builder()
											.reviewId(review.getReviewId())
											.placeId(review.getPlaceId())
											.userId(review.getUserId())
											.content(review.getContent())
											.updtDate(LocalDateTime.now()) 
											.build();
			when(reviewRepositroy.findByReviewIdAndAttachedPhotoIdsDelYn(review.getReviewId(), "N")).thenReturn(reviewEntity);
			
			Integer addMileage = reviewService.deleteReview(review);
			assertThat(addMileage == -2);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			fail("Failed");
		}
	}


}
