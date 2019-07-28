# shortening_url

# 1. 소개
### 1.1 개요
  입력받은 URL을 단축URL(8글자이하) 로 변경 해주고, 
  단축URL을 입력했을 경우 해당하는 원래 URL로 리다이렉트 한다.
### 1.2 사용기술 
  1. 프레임워크 : SpringBoot(ver.2.1.6), Thymeleaf, JPA
  2. 언어 : Java(1.8), Javascript
  3. DB : H2

# 2.문제해결 전략
### 2.1 원본URL과 단축URL 매핑
    1. DB의 Serial한 sequence(숫자형) 값을 기준으로 매핑을 한다.
    2. 이때 sequence 값을 그대로 원본URL로 사용 했을 경우, 1 ~ 99999999(단축URL은 8글자이하) 의 범위를 가지게 된다.
    3. 하지만 62진수(0~9, a~z, A~Z)로 표현했을 경우 (62^8 - 1) = 218,340,105,584,895 개의 단축URL을 가질수 있다.
### 2.2 원본URL -> 단축URL
    1. DB에 원본URL을 insert를 하면서 생긴 sequence 를 62진수로 변환하고, 변환된 62진수를 단축URL로 사용한다.
    2. 이때, 이미 DB에 존재하는 원본URL일 경우 이미 저장된 sequence 를 62진수로 변환하여 단축URL로 사용한다.
### 2.3 단축URL -> 원본URL
    1. 단축URL의 판단은 설정된 PrefixUrl(기본 : "http://localhost:8080/") 중 하나 일 경우로 한다. 
    2. 단축URL일 경우 해당하는 62진수 값을 10진수로 변경한뒤, DB에 저장된 원본URL을 가져온뒤 리다이렉트 한다.

# 3. 실행방법
### 3.1 사전준비
    1. jdk1.8 이상
    2. git 설치
    3. maven 설치
### 3.2 빌드 
    1. maven 빌드로 실행파일(jar) 생성 > mvn clean package
    2. maven 빌드로 생성된 파일 위치 > target/shortening_url-0.0.1-SNAPSHOT.jar
### 3.3 개발환경
    1. github 의 소스를 로컬개발환경에 clone
    2. IDE 에서 ShorteningUrlApplication.java > run or debug
    3. localhost:8080 으로 접속
### 3.4 운영환경
    1. 빌드결과 나온 jar 파일을 배포할 경로로 이동
    2. nohup {설치된 JAVA} -jar {배포된 shortening_url-0.0.1-SNAPSHOT.jar} &
    3. 배포된 운영환경의 8080 포트로 접속
