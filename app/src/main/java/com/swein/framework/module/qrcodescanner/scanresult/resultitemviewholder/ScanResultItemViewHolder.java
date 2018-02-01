package com.swein.framework.module.qrcodescanner.scanresult.resultitemviewholder;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.swein.framework.module.qrcodescanner.scanresult.model.ScanResultItem;
import com.swein.framework.tools.util.toast.ToastUtil;
import com.swein.shandroidtoolutils.R;

/**
 * Created by seokho on 31/01/2018.
 */

public class ScanResultItemViewHolder extends View {
    public interface ScanResultItemViewHolderDelegate {
        void onDeleteClicked(View view, ScanResultItemViewHolder scanResultItemViewHolder);
    }

    private TextView titleTextView;
    private TextView dateTextView;
    private TextView indexTextView;
    private View itemView;
    private ImageButton deleteImageButton;
    private ScanResultItemViewHolderDelegate scanResultItemViewHolderDelegate;
    private ScanResultItem scanResultItem;
    private Context context;

    public ScanResultItemViewHolder(Context context, ScanResultItem scanResultItem, ScanResultItemViewHolderDelegate scanResultItemViewHolderDelegate) {
        super(context);

        itemView = ((Activity)context).getLayoutInflater().inflate(R.layout.view_holder_scan_result_item, null);

        this.context = context;
        this.scanResultItemViewHolderDelegate = scanResultItemViewHolderDelegate;
        this.scanResultItem = scanResultItem;

        findView();
    }

    private void findView() {
        titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
        dateTextView = (TextView) itemView.findViewById(R.id.dateTextView);
        indexTextView = (TextView) itemView.findViewById(R.id.indexTextView);
        deleteImageButton = (ImageButton) itemView.findViewById(R.id.deleteImageButton);

        titleTextView.setText(scanResultItem.title);
        dateTextView.setText(scanResultItem.date);


        deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanResultItemViewHolderDelegate.onDeleteClicked(itemView, ScanResultItemViewHolder.this);
            }
        });

        titleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showCustomShortToastNormal(context, scanResultItem.title);
            }
        });
    }

    public void setIndex(String index) {
        indexTextView.setText(index);
    }

    public View getItemView() {
        return itemView;
    }

}
