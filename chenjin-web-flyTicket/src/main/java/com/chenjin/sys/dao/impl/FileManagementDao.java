package com.chenjin.sys.dao.impl;

import com.chenjin.common.framework.dao.BaseDao;
import com.chenjin.sys.dao.IFileManagementDao;
import com.chenjin.sys.entity.FileManagement;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class FileManagementDao extends BaseDao<FileManagement, Long>
        implements IFileManagementDao
{
    public FileManagement queryByUrl(String fileURL)
    {
        String hql = " from FileManagement t where t.fileURL = ?";
        return (FileManagement)super.getByHql(hql, new Object[] { fileURL });
    }

    public List<FileManagement> queryByPath(String path)
    {
        String pathLK = path + "%";
        String hql = " from FileManagement t where t.fileURL like ?";
        return super.listByHql(hql, null, new Object[] { pathLK });
    }

    public List<FileManagement> findByKeyFlag(String keyFlag)
    {
        String hql = " from FileManagement t where t.keyFlag = ?";
        return super.listByHql(hql, null, new Object[] { keyFlag });
    }
}