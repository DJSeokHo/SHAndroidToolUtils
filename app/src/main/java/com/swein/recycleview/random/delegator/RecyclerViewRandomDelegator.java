package com.swein.recycleview.random.delegator;

/**
 * Created by seokho on 02/03/2017.
 */

public interface RecyclerViewRandomDelegator {

    void setItemCheckState(Object object);

    void setAllItemSelected();

    void setAllItemUnSelected();

    void singleTagSearch(Object object);

    void checkSelectedItem();

}
