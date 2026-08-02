package com.zhenai2.network

import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.InetAddress
import java.net.Socket
import java.net.SocketAddress
import java.nio.channels.SocketChannel
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

/**
 * 平台默认 SSLSocketFactory 包装器
 *
 * 核心作用: 阻止 OkHttp 的 ConnectionSpec 对 SSLSocket 调用 setEnabledCipherSuites()
 * 和 setEnabledProtocols() 来过滤密码套件。
 *
 * 原理:
 * 1. OkHttp 在建立 TLS 连接时,会从 ConnectionSpec 中取出允许的密码套件列表,
 *    然后调用 SSLSocket.setEnabledCipherSuites() 来限制 Socket 只使用这些套件。
 * 2. 这会导致 JA3 指纹被 OkHttp 的 ConnectionSpec 修改,与官方 App 的 consCrypt
 *    默认指纹不同。
 * 3. 本包装器重写 setEnabledCipherSuites() 和 setEnabledProtocols() 为空操作,
 *    让 Socket 使用平台默认的完整密码套件集,从而保持 JA3 指纹与官方 App 一致。
 */
class PlatformSSLSocketFactory : SSLSocketFactory() {

    private val delegate: SSLSocketFactory
    private val trustManager: X509TrustManager

    init {
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, null, null)
        delegate = sslContext.socketFactory

        val tmf = javax.net.ssl.TrustManagerFactory.getInstance(
            javax.net.ssl.TrustManagerFactory.getDefaultAlgorithm()
        ).apply { init(null as java.security.KeyStore?) }
        trustManager = tmf.trustManagers.filterIsInstance<X509TrustManager>().first()
    }

    fun trustManager(): X509TrustManager = trustManager

    // ---- 包装委托方法 ----

    override fun createSocket(): Socket = wrap(delegate.createSocket())

    override fun createSocket(s: Socket, host: String, port: Int, autoClose: Boolean): Socket =
        wrap(delegate.createSocket(s, host, port, autoClose))

    override fun createSocket(host: String, port: Int): Socket =
        wrap(delegate.createSocket(host, port))

    override fun createSocket(host: String, port: Int, localHost: InetAddress, localPort: Int): Socket =
        wrap(delegate.createSocket(host, port, localHost, localPort))

    override fun createSocket(host: InetAddress, port: Int): Socket =
        wrap(delegate.createSocket(host, port))

    override fun createSocket(host: InetAddress, port: Int, localHost: InetAddress, localPort: Int): Socket =
        wrap(delegate.createSocket(host, port, localHost, localPort))

    override fun getDefaultCipherSuites(): Array<String> = delegate.defaultCipherSuites

    override fun getSupportedCipherSuites(): Array<String> = delegate.supportedCipherSuites

    /**
     * 包装 SSLSocket,阻止 OkHttp 修改密码套件和协议版本
     */
    private fun wrap(socket: Socket): Socket {
        if (socket is SSLSocket) {
            return IgnoreFilterSSLSocket(socket)
        }
        return socket
    }
}

/**
 * 忽略过滤器 SSLSocket 包装器
 *
 * 重写 setEnabledCipherSuites() 和 setEnabledProtocols() 为空操作,
 * 让底层 Socket 使用平台默认的密码套件和协议版本。
 * 其他所有方法委托给原始 SSLSocket。
 */
