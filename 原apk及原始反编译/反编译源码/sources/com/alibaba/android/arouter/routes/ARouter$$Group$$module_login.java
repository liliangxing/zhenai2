package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.zhenai.login.guide.GuideActivityV2;
import com.zhenai.login.help.LoginHelpHtmlActivity;
import com.zhenai.login.login.v8.LoginActivityV8;
import com.zhenai.login.login.v8.ModifyPhoneCheckMsgCodeActivityV8;
import com.zhenai.login.login.v8.RetrievePasswordActivity;
import com.zhenai.login.login_intercept_guide.UploadCardsHtmlActivity;
import com.zhenai.login.login_intercept_guide.concern_issue.MyConcernThingsActivity;
import com.zhenai.login.provider.LoginProvider;
import com.zhenai.login.register.ui.fragment.SupplementEducationFragment;
import com.zhenai.login.register.ui.fragment.SupplementHeightFragment;
import com.zhenai.login.register.ui.fragment.SupplementSalaryFragment;
import com.zhenai.login.register.ui.fragment.SupplementUploadAvatarFragment;
import com.zhenai.login.register.ui.fragment.SupplementWorkCityFragment;
import com.zhenai.login.take_photo.CropAvatarActivity;
import com.zhenai.login.take_photo.TakeAvatarActivity;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Group$$module_login implements IRouteGroup {
    @Override // com.alibaba.android.arouter.facade.template.IRouteGroup
    public void loadInto(Map<String, RouteMeta> map) {
        RouteType routeType = RouteType.ACTIVITY;
        map.put("/module_login/avatar/CropAvatarActivity", RouteMeta.a(routeType, CropAvatarActivity.class, "/module_login/avatar/cropavataractivity", "module_login", null, -1, Integer.MIN_VALUE));
        map.put("/module_login/avatar/TakeAvatarActivity", RouteMeta.a(routeType, TakeAvatarActivity.class, "/module_login/avatar/takeavataractivity", "module_login", null, -1, Integer.MIN_VALUE));
        map.put("/module_login/guide/GuideActivityV2", RouteMeta.a(routeType, GuideActivityV2.class, "/module_login/guide/guideactivityv2", "module_login", null, -1, Integer.MIN_VALUE));
        map.put("/module_login/helper/LoginHelpHtmlActivity", RouteMeta.a(routeType, LoginHelpHtmlActivity.class, "/module_login/helper/loginhelphtmlactivity", "module_login", null, -1, Integer.MIN_VALUE));
        RouteType routeType2 = RouteType.FRAGMENT;
        map.put("/module_login/login/SupplementEducationFragment", RouteMeta.a(routeType2, SupplementEducationFragment.class, "/module_login/login/supplementeducationfragment", "module_login", null, -1, Integer.MIN_VALUE));
        map.put("/module_login/login/SupplementEmptyUploadAvatarFragment", RouteMeta.a(routeType2, SupplementUploadAvatarFragment.class, "/module_login/login/supplementemptyuploadavatarfragment", "module_login", null, -1, Integer.MIN_VALUE));
        map.put("/module_login/login/SupplementHeightFragment", RouteMeta.a(routeType2, SupplementHeightFragment.class, "/module_login/login/supplementheightfragment", "module_login", null, -1, Integer.MIN_VALUE));
        map.put("/module_login/login/SupplementSalaryFragment", RouteMeta.a(routeType2, SupplementSalaryFragment.class, "/module_login/login/supplementsalaryfragment", "module_login", null, -1, Integer.MIN_VALUE));
        map.put("/module_login/login/SupplementWorkCityFragment", RouteMeta.a(routeType2, SupplementWorkCityFragment.class, "/module_login/login/supplementworkcityfragment", "module_login", null, -1, Integer.MIN_VALUE));
        map.put("/module_login/login/v8/LoginActivityV8", RouteMeta.a(routeType, LoginActivityV8.class, "/module_login/login/v8/loginactivityv8", "module_login", null, -1, Integer.MIN_VALUE));
        map.put("/module_login/login/v8/RetrievePasswordActivity", RouteMeta.a(routeType, RetrievePasswordActivity.class, "/module_login/login/v8/retrievepasswordactivity", "module_login", null, -1, Integer.MIN_VALUE));
        map.put("/module_login/mine/MyConcernThingsActivity", RouteMeta.a(routeType, MyConcernThingsActivity.class, "/module_login/mine/myconcernthingsactivity", "module_login", null, -1, Integer.MIN_VALUE));
        map.put("/module_login/modify/v8/ModifyPhoneCheckMsgCodeActivityV8", RouteMeta.a(routeType, ModifyPhoneCheckMsgCodeActivityV8.class, "/module_login/modify/v8/modifyphonecheckmsgcodeactivityv8", "module_login", null, -1, Integer.MIN_VALUE));
        map.put("/module_login/provider/LoginProvider", RouteMeta.a(RouteType.PROVIDER, LoginProvider.class, "/module_login/provider/loginprovider", "module_login", null, -1, Integer.MIN_VALUE));
        map.put("/module_login/upload/UploadCardsHtmlActivity", RouteMeta.a(routeType, UploadCardsHtmlActivity.class, "/module_login/upload/uploadcardshtmlactivity", "module_login", null, -1, Integer.MIN_VALUE));
    }
}
