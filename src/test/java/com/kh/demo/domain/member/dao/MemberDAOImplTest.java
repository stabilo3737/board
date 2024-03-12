package com.kh.demo.domain.member.dao;

import com.kh.demo.domain.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
class MemberDAOImplTest {

  @Test
  @DisplayName("회원가입테스트")
  void inserMember() {
    Member member = new Member();
    member.setEmail("user3@kh.com");
    member.setPasswd("user3");
    member.setNickname("사용자3");

    log.info("member={}",member);
  }
}