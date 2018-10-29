package com.swein.framework.template.transitionview;

import android.app.Activity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.swein.framework.template.transitionview.animationcomponentsview.AnimationComponentsViewHolder;
import com.swein.shandroidtoolutils.R;

public class TransitionViewTemplateActivity extends Activity {

    private Button buttonChange;
    private FrameLayout rootView;

    private int currentScene;

    private Scene scene1;
    private Scene scene2;
    private Scene scene3;

    private void prepareScene() {
        scene1 = Scene.getSceneForLayout(rootView, R.layout.view_holder_transition_view_scene1, TransitionViewTemplateActivity.this);
        scene2 = Scene.getSceneForLayout(rootView, R.layout.view_holder_transition_view_scene2, TransitionViewTemplateActivity.this);
        scene3 = Scene.getSceneForLayout(rootView, R.layout.view_holder_transition_view_scene3, TransitionViewTemplateActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_view_template);



        rootView = findViewById(R.id.rootView);
        new AnimationComponentsViewHolder(getLayoutInflater().inflate(R.layout.view_holder_transition_view_scene1, null));
        currentScene = 1;

        prepareScene();

        buttonChange = findViewById(R.id.buttonChange);


        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (currentScene) {

                    default:

                    case 1:

                        TransitionManager.go(scene2, new ChangeBounds());
                        currentScene = 2;
                        break;

                    case 2:

                        TransitionManager.go(scene3, new ChangeBounds());
                        currentScene = 3;
                        break;

                    case 3:

                        TransitionManager.go(scene1, new ChangeBounds());
                        currentScene = 1;
                        break;
                }


            }
        });
    }

}
