package com.swein.blockgame.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.swein.blockgame.fragment.BlockGameTZFEFragment;
import com.swein.framework.tools.util.fragment.FragmentUtils;
import com.swein.shandroidtoolutils.R;

public class BlockGameTZFEActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_game_tzfe);

        FragmentUtils.replaceFragmentv4Commit(this, new BlockGameTZFEFragment(), R.id.blockGameTZFEContainer);

    }
}
