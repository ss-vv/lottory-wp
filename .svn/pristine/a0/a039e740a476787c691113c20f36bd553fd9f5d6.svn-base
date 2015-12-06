/**
 * 
 */
package com.xhcms.ucenter.persist.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.xhcms.lottery.commons.data.User;
import com.xhcms.ucenter.persist.dao.ISyncUserDao;

/**
 * @author Bean.Long
 *
 */
public class SyncUserDaoImpl extends JdbcDaoSupport implements ISyncUserDao {
	private final static String pre_ucenter_members = "pre_ucenter_members";
	private final static String pre_common_member = "pre_common_member";
	private final static String pre_common_member_count = "pre_common_member_count";
	
	@Override
	public void insertCommonMember(final long uid, final String username, final String password,
			final String email, final String clientip, final String groupid) {
		getJdbcTemplate().update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement("insert into " + pre_common_member + "(uid, username, password, groupid) values(?,?,?,?)");
			
				ps.setLong(1, uid);
				ps.setString(2, username);
				ps.setString(3, password);
				ps.setShort(4, (short)Integer.parseInt(groupid));
				
				return ps;
			}
		});
	}

	@Override
	public void insertMember(final long uid, final String username, final String password,
			final String email, final String regip) {
		getJdbcTemplate().update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement("insert into " + pre_ucenter_members + 
						"(uid, username, password, email, regip, salt) values(?,?,?,?,?, '')");
				
				ps.setLong(1, uid);
				ps.setString(2, username);
				ps.setString(3, password);
				ps.setString(4, email);
				ps.setString(5, regip);
				
				return ps;
			}
		});
	}
	
	@Override
	public void updateMemberPassword(long uid, String password) {
		try {
			getJdbcTemplate().update("update " + pre_ucenter_members + " set password=? where uid=?", password, uid);
			getJdbcTemplate().update("update " + pre_common_member + " set password=? where uid=?", password, uid);
		} catch(DataAccessException exp) {
			logger.error(exp.getMessage(), exp);
		}
	}

	@Override
	public void insertMemberCount(final long uid) {
		getJdbcTemplate().update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement("insert into " + pre_common_member_count + 
						"(uid) values(?)");
				ps.setLong(1, uid);
				return ps;
			}
		});
	}

	@Override
	public User findMemberByUsername(final String username) {
		List<User> users = getJdbcTemplate().query(new PreparedStatementCreator(){

			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement("select uid, username, password, regip, email from " +
					pre_ucenter_members + " where username=?");
				ps.setString(1, username);
				return ps;
			}
			
		}, new UserRowmapper());
		
		if(users.size() > 0) {
			return users.get(0);
		}
		
		return null;
	}
	
	class UserRowmapper implements RowMapper<User> {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			User user = new User();
			user.setId(rs.getLong("uid"));
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setIp(rs.getString("regip"));
			user.setEmail(rs.getString("email"));
			
			return user;
		}
		
	}
}
