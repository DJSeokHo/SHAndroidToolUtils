package com.swein.recycleview.random.manager;

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



    private void setItemColShort(int index, List list) {
        list.set( index, 2 );
    }

    private void setItemColHalf(int index, List list) {
        list.set( index, 3 );
    }

    private void setItemColNormal(int index, List list) {
        list.set( index, 4 );
    }

    private void setItemColLong(int index, List list) {
        list.set( index, 6 );
    }

    private void setItemLeft(List<ListItemData> listItemDatas, int index) {
        listItemDatas.get( index ).itemPosition = ItemPosition.LEFT;
    }

    private void setItemMid(List<ListItemData> listItemDatas, int index) {
        listItemDatas.get( index ).itemPosition = ItemPosition.MID;
    }

    private void setItemRight(List<ListItemData> listItemDatas, int index) {
        listItemDatas.get( index ).itemPosition = ItemPosition.RIGHT;
    }

    public void setItemPosition( List colList, List<ListItemData> listItemDatas ) {

        int totalSpace = 6;

        for(int i = 0; i < listItemDatas.size(); i++) {
            //init  col

            if(6 == totalSpace) {

                if(getTagCol( listItemDatas.get( i ).tagName ) == ItemLength.LONG) {  //tag length is 6
                    setItemColLong( i, colList );
                    totalSpace -= 6;
                    setItemMid( listItemDatas, i );
                }
                else if(getTagCol( listItemDatas.get( i ).tagName ) == ItemLength.NORMAL) { //tag length is 4
                    if(i == listItemDatas.size() - 1) { //last item
                        setItemColLong( i, colList );
                        totalSpace -= 6;
                        setItemMid( listItemDatas, i );
                    }
                    else {
                        setItemColNormal( i, colList );
                        totalSpace -= 4;
                        setItemLeft( listItemDatas, i );
                    }
                }
                else {  //tag length is 2
                    if(i == listItemDatas.size() - 1) { //last item
                        setItemColLong( i, colList );
                        totalSpace -= 6;
                        setItemMid( listItemDatas, i );
                    }
                    else {
                        setItemColShort( i, colList );
                        totalSpace -= 2;
                        setItemLeft( listItemDatas, i );
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
                    setItemMid( listItemDatas, i - 1 );
                    setItemMid( listItemDatas, i );
                }
                else if(getTagCol( listItemDatas.get( i ).tagName ) == ItemLength.NORMAL) { //tag length is 4
                    setItemColNormal( i, colList );
                    totalSpace -= 4;
                    setItemRight( listItemDatas, i );
                }
                else {  //tag length is 2
                    if(i == listItemDatas.size() - 1) { //last item
                        setItemColHalf( i - 1, colList );
                        setItemColHalf( i, colList );

                        setItemLeft( listItemDatas, i - 1 );
                        setItemRight( listItemDatas, i );
                        totalSpace -= 4;
                    }
                    else {
                        setItemColShort( i, colList );
                        setItemMid( listItemDatas, i );
                        totalSpace -= 2;
                    }
                }

                if(0 == totalSpace) {
                    totalSpace = 6;
                }
                continue;

            }

            if(2 == totalSpace) {

                if(getTagCol( listItemDatas.get( i ).tagName ) == ItemLength.LONG) {

                    if(1 < i && ItemLength.SHORT == getTagCol( listItemDatas.get( i - 2 ).tagName ) && ItemLength.SHORT == getTagCol( listItemDatas.get( i - 1 ).tagName )) {

                        setItemColHalf( i - 2, colList );
                        setItemColHalf( i - 1, colList );
                        setItemColLong( i, colList );
                        setItemLeft( listItemDatas, i - 2 );
                        setItemRight( listItemDatas, i - 1 );
                        setItemMid( listItemDatas, i );
                    }
                    else if(ItemLength.NORMAL == getTagCol( listItemDatas.get( i - 1 ).tagName )) {
                        setItemColLong( i - 1, colList );
                        setItemColLong( i, colList );
                        setItemMid( listItemDatas, i - 1 );
                        setItemMid( listItemDatas, i );
                    }
                    else if(ItemLength.SHORT == getTagCol( listItemDatas.get( i - 1 ).tagName )) {
                        setItemColShort( i - 1, colList );
                        setItemColLong( i, colList );
                        setItemLeft( listItemDatas, i - 2 );
                        setItemRight( listItemDatas, i - 1 );
                        setItemMid( listItemDatas, i );
                    }
                    totalSpace -= 2;
                }
                else if(getTagCol( listItemDatas.get( i ).tagName ) == ItemLength.NORMAL) {

                    if(1 < i && ItemLength.SHORT == getTagCol( listItemDatas.get( i - 2 ).tagName ) && ItemLength.SHORT == getTagCol( listItemDatas.get( i - 1 ).tagName )) {

                        setItemColHalf( i - 2, colList );
                        setItemColHalf( i - 1, colList );
                        setItemColLong( i, colList );

                        setItemLeft( listItemDatas, i - 2 );
                        setItemRight( listItemDatas, i - 1 );
                        setItemMid( listItemDatas, i );
                    }
                    else if(ItemLength.NORMAL == getTagCol( listItemDatas.get( i - 1 ).tagName )) {
                        setItemColLong( i - 1, colList );
                        setItemColLong( i, colList );

                        setItemMid( listItemDatas, i - 1 );
                        setItemMid( listItemDatas, i);
                    }
                    totalSpace -= 2;
                }
                else {
                    setItemColShort( i, colList );
                    setItemRight( listItemDatas, i );
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
