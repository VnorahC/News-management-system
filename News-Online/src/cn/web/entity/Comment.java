package cn.web.entity;

/*
 * 评论 实体类
 * @author 
 **/
public class Comment {
	public Comment() {
		
	}
	public Comment( int cnid, String cauthor,  String cip, String ccontent) {
		this.cnid = cnid;
		this.cauthor = cauthor;
		this.cip = cip;
		this.ccontent = ccontent;
	
		
	}

	private int cid;
	private int cnid;
	private String ccontent;
	private String cdate;
	private String cip;
	private String cauthor;
	
	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getCnid() {
		return cnid;
	}

	public void setCnid(int cnid) {
		this.cnid = cnid;
	}

	public String getCcontent() {
		return ccontent;
	}

	public void setCcontent(String ccontent) {
		this.ccontent = ccontent;
	}

	public String getCdate() {
		return cdate;
	}

	public void setCdate(String cdate) {
		this.cdate = cdate;
	}

	public String getCip() {
		return cip;
	}

	public void setCip(String cip) {
		this.cip = cip;
	}

	public String getCauthor() {
		return cauthor;
	}

	public void setCauthor(String cauthor) {
		this.cauthor = cauthor;
	}
}
