 package cn.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.web.entity.Comment;

public class CommentDao extends BaseDao {
	/**
	 * 删除评论
	 * @param cid
	 * @return
	 */
	public int deleteComment(int cid) {
		String sql = "DELETE FROM comments WHERE cid=?";
		Connection con = super.getConnection();
		PreparedStatement pstmt = null;
		int row = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cid);
			
			row = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			super.closeDB(con, pstmt, null);
		}
		return row;
	}
	/**
	 * 根据新闻查询评论列表
	 * @param newId
	 * @return
	 */
	public List<Comment> findCommentsByNews(int newId){
		String sql = "SELECT * FROM comments WHERE cnid = ?";
		
		Connection con = super.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Comment> list = new ArrayList<Comment>();
		Comment comment = null;
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, newId);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				comment = new Comment();
				comment.setCid(rs.getInt("cid"));
				comment.setCnid(rs.getInt("cnid"));
				comment.setCcontent(rs.getString("ccontent"));
				comment.setCdate(rs.getString("cdate"));
				comment.setCip(rs.getString("cip"));
				comment.setCauthor(rs.getString("cauthor"));
				list.add(comment);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			super.closeDB(con, pstmt, rs);
		}
		
		return list;
		
	}
	
	/**
	 * 发表评论
	 * @param comment
	 * @return
	 */
	public int addComment(Comment comment) {
		String sql = "INSERT INTO comments(cnid,ccontent,cdate,cip,cauthor)VALUES(?,?,NOW(),?,?)";
		Connection con = super.getConnection();
		PreparedStatement pstmt  = null;
		int row = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, comment.getCnid());
			pstmt.setString(2,comment.getCcontent());
			pstmt.setString(3, comment.getCip());
			pstmt.setString(4, comment.getCauthor());
			
			row = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			super.closeDB(con, pstmt, null);
		}
		return row;
	}
}
