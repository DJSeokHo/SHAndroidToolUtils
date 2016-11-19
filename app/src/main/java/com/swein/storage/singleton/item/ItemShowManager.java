package com.swein.storage.singleton.item;

/**
 * Created by seokho on 16/11/2016.
 */

public class ItemShowManager {

//    private boolean isMyListFragmentShow = false;

    private static ItemShowManager instance = new ItemShowManager();

    public static ItemShowManager getInstance() {
        return instance;
    }

//    public void setIsMyListFragmentShow(boolean isMyListFragmentShow) {
//        this.isMyListFragmentShow = isMyListFragmentShow;
//    }
//
//    public boolean getIsMyListFragmentShow() {
//        return this.isMyListFragmentShow;
//    }

}
