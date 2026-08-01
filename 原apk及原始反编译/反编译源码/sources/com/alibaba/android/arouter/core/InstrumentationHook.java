package com.alibaba.android.arouter.core;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.arouter.utils.TextUtils;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public class InstrumentationHook extends Instrumentation {
    @Override // android.app.Instrumentation
    public Activity newActivity(ClassLoader classLoader, String str, Intent intent) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        String[] stringArrayExtra;
        Class<?> clsLoadClass = classLoader.loadClass(str);
        Object objNewInstance = clsLoadClass.newInstance();
        if (ARouter.b() && (stringArrayExtra = intent.getStringArrayExtra("wmHzgD4lOj5o4241")) != null && stringArrayExtra.length > 0) {
            for (String str2 : stringArrayExtra) {
                Object obj = intent.getExtras().get(TextUtils.b(str2));
                if (obj != null) {
                    try {
                        Field declaredField = clsLoadClass.getDeclaredField(TextUtils.b(str2));
                        declaredField.setAccessible(true);
                        declaredField.set(objNewInstance, obj);
                    } catch (Exception e) {
                        ARouter.c.r0("ARouter::", "Inject values for activity error! [" + e.getMessage() + "]");
                    }
                }
            }
        }
        return (Activity) objNewInstance;
    }
}
