package com.chenjin.sys.dao.impl;

import com.chenjin.common.entity.DataGrid;
import com.chenjin.common.entity.PageRequest;
import com.chenjin.common.framework.dao.BaseDao;
import com.chenjin.sys.dao.IGroupUserDao;
import com.chenjin.sys.entity.Group;
import com.chenjin.sys.entity.GroupUser;
import com.chenjin.sys.entity.User;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class GroupUserDao extends BaseDao<GroupUser, Long>
        implements IGroupUserDao
{
    public DataGrid<GroupUser> queryByDept(PageRequest pageable, Group dept)
    {
        String hql = "from DeptUser t where t.dept=? ";
        return super.query(hql, pageable, new Object[] { dept });
    }

    public List<Map<String, Object>> getIndexTree(Long id)
    {
        String sql = "select c.TEXT as text,c.TXTEN as iconCls,c.URL as url,c.PID as pid,c.id,sum(a.if_write) as if_write from t_access a left join t_dept_user b on a.deptId = b.deptId left join t_webframe c on a.frameId = c.id where b.userId = ? group by c.id";
        return super.listBySql(sql, null, Map.class, new Object[] { id });
    }

    public DataGrid<Map<String, Object>> groupUserPage(PageRequest pageable, String orgType, Long id, String name)
    {
        String sql = "select a.*,b.id as bid from t_sys_user a left join t_sys_group_user b on (a.id = b.userId and b.groupId = ?) left join t_sys_group c on b.groupId = c.id and a.orgType = c.orgType  left join t_sys_organization d on a.organizationId=d.id where d.orgType=? and   a.name like ? and a.empid<>'admin' order by b.id,a.empId ";

        return super.findBySql(sql, pageable, Map.class, new Object[] { id, orgType, "%" + name + "%" });
    }

    public List<GroupUser> findByGroup(Long groupId)
    {
        String hql = "from GroupUser t where group.id=? ";
        return super.listByHql(hql, null, new Object[] { groupId });
    }

    public List<Map<String, Object>> groupUserPageSelect(Long id)
    {
        String sql = "select a.*  from t_sys_user a  join t_sys_group_user b on a.id = b.userId and b.groupId = ? where 1=1  order by b.id desc,a.id asc";
        return super.listBySql(sql, null, Map.class, new Object[] { id });
    }

    public List<GroupUser> findByNeedDel(Long groupId, User users)
    {
        String name = users.getName() == null ? "" : users.getName();
        String sql = "select a.* from t_sys_group_user a join t_sys_group b on a.groupId = b.id join t_sys_user c on a.userId = c.id  where b.id = ? and c.compCode like ? and c.name like ? ";
        return super.listBySql(sql, null, GroupUser.class, new Object[] { groupId, "%%", "%" + name + "%" });
    }

    public GroupUser findByKey(Long userId, Long groupId)
    {
        String hql = "from GroupUser t where t.group.id=? and t.user.id=? ";
        return (GroupUser)super.getByHql(hql, new Object[] { groupId, userId });
    }
}