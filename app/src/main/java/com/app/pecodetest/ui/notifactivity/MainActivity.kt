package com.app.pecodetest.ui.notifactivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.pecodetest.R
import com.app.pecodetest.adapters.NotifPagerAdapter
import com.app.pecodetest.databinding.ActivityMainBinding

class MainActivity : FragmentActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: NotifPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFields()
        loadData()
        initListeners()
    }

    private fun initFields() {
        with(binding) {
            adapter = NotifPagerAdapter(this@MainActivity)
            vpMain.adapter = adapter
        }
    }

    private fun loadData() {
        with(binding) {
            with(viewModel) {
                val savedFragmentsCount = readFragmentsCount()
                checkFragmentsCount(savedFragmentsCount, adapter, tvFragmentId)
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            when (intent.action) {
                Intent.ACTION_VIEW -> {
                    val pageFromNotif = intent.data?.lastPathSegment?.toIntOrNull() ?: 1
                    binding.vpMain.setCurrentItem(pageFromNotif - 1, true)
                }
            }
        }
    }

    private fun initListeners() {
        with(binding) {
            with(viewModel) {

                addBtn.setOnClickListener {
                    addFragment(adapter, vpMain)
                }

                removeBtn.setOnClickListener {
                    removeFragment(adapter, vpMain)
                }

                vpMain.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)

                        if (position == 0) {
                            hideRemoveBtn(removeBtn)
                        } else {
                            showRemoveBtn(removeBtn)
                        }
                        tvFragmentId.text = (position + 1).toString()
                    }
                })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        with(viewModel) {
            clearAllNotifications()
            saveFragmentsCount(fragmentsCount)
        }
    }
}