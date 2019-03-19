package com.chenjin.testPCQueue.sys.service.realm;

import org.apache.shiro.authc.UsernamePasswordToken;

public class ShiroMyToken extends UsernamePasswordToken
{
    private static final long serialVersionUID = 5898441540965086534L;
    private String empId;
    private String pwd;
    private String vcode;
    private String type;
    private boolean isAuth = true;
    private String UserCert;
    private String UserSignedData;
    private String userIP;
    private String projectCode;

    public ShiroMyToken(String username, String password, boolean isAuth)
    {
        new ShiroMyToken(username, password, null, null, isAuth, null, null, null, null);
    }

    public ShiroMyToken(String username, String password, String vcode, String type, boolean isAuth, String UserCert, String UserSignedData, String userIP, String projectCode) {
        super(username, password);
        this.empId = username;
        this.pwd = password;
        this.vcode = vcode;
        this.type = type;
        this.isAuth = isAuth;
        this.UserCert = UserCert;
        this.UserSignedData = UserSignedData;
        this.userIP = userIP;
        this.projectCode = projectCode;
        System.out.println("ShiroMyToken=" + getUsername() + "-" + getEmpId());
    }

    public String getEmpId()
    {
        return this.empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getPwd() {
        return this.pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVcode() {
        return this.vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public boolean isAuth() {
        return this.isAuth;
    }

    public void setAuth(boolean isAuth) {
        this.isAuth = isAuth;
    }

    public String getUserCert() {
        return this.UserCert;
    }

    public void setUserCert(String UserCert) {
        this.UserCert = UserCert;
    }

    public String getUserSignedData() {
        return this.UserSignedData;
    }

    public void setUserSignedData(String userSignedData) {
        this.UserSignedData = userSignedData;
    }

    public String getUserIP() {
        return this.userIP;
    }

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }

    public String getProjectCode() {
        return this.projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }
}