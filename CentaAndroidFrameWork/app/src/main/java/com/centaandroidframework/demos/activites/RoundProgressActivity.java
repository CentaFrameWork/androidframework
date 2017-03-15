package com.centaandroidframework.demos.activites;

import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.centaandroidframework.R;
import com.centaandroidframework.models.respmodels.RespCityModel;
import com.centaandroidframework.network.RequestFirstApiPresenter;
import com.centaline.corelib.base.AbsActivity;
import com.centaline.corelib.utils.YLog;
import com.centaline.corelib.widgets.RoundProgressBar;
import com.google.gson.Gson;

import rx.Subscriber;

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

        RequestFirstApiPresenter.getCity("北京", new Subscriber<RespCityModel>() {
            @Override
            public void onCompleted() {
                Toast.makeText(RoundProgressActivity.this, "请求完成", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(RoundProgressActivity.this, " 错误 =_= ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(RespCityModel resCityModel) {
                String json = new Gson().toJson(resCityModel);
                YLog.json("数据返回", json);
            }
        });
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
                                toast("完成 Done ! ");
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
