package com.chenjin.common.web;

import com.chenjin.common.web.util.DateTime;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang.time.DateUtils;

public class DateConvertEditor extends PropertyEditorSupport
{
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private boolean emptyAsNull;
    private String dateFormat = "yyyy-MM-dd HH:mm:ss";

    public DateConvertEditor(boolean emptyAsNull)
    {
        this.emptyAsNull = emptyAsNull;
    }

    public DateConvertEditor(boolean emptyAsNull, String dateFormat)
    {
        this.emptyAsNull = emptyAsNull;
        this.dateFormat = dateFormat;
    }

    public String getAsText()
    {
        Date value = (Date)getValue();
        return value != null ? new SimpleDateFormat(this.dateFormat).format(value) : "";
    }

    public void setAsText(String text)
    {
        if (text == null) {
            setValue(null);
        } else {
            String value = text.trim();
            if ((this.emptyAsNull) && ("".equals(value)))
                setValue(null);
            else
                try {
                    setValue(DateUtils.parseDate(value, DateTime.DATE_PATTERNS));
                } catch (ParseException e) {
                    setValue(null);
                }
        }
    }
}