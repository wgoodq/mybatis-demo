package cn.ok.mybatisdemo.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

@Slf4j
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {

        if (ex instanceof AsyncException) {
            AsyncException asyncException = (AsyncException) ex;
            log.error("asyncException:" + asyncException.getMessage());
        }

        log.error("Exception :", ex);
    }
}
