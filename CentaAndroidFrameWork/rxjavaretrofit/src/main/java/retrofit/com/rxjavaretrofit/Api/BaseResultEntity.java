package retrofit.com.rxjavaretrofit.Api;

/**
 * 回调信息统一封装类
 */
public class BaseResultEntity<T> {
    //  判断标示
    private boolean flag;
    //  提示信息
    private String msg;
    //  显示数据（用户需要关心的数据）
    private T data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
