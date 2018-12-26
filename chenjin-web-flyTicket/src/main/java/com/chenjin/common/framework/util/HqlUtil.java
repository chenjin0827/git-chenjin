package com.chenjin.common.framework.util;

import com.chenjin.common.entity.PageParam;
import com.chenjin.common.entity.Sort;
import com.chenjin.common.entity.Sort.Direction;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;

public class HqlUtil
{
    private static String DBTYPE = "ORACLE";
    private static String canNull = "0";
    private Map<String, Object> params = new HashMap();
    private StringBuffer hql = new StringBuffer();

    public String getDBTYPE() {
        return DBTYPE;
    }

    public void setDBTYPE(String dbType) {
        DBTYPE = dbType;
    }

    public String getCanNull() {
        return canNull;
    }

    public void setCanNull(String cannull) {
        canNull = cannull;
    }

    private String getSqlOperator(String operator)
    {
        String o = operator.trim().toUpperCase();
        if (o.equals("EQ"))
            return " = ";
        if (o.equals("NE"))
            return " != ";
        if (o.equals("LT"))
            return " < ";
        if (o.equals("GT"))
            return " > ";
        if (o.equals("LE"))
            return " <= ";
        if (o.equals("GE"))
            return " >= ";
        if ((o.equals("LK")) || (o.equals("RLK")) || (o.equals("LLK")))
            return " like ";
        if (o.equals("IS"))
            return " is null ";
        if (o.equals("NOT")) {
            return " is not null ";
        }
        return "";
    }

    private Object getObjValue(String valueType, String operator, Object obj)
    {
        String value = "";
        if ((obj instanceof String)) {
            value = (String)obj;
            if (valueType.equals("SU"))
                value = value.toUpperCase();
        } else {
            return obj;
        }

        if ((valueType.equals("S")) || (valueType.equals("PY")) || (valueType.equals("FL"))) {
            if (operator.equals("LK"))
                value = "%%" + value + "%%";
            else if (operator.equals("RLK"))
                value = value + "%%";
            else if (operator.equals("LLK")) {
                value = "%%" + value;
            }
            return value;
        }if (valueType.equals("L"))
        return Long.valueOf(Long.parseLong(value));
        if (valueType.equals("I"))
            return Integer.valueOf(Integer.parseInt(value));
        if (valueType.equals("D")) {
            try {
                return DateUtils.parseDate(value, new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm" });
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            if (valueType.equals("ST"))
                return Short.valueOf(Short.parseShort(value));
            if (valueType.equals("BD"))
                return BigDecimal.valueOf(Long.parseLong(value));
            if (valueType.equals("FT"))
                return Float.valueOf(Float.parseFloat(value));
            if (valueType.equals("B"))
                return Boolean.valueOf(Boolean.parseBoolean(value));
        }
        return null;
    }

    public void addFilter(Map<String, Object> query)
    {
        if ((query != null) && (!query.isEmpty())) {
            for (String key : query.keySet()) {
                Object obj = query.get(key);
                String[] keyArr = key.split("_");
                String columnName = keyArr[0].replaceAll("#", ".");

                if (obj == null) {
                    if (canNull.equals("1")) {
                        this.hql.append(" and " + columnName + " is null ");
                        continue;
                    }

                }

                if (((obj instanceof String)) && (StringUtils.isEmpty((String)obj))) {
                    if (keyArr[1].equals("SB")) {
                        keyArr[1] = "S";
                    }

                }

                if (key.indexOf("_M_") == -1)
                {
                    if ((DBTYPE.equals("ORACLE")) && (keyArr[1].equals("D"))) {
                        System.out.println("进入oracle日期格式特殊处理=");
                        columnName = "to_char(" + columnName + ",'yyyy-mm-dd')";
                        keyArr[1] = "S";
                    } else if (keyArr[1].equals("SU")) {
                        columnName = "upper(" + columnName + ")";
                    } else if (keyArr[1].equals("PY")) {
                        columnName = "to_pinyin(" + columnName + ")";
                        obj = obj.toString().toLowerCase();
                    } else if (keyArr[1].equals("FL")) {
                        columnName = "to_firstLetter(" + columnName + ")";
                        obj = obj.toString().toLowerCase();
                    }

                    String operator = getSqlOperator(keyArr[2]);
                    if ("IN".equals(keyArr[2])) {
                        String inVal = getInVal(keyArr[1], obj.toString());
                        this.hql.append(" and " + columnName + " in " + inVal + " ");
                    } else if (!"NULL".equals(keyArr[1])) {
                        String placeholder = UUID.randomUUID().toString().replace("-", "");
                        this.hql.append(" and " + columnName + " " + operator + " :c" + placeholder + " ");
                        this.params.put("c" + placeholder, getObjValue(keyArr[1], keyArr[2], obj));
                    }
                    else {
                        this.hql.append(" and " + columnName + " " + operator + " ");
                    }
                }
            }

        }

        System.out.println("this.hql=" + this.hql);
    }
    private String getInVal(String type, String val) {
        if (val == null)
            return "";
        if (val.indexOf(")") > 0)
            return val;
        String[] valarr = val.split(",");
        String s = "";
        for (int i = 0; i < valarr.length; i++) {
            s = s + "," + valarr[i];
        }
        s = s.length() > 0 ? s.substring(1) : s;
        return "(" + s + ")";
    }

    public void setSort(PageParam pageable)
    {
        Map orderBy = pageable.getOrderBy();
        if ((orderBy != null) && (!orderBy.isEmpty())) {
            String sortK = (String)orderBy.get("sortK");
            String sortD = (String)orderBy.get("sortD");
            if ((!"".equals(sortK)) && (!"".equals(sortD))) {
                Sort sort = new Sort(Sort.Direction.fromString(sortD), new String[] { sortK.replaceAll("#", ".") });
                pageable.setSort(sort);
            }
        }
    }

    public Map<String, Object> getParams()
    {
        return this.params;
    }

    public String getWhereHql()
    {
        return this.hql.toString();
    }
}