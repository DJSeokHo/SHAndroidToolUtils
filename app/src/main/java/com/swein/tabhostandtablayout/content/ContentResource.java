package com.swein.tabhostandtablayout.content;

import android.view.View;
import android.widget.ImageView;

import com.swein.shandroidtoolutils.R;

/**
 * Created by seokho on 05/04/2017.
 */

public class ContentResource {

    public static void setSelectImageResource(View view, int resouceID, int position) {

        switch ( position ) {
            case 0:
                ((ImageView) view.findViewById( resouceID )).setImageResource( R.drawable.home_fragment_icon_select );
                break;

            case 1:

                ((ImageView) view.findViewById( resouceID )).setImageResource( R.drawable.friends_fragment_icon_select );
                break;

            case 2:

                ((ImageView) view.findViewById( resouceID )).setImageResource( R.drawable.event_fragment_icon_select );
                break;

            case 3:

                ((ImageView) view.findViewById( resouceID )).setImageResource( R.drawable.profile_fragment_icon_select );
                break;
        }

    }

    public static void setUnSelectImageResource(View view, int resouceID, int position) {

        switch ( position ) {
            case 0:

                ((ImageView) view.findViewById( resouceID )).setImageResource( R.drawable.home_fragment_icon_unselect );
                break;

            case 1:

                ((ImageView) view.findViewById( resouceID )).setImageResource( R.drawable.friends_fragment_icon_unselect );
                break;

            case 2:

                ((ImageView) view.findViewById( resouceID )).setImageResource( R.drawable.event_fragment_icon_unselect );
                break;

            case 3:

                ((ImageView) view.findViewById( resouceID )).setImageResource( R.drawable.profile_fragment_icon_unselect );
                break;
        }
    }

}
