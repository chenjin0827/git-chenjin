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
@Table(name="t_sys_resource")
public class Resource extends BasicEntity
{
    private static final long serialVersionUID = -8326251862447544117L;
    private Long parentId;
    private String name;
    private String type;
    private Integer sort;
    private String url;
    private String permCode;
    private String icon;
    private String state;
    private String description;
    private String treePath;

    @Id
    @SequenceGenerator(name="generator", sequenceName="t_sys_resource_s")
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
    @Column(length=100)
    public String getName() { return this.name; }

    public void setName(String name) {
        this.name = name;
    }
    @Column(length=1)
    public String getType() { return this.type; }

    public void setType(String type) {
        this.type = type;
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
    public String getPermCode() { return this.permCode; }

    public void setPermCode(String permCode) {
        this.permCode = permCode;
    }
    @Column(length=50)
    public String getIcon() { return this.icon; }

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
}