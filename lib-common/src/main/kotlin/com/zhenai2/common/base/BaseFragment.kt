package com.zhenai2.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Fragment 基类 —— 对应原 App 各 Tab/页面 Fragment。
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!

    protected abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): VB
    protected abstract fun initViews()
    protected open fun initListener() {}
    protected open fun initData() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflateBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListener()
        initData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
