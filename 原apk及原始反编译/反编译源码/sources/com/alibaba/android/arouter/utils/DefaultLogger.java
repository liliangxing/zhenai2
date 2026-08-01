package com.alibaba.android.arouter.utils;

import com.alibaba.android.arouter.facade.template.ILogger;

/* JADX INFO: loaded from: classes.dex */
public class DefaultLogger implements ILogger {
    public static boolean b;
    public static boolean c;
    public String a;

    public DefaultLogger() {
        this.a = "ARouter";
    }

    public static String b(StackTraceElement stackTraceElement) {
        StringBuilder sb = new StringBuilder("[");
        if (c) {
            String name = Thread.currentThread().getName();
            String fileName = stackTraceElement.getFileName();
            String className = stackTraceElement.getClassName();
            String methodName = stackTraceElement.getMethodName();
            long id = Thread.currentThread().getId();
            int lineNumber = stackTraceElement.getLineNumber();
            sb.append("ThreadId=");
            sb.append(id);
            sb.append(" & ");
            sb.append("ThreadName=");
            sb.append(name);
            sb.append(" & ");
            sb.append("FileName=");
            sb.append(fileName);
            sb.append(" & ");
            sb.append("ClassName=");
            sb.append(className);
            sb.append(" & ");
            sb.append("MethodName=");
            sb.append(methodName);
            sb.append(" & ");
            sb.append("LineNumber=");
            sb.append(lineNumber);
        }
        sb.append(" ] ");
        return sb.toString();
    }

    public String a() {
        return this.a;
    }

    @Override // com.alibaba.android.arouter.facade.template.ILogger
    public void debug(String str, String str2) {
        if (b) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            if (android.text.TextUtils.isEmpty(str)) {
                a();
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(b(stackTraceElement));
        }
    }

    @Override // com.alibaba.android.arouter.facade.template.ILogger
    public void info(String str, String str2) {
        if (b) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            if (android.text.TextUtils.isEmpty(str)) {
                a();
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(b(stackTraceElement));
        }
    }

    @Override // com.alibaba.android.arouter.facade.template.ILogger
    public void r0(String str, String str2) {
        if (b) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            if (android.text.TextUtils.isEmpty(str)) {
                a();
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(b(stackTraceElement));
        }
    }

    @Override // com.alibaba.android.arouter.facade.template.ILogger
    public void s0(String str, String str2, Throwable th) {
        if (b && android.text.TextUtils.isEmpty(str)) {
            a();
        }
    }

    @Override // com.alibaba.android.arouter.facade.template.ILogger
    public void t0(boolean z) {
        b = z;
    }

    @Override // com.alibaba.android.arouter.facade.template.ILogger
    public void u0(String str, String str2) {
        if (b) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            if (android.text.TextUtils.isEmpty(str)) {
                a();
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(b(stackTraceElement));
        }
    }

    public DefaultLogger(String str) {
        this.a = "ARouter";
        this.a = str;
    }
}
