//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

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
@Table(
        name = "t_sys_organization"
)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Organization extends BasicEntity {
    private static final long serialVersionUID = -8326251862447544117L;
    private Long orgId;
    private String orgCode;
    private String orgName;
    private Integer orgType;

    public Organization() {
    }

    @Id
    @SequenceGenerator(
            name = "generator",
            sequenceName = "t_sys_organization_s"
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "generator"
    )
    public Long getId() {
        return super.id;
    }

    @Column(
            length = 2
    )
    public Integer getOrgType() {
        return this.orgType;
    }

    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }

    @Column(
            length = 20
    )
    public Long getOrgId() {
        return this.orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    @Column(
            length = 50
    )
    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    @Column(
            length = 100
    )
    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
