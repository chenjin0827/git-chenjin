package com.chenjin.sys.dao;

import com.chenjin.common.framework.dao.IBaseDao;
import com.chenjin.sys.entity.FileManagement;
import java.util.List;

public abstract interface IFileManagementDao extends IBaseDao<FileManagement, Long>
{
    public abstract FileManagement queryByUrl(String paramString);

    public abstract List<FileManagement> queryByPath(String paramString);

    public abstract List<FileManagement> findByKeyFlag(String paramString);
}