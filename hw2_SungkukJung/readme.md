# 날씨 CRUD (OpenWeather API 사용)

OpenWeather API를 사용해 실시간 날씨 정보를 가져와 서버에 저장하고 가져온 정보를 조회, 수정, 삭제할 수 있는 CRUD

---

## Entity 
- 필드: `id, country, city, temperature, humidity, description`
- `updateAll()` 메서드로 데이터 전체 업데이트 가능

---

## DTO (Data Transfer Object)
- 클라이언트-서버 데이터 전송용
- 목적별로 분리:
  - `WeatherDto`: 전체 정보
  - `WeatherTempDto`: 온도/습도
  - `WeatherDescriptionDto`: 날씨 설명
- Entity ↔ DTO 변환 메서드 포함 (`toDto()`)

---

## Controller

기본 URL: `/weather`

| Method  | URL                 | 설명                 | Parameters                                            |
|---------|---------------------|---------------------|-------------------------------------------------------|
| POST    | `/weather/{country}/{city}`            | OpenWeather API에서 날씨 정보 불러오고 저장       | PathVariable: `country`, `city`               |
| GET     | `/weather`   | 전체 날씨 정보 조회        | 없음                             |
| GET     | `/weather/{id}`   | 특정 날씨 정보 조회        | PathVariable: `id`                                |
| GET     | `/weather/{id}/temp`   | 특정 날씨 정보의 **온도, 습도** 정보만 반환      | PathVariable: `id`                                |
| GET     | `/weather/{id}/description`   | 특정 날씨 정보의 **설명** 정보만 반환      | PathVariable: `id`                                |
| PATCH   | `/weather/{id}`   | 날씨 정보 수정        | PathVariable: `id`, RequestBody: `WeatherDto` |
| DELETE  | `/weather/{id}`   | 날씨 정보 삭제            | PathVariable: `id`                                |

---

## 사용 예시

### 1. 날씨 정보 저장
```bash
POST http://localhost:8080/weather/KR/Pohang
```
응답:
```
Weather Data Saved
```

### 2. 전체 날씨 정보 조회
```bash
GET http://localhost:8080/weather
```
응답:
```json
[
  {
    "id": 1759252212515,
    "country": "KR",
    "city": "Pohang",
    "temperature": 18.13,
    "humidity": 88,
    "description": "scattered clouds"
  }
]
```

### 3. 특정 날씨 정보 조회
```bash
GET http://localhost:8080/weather/1759252212515
```
응답:
```json
{
  "id": 1759252212515,
  "country": "KR",
  "city": "Pohang",
  "temperature": 18.13,
  "humidity": 88,
  "description": "scattered clouds"
}
```

### 4. 특정 날씨 정보 온도/습도 조회
```bash
GET http://localhost:8080/weather/1759252212515/temp
```
응답:
```json
{
  "country": "KR",
  "city": "Pohang",
  "temperature": 18.13,
  "humidity": 88
}
```

### 5. 특정 날씨 정보 설명 조회
```bash
GET http://localhost:8080/weather/1759252212515/description
```
응답:
```json
{
  "country": "KR",
  "city": "Pohang",
  "description": "scattered clouds"
}
```

### 6. 날씨 정보 수정
```bash
PATCH http://localhost:8080/weather/1759252212515
```
Body 예시:
```json
{
  "id": 1759252212515,
  "country": "KR",
  "city": "Pohang",
  "temperature": 20.5,
  "humidity": 80,
  "description": "clear sky"
}
```
응답:
```
Weather Data Updated
```

### 7. 날씨 정보 삭제
```bash
DELETE http://localhost:8080/weather/1759252212515
```
응답:
```
Data Deleted
```

---

## OpenWeatherClient 설명

`OpenWeatherClient`는 외부 OpenWeather API를 호출하는 컴포넌트

### WebClient 사용
- Spring5의 비동기/반응형 HTTP 클라이언트
- HTTP 요청을 보내고 응답을 받을 수 있음
- REST API 호출 시 주로 사용
- **webClient.get(), .uri()까지는 요청 구성 단계**, 실제 HTTP 호출은 `.retrieve()` 또는 `.exchangeToMono()` 단계에서 발생

#### 예시:
```java
Mono<Map> response = webClient.get()
        .uri(uriBuilder -> uriBuilder
                .queryParam("q", city + "," + country)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .build())
        .retrieve()
        .bodyToMono(Map.class);

Map<String, Object> result = response.block();
```

#### 단계별 설명
1. `webClient.get()` → HTTP GET 요청 객체 생성, 아직 서버 호출 X
2. `.uri(...)` → URL 완성 및 쿼리 파라미터 설정
3. `.retrieve()` → HTTP 요청 실행
4. `.bodyToMono(Map.class)` → JSON 응답을 Map으로 변환
5. `.block()` → Mono 안 데이터를 동기적으로 꺼내 Map 저장

#### Mono vs Flux
| 개념 | 설명 | 사용 예시 |
|------|------|------------|
| Mono | 비동기 단일 값 | API 한 번 호출 → JSON 하나 반환 |
| Flux | 비동기 여러 값 스트림 | API 반복 호출 → JSON 배열, 이벤트 스트림 |
| block() | Mono/Flux 동기화 | 즉시 결과 필요 시 |

---

### API 호출 시 필요한 파라미터

| 파라미터 | 설명 |
|-----------|------|
| `q`       | 도시 이름과 국가 코드, 예: `Seoul,KR` |
| `appid`   | OpenWeather API Key (application.properties에서 관리) |
| `units`   | 온도 단위 "metric" → Celsius |

---

### OpenWeather API 응답 예시
```json
{
  "coord": {"lon":126.9778,"lat":37.5683},
  "weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01n"}],
  "main":{"temp":18.93,"humidity":86},
  "wind":{"speed":0.62},
  "clouds":{"all":1},
  "sys":{"country":"KR"},
  "name":"Seoul",
  "cod":200
}
```
- 주요 데이터 추출:
  - temperature → `main.temp`
  - humidity → `main.humidity`
  - description → `weather[0].description`

---

## fetchWeather() 메서드 설명

`WeatherService.fetchWeather(String country, String city)` 동작:

1. `OpenWeatherClient` 호출 → 날씨 데이터 Map 반환
2. JSON Map에서 온도, 습도, 날씨 설명 추출 (main.temp, weather[0].description)
3. `WeatherDto` 생성, 고유 ID는 `System.currentTimeMillis()` 사용
   - ID 충돌 가능성 있음 (동시 다수 요청 시)
4. `saveWeather()` 호출 → In-memory Repository 저장

### ID 생성
- System.currentTimeMillis() 사용 → 서버 기준 현재 시간(ms) → 유니크 ID
- 동시에 다수 요청 시 충돌 가능 

### In-memory Repository
- HashMap을 사용하여 서버 메모리에 데이터 저장
- 서버 재시작 시 데이터 소멸
- 간단 테스트 및 개발용

### WebClient
- 비동기 HTTP 클라이언트
- Mono: 단일 비동기 응답, Flux: 다중 비동기 응답
- block() 사용 시 동기 처리
- 실제 HTTP 호출은 retrieve() 또는 exchangeToMono() 단계에서 발생

