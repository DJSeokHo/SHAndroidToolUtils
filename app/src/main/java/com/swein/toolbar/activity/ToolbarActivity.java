package com.swein.toolbar.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.swein.framework.tools.util.toast.ToastUtils;
import com.swein.shandroidtoolutils.R;

/**
 * toolbar need Theme.AppCompat.Light.NoActionBar setting
 *
 * <activity android:name="com.swein.toolbar.activity.ToolbarActivity"
        android:theme="@style/AppThemeToolbar"/>
 *
 */
public class ToolbarActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_toolbar_activity );

        toolbar = (Toolbar) findViewById( R.id.toolbar );

        toolbar.setLogo(R.drawable.tx);
        toolbar.setTitle("Tool Bar");
        toolbar.setSubtitle( "sub title" );
        setSupportActionBar( toolbar );

        //set navigationIcon must after setSupportActionBar method
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);

        toolbar.setOnMenuItemClickListener( new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick( MenuItem item ) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        ToastUtils.showShortToastNormal( ToolbarActivity.this, "Search");
                        break;
                    case R.id.action_notifications:
                        ToastUtils.showShortToastNormal( ToolbarActivity.this, "Notificationa");
                        break;
                    case R.id.action_settings:
                        ToastUtils.showShortToastNormal( ToolbarActivity.this, "Settings");
                        break;
                }
                return true;
            }
        } );
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate( R.menu.menu_toolbar_activity, menu );
        return true;
    }
}
