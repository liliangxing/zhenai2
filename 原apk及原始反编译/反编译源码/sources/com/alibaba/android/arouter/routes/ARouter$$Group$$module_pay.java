package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.zhenai.pay.love_key.LoveKeyUnlockPayActivity;
import com.zhenai.pay.mail.v2.PayMailActivityV2;
import com.zhenai.pay.mail.v2.RenewalManageActivity;
import com.zhenai.pay.messager.PayMessagerActivity;
import com.zhenai.pay.provider.PayProvider;
import com.zhenai.pay.star.PayStarActivity;
import com.zhenai.pay.super_recommend.recharge.SuperRecommendRechargeActivity;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Group$$module_pay implements IRouteGroup {
    @Override // com.alibaba.android.arouter.facade.template.IRouteGroup
    public void loadInto(Map<String, RouteMeta> map) {
        RouteType routeType = RouteType.ACTIVITY;
        map.put("/module_pay/pay/LoveKeyUnlockPayActivity", RouteMeta.a(routeType, LoveKeyUnlockPayActivity.class, "/module_pay/pay/lovekeyunlockpayactivity", "module_pay", null, -1, Integer.MIN_VALUE));
        map.put("/module_pay/pay/PayMailActivityV2", RouteMeta.a(routeType, PayMailActivityV2.class, "/module_pay/pay/paymailactivityv2", "module_pay", null, -1, Integer.MIN_VALUE));
        map.put("/module_pay/pay/PayMessagerActivity", RouteMeta.a(routeType, PayMessagerActivity.class, "/module_pay/pay/paymessageractivity", "module_pay", null, -1, Integer.MIN_VALUE));
        map.put("/module_pay/pay/PayStarActivity", RouteMeta.a(routeType, PayStarActivity.class, "/module_pay/pay/paystaractivity", "module_pay", null, -1, Integer.MIN_VALUE));
        map.put("/module_pay/pay/SuperRecommendRechargeActivity", RouteMeta.a(routeType, SuperRecommendRechargeActivity.class, "/module_pay/pay/superrecommendrechargeactivity", "module_pay", null, -1, Integer.MIN_VALUE));
        map.put("/module_pay/provider/PayProvider", RouteMeta.a(RouteType.PROVIDER, PayProvider.class, "/module_pay/provider/payprovider", "module_pay", null, -1, Integer.MIN_VALUE));
        map.put("/module_pay/pya/RenewalManageActivity", RouteMeta.a(routeType, RenewalManageActivity.class, "/module_pay/pya/renewalmanageactivity", "module_pay", null, -1, Integer.MIN_VALUE));
    }
}
