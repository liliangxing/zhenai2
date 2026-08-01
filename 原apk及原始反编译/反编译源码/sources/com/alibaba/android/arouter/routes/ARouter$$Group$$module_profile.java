package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.zhenai.profile.ads.AdsProvider;
import com.zhenai.profile.background.ProfileChangeBackgroundActivity;
import com.zhenai.profile.hobby.activity.MyHobbyActivity;
import com.zhenai.profile.label.MyLabelActivity;
import com.zhenai.profile.other_profile.OtherProfileActivity7;
import com.zhenai.profile.other_profile.OtherProfileFragment7;
import com.zhenai.profile.provider.ProfileProvider;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Group$$module_profile implements IRouteGroup {
    @Override // com.alibaba.android.arouter.facade.template.IRouteGroup
    public void loadInto(Map<String, RouteMeta> map) {
        RouteType routeType = RouteType.ACTIVITY;
        map.put("/module_profile/mine/MyHobbyActivity", RouteMeta.a(routeType, MyHobbyActivity.class, "/module_profile/mine/myhobbyactivity", "module_profile", null, -1, Integer.MIN_VALUE));
        map.put("/module_profile/profile/MyLabelActivity", RouteMeta.a(routeType, MyLabelActivity.class, "/module_profile/profile/mylabelactivity", "module_profile", null, -1, Integer.MIN_VALUE));
        map.put("/module_profile/profile/OtherProfileActivity7", RouteMeta.a(routeType, OtherProfileActivity7.class, "/module_profile/profile/otherprofileactivity7", "module_profile", null, -1, Integer.MIN_VALUE));
        map.put("/module_profile/profile/OtherProfileFragment7", RouteMeta.a(RouteType.FRAGMENT, OtherProfileFragment7.class, "/module_profile/profile/otherprofilefragment7", "module_profile", null, -1, Integer.MIN_VALUE));
        map.put("/module_profile/profile/ProfileChangeBackgroundActivity", RouteMeta.a(routeType, ProfileChangeBackgroundActivity.class, "/module_profile/profile/profilechangebackgroundactivity", "module_profile", null, -1, Integer.MIN_VALUE));
        RouteType routeType2 = RouteType.PROVIDER;
        map.put("/module_profile/provider/AdsProvider", RouteMeta.a(routeType2, AdsProvider.class, "/module_profile/provider/adsprovider", "module_profile", null, -1, Integer.MIN_VALUE));
        map.put("/module_profile/provider/ProfileProvider", RouteMeta.a(routeType2, ProfileProvider.class, "/module_profile/provider/profileprovider", "module_profile", null, -1, Integer.MIN_VALUE));
    }
}
