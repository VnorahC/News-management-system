package cn.yq.oa.pojo;

import java.util.List;

public class Menu {
	private Integer id;
	private String name;
	private String url;
	
	private List<Menu> chidren;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Menu> getChidren() {
		return chidren;
	}

	public void setChidren(List<Menu> chidren) {
		this.chidren = chidren;
	}
	@Override
	public String toString() {
		return "Menu [id=" + id + ", name=" + name + ", url=" + url + ", chidren=" + chidren + "]";
	}
}
