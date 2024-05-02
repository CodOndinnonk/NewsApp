package com.ondinnonk.newsapp.features.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.bumptech.glide.Glide
import com.ondinnonk.newsapp.R
import com.ondinnonk.newsapp.SharedViewModel
import com.ondinnonk.newsapp.core.BaseFragment
import com.ondinnonk.newsapp.databinding.FragmentNewsDetailsBinding
import com.ondinnonk.newsapp.features.NewsUiModel
import com.ondinnonk.newsapp.utils.observe
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel
import org.koin.core.KoinComponent

class NewsDetailsFragment : BaseFragment<FragmentNewsDetailsBinding, NewsDetailsViewModel>(), KoinComponent {

    override var viewModelSetup = ViewModelSetup(NewsDetailsViewModel::class)
    private val sharedViewModel: SharedViewModel by lazy { getSharedViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                sharedViewModel.onCloseDetails()
                parentFragmentManager.popBackStack()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindView(FragmentNewsDetailsBinding.inflate(inflater, container, false))
        return binding.root
    }

    override fun initObservers(): Unit {
        sharedViewModel.openDetails.observe(viewLifecycleOwner) {
            it?.let {
                setupUi(it)
            }
        }
    }

    override fun initListeners() {
        super.initListeners()
        binding.closeBtn.setOnClickListener {
            sharedViewModel.onCloseDetails()
            parentFragmentManager.popBackStack()
        }
    }

    private fun setupUi(model: NewsUiModel) {
        binding.title.text = model.title
        binding.author.text = model.author
        binding.date.text = model.date
        binding.content.text = model.content

        model.image?.let { imgUrl ->
            Glide.with(this)
                .load(imgUrl)
                .centerCrop()
                .placeholder(R.drawable.picture_placeholder)
                .into(binding.image)
        }
    }

}