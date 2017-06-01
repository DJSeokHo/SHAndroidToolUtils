package com.swein.recycleview.random.delegate;

/**
 *
 * Created by seokho on 02/03/2017.
 */

public interface RecyclerViewRandomDelegate {

    void setItemCheckState(Object object);

    void setAllItemSelected();

    void setAllItemUnSelected();

    void singleTagSearch(Object object);

    void checkSelectedItem();

}
