package com.bairesdev.util;

import java.util.*;

/**
 * Utilities class
 */
public class AppUtils {

    public static List<String> setPropertyArray(String props) {

        String[] propArray = props.split(",");
        List<String> list = Arrays.asList(propArray);
        return list;

    }

}
