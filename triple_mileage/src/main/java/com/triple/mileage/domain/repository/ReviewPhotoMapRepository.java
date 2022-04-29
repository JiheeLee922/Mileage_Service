package com.triple.mileage.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.triple.mileage.domain.entity.ReviewPhotoMapEntity;
import com.triple.mileage.domain.entity.id.ReviewPhotoMapID;

@Repository
public interface ReviewPhotoMapRepository extends JpaRepository<ReviewPhotoMapEntity, ReviewPhotoMapID> {

}
