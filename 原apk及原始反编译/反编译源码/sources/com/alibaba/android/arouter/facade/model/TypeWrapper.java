package com.alibaba.android.arouter.facade.model;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: classes.dex */
public class TypeWrapper<T> {
    public final Type a = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
}
