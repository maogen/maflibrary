package com.maf.base.permission;

import java.util.List;

/**
 * Created by mzgkq on 16/4/15.
 * 同类权限列表，如果没有字权限，那本身就代表一个权限
 */
public class PermissionList extends UserPermission {
    private List<UserPermission> subPermission;

    public List<UserPermission> getSubPermission() {
        return subPermission;
    }

    public void setSubPermission(List<UserPermission> subPermission) {
        this.subPermission = subPermission;
    }
}
