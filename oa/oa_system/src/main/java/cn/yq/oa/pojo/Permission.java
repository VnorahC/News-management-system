package cn.yq.oa.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Permission {
    private Integer id;

    private String name;

    private String type;
    //jackson 把对象转换成json时候，忽略此属性的转换
    @JsonIgnore
    private String url;

    private String percode;
    
    //自定义Jackson 工具在把对象转换成json字符串时候对应的 key的名称 
    @JsonProperty("pId")
    private Long parentid;

    private String sortstring;

    private Integer available;
    
    
    //此属性是给zTree使用的时候，默认让ztree的节点全部展开
    private boolean open = true;
    
    

    public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPercode() {
        return percode;
    }

    public void setPercode(String percode) {
        this.percode = percode;
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public String getSortstring() {
        return sortstring;
    }

    public void setSortstring(String sortstring) {
        this.sortstring = sortstring;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

	@Override
	public String toString() {
		return "Permission [id=" + id + ", name=" + name + ", type=" + type + ", url=" + url + ", percode=" + percode
				+ ", parentid=" + parentid + ", sortstring=" + sortstring + ", available=" + available + "]";
	}
    
}