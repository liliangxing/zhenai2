package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.zhenai.module_emotion.classify.ClassifyListActivity;
import com.zhenai.module_emotion.consultation.ConsultationServiceDetailsActivity;
import com.zhenai.module_emotion.consultation.ReservationConsultationActivity;
import com.zhenai.module_emotion.consultation.ReservationConsultationFeedBackActivity;
import com.zhenai.module_emotion.course.CourseListActivity;
import com.zhenai.module_emotion.course.LessonDetailActivity;
import com.zhenai.module_emotion.course.QualityCourseDetailsActivity;
import com.zhenai.module_emotion.course.SupremePackageListActivity;
import com.zhenai.module_emotion.daily.DailyChoiceActivity;
import com.zhenai.module_emotion.daily.DailyChoiceFMDetailActivity;
import com.zhenai.module_emotion.daily.DailyChoiceSearchClassifyResultActivity;
import com.zhenai.module_emotion.daily.DailyChoiceSearchResultActivity;
import com.zhenai.module_emotion.emotionanalysis.EmotionsAnalysisResultHtmlActivity;
import com.zhenai.module_emotion.home.EmotionHomeActivity;
import com.zhenai.module_emotion.order.MineSchoolActivity;
import com.zhenai.module_emotion.player.SimpleVideoPlayActivity;
import com.zhenai.module_emotion.provider.EmotionProvider;
import com.zhenai.module_emotion.question.EmotionCommunityActivity;
import com.zhenai.module_emotion.question.EmotionQuestionDetailActivity;
import com.zhenai.module_emotion.question.PublishQuestionActivity;
import com.zhenai.module_emotion.shop.MineShopActivity;
import com.zhenai.module_emotion.shop.MineShopChooseActivity;
import com.zhenai.module_emotion.teacher.TeacherDetailActivity;
import com.zhenai.module_emotion.teacher.TeacherListActivity;
import com.zhenai.module_emotion.teacher.TeacherUserAppraiseActivity;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Group$$module_emotion implements IRouteGroup {
    @Override // com.alibaba.android.arouter.facade.template.IRouteGroup
    public void loadInto(Map<String, RouteMeta> map) {
        RouteType routeType = RouteType.ACTIVITY;
        map.put("/module_emotion/classify/ClassifyListActivity", RouteMeta.a(routeType, ClassifyListActivity.class, "/module_emotion/classify/classifylistactivity", "module_emotion", new HashMap<String, Integer>() { // from class: com.alibaba.android.arouter.routes.ARouter$$Group$$module_emotion.1
            {
                put("optionKey", 3);
                put("optionName", 8);
            }
        }, -1, Integer.MIN_VALUE));
        map.put("/module_emotion/consultation/ConsultationServiceDetailsActivity", RouteMeta.a(routeType, ConsultationServiceDetailsActivity.class, "/module_emotion/consultation/consultationservicedetailsactivity", "module_emotion", new HashMap<String, Integer>() { // from class: com.alibaba.android.arouter.routes.ARouter$$Group$$module_emotion.2
            {
                put("orderNO", 8);
                put("orderID", 4);
                put("autoPopPay", 0);
            }
        }, -1, Integer.MIN_VALUE));
        map.put("/module_emotion/consultation/ReservationConsultationActivity", RouteMeta.a(routeType, ReservationConsultationActivity.class, "/module_emotion/consultation/reservationconsultationactivity", "module_emotion", new HashMap<String, Integer>() { // from class: com.alibaba.android.arouter.routes.ARouter$$Group$$module_emotion.3
            {
                put("consultingServiceID", 4);
                put("consulting_service_type", 3);
                put("source", 8);
            }
        }, -1, Integer.MIN_VALUE));
        map.put("/module_emotion/consultation/ReservationConsultationFeedBackActivity", RouteMeta.a(routeType, ReservationConsultationFeedBackActivity.class, "/module_emotion/consultation/reservationconsultationfeedbackactivity", "module_emotion", new HashMap<String, Integer>() { // from class: com.alibaba.android.arouter.routes.ARouter$$Group$$module_emotion.4
            {
                put("orderID", 4);
                put("consulting_service_type", 3);
            }
        }, -1, Integer.MIN_VALUE));
        map.put("/module_emotion/course/LessonDetailActivity", RouteMeta.a(routeType, LessonDetailActivity.class, "/module_emotion/course/lessondetailactivity", "module_emotion", new HashMap<String, Integer>() { // from class: com.alibaba.android.arouter.routes.ARouter$$Group$$module_emotion.5
            {
                put("source", 8);
                put("supremeLessonID", 4);
                put("fm_url", 8);
                put("supremeCourseID", 4);
            }
        }, -1, Integer.MIN_VALUE));
        map.put("/module_emotion/course/QualityCourseActivity", RouteMeta.a(routeType, CourseListActivity.class, "/module_emotion/course/qualitycourseactivity", "module_emotion", null, -1, Integer.MIN_VALUE));
        map.put("/module_emotion/course/QualityCourseDetailsActivity", RouteMeta.a(routeType, QualityCourseDetailsActivity.class, "/module_emotion/course/qualitycoursedetailsactivity", "module_emotion", new HashMap<String, Integer>() { // from class: com.alibaba.android.arouter.routes.ARouter$$Group$$module_emotion.6
            {
                put("allow", 0);
                put("source", 8);
                put("supremeCourseID", 8);
            }
        }, -1, Integer.MIN_VALUE));
        map.put("/module_emotion/course/SupremePackageListActivity", RouteMeta.a(routeType, SupremePackageListActivity.class, "/module_emotion/course/supremepackagelistactivity", "module_emotion", new HashMap<String, Integer>() { // from class: com.alibaba.android.arouter.routes.ARouter$$Group$$module_emotion.7
            {
                put("allow", 0);
                put("supremePackageID", 4);
                put("source", 8);
            }
        }, -1, Integer.MIN_VALUE));
        map.put("/module_emotion/daily/DailyChoiceActivity", RouteMeta.a(routeType, DailyChoiceActivity.class, "/module_emotion/daily/dailychoiceactivity", "module_emotion", null, -1, Integer.MIN_VALUE));
        map.put("/module_emotion/daily/DailyChoiceFMDetailActivity", RouteMeta.a(routeType, DailyChoiceFMDetailActivity.class, "/module_emotion/daily/dailychoicefmdetailactivity", "module_emotion", new HashMap<String, Integer>() { // from class: com.alibaba.android.arouter.routes.ARouter$$Group$$module_emotion.8
            {
                put("fm_id", 4);
                put("fm_url", 8);
            }
        }, -1, Integer.MIN_VALUE));
        map.put("/module_emotion/daily/DailyChoiceSearchClassifyResultActivity", RouteMeta.a(routeType, DailyChoiceSearchClassifyResultActivity.class, "/module_emotion/daily/dailychoicesearchclassifyresultactivity", "module_emotion", new HashMap<String, Integer>() { // from class: com.alibaba.android.arouter.routes.ARouter$$Group$$module_emotion.9
            {
                put("optionKey", 4);
                put("name", 8);
            }
        }, -1, Integer.MIN_VALUE));
        map.put("/module_emotion/daily/DailyChoiceSearchResultActivity", RouteMeta.a(routeType, DailyChoiceSearchResultActivity.class, "/module_emotion/daily/dailychoicesearchresultactivity", "module_emotion", null, -1, Integer.MIN_VALUE));
        map.put("/module_emotion/emotionanalysis/EmotionsAnalysisResultHtmlActivity", RouteMeta.a(routeType, EmotionsAnalysisResultHtmlActivity.class, "/module_emotion/emotionanalysis/emotionsanalysisresulthtmlactivity", "module_emotion", null, -1, Integer.MIN_VALUE));
        map.put("/module_emotion/home/EmotionHomeActivity", RouteMeta.a(routeType, EmotionHomeActivity.class, "/module_emotion/home/emotionhomeactivity", "module_emotion", new HashMap<String, Integer>() { // from class: com.alibaba.android.arouter.routes.ARouter$$Group$$module_emotion.10
            {
                put("emotion_home_title", 8);
            }
        }, -1, Integer.MIN_VALUE));
        map.put("/module_emotion/order/MineSchoolActivity", RouteMeta.a(routeType, MineSchoolActivity.class, "/module_emotion/order/mineschoolactivity", "module_emotion", null, -1, Integer.MIN_VALUE));
        map.put("/module_emotion/play/SimpleVideoPlayActivity", RouteMeta.a(routeType, SimpleVideoPlayActivity.class, "/module_emotion/play/simplevideoplayactivity", "module_emotion", new HashMap<String, Integer>() { // from class: com.alibaba.android.arouter.routes.ARouter$$Group$$module_emotion.11
            {
                put("video_url", 8);
            }
        }, -1, Integer.MIN_VALUE));
        map.put("/module_emotion/provider/EmotionProvider", RouteMeta.a(RouteType.PROVIDER, EmotionProvider.class, "/module_emotion/provider/emotionprovider", "module_emotion", null, -1, Integer.MIN_VALUE));
        map.put("/module_emotion/question/EmotionCommunityActivity", RouteMeta.a(routeType, EmotionCommunityActivity.class, "/module_emotion/question/emotioncommunityactivity", "module_emotion", null, -1, Integer.MIN_VALUE));
        map.put("/module_emotion/question/PublishQuestionActivity", RouteMeta.a(routeType, PublishQuestionActivity.class, "/module_emotion/question/publishquestionactivity", "module_emotion", null, -1, Integer.MIN_VALUE));
        map.put("/module_emotion/question/QuestionDetailActivity", RouteMeta.a(routeType, EmotionQuestionDetailActivity.class, "/module_emotion/question/questiondetailactivity", "module_emotion", null, -1, Integer.MIN_VALUE));
        map.put("/module_emotion/show/MineShopActivity", RouteMeta.a(routeType, MineShopActivity.class, "/module_emotion/show/mineshopactivity", "module_emotion", null, -1, Integer.MIN_VALUE));
        map.put("/module_emotion/show/MineShopChooseActivity", RouteMeta.a(routeType, MineShopChooseActivity.class, "/module_emotion/show/mineshopchooseactivity", "module_emotion", null, -1, Integer.MIN_VALUE));
        map.put("/module_emotion/teacher/TeacherDetailActivity", RouteMeta.a(routeType, TeacherDetailActivity.class, "/module_emotion/teacher/teacherdetailactivity", "module_emotion", new HashMap<String, Integer>() { // from class: com.alibaba.android.arouter.routes.ARouter$$Group$$module_emotion.12
            {
                put("teacherID", 4);
                put("teacherInfo", 9);
            }
        }, -1, Integer.MIN_VALUE));
        map.put("/module_emotion/teacher/TeacherListActivity", RouteMeta.a(routeType, TeacherListActivity.class, "/module_emotion/teacher/teacherlistactivity", "module_emotion", new HashMap<String, Integer>() { // from class: com.alibaba.android.arouter.routes.ARouter$$Group$$module_emotion.13
            {
                put("source", 8);
            }
        }, -1, Integer.MIN_VALUE));
        map.put("/module_emotion/teacher/TeacherUserAppraiseActivity", RouteMeta.a(routeType, TeacherUserAppraiseActivity.class, "/module_emotion/teacher/teacheruserappraiseactivity", "module_emotion", new HashMap<String, Integer>() { // from class: com.alibaba.android.arouter.routes.ARouter$$Group$$module_emotion.14
            {
                put("teacherID", 4);
                put("comment_id", 8);
            }
        }, -1, Integer.MIN_VALUE));
    }
}
