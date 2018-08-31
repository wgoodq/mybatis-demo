package cn.ok.mybatisdemo.entity;

import cn.ok.mybatisdemo.mapper.smp.SmpUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
@Slf4j
public class AsyncTask {

    private final SmpUserMapper smpUserMapper;

    @Autowired
    public AsyncTask(SmpUserMapper smpUserMapper) {
        this.smpUserMapper = smpUserMapper;
    }

    @Async
    public void dealNoReturnTask(String password) {
        log.info("返回值为void的异步调用开始" + Thread.currentThread().getName());

        if (password.equalsIgnoreCase("password")) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        smpUserMapper.doUpdate(password);

        log.info("返回值为void的异步调用结束" + Thread.currentThread().getName());
    }

    @Async
    public Future<String> dealHaveReturnTask(int i) {
        log.info("asyncInvokeReturnFuture, parementer=" + i);
        Future<String> future;
        try {
            Thread.sleep(1000 * i);
            future = new AsyncResult<>("success:" + i);
        } catch (InterruptedException e) {
            future = new AsyncResult<>("error");
        }
        return future;
    }
}
