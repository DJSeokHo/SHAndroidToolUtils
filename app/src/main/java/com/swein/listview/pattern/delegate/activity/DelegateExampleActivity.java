package com.swein.listview.pattern.delegate.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.toast.ToastUtils;
import com.swein.listview.pattern.delegate.adapter.ListViewAdapter;
import com.swein.listview.pattern.delegate.interfaces.ListViewDelegator;
import com.swein.shandroidtoolutils.R;

import java.util.ArrayList;
import java.util.HashMap;

public class DelegateExampleActivity extends AppCompatActivity implements ListViewDelegator {

    EditText editTextView;
    ListView listView;
    Button buttonAdd;

    ListViewAdapter listViewAdapter;

    ArrayList<HashMap<String, String>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delegate);

        editTextView = (EditText) findViewById(R.id.editTextView);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);

        listView = (ListView) findViewById(R.id.listView);

        data = getDate();

        listViewAdapter = new ListViewAdapter(this);
        listViewAdapter.setData(data);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ILog.iLogDebug(DelegateExampleActivity.class.getSimpleName(), "list item clicked " + position);
            }
        });

        listView.setAdapter(listViewAdapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextView.getText().toString().equals("")) {
                    ToastUtils.showShortToastNormal(DelegateExampleActivity.this, "input someting");
                    return;
                }

                HashMap<String, String> addData = new HashMap<String, String>();
                addData.put("itemTitle", editTextView.getText().toString());
                data.add(addData);
                listViewAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        listViewAdapter.setListViewDelegator(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        listViewAdapter.setListViewDelegator(null);
    }

    private ArrayList<HashMap<String, String>> getDate(){
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String,String>>();

        //create a data
        for(int i = 0; i < 10; i++)
        {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("itemTitle", i + "'th line");
            listItem.add(map);
        }
        return listItem;
    }

    @Override
    public void setViewText(String name) {
        editTextView.setText(name);
    }

    @Override
    public void deleteListItem(int position) {
        data.remove(position);
    }
}
