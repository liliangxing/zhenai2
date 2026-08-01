package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.zhenai.message.anti_fraud_little_helper.AntiFraudLittleHelperActivity;
import com.zhenai.message.email_chat.EmailChatDialogActivity;
import com.zhenai.message.email_chat.EmailChatTabActivity;
import com.zhenai.message.email_chat.TextPreviewActivity;
import com.zhenai.message.email_chat.widget.EmailChatFragment;
import com.zhenai.message.message.LoveKeyHtmlActivity;
import com.zhenai.message.provider.MessageProvider;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Group$$module_message implements IRouteGroup {
    @Override // com.alibaba.android.arouter.facade.template.IRouteGroup
    public void loadInto(Map<String, RouteMeta> map) {
        RouteType routeType = RouteType.ACTIVITY;
        map.put("/module_message/chat/EmailChatDialogActivity", RouteMeta.a(routeType, EmailChatDialogActivity.class, "/module_message/chat/emailchatdialogactivity", "module_message", null, -1, Integer.MIN_VALUE));
        map.put("/module_message/chat/EmailChatFragment", RouteMeta.a(RouteType.FRAGMENT, EmailChatFragment.class, "/module_message/chat/emailchatfragment", "module_message", null, -1, Integer.MIN_VALUE));
        map.put("/module_message/chat/EmailChatTabActivity", RouteMeta.a(routeType, EmailChatTabActivity.class, "/module_message/chat/emailchattabactivity", "module_message", null, -1, Integer.MIN_VALUE));
        map.put("/module_message/chat/TextPreviewActivity", RouteMeta.a(routeType, TextPreviewActivity.class, "/module_message/chat/textpreviewactivity", "module_message", null, -1, Integer.MIN_VALUE));
        map.put("/module_message/message/AntiFraudLittleHelperActivity", RouteMeta.a(routeType, AntiFraudLittleHelperActivity.class, "/module_message/message/antifraudlittlehelperactivity", "module_message", null, -1, Integer.MIN_VALUE));
        map.put("/module_message/message/LoveKeyHtmlActivity", RouteMeta.a(routeType, LoveKeyHtmlActivity.class, "/module_message/message/lovekeyhtmlactivity", "module_message", null, -1, Integer.MIN_VALUE));
        map.put("/module_message/provider/MessageProvider", RouteMeta.a(RouteType.PROVIDER, MessageProvider.class, "/module_message/provider/messageprovider", "module_message", null, -1, Integer.MIN_VALUE));
    }
}
