# UserInfoController

간단한 Spring Boot REST API
`Map<String, String>`을 사용해 사용자 정보를 저장하고,  
RESTful 방식으로 CRUD(Create, Read, Update, Delete)를 구현했습니다.  

선차적 순서는 상광이 없고 각 사용자 마다 아이디(key)가 중복되지 않아야만 하는 반면 사용마다 이름을 같을 수 있으니 Key는 중복 불가능 Value는 중복가능한 HashMap을 사용했습니다.

## 요약

| Method  | URL                 | 설명                 | Parameters                                            |
|---------|---------------------|---------------------|-------------------------------------------------------|
| GET     | `/users`            | 전체 사용자 조회        | 없음                                                   |
| POST    | `/users`            | 새로운 사용자 추가       | RequestParam: `userId`, `userLegalName`               |
| GET     | `/users/{userId}`   | 특정 사용자 조회        | PathVariable: `userId`                                |
| PUT     | `/users/{userId}`   | 사용자 정보 수정        | PathVariable: `userId`, RequestParam: `userLegalName` |
| DELETE  | `/users/{userId}`   | 사용자 삭제            | PathVariable: `userId`                                |


## 설명

기본 URL: `/users`

### 1. 전체 사용자 조회
- **GET** `/users`
- **결과 예시**
```json
{}
```
- ***처음에는 등록된 사용자 정보가 없을겁니다.***
  ***POST를 통해 새로운 사용자 추가후 아래와 같이 사용자 정보들이 나옵니다.***
- **예시**
```json
{
  "testId1": "Tester1",
  "testId2": "Tester2"
}
```

### 2. 새로운 사용자 추가 
- **POST** `/users?userId=testId1&userLegalName=Tester1`
- **결과 예시**
```json
{
  "testId1": "Tester1"
}
```

### 3. 특정 사용자 조회 
- **GET** `/users/testId1`
- **결과 예시**
```json
{
  "testId1": "Tester1"
}
```

### 4. 사용자 정보 수정
- **PUT** `/users/testId1?userLegalName=Tester2`
- **결과 예시**
```json
{
  "testId1": "Tester2"
}
```
- ***userId를 통해 특정 사용자 정보를 가져오고 사용자의 아름(userLegalName)을 바꿀 수 있습니다***

### 5. 사용자 삭제 
- **DELETE** `/users/testId1`
- **결과 예시**
```json
{}
```
- ***사용자 정보가 여러개 있을시, GET을 통해 전체 사용자 정보를 검색할시 삭제된 사용자 정보가 없는 것을 확인할 수 있습니다.***
- **예시**
```json
{
  "testId2": "Tester2"
}
```


## 친구 Controller 접속하기

<img width="5092" height="3180" alt="이상현님 테스트" src="https://github.com/user-attachments/assets/83e0544f-2e3f-49cb-9471-db8dce658c79" />
