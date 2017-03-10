package com.swein.recycleview.random.data;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.random.RandomNumberUtils;
import com.swein.framework.tools.util.random.RandomStringUtils;
import com.swein.recycleview.random.manager.ItemPosition;
import com.swein.recycleview.random.manager.ItemPositionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seokho on 02/03/2017.
 */

public class RecyclerViewRandomData {

    private List< ListItemData > list;
    private List<Integer> colList;

    private ItemPositionManager itemPositionManager;

    private RecyclerViewRandomData() {
        list = new ArrayList< ListItemData >();
        colList = new ArrayList<Integer>();
        itemPositionManager = new ItemPositionManager();
    }

    private static RecyclerViewRandomData instance = null;

//    private DisplayMetrics metric = new DisplayMetrics();



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


    public List<Integer> getColList() {
        return this.colList;
    }

    /**
     * init list
     */
    public void initList() {

        //create random string
        int count = RandomNumberUtils.getRandomIntegerNumber(11, 50);

        list.clear();
        colList.clear();

        for ( int i = 0; i < 10; i++ ) {
            int length = RandomNumberUtils.getRandomIntegerNumber(25, 1);
            String tagName = RandomStringUtils.createRandomString(length);
            ListItemData listItemData = new ListItemData( tagName, false, null, ItemPosition.LEFT);
            list.add( listItemData );
            colList.add( i );
        }

        itemPositionManager.setItemPosition( colList, list );

        for( int i = 0; i < getColList().size(); i++) {
            ILog.iLogDebug( RecyclerViewRandomData.class.getSimpleName(), getList().get( i ).tagName + " [" + getList().get( i ).tagName.length() + "] " + getColList().get( i ) + " " + getList().get( i ).itemPosition);
        }
    }

    /**
     * load more data
     */
    public void loadList() {
        int count = RandomNumberUtils.getRandomIntegerNumber(11, 30);

        for ( int i = 0; i < count; i++ ) {
            int length = RandomNumberUtils.getRandomIntegerNumber(25, 1);

            String tagName = RandomStringUtils.createRandomString(length);
            ListItemData listItemData = new ListItemData(tagName, false, null, ItemPosition.LEFT);
            list.add( listItemData );
            colList.add( i );
        }

        itemPositionManager.setItemPosition( colList, list );
    }

    public void setList( List< ListItemData > list ) {
        this.list = list;
    }

    public List< ListItemData > getList() {
        return this.list;
    }

    public void removeListItem( int position ) {
        list.remove( position );
    }

}
