package com.zhenai2.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhenai2.network.ApiResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

/**
 * ViewModel 基类 —— 统一协程异常处理。
 */
abstract class BaseViewModel : ViewModel() {

    protected fun launch(
        onError: (Throwable) -> Unit = {},
        block: suspend () -> Unit
    ) {
        val handler = CoroutineExceptionHandler { _, e -> onError(e) }
        viewModelScope.launch(handler) { block() }
    }

    /** 统一请求封装: 处理 isError/errorCode,返回 data 或 null */
    protected suspend fun <T> request(
        api: suspend () -> ApiResponse<T>,
        onError: (ApiResponse<T>) -> Unit = {}
    ): T? {
        val resp = try { api() } catch (e: Exception) { return null }
        return if (resp.isError) { onError(resp); null } else resp.data
    }
}
