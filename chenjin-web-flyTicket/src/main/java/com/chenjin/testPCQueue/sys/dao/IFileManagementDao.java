package com.chenjin.testPCQueue.sys.dao;

import com.chenjin.testPCQueue.common.framework.dao.IBaseDao;
import com.chenjin.testPCQueue.sys.entity.FileManagement;

import java.util.List;

public abstract interface IFileManagementDao extends IBaseDao<FileManagement, Long>
{
    public abstract FileManagement queryByUrl(String paramString);

    public abstract List<FileManagement> queryByPath(String paramString);

    public abstract List<FileManagement> findByKeyFlag(String paramString);
}