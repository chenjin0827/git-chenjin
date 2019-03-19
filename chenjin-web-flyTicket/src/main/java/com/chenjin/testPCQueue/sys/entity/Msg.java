package com.chenjin.testPCQueue.sys.entity;

import com.chenjin.testPCQueue.common.entity.BasicEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="t_sys_msg")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Msg extends BasicEntity
{
    private static final long serialVersionUID = -8326251862447544117L;
    private String ids;
    private String caty;
    private Long ownerId;
    private String ownerName;
    private Long organizationId;
    private String orgName;
    private String title;
    private String body;
    private String attach;

    @Id
    @SequenceGenerator(name="generator", sequenceName="t_sys_msg_s")
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
    @Column(length=1)
    public String getCaty() { return this.caty; }

    public void setCaty(String caty) {
        this.caty = caty;
    }

    public Long getOwnerId() {
        return this.ownerId;
    }
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
    public String getOwnerName() {
        return this.ownerName;
    }
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
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
    @Column(length=100)
    public String getTitle() { return this.title; }

    public void setTitle(String title) {
        this.title = title;
    }
    @Column(length=500)
    public String getBody() { return this.body; }

    public void setBody(String body) {
        this.body = body;
    }
    @Column(length=100)
    public String getAttach() { return this.attach; }

    public void setAttach(String attach) {
        this.attach = attach;
    }
}