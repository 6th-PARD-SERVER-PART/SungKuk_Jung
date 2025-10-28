package com.pard.server.hw1_sungkukjung.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserInfoController {

    // 사용자 정보 Map (key: userId, value: userLegalName) 형태로 저장
    public Map<String, String> users = new HashMap<>();

    // 전체 사용자 정보 불러오기 (전체 조회)
    // 에) /users
    @GetMapping
    public Map<String, String> allUserInfo () {
        return users;
    }

    // 새로운 사용자 정보 추가
    // 에) /users?userId=testId1&userLegalName=Tester2
    @PostMapping
    public Map<String, String> addUser(@RequestParam String userId, @RequestParam String userLegalName) {
        // put은 key와 value값을 입력합니다
        users.put(userId, userLegalName);
        return users;
    }

    // 특정 (검색한 ID에 맞는) 사용자 정보 불러오기
    // 예) /users/testId1
    @GetMapping("/{userId}")
    public Map<String, String> findUser(@PathVariable String userId) {
        Map<String, String> idFound = new HashMap<>();
        // containsKey는 해당 key가 있을 시 결과값을 return합니다
        if(users.containsKey(userId)) {
            idFound.put(userId, users.get(userId));
        }
        return idFound;
    }

    // 사용자 정보 수정
    // 예) /users/testId1?userLegalName=Tester2
    @PutMapping("/{userId}")
    public Map<String, String> updateUser(@PathVariable String userId, @RequestParam String userLegalName) {
        if (users.containsKey(userId)) {
            users.put(userId, userLegalName);
        }
        return users;
    }

    // 사용자 기록 삭제
    // 예) /users/testId1
    @DeleteMapping("/{userId}")
    public Map<String, String> deleteUser(@PathVariable String userId) {
        // remove 매소드는 map항목에서 특정한 아이템 (key, value)를 삭제합니다
        users.remove(userId);
        return users;
    }
}
