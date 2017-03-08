package com.swein.recycleview.random.data;

import com.swein.framework.tools.util.random.RandomNumberUtils;
import com.swein.framework.tools.util.random.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seokho on 02/03/2017.
 */

public class RecyclerViewRandomData {

    private List< ListItemData > list;

    private RecyclerViewRandomData() {
        list = new ArrayList< ListItemData >();
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

    /**
     * init list
     */
    public void initList() {

        //create random string
        int count = RandomNumberUtils.getRandomIntegerNumber(11, 50);

        list.clear();

        for ( int i = 0; i < count; i++ ) {
            int length = RandomNumberUtils.getRandomIntegerNumber(15, 1);
            String tagName = RandomStringUtils.createRandomString(length);
            ListItemData listItemData = new ListItemData(tagName, false, null );
            list.add( listItemData );
        }
    }

    /**
     * load more data
     */
    public void loadList() {
        int count = RandomNumberUtils.getRandomIntegerNumber(11, 30);

        for ( int i = 0; i < count; i++ ) {
            int length = RandomNumberUtils.getRandomIntegerNumber(15, 1);

            String tagName = RandomStringUtils.createRandomString(length);
            ListItemData listItemData = new ListItemData(tagName, false, null );
            list.add( listItemData );
        }
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
