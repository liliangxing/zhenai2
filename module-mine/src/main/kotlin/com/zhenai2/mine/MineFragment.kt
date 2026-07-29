package com.zhenai2.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.zhenai2.common.router.RouterPath

/**
 * 我的模块入口 Fragment
 *
 * ARouter 路由: /mine/index
 * 职责: 资料/设置/互动(来访/点赞/关注/礼物)。原包 com.zhenai.mine.*
 * 复刻状态: 脚手架。原 App 业务代码被爱加密加固,无法静态还原,
 *          本模块为可编译运行的骨架,具体业务逻辑需真机抓包 + 脱壳后补全。
 */
@Route(path = RouterPath.MINE)
class MineFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return TextView(requireContext()).apply {
            text = "我的\n\n(资料/设置/互动)"
            textSize = 16f
            gravity = android.view.Gravity.CENTER
        }
    }
}
