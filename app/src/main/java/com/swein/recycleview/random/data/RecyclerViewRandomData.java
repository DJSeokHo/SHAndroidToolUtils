package com.swein.recycleview.random.data;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.random.RandomNumberUtils;
import com.swein.framework.tools.util.random.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seokho on 02/03/2017.
 */

public class RecyclerViewRandomData {

    private List< String > list;

    private List<Integer> containerItemCount;

    private RecyclerViewRandomData() {
        list = new ArrayList< String >();
        containerItemCount = new ArrayList<Integer>();
    }

    private static RecyclerViewRandomData instance = null;

    public static RecyclerViewRandomData getInstance() {

        if ( null == instance ) {

            synchronized ( RecyclerViewRandomData.class ) {

                if ( null == instance ) {

                    instance = new RecyclerViewRandomData();

                }
            }
        }

        return instance;
    }

    public void initList() {

        //create random string
        int count = RandomNumberUtils.getRandomIntegerNumber(11, 30);

        list.clear();

        ILog.iLogDebug( RecyclerViewRandomData.class.getSimpleName(), count );

        for ( int i = 0; i < count; i++ ) {
            int length = RandomNumberUtils.getRandomIntegerNumber(8, 1);

            list.add( RandomStringUtils.createRandomString(length) );

            ILog.iLogDebug( RecyclerViewRandomData.class.getSimpleName(), list.get( i ).toString() );
        }
    }

//    public int getMaxLengthItemFromStringList() {
//
//        int max = 0;
//
//        for(int i = 0; i < list.size(); i++) {
//            if(max <= list.get( i ).length()) {
//                max = list.get( i ).length();
//            }
//        }
//
//        return max;
//
//    }

    public void loadList() {
        for ( int i = 0; i < 30; i++ ) {
            int length = RandomNumberUtils.getRandomIntegerNumber(8, 1);

            list.add( RandomStringUtils.createRandomString(length) );

            ILog.iLogDebug( RecyclerViewRandomData.class.getSimpleName(), list.get( i ).toString() );
        }
    }

    public void setList( List< String > list ) {
        this.list = list;
    }

    public List< String > getList() {
        return this.list;
    }

    public void removeListItem( int position ) {
        list.remove( position );
    }

    public void setContainerItemCount(){

        for(String title : list) {

            int itemCount = 0;




        }

//        containerItemCount

    }

    public void getContainerItemCount(){

    }



}
