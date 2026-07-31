package com.zhenai2.android.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zhenai2.common.router.RouterPath

/**
 * 主页 —— 底部 Tab 容器
 *
 * 原 App: com.zhenai2.android.ui.main.MainActivity (复刻后包名)
 * 复刻珍爱 App 主页四 Tab 结构: 推荐 / 消息 / 动态 / 我的
 * 各 Tab Fragment 通过 ARouter 按模块获取,实现模块解耦。
 */
@Route(path = RouterPath.MAIN)
class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var bottomNav: BottomNavigationView

    private val tabs = listOf(
        RouterPath.HOME_RECOMMEND,
        RouterPath.CHAT_CONVERSATION,
        RouterPath.MOMENT_SQUARE,
        RouterPath.MINE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // UI 由迁移的 layout 承载,这里用代码构建保证可运行
        viewPager = ViewPager2(this).apply { id = android.R.id.list }
        bottomNav = BottomNavigationView(this).apply { id = android.R.id.text1 }

        setContentView(
            android.widget.LinearLayout(this).apply {
                orientation = android.widget.LinearLayout.VERTICAL
                addView(viewPager, android.widget.LinearLayout.LayoutParams(
                    -1, 0, 1f
                ))
                addView(bottomNav, android.widget.LinearLayout.LayoutParams(-1, -2))
            }
        )

        setupViewPager()
        setupBottomNav()
    }

    private fun setupViewPager() {
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = tabs.size
            override fun createFragment(position: Int): Fragment =
                ARouter.getInstance().build(tabs[position])
                    .navigation() as? Fragment
                    ?: androidx.fragment.app.Fragment() // 路由缺失时兜底,避免 null 强转闪退
        }
        viewPager.isUserInputEnabled = false
    }

    private fun setupBottomNav() {
        val menu = bottomNav.menu
        menu.add(0, 0, 0, "推荐")
        menu.add(0, 1, 0, "消息")
        menu.add(0, 2, 0, "动态")
        menu.add(0, 3, 0, "我的")
        bottomNav.setOnItemSelectedListener {
            viewPager.currentItem = it.itemId
            true
        }
    }
}