private class IgnoreFilterSSLSocket(
    private val delegate: SSLSocket
) : SSLSocket() {

    override fun setEnabledCipherSuites(suites: Array<String>) {
        // 空操作: 阻止 OkHttp 过滤密码套件
        // 让底层 Socket 使用平台默认的密码套件集
    }

    override fun setEnabledProtocols(protocols: Array<String>) {
        // 空操作: 阻止 OkHttp 限制 TLS 协议版本
        // 让底层 Socket 使用平台默认的协议版本
    }

    // ---- 以下所有方法直接委托给原始 SSLSocket ----

    override fun getEnabledCipherSuites(): Array<String> = delegate.enabledCipherSuites
    override fun getSupportedCipherSuites(): Array<String> = delegate.supportedCipherSuites
    override fun getEnabledProtocols(): Array<String> = delegate.enabledProtocols
    override fun getSupportedProtocols(): Array<String> = delegate.supportedProtocols

    override fun getNeedClientAuth(): Boolean = delegate.needClientAuth
    override fun setNeedClientAuth(need: Boolean) = delegate.setNeedClientAuth(need)
    override fun getWantClientAuth(): Boolean = delegate.wantClientAuth
    override fun setWantClientAuth(want: Boolean) = delegate.setWantClientAuth(want)
    override fun getUseClientMode(): Boolean = delegate.useClientMode
    override fun setUseClientMode(mode: Boolean) = delegate.setUseClientMode(mode)

    override fun startHandshake() = delegate.startHandshake()
    override fun getSession() = delegate.session

    override fun getHandshakeApplicationProtocol(): String? = delegate.handshakeApplicationProtocol
    override fun getApplicationProtocol(): String? = delegate.applicationProtocol
    override fun setHandshakeApplicationProtocolSelector(selector: java.util.function.BiFunction<SSLSocket, MutableList<String>, String>?) =
        delegate.setHandshakeApplicationProtocolSelector(selector)
    override fun getHandshakeApplicationProtocolSelector(): java.util.function.BiFunction<SSLSocket, MutableList<String>, String>? =
        delegate.handshakeApplicationProtocolSelector

    // Socket 方法
    override fun getInputStream(): InputStream = delegate.inputStream
    override fun getOutputStream(): OutputStream = delegate.outputStream
    override fun getTcpNoDelay(): Boolean = delegate.tcpNoDelay
    override fun setTcpNoDelay(on: Boolean) = delegate.setTcpNoDelay(on)
    override fun getReuseAddress(): Boolean = delegate.reuseAddress
    override fun setReuseAddress(on: Boolean) = delegate.setReuseAddress(on)
    override fun getSoTimeout(): Int = delegate.soTimeout
    override fun setSoTimeout(timeout: Int) = delegate.setSoTimeout(timeout)
    override fun getSendBufferSize(): Int = delegate.sendBufferSize
    override fun setSendBufferSize(size: Int) = delegate.setSendBufferSize(size)
    override fun getReceiveBufferSize(): Int = delegate.receiveBufferSize
    override fun setReceiveBufferSize(size: Int) = delegate.setReceiveBufferSize(size)
    override fun getKeepAlive(): Boolean = delegate.keepAlive
    override fun setKeepAlive(on: Boolean) = delegate.setKeepAlive(on)
    override fun getOOBInline(): Boolean = delegate.oobInline
    override fun setOOBInline(on: Boolean) = delegate.setOOBInline(on)
    override fun getSoLinger(): Int = delegate.soLinger
    override fun setSoLinger(on: Boolean, timeout: Int) = delegate.setSoLinger(on, timeout)
    override fun getTrafficClass(): Int = delegate.trafficClass
    override fun setTrafficClass(tc: Int) = delegate.setTrafficClass(tc)

    override fun bind(bindpoint: SocketAddress) = delegate.bind(bindpoint)
    override fun connect(endpoint: SocketAddress) = delegate.connect(endpoint)
    override fun connect(endpoint: SocketAddress, timeout: Int) = delegate.connect(endpoint, timeout)
    override fun isBound(): Boolean = delegate.isBound
    override fun isClosed(): Boolean = delegate.isClosed
    override fun isConnected(): Boolean = delegate.isConnected
    override fun isInputShutdown(): Boolean = delegate.isInputShutdown
    override fun isOutputShutdown(): Boolean = delegate.isOutputShutdown
    override fun shutdownInput() = delegate.shutdownInput()
    override fun shutdownOutput() = delegate.shutdownOutput()
    override fun close() = delegate.close()
    override fun toString(): String = delegate.toString()
    override fun getLocalAddress(): InetAddress = delegate.localAddress
    override fun getLocalPort(): Int = delegate.localPort
    override fun getLocalSocketAddress(): SocketAddress? = delegate.localSocketAddress
    override fun getInetAddress(): InetAddress = delegate.inetAddress
    override fun getPort(): Int = delegate.port
    override fun getRemoteSocketAddress(): SocketAddress? = delegate.remoteSocketAddress
    override fun getChannel(): SocketChannel? = delegate.channel

    override fun setPerformancePreferences(connectionTime: Int, latency: Int, bandwidth: Int) =
        delegate.setPerformancePreferences(connectionTime, latency, bandwidth)

    override fun addHandshakeCompletedListener(listener: javax.net.ssl.HandshakeCompletedListener) =
        delegate.addHandshakeCompletedListener(listener)

    override fun removeHandshakeCompletedListener(listener: javax.net.ssl.HandshakeCompletedListener) =
        delegate.removeHandshakeCompletedListener(listener)

    override fun getEnableSessionCreation(): Boolean = delegate.enableSessionCreation
    override fun setEnableSessionCreation(flag: Boolean) = delegate.setEnableSessionCreation(flag)
}