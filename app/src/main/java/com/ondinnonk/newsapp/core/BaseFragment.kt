package com.ondinnonk.newsapp.core

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.KoinComponent
import kotlin.reflect.KClass


@Suppress("unused")
abstract class BaseFragment<BINDING, VIEW_MODEL : BaseViewModel> : Fragment(), KoinComponent {

    private var _binding: BINDING? = null
    val binding get() = _binding!!
    abstract var viewModelSetup: ViewModelSetup<VIEW_MODEL>
    val viewModel: VIEW_MODEL by lazy {
        if (viewModelSetup.isShared) getSharedViewModel(viewModelSetup.viewModelClass)
        else getViewModel(viewModelSetup.viewModelClass)
    }

    open fun initViews() {}
    open fun initListeners() {}
    open fun initObservers() {}

    override fun onStart() {
        super.onStart()
        initObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onResume() {
        super.onResume()
        initListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun bindView(v: BINDING) {
        _binding = v
    }

    fun launch(dispatcher: CoroutineDispatcher = Dispatchers.Default, action: suspend () -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch(dispatcher) { action() }
    }

    data class ViewModelSetup<V : BaseViewModel>(
        var viewModelClass: KClass<V>,
        var isShared: Boolean = false
    )

}