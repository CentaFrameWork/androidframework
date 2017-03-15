package com.centaandroidframework;

import android.app.Application;
import android.util.Log;

import com.taobao.hotfix.HotFixManager;
import com.taobao.hotfix.PatchLoadStatusListener;
import com.taobao.hotfix.util.PatchStatusCode;

/**
 * @author yanwenqiang
 * @Date 17/3/15
 * @description 项目Application
 */
public class CentaApplication extends Application {

    private String appVersion;
    private String appId;
    private final String HotFixTest = "HotFixTest";

    @Override
    public void onCreate() {
        super.onCreate();
        inithotFox();
    }

    private void initApp() {
        this.appId = "99738-1";
        try {
            this.appVersion = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (Exception e) {
            this.appVersion = "1.0.0";
        }
    }

    private void inithotFox()
    {
        initApp();

        HotFixManager.getInstance().setContext(this)
                .setAppVersion(appVersion)
                .setAppId(appId)
                .setAesKey(null)
                .setSupportHotpatch(true)
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onload(final int mode, final int code, final String info, final int handlePatchVersion) {
                        // 补丁加载回调通知

//                mode: 补丁模式, 0:正常请求模式 1:扫码模式 2:本地补丁模式
//                code: 补丁加载状态码, 详情查看PatchStatusCode类说明
//                info: 补丁加载详细说明, 详情查看PatchStatusCode类说明
//                handlePatchVersion: 当前处理的补丁版本号, 0:无 -1:本地补丁 其它:后台补丁

//                code: 1 补丁加载成功
//                code: 6 服务端没有最新可用的补丁
//                code: 12 当前应用已经存在一个旧补丁, 应用重启尝试加载新补丁
//                code: 13 补丁加载失败, 导致的原因很多种, 比如UnsatisfiedLinkError等异常, 此时应该严格检查logcat异常日志

                        if (code == PatchStatusCode.CODE_SUCCESS_LOAD) {
                            Log.d(HotFixTest, "补丁加载成功");
                        } else if (code == PatchStatusCode.CODE_ERROR_NEEDRESTART) {
                            Log.d(HotFixTest, "新补丁生效需要重启. 业务方可自行实现逻辑, 提示用户或者强制重启, 可以监听应用进入后台事件, 然后应用自杀");
                        } else if (code == PatchStatusCode.CODE_ERROR_INNERENGINEFAIL) {
                            // 内部引擎加载异常, 推荐此时清空本地补丁, 但是不清空本地版本号, 防止失败补丁重复加载
                            //HotFixManager.getInstance().cleanPatches(false);
                        } else {
                            Log.d(HotFixTest, "其它信息");
                        }
                    }
                }).initialize();
    }
}
