package com.android.developer.expert.presentation.detail

import android.os.Bundle
import com.android.developer.expert.R
import com.android.developer.expert.core.domain.model.DetailTvModel
import com.android.developer.expert.core.domain.model.Type
import com.android.developer.expert.databinding.FragmentDetailTvBinding
import com.base.binding.delegate.viewBinding

class DetailTvFragment : DetailFragment<DetailTvModel>(R.layout.fragment_detail_tv) {

    private val binding by viewBinding<FragmentDetailTvBinding>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.tv.observe(viewLifecycleOwner) { submitData(it) }
    }

    override fun getRecyclerView() = binding.recyclerview.movieRecyclerView

    override fun onSubmitData(data: DetailTvModel?) {
        super.onSubmitData(data)
        binding.data = data
    }

    override fun getType(id: Int) = Type.Tv(id)
}