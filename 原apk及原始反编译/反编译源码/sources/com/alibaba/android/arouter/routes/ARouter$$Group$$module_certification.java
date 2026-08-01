package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.zhenai.certification.education.ui.EducationCertificationActivity;
import com.zhenai.certification.income.ui.IncomeCertificationActivity;
import com.zhenai.certification.income.ui.IncomeShowSettingActivity;
import com.zhenai.certification.mine.ui.MyIdentification2Activity;
import com.zhenai.certification.provider.CertificationProvider;
import com.zhenai.certification.senseid.CertificationUploadCardsHtmlActivity;
import com.zhenai.certification.senseid.SenseCertificateActivity;
import com.zhenai.certification.senseid.sense.SenseActivity;
import com.zhenai.certification.zhima.RealNameZoneActivity;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Group$$module_certification implements IRouteGroup {
    @Override // com.alibaba.android.arouter.facade.template.IRouteGroup
    public void loadInto(Map<String, RouteMeta> map) {
        RouteType routeType = RouteType.ACTIVITY;
        map.put("/module_certification/certificate/CertificationUploadCardsHtmlActivity", RouteMeta.a(routeType, CertificationUploadCardsHtmlActivity.class, "/module_certification/certificate/certificationuploadcardshtmlactivity", "module_certification", null, -1, Integer.MIN_VALUE));
        map.put("/module_certification/certificate/EducationCertificationActivity", RouteMeta.a(routeType, EducationCertificationActivity.class, "/module_certification/certificate/educationcertificationactivity", "module_certification", null, -1, Integer.MIN_VALUE));
        map.put("/module_certification/certificate/IncomeCertificationActivity", RouteMeta.a(routeType, IncomeCertificationActivity.class, "/module_certification/certificate/incomecertificationactivity", "module_certification", null, -1, Integer.MIN_VALUE));
        map.put("/module_certification/certificate/IncomeShowSettingActivity", RouteMeta.a(routeType, IncomeShowSettingActivity.class, "/module_certification/certificate/incomeshowsettingactivity", "module_certification", null, -1, Integer.MIN_VALUE));
        map.put("/module_certification/certificate/SenseCertificateActivity", RouteMeta.a(routeType, SenseCertificateActivity.class, "/module_certification/certificate/sensecertificateactivity", "module_certification", null, -1, Integer.MIN_VALUE));
        map.put("/module_certification/certification/CertificationProvider", RouteMeta.a(RouteType.PROVIDER, CertificationProvider.class, "/module_certification/certification/certificationprovider", "module_certification", null, -1, Integer.MIN_VALUE));
        map.put("/module_certification/credit/RealNameZoneActivity", RouteMeta.a(routeType, RealNameZoneActivity.class, "/module_certification/credit/realnamezoneactivity", "module_certification", null, -1, Integer.MIN_VALUE));
        map.put("/module_certification/identification/MyIdentification2Activity", RouteMeta.a(routeType, MyIdentification2Activity.class, "/module_certification/identification/myidentification2activity", "module_certification", null, -1, Integer.MIN_VALUE));
        map.put("/module_certification/provider/SenseProvider", RouteMeta.a(routeType, SenseActivity.class, "/module_certification/provider/senseprovider", "module_certification", null, -1, Integer.MIN_VALUE));
    }
}
