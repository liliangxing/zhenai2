package com.zhenai2.common.router

/**
 * ARouter 路由路径表
 *
 * 来源于原 App 的 ARouter 路由体系(原 App 有约 380 条内部路由,覆盖 20 个业务模块)。
 * 复刻项目按模块分组保留主要路由,模块内 Activity 通过 ARouter 跳转。
 *
 * 命名规则: /模块名/页面名
 */
object RouterPath {

    // ---- 主壳 app ----
    const val SPLASH = "/app/splash"
    const val MAIN = "/app/main"

    // ---- 登录注册 module-login ----
    const val LOGIN = "/login/login"
    const val REGISTER_INFO = "/login/registerInfo"
    const val PROTOCOL_AGREE = "/login/protocolAgree"
    const val FORGET_PWD = "/login/forgetPwd"

    // ---- 首页/推荐 module-home ----
    const val HOME_RECOMMEND = "/home/recommend"
    const val HOME_MATCH = "/home/match"
    const val HOME_SEARCH = "/home/search"
    const val HOME_MEMBER_DETAIL = "/home/memberDetail"

    // ---- 我的 module-mine ----
    const val MINE = "/mine/index"
    const val MINE_PROFILE = "/mine/profile"
    const val MINE_SETTING = "/mine/setting"
    const val MINE_MODIFY_PHONE = "/mine/modifyPhone"
    const val MINE_VISITED = "/mine/visited"
    const val MINE_PRAISE = "/mine/praise"
    const val MINE_FOLLOWED = "/mine/followed"
    const val MINE_GIFT = "/mine/gift"

    // ---- 直播 module-live ----
    const val LIVE_LIST = "/live/list"
    const val LIVE_ANCHOR = "/live/anchor"
    const val LIVE_AUDIENCE = "/live/audience"
    const val LIVE_PLAYBACK = "/live/playback"
    const val LIVE_HN_MATCH = "/live/hnMatch"
    const val LIVE_DECORATION = "/live/decoration"

    // ---- 即时通讯 module-chat ----
    const val CHAT_CONVERSATION = "/chat/conversation"
    const val CHAT_DETAIL = "/chat/detail"
    const val CHAT_AVOID_VIDEO = "/chat/avoidVideo"

    // ---- 认证 module-cert ----
    const val CERT_REALNAME = "/cert/realname"
    const val CERT_EDUCATION = "/cert/education"
    const val CERT_INCOME = "/cert/income"
    const val CERT_ID_CARD = "/cert/idCard"
    const val CERT_LIVENESS = "/cert/liveness"
    const val CERT_MINE = "/cert/mine"

    // ---- 动态 module-moment ----
    const val MOMENT_SQUARE = "/moment/square"
    const val MOMENT_DETAIL = "/moment/detail"
    const val MOMENT_PUBLISH = "/moment/publish"
    const val MOMENT_COMMENT = "/moment/comment"

    // ---- 支付 module-pay ----
    const val PAY_RECHARGE = "/pay/recharge"
    const val PAY_ORDER = "/pay/order"
    const val PAY_VIP = "/pay/vip"
    const val PAY_WALLET = "/pay/wallet"

    // ---- 情感咨询 module-emotion ----
    const val EMOTION_CONSULT = "/emotion/consult"
    const val EMOTION_CHAT = "/emotion/chat"
    const val EMOTION_COURSE = "/emotion/course"
    const val EMOTION_TEACHER = "/emotion/teacher"
    const val EMOTION_TASK = "/emotion/task"

    // ---- H5 容器 module-web ----
    const val WEB_HTML = "/web/html"
    const val WEB_WEBKIT = "/web/webkit"
}
