package com.zhenai2.cert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.zhenai2.common.router.RouterPath

/**
 * 认证模块入口 Fragment
 *
 * ARouter 路由: /cert/realname
 * 职责: 实名/学历/收入/身份证/活体认证。原包 com.zhenai.certification.*
 *   - 实名: 芝麻信用(Zhima) -> CertificateResultActivity
 *   - 活体: 商汤 SenseId + 腾讯慧眼(libYTCommonLiveness.so / huiyansdkface)
 *   - 学历/收入: EducationCertificationActivity / IncomeCertificationActivity
 * 复刻状态: 脚手架。认证流程需接入商汤/腾讯慧眼/芝麻 SDK 后补全。
 */
@Route(path = RouterPath.CERT_REALNAME)
class CertFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return TextView(requireContext()).apply {
            text = "认证中心\n\n(实名/学历/收入/活体)"
            textSize = 16f
            gravity = android.view.Gravity.CENTER
        }
    }
}
