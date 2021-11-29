package com.app.pecodetest.ui.notifscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.pecodetest.R
import com.app.pecodetest.databinding.FragmentNotifBinding


class NotifFragment : Fragment(R.layout.fragment_notif) {

    private val binding by viewBinding(FragmentNotifBinding::bind)
    private val viewModel by viewModels<NotifViewModel>()
    private var tempFragmentId: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFields()
        initListeners()
    }

    private fun initFields() {
        viewModel.fragmentId = tempFragmentId
    }

    private fun initListeners() {
        with(binding) {
            createNotifBtn.setOnClickListener {
                with(viewModel) {
                    createNotification()
                }
            }
        }
    }

    fun invoke(id: Int): NotifFragment {
        tempFragmentId = id
        return this
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearNotification()
    }

}