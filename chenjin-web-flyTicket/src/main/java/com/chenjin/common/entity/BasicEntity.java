//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.chenjin.common.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class BasicEntity extends Entity {
    private static final long serialVersionUID = 2827551183115556603L;
    public static final String CREATE_DATE_PROPERTY_NAME = "createDate";
    public static final String MODIFY_DATE_PROPERTY_NAME = "modifyDate";
    public static final String CREATE_USER_PROPERTY_NAME = "createUser";
    public static final String MODIFY_USER_PROPERTY_NAME = "modifyUser";
    public static final String CREATE_NAME_PROPERTY_NAME = "createName";
    public static final String MODIFY_NAME_PROPERTY_NAME = "modifyName";
    protected Long id;
    private Date createDate;
    private Date modifyDate;
    private String createUser;
    private String modifyUser;
    private String createName;
    private String modifyName;
    private String projectCode;
    private String segmentStr = "";
    private Long segmentLong;
    private BigDecimal segmentBD = new BigDecimal(0);
    private Object segment;
    private List<?> segmentList;
    private Map<String, Object> segmentMap;
    private String text = "";

    public BasicEntity() {
    }

    @Transient
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(
            length = 20
    )
    public String getProjectCode() {
        return this.projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    @Column(
            updatable = false
    )
    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return this.modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Column(
            length = 20,
            updatable = false
    )
    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Column(
            length = 20
    )
    public String getModifyUser() {
        return this.modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    @Column(
            length = 50
    )
    public String getCreateName() {
        return this.createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    @Column(
            length = 50
    )
    public String getModifyName() {
        return this.modifyName;
    }

    public void setModifyName(String modifyName) {
        this.modifyName = modifyName;
    }

    @Transient
    public String getSegmentStr() {
        return this.segmentStr;
    }

    public void setSegmentStr(String segmentStr) {
        this.segmentStr = segmentStr;
    }

    @Transient
    public Long getSegmentLong() {
        return this.segmentLong;
    }

    public void setSegmentLong(Long segmentLong) {
        this.segmentLong = segmentLong;
    }

    @Transient
    public BigDecimal getSegmentBD() {
        return this.segmentBD;
    }

    public void setSegmentBD(BigDecimal segmentBD) {
        this.segmentBD = segmentBD;
    }

    @Transient
    public Object getSegment() {
        return this.segment;
    }

    public void setSegment(Object segment) {
        this.segment = segment;
    }

    @Transient
    public List<?> getSegmentList() {
        return this.segmentList;
    }

    public void setSegmentList(List<?> segmentList) {
        this.segmentList = segmentList;
    }

    @Transient
    public Map<String, Object> getSegmentMap() {
        return this.segmentMap;
    }

    public void setSegmentMap(Map<String, Object> segmentMap) {
        this.segmentMap = segmentMap;
    }

    @Transient
    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean equals(Object obj) {
        System.out.println("======" + this.getId());
        if (obj == null) {
            return false;
        } else if (this == obj) {
            return true;
        } else if (!BaseEntity.class.isAssignableFrom(obj.getClass())) {
            return false;
        } else {
            BaseEntity other = (BaseEntity)obj;
            return this.getId() != null ? this.getId().equals(other.getId()) : false;
        }
    }
}
