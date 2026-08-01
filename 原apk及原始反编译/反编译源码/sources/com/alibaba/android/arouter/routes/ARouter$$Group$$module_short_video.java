package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.zhenai.short_video.CoverActivity;
import com.zhenai.short_video.CropperActivity;
import com.zhenai.short_video.EditorActivity;
import com.zhenai.short_video.RecorderActivity;
import com.zhenai.short_video.provider.ShortVideoProvider;
import com.zhenai.short_video.recommend.RecommendVideoActivity;
import com.zhenai.short_video.topic.view.HotTopicListActivity;
import com.zhenai.short_video.topic.view.SpecialTopicActivity;
import com.zhenai.short_video.topic.view.TopicDetailActivity;
import com.zhenai.short_video.video_detail.view.ShortVideoDetailActivity;
import com.zhenai.short_video.videomask.VideoMaskIntroActivity;
import com.zhenai.short_video.videomask.VideoMaskPreviewActivity;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Group$$module_short_video implements IRouteGroup {
    @Override // com.alibaba.android.arouter.facade.template.IRouteGroup
    public void loadInto(Map<String, RouteMeta> map) {
        RouteType routeType = RouteType.ACTIVITY;
        map.put("/module_short_video/detail/ShortVideoDetailActivity", RouteMeta.a(routeType, ShortVideoDetailActivity.class, "/module_short_video/detail/shortvideodetailactivity", "module_short_video", null, -1, Integer.MIN_VALUE));
        map.put("/module_short_video/mine/VideoMaskActivity", RouteMeta.a(routeType, VideoMaskIntroActivity.class, "/module_short_video/mine/videomaskactivity", "module_short_video", null, -1, Integer.MIN_VALUE));
        map.put("/module_short_video/photo/VideoMaskPreviewActivity", RouteMeta.a(routeType, VideoMaskPreviewActivity.class, "/module_short_video/photo/videomaskpreviewactivity", "module_short_video", null, -1, Integer.MIN_VALUE));
        map.put("/module_short_video/provider/ShortVideoProvider", RouteMeta.a(RouteType.PROVIDER, ShortVideoProvider.class, "/module_short_video/provider/shortvideoprovider", "module_short_video", null, -1, Integer.MIN_VALUE));
        map.put("/module_short_video/recommend/RecommendVideoActivity", RouteMeta.a(routeType, RecommendVideoActivity.class, "/module_short_video/recommend/recommendvideoactivity", "module_short_video", null, -1, Integer.MIN_VALUE));
        map.put("/module_short_video/record/CoverActivity", RouteMeta.a(routeType, CoverActivity.class, "/module_short_video/record/coveractivity", "module_short_video", null, -1, Integer.MIN_VALUE));
        map.put("/module_short_video/record/CropperActivity", RouteMeta.a(routeType, CropperActivity.class, "/module_short_video/record/cropperactivity", "module_short_video", null, -1, Integer.MIN_VALUE));
        map.put("/module_short_video/record/EditorActivity", RouteMeta.a(routeType, EditorActivity.class, "/module_short_video/record/editoractivity", "module_short_video", null, -1, Integer.MIN_VALUE));
        map.put("/module_short_video/record/RecorderActivity", RouteMeta.a(routeType, RecorderActivity.class, "/module_short_video/record/recorderactivity", "module_short_video", null, -1, Integer.MIN_VALUE));
        map.put("/module_short_video/topic/HotTopicListActivity", RouteMeta.a(routeType, HotTopicListActivity.class, "/module_short_video/topic/hottopiclistactivity", "module_short_video", null, -1, Integer.MIN_VALUE));
        map.put("/module_short_video/topic/SpecialTopicActivity", RouteMeta.a(routeType, SpecialTopicActivity.class, "/module_short_video/topic/specialtopicactivity", "module_short_video", null, -1, Integer.MIN_VALUE));
        map.put("/module_short_video/topic/TopicDetailActivity", RouteMeta.a(routeType, TopicDetailActivity.class, "/module_short_video/topic/topicdetailactivity", "module_short_video", null, -1, Integer.MIN_VALUE));
    }
}
