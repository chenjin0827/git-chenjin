package com.chenjin.sys.service;

import com.chenjin.common.framework.annotation.ProjectCodeFlag;
import com.chenjin.common.framework.service.IBaseService;
import com.chenjin.sys.entity.FileManagement;
import java.util.List;

public abstract interface IFileManagementService extends IBaseService<FileManagement, Long>
{
    public abstract FileManagement queryByUrl(@ProjectCodeFlag String paramString1, String paramString2);

    public abstract List<FileManagement> queryByPath(@ProjectCodeFlag String paramString1, String paramString2);

    public abstract List<FileManagement> findByKeyFlag(@ProjectCodeFlag String paramString1, String paramString2);
}