package com.chenjin.common.ca;

import java.io.File;
import java.util.Map;

public abstract interface ICompanyCA
{
    public abstract Map<String, String> checkSign(String paramString1, String paramString2, String paramString3, String paramString4);

    public abstract File addSignLocal(byte[] paramArrayOfByte, String paramString1, String paramString2);

    public abstract String addSignWeb(String paramString1, String paramString2, String paramString3);
}