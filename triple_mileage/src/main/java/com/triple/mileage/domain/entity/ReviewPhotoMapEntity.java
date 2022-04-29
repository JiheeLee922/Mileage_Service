package com.triple.mileage.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.triple.mileage.domain.entity.id.ReviewPhotoMapID;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "REVIEW_PHOTO_MAP")
@NoArgsConstructor
@IdClass(ReviewPhotoMapID.class)
public class ReviewPhotoMapEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
//	@ManyToOne
//	@JoinColumn(name = "REVIEW_ID")
//	private ReviewEntity review;
	private String reviewId;
	
	@Id
	private String attachedPhotoId;
	
	@Column
	private String delYn;
	
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime regDate;
	
	private LocalDateTime delDate;

	@Builder
	public ReviewPhotoMapEntity(String reviewId, String attachedPhotoId, String delYn) {
		this.reviewId = reviewId;
		this.attachedPhotoId = attachedPhotoId;
		this.delYn = delYn;
	}
	
	
}
