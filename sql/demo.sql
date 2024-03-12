--테이블삭제
drop table member;
drop table Bcomment;
drop table Board;

--시퀀스삭제 생성
drop sequence Board_user_id_seq;
create sequence Board_user_id_seq;

-----------
--게시판테이블
------------
create table Board(
    user_id     number(10),     --유저아이디
    title       varchar2(30),   --제목
    contents    clob,           --내용
    uname       varchar2(30),   --작성자
    email       varchar2(50),   --email 추가
    cdate       timestamp,      --작성날짜
    udate       timestamp       --수정날짜
);
--기본키
alter table Board add constraint Board_user_id_pk primary key(user_id);

--외래키
alter table Board add constraint Board_email_fk foreign key(email) references member(email);


--디폴트
alter table Board modify cdate default systimestamp; --운영체제 일시를 기본값으로
alter table Board modify udate default systimestamp; --운영체제 일시를 기본값으로

--필수 not null
alter table board modify title not null;
alter table board modify contents not null;
alter table board modify uname not null;

--생성--
insert into Board(user_id,title,contents,uname,email)
     values(Board_user_id_seq.nextval, '제목1','내용을 입력합니다1','홍길동','user1@kh.com');
insert into Board(user_id,title,contents,uname,email)
     values(Board_user_id_seq.nextval, '제목2', '내용을 입력합니다2','홍길순','user2@kh.com');
insert into Board(user_id,title,contents,uname,email)
     values(Board_user_id_seq.nextval, '제목3', '내용을 입력합니다3','홍길남','user3@kh.com');

--시퀀스삭제
drop sequence Bcomment_bcomment_id_seq;
--시퀀스생성
create sequence bcomment_bcomment_id_seq;


----------
--댓글테이블
----------
create table Bcomment(
    bcomment_id  number(10),  -- 아이디
    bnum        number(10),  -- 원글번호
    contents    clob,        -- 내용
    uname       varchar2(30),-- 작성자
    cdate       timestamp,   --작성날짜
    udate       timestamp    --수정날짜
);

--기본키
alter table Bcomment add constraint Bcomment_bcomment_id_pk primary key(bcomment_id);

--외래키
alter table Bcomment add constraint Bcomment_bnum_fk foreign key(bnum) references Board(user_id);

--디폴트
alter table Bcomment modify cdate default systimestamp; --운영체제 일시를 기본값으로
alter table Bcomment modify udate default systimestamp; --운영체제 일시를 기본값으로

--필수 not null
alter table Bcomment modify bnum not null;
alter table Bcomment modify contents not null;
alter table Bcomment modify uname not null;

--샘플데이터 생성--
insert into Bcomment(bcomment_id, bnum, contents, uname)
     values(Bcomment_bcomment_id_seq.nextval, '1', '댓글 내용 입력', '홍길동');
insert into Bcomment(bcomment_id, bnum, contents, uname)
     values(Bcomment_bcomment_id_seq.nextval, '1', '댓글 내용 입력', '홍길순');
insert into Bcomment(bcomment_id, bnum, contents, uname)
     values(Bcomment_bcomment_id_seq.nextval, '2', '댓글 내용 입력', '홍길남');

----------
--회원테이블
----------
create table member(
    member_id   number(10),     --내부관리 아이디
    email       varchar2(50),   --로긴 아이디
    passwd      varchar2(12),   --로긴 비밀번호
    nickname    varchar2(30),   --별칭
    gubun       varchar2(11),    --default 'M0101', --회원구분(일반,우수,관리자1,관리자2)..
    pic         blob,           --사진
    cdate       timestamp,      --생성일,가입일
    udate       timestamp       --수정일
);
--기본키생성
alter table member add Constraint member_member_id_pk primary key(member_id);

drop sequence member_member_id_seq;
--시퀀스
create sequence member_member_id_seq;

--디폴트
alter table member modify gubun default 'M0101';
alter table member modify cdate default systimestamp;
alter table member modify udate default systimestamp;

--notnull

--샘플테이터
insert into member (member_id,email,passwd,nickname)
    values(member_member_id_seq.nextval,'user1@kh.com','user1','사용자1');
insert into member (member_id,email,passwd,nickname)
    values(member_member_id_seq.nextval,'user2@kh.com','user2','사용자2');





