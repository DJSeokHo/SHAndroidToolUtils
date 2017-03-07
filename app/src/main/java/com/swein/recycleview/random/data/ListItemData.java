package com.swein.recycleview.random.data;

/**
 * Created by seokho on 07/03/2017.
 */

public class ListItemData {

    public String tagName;
    public boolean tagCheckState;
    public String imagePath;

    public ListItemData(String tagName, boolean tagCheckState, String imagePath) {
        this.tagName = tagName;
        this.tagCheckState = tagCheckState;
        this.imagePath = imagePath;
    }

}
