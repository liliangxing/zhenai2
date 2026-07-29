package com.zhenai2.live

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.zhenai2.common.router.RouterPath

/**
 * 直播模块入口 Fragment
 *
 * ARouter 路由: /live/list
 * 职责: 直播列表/主播/观众/回放/红娘匹配/装扮。原包 com.zhenai.live.*
 *   - 声网 Agora: AgoraPKAnchorActivity / AgoraPlaybackActivity
 *   - 连麦/PK: LiveVideo7AnchorActivity / LiveVideo7AudienceActivity
 *   - 红娘匹配: HnMatchVIPAnchorActivity / HnMatchVIPAudienceActivity
 *   - 装扮/时尚秀: DecorationMarketActivity / FashionMallActivity
 *   - 录屏: RecordVideoPlayActivity / TXScreenCapture
 * 复刻状态: 脚手架。直播能力需接入声网 Agora SDK + 腾讯直播组件后补全。
 */
@Route(path = RouterPath.LIVE_LIST)
class LiveFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return TextView(requireContext()).apply {
            text = "直播\n\n(列表/主播/连麦/PK - 声网Agora)"
            textSize = 16f
            gravity = android.view.Gravity.CENTER
        }
    }
}
