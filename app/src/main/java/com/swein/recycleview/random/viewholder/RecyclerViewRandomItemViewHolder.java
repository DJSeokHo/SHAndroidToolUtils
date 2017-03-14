package com.swein.recycleview.random.viewholder;

import android.graphics.Color;
import android.graphics.Shader;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.swein.framework.tools.util.random.RandomNumberUtils;
import com.swein.shandroidtoolutils.R;

import static com.swein.framework.tools.util.color.ColorUtils.createRandomColorString;

/**
 * Created by seokho on 02/03/2017.
 */

public class RecyclerViewRandomItemViewHolder extends RecyclerView.ViewHolder {

    private RoundedImageView recyclerViewRandomItemBackgroundImageView;
    private RoundedImageView recyclerViewRandomItemColorImageView;
    private TextView         recyclerViewRandomItemTextView;
    private TextView         recyclerViewRandomItemNumberTextView;
    private ImageView        recyclerViewRandomItemCheckImageView;
    private RelativeLayout   tagCloudItem;

    public RecyclerViewRandomItemViewHolder( View itemView ) {
        super( itemView );

        findView();

    }

    private void findView() {
        recyclerViewRandomItemTextView = (TextView)itemView.findViewById( R.id.recyclerViewRandomItemTextView );
        recyclerViewRandomItemNumberTextView = (TextView)itemView.findViewById( R.id.recyclerViewRandomItemNumberTextView );
        recyclerViewRandomItemBackgroundImageView = (RoundedImageView)itemView.findViewById( R.id.recyclerViewRandomItemBackgroundImageView );
        recyclerViewRandomItemColorImageView = (RoundedImageView)itemView.findViewById( R.id.recyclerViewRandomItemColorImageView );
        recyclerViewRandomItemCheckImageView = (ImageView)itemView.findViewById( R.id.recyclerViewRandomItemCheckImageView );
        tagCloudItem = (RelativeLayout)itemView.findViewById( R.id.tagCloudItem );
    }


    public void textViewSetText( String string ) {

//        String number = String.valueOf( RandomNumberUtils.getRandomIntegerNumber( 25, 1) );
//        String brackets = string + "(" + number + ")";
//
//
//
//        Spannable spannable = new SpannableString( brackets );
//        spannable.setSpan(new AbsoluteSizeSpan(20), brackets.lastIndexOf( "(" ), brackets.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //        recyclerViewRandomItemTextView.setText( spannable );

        recyclerViewRandomItemNumberTextView.setText( "(" + String.valueOf( RandomNumberUtils.getRandomIntegerNumber( 25, 1) ) + ")" );

        recyclerViewRandomItemTextView.setText( string );

        recyclerViewRandomItemTextView.setSingleLine();
    }

    public void backgroundImageViewSetImage(int position) {

        recyclerViewRandomItemBackgroundImageView.setScaleType( ImageView.ScaleType.FIT_XY);
        recyclerViewRandomItemBackgroundImageView.mutateBackground(true);
        recyclerViewRandomItemColorImageView.mutateBackground(true);

        //for test
        if(String.valueOf( position ).substring( String.valueOf( position ).length() - 1, String.valueOf( position ).length() ).contains( "1" )) {
            recyclerViewRandomItemBackgroundImageView.setBackgroundResource( R.drawable.t1 );
        }
        else if(String.valueOf( position ).substring( String.valueOf( position ).length() - 1, String.valueOf( position ).length() ).contains( "2" )) {
            recyclerViewRandomItemBackgroundImageView.setBackgroundResource( R.drawable.t2 );
        }
        else if(String.valueOf( position ).substring( String.valueOf( position ).length() - 1, String.valueOf( position ).length() ).contains( "3" )) {
            recyclerViewRandomItemBackgroundImageView.setBackgroundResource( R.drawable.t3 );
        }
        else if(String.valueOf( position ).substring( String.valueOf( position ).length() - 1, String.valueOf( position ).length() ).contains( "4" )) {
            recyclerViewRandomItemBackgroundImageView.setBackgroundResource( R.drawable.t4 );
        }
        else if(String.valueOf( position ).substring( String.valueOf( position ).length() - 1, String.valueOf( position ).length() ).contains( "5" )) {
            recyclerViewRandomItemBackgroundImageView.setBackgroundResource( R.drawable.t5 );
        }
        else if(String.valueOf( position ).substring( String.valueOf( position ).length() - 1, String.valueOf( position ).length() ).contains( "6" )) {
            recyclerViewRandomItemBackgroundImageView.setBackgroundResource( R.drawable.t6 );
        }
        else if(String.valueOf( position ).substring( String.valueOf( position ).length() - 1, String.valueOf( position ).length() ).contains( "7" )) {
            recyclerViewRandomItemBackgroundImageView.setBackgroundResource( R.drawable.t7 );
        }
        else if(String.valueOf( position ).substring( String.valueOf( position ).length() - 1, String.valueOf( position ).length() ).contains( "8" )) {
            recyclerViewRandomItemBackgroundImageView.setBackgroundResource( R.drawable.t8 );
        }
        else if(String.valueOf( position ).substring( String.valueOf( position ).length() - 1, String.valueOf( position ).length() ).contains( "9" )) {
            recyclerViewRandomItemBackgroundImageView.setBackgroundResource( R.drawable.t9 );
        }
        else if(String.valueOf( position ).substring( String.valueOf( position ).length() - 1, String.valueOf( position ).length() ).contains( "0" )) {
            recyclerViewRandomItemBackgroundImageView.setBackgroundResource( R.drawable.t1 );
        }

        recyclerViewRandomItemColorImageView.setBackgroundColor( Color.parseColor( createRandomColorString() ) );
        recyclerViewRandomItemColorImageView.getBackground().setAlpha( 100 );
        recyclerViewRandomItemBackgroundImageView.setOval(false);
        recyclerViewRandomItemBackgroundImageView.setTileModeX( Shader.TileMode.REPEAT);
        recyclerViewRandomItemBackgroundImageView.setTileModeY(Shader.TileMode.REPEAT);
    }

    //show check box
    public void showImageView() {
        recyclerViewRandomItemCheckImageView.setVisibility( View.VISIBLE );
    }

    //hide check box
    public void hideImageView() {
        recyclerViewRandomItemCheckImageView.setVisibility( View.GONE );
    }

    //set checked
    public void setImageViewChecked() {
        recyclerViewRandomItemCheckImageView.setImageResource(R.drawable.recyclerview_random_item_checked );
    }

    //set unchecked
    public void setImageViewUnChecked() {
        recyclerViewRandomItemCheckImageView.setImageResource(R.drawable.recyclerview_random_item_unchecked );
    }

    //set item background
    public void tagCloudItemLayoutSetBackGround() {
//        tagCloudItem.setBackgroundResource( R.drawable.recycler_view_random_item_custom_textview_background );
        recyclerViewRandomItemBackgroundImageView.setBackgroundResource( R.drawable.recycler_view_random_item_custom_textview_background );
    }

    //set item onclick
    public void tagCloudItemLayoutSetOnClickListener(View.OnClickListener onClickListener) {
        tagCloudItem.setOnClickListener( onClickListener );
    }

    //set item check or not
    public void tagCloudItemSetCheckState(boolean checkState) {

        if(checkState) {
            showImageView();
            setImageViewChecked();
        }
        else {
            showImageView();
            setImageViewUnChecked();
        }

    }

    //hide item
    public void hideViewHolder() {
        itemView.setVisibility( View.GONE );
    }


}