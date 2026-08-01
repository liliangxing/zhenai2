package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.zhenai.psychology_test.GuidePsyTestActivity;
import com.zhenai.psychology_test.MarriageViewsReportActivity;
import com.zhenai.psychology_test.MyMarriageViewsActivity;
import com.zhenai.psychology_test.MyMarriageViewsHeadActivity;
import com.zhenai.psychology_test.OtherMarriageOpinionActivity;
import com.zhenai.psychology_test.OtherMarriageViewActivity;
import com.zhenai.psychology_test.view.activity.MarriageTestWithEditAnswerActivity;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Group$$psychology_test implements IRouteGroup {
    @Override // com.alibaba.android.arouter.facade.template.IRouteGroup
    public void loadInto(Map<String, RouteMeta> map) {
        RouteType routeType = RouteType.ACTIVITY;
        map.put("/psychology_test/mine/MarriageTestWithEditAnswerActivity", RouteMeta.a(routeType, MarriageTestWithEditAnswerActivity.class, "/psychology_test/mine/marriagetestwitheditansweractivity", "psychology_test", new HashMap<String, Integer>() { // from class: com.alibaba.android.arouter.routes.ARouter$$Group$$psychology_test.1
            {
                put("extra_question_id", 3);
            }
        }, -1, Integer.MIN_VALUE));
        map.put("/psychology_test/mine/MyMarriageViewsActivity", RouteMeta.a(routeType, MyMarriageViewsActivity.class, "/psychology_test/mine/mymarriageviewsactivity", "psychology_test", null, -1, Integer.MIN_VALUE));
        map.put("/psychology_test/mine/MyMarriageViewsHeadActivity", RouteMeta.a(routeType, MyMarriageViewsHeadActivity.class, "/psychology_test/mine/mymarriageviewsheadactivity", "psychology_test", null, -1, Integer.MIN_VALUE));
        map.put("/psychology_test/profile/OtherMarriageOpinionActivity", RouteMeta.a(routeType, OtherMarriageOpinionActivity.class, "/psychology_test/profile/othermarriageopinionactivity", "psychology_test", new HashMap<String, Integer>() { // from class: com.alibaba.android.arouter.routes.ARouter$$Group$$psychology_test.2
            {
                put("gender", 3);
                put("object_id", 4);
            }
        }, -1, Integer.MIN_VALUE));
        map.put("/psychology_test/psyTest/GuidePsyTestActivity", RouteMeta.a(routeType, GuidePsyTestActivity.class, "/psychology_test/psytest/guidepsytestactivity", "psychology_test", null, -1, Integer.MIN_VALUE));
        map.put("/psychology_test/psyTest/MarriageViewsReportActivity", RouteMeta.a(routeType, MarriageViewsReportActivity.class, "/psychology_test/psytest/marriageviewsreportactivity", "psychology_test", null, -1, Integer.MIN_VALUE));
        map.put("/psychology_test/psyTest/OtherMarriageViewActivity", RouteMeta.a(routeType, OtherMarriageViewActivity.class, "/psychology_test/psytest/othermarriageviewactivity", "psychology_test", null, -1, Integer.MIN_VALUE));
    }
}
