package com.kh.demo.domain.entity;
// wrapper class
// byte->Byte, short->Short, char->Character, int->Integer, long->Long
// boolean->Boolean, double->Double, float->Float

import lombok.*;

import java.time.LocalDateTime;

@Data
public class Board {
  private Long userId;          //아이디 USER_ID	NUMBER(10,0)
  private String title;         //제목 varchar2(30),
  private String contents;      //내용 CONTENTS (clob)
  private String uname;         //작성자 UNAME	VARCHAR2(30 BYTE)
  private LocalDateTime cdate;  //작성날짜 CDATE	TIMESTAMP(6)
  private LocalDateTime udate;  //수정날짜 UDATE	TIMESTAMP(6)
}