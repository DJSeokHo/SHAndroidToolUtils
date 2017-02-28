package com.swein.recycleview.list.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seokho on 28/02/2017.
 */

public class RecyclerViewListData {

    private List< String > list;

    private RecyclerViewListData() {
        list = new ArrayList< String >();
    }

    private static RecyclerViewListData instance = null;

    public static RecyclerViewListData getInstance() {

        if ( null == instance ) {

            synchronized ( RecyclerViewListData.class ) {

                if ( null == instance ) {

                    instance = new RecyclerViewListData();

                }
            }
        }

        return instance;
    }

    public void initList() {

        list.clear();

        for ( int i = 'A'; i <= 'z'; i++ ) {
            list.add( "" + (char)i );
        }
    }

    public void loadList() {
        for ( int i = 0; i < 10; i++ ) {
            list.add( String.valueOf( i ) );
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

}
