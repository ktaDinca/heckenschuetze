package com.intervals.util;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 09/Jun/2014
 */
public class StringUtils {

    public static Boolean isNotEmptyNullOrUndefined(String str) {
        if (str != null && str.length() > 0 && !"undefined".equals(str)) {
            return true;
        }
        return false;
    }

}
