package com.chenjin.sys.service;

import com.chenjin.common.entity.PageRequest;
import com.chenjin.common.framework.annotation.ProjectCodeFlag;
import com.chenjin.common.framework.service.IBaseService;
import com.chenjin.sys.entity.Attribute;

public abstract interface IAttributeService extends IBaseService<Attribute, Long>
{
    public abstract Attribute queryByAttributeNo(@ProjectCodeFlag String paramString1, PageRequest paramPageRequest, String paramString2);
}