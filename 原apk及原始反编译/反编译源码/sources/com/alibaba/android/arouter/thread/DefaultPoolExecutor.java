package com.alibaba.android.arouter.thread;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.arouter.utils.TextUtils;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class DefaultPoolExecutor extends ThreadPoolExecutor {
    public static final int b;
    public static final int c;
    public static final int d;
    public static volatile DefaultPoolExecutor e;

    static {
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        b = iAvailableProcessors;
        int i = iAvailableProcessors + 1;
        c = i;
        d = i;
    }

    public DefaultPoolExecutor(int i, int i2, long j, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue, ThreadFactory threadFactory) {
        super(i, i2, j, timeUnit, blockingQueue, threadFactory, new RejectedExecutionHandler() { // from class: com.alibaba.android.arouter.thread.DefaultPoolExecutor.1
            @Override // java.util.concurrent.RejectedExecutionHandler
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
                ARouter.c.r0("ARouter::", "Task rejected, too many task!");
            }
        });
    }

    public static DefaultPoolExecutor c() {
        if (e == null) {
            synchronized (DefaultPoolExecutor.class) {
                if (e == null) {
                    e = new DefaultPoolExecutor(c, d, 30L, TimeUnit.SECONDS, new ArrayBlockingQueue(64), new DefaultThreadFactory());
                }
            }
        }
        return e;
    }

    @Override // java.util.concurrent.ThreadPoolExecutor
    public void afterExecute(Runnable runnable, Throwable th) {
        super.afterExecute(runnable, th);
        if (th == null && (runnable instanceof Future)) {
            try {
                ((Future) runnable).get();
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            } catch (CancellationException e2) {
                th = e2;
            } catch (ExecutionException e3) {
                th = e3.getCause();
            }
        }
        if (th != null) {
            ARouter.c.u0("ARouter::", "Running task appeared exception! Thread [" + Thread.currentThread().getName() + "], because [" + th.getMessage() + "]\n" + TextUtils.a(th.getStackTrace()));
        }
    }
}
