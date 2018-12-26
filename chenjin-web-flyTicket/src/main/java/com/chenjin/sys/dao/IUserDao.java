package com.chenjin.sys.dao;

import com.chenjin.common.entity.DataGrid;
import com.chenjin.common.entity.PageRequest;
import com.chenjin.common.framework.dao.IBaseDao;
import com.chenjin.sys.entity.User;
import java.util.List;
import java.util.Map;

public abstract interface IUserDao extends IBaseDao<User, Long>
{
    public abstract void unlock();

    public abstract List<Map<String, Object>> getUserGList(Long paramLong, String paramString);

    public abstract User getLogin(User paramUser);

    public abstract User findByEmpId(String paramString);

    public abstract DataGrid<Map<String, Object>> queryBySql(String paramString, PageRequest paramPageRequest);

    public abstract int executeSql(String paramString);

    public abstract User getByCert(String paramString);

    public abstract List<Map<String, Object>> listBySql(String paramString);

    public abstract User getByIdcode(String paramString);
}