# Mileage_Service
리뷰 관리와 리뷰에 따른 마일리지 적립&소멸 , 마일리지 조회 서비스 입니다.

### 기술스택
+ JAVA 16.0.2
+ SpringBoot 2.6.7
+ JPA Hibernate 5.6.8
+ gradle
+ MariaDB 10.5.13

### 실행방법
  $ git clone https://github.com/JiheeLee922/Mileage_Service.git
  $ cd Mileage_Service/triple_mileage
  $ ./gradlew clean build
  $ java -jar build/libs/triple_mileage-0.0.1-SNAPSHOT.jar
  
접속 URL  : http://localhost:8080

### API
#### 1. http://localhost:8080/events (POST)
+ 리뷰를 저장,삭제하고 리뷰에 따른 마일리지 적립,소멸한다.
+ 전송데이터 예시
'''
{
    "type" : "REVIEW",
    "action" : "ADD",
    "reviewId" : "20220501-33f32g-3gefg-ef",
    "attachedPhotoIds" : ["image3-idg-dbw","image1-idg-dgd"],
    "content" : "다시 가고싶어요.",
    "userId": "leejh-3g3g3-egwe",
    "placeId" : "gj30t2-gjrgj3-jhjho"
}
'''

### 2. http://localhost:8080/mileage/{userId} (GET)
+ 사용자의 마일리지 조회한다.
