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
@Table(name="t_sys_actionLog")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ActionLog extends BasicEntity
{
    private static final long serialVersionUID = -8326251862447544117L;
    private String appId;
    private String action;
    private String keyId;
    private String memo;

    @Id
    @SequenceGenerator(name="generator", sequenceName="t_sys_actionLog_s")
    @GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
    public Long getId()
    {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Column(length=50)
    public String getAppId() { return this.appId; }

    public void setAppId(String appId) {
        this.appId = appId;
    }
    @Column(length=20)
    public String getAction() { return this.action; }

    public void setAction(String action) {
        this.action = action;
    }
    @Column(length=50)
    public String getKeyId() { return this.keyId; }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }
    @Column(length=255)
    public String getMemo() {
        return this.memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
}