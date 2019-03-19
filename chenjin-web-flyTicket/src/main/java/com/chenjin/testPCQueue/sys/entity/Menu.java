package com.chenjin.testPCQueue.sys.entity;

import com.chenjin.testPCQueue.common.entity.BasicEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="t_sys_menu")
public class Menu extends BasicEntity
{
    private static final long serialVersionUID = -8326251862447544117L;
    private Long parentId;
    private Long resourceId;
    private String name;
    private String sysId;
    private String orgType;
    private Integer sort;
    private String url;
    private String icon;
    private String state;
    private String description;
    private String treePath;

    @Id
    @SequenceGenerator(name="generator", sequenceName="t_sys_menu_s")
    @GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
    public Long getId()
    {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getParentId() {
        return this.parentId;
    }
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getResourceId()
    {
        return this.resourceId;
    }
    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }
    @Column(length=100)
    public String getName() { return this.name; }

    public void setName(String name) {
        this.name = name;
    }
    @Column(length=1)
    public String getOrgType() { return this.orgType; }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }
    public Integer getSort() {
        return this.sort;
    }
    public void setSort(Integer sort) {
        this.sort = sort;
    }
    @Column(length=100)
    public String getUrl() { return this.url; }

    public void setUrl(String url) {
        this.url = url;
    }
    @Column(length=50)
    public String getIcon() {
        return this.icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    @Column(length=10)
    public String getState() { return this.state; }

    public void setState(String state) {
        this.state = state;
    }
    @Column(length=100)
    public String getDescription() { return this.description; }

    public void setDescription(String description) {
        this.description = description;
    }
    @Column(length=50)
    public String getTreePath() {
        return this.treePath;
    }
    public void setTreePath(String treePath) {
        this.treePath = treePath;
    }
    @Column(length=20)
    public String getSysId() { return this.sysId; }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }
}