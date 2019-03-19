//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.chenjin.testPCQueue.sys.entity;

import com.chenjin.testPCQueue.common.entity.BasicEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.beans.Transient;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(
        name = "t_sys_user"
)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends BasicEntity {
    private static final long serialVersionUID = -8326251862447544117L;
    private String empId;
    private String name;
    private String idcard;
    private String mail;
    private String cell;
    private String ext;
    private String title;
    private String pwd;
    private String isAdmin;
    private String status;
    private Integer isLocked = Integer.valueOf(0);
    private Integer errTimes = Integer.valueOf(0);
    private Integer isDisabled = Integer.valueOf(0);
    private Organization organization;
    private String clientCert;
    private Integer isSelected = Integer.valueOf(0);
    private Date lastDate;
    private Date passwdDate;
    private String currentSessionId;

    public User() {
    }

    @Id
    @SequenceGenerator(
            name = "generator",
            sequenceName = "t_sys_user_s"
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "generator"
    )
    public Long getId() {
        return super.id;
    }

    @Column(
            length = 20
    )
    public String getEmpId() {
        return this.empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    @Column(
            length = 50
    )
    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Column(
            length = 20
    )
    public String getCell() {
        return this.cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    @Column(
            length = 20
    )
    public String getExt() {
        return this.ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    @Column(
            length = 50
    )
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(
            length = 50
    )
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(
            length = 50
    )
    public String getPwd() {
        return this.pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Column(
            length = 10
    )
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(
            length = 10
    )
    public String getIsAdmin() {
        return this.isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Integer getIsLocked() {
        return this.isLocked;
    }

    public void setIsLocked(Integer isLocked) {
        this.isLocked = isLocked;
    }

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "organizationId",
            nullable = false
    )
    public Organization getOrganization() {
        return this.organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Integer getErrTimes() {
        return this.errTimes;
    }

    public void setErrTimes(Integer errTimes) {
        this.errTimes = errTimes;
    }

    public Integer getIsDisabled() {
        return this.isDisabled;
    }

    public void setIsDisabled(Integer isDisabled) {
        this.isDisabled = isDisabled;
    }

    public String getClientCert() {
        return this.clientCert;
    }

    public void setClientCert(String clientCert) {
        this.clientCert = clientCert;
    }

    @Transient
    public Integer getIsSelected() {
        return this.isSelected;
    }

    public void setIsSelected(Integer isSelected) {
        this.isSelected = isSelected;
    }

    public String getIdcard() {
        return this.idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public Date getLastDate() {
        return this.lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public Date getPasswdDate() {
        return this.passwdDate;
    }

    public void setPasswdDate(Date passwdDate) {
        this.passwdDate = passwdDate;
    }

    public String getCurrentSessionId() {
        return this.currentSessionId;
    }

    public void setCurrentSessionId(String currentSessionId) {
        this.currentSessionId = currentSessionId;
    }
}
