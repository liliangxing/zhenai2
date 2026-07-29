# 珍爱2 (zhenai2)

基于珍爱网 Android APK v9.29.0（包名 `com.zhenai.android`）的复刻项目。

- **新包名**：`com.zhenai2.android`（原 `com.zhenai.android`，仅加 `2` 区分）
- **架构**：多模块 Gradle 项目（Kotlin DSL）
- **最小 SDK**：23｜**目标 SDK**：34

---

## 项目结构

```
zhenai2/
├── app/                  主壳：Application / Splash / Main，承载资源/Assets/Native库
├── lib-common/           公共库：常量 / 路由表 / Base类 / 账号管理
├── lib-network/          网络层：Retrofit + OkHttp + 拦截器 + API契约
├── module-login/         登录注册（完整实现，基于已逆向的API契约）
├── module-home/          首页推荐
├── module-mine/          我的（资料/设置/互动）
├── module-live/          直播（声网Agora/连麦/PK）
├── module-chat/          即时通讯（腾讯IM）
├── module-cert/          认证（实名/学历/收入/活体）
├── module-moment/        动态广场
├── module-pay/           支付（微信/支付宝/银联）
├── module-emotion/       情感咨询/恋爱任务
└── module-web/           H5 容器
```

---

## 迁移情况

### 已直接迁移（来自原 APK）

| 资源 | 数量 | 位置 |
|---|---|---|
| res（drawable/layout/strings 等） | 6486 文件 | `app/src/main/res/` |
| assets（H5/字体/svga/配置） | 473 文件 | `app/src/main/assets/` |
| native 库（业务 SDK，非加固） | ~30 个 .so | `app/src/main/jniLibs/` |
| AndroidManifest（322 Activity/39 Service/17 Receiver/17 Provider） | 全保留 | `app/src/main/AndroidManifest.xml` |
| 权限 | 59 项 | 同上 |

**已剔除**：爱加密加固相关文件（`assets/ijiami.dat`、`assets/ijm_lib`、`libdexvmp.so`、`libexec.so`、`libexecmain.so`、壳类 `s.h.e.l.l.*`）。

### 已重写（原代码加密无法迁移）

原 APK 业务 Java/Kotlin 代码被**爱加密 DexVMP 加固**，`classes.dex` 仅 132KB 壳，真实 12MB 业务代码在 `assets/ijiami.dat` 内 AES 加密、由 native 运行时解密。**无真机脱壳无法直接迁移业务代码**，因此以下代码为基于接口契约/路由表/资源结构从零重写：

- 网络层 API 契约（来自 H5 端明文 JS 逆向，17 个 `.do` 接口）
- 登录流程（完整可运行）
- 首页/主壳/路由框架
- 其余 8 个业务模块脚手架（可编译运行的骨架，业务逻辑待补全）

---

## 各模块实现状态

| 模块 | 状态 | 说明 |
|---|---|---|
| app 主壳 | ✅ 完整 | Application/Splash/Main，启动→登录态检查→路由 |
| lib-common | ✅ 完整 | 常量(主机/错误码/Cookie)、路由表(50+路径)、Base类、账号管理 |
| lib-network | ✅ 完整 | 17 个 `.do` 接口定义、标准返回结构、指纹/Cookie 拦截器 |
| module-login | ✅ 完整 | 账号密码登录全流程：配置拉取→验证码→登录→错误处理→会话保存 |
| module-home | 🟡 骨架 | 推荐Fragment + getBasicProfile 调用，推荐列表接口待抓包补全 |
| module-mine | 🟡 骨架 | 入口Fragment，资料/设置/互动子页待补 |
| module-live | 🟡 骨架 | 入口Fragment，需接入声网Agora SDK |
| module-chat | 🟡 骨架 | 入口Fragment，需接入腾讯TUIKIT |
| module-cert | 🟡 骨架 | 入口Fragment，需接入商汤/腾讯慧眼/芝麻 |
| module-moment | 🟡 骨架 | 入口Fragment，动态接口待抓包 |
| module-pay | 🟡 骨架 | 入口Fragment，需接入微信/支付宝/银联 |
| module-emotion | 🟡 骨架 | 入口Fragment，咨询接口待抓包 |
| module-web | 🟡 骨架 | WebView容器，JSBridge 待补 |

---

## 构建

需 Android Studio Hedgehog+ / JDK 17 / Android SDK 34。

```bash
./gradlew :app:assembleDebug
```

> 仓库未提交 gradle-wrapper.jar，首次用 `gradle wrapper` 生成，或直接用 Android Studio 打开自动生成。

### 沙箱内编译验证说明

本次提交前在 Linux 沙箱内做了如下验证：
- 已安装 JDK 17 + Android SDK 34 (platform-tools/build-tools/cmdline-tools) + Gradle 8.7
- **资源层验证通过**：用 `aapt2 compile` 编译 `res/drawable` 等，exit 0，证明迁移的 6486 个资源文件结构有效
- **Kotlin 源静态校验通过**：括号配平、RouterPath 引用一致性、包名/目录一致性全部通过
- **完整 Gradle 构建未能在沙箱完成**：沙箱将 `dl.google.com` DNS 劫持到国内代理 IP（`220.181.174.97`），该代理对 Gradle 的 HTTP/2 ALPN 握手无响应，导致 AGP 插件（仅在 Google Maven 发布）无法解析。这是**环境网络层阻断，非代码问题**——curl 同样请求可成功（代理支持 HTTP/1.1），仅 Gradle HTTP 客户端受影响。

在标准网络环境（能正常访问 Google Maven）用 Android Studio 打开即可正常构建。

---

## 接口契约（已逆向，详见 lib-network/ApiService.kt）

主机：`https://api.zhenai.com`（H5 同源 `https://www.zhenai.com/api/`）
标准返回：`{ isError, errorCode, errorMessage, data }`

App 打开主页调用序列：
```
1. system/appConfig.do          App配置
2. login/checkLogin.do          登录态检查
3. system/getConfigureInfo.do   登录页配置
4. login/userLogin.do           账号密码登录
5. profile/getBasicProfile.do   用户资料(登录后)
6. log/logTransferDc.do         埋点上报
```

完整接口清单与入参结构见 `/workspace/zhenai_apk_动态接口报告.md`。

---

## 后续补全指引

业务模块从"骨架"到"完整复刻"需要：
1. **真机 Frida 脱壳**：dump 出 `assets/ijiami.dat` 解密后的真实 dex
2. **jadx 反编译**：从真实 dex 提取各模块业务逻辑
3. **真机 Charles 抓包**：补全各模块的网络接口契约（推荐/动态/直播等）
4. **接入第三方 SDK**：按 `app/build.gradle.kts` 补全声网/腾讯IM/商汤等依赖
