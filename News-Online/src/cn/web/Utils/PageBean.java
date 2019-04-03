package cn.web.Utils;

public class PageBean {

	public static final int DEFAULT_PAGE_SIZE = 10;

	private int pageNo;
	private int allCount;
	private int allPages;
	private int prev;
	private int next;
	private int pageSize;
	public PageBean(int pageNo) {
		this.pageNo = pageNo;
		this.prev = pageNo;
		this.next = pageNo;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNO(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getAllCount() {
		return allCount;
	}
	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}
	public int getAllPages() {
		//在调用get方法时，计算总页数，在页面el
		this.allPages = (allCount % this.getPageSize() == 0) ? (allCount / this.getPageSize()) : (allCount / this.getPageSize() + 1);
		return allPages;
	}
	public void setAllPages(int allPages) {
		this.allPages = allPages;
	}
	public int getPrev() {
		//判断页数边界
		if(pageNo<=1) {
			prev = 1;
		}else if(pageNo>=allPages) {
			prev--;
		}else {
			prev--;
		}
		return prev;
	}
	public void setPrev(int prev) {
		this.prev = prev;
	}
	public int getNext() {
		//判断页数边界
		if(pageNo<=1) {
			next++;
		}else if(pageNo>=allPages) {
			next=allPages;
		}else {
			next++;
		}
		return next;
	}
	public void setNext(int next) {
		this.next = next;
	}
	public int getPageSize() {
		this.pageSize = (this.pageSize<DEFAULT_PAGE_SIZE?DEFAULT_PAGE_SIZE:this.pageSize);
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public static int getDefaultPageSize() {
		return DEFAULT_PAGE_SIZE;
	}
}
