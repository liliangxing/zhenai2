package com.zhenai2.moment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.zhenai2.common.router.RouterPath

/**
 * 动态广场入口 Fragment
 *
 * ARouter 路由: /moment/square
 * 职责: 动态发布/详情/评论。原包 com.zhenai.moments.*
 *   - comments.ui.CommentOnMe2Activity 等互动页
 * 复刻状态: 脚手架。动态流接口需真机抓包补全。
 */
@Route(path = RouterPath.MOMENT_SQUARE)
class MomentFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return TextView(requireContext()).apply {
            text = "动态广场\n\n(发布/详情/评论)"
            textSize = 16f
            gravity = android.view.Gravity.CENTER
        }
    }
}
