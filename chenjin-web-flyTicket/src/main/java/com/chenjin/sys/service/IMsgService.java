package com.chenjin.sys.service;

import com.chenjin.common.entity.DataGrid;
import com.chenjin.common.entity.PageRequest;
import com.chenjin.common.framework.annotation.ProjectCodeFlag;
import com.chenjin.common.framework.service.IBaseService;
import com.chenjin.sys.entity.Msg;
import com.chenjin.sys.entity.User;

public abstract interface IMsgService extends IBaseService<Msg, Long>
{
    public abstract void sendBySYSToOrg(@ProjectCodeFlag String paramString, Msg paramMsg, Long paramLong)
            throws Exception;

    public abstract void sendBySYS(@ProjectCodeFlag String paramString, Msg paramMsg, Long[] paramArrayOfLong);

    public abstract DataGrid<Msg> getDraftMsg(@ProjectCodeFlag String paramString, Msg paramMsg, PageRequest paramPageRequest);

    public abstract void sendFromDraft(@ProjectCodeFlag String paramString1, Msg paramMsg, String paramString2, User paramUser);
}