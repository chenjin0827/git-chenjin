package com.chenjin.sys.service;

import com.chenjin.common.entity.DataGrid;
import com.chenjin.common.entity.PageRequest;
import com.chenjin.common.framework.annotation.ProjectCodeFlag;
import com.chenjin.common.framework.service.IBaseService;
import com.chenjin.sys.entity.User;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public abstract interface IUserService extends IBaseService<User, Long>
{
    public abstract void unlock();

    public abstract User getLogin(@ProjectCodeFlag String paramString, User paramUser);

    public abstract void clear();

    public abstract User findByEmpId(@ProjectCodeFlag String paramString1, String paramString2);

    public abstract String doExcelH(MultipartFile paramMultipartFile)
            throws IOException;

    public abstract DataGrid<Map<String, Object>> queryBySql(@ProjectCodeFlag String paramString1, String paramString2, PageRequest paramPageRequest);

    public abstract List<Map<String, Object>> listBySql(@ProjectCodeFlag String paramString1, String paramString2);

    public abstract int executeSql(@ProjectCodeFlag String paramString1, String paramString2);

    public abstract User getByCert(@ProjectCodeFlag String paramString1, String paramString2);

    public abstract void saveSQL(@ProjectCodeFlag String paramString1, String paramString2, List paramList);

    public abstract User getByIdcode(String paramString);

    public abstract void registerSSO(User paramUser1, User paramUser2);
}