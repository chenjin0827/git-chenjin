package com.chenjin.testPCQueue.sys.service;

import com.chenjin.testPCQueue.common.entity.PageRequest;
import com.chenjin.testPCQueue.common.framework.annotation.ProjectCodeFlag;
import com.chenjin.testPCQueue.common.framework.service.IBaseService;
import com.chenjin.testPCQueue.sys.entity.Attribute;

public abstract interface IAttributeService extends IBaseService<Attribute, Long>
{
    public abstract Attribute queryByAttributeNo(@ProjectCodeFlag String paramString1, PageRequest paramPageRequest, String paramString2);
}