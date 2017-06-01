package com.swein.recycleview.random.manager;

import com.swein.recycleview.random.data.ListItemData;
import com.swein.recycleview.random.manager.content.ItemLength;
import com.swein.recycleview.random.manager.content.ItemPosition;

import java.util.List;

/**
 *
 * Created by seokho on 10/03/2017.
 */

public class ItemPositionManager {

    public final static int MAX_LENGTH = 6;

    public final static int STRING_LENGTH_ZERO = 0;
    public final static int STRING_LENGTH_SHORT = 5;
    public final static int STRING_LENGTH_NORMAL = 10;

    private final static int ITEM_SPAN_SHORT = 2;
    private final static int ITEM_SPAN_HALF = 3;
    private final static int ITEM_SPAN_NORMAL = 4;
    private final static int ITEM_SPAN_LONG = 6;

    public final static float LEVEL_ONE_SPACE_RATIO = 0.07f;
    public final static float LEVEL_TWO_SPACE_RATIO = 0.068f;
    public final static float LEVEL_THREE_SPACE_RATIO = 0.06f;
    public final static float LEVEL_FOUR_SPACE_RATIO = 0.055f;
    public final static float LEVEL_FIVE_SPACE_RATIO = 0.026f;
    public final static float LEVEL_SIX_SPACE_RATIO = 0.003f;


    private ItemLength getTagCol(String string) {

        if(STRING_LENGTH_ZERO < string.length() && STRING_LENGTH_SHORT >= string.length()) {
            return ItemLength.SHORT;
        }
        else if(STRING_LENGTH_SHORT < string.length() && STRING_LENGTH_NORMAL >= string.length()) {
            return ItemLength.NORMAL;
        }
        else {
            return ItemLength.LONG;
        }
    }

    private void setItemColShort(int index, List list) {
        list.set( index, ITEM_SPAN_SHORT );
    }

    private void setItemColHalf(int index, List list) {
        list.set( index, ITEM_SPAN_HALF );
    }

    private void setItemColNormal(int index, List list) {
        list.set( index, ITEM_SPAN_NORMAL );
    }

    private void setItemColLong(int index, List list) {
        list.set( index, ITEM_SPAN_LONG );
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

        int totalSpace = ITEM_SPAN_LONG;

        for(int i = 0; i < listItemDatas.size(); i++) {
            //init  col

            if(ITEM_SPAN_LONG == totalSpace) {

                if(getTagCol( listItemDatas.get( i ).tagName ) == ItemLength.LONG) {  //tag length is 6
                    setItemColLong( i, colList );
                    totalSpace -= ITEM_SPAN_LONG;

                    setItemMid( listItemDatas, i );

                }
                else if(getTagCol( listItemDatas.get( i ).tagName ) == ItemLength.NORMAL) { //tag length is 4
                    if(i == listItemDatas.size() - 1) { //last item
                        setItemColLong( i, colList );
                        totalSpace -= ITEM_SPAN_LONG;

                        setItemMid( listItemDatas, i );
                    }
                    else {
                        setItemColNormal( i, colList );
                        totalSpace -= ITEM_SPAN_NORMAL;

                        setItemLeft( listItemDatas, i );
                    }
                }
                else {  //tag length is 2
                    if(i == listItemDatas.size() - 1) { //last item
                        setItemColLong( i, colList );
                        totalSpace -= ITEM_SPAN_LONG;
                        setItemMid( listItemDatas, i );
                    }
                    else {
                        setItemColShort( i, colList );
                        totalSpace -= ITEM_SPAN_SHORT;
                        setItemLeft( listItemDatas, i );
                    }
                }

                if(0 == totalSpace) {
                    totalSpace = ITEM_SPAN_LONG;
                }
                continue;
            }

            if(ITEM_SPAN_NORMAL == totalSpace) {

                if(getTagCol( listItemDatas.get( i ).tagName ) == ItemLength.LONG) {  //tag length is 6
                    setItemColLong( i - 1, colList );
                    setItemColLong( i, colList );
                    totalSpace -= ITEM_SPAN_NORMAL;
                    setItemMid( listItemDatas, i - 1 );
                    setItemMid( listItemDatas, i );
                }
                else if(getTagCol( listItemDatas.get( i ).tagName ) == ItemLength.NORMAL) { //tag length is 4
                    setItemColNormal( i, colList );
                    totalSpace -= ITEM_SPAN_NORMAL;
                    setItemRight( listItemDatas, i );
                }
                else {  //tag length is 2
                    if(i == listItemDatas.size() - 1) { //last item
                        setItemColHalf( i - 1, colList );
                        setItemColHalf( i, colList );

                        setItemLeft( listItemDatas, i - 1 );
                        setItemRight( listItemDatas, i );
                        totalSpace -= ITEM_SPAN_NORMAL;
                    }
                    else {
                        setItemColShort( i, colList );
                        setItemMid( listItemDatas, i );
                        totalSpace -= ITEM_SPAN_SHORT;
                    }
                }

                if(0 == totalSpace) {
                    totalSpace = ITEM_SPAN_LONG;
                }
                continue;

            }

            if(ITEM_SPAN_SHORT == totalSpace) {

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
                    totalSpace -= ITEM_SPAN_SHORT;
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
                    totalSpace -= ITEM_SPAN_SHORT;
                }
                else {
                    setItemColShort( i, colList );

                    setItemRight( listItemDatas, i );

                    totalSpace -= ITEM_SPAN_SHORT;
                }

                if(0 == totalSpace) {
                    totalSpace = ITEM_SPAN_LONG;
                }
            }
        }
    }
}
