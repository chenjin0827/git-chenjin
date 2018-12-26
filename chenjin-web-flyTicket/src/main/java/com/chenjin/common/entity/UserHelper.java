package com.chenjin.common.entity;

import javax.persistence.Transient;

public class UserHelper extends BaseEntity
{
    private static final long serialVersionUID = -8326251862447544117L;
    private String projectCode = "";
    private String sysUser;
    private String sysName;

    public UserHelper(String projectCode)
    {
        this.projectCode = projectCode;
        this.sysUser = "system";
        this.sysName = "系统管理员";
    }
    public UserHelper() {
        this.projectCode = "main";
        this.sysUser = "system";
        this.sysName = "系统管理员";
    }

    public String getProjectCode()
    {
        return this.projectCode;
    }
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }
    @Transient
    public String getSysUser() { return this.sysUser; }

    public void setSysUser(String sysUser) {
        this.sysUser = sysUser;
    }
    @Transient
    public String getSysName() { return this.sysName; }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }
}