package com.maf.base.permission;


import java.util.List;

/**
 * Created by mzgkq on 16/4/15.
 * 权限组，相似权限在一组
 */
public class PermissionGroup {
    private String group;
    private String name;
    private List<PermissionList> list;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PermissionList> getList() {
        return list;
    }

    public void setList(List<PermissionList> list) {
        this.list = list;
    }
}
