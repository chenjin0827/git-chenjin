package com.chenjin.testPCQueue.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.chenjin.testPCQueue.common.entity.BasicEntity;
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
@Table(name="t_set_attribute_item")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AttributeItem extends BasicEntity
{
    private static final long serialVersionUID = 5335250976894539542L;
    private Attribute attribute;
    private String field1;
    private String field2;
    private String field3;
    private String field4;

    @Id
    @SequenceGenerator(name="generator", sequenceName="t_set_attribute_item_s")
    @GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
    public Long getId()
    {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id; }
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="attributeId")
    public Attribute getAttribute() { return this.attribute; }

    public void setAttribute(Attribute attribute)
    {
        this.attribute = attribute;
    }
    @Column(length=50, nullable=false, updatable=false)
    public String getField1() { return this.field1; }

    public void setField1(String field1)
    {
        this.field1 = field1;
    }
    @Column(length=100)
    public String getField2() { return this.field2; }

    public void setField2(String field2)
    {
        this.field2 = field2;
    }
    @Column(length=500)
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