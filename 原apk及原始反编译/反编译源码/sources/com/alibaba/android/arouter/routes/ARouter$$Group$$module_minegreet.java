package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.zhenai.mine.greetv8.GreetActivityV8;
import com.zhenai.mine.greetv8.GreetEditActivityV8;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Group$$module_minegreet implements IRouteGroup {
    @Override // com.alibaba.android.arouter.facade.template.IRouteGroup
    public void loadInto(Map<String, RouteMeta> map) {
        RouteType routeType = RouteType.ACTIVITY;
        map.put("/module_minegreet/GreetActivityV8", RouteMeta.a(routeType, GreetActivityV8.class, "/module_minegreet/greetactivityv8", "module_minegreet", null, -1, Integer.MIN_VALUE));
        map.put("/module_minegreet/GreetEditActivityV8", RouteMeta.a(routeType, GreetEditActivityV8.class, "/module_minegreet/greeteditactivityv8", "module_minegreet", new HashMap<String, Integer>() { // from class: com.alibaba.android.arouter.routes.ARouter$$Group$$module_minegreet.1
            {
                put("greet_min_size", 3);
                put("greet_content", 8);
                put("greet_size", 3);
                put("is_create_by_ai", 0);
                put("greet_introduce_content", 8);
            }
        }, -1, Integer.MIN_VALUE));
    }
}
