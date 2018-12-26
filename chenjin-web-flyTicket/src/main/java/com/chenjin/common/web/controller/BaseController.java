package com.chenjin.common.web.controller;

import com.chenjin.common.web.DateConvertEditor;
import com.chenjin.common.web.StringEscapeEditor;
import java.util.Date;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.NativeWebRequest;

public abstract class BaseController
{
    @InitBinder
    protected final void initBinder(WebDataBinder binder, NativeWebRequest webRequest)
    {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(Date.class, new DateConvertEditor(true));

        binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false));

        init(binder);
    }

    protected abstract void init(WebDataBinder paramWebDataBinder);
}