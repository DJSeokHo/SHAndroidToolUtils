package com.swein.framework.tools.util.popup;

import android.content.Context;
import androidx.appcompat.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by seokho on 24/12/2016.
 */

public class PopupMenuUtil {

    public static void popupMenuByButton(Context context, View button, int menuResource) {
        final PopupMenu popupMenu = new PopupMenu(context, button);
        popupMenu.getMenuInflater().inflate(menuResource, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {

                    //add popup menu item's it at here

                    default:

                        break;

                }
                return true;
            }
        });

        popupMenu.show();
    }

}
