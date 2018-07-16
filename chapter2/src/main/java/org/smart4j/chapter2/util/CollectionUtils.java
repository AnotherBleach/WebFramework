package org.smart4j.chapter2.util;

import org.apache.commons.collections4.MapUtils;

import java.util.Collection;
import java.util.Map;

public class CollectionUtils {

    /**
     * collection is Empty
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?>collection)
    {
        return CollectionUtils.isEmpty(collection);

    }

    /**
     * collection is not Empty
     * @param collection
     * @return
     */
    public static boolean isNotEmpy(Collection<?>collection)
    {

        return !isEmpty(collection);
    }


    /**
     * map is Empty
     * @param map
     * @return
     */
    public  static  boolean isEmpty(Map<?,?>map)
    {
        return MapUtils.isEmpty(map);

    }

    public static boolean isNotEmpty(Map<?,?>map)
    {
        return !isEmpty(map);

    }

}
