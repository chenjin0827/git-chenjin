package com.chenjin.testPCQueue.sys.dao.impl;

import com.chenjin.testPCQueue.common.entity.DataGrid;
import com.chenjin.testPCQueue.common.entity.PageRequest;
import com.chenjin.testPCQueue.common.framework.dao.BaseDao;
import com.chenjin.testPCQueue.common.util.DateUtil;
import com.chenjin.testPCQueue.sys.dao.IUserDao;
import com.chenjin.testPCQueue.sys.entity.User;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends BaseDao<User, Long>
        implements IUserDao
{
    public void unlock()
    {
        super.executeHql("update User set isLocked=0,errTimes=0  where isDisabled=0 and isLocked=1 and modifyDate<?", new Object[] {
                DateUtil.getAddNumDate(13, -180) });
    }

    public List<Map<String, Object>> getUserGList(Long id, String searchkey)
    {
        String sql = "select a.*,if(isnull(b.id),0,1) as ischeck,b.id as deptuserid from t_user a left join t_dept_user b on a.id = b.userId  and  b.deptId = ? where a.empId like ?";

        return super.listBySql(sql, null, Map.class, new Object[] { id, "%" + searchkey + "%" });
    }

    public User getLogin(User user)
    {
        String hql = "from User t  left join fetch t.organization o where t.empId=? and t.pwd=? ";
        return (User)super.getByHql(hql, new Object[] { user.getEmpId(), user.getPwd() });
    }

    public User findByEmpId(String empId)
    {
        String hql = "from User t  left join fetch t.organization  where t.empId=? ";
        return (User)super.getByHql(hql, new Object[] { empId });
    }

    public DataGrid<Map<String, Object>> queryBySql(String mysql, PageRequest pageable)
    {
        return super.findBySql(mysql, pageable, Map.class, new Object[0]);
    }

    public List<Map<String, Object>> listBySql(String mysql)
    {
        return super.listBySql(mysql, null, Map.class, new Object[0]);
    }

    public int executeSql(String mysql)
    {
        return super.executeSql(mysql, new Object[0]);
    }

    public User getByCert(String caCert)
    {
        String hql = "from User t where t.clientCert=? ";
        return (User)super.getByHql(hql, new Object[] { caCert });
    }

    public User getByIdcode(String idCard)
    {
        String hql = "from User t where t.idcard=? ";
        return (User)super.getByHql(hql, new Object[] { idCard });
    }
}