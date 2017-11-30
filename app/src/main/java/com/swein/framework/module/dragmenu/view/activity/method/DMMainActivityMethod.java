package com.swein.framework.module.dragmenu.view.activity.method;

import android.view.MotionEvent;

/**
 * Created by seokho on 13/09/2017.
 */

public interface DMMainActivityMethod {

    boolean onActionBarDragArrowTouch(MotionEvent event);

    void addDragMenu();

    void dragMenuAutoDownToBottom();

}
