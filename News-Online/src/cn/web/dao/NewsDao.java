package cn.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.web.entity.News;
import cn.web.entity.NewsDetail;

public class NewsDao extends BaseDao {
	public int updateNews(News news) {
		String sql="UPDATE news set ntid=?,ntitle=?,nauthor=?,npicpath=?,ncontent=?,nmodifydate=NOW(),nsummary=? WHERE nid=?";
		Connection con = super.getConnection();
		PreparedStatement pstmt = null;
		int row = 0;
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, news.getNtid());
			pstmt.setString(2, news.getNtitle());
			pstmt.setString(3, news.getNauthor());
			pstmt.setString(4, news.getNpicpath());
			pstmt.setString(5, news.getNcontent());
			pstmt.setString(6, news.getNsummary());
			pstmt.setInt(7, news.getNid());
			
			row  = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			super.closeDB(con, pstmt, null);
		}
		return row;
	}
	/**
	 * 分页查询
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页记录数
	 * @return
	 */
	public List<News> findNewsByPage(int pageNo, int pageSize) {
		String sql = "SELECT * FROM news LIMIT ?,?";
		Connection con = super.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<News> list = new ArrayList<News>();
		try {
			pstmt = con.prepareStatement(sql);
			// 设置分页的参数
			pstmt.setInt(1, (pageNo - 1) * pageSize);
			pstmt.setInt(2, pageSize);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				News news = new News();
				news.setNid(rs.getInt("nid"));
				news.setNtid(rs.getInt("ntid"));
				news.setNtitle(rs.getString("ntitle"));
				news.setNauthor(rs.getString("nauthor"));
				news.setNcreatedate(rs.getString("ncreatedate"));
				news.setNcontent(rs.getString("ncontent"));
				news.setNsummary(rs.getString("nsummary"));
				news.setNpicpath(rs.getString("npicpath"));
				list.add(news);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			super.closeDB(con, pstmt, rs);
		}
		return list;
	}

	/**
	 * 查询新闻总记录数
	 * 
	 * @return
	 */
	public int getAllCounts() {
		String sql = "SELECT COUNT(*) FROM news";
		Connection con = super.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int allCounts = 0;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();// 游标是在第一行之前
			rs.next();
			allCounts = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			super.closeDB(con, pstmt, rs);
		}
		return allCounts;
	}

	/**
	 * 根据新闻主键查询新闻详情
	 * 
	 * @param nid
	 * @return
	 */
	public NewsDetail findNewsById(int nid) {
		String sql = "SELECT news.*,topic.tname FROM news,topic WHERE news.ntid=topic.tid AND news.nid=?";
		Connection con = super.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		NewsDetail news = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, nid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				news = new NewsDetail();
				news.setNid(rs.getInt("nid"));
				news.setNtid(rs.getInt("ntid"));
				news.setNtitle(rs.getString("ntitle"));
				news.setNauthor(rs.getString("nauthor"));
				news.setNcreatedate(rs.getString("ncreatedate"));
				news.setNcontent(rs.getString("ncontent"));
				news.setNsummary(rs.getString("nsummary"));
				news.setNpicpath(rs.getString("npicpath"));
				news.setTname(rs.getString("tname"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			super.closeDB(con, pstmt, rs);
		}
		return news;
	}

	/**
	 * 查找到指定类型新闻
	 * 
	 * @param tid
	 * @return
	 */
	public List<News> findNews(int tid) {
		String sql = "SELECT * FROM news WHERE ntid=?";
		Connection con = super.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		News news = null;
		List<News> list = new ArrayList<>();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, tid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				news = new News();
				news.setNid(rs.getInt("nid"));
				news.setNtid(rs.getInt("ntid"));
				news.setNtitle(rs.getString("ntitle"));
				news.setNauthor(rs.getString("nauthor"));
				news.setNcreatedate(rs.getString("ncreatedate"));
				news.setNcontent(rs.getString("ncontent"));
				news.setNsummary(rs.getString("nsummary"));
				news.setNpicpath(rs.getString("npicpath"));
				list.add(news);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			super.closeDB(con, pstmt, rs);
		}

		return list;

	}

	/**
	 * 查找到所有新闻
	 * 
	 * @return
	 */
	public List<News> findAllNews() {

		String sql = "SELECT * FROM news ";
		Connection con = super.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<News> list = new ArrayList<>();
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				News news = new News();
				news.setNid(rs.getInt("nid"));
				news.setNtid(rs.getInt("ntid"));
				news.setNtitle(rs.getString("ntitle"));
				news.setNauthor(rs.getString("nauthor"));
				news.setNcreatedate(rs.getString("ncreatedate"));
				news.setNcontent(rs.getString("ncontent"));
				news.setNsummary(rs.getString("nsummary"));
				news.setNpicpath(rs.getString("npicpath"));
				list.add(news);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			super.closeDB(con, pstmt, rs);
		}

		return list;

	}

	/**
	 * 分页查询
	 * 
	 * @param ntid
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<News> findClassNews(int ntid, int pageNo, int pageSize) {
		String sql = "select * from news  WHERE ntid=? limit ?,?";
		Connection con = super.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<News> listNews = new ArrayList<News>();
		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, ntid);
			stmt.setInt(2, (pageNo - 1) * pageSize);
			stmt.setInt(3, pageSize);
			rs = stmt.executeQuery();
			while (rs.next()) {
				News news = new News();
				news.setNid(rs.getInt("nid"));
				news.setNtid(rs.getInt("ntid"));
				news.setNtitle(rs.getString("ntitle"));
				news.setNauthor(rs.getString("nauthor"));
				news.setNcreatedate(rs.getString("ncreatedate"));
				news.setNpicpath(rs.getString("npicpath"));
				news.setNcontent(rs.getString("ncontent"));
				news.setNmodifydate(rs.getString("nmodifydate"));
				news.setNsummary(rs.getString("nsummary"));
				listNews.add(news);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			super.closeDB(con, stmt, rs);
		}
		return listNews;
	}

	/**
	 * 保存新闻
	 * 
	 * @param news
	 * @return
	 */

	public int saveNews(News news) {
		String sql = "INSERT INTO news(ntid,ntitle,nauthor,ncreatedate,npicpath,ncontent,nsummary)VALUES(?,?,?,NOW(),?,?,?)";

		Connection con = super.getConnection();
		PreparedStatement pstmt = null;
		int row = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, news.getNid());
			pstmt.setString(2, news.getNtitle());
			pstmt.setString(3, news.getNauthor());
			pstmt.setString(4, news.getNpicpath());
			pstmt.setString(5, news.getNcontent());
			pstmt.setString(6, news.getNsummary());

			row = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			super.closeDB(con, pstmt, null);
		}
		return row;
	}

	/**
	 * 删除指定id新闻和新闻中的评论
	 * @param nid
	 * @return
	 */
	public int deleteNews(int nid) {
		String sql = "DELETE news,comments from news LEFT JOIN comments ON news.nid=comments.cnid WHERE news.nid=?";

		Connection con = super.getConnection();
		PreparedStatement pstmt = null;
		int row = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, nid);

			row = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			super.closeDB(con, pstmt, null);
		}
		return row;
	}
}
