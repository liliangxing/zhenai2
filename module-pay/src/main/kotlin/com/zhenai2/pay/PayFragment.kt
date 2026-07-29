package com.zhenai2.pay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.zhenai2.common.router.RouterPath

/**
 * 支付模块入口 Fragment
 *
 * ARouter 路由: /pay/recharge
 * 职责: 充值/会员/订单/钱包。原包 com.zhenai.business.framework.pay.*
 *   - 微信支付: WXPayEntryActivity (com.zhenai2.android.wxapi.WXPayEntryActivity)
 *   - 支付宝: com.alipay.sdk.app.H5PayActivity
 *   - 银联: com.unionpay.uppay.PayActivity
 *   - 招行一网通: com.cmbchina.ccd.pluto.cmbActivity
 * 复刻状态: 脚手架。支付渠道需接入各 SDK 后补全。
 */
@Route(path = RouterPath.PAY_RECHARGE)
class PayFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return TextView(requireContext()).apply {
            text = "充值中心\n\n(微信/支付宝/银联)"
            textSize = 16f
            gravity = android.view.Gravity.CENTER
        }
    }
}
