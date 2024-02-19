package com.kh.demo.domain.board.dao;

import com.kh.demo.domain.entity.Board;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository //dao역할을 하는 클래스
public class BoardDAOImpl implements BoardDAO {

  private final NamedParameterJdbcTemplate template;

  BoardDAOImpl(NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  //게시글작성
  @Override
  public Long write(Board board) {
    StringBuffer sql = new StringBuffer();
    sql.append("insert into Board(user_id,title,contents,uname) ");
    sql.append("values(Board_user_id_seq.nextval, :title, :contents, :uname) ");

    // SQL파라미터 자동매핑
    SqlParameterSource param = new BeanPropertySqlParameterSource(board);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.update(sql.toString(),param,keyHolder,new String[]{"user_id","title","contents","uname"});
    Long user_id = ((BigDecimal)keyHolder.getKeys().get("user_id")).longValue(); //유저아이디
    return user_id;
  }

  //조회
  @Override
  public Optional<Board> findById(Long userId) {
    StringBuffer sql = new StringBuffer();
    sql.append("select user_id,title,contents,uname,cdate,udate ");
    sql.append("  from board ");
    sql.append(" where user_id = :userId ");

    try {
      Map<String,Long> map = Map.of("userId",userId);
      Board board = template.queryForObject(sql.toString(), map, BeanPropertyRowMapper.newInstance(Board.class));
      return Optional.of(board);

    }catch (EmptyResultDataAccessException e){
      //조회결과가 없는경우
      return Optional.empty();
    }
  }

  //단건삭제
  @Override
  public int deleteById(Long userId) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from board ");
    sql.append(" where user_id = :userId ");

    SqlParameterSource param = new MapSqlParameterSource()
            .addValue("userId",userId);

    int deletedRowCnt = template.update(sql.toString(), param);

    return deletedRowCnt;
  }

  //여러건삭제
  @Override
  public int deleteByIds(List<Long> userIds) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from board ");
    sql.append(" where user_id in (:userIds) ");

    Map<String, List<Long>> map = Map.of("userIds", userIds);
    int deletedRowCnt = template.update(sql.toString(), map);
    return deletedRowCnt;
  }

  //수정
  @Override
  public int updateById(Long userId, Board board) {
    StringBuffer sql = new StringBuffer();
    sql.append("update board ");
    sql.append("   set title = :title, ");
    sql.append("       contents = :contents, ");
    sql.append("       uname = :uname, ");
    sql.append("       udate = default ");
    sql.append(" where user_id = :userId ");

    //sql 파라미터 변수에 값 매핑
    SqlParameterSource param = new MapSqlParameterSource()
            .addValue("title", board.getTitle())
            .addValue("contents", board.getContents())
            .addValue("uname",  board.getUname())
            .addValue("userId", userId);

    //update수행 후 변경된 행수 반환
    int updateRowCnt = template.update(sql.toString(), param);

    return updateRowCnt;
  }

  //목록
  @Override
  public List<Board> findAll() {
    StringBuffer sql = new StringBuffer();
    sql.append("  select user_id,title,contents,uname,cdate,udate ");
    sql.append("    from board ");
    sql.append("order by user_id desc");

    List<Board> list = template.query(sql.toString(), BeanPropertyRowMapper.newInstance(Board.class));

    return list;
  }


}