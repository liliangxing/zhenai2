package com.alibaba.android.arouter.core;

import android.content.Context;
import com.alibaba.android.arouter.exception.HandlerException;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.service.InterceptorService;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.arouter.thread.CancelableCountDownLatch;
import com.alibaba.android.arouter.utils.MapUtils;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
@Route
public class InterceptorServiceImpl implements InterceptorService {
    public static boolean a;
    public static final Object b = new Object();

    public static void K3(final int i, final CancelableCountDownLatch cancelableCountDownLatch, final Postcard postcard) {
        if (i < Warehouse.interceptors.size()) {
            Warehouse.interceptors.get(i).o3(postcard, new InterceptorCallback() { // from class: com.alibaba.android.arouter.core.InterceptorServiceImpl.2
                @Override // com.alibaba.android.arouter.facade.callback.InterceptorCallback
                public void a(Postcard postcard2) {
                    cancelableCountDownLatch.countDown();
                    InterceptorServiceImpl.K3(i + 1, cancelableCountDownLatch, postcard2);
                }

                @Override // com.alibaba.android.arouter.facade.callback.InterceptorCallback
                public void b(Throwable th) {
                    Postcard postcard2 = postcard;
                    if (th == null) {
                        th = new HandlerException("No message.");
                    }
                    postcard2.I(th);
                    cancelableCountDownLatch.a();
                }
            });
        }
    }

    public static void O3() {
        synchronized (b) {
            while (!a) {
                try {
                    b.wait(10000L);
                } catch (InterruptedException e) {
                    throw new HandlerException("ARouter::Interceptor init cost too much time error! reason = [" + e.getMessage() + "]");
                }
            }
        }
    }

    @Override // com.alibaba.android.arouter.facade.service.InterceptorService
    public void d2(final Postcard postcard, final InterceptorCallback interceptorCallback) {
        if (!MapUtils.b(Warehouse.interceptorsIndex)) {
            interceptorCallback.a(postcard);
            return;
        }
        O3();
        if (a) {
            LogisticsCenter.b.execute(new Runnable(this) { // from class: com.alibaba.android.arouter.core.InterceptorServiceImpl.1
                @Override // java.lang.Runnable
                public void run() {
                    CancelableCountDownLatch cancelableCountDownLatch = new CancelableCountDownLatch(Warehouse.interceptors.size());
                    try {
                        InterceptorServiceImpl.K3(0, cancelableCountDownLatch, postcard);
                        cancelableCountDownLatch.await(postcard.x(), TimeUnit.SECONDS);
                        if (cancelableCountDownLatch.getCount() > 0) {
                            interceptorCallback.b(new HandlerException("The interceptor processing timed out."));
                        } else if (postcard.w() != null) {
                            interceptorCallback.b((Throwable) postcard.w());
                        } else {
                            interceptorCallback.a(postcard);
                        }
                    } catch (Exception e) {
                        interceptorCallback.b(e);
                    }
                }
            });
        } else {
            interceptorCallback.b(new HandlerException("Interceptors initialization takes too much time."));
        }
    }

    @Override // com.alibaba.android.arouter.facade.template.IProvider
    public void init(final Context context) {
        LogisticsCenter.b.execute(new Runnable(this) { // from class: com.alibaba.android.arouter.core.InterceptorServiceImpl.3
            @Override // java.lang.Runnable
            public void run() {
                if (MapUtils.b(Warehouse.interceptorsIndex)) {
                    Iterator<Map.Entry<Integer, Class<? extends IInterceptor>>> it = Warehouse.interceptorsIndex.entrySet().iterator();
                    while (it.hasNext()) {
                        Class<? extends IInterceptor> value = it.next().getValue();
                        try {
                            IInterceptor iInterceptorNewInstance = value.getConstructor(new Class[0]).newInstance(new Object[0]);
                            iInterceptorNewInstance.init(context);
                            Warehouse.interceptors.add(iInterceptorNewInstance);
                        } catch (Exception e) {
                            throw new HandlerException("ARouter::ARouter init interceptor error! name = [" + value.getName() + "], reason = [" + e.getMessage() + "]");
                        }
                    }
                    boolean unused = InterceptorServiceImpl.a = true;
                    ARouter.c.info("ARouter::", "ARouter interceptors init over.");
                    synchronized (InterceptorServiceImpl.b) {
                        InterceptorServiceImpl.b.notifyAll();
                    }
                }
            }
        });
    }
}
