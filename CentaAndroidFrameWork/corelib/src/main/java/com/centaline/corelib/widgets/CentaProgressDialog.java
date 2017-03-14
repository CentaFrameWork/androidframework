package com.centaline.corelib.widgets;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.centaline.corelib.R;

/**
 * @author yanwenqiang
 * @Date 17/3/14
 * @description 自定义对话框
 */
public class CentaProgressDialog extends Dialog {
    public CentaProgressDialog(Context context) {
        super(context, R.style.DefaultProgressDialog);
        this.setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_progress_dialog);
    }
}