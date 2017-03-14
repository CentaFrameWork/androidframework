package com.centaandroidframework.demos;

import android.os.Handler;
import android.view.View;

import com.centaandroidframework.R;
import com.centaline.corelib.base.AbsActivity;
import com.centaline.corelib.widgets.RoundProgressBar;

/**
 * @author yanwenqiang
 * @Date 17/3/14
 * @description 圆形进度条例子
 */
public class RoundProgressActivity extends AbsActivity {

    private RoundProgressBar roundProgressBar;

    @Override
    protected int layoutResId() {
        return R.layout.demo_layout_roundprogress;
    }

    @Override
    protected void findViews() {
        roundProgressBar = searchViewById(R.id.roundProgressBar);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void appendEvents() {

    }

    @Override
    protected void initComplete() {

    }


    public void click(View v){
        startProgressBar();
    }

    private void startProgressBar() {


        final Handler handler = new Handler();

        new Thread(new Runnable() {


            @Override
            public void run() {

                int num = 0;

                while (num < 100) {

                    num += 1;
                    if (num >= 100) {
                        num = 100;
                    }

                    roundProgressBar.setProgress(num);
                    final int finalNum = num;

                    if (num == 100) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                toast("Done ! ");
                            }
                        });
                    }

                    try {
                        Thread.sleep(50);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }
        }).start();


    }

}
