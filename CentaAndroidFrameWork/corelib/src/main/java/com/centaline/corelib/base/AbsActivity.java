package com.centaline.corelib.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.centaline.corelib.R;
import com.centaline.corelib.widgets.CentaProgressDialog;
import com.orhanobut.logger.Logger;


/**
 * @author yanwenqiang
 * @Date 17/1/5
 * @description Activity 基类
 */


/**
 * @author yanwenqiang
 * @Date 17/1/5
 * @description Activity 基类
 *
 * <p>
 * 描述:Activity基类,规范了初始化顺序,在{@link #onCreate(Bundle)}中会依次调用
 * {@link #setContentView(int)}、{@link #preInit(Bundle)}、{@link #findViews()}、{@link #initViews()}、
 * {@link #initComplete()}
 * <p>
 * 返回按钮和标题设置,{@link #setUniversalToolbar()}、{@link #setContentView(int)}、{@link #setUniversalToolbar(CharSequence)}
 * 只需要调用一次,将会设置页面的返回按钮和标题
 * <p>
 * 使用阻塞式加载对话框,{@link #showLoadingDialog()}显示,{@link #cancelLoadingDialog()}取消
 */

public abstract class AbsActivity extends AppCompatActivity {

    public static final String TITLE_ACTIVITY = "TITLE_ACTIVITY";//页面标题

    protected Toolbar mToolbar;
    private CentaProgressDialog mDialog;

    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return resources;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResId());
        preInit(savedInstanceState);
        findViews();
        initViews();
        appendEvents();
        initComplete();
    }

    /**
     * 布局id
     */
    @LayoutRes
    protected abstract int layoutResId();

    /**
     * 初始化view前的内容
     */
    protected void preInit(Bundle savedInstanceState) {

    }

    /**
     * findViewById
     */
    protected abstract void findViews();

    /**
     * view初始化
     */
    protected abstract void initViews();

    /**
     * 在{@link #appendEvents()}💰执行
     */
    protected abstract void appendEvents();

    /**
     * 初始化完成
     */
    protected abstract void initComplete();

    /**
     * 设置通用的toolbar,设置返回按钮,setUniversalToolbar只需调用一次
     */
    protected void setUniversalToolbar() {
        setUniversalToolbar("");
    }

    /**
     * 设置通用的toolbar，设置标题、默认添加返回按钮,setUniversalToolbar只需调用一次
     *
     * @param id 标题
     */
    protected void setUniversalToolbar(@StringRes int id) {
        setUniversalToolbar(getString(id));
    }

    /**
     * 设置通用的toolbar，设置标题、默认添加返回按钮,setUniversalToolbar只需调用一次
     *
     * @param title 标题
     */
    protected void setUniversalToolbar(CharSequence title) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        String activityTitle = getIntent().getStringExtra(TITLE_ACTIVITY);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(TextUtils.isEmpty(activityTitle) ? title : activityTitle);
        }
    }

    /**
     * 设置ToolbarTitle
     *
     * @param title CharSequence
     */
    protected void setToolbarTitle(CharSequence title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    /**
     * 添加默认返回事件，finish当前页面
     *
     * @param item MenuItem
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {//返回键关闭页面
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 显示进度Dialog
     */
    protected void showLoadingDialog() {
        if (mDialog == null) {
            mDialog = new CentaProgressDialog(this);
        }
        mDialog.show();
    }

    /**
     * 取消进度Dialog
     */
    protected void cancelLoadingDialog() {
        if (mDialog != null &&
                mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    /**
     * 开启权限授权设置页面
     */
    protected void startPermissionSetting() {
        Intent intent = new Intent();
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    /**
     * 网络不可用
     */
    protected void netWorkUnable(Throwable e) {
        Logger.e(e, "netWorkUnable");
        toast(R.string.network_unable);
    }

    /**
     * 网络不可用
     */
    @Deprecated
    protected void netWorkUnable() {
        toast(R.string.network_unable);
    }

    /**
     * Snackbar统一显示入口
     */
    protected void snack(@StringRes int id) {
        View coordinatorLayout = findViewById(R.id.coordinatorLayout);
        if (coordinatorLayout == null) {
            Logger.e("cannot find coordinatorLayout");
        } else {
            Snackbar.make(coordinatorLayout, id, Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * Snackbar统一显示入口
     *
     * @param text 显示内容
     */
    protected void snack(CharSequence text) {
        View coordinatorLayout = findViewById(R.id.coordinatorLayout);
        if (coordinatorLayout == null) {
            Logger.e("cannot find coordinatorLayout");
        } else {
            Snackbar.make(coordinatorLayout, text, Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * Toast统一显示入口
     */
    protected void toast(@StringRes int id) {
        Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();
    }

    /**
     * Toast统一显示入口
     */
    protected void toast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 隐藏软键盘
     */
    protected void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected <VT extends View> VT searchViewById(int id) {
        VT view = (VT) findViewById(id);
        if (view == null)
            throw new NullPointerException("This resource id is invalid.");
        return view;
    }


//    protected <VT extends View> VT searchViewById(int id) {
//        if (mView == null)
//            throw new NullPointerException("Fragment content view is null.");
//        VT view = (VT) mView.findViewById(id);
//        if (view == null)
//            throw new NullPointerException("This resource id is invalid.");
//        return view;
//    }
}
