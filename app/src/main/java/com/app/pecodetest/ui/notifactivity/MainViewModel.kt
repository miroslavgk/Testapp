package com.app.pecodetest.ui.notifactivity

import android.app.Application
import android.view.View
import android.widget.TextView
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.AndroidViewModel
import androidx.viewpager2.widget.ViewPager2
import com.app.pecodetest.adapters.NotifPagerAdapter
import com.app.pecodetest.data.NotifRepository
import com.app.pecodetest.ui.notifscreen.NotifFragment

class MainViewModel(private val app: Application) : AndroidViewModel(app) {

    private var _fragmentsCount: Int = 0
    val fragmentsCount get() = _fragmentsCount
    private var notifRepository: NotifRepository? = null

    init {
        notifRepository = NotifRepository(app)
    }

    private fun increaseFragmentsCount() = _fragmentsCount++

    private fun decreaseFragmentsCount() = _fragmentsCount--

    fun addFragment(adapter: NotifPagerAdapter, vp: ViewPager2) {
        addOneFragment(adapter)
        vp.setCurrentItem(fragmentsCount, true)
        vp.offscreenPageLimit = fragmentsCount
    }

    fun removeFragment(adapter: NotifPagerAdapter, vp: ViewPager2) {
        decreaseFragmentsCount()
        vp.setCurrentItem(fragmentsCount - 1, true)
        adapter.removeFragment(fragmentsCount)
        adapter.notifyItemRemoved(fragmentsCount)
        vp.offscreenPageLimit = fragmentsCount
    }

    fun hideRemoveBtn(removeBtn: View) {
        removeBtn.visibility = View.GONE
    }

    fun showRemoveBtn(removeBtn: View) {
        removeBtn.visibility = View.VISIBLE
    }

    fun saveFragmentsCount(count: Int) {
        notifRepository?.saveFragmentsCount(count)
    }

    fun readFragmentsCount(): Int? = notifRepository?.readFragmentsCount()

    fun checkFragmentsCount(count: Int?, adapter: NotifPagerAdapter, tvId: TextView) {
        if (count != null && count != 0) {
            for (i in 0 until count) {
                addOneFragment(adapter)
                tvId.text = fragmentsCount.toString()
            }
        } else {
            addOneFragment(adapter)
            tvId.text = fragmentsCount.toString()
        }
    }

    private fun addOneFragment(adapter: NotifPagerAdapter){
        increaseFragmentsCount()
        adapter.addFragment(NotifFragment().invoke(fragmentsCount))
    }

    fun clearAllNotifications() {
        val notifManager = NotificationManagerCompat.from(app)
        notifManager.cancelAll()
    }

}