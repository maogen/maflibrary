package com.maf.base.permission;

/**
 * Created by mzg on 2016/5/23.
 */
public class UserPermission {
    /**
     * 权限的code
     */
    private String code;
    /**
     * 权限所能打开的view
     */
    private String view;
    /**
     * 权限的名字
     */
    private String name;
    /**
     * 是否有此权限
     */
    private String has;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHas() {
        return has;
    }

    public void setHas(String has) {
        this.has = has;
    }
}
