package com.test.sawada.fragnavteest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavLogger
import com.ncapdevi.fragnav.FragNavSwitchController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import com.ncapdevi.fragnav.tabhistory.UniqueTabHistoryStrategy
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FragNavController.RootFragmentListener,
    FragNavController.TransactionListener {

    private val fragNavController: FragNavController = FragNavController(supportFragmentManager, R.id.container)

    override val numberOfRootFragments: Int = 3


    override fun getRootFragment(index: Int): Fragment {
        when (index) {
            0 -> return HomeFragment.newInstance("ホーム")
            1 -> return HomeFragment.newInstance("検索")
            2 -> return HomeFragment.newInstance("ブックマーク")
        }
        throw IllegalStateException("これはあかんで")
    }

    override fun onFragmentTransaction(fragment: Fragment?, transactionType: FragNavController.TransactionType) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTabTransaction(fragment: Fragment?, index: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragNavController.apply {
            transactionListener = this@MainActivity
            rootFragmentListener = this@MainActivity
            createEager = true
            fragNavLogger = object : FragNavLogger {
                override fun error(message: String, throwable: Throwable) {
                    Log.e("error", message, throwable)
                }
            }

            fragmentHideStrategy = FragNavController.DETACH_ON_NAVIGATE_HIDE_ON_SWITCH
            navigationStrategy = UniqueTabHistoryStrategy(object : FragNavSwitchController {
                override fun switchTab(index: Int, transactionOptions: FragNavTransactionOptions?) {
                    bottom_navigation.selectedItemId = getItemId(index)
                }

            })
        }

        val fragments = listOf(
            HomeFragment.newInstance("home"),
            HomeFragment.newInstance("Search"),
            HomeFragment.newInstance("BookMark")
        )

        fragNavController.rootFragments = fragments
        fragNavController.initialize(FragNavController.TAB1, savedInstanceState)

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        fragNavController.onSaveInstanceState(outState!!)
    }

    private fun getItemId(index: Int): Int {
        when(index){
            0 -> R.id.menu_home
            1 -> R.id.menu_search
            2 -> R.id.menu_book
        }
        throw IllegalStateException("これはあかんで")
    }
}
