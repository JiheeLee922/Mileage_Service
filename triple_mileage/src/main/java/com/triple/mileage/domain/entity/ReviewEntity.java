package com.triple.mileage.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "REVIEW")
@NoArgsConstructor
@DynamicUpdate
public class ReviewEntity {

	@Id
	private String reviewId;
	
	@Column(nullable = false, length = 100)
	private String placeId;
	
	@Column(nullable = false, length = 100)
	private String userId;
	
	@Column(nullable = true, length = 500)
	private String content;
	
	@Column
	private String delYn;
	
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime regDate;
	
	@LastModifiedDate
	private LocalDateTime updtDate;
	
	private LocalDateTime delDate;

	@Builder
	public ReviewEntity(String reviewId, String placeId, String userId, String content, String delYn) {
		this.reviewId = reviewId;
		this.placeId = placeId;
		this.userId = userId;
		this.content = content;
		this.delYn = delYn;
	}
	
	
}
