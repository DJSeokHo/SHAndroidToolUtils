package com.swein.framework.template.transitionview.animationcomponentsview;

import android.view.View;
import android.widget.ImageButton;

import com.swein.framework.tools.util.toast.ToastUtil;
import com.swein.shandroidtoolutils.R;

public class AnimationComponentsViewHolder {

    private ImageButton imageButtonCheese;
    private ImageButton imageButtonCake;
    private ImageButton imageButtonEspresso;


    public AnimationComponentsViewHolder(View view) {
        findView(view);
    }

    private void findView(View view) {
        imageButtonCheese = view.findViewById(R.id.imageButtonCheese);
        imageButtonCheese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showCustomShortToastNormal(v.getContext(), "cheese");
            }
        });
        imageButtonCake = view.findViewById(R.id.imageButtonCake);
        imageButtonCake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showCustomShortToastNormal(v.getContext(), "cake");
            }
        });
        imageButtonEspresso = view.findViewById(R.id.imageButtonEspresso);
        imageButtonEspresso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showCustomShortToastNormal(v.getContext(), "espresso");
            }
        });

    }

}
