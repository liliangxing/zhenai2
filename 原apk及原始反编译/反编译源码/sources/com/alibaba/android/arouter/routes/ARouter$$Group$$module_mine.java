package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.zhenai.mine.interaction.live_each_other.LiveEachOtherActivity;
import com.zhenai.mine.interaction.visited.InvisibleVisitActivity;
import com.zhenai.mine.introduce.IntroduceActivity;
import com.zhenai.mine.my_opinion.MoreOpinionActivity;
import com.zhenai.mine.my_opinion.MyOpinionListActivityV8;
import com.zhenai.mine.privilege_center.PrivilegeCenterActivity;
import com.zhenai.mine.profilev2.activity.MineProfileV8Activity;
import com.zhenai.mine.profilev2.dialog.ProfileCompleteWindowV8Activity;
import com.zhenai.mine.provider.MineProvider;
import com.zhenai.mine.setting.account.AccountSafeActivity;
import com.zhenai.mine.setting.account.housekeeper.HousekeeperSwitchActivity;
import com.zhenai.mine.setting.phone.ui.ModifyPhone2Activity;
import com.zhenai.mine.setting.update_app.provider.UpgradeAppProvider;
import com.zhenai.mine.setting.update_app.view.ForceUpdateAppActivityV8;
import com.zhenai.mine.setting.view.AboutActivityV8;
import com.zhenai.mine.setting.view.AccountCancelHtmlActivity;
import com.zhenai.mine.setting.view.FeedbackActivityV8;
import com.zhenai.mine.setting.view.HelperDebugActivity;
import com.zhenai.mine.setting.view.HtmlReportBadInfoActivity;
import com.zhenai.mine.setting.view.LogFileUploadActivity;
import com.zhenai.mine.setting.view.OnlineCustomerServiceActivity;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Group$$module_mine implements IRouteGroup {
    @Override // com.alibaba.android.arouter.facade.template.IRouteGroup
    public void loadInto(Map<String, RouteMeta> map) {
        RouteType routeType = RouteType.ACTIVITY;
        map.put("/module_mine/mine/AboutActivity", RouteMeta.a(routeType, AboutActivityV8.class, "/module_mine/mine/aboutactivity", "module_mine", null, -1, Integer.MIN_VALUE));
        map.put("/module_mine/mine/AccountCancelHtmlActivity", RouteMeta.a(routeType, AccountCancelHtmlActivity.class, "/module_mine/mine/accountcancelhtmlactivity", "module_mine", null, -1, Integer.MIN_VALUE));
        map.put("/module_mine/mine/AccountSafeActivity", RouteMeta.a(routeType, AccountSafeActivity.class, "/module_mine/mine/accountsafeactivity", "module_mine", null, -1, Integer.MIN_VALUE));
        map.put("/module_mine/mine/HousekeeperSwitchActivity", RouteMeta.a(routeType, HousekeeperSwitchActivity.class, "/module_mine/mine/housekeeperswitchactivity", "module_mine", null, -1, Integer.MIN_VALUE));
        map.put("/module_mine/mine/IntroduceActivity", RouteMeta.a(routeType, IntroduceActivity.class, "/module_mine/mine/introduceactivity", "module_mine", null, -1, Integer.MIN_VALUE));
        map.put("/module_mine/mine/LiveEachOtherActivity", RouteMeta.a(routeType, LiveEachOtherActivity.class, "/module_mine/mine/liveeachotheractivity", "module_mine", null, -1, Integer.MIN_VALUE));
        map.put("/module_mine/mine/ModifyPhone2Activity", RouteMeta.a(routeType, ModifyPhone2Activity.class, "/module_mine/mine/modifyphone2activity", "module_mine", null, -1, Integer.MIN_VALUE));
        map.put("/module_mine/mine/MoreOpinionActivity", RouteMeta.a(routeType, MoreOpinionActivity.class, "/module_mine/mine/moreopinionactivity", "module_mine", null, -1, Integer.MIN_VALUE));
        map.put("/module_mine/mine/MyOpinionListActivityV8", RouteMeta.a(routeType, MyOpinionListActivityV8.class, "/module_mine/mine/myopinionlistactivityv8", "module_mine", null, -1, Integer.MIN_VALUE));
        map.put("/module_mine/mine/MyProfileActivityV8", RouteMeta.a(routeType, MineProfileV8Activity.class, "/module_mine/mine/myprofileactivityv8", "module_mine", null, -1, Integer.MIN_VALUE));
        map.put("/module_mine/mine/OnlineCustomServiceActivity", RouteMeta.a(routeType, OnlineCustomerServiceActivity.class, "/module_mine/mine/onlinecustomserviceactivity", "module_mine", null, -1, Integer.MIN_VALUE));
        map.put("/module_mine/mine/PrivilegeCenterActivity", RouteMeta.a(routeType, PrivilegeCenterActivity.class, "/module_mine/mine/privilegecenteractivity", "module_mine", null, -1, Integer.MIN_VALUE));
        map.put("/module_mine/mine/ProfileCompleteWindowV8Activity", RouteMeta.a(routeType, ProfileCompleteWindowV8Activity.class, "/module_mine/mine/profilecompletewindowv8activity", "module_mine", null, -1, Integer.MIN_VALUE));
        RouteType routeType2 = RouteType.PROVIDER;
        map.put("/module_mine/provider/MineProvider", RouteMeta.a(routeType2, MineProvider.class, "/module_mine/provider/mineprovider", "module_mine", null, -1, Integer.MIN_VALUE));
        map.put("/module_mine/provider/UpgradeAppProvider", RouteMeta.a(routeType2, UpgradeAppProvider.class, "/module_mine/provider/upgradeappprovider", "module_mine", null, -1, Integer.MIN_VALUE));
        map.put("/module_mine/setting/FeedbackActivityV8", RouteMeta.a(routeType, FeedbackActivityV8.class, "/module_mine/setting/feedbackactivityv8", "module_mine", null, -1, Integer.MIN_VALUE));
        map.put("/module_mine/setting/ForceUpdateAppActivityV8", RouteMeta.a(routeType, ForceUpdateAppActivityV8.class, "/module_mine/setting/forceupdateappactivityv8", "module_mine", null, -1, Integer.MIN_VALUE));
        map.put("/module_mine/setting/HelperDebugActivity", RouteMeta.a(routeType, HelperDebugActivity.class, "/module_mine/setting/helperdebugactivity", "module_mine", null, -1, Integer.MIN_VALUE));
        map.put("/module_mine/setting/HtmlReportBadInfoActivity", RouteMeta.a(routeType, HtmlReportBadInfoActivity.class, "/module_mine/setting/htmlreportbadinfoactivity", "module_mine", null, -1, Integer.MIN_VALUE));
        map.put("/module_mine/setting/LogFileUploadActivity", RouteMeta.a(routeType, LogFileUploadActivity.class, "/module_mine/setting/logfileuploadactivity", "module_mine", null, -1, Integer.MIN_VALUE));
        map.put("/module_mine/visited/InvisibleVisitedActivity", RouteMeta.a(routeType, InvisibleVisitActivity.class, "/module_mine/visited/invisiblevisitedactivity", "module_mine", null, -1, Integer.MIN_VALUE));
    }
}
