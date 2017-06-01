package com.swein.recycleview.random.data;

import android.graphics.Rect;

import com.swein.framework.tools.util.random.RandomNumberUtils;
import com.swein.framework.tools.util.random.RandomStringUtils;
import com.swein.recycleview.random.manager.ItemPositionManager;
import com.swein.recycleview.random.manager.content.ItemPosition;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by seokho on 02/03/2017.
 */

public class RecyclerViewRandomData {

    private List<ListItemData> listFromDB;
    private List<ListItemData> list;
    private List<Integer> colList;
    private List<Rect> itemOffsetList;
    private ItemPositionManager itemPositionManager;

    private RecyclerViewRandomData() {
        listFromDB = new ArrayList<>();
        list = new ArrayList<>();
        colList = new ArrayList<>();
        itemOffsetList = new ArrayList<>();
        itemPositionManager = new ItemPositionManager();

    }

    private static RecyclerViewRandomData instance = new RecyclerViewRandomData();

    public static RecyclerViewRandomData getInstance() {
        return instance;
    }


    public List<Integer> getColList() {
        return this.colList;
    }


    public List<Rect> getItemOffsetList() {
        return this.itemOffsetList;
    }

    public void initListFromDB() {

        //create random string
        int count = RandomNumberUtils.getRandomIntegerNumber(1, 12);
        listFromDB.clear();

        for (int i = 0; i < count; i++) {
            int length = RandomNumberUtils.getRandomIntegerNumber(25, 1);
            String tagName = RandomStringUtils.createRandomString(length);
            ListItemData listItemData = new ListItemData(tagName, false, null, ItemPosition.LEFT);
            listFromDB.add(listItemData);
        }

        initList();
    }

    public void loadMoreListFromDB() {
        int count = RandomNumberUtils.getRandomIntegerNumber(1, 12);
        for (int i = 0; i < count; i++) {
            int length = RandomNumberUtils.getRandomIntegerNumber(25, 1);
            String tagName = RandomStringUtils.createRandomString(length);
            ListItemData listItemData = new ListItemData(tagName, false, null, ItemPosition.LEFT);
            listFromDB.add(listItemData);
        }

        initList();
    }

    /**
     * init list
     */
    private void initList() {

        list.clear();
        colList.clear();
        itemOffsetList.clear();

        Rect rect = new Rect();
        rect.left = 0;
        rect.right = 0;

        list.addAll(listFromDB);

        for (int i = 0; i < list.size(); i++) {
            colList.add(i);
            itemOffsetList.add(rect);
        }

        itemPositionManager.setItemPosition(colList, list);
    }

    public void setList(List<ListItemData> list) {
        this.list = list;
    }

    public List<ListItemData> getList() {
        return this.list;
    }

    public void removeListItem(int position) {
        list.remove(position);
    }

}
