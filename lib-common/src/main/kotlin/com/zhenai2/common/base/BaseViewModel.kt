package com.zhenai2.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

/**
 * ViewModel 基类 —— 统一协程异常处理。
 *
 * 注意: 本类位于 lib-common,不能直接依赖 lib-network(会形成循环依赖),
 *      因此 request 用泛型 lambda 解耦,由子类自行处理 ApiResponse。
 */
abstract class BaseViewModel : ViewModel() {

    protected fun launch(
        onError: (Throwable) -> Unit = {},
        block: suspend () -> Unit
    ) {
        val handler = CoroutineExceptionHandler { _, e -> onError(e) }
        viewModelScope.launch(handler) { block() }
    }

    /**
     * 统一请求封装: 执行 [api],异常或返回 null 视为失败,调用 [onError]。
     * 子类可自行包装 ApiResponse -> data 的提取逻辑后传入。
     */
    protected suspend fun <T> request(
        api: suspend () -> T?,
        onError: (Throwable?) -> Unit = {}
    ): T? {
        return try {
            val result = api()
            if (result == null) { onError(null); null } else result
        } catch (e: Exception) {
            onError(e); null
        }
    }
}
