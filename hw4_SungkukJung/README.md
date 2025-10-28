## hw4_SungkukJung

사용자, 게시글, 댓글, 좋아요, 파일 업로드, Swagger 문서를 포함한 Spring Boot 기반 간단한 소셜/블로그 REST API입니다.

### 데이터베이스 설정
1) 데이터베이스 생성:
```sql
CREATE DATABASE IF NOT EXISTS hw4 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```
2) `src/main/resources/application.yaml`에서 자격 증명 수정:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/hw4?serverTimezone=UTC&characterEncoding=UTF-8
    username: root
    password: YOUR_PASSWORD
  jpa:
    hibernate:
      ddl-auto: update
```
3) 테스트는 `src/test/resources/application.yaml`의 로컬 MySQL 설정을 사용합니다.

### 빌드 및 실행
```bash
./gradlew clean build
./gradlew bootRun
```
애플리케이션: `http://localhost:8080`

### API 문서
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`

### 파일 업로드
- 업로드 디렉터리: `file.upload-dir` (기본값 `uploads/posts`)
- 정적 제공: 해당 디렉터리의 파일은 `/uploads/{filename}` 경로로 접근
- `FileUploadConfig`로 리소스 핸들러 구성

### 주요 엔드포인트 (일부)
- `POST /user/sign-up` – 회원가입
- `POST /user/log-in` – 로그인 (데모: 평문 비밀번호)
- `GET /user/{username}` – 사용자 프로필 카드
- `GET /user/{username}/activity` – 활동 집계
- `GET /user` – 사용자 목록
- `POST /post` (multipart) – 파일 선택 포함 게시글 생성
- `GET /post/{id}` – 게시글 조회(조회수 증가)
- `GET /post/user/{username}` – 특정 사용자 게시글 목록
- `GET /post/all` – 전체 게시글 목록
- `PATCH /post/{id}` (multipart) – 부분 수정 + 새 파일 업로드 선택
- `DELETE /post/{id}` – 게시글 삭제
- `POST /comment` – 댓글 작성
- `GET /comment/post/{postId}` – 게시글 댓글 목록
- `PATCH /comment/{id}` – 댓글 수정
- `DELETE /comment/{id}` – 댓글 삭제
- `POST /like/toggle` – 좋아요/좋아요 취소 토글
- `GET /like/post/{postId}` – 좋아요한 사용자 목록

### 테스트 실행
```bash
./gradlew clean test
```

### 프로젝트 구조
```
src/main/java/com/pard/server/hw4_sungkukjung/
  user/      # 사용자 엔터티, 서비스, 컨트롤러, 리포지토리, DTO
  post/      # 게시글 엔터티, 서비스(파일 저장), 컨트롤러, 리포지토리, DTO
  comment/   # 댓글 엔터티, 서비스, 컨트롤러, 리포지토리, DTO
  like/      # 좋아요 엔터티, 서비스, 컨트롤러, 리포지토리, DTO
  config/    # Swagger/OpenAPI 및 파일 제공 설정
```

### 참고 사항
- 과제 단순화를 위해 비밀번호는 평문으로 저장되어 있습니다. 실제 서비스에서는 반드시 해싱(예: BCrypt)을 사용하세요.
- 엔터티는 필요한 곳에 LAZY 로딩과 cascade를 사용하며, DTO는 필요한 필드만 노출합니다.