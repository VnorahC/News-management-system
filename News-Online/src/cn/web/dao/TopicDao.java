package cn.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.web.entity.News;
import cn.web.entity.Topic;

public class TopicDao extends BaseDao{
	/**
	 * 查询所有主题
	 * @return
	 */
	public List<Topic> findAllTopic(){
		String sql = "SELECT * FROM topic";
		Connection con = super.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Topic> list = new ArrayList<Topic>();
		Topic topic=null;
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				 topic = new Topic();
				topic.setTid(rs.getInt("tid"));
				topic.setTname(rs.getString("tname"));
				list.add(topic);
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
	 * 查找主题和新闻
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<Topic> findTopicAndNewsList(int pageNo,int pageSize) {
		String sql = "SELECT * from topic t LEFT OUTER JOIN news n ON t.tid=n.ntid ORDER BY t.tid LIMIT ?,?";
		
		List<Topic> list = new ArrayList<Topic>();
		Connection con = super.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, (pageNo - 1)*pageSize);
			ps.setInt(2, pageSize);
			
			rs = ps.executeQuery();
			// topic--> List<News>
			int tid=0;  //中间比较数
			Topic topic = null;
			while(rs.next()){
				
				if (rs.getInt("tid") != tid) { //判断是否是同一个主题的ID，同一个ID，仅创建一个Topic对象
					//创建topic对象
					topic = new Topic();
					topic.setTid(rs.getInt("tid"));
					topic.setTname(rs.getString("tname"));
					list.add(topic);
				}
				
				//封装新闻数据?
				if (rs.getString("nid") != null) {
					News news = new News();
					news.setNid(rs.getInt("nid"));
					news.setNtid(rs.getInt("ntid"));
					news.setNtitle(rs.getString("ntitle"));
					news.setNauthor(rs.getString("nauthor"));
					news.setNcreatedate(rs.getString("ncreatedate"));
					news.setNcontent(rs.getString("ncontent"));
					news.setNsummary(rs.getString("nsummary"));
					topic.getNewsList().add(news);
				}			
				tid = rs.getInt("tid");  //更新中间比较数
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			super.closeDB(con, ps, rs);
		}
		return list;
	}
	
	
	/**
	 * 指定主题和对应的新闻总数
	 * @return
	 */
	public int getTopicCount() {
		
		String sql = "SELECT count(*) from topic t LEFT OUTER JOIN news n ON t.tid=n.ntid"; 
		
		Connection con = super.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			super.closeDB(con, pstmt, rs);;
		}
		return count;	
	}
	
	/**
	 * 查找指定的主题id
	 * @param tid
	 * @return
	 */
	public Topic getTopicById(int tid) {
		String sql = "select * from topic where tid=?";
		
		Connection con = super.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Topic topic=null;
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, tid);
			
			rs = ps.executeQuery();
			if (rs.next()) {
				topic = new Topic();
				topic.setTid(rs.getInt("tid"));
				topic.setTname(rs.getString("tname"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			super.closeDB(con, ps, rs);
		}
		return topic;
	}
	
	/**
	 * 查询从某条开始至某条结束的主题
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<Topic> getTopicsByPager(int pageNo,int pageSize) {
		String sql = "select * from topic limit ?,?";
		List<Topic> list = new ArrayList<Topic>();
		Connection con = super.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, (pageNo-1)*pageSize);
			ps.setInt(2, pageSize);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Topic topic = new Topic();
				topic.setTid(rs.getInt("tid"));
				topic.setTname(rs.getString("tname"));
				list.add(topic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			super.closeDB(con, ps, rs);
		}
		return list;
	}
	
	/**
	 * 主题总记录数
	 * @return
	 */
	public int getAllTopicsCount() {
		String sql = "select count(*) from topic";
		
		Connection con = super.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			super.closeDB(con, ps, rs);
		}
		return count;
	}

	/**
	 * 添加主题
	 * @param topic
	 * @return
	 */
	public int addTopic(Topic topic) {
		String sql = "insert into topic(tname) values(?)";
		
		Connection con = super.getConnection();
		PreparedStatement pstmt = null;
		int row = 0;
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, topic.getTname());
			
			row = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			super.closeDB(con, pstmt, null);
		}
		return row;
	}
	
	/**
	 * 删除指定主题
	 * @param tid
	 * @return
	 */
	public int deleteTopic(int tid) {
		String sql = "delete from topic where tid=?";
		
		Connection con = super.getConnection();
		PreparedStatement pstmt = null;
		int row = 0;
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, tid);
			
			row = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			super.closeDB(con, pstmt, null);
		}
		return row;
	}
	
	/**
	 * 修改主题
	 * @param topic
	 * @return
	 */
	public int updateTopic(Topic topic) {
		String sql = "update topic set tname=? where tid=?";
		
		Connection con = super.getConnection();
		PreparedStatement pstmt = null;
		int row = 0;
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, topic.getTname());
			pstmt.setInt(2, topic.getTid());
			
			row = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			super.closeDB(con, pstmt, null);
		}
		return row;
	}
	
}
