package com.zhenai2.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * Activity 基类
 *
 * 复刻原 App 的基类职责: ViewBinding 绑定、状态栏、统一返回、loading。
 * 原 App 基类被加密,这里按珍爱网 H5/原生通用模式重建。
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected lateinit var binding: VB

    protected abstract fun inflateBinding(): VB
    protected abstract fun initViews()
    protected open fun initListener() {}
    protected open fun initData() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateBinding()
        setContentView(binding.root)
        initViews()
        initListener()
        initData()
    }
}
