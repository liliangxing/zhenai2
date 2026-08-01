package com.alibaba.android.arouter.thread;

import androidx.annotation.NonNull;
import com.alibaba.android.arouter.launcher.ARouter;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public class DefaultThreadFactory implements ThreadFactory {
    public static final AtomicInteger e = new AtomicInteger(1);
    public final AtomicInteger b = new AtomicInteger(1);
    public final ThreadGroup c;
    public final String d;

    public DefaultThreadFactory() {
        SecurityManager securityManager = System.getSecurityManager();
        this.c = securityManager != null ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.d = "ARouter task pool No." + e.getAndIncrement() + ", thread No.";
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(@NonNull Runnable runnable) {
        String str = this.d + this.b.getAndIncrement();
        ARouter.c.info("ARouter::", "Thread production, name is [" + str + "]");
        Thread thread = new Thread(this.c, runnable, str, 0L);
        if (thread.isDaemon()) {
            thread.setDaemon(false);
        }
        if (thread.getPriority() != 5) {
            thread.setPriority(5);
        }
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(this) { // from class: com.alibaba.android.arouter.thread.DefaultThreadFactory.1
            @Override // java.lang.Thread.UncaughtExceptionHandler
            public void uncaughtException(Thread thread2, Throwable th) {
                ARouter.c.info("ARouter::", "Running task appeared exception! Thread [" + thread2.getName() + "], because [" + th.getMessage() + "]");
            }
        });
        return thread;
    }
}
