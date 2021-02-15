package com.android.developer.expert.presentation.error

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.developer.expert.R
import com.android.developer.expert.base.BasePagingDataFragment.Companion.IS_ERROR
import com.android.developer.expert.databinding.FragmentErrorBinding
import com.base.binding.delegate.viewBinding
import com.base.extension.setVisibleWithCircularReveal

class ErrorFragment : Fragment(R.layout.fragment_error) {

    private val binding by viewBinding<FragmentErrorBinding>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.icError.setVisibleWithCircularReveal(true)
        binding.textMessage.setVisibleWithCircularReveal(true)

        binding.retry.setOnClickListener {
            it.visibility = View.GONE
            val id = findNavController().previousBackStackEntry?.destination?.id
                ?: throw Exception()
            findNavController().navigate(id, bundleOf(IS_ERROR to true))
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })
    }
}