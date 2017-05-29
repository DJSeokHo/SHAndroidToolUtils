package com.swein.recycleview.random.data;

import android.content.Context;
import android.graphics.Rect;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.random.RandomNumberUtils;
import com.swein.framework.tools.util.random.RandomStringUtils;
import com.swein.recycleview.random.manager.ItemPositionManager;
import com.swein.recycleview.random.manager.content.ItemPosition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seokho on 02/03/2017.
 */

public class RecyclerViewRandomData {

    private List< ListItemData > list;
    private List<Integer>        colList;
    private List<Rect>           itemOffsetList;
    private ItemPositionManager itemPositionManager;
    private Context context;

    private RecyclerViewRandomData() {
        list = new ArrayList< ListItemData >();
        colList = new ArrayList<Integer>();
        itemOffsetList = new ArrayList<Rect>();
        itemPositionManager = new ItemPositionManager();

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


    public List<Integer> getColList() {
        return this.colList;
    }


    public List<Rect> getItemOffsetList() {
        return this.itemOffsetList;
    }

    /**
     * init list
     */
    public void initList() {

        //create random string
        int count = RandomNumberUtils.getRandomIntegerNumber(1, 12);
        list.clear();
        colList.clear();
        itemOffsetList.clear();

        Rect rect = new Rect();
        rect.left = 0;
        rect.right = 0;

        for ( int i = 0; i < count; i++ ) {
            int length = RandomNumberUtils.getRandomIntegerNumber(25, 1);
            String tagName = RandomStringUtils.createRandomString(length);
            ListItemData listItemData = new ListItemData( tagName, false, null, ItemPosition.LEFT);
            list.add( listItemData );
            colList.add( i );
            itemOffsetList.add( rect );
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
        int count = RandomNumberUtils.getRandomIntegerNumber(1, 12);

        Rect rect = new Rect();
        rect.left = 0;
        rect.right = 0;

        for ( int i = 0; i < count; i++ ) {
            int length = RandomNumberUtils.getRandomIntegerNumber(25, 1);

            String tagName = RandomStringUtils.createRandomString(length);
            ListItemData listItemData = new ListItemData(tagName, false, null, ItemPosition.LEFT);
            list.add( listItemData );
            colList.add( i );
            itemOffsetList.add( rect );
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
