package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.zhenai.live.concern.ui.MyConcernPersonActivity;
import com.zhenai.live.decorate.utils.DecorationMarketActivity;
import com.zhenai.live.decorate.utils.MyDecorationActivity;
import com.zhenai.live.fashion_show.FashionMallActivity;
import com.zhenai.live.fashion_show.FashionSettingActivity;
import com.zhenai.live.hong_niang_match_vip.HnMatchVIPAnchorActivity;
import com.zhenai.live.hong_niang_match_vip.HnMatchVIPAudienceActivity;
import com.zhenai.live.hong_niang_match_vip.HongNiangMatchVipEndActivity;
import com.zhenai.live.hong_niang_match_vip_board.LiveHnVIPListActivity;
import com.zhenai.live.live_video_7.LiveVideo7AnchorActivity;
import com.zhenai.live.live_video_7.LiveVideo7AudienceActivity;
import com.zhenai.live.provider.LiveProvider;
import com.zhenai.live.record_screen.RecordVideoPlayActivity;
import com.zhenai.live.user_match.UserHnAnchorMatchActivity;
import com.zhenai.live.user_match.UserHnAudienceActivity;
import com.zhenai.live.zone.LiveVideoZone2Activity;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Group$$module_live implements IRouteGroup {
    @Override // com.alibaba.android.arouter.facade.template.IRouteGroup
    public void loadInto(Map<String, RouteMeta> map) {
        RouteType routeType = RouteType.ACTIVITY;
        map.put("/module_live/live/DecorationMarketActivity", RouteMeta.a(routeType, DecorationMarketActivity.class, "/module_live/live/decorationmarketactivity", "module_live", null, -1, Integer.MIN_VALUE));
        map.put("/module_live/live/FashionMallActivity", RouteMeta.a(routeType, FashionMallActivity.class, "/module_live/live/fashionmallactivity", "module_live", null, -1, Integer.MIN_VALUE));
        map.put("/module_live/live/FashionSettingActivity", RouteMeta.a(routeType, FashionSettingActivity.class, "/module_live/live/fashionsettingactivity", "module_live", null, -1, Integer.MIN_VALUE));
        map.put("/module_live/live/HnMatchVIPAnchorActivity", RouteMeta.a(routeType, HnMatchVIPAnchorActivity.class, "/module_live/live/hnmatchvipanchoractivity", "module_live", null, -1, Integer.MIN_VALUE));
        map.put("/module_live/live/HnMatchVIPAudienceActivity", RouteMeta.a(routeType, HnMatchVIPAudienceActivity.class, "/module_live/live/hnmatchvipaudienceactivity", "module_live", null, -1, Integer.MIN_VALUE));
        map.put("/module_live/live/HongNiangMatchVipEndActivity", RouteMeta.a(routeType, HongNiangMatchVipEndActivity.class, "/module_live/live/hongniangmatchvipendactivity", "module_live", null, -1, Integer.MIN_VALUE));
        map.put("/module_live/live/LiveHnVIPListActivity", RouteMeta.a(routeType, LiveHnVIPListActivity.class, "/module_live/live/livehnviplistactivity", "module_live", null, -1, Integer.MIN_VALUE));
        map.put("/module_live/live/LiveVideo7AnchorActivity", RouteMeta.a(routeType, LiveVideo7AnchorActivity.class, "/module_live/live/livevideo7anchoractivity", "module_live", null, -1, Integer.MIN_VALUE));
        map.put("/module_live/live/LiveVideo7AudienceActivity", RouteMeta.a(routeType, LiveVideo7AudienceActivity.class, "/module_live/live/livevideo7audienceactivity", "module_live", null, -1, Integer.MIN_VALUE));
        map.put("/module_live/live/MyConcernPersonActivity", RouteMeta.a(routeType, MyConcernPersonActivity.class, "/module_live/live/myconcernpersonactivity", "module_live", null, -1, Integer.MIN_VALUE));
        map.put("/module_live/live/MyDecorationActivity", RouteMeta.a(routeType, MyDecorationActivity.class, "/module_live/live/mydecorationactivity", "module_live", null, -1, Integer.MIN_VALUE));
        map.put("/module_live/live/SameCityZoneActivity", RouteMeta.a(routeType, LiveVideoZone2Activity.class, "/module_live/live/samecityzoneactivity", "module_live", null, -1, Integer.MIN_VALUE));
        map.put("/module_live/live/UserHnAnchorMatchActivity", RouteMeta.a(routeType, UserHnAnchorMatchActivity.class, "/module_live/live/userhnanchormatchactivity", "module_live", null, -1, Integer.MIN_VALUE));
        map.put("/module_live/live/UserHnAudienceActivity", RouteMeta.a(routeType, UserHnAudienceActivity.class, "/module_live/live/userhnaudienceactivity", "module_live", null, -1, Integer.MIN_VALUE));
        map.put("/module_live/provider/LiveProvider", RouteMeta.a(RouteType.PROVIDER, LiveProvider.class, "/module_live/provider/liveprovider", "module_live", null, -1, Integer.MIN_VALUE));
        map.put("/module_live/record/RecordVideoPlayActivity", RouteMeta.a(routeType, RecordVideoPlayActivity.class, "/module_live/record/recordvideoplayactivity", "module_live", null, -1, Integer.MIN_VALUE));
    }
}
