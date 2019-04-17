package cn.yq.oa.pojo;

import java.util.Arrays;

public class Role {
    private Integer roleId;

    private String name;

    private String remark;

    private Integer available;
    
    
    private Integer[] permissionIds;
    
    

    public Integer[] getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(Integer[] permissionIds) {
		this.permissionIds = permissionIds;
	}

	public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", name=" + name + ", remark=" + remark + ", available=" + available
				+ ", permissionIds=" + Arrays.toString(permissionIds) + "]";
	}
    
    
}