package com.kh.demo.domain.comment.dao;

import com.kh.demo.domain.entity.Comment;
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
@Repository
public class CommentDAOImpl implements CommentDAO{

  private final NamedParameterJdbcTemplate template;

  CommentDAOImpl(NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  //댓글 작성
  @Override
  public Long write(Comment comment) {
    StringBuffer sql = new StringBuffer();
    sql.append("insert into Bcomment(bcomment_id, bnum, contents, uname) ");
    sql.append("values(Bcomment_bcomment_id_seq.nextval, :bnum, :contents, :uname) ");

    SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.update(sql.toString(),param,keyHolder,new String[]{"bcomment_id", "bnum", "contents", "uname"});
    Long bcomment_id = ((BigDecimal)keyHolder.getKeys().get("bcomment_id")).longValue();
    return bcomment_id;
  }

  //조회
  @Override
  public Optional<Comment> findById(Long bcommentId) {

    StringBuffer sql = new StringBuffer();
    sql.append("select bcommentId,bnum,contents,uname,cdate,udate ");
    sql.append("  from bcomment ");
    sql.append(" where bcommentId = :bcommentId ");

    try{
      Map<String, Long> map = Map.of("bcommentId",bcommentId);
      Comment comment = template.queryForObject(sql.toString(), map, BeanPropertyRowMapper.newInstance(Comment.class));
      return Optional.of(comment);
    }catch (EmptyResultDataAccessException e){
      return Optional.empty();
    }
  }

  //삭제
  @Override
  public int deleteById(Long bcommentId) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from bcomment ");
    sql.append("where bcomment_id = :bcommentId"); // 수정된 부분
    SqlParameterSource param = new MapSqlParameterSource()
            .addValue("bcommentId", bcommentId);
    int deletedRowCnt = template.update(sql.toString(), param);
    return deletedRowCnt;
  }

  //수정
  @Override
  public int updateById(Long bcommentId, Comment comment) {
    StringBuffer sql = new StringBuffer();
    sql.append("update bcomment ");
    sql.append("set contents = :contents "); // 수정된 부분, 불필요한 콤마 제거
    sql.append("where bcomment_id = :bcommentId"); // 수정된 부분
    SqlParameterSource param = new MapSqlParameterSource()
            .addValue("contents", comment.getContents())
            .addValue("bcommentId", bcommentId); // 추가된 부분
    int updateRowCnt = template.update(sql.toString(), param);
    return updateRowCnt;
  }
  //목록
  @Override
  public List<Comment> findAll(Long bnum, Long reqPage, Long recCnt) {
    StringBuffer sql = new StringBuffer();
    sql.append("  select bcomment_id,bnum,contents,uname,cdate,udate ");
    sql.append("    from bcomment ");
    sql.append("where bnum = :bnum ");
    sql.append("order by bcomment_id desc ");
    sql.append("offset (:reqPage - 1) * :recCnt rows fetch first :recCnt rows only ");

    Map<String,Long> param = Map.of("bnum", bnum, "reqPage",reqPage,"recCnt",recCnt);
    List<Comment> list = template.query(sql.toString(), param, BeanPropertyRowMapper.newInstance(Comment.class));

    return list;
  }
  @Override
  public int totalCnt() {
    String sql = "select count(bcomment_id) from bcomment ";

    SqlParameterSource param = new MapSqlParameterSource();
    Integer cnt = template.queryForObject(sql, param, Integer.class);

    return cnt;
  }

}
