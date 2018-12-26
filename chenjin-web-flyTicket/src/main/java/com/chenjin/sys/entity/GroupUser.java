package com.chenjin.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.chenjin.common.entity.BasicEntity;
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
@Table(name="t_sys_group_user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GroupUser extends BasicEntity
{
    private static final long serialVersionUID = -8326251862447544117L;
    private Group group;

    @JsonIgnore
    private User user;

    @Id
    @SequenceGenerator(name="generator", sequenceName="t_sys_group_user_s")
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
    public Group getGroup() { return this.group; }

    public void setGroup(Group group)
    {
        this.group = group;
    }
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="userId")
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}