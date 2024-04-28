package com.ondinnonk.newsapp.features.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ondinnonk.newsapp.core.BaseFragment
import com.ondinnonk.newsapp.databinding.FragmentNewsDetailsBinding
import org.koin.core.KoinComponent

class NewsDetailsFragment : BaseFragment<FragmentNewsDetailsBinding, NewsDetailsViewModel>(), KoinComponent {

    override var viewModelSetup = ViewModelSetup(NewsDetailsViewModel::class)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindView(FragmentNewsDetailsBinding.inflate(inflater, container, false))
        return binding.root
    }

    override fun initViews() {
        super.initViews()

    }

    override fun initObservers(): Unit = with(viewModel) {
    }

    override fun initListeners() {
        super.initListeners()
    }


}