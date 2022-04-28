package com.triple.mileage.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "MILEAGE_HISTORY")
@NoArgsConstructor
public class MileageHistoryEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mileageHistorySeq;
	
	@Column
	private String userId;
	@Column
	private String historyType;
	@Column
	private Integer mileageAmount;
	@Column
	private String reviewId;
	
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime regDate;
	
	@Builder
	public MileageHistoryEntity(String userId, String historyType, Integer mileageAmount, String reviewId) {
		this.userId = userId;
		this.historyType = historyType;
		this.mileageAmount = mileageAmount;
		this.reviewId = reviewId;
	}
	
	
}
