package com.kh.demo.web.req.comment;

import lombok.Data;

@Data
public class ReqWrite {
  private Long bnum;
  private String uname;
  private String contents;
}
