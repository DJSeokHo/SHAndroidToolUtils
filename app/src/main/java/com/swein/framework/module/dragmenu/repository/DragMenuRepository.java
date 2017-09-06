package com.swein.framework.module.dragmenu.repository;

/**
 *
 * Created by seokho on 28/08/2017.
 */

public class DragMenuRepository {

    private DragMenuRepository() {}

    public static DragMenuRepository instance = new DragMenuRepository();

    public static DragMenuRepository getInstance() {
        return instance;
    }

    private String contentString;

    public void setContentString(String contentString) {
        this.contentString = contentString;
    }

    public String getContentString() {
        return contentString;
    }
}
