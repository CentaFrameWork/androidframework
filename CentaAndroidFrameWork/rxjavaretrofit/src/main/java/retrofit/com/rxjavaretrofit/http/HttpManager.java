package retrofit.com.rxjavaretrofit.http;

import com.trello.rxlifecycle.android.ActivityEvent;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit.com.rxjavaretrofit.Api.BaseApi;
import retrofit.com.rxjavaretrofit.exception.RetryWhenNetworkException;
import retrofit.com.rxjavaretrofit.http.cookie.CacheInterceptor;
import retrofit.com.rxjavaretrofit.listener.HttpOnNextListener;
import retrofit.com.rxjavaretrofit.subscribers.ProgressSubscriber;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * http交互处理类
 */
public class HttpManager {
    private volatile static HttpManager INSTANCE;

    //构造方法私有
    private HttpManager() {
    }

    //获取单例
    public static HttpManager getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpManager();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 处理http请求
     *
     * @param basePar 封装的请求数据
     */
    public void doHttpDeal(BaseApi basePar) {
        //手动创建一个OkHttpClient并设置超时时间缓存等设置
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //连接失败后是否重新连接
        builder.retryOnConnectionFailure(true);
        builder.connectTimeout(basePar.getConnectionTime(), TimeUnit.SECONDS);
        if(basePar.isCache()){
//            builder.addInterceptor(new CookieInterceptor(basePar.isCache(),basePar.getUrl()));
            builder.addNetworkInterceptor(new CacheInterceptor());
        }else{
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request=chain.request().newBuilder()
                            .addHeader("android","android")
                            .build();
                    return chain.proceed(request);
                }
            });
        }

        /*创建retrofit对象*/
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(basePar.getBaseUrl())
                .build();


        /*rx处理*/
        ProgressSubscriber subscriber = new ProgressSubscriber(basePar);
        Observable observable = basePar.getObservable(retrofit)
                /*失败后的retry配置*/
                .retryWhen(new RetryWhenNetworkException())
                /*生命周期管理*/
//                .compose(basePar.getRxAppCompatActivity().bindToLifecycle())
                .compose(basePar.getRxAppCompatActivity().bindUntilEvent(ActivityEvent.DESTROY))
                /*http请求线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread())
                /*结果判断*/
                .map(basePar);


        /*链接式对象返回*/
        SoftReference<HttpOnNextListener> httpOnNextListener= basePar.getListener();
        if(httpOnNextListener!=null&&httpOnNextListener.get()!=null){
            httpOnNextListener.get().onNext(observable);
        }

        /*数据回调*/
        observable.subscribe(subscriber);
    }
}
