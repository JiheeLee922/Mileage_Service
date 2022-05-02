# Mileage_Service
리뷰 관리와 리뷰에 따른 마일리지 적립&소멸 , 마일리지 조회 서비스 입니다.

### 기술스택
+ JAVA 16.0.2
+ SpringBoot 2.6.7
+ JPA Hibernate 5.6.8
+ gradle
+ MariaDB 10.5.13
+ JUnit5

### 실행방법
```
  $ git clone https://github.com/JiheeLee922/Mileage_Service.git
  $ cd Mileage_Service/triple_mileage
  $ ./gradlew clean build
  $ java -jar build/libs/triple_mileage-0.0.1-SNAPSHOT.jar
``` 
접속 URL  : http://localhost:8080

### API
#### 1. http://localhost:8080/events (POST)
+ 리뷰를 저장,삭제하고 리뷰에 따른 마일리지 적립,소멸한다.
+ Result : 해당 이벤트로 인해 적립(소멸) 된 마일리지 점수
+ Request 데이터 예시
```
{
    "type" : "REVIEW",
    "action" : "ADD",
    "reviewId" : "20220501-33f32g-3gefg-ef",
    "attachedPhotoIds" : ["image3-idg-dbw","image1-idg-dgd"],
    "content" : "다시 가고싶어요.",
    "userId": "leejh-3g3g3-egwe",
    "placeId" : "gj30t2-gjrgj3-jhjho"
}
```
+ 테스트 케이스
  + 리뷰 생성
    1) action이 ADD인 경우 리뷰와 리뷰 이미지 데이터 insert.
    2) 내용(content)가 1자이상이면 마일리지 1점 추가
    3) 첨부파일(attachedPhotoIds)이 1개 이상이면 마일리지 1점 추가
    4) 해당 장소의 삭제되지않은 첫번째 리뷰라면 마일리지 1점 추가
  + 리뷰 수정
    1) 변경된 리뷰와 리뷰 이미지 데이터 update & insert
    2) 첨부파일이 없는 리뷰에 첨부파일이 1개 이상 추가된다면 마일리지 1점 추가
    3) 첨부파일이 있는 리뷰에 첨부파일을 모두 삭제한다면 마일리지 1점 소멸
  + 리뷰 삭제
    1) 해당 리뷰와 리뷰 이미지 데이터 제거 (del_yn = "Y" 처리)
    2) 해당 리뷰로 인해 적립된 마일리지 소멸처리


#### 2. http://localhost:8080/mileage/{userId} (GET)
+ 사용자의 마일리지 조회한다.
+ Result : 해당 사용자의 전체 마일리지의 합
+ 테스트 케이스
  1) 해당 사용자의 전체 마일리지의 합이 조회되는지 확인
