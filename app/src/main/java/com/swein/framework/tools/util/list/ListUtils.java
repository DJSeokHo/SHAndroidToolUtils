package com.swein.framework.tools.util.list;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by seokho on 14/12/2016.
 */

public class ListUtils {

    public static List removeDuplicateWithOrder(List list) {

        Set set = new HashSet();

        List newList = new ArrayList();

        for ( Iterator iter = list.iterator(); iter.hasNext(); ) {

            Object element = iter.next();

            if ( set.add(element) ){
                newList.add(element);
            }

        }
        return newList;

    }

    public static List mergeList(List listOne, List listTwo) {
        List list = new ArrayList();

        list.addAll( listOne );
        list.removeAll( listTwo );
        list.addAll( listTwo );

        return list;
    }

}
