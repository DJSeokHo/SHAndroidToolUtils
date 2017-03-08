package com.swein.recycleview.random.activity;

import android.text.TextWatcher;
import android.view.View;

/**
 * Created by seokho on 08/03/2017.
 */

public interface ListenerInterface {

    View.OnClickListener onClickListener();
    TextWatcher textWatcher();

}
