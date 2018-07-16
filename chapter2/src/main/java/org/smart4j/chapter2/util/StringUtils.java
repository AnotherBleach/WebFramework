package org.smart4j.chapter2.util;

public final class StringUtils {

    public  static boolean isEmpty(String s)
    {
        if(s!=null)
            s=s.trim();

        return org.apache.commons.lang3.StringUtils.isEmpty(s);

    }
    public static boolean isNotEmpty(String s)
    {
        return !isEmpty(s);

    }

}
