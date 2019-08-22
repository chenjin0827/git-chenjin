package com.chenjin.myproject.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 3362815480760173870L;

    /**
     * "创建日期"属性名称
     */
    public static final String CREATE_DATE_PROPERTY_NAME = "createDate";

    /**
     * "修改日期"属性名称
     */
    public static final String MODIFY_DATE_PROPERTY_NAME = "modifyDate";

    /**
     * "创建人"属性名称
     */
    public static final String CREATE_USER_PROPERTY_NAME = "createUser";

    /**
     * "修改人"属性名称
     */
    public static final String MODIFY_USER_PROPERTY_NAME = "modifyUser";

    /**
     * "创建人名称"属性名称
     */
    public static final String CREATE_NAME_PROPERTY_NAME = "createName";

    /**
     * "修改人名称"属性名称
     */
    public static final String MODIFY_NAME_PROPERTY_NAME = "modifyName";


    /**
     * 主键
     */
    protected Long id;
    /**
     * 新增日期
     */
    private Date createDate;

    /**
     * 修改日期
     */
    private Date modifyDate;

    /**
     * 新增人
     */
    private String createUser;

    /**
     * 修改人
     */
    private String modifyUser;

    /**
     * 新增人名称
     */
    private String createName;

    /**
     * 修改人名称
     */
    private String modifyName;

    /**
     * 临时String字段
     */
    private String segmentStr = "";

    /**
     * 临时Long字段
     */
    private Long segmentLong;

    /**
     * 临时BigDecimal字段
     */
    private BigDecimal segmentBD = new BigDecimal(0);
    /**
     * 临时object字段
     */
    private Object segment;

    /**
     * 临时List字段
     */
    private List<?> segmentList;

    /**
     * 临时Map字段
     */
    private Map<String, Object> segmentMap;

    @Transient
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Column(updatable = false)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Column(length = 20, updatable = false)
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Column(length = 20)
    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    @Column(length = 50)
    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    @Column(length = 50)
    public String getModifyName() {
        return modifyName;
    }

    public void setModifyName(String modifyName) {
        this.modifyName = modifyName;
    }

    @Transient
    public String getSegmentStr() {
        return segmentStr;
    }

    public void setSegmentStr(String segmentStr) {
        this.segmentStr = segmentStr;
    }

    @Transient
    public Long getSegmentLong() {
        return segmentLong;
    }

    public void setSegmentLong(Long segmentLong) {
        this.segmentLong = segmentLong;
    }

    @Transient
    public BigDecimal getSegmentBD() {
        return segmentBD;
    }

    public void setSegmentBD(BigDecimal segmentBD) {
        this.segmentBD = segmentBD;
    }

    @Transient
    public Object getSegment() {
        return segment;
    }

    public void setSegment(Object segment) {
        this.segment = segment;
    }

    @Transient
    public List<?> getSegmentList() {
        return segmentList;
    }

    public void setSegmentList(List<?> segmentList) {
        this.segmentList = segmentList;
    }

    @Transient
    public Map<String, Object> getSegmentMap() {
        return segmentMap;
    }

    public void setSegmentMap(Map<String, Object> segmentMap) {
        this.segmentMap = segmentMap;
    }


}
