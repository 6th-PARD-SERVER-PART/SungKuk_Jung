# Product Management API

Spring Boot 기반의 상품 관리 REST API 서버입니다.

## 프로젝트 개요

상품의 등록, 조회, 수정 기능을 제공하는 RESTful API입니다. 상품의 색상별 검색 및 수량 관리 기능을 지원합니다.

## 주요 기능

### 1. 상품 등록
새로운 상품을 데이터베이스에 등록합니다.

### 2. 전체 상품 조회
등록된 모든 상품을 최신순으로 조회합니다.

### 3. 상품 상세 조회
ID를 통해 특정 상품의 상세 정보를 조회합니다.

### 4. 색상별 상품 조회
특정 색상의 모든 상품과 수량을 조회합니다.

### 5. 색상별 수량 수정
특정 색상의 모든 상품 수량을 일괄 수정합니다.


##  API 명세서

### 1. 홈 화면 (상품 전체 조회)

**Endpoint**
```
GET /product/home
```

**설명**  
등록된 모든 상품 목록을 ID 내림차순으로 조회합니다.

**Request URL**
```
http://192.168.0.123:8080/product/home
```

**Response (200 OK)**
```json
[
  {
    "id": 1,
    "productName": "셔츠",
    "color": "빨간",
    "price": 10000,
    "quantity": 50,
    "available": true
  }
]
```

**Response 필드**

| Key | 타입 | 설명 |
|-----|------|------|
| id | Long | 상품 고유 ID |
| productName | String | 상품 종류 |
| color | String | 색상 |
| price | Integer | 가격 |
| quantity | Integer | 수량 |
| available | Boolean | 판매 가능 여부 |

---

### 2. 상품 등록

**Endpoint**
```
POST /product
```

**설명**  
새로운 상품을 등록합니다.

**Request URL**
```
http://192.168.0.123:8080/product
```

**Request Body**
```json
{
  "productName": "셔츠",
  "color": "파란",
  "price": 15000,
  "quantity": 30
}
```

**Request 필드**

| Key | 타입 | 설명 |
|-----|------|------|
| productName | String | 상품 종류 |
| color | String | 색상 |
| price | Integer | 가격 |
| quantity | Integer | 수량 |

**Response (200 OK)**
```json
1
```

**Response 필드**

| Key | 타입 | 설명 |
|-----|------|------|
| id | Long | 등록된 상품의 ID (예: 1, 2, 3) |

---

### 3. 상품 수량 수정

**Endpoint**
```
PATCH /product/{color}
```

**설명**  
특정 색상의 모든 상품 수량을 일괄 수정합니다.

**Request URL**
```
http://192.168.0.123:8080/product/빨간
```

**Path Parameter**

| Key | 타입 | 설명 |
|-----|------|------|
| color | String | 수정할 상품의 색상 |

**Request Body**
```json
{
  "quantity": 10
}
```

**Request 필드**

| Key | 타입 | 설명 |
|-----|------|------|
| quantity | Integer | 변경할 수량 |

**Response (200 OK)**
```json
[1, 2, 3]
```

**Response 필드**

| Key | 타입 | 설명 |
|-----|------|------|
| id | Long | 수정된 상품들의 ID 목록 |

---

### 4. 상품 색상별 수량 조회

**Endpoint**
```
GET /product/color/{color}
```

**설명**  
특정 색상의 모든 상품과 수량을 조회합니다.

**Request URL**
```
http://192.168.0.123:8080/product/color/초록
```

**Path Parameter**

| Key | 타입 | 설명 |
|-----|------|------|
| color | String | 조회할 색상 |

**Response (200 OK)**
```json
[
  {
    "color": "초록",
    "quantity": 10
  },
  {
    "color": "초록",
    "quantity": 5
  }
]
```

**Response 필드**

| Key | 타입 | 설명 |
|-----|------|------|
| color | String | 색상 |
| quantity | Integer | 수량 |

**Error Response (404 NOT FOUND)**
```json
"검정 존재하지 않습니다."
```

---

### 5. 상품 상세 페이지

**Endpoint**
```
GET /product/{id}
```

**설명**  
특정 ID의 상품 상세 정보를 조회합니다.

**Request URL**
```
http://192.168.0.123:8080/product/6
```

**Path Parameter**

| Key | 타입 | 설명 |
|-----|------|------|
| id | Long | 조회할 상품의 ID |

**Response (200 OK)**
```json
{
  "productName": "셔츠",
  "color": "빨간",
  "price": 10000,
  "quantity": 10
}
```

**Response 필드**

| Key | 타입 | 설명 |
|-----|------|------|
| productName | String | 상품 종류 |
| color | String | 색상 |
| price | Integer | 가격 |
| quantity | Integer | 수량 |

**Error Response (404 NOT FOUND)**
```json
"6 ID를 가진 상품이 존재하지 않습니다."
```

## 데이터 모델

### Product Entity
```java
{
  "id": Long,              // 상품 ID (자동 생성)
  "productName": String,   // 상품명 (필수)
  "color": String,         // 색상 (필수)
  "price": Integer,        // 가격 (필수)
  "quantity": Integer,     // 수량 (필수)
  "available": Boolean     // 재고 가능 여부 (자동 설정)
}
```

> **available**: 수량이 0보다 크면 `true`, 그렇지 않으면 `false`로 자동 설정됩니다.

## 설정

### CORS 설정
- 허용 Origin: `http://localhost:3000/`
- 허용 Header: 전체 (`*`)
- 허용 Method: 전체 (`*`)
- Credentials: 허용

### Swagger UI
프로젝트 실행 후 다음 URL에서 API 문서를 확인할 수 있습니다:
```
http://localhost:8080/swagger-ui.html
```

## 실행 방법

### 1. 프로젝트 클론
```bash
git clone [repository-url]
cd seminar6
```

### 2. 애플리케이션 실행
```bash
./gradlew bootRun
```

### 3. API 테스트
서버가 실행되면 `http://localhost:8080`에서 API를 사용할 수 있습니다.

## 📝 에러 처리

### 404 Not Found
- 존재하지 않는 상품 ID 조회 시: `"{id} ID를 가진 상품이 존재하지 않습니다."`
- 존재하지 않는 색상 조회 시: `"{color} 존재하지 않습니다."`
- 존재하지 않는 색상 수정 시: `"검색한 색상이 존재하지 않습니다: {color}"`

## 주요 특징

- **Builder 패턴**: 객체 생성의 가독성 향상
- **DTO 분리**: Request/Response 객체 명확히 구분
- **트랜잭션 관리**: `@Transactional`을 통한 데이터 일관성 보장
- **예외 처리**: 명확한 에러 메시지 제공
- **자동 재고 관리**: 수량 변경 시 `available` 필드 자동 업데이트
