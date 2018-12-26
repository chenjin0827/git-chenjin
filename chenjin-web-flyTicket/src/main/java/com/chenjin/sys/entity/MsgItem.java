package com.chenjin.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.chenjin.common.entity.BasicEntity;
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
@Table(name="t_sys_msg_item")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MsgItem extends BasicEntity
{
    private static final long serialVersionUID = -8326251862447544117L;
    private String ids;
    private Msg msg;
    private Long userId;
    private String userName;
    private Long organizationId;
    private String orgName;
    private String sender;

    @Id
    @SequenceGenerator(name="generator", sequenceName="t_sys_msg_item_s")
    @GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
    public Long getId()
    {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Column(length=2)
    public String getIds() { return this.ids; }

    public void setIds(String ids) {
        this.ids = ids;
    }
    @Column(length=20)
    public String getSender() { return this.sender; }

    public void setSender(String sender) {
        this.sender = sender; }
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="msgId")
    public Msg getMsg() { return this.msg; }

    public void setMsg(Msg msg) {
        this.msg = msg;
    }
    public Long getUserId() {
        return this.userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Long getOrganizationId() {
        return this.organizationId;
    }
    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }
    public String getOrgName() {
        return this.orgName;
    }
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}