package com.chenjin.sys.service.impl;

import com.chenjin.common.framework.service.BaseService;
import com.chenjin.sys.dao.IFileManagementDao;
import com.chenjin.sys.entity.FileManagement;
import com.chenjin.sys.service.IFileManagementService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class FileManagementService extends BaseService<FileManagement, Long>
        implements IFileManagementService
{
    private IFileManagementDao fileManagementDao;

    public IFileManagementDao getFileManagementDao()
    {
        return this.fileManagementDao;
    }
    @Resource
    public void setFileManagementDao(IFileManagementDao fileManagementDao) {
        this.fileManagementDao = fileManagementDao;
        super.setBaseDao(fileManagementDao);
    }

    @Transactional(readOnly=true)
    public FileManagement queryByUrl(String projectCode, String fileURL) {
        return this.fileManagementDao.queryByUrl(fileURL);
    }

    @Transactional(readOnly=true)
    public List<FileManagement> queryByPath(String projectCode, String path) {
        return this.fileManagementDao.queryByPath(path);
    }

    public List<FileManagement> findByKeyFlag(String projectCode, String keyFlag)
    {
        return this.fileManagementDao.findByKeyFlag(keyFlag);
    }
}