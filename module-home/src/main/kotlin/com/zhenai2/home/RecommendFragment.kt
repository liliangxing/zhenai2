package com.zhenai2.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.zhenai2.common.FileLog
import com.zhenai2.common.router.RouterPath
import com.zhenai2.network.NetworkClient
import kotlinx.coroutines.launch

/**
 * 首页推荐 Fragment
 *
 * ARouter 路由: /home/recommend
 * 对应原 App 主页 Tab1: 推荐列表(调用推荐/匹配接口)
 *
 * 复刻说明: 原 App 推荐列表的接口路径在加密 dex 内,无法静态还原。
 *          登录后此处调用 getBasicProfile.do 拉取用户资料(基础库硬编码接口),
 *          推荐列表数据接口需真机抓包补全。
 */
@Route(path = RouterPath.HOME_RECOMMEND)
class RecommendFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return TextView(requireContext()).apply {
            text = "首页推荐\n\n(登录后展示推荐会员列表)"
            textSize = 16f
            gravity = android.view.Gravity.CENTER
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 登录后主页必调: 获取用户基本资料
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val profile = NetworkClient.apiService.getBasicProfile()
                (view as TextView).text = buildString {
                    append("首页推荐\n\n")
                    append("当前用户: ${profile.data?.nickname ?: "未登录"}\n")
                    append("会员ID: ${profile.data?.memberID ?: 0}\n")
                    append("城市: ${profile.data?.city ?: "-"}")
                }
            } catch (e: Exception) {
                // 网络异常(WAF/未登录)忽略,展示默认
                FileLog.w("getBasicProfile.do 异常", e)
            }
        }
    }
}
