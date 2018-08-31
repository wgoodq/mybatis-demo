package cn.ok.mybatisdemo.async;

public class AsyncException extends RuntimeException {
    /**
     * 错误代码
     */
    private int code;

    public AsyncException() {
        super();
    }

    public AsyncException(String msg) {
        super(msg);
    }

    public AsyncException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
