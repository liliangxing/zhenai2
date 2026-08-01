package com.alibaba.android.arouter.facade;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.alibaba.android.arouter.launcher.ARouter;
import java.io.Serializable;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class Postcard extends RouteMeta {
    public Uri j;
    public Object k;
    public Bundle l;
    public int m;
    public int n;
    public IProvider o;
    public boolean p;
    public Context q;
    public String r;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public Bundle f0s;
    public int t;
    public int u;

    public Postcard() {
        this(null, null);
    }

    public boolean A() {
        return this.p;
    }

    public Object B() {
        return C(null);
    }

    public Object C(Context context) {
        return D(context, null);
    }

    public Object D(Context context, NavigationCallback navigationCallback) {
        return ARouter.d().g(context, this, -1, navigationCallback);
    }

    public void E(Activity activity, int i) {
        F(activity, i, null);
    }

    public void F(Activity activity, int i, NavigationCallback navigationCallback) {
        ARouter.d().g(activity, this, i, navigationCallback);
    }

    public void G(Context context) {
        this.q = context;
    }

    public Postcard H(IProvider iProvider) {
        this.o = iProvider;
        return this;
    }

    public Postcard I(Object obj) {
        this.k = obj;
        return this;
    }

    public Postcard J(Uri uri) {
        this.j = uri;
        return this;
    }

    public Postcard K(@Nullable String str, boolean z) {
        this.l.putBoolean(str, z);
        return this;
    }

    public Postcard L(@Nullable String str, @Nullable Bundle bundle) {
        this.l.putBundle(str, bundle);
        return this;
    }

    public Postcard M(@Nullable String str, byte b) {
        this.l.putByte(str, b);
        return this;
    }

    public Postcard N(@Nullable String str, double d) {
        this.l.putDouble(str, d);
        return this;
    }

    public Postcard O(int i) {
        this.m = i;
        return this;
    }

    public Postcard P(@Nullable String str, float f) {
        this.l.putFloat(str, f);
        return this;
    }

    public Postcard Q(@Nullable String str, int i) {
        this.l.putInt(str, i);
        return this;
    }

    public Postcard R(@Nullable String str, @Nullable ArrayList<Integer> arrayList) {
        this.l.putIntegerArrayList(str, arrayList);
        return this;
    }

    public Postcard S(@Nullable String str, long j) {
        this.l.putLong(str, j);
        return this;
    }

    public Postcard T(@Nullable String str, @Nullable Serializable serializable) {
        this.l.putSerializable(str, serializable);
        return this;
    }

    public Postcard U(@Nullable String str, short s2) {
        this.l.putShort(str, s2);
        return this;
    }

    public Postcard V(@Nullable String str, @Nullable String str2) {
        this.l.putString(str, str2);
        return this;
    }

    public Postcard W(@Nullable String str, @Nullable ArrayList<String> arrayList) {
        this.l.putStringArrayList(str, arrayList);
        return this;
    }

    public Postcard X(int i, int i2) {
        this.t = i;
        this.u = i2;
        return this;
    }

    public String o() {
        return this.r;
    }

    public Context p() {
        return this.q;
    }

    public int q() {
        return this.t;
    }

    public int r() {
        return this.u;
    }

    public Bundle s() {
        return this.l;
    }

    public int t() {
        return this.m;
    }

    @Override // com.alibaba.android.arouter.facade.model.RouteMeta
    public String toString() {
        return "Postcard{uri=" + this.j + ", tag=" + this.k + ", mBundle=" + this.l + ", flags=" + this.m + ", timeout=" + this.n + ", provider=" + this.o + ", greenChannel=" + this.p + ", optionsCompat=" + this.f0s + ", enterAnim=" + this.t + ", exitAnim=" + this.u + "}\n" + super.toString();
    }

    public Bundle u() {
        return this.f0s;
    }

    public IProvider v() {
        return this.o;
    }

    public Object w() {
        return this.k;
    }

    public int x() {
        return this.n;
    }

    public Uri y() {
        return this.j;
    }

    public Postcard z() {
        this.p = true;
        return this;
    }

    public Postcard(String str, String str2) {
        this(str, str2, null, null);
    }

    public Postcard(String str, String str2, Uri uri, Bundle bundle) {
        this.m = 0;
        this.n = 300;
        this.t = -1;
        this.u = -1;
        l(str);
        k(str2);
        J(uri);
        this.l = bundle == null ? new Bundle() : bundle;
    }
}
