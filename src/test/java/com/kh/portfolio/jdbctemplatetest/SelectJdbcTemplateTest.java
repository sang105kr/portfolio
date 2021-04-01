package com.kh.portfolio.jdbctemplatetest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StringUtils;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/*.xml" })
public class SelectJdbcTemplateTest {

	private final static Logger logger = LoggerFactory.getLogger(SelectJdbcTemplateTest.class);

	@Autowired
	JdbcTemplate jt;

	@Test
	@Disabled
	void select1() {
		logger.info("==select1()==");
		StringBuilder sql = new StringBuilder();

		sql.append("select id,name,kor,eng,math from student");

		// 하나의 레코드를 컬럼은 key,값은value를 매핑항 맵객체를 만들고 List에 담아올때 사용
		List<Map<String, Object>> list = jt.queryForList(sql.toString());
		for (Map<String, Object> record : list) {
			logger.info(record.toString());
		}

		System.out.println(list.get(0).values());
		System.out.println(list.get(0).keySet());
		System.out.println(list.get(0).get("kor"));
	}

	@Test
	@Disabled
	void select2() {

		logger.info("==select2()==");
		String sql = "select kor from student";
		// 하나의 컬럼목록을 읽어오고자 할때 사용
		List<Integer> list = jt.queryForList(sql, Integer.class);
		for (Integer i : list) {
			logger.info(i.toString());
		}
	}

	@Test
	@Disabled
	void select3() {

		logger.info("==select3()==");
		String sql = "select kor,math from student where kor >= ? and math >= ?";

		List<Map<String, Object>> list = jt.queryForList(sql.toString(), 50, 50);
		for (Map<String, Object> record : list) {
			logger.info(record.toString());
		}
	}

	@Test
	@Disabled
	void select4() {

		logger.info("==select4()==");
		String sql = "select kor from student where kor >= ? ";
		// 하나의 컬럼목록을 읽어오고자 할때 사용
		List<Integer> list = jt.queryForList(sql, Integer.class, 60);
		for (Integer i : list) {
			logger.info(i.toString());
		}
	}

	@Test
	@Disabled
	void select5() {
		logger.info("==select5()==");

		String sql = "select name from student where kor >= ? and math >= ?";

		List<String> list = jt.queryForList(sql, new Object[] { 50, 50 }, String.class);
//	List<String> list =	 jt.queryForList(sql, String.class, 50,50);
		for (String str : list) {
			logger.info(str);
		}
	}

	@Test
	@Disabled
	void select6() {
		logger.info("==select6()==");

		String sql = "select name, eng from student where kor >= ? and math >= ?";
		List<Map<String, Object>> list = jt.queryForList(sql, new Object[] { 50, 50 },
				new int[] { Types.INTEGER, Types.INTEGER });

		for (Map<String, Object> record : list) {
			logger.info(record.toString());
		}
	}

	@Test
	@Disabled
	void select7() {
		logger.info("==select7()==");

		String sql = "select name from student where kor >= ? and math >= ? ";

		List<String> list = jt.queryForList(sql, // sql문
				new Object[] { 50, 50 }, // ?에대한 치환할 값
				new int[] { Types.INTEGER, Types.INTEGER }, // ?에대한 치환할 값의 타입
				String.class); // 결과 컬럼의 타입

		for (String record : list) {
			logger.info(record.toString());
		}
	}

	@Test
	@Disabled
	void select11() {
		logger.info("==select11()==");

		String sql = "select id,name,kor,eng,math from student where id = 'id1'";

		Map<String, Object> map = jt.queryForMap(sql);
		logger.info(map.toString());
	}

	@Test
	@Disabled
	void select12() {
		logger.info("==select12()==");

		String sql = "select id,name,kor,eng,math from student where id = ?";

		Map<String, Object> map = jt.queryForMap(sql, "id2");
		logger.info(map.toString());
	}

	@Test
	@Disabled
	void select13() {
		logger.info("==select13()==");

		String sql = "select id,name,kor,eng,math from student where id = ?";

		Map<String, Object> map = jt.queryForMap(sql, new Object[] { "id1" }, new int[] { Types.VARCHAR });
		logger.info(map.toString());
	}

	@Test
	@Disabled
	void select21() {
		logger.info("==select21()==");

		String sql = "select count(*) from student";

		Integer cnt = jt.queryForObject(sql, Integer.class);
		logger.info(cnt.toString());
	}

	@Test
	@Disabled
	void select22() {
		logger.info("==select22()==");

		String sql = "select * from student where id = 'id1'";

		StudentVO studentVO = jt.queryForObject(sql, new RowMapper<StudentVO>() {

			@Override
			public StudentVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				StudentVO studentVO = new StudentVO();
				studentVO.setId(rs.getString("id"));
				studentVO.setName(rs.getString("name"));
				studentVO.setKor(rs.getInt("kor"));
				studentVO.setEng(rs.getInt("eng"));
				studentVO.setMath(rs.getInt("math"));
				return studentVO;
			}

		});

		logger.info(studentVO.toString());
	}

	@Test
	@Disabled
	void select23() {
		logger.info("==select23()==");

		String sql = "select count(*) from student where eng > ?";

		Integer cnt = jt.queryForObject(sql, Integer.class, 50);
		logger.info(cnt.toString());
	}

	@Test
	@Disabled
	void select24() {
		logger.info("==select24()==");

		String sql = "select count(*) from student where eng > ?";

		Integer cnt = jt.queryForObject(sql, new Object[] { 50 }, Integer.class);
		logger.info(cnt.toString());
	}

	@Test
	@Disabled
	void select25() {
		logger.info("==select25()==");

		String sql = "select id,name from student where id = ? ";
		RowMapper<StudentVO> rowMapper = new RowMapper<StudentVO>() {

			@Override
			public StudentVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				StudentVO studentVO = new StudentVO();

				studentVO.setId(rs.getString("id"));
				studentVO.setName(rs.getString("name"));

				return studentVO;
			}
		};

		StudentVO studentVO = jt.queryForObject(sql, new Object[] { "id1" }, rowMapper);
		logger.info(studentVO.toString());
	}

	@Test
	@Disabled
	void select26() {
		logger.info("==select26()==");

		String sql = "select id,name from student where id = ? ";
		RowMapper<StudentVO> rowMapper = new RowMapper<StudentVO>() {

			@Override
			public StudentVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				StudentVO studentVO = new StudentVO();

				studentVO.setId(rs.getString("id"));
				studentVO.setName(rs.getString("name"));

				return studentVO;
			}
		};

		StudentVO studentVO = jt.queryForObject(sql, rowMapper, "id1");
		logger.info(studentVO.toString());
	}

	@Test
	@Disabled
	void select27() {
		logger.info("==select27()==");

		String sql = "select name from student where id = ? ";

		String name = jt.queryForObject(sql, new Object[] { "id1" }, new int[] { Types.VARCHAR }, String.class);
		logger.info(name.toString());

	}

	@Test
	@Disabled
	void select28() {
		logger.info("==select28()==");

		String sql = "select id,name from student where id = ? ";
		RowMapper<StudentVO> rowMapper = new RowMapper<StudentVO>() {

			@Override
			public StudentVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				StudentVO studentVO = new StudentVO();

				studentVO.setId(rs.getString("id"));
				studentVO.setName(rs.getString("name"));

				return studentVO;
			}
		};

		StudentVO studentVO = jt.queryForObject(sql, new Object[] { "id1" }, new int[] { Types.VARCHAR }, rowMapper);
		logger.info(studentVO.toString());

	}

	@Test
	@Disabled
	void select29() {
		logger.info("==select29()==");

		String sql = "select id,name from student where id = ? ";

		StudentVO studentVO = jt.queryForObject(sql, new BeanPropertyRowMapper<StudentVO>(StudentVO.class), "id1");
		logger.info(studentVO.toString());
	}

	@Test
	@Disabled
	void select31() {
		logger.info("==select31()==");

		String sql = "select id,name,kor,eng,math from student where id='id1'";

		SqlRowSet srs = jt.queryForRowSet(sql);

		while (srs.next()) {
			logger.info(srs.getString("id"));
			logger.info(srs.getString("name"));
			logger.info(srs.getString("kor"));
			logger.info(srs.getString("eng"));
			logger.info(srs.getString("math"));
		}
	}

	@Test
	@Disabled
	void select32() {
		logger.info("==select32()==");

		String sql = "select id,name,kor,eng,math from student where id=?";

		SqlRowSet srs = jt.queryForRowSet(sql, "id1");

		while (srs.next()) {
			logger.info(srs.getString("id"));
			logger.info(srs.getString("name"));
			logger.info(srs.getString("kor"));
			logger.info(srs.getString("eng"));
			logger.info(srs.getString("math"));
		}
	}

	@Test
	@Disabled
	void select33() {
		logger.info("==select33()==");

		String sql = "select id,name,kor,eng,math from student where id=?";

		SqlRowSet srs = jt.queryForRowSet(sql, new Object[] { "id1" }, new int[] { Types.VARCHAR });

		while (srs.next()) {
			logger.info(srs.getString("id"));
			logger.info(srs.getString("name"));
			logger.info(srs.getString("kor"));
			logger.info(srs.getString("eng"));
			logger.info(srs.getString("math"));
		}
	}

	@Test
	void select41() {
		logger.info("==select41()==");

		String sql = "select * from student where eng >= ? and math >= ? ";

		List<StudentVO> list = jt.query(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, 50);
				pstmt.setInt(2, 50);
				return pstmt;
			}
		}, new ResultSetExtractor<List<StudentVO>>() {

			@Override
			public List<StudentVO> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<StudentVO> list = new ArrayList<>();
				while (rs.next()) {
					StudentVO studentVO = new StudentVO();
					studentVO.setId(rs.getString("id"));
					studentVO.setName(rs.getString("name"));
					studentVO.setKor(rs.getInt("kor"));
					studentVO.setEng(rs.getInt("eng"));
					studentVO.setMath(rs.getInt("math"));
					list.add(studentVO);
				}
				return list;
			}
		});
		logger.info(list.toString());
	}

	@Test
	void select42() {
		logger.info("==select42()==");

		String sql = "select * from student where eng >= ? and math >= ? ";
		jt.query(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, 50);
				pstmt.setInt(2, 50);
				return pstmt;
			}
		}, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {

				try (BufferedWriter writer = 
							new BufferedWriter(
									new OutputStreamWriter(
											new FileOutputStream(File.createTempFile("student", ".csv", new File("/")),true), "EUC-KR"))){
					
//					while (rs.next()) {
						Object[] array = new Object[] { rs.getString("id"), rs.getString("name"), rs.getInt("kor"),
								rs.getInt("eng"), rs.getInt("math"), };
						String reportRow = StringUtils.arrayToCommaDelimitedString(array);
						writer.write(reportRow);
						writer.newLine();
						writer.flush();
//					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Test
	void select43() {
		logger.info("==select43()==");

		String sql = "select * from student where eng >= ? and math >= ? ";

		List<StudentVO> list = jt.query(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, 50);
				pstmt.setInt(2, 50);
				return pstmt;
			}
		}, new RowMapper<StudentVO>() {
			@Override
			public StudentVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				StudentVO studentVO = new StudentVO();
				studentVO.setId(rs.getString("id"));
				studentVO.setName(rs.getString("name"));
				studentVO.setKor(rs.getInt("kor"));
				studentVO.setEng(rs.getInt("eng"));
				studentVO.setMath(rs.getInt("math"));
				return studentVO;
			}
		});
		logger.info(list.toString());
	}

	@Test
	void select44() {
		logger.info("==select44()==");

		String sql = "select * from student where eng >= ? and math >= ? ";

		List<StudentVO> list = jt.query(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, 50);
				pstmt.setInt(2, 50);
				return pstmt;
			}
		}, new BeanPropertyRowMapper(StudentVO.class));
		logger.info(list.toString());
	}

	@Test
	void select45() {
		logger.info("==select45()==");

		String sql = "select * from student where eng >= ? and math >= ? ";

		List<StudentVO> list = jt.query(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql);

				return pstmt;
			}
		}, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, 50);
				ps.setInt(2, 50);
			}
		}, new ResultSetExtractor<List<StudentVO>>() {
			
			@Override
			public List<StudentVO> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<StudentVO> list = new ArrayList<>();
				while (rs.next()) {
					StudentVO studentVO = new StudentVO();
					studentVO.setId(rs.getString("id"));
					studentVO.setName(rs.getString("name"));
					studentVO.setKor(rs.getInt("kor"));
					studentVO.setEng(rs.getInt("eng"));
					studentVO.setMath(rs.getInt("math"));
					list.add(studentVO);
				}
				return list;
			}
		});
		logger.info(list.toString());
	}
}
