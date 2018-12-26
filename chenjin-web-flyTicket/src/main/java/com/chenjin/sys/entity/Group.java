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
@Table(name="t_sys_group")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Group extends BasicEntity
{
    private static final long serialVersionUID = -8326251862447544117L;
    private String orgType;
    private Organization organization;
    private String name;
    private Long parentId;

    @Id
    @SequenceGenerator(name="generator", sequenceName="t_sys_group_s")
    @GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
    public Long getId()
    {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Column(length=50)
    public String getName() { return this.name; }

    public void setName(String name)
    {
        this.name = name;
    }
    @Column(length=1)
    public String getOrgType() {
        return this.orgType;
    }
    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="organizationId")
    public Organization getOrganization() { return this.organization; }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
    public Long getParentId() {
        return this.parentId;
    }
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}