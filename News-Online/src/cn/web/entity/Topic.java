package cn.web.entity;

import java.util.ArrayList;
import java.util.List;

/*
 * 主题  实体类   一对多  主题对新闻
 * @author
 */

public class Topic {
	public Topic() {
		
	}
	public Topic(int tid, String tname) {
		this.tid = tid;
		this.tname = tname;
	}

	private int tid;
	private String tname;

	
	private List<News> newsList = new ArrayList<News>();

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public List<News> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<News> newsList) {
		this.newsList = newsList;
	}

}
