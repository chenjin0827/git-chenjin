package com.chenjin.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.chenjin.common.entity.BasicEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="t_set_attribute")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Attribute extends BasicEntity
{
    private static final long serialVersionUID = 8999057192563600418L;
    private String attributeNo;
    private String name;
    private String description;
    private String owner;
    private String field1;
    private String field2;
    private String field3;
    private String field4;

    @Id
    @SequenceGenerator(name="generator", sequenceName="t_set_attribute_s")
    @GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
    public Long getId()
    {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Column(length=20, unique=true, nullable=false, updatable=false)
    public String getAttributeNo() { return this.attributeNo; }

    public void setAttributeNo(String attributeNo)
    {
        this.attributeNo = attributeNo;
    }
    @Column(length=20, nullable=false)
    public String getName() { return this.name; }

    public void setName(String name)
    {
        this.name = name;
    }
    @Column(length=200)
    public String getDescription() { return this.description; }

    public void setDescription(String description)
    {
        this.description = description;
    }
    @Column(length=20)
    public String getOwner() { return this.owner; }

    public void setOwner(String owner)
    {
        this.owner = owner;
    }
    @Column(length=50)
    public String getField1() { return this.field1; }

    public void setField1(String field1)
    {
        this.field1 = field1;
    }
    @Column(length=50)
    public String getField2() { return this.field2; }

    public void setField2(String field2)
    {
        this.field2 = field2;
    }
    @Column(length=50)
    public String getField3() { return this.field3; }

    public void setField3(String field3)
    {
        this.field3 = field3;
    }
    @Column(length=50)
    public String getField4() { return this.field4; }

    public void setField4(String field4)
    {
        this.field4 = field4;
    }
}