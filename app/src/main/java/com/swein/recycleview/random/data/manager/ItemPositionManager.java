package com.swein.recycleview.random.data.manager;

import com.swein.recycleview.random.data.ListItemData;

import java.util.List;

/**
 * Created by seokho on 10/03/2017.
 */

public class ItemPositionManager {

    public final static int MAX_LENGTH = 6;

    private ItemLength getTagCol(String string) {

        if(0 < string.length() && 5 >= string.length()) {
            return ItemLength.SHORT;
        }
        else if(5 < string.length() && 10 >= string.length()) {
            return ItemLength.NORMAL;
        }
        else {
            return ItemLength.LONG;
        }
    }

    private void setItemColSort(int index, List list) {
        list.set( index, 2 );
    }

    private void setItemColMid(int index, List list) {
        list.set( index, 3 );
    }

    private void setItemColNormal(int index, List list) {
        list.set( index, 4 );
    }

    private void setItemColLong(int index, List list) {
        list.set( index, 6 );
    }

    public void setItemPosition( List colList, List<ListItemData> listItemDatas ) {

        int totalSpace = 6;

        for(int i = 0; i < listItemDatas.size(); i++) {
            //init  col

            if(6 == totalSpace) {

                if(getTagCol( listItemDatas.get( i ).tagName ) == ItemLength.LONG) {  //tag length is 6
                    setItemColLong( i, colList );
                    totalSpace -= 6;

                }
                else if(getTagCol( listItemDatas.get( i ).tagName ) == ItemLength.NORMAL) { //tag length is 4
                    if(i == listItemDatas.size() - 1) { //last item
                        setItemColLong( i, colList );
                        totalSpace -= 6;
                    }
                    else {
                        setItemColNormal( i, colList );
                        totalSpace -= 4;
                    }
                }
                else {  //tag length is 2
                    if(i == listItemDatas.size() - 1) { //last item
                        setItemColLong( i, colList );
                        totalSpace -= 6;
                    }
                    else {
                        setItemColSort( i, colList );
                        totalSpace -= 2;
                    }
                }

                if(0 == totalSpace) {
                    totalSpace = 6;
                }
                continue;
            }

            if(4 == totalSpace) {

                if(getTagCol( listItemDatas.get( i ).tagName ) == ItemLength.LONG) {  //tag length is 6
                    setItemColLong( i - 1, colList );
                    setItemColLong( i, colList );
                    totalSpace -= 4;
                }
                else if(getTagCol( listItemDatas.get( i ).tagName ) == ItemLength.NORMAL) { //tag length is 4
                    setItemColNormal( i, colList );
                    totalSpace -= 4;
                }
                else {  //tag length is 2
                    setItemColSort( i, colList );
                    totalSpace -= 2;
                }

                if(0 == totalSpace) {
                    totalSpace = 6;
                }
                continue;

            }

            if(2 == totalSpace) {

                if(getTagCol( listItemDatas.get( i ).tagName ) == ItemLength.LONG) {

                    if(1 < i && ItemLength.SHORT == getTagCol( listItemDatas.get( i - 2 ).tagName ) && ItemLength.SHORT == getTagCol( listItemDatas.get( i - 1 ).tagName )) {

                        setItemColMid( i - 2, colList );
                        setItemColMid( i - 1, colList );
                        setItemColLong( i, colList );
                    }
                    else if(ItemLength.NORMAL == getTagCol( listItemDatas.get( i - 1 ).tagName )) {
                        setItemColLong( i - 1, colList );
                        setItemColLong( i, colList );
                    }
                    totalSpace -= 2;
                }
                else if(getTagCol( listItemDatas.get( i ).tagName ) == ItemLength.NORMAL) {

                    if(1 < i && ItemLength.SHORT == getTagCol( listItemDatas.get( i - 2 ).tagName ) && ItemLength.SHORT == getTagCol( listItemDatas.get( i - 1 ).tagName )) {

                        setItemColMid( i - 2, colList );
                        setItemColMid( i - 1, colList );
                        setItemColLong( i, colList );
                    }
                    else if(ItemLength.NORMAL == getTagCol( listItemDatas.get( i - 1 ).tagName )) {
                        setItemColLong( i - 1, colList );
                        setItemColLong( i, colList );
                    }
                    totalSpace -= 2;
                }
                else {
                    setItemColSort( i, colList );
                    totalSpace -= 2;
                }

                if(0 == totalSpace) {
                    totalSpace = 6;
                }
                continue;
            }
        }
    }
}
