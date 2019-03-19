package com.chenjin.testPCQueue.sys.entity;

import com.chenjin.testPCQueue.common.entity.BasicEntity;
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
@Table(name="t_sys_permission")
public class Permission extends BasicEntity
{
    private static final long serialVersionUID = -8326251862447544117L;
    private Group group;
    private Long resourceId;

    @Id
    @SequenceGenerator(name="generator", sequenceName="t_sys_permission_s")
    @GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
    public Long getId()
    {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="groupId")
    public Group getGroup() {
        return this.group;
    }
    public void setGroup(Group group) {
        this.group = group;
    }

    public Long getResourceId() {
        return this.resourceId;
    }
    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }
}