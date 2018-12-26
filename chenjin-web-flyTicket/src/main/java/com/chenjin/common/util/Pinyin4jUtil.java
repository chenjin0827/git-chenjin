//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.chenjin.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Pinyin4jUtil {
    public Pinyin4jUtil() {
    }

    public static String getPinYin(String src) {
        char[] t1 = src.toCharArray();
        String[] t2 = new String[t1.length];
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";
        int t0 = t1.length;

        try {
            for(int i = 0; i < t0; ++i) {
                if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
                    t4 = t4 + t2[0];
                } else {
                    t4 = t4 + Character.toString(t1[i]);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination var7) {
            var7.printStackTrace();
        }

        return t4;
    }

    public static String getPinYinHeadChar(String str) {
        String convert = "";

        for(int j = 0; j < str.length(); ++j) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert = convert + pinyinArray[0].charAt(0);
            } else {
                convert = convert + word;
            }
        }

        return convert;
    }

    public static String getCnASCII(String cnStr) {
        StringBuffer strBuf = new StringBuffer();
        byte[] bGBK = cnStr.getBytes();

        for(int i = 0; i < bGBK.length; ++i) {
            strBuf.append(Integer.toHexString(bGBK[i] & 255));
        }

        return strBuf.toString();
    }

    public static String getPinYinHeadChars(String str) {
        if (str.equals("")) {
            return "";
        } else {
            String convert = "";
            String[][] pingyins = new String[str.length()][];

            for(int j = 0; j < str.length(); ++j) {
                char word = str.charAt(j);
                String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
                if (pinyinArray != null) {
                    pingyins[j] = pinyinArray;
                } else {
                    pingyins[j] = new String[]{String.valueOf(word)};
                }
            }

            String[][] ss = new String[pingyins.length][];

            int qq;
            for(int i = 0; i < pingyins.length; ++i) {
                if (pingyins[i] != null) {
                    ss[i] = new String[pingyins[i].length];

                    for(qq = 0; qq < pingyins[i].length; ++qq) {
                        ss[i][qq] = pingyins[i][qq].substring(0, 1);
                    }
                }
            }

            String[] gg = ss[0];

            for(qq = 1; qq < ss.length; ++qq) {
                if (gg != null && ss[qq] != null) {
                    gg = recursion(gg, ss[qq]);
                }
            }

            for(qq = 0; qq < gg.length; ++qq) {
                if (!convert.contains(gg[qq])) {
                    convert = convert + gg[qq] + ",";
                }
            }

            return convert;
        }
    }

    public static String[] recursion(String[] aa, String[] bb) {
        String[] result = new String[aa.length * bb.length];

        for(int i = 0; i < aa.length; ++i) {
            for(int j = 0; j < bb.length; ++j) {
                String rtn = aa[i] + bb[j];
                result[j + i * bb.length] = rtn;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        long starTime = System.currentTimeMillis();
        String cnStr = "重组人Ⅱ型肿瘤坏死因子受体-抗体融合蛋白";
        System.out.println(getPinYin(cnStr));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println(Time);
    }
}
