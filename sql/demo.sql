--테이블삭제
drop table Board;

--시퀀스삭제
drop sequence Board_user_id_seq;

---------
--상품관리
--------
create table Board(
    user_id     number(10),     --유저아이디
    title       varchar2(30),   --제목
    contents    clob,           --내용
    uname       varchar2(30),   --작성자
    cdate       timestamp,      --작성날짜
    udate       timestamp       --수정날짜
);
--기본키
alter table Board add constraint Board_user_id_pk primary key(user_id);

--시퀀스생성
create sequence Board_user_id_seq;

--디폴트
alter table product modify cdate default systimestamp; --운영체제 일시를 기본값으로
alter table product modify udate default systimestamp; --운영체제 일시를 기본값으로

--필수 not null
alter table board modify title not null;
alter table board modify contents not null;
alter table board modify uname not null;

--생성--
insert into Board(user_id,title,contents,uname)
     values(Board_user_id_seq.nextval, '제목1','내용을 입력합니다1','홍길동');
insert into Board(user_id,title,contents,uname)
     values(Board_user_id_seq.nextval, '제목2', '내용을 입력합니다2','홍길순');
insert into Board(user_id,title,contents,uname)
     values(Board_user_id_seq.nextval, '제목3', '내용을 입력합니다3','홍길남');
commit;