package com.swein.recycleview.random.data;

import com.swein.recycleview.random.manager.ItemPosition;

/**
 * Created by seokho on 07/03/2017.
 */

public class ListItemData {

    public String       tagName;
    public boolean      tagCheckState;
    public String       imagePath;
    public ItemPosition itemPosition;

    public ListItemData(String tagName, boolean tagCheckState, String imagePath, ItemPosition itemPosition) {
        this.tagName = tagName;
        this.tagCheckState = tagCheckState;
        this.imagePath = imagePath;
        this.itemPosition = itemPosition;
    }

}
