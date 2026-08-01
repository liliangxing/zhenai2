package com.alibaba.android.arouter.core;

import android.content.Context;
import android.util.LruCache;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.AutowiredService;
import com.alibaba.android.arouter.facade.template.ISyringe;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@Route
public class AutowiredServiceImpl implements AutowiredService {
    public LruCache<String, ISyringe> a;
    public List<String> b;

    public final void K3(Object obj, Class<?> cls) {
        if (cls == null) {
            cls = obj.getClass();
        }
        ISyringe iSyringeL3 = L3(cls);
        if (iSyringeL3 != null) {
            iSyringeL3.inject(obj);
        }
        Class<? super Object> superclass = cls.getSuperclass();
        if (superclass == null || superclass.getName().startsWith("android")) {
            return;
        }
        K3(obj, superclass);
    }

    public final ISyringe L3(Class<?> cls) {
        String name = cls.getName();
        try {
            if (this.b.contains(name)) {
                return null;
            }
            ISyringe iSyringe = this.a.get(name);
            if (iSyringe == null) {
                iSyringe = (ISyringe) Class.forName(cls.getName() + "$$ARouter$$Autowired").getConstructor(new Class[0]).newInstance(new Object[0]);
            }
            this.a.put(name, iSyringe);
            return iSyringe;
        } catch (Exception unused) {
            this.b.add(name);
            return null;
        }
    }

    @Override // com.alibaba.android.arouter.facade.service.AutowiredService
    public void T(Object obj) {
        K3(obj, null);
    }

    @Override // com.alibaba.android.arouter.facade.template.IProvider
    public void init(Context context) {
        this.a = new LruCache<>(50);
        this.b = new ArrayList();
    }
}
