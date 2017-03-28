package com.centaandroidframework;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.centaandroidframework.demos.activites.RoundProgressActivity;
import com.centaline.corelib.base.AbsActivity;

public class MainActivity extends AbsActivity {

    private Button btn_start;


    @Override
    protected int layoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void findViews() {
        btn_start = searchViewById(R.id.btn_start);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void appendEvents() {
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goDemos();
            }
        });
    }

    @Override
    protected void initComplete() {

    }

    private void goDemos() {
        Intent intent = new Intent(this, RoundProgressActivity.class);
        this.startActivity(intent);
    }
}
