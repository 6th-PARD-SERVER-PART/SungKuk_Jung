# HW5: Spring Boot REST API 소셜 로그인 통합

## 프로젝트 개요

HW5는 Spring Security를 활용한 Google OAuth 2.0 소셜 로그인 기능이 통합된 Spring Boot REST API 프로젝트입니다.

## 🚀 주요 변경 사항 (HW4 대비)

### 1. Spring Security 및 OAuth 의존성 추가

Google 소셜 로그인 기능을 활성화하기 위해 다음 의존성을 추가해야 합니다.

#### Gradle (build.gradle)
```gradle
implementation 'org.springframework.boot:spring-boot-starter-security'
implementation 'org.springframework.boot:spring-boot-starter-oauth2-client'
```

---

### 2. 사용자 엔티티 (User.java) 확장

소셜 로그인 정보를 저장하고 관리하기 위해 `User` 엔티티에 다음 필드가 추가되었습니다.
```java
@Entity
public class User {
    // 기존 필드들...
    
    @Column(name = "social_id")
    private String socialID;  // Google에서 제공하는 고유 ID(sub)
    
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;  // 사용자 권한 (기본값: USER)
}
```

#### 필드 설명
- **socialID**: Google에서 제공하는 고유 식별자(`sub`)를 저장
- **role**: 사용자의 권한 수준을 정의 (ENUM 타입, 기본값: `Role.USER`)

---

### 3. 사용자 정의 OAuth 서비스 (PrincipalOauth2UserService.java)

이 서비스는 소셜 로그인 요청을 처리하는 핵심 로직을 담당합니다.

#### 주요 기능

**역할**
- `DefaultOAuth2UserService`를 구현하여 Google에서 사용자 정보를 로드

**자동 회원가입**
- `email`로 기존 사용자 확인
- 신규 사용자일 경우 자동으로 `User` 엔티티를 생성하고 저장

**데이터 무결성 처리** ⚠️ 중요
User 엔티티의 필수 필드(`username`, `password`)에 값이 없으면 데이터베이스 삽입이 실패하므로, 임시 값을 생성하여 채웁니다:

- **username**: `google_[socialId]` 형식의 고유한 값으로 설정
- **password**: 안전한 무작위 UUID 문자열로 설정 (실제 환경에서는 BCrypt 인코딩 권장)

**트랜잭션 보장**
- `loadUser` 메서드에 `@Transactional` 어노테이션 추가로 데이터베이스 저장 작업의 안정성 확보
```java
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        
        String email = oAuth2User.getAttribute("email");
        String socialId = oAuth2User.getAttribute("sub");
        
        User user = userRepository.findByEmail(email)
            .orElseGet(() -> {
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setUsername("google_" + socialId);
                newUser.setPassword(UUID.randomUUID().toString());
                newUser.setSocialID(socialId);
                newUser.setRole(Role.USER);
                return userRepository.save(newUser);
            });
        
        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }
}
```

---

##  구성 및 실행 필수 사항

### A. Google API 인증 정보 설정

프로젝트 실행을 위해서는 Google Cloud Console에서 OAuth 2.0 클라이언트 ID를 발급받아야 합니다.

#### application.yml
```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: YOUR_GOOGLE_CLIENT_ID
            client-secret: YOUR_GOOGLE_CLIENT_SECRET
            scope:
              - email
              - profile
```

### B. 프론트엔드 로그인 링크

로그인 페이지 템플릿에 다음 링크를 추가합니다:
```html
<a href="/oauth2/authorization/google">
    <button>구글 로그인</button>
</a>
```

---

## 보안 고려사항

1. **비밀번호 인코딩**: 실제 프로덕션 환경에서는 BCrypt를 사용하여 비밀번호를 인코딩해야 합니다.
```java
   passwordEncoder.encode(UUID.randomUUID().toString())
```

2. **환경 변수 사용**: Google 클라이언트 ID와 Secret은 환경 변수로 관리하는 것을 권장합니다.
```yaml
   client-id: ${GOOGLE_CLIENT_ID}
   client-secret: ${GOOGLE_CLIENT_SECRET}
```

3. **리다이렉트 URI**: Google Cloud Console에서 승인된 리다이렉트 URI를 정확히 설정해야 합니다.
    - 개발 환경: `http://localhost:8080/login/oauth2/code/google`
    - 프로덕션: `https://yourdomain.com/login/oauth2/code/google`

---

## 📚 참고 자료

- [Spring Security OAuth 2.0 공식 문서](https://spring.io/guides/tutorials/spring-boot-oauth2)
- [Google OAuth 2.0 가이드](https://developers.google.com/identity/protocols/oauth2)

---