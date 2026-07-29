package com.zhenai2.emotion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.zhenai2.common.router.RouterPath

/**
 * 情感咨询模块入口 Fragment
 *
 * ARouter 路由: /emotion/consult
 * 职责: 情感咨询/聊天/课程/导师/恋爱任务。原包 com.zhenai.love_zone.*
 *   - 恋爱任务: LoveTask5/10 系列 Activity
 *   - 情感分析/咨询/导师/课程
 * 复刻状态: 脚手架。咨询与任务接口需真机抓包补全。
 */
@Route(path = RouterPath.EMOTION_CONSULT)
class EmotionFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return TextView(requireContext()).apply {
            text = "情感咨询\n\n(咨询/课程/恋爱任务)"
            textSize = 16f
            gravity = android.view.Gravity.CENTER
        }
    }
}
