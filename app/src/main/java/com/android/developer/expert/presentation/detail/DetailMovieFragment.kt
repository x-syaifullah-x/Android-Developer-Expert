package com.android.developer.expert.presentation.detail

import android.os.Bundle
import com.android.developer.expert.R
import com.android.developer.expert.databinding.FragmentDetailMovieBinding
import id.xxx.the.movie.db.domain.model.DetailMovieModel
import id.xxx.the.movie.db.domain.model.Type
import id.xxx.base.binding.delegate.viewBinding

class DetailMovieFragment : DetailFragment<DetailMovieModel>(R.layout.fragment_detail_movie) {

    private val binding by viewBinding<FragmentDetailMovieBinding>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.movie.observe(viewLifecycleOwner) { submitData(it) }
    }

    override fun getRecyclerView() = binding.recyclerview.movieRecyclerView

    override fun onSubmitData(data: DetailMovieModel?) {
        super.onSubmitData(data)
        binding.data = data
    }

    override fun getType(id: Int) = Type.Movie(id)
}