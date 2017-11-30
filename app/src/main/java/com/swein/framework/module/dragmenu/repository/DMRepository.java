package com.swein.framework.module.dragmenu.repository;

/**
 *
 * Created by seokho on 28/08/2017.
 */

public class DMRepository {

    private DMRepository() {}

    public static DMRepository instance = new DMRepository();

    public static DMRepository getInstance() {
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
