package com.alibaba.android.arouter.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.arouter.thread.DefaultPoolExecutor;
import dalvik.system.DexFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public class ClassUtils {
    public static final String a = "code_cache" + File.separator + "secondary-dexes";

    public static Set<String> a(Context context, final String str) throws InterruptedException, PackageManager.NameNotFoundException, IOException {
        final HashSet hashSet = new HashSet();
        List<String> listC = c(context);
        final CountDownLatch countDownLatch = new CountDownLatch(listC.size());
        for (final String str2 : listC) {
            DefaultPoolExecutor.c().execute(new Runnable() { // from class: com.alibaba.android.arouter.utils.ClassUtils.1
                @Override // java.lang.Runnable
                public void run() {
                    DexFile dexFile = null;
                    try {
                        try {
                            if (str2.endsWith(".zip")) {
                                dexFile = DexFile.loadDex(str2, str2 + ".tmp", 0);
                            } else {
                                dexFile = new DexFile(str2);
                            }
                            Enumeration<String> enumerationEntries = dexFile.entries();
                            while (enumerationEntries.hasMoreElements()) {
                                String strNextElement = enumerationEntries.nextElement();
                                if (strNextElement.startsWith(str)) {
                                    hashSet.add(strNextElement);
                                }
                            }
                        } catch (Throwable unused) {
                            if (dexFile != null) {
                            }
                            countDownLatch.countDown();
                        }
                        dexFile.close();
                    } catch (Throwable unused2) {
                    }
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        StringBuilder sb = new StringBuilder();
        sb.append("Filter ");
        sb.append(hashSet.size());
        sb.append(" classes by packageName <");
        sb.append(str);
        sb.append(">");
        return hashSet;
    }

    public static SharedPreferences b(Context context) {
        return context.getSharedPreferences("multidex.version", Build.VERSION.SDK_INT < 11 ? 0 : 4);
    }

    public static List<String> c(Context context) throws PackageManager.NameNotFoundException, IOException {
        ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
        File file = new File(applicationInfo.sourceDir);
        ArrayList arrayList = new ArrayList();
        arrayList.add(applicationInfo.sourceDir);
        String str = file.getName() + ".classes";
        if (!d()) {
            int i = b(context).getInt("dex.number", 1);
            File file2 = new File(applicationInfo.dataDir, a);
            for (int i2 = 2; i2 <= i; i2++) {
                File file3 = new File(file2, str + i2 + ".zip");
                if (!file3.isFile()) {
                    throw new IOException("Missing extracted secondary dex file '" + file3.getPath() + "'");
                }
                arrayList.add(file3.getAbsolutePath());
            }
        }
        if (ARouter.c()) {
            arrayList.addAll(f(applicationInfo));
        }
        return arrayList;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x001d A[PHI: r1
      0x001d: PHI (r1v8 java.lang.String) = (r1v6 java.lang.String), (r1v6 java.lang.String), (r1v9 java.lang.String) binds: [B:13:0x004a, B:15:0x004e, B:6:0x001b] A[DONT_GENERATE, DONT_INLINE]] */
    public static boolean d() {
        boolean z = false;
        String str = null;
        try {
            if (e()) {
                str = "'YunOS'";
                if (Integer.valueOf(System.getProperty("ro.build.version.sdk")).intValue() >= 21) {
                    z = true;
                }
            } else {
                str = "'Android'";
                String property = System.getProperty("java.vm.version");
                if (property != null) {
                    Matcher matcher = Pattern.compile("(\\d+)\\.(\\d+)(\\.\\d+)?").matcher(property);
                    if (matcher.matches()) {
                        int i = Integer.parseInt(matcher.group(1));
                        int i2 = Integer.parseInt(matcher.group(2));
                        if (i > 2 || (i == 2 && i2 >= 1)) {
                            z = true;
                        }
                    }
                }
            }
        } catch (NumberFormatException | Exception unused) {
        }
        StringBuilder sb = new StringBuilder();
        sb.append("VM with name ");
        sb.append(str);
        sb.append(z ? " has multidex support" : " does not have multidex support");
        return z;
    }

    public static boolean e() {
        try {
            String property = System.getProperty("ro.yunos.version");
            String property2 = System.getProperty("java.vm.name");
            return (property2 != null && property2.toLowerCase().contains("lemur")) || (property != null && property.trim().length() > 0);
        } catch (Exception unused) {
            return false;
        }
    }

    public static List<String> f(ApplicationInfo applicationInfo) {
        String[] strArr;
        ArrayList arrayList = new ArrayList();
        if (Build.VERSION.SDK_INT < 21 || (strArr = applicationInfo.splitSourceDirs) == null) {
            try {
                File file = new File((String) Class.forName("com.android.tools.fd.runtime.Paths").getMethod("getDexFileDirectory", String.class).invoke(null, applicationInfo.packageName));
                if (file.exists() && file.isDirectory()) {
                    for (File file2 : file.listFiles()) {
                        if (file2 != null && file2.exists() && file2.isFile() && file2.getName().endsWith(".dex")) {
                            arrayList.add(file2.getAbsolutePath());
                        }
                    }
                }
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder();
                sb.append("InstantRun support error, ");
                sb.append(e.getMessage());
            }
        } else {
            arrayList.addAll(Arrays.asList(strArr));
        }
        return arrayList;
    }
}
