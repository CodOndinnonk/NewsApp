package com.ondinnonk.newsapp.features.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ondinnonk.newsapp.SharedViewModel
import com.ondinnonk.newsapp.core.BaseFragment
import com.ondinnonk.newsapp.databinding.FragmentNewsListBinding
import com.ondinnonk.newsapp.isLandscape
import com.ondinnonk.newsapp.utils.observe
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel
import org.koin.core.KoinComponent

class NewsListFragment : BaseFragment<FragmentNewsListBinding, NewsListViewModel>(), KoinComponent {

    override var viewModelSetup = ViewModelSetup(NewsListViewModel::class)
    private val sharedViewModel: SharedViewModel by lazy { getSharedViewModel() }
    private val adapter = NewsListAdapter(
        onSelect = { viewModel.onOpenDetails(it) },
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindView(FragmentNewsListBinding.inflate(inflater, container, false))
        return binding.root
    }

    override fun initViews() {
        super.initViews()
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter
    }

    override fun initObservers(): Unit = with(viewModel) {
        onUpdateList.observe(viewLifecycleOwner) {
            adapter.setListData(it)
            binding.listRefresh.isRefreshing = false
        }
        onRefreshLoadFinish.observe(viewLifecycleOwner) {
            binding.listRefresh.isRefreshing = false
            it?.message?.let {
                showUserInfo(it)
            }
        }
        onOpenDetails.observe(viewLifecycleOwner) {
            sharedViewModel.onOpenDetails(it)
            if (requireContext().isLandscape().not()) {
                openDetails()
            }
        }
    }

    override fun initListeners() {
        binding.listRefresh.setOnRefreshListener {
            viewModel.onRefreshList()
        }
    }

    private fun openDetails() {
        findNavController().navigate(NewsListFragmentDirections.goDetailsFragment())
    }

    private fun showUserInfo(str: String) {
        //later use dialog
        Toast.makeText(requireContext(), str, Toast.LENGTH_LONG).show()
    }

}