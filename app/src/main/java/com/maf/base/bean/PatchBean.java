package com.maf.base.bean;

/**
 * 修复补丁信息
 *
 * @author mzg
 */
public class PatchBean {
    /**
     * 是否有新补丁
     */
    private boolean hasNewPatch;
    /**
     * 新补丁的路径
     */
    private String patchPath;

    public boolean isHasNewPatch() {
        return hasNewPatch;
    }

    public void setHasNewPatch(boolean hasNewPatch) {
        this.hasNewPatch = hasNewPatch;
    }

    public String getPatchPath() {
        return patchPath;
    }

    public void setPatchPath(String patchPath) {
        this.patchPath = patchPath;
    }
}
