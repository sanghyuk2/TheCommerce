# TheCommerce

| 🛠Tech Stack                                         |                                                                                                                                                                                                                                                                                                                                                                                                            |
| --------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 🖥 **Backend**                                       | ![Java](https://img.shields.io/badge/-Java-007396?logo=Java&logoColor=white) ![SpringBoot](https://img.shields.io/badge/-Springboot-6DB33F?logo=Springboot&logoColor=white)                                                                                                                                                               |
| 📝 **Logging** | ![Lombok](https://img.shields.io/badge/-Lombok-231F20)                                                                                                                                                               |
| 💾 **Database**                                   | ![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?&logo=mysql&logoColor=white)                                                                                                                                                                                                                |
| 🧪 **Test** | ![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?&logo=swagger&logoColor=white) ![Postman](https://img.shields.io/badge/Postman-FF6C37?logo=postman&logoColor=white)                                                                                                                                                                 |
| 🕓 **Version Control** | ![Git](https://img.shields.io/badge/git-%23F05033.svg?logo=git&logoColor=white) ![GitHub](https://img.shields.io/badge/github-%23121011.svg?logo=github&logoColor=white)                                                                                                                                                                 |


# 🗂 Project

이 프로젝트는 더커머스가 주관하는 프로젝트로 시작되었습니다. **JDK 1.8** 버전과 **Java 8**을 사용하여 기능을 구현했습니다.
<br>  
회원 가입, 회원 전체 조회, 회원 수정 기능 구현을 했습니다.
<br>  

<p style="color:orange">자세한 서비스 로직은 각 기능별 PR에서 확인하실 수 있습니다.</p>

<details>
<summary>기능 PR 링크 접기/펼치기</summary>

- [회원가입](https://github.com/sanghyuk2/TheCommerce/pull/5)
- [회원 목록 조회](https://github.com/sanghyuk2/TheCommerce/pull/6)
- [회원 수정](https://github.com/sanghyuk2/TheCommerce/pull/7)
- [Swagger 연동](https://github.com/sanghyuk2/TheCommerce/pull/9)
- [유효성 검증](https://github.com/sanghyuk2/TheCommerce/pull/11)

</details>

## 🧩 사용법

| **참고 : 본 프로젝트는 Postman으로 진행했습니다.**
  
#### git clone

```
git clone https://github.com/sanghyuk2/TheCommerce.git

yml 파일 password 및 username 변경

실행
```

## 기능 설명

#### 회원가입

`UserController`의 `join` 메서드에 아래 형태의 userReqDto 를 집어넣습니다. 
```
{
    "userid": userid,
    "password": "password",
    "nickname": "user1",
    "username": "User One",
    "phonenumber": "3456789012",
    "email": "user1@example.com"
}
```
`@Valid`와 `@Pattern`을 이용한 유효성 검증 과정을 거친 후 적절한 형태의 userReqDto가 입력됏을 시, `HttpStatus 201`과 `로그인 성공 메시지`가 반환됩니다.

#### 회원 목록 조회

`PageRequest.of` 메서드를 사용하여 구현했습니다.
```
http://localhost:8080/api/user/list?page=1&pageSize=2&sort=joinDate
```
3명의 유저가 Repository에 저장되어있다고 가정할 때, 위 형태의 url을 `Get` 방식으로 전송하게 되면, `페이지 번호 1`과, `두 명의 유저 정보`가 반환됩니다.

#### 회원 수정

`JPA 메서드`를 사용하여 구현했습니다.
```
http://localhost:8080/api/user/3

{
    "userid": userid,
    "password": "passwordChanges",
    "nickname": "user1",
    "username": "User One",
    "phonenumber": "3456789012",
    "email": "user1@example.com"
}
```
위 형태의 url과 JSON을 PUT 방식으로 전송 시, 회원 가입했을 때의 password의 정보가 변경되었기에 `password가 변경됐습니다` 가 반환됩니다.

## 추가 기능
sql을 따로 관리하여 DDL을 sql이 관리하게끔 설정하였습니다. 자세한 사항은 아래 링크를 참조 부탁드립니다.

- [SQL](https://github.com/sanghyuk2/TheCommerce/tree/dev/src/main/resources/local/sql)

Open API 는 Swagger 를 사용하여 관리했습니다. 프로젝트 실행 후 아래 링크를 참조 부탁드립니다.

- [Swagger UI](http://localhost:8080/swagger-ui.html)
- [Swagger API](http://localhost:8080/v2/api-docs)

## 🪣 Database

### User

|컬럼명|CONSTRAINTS|TYPE|설명|
|------|-----|-----|-----|
|id|PK|BIGINT|유저 식별자|
|userid|UNIQUE|VARCHAR(100)|유저 아이디|
|password| |VARCHAR(100)|비밀번호|
|nickname|UNIQUE|VARCHAR(100)|닉네임|
|username|UNIQUE|VARCHAR(100)|유저 이름|
|phonenumber| |VARCHAR(15)|폰 번호|
|email| |VARCHAR(50)|이메일|
|join_date| |DATETIME|회원가입 날짜|
|updated_date| |DATETIME|변경 날짜|


