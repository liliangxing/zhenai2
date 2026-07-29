package com.zhenai2.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.zhenai2.common.router.RouterPath

/**
 * 消息模块入口 Fragment
 *
 * ARouter 路由: /chat/conversation
 * 职责: 会话列表/私聊。原 App 用腾讯 IM SDK(libImSDK.so) + TUI 组件
 * 复刻状态: 脚手架。需集成腾讯 TUIKIT 后补全会话列表。
 */
@Route(path = RouterPath.CHAT_CONVERSATION)
class ChatFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return TextView(requireContext()).apply {
            text = "消息\n\n(会话列表 - 腾讯IM)"
            textSize = 16f
            gravity = android.view.Gravity.CENTER
        }
    }
}
