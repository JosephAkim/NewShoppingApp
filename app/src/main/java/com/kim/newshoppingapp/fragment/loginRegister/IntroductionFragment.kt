package com.kim.newshoppingapp.fragment.loginRegister

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.kim.newshoppingapp.R
import com.kim.newshoppingapp.activities.ShoppingActivity
import com.kim.newshoppingapp.databinding.FragmentIntroductionBinding
import com.kim.newshoppingapp.viewmodel.IntroductionViewModel
import com.kim.newshoppingapp.viewmodel.IntroductionViewModel.Companion.ACCOUNT_OPTIONS_FRAGMENT
import com.kim.newshoppingapp.viewmodel.IntroductionViewModel.Companion.SHOPPING_ACTIVITY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class IntroductionFragment: Fragment() {
    private lateinit var binding: FragmentIntroductionBinding
    private val viewModel by viewModels<IntroductionViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentIntroductionBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonStart.setOnClickListener {
            viewModel.startButtonClicked()
            findNavController().navigate(R.id.action_introductionFragment_to_accountOptionsFragment)
        }

        lifecycleScope.launch {
            viewModel.navigate.collect{
                when(it){
                    SHOPPING_ACTIVITY ->
                        Intent(requireActivity(), ShoppingActivity::class.java).also { intent ->
                        intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                    ACCOUNT_OPTIONS_FRAGMENT ->
                        findNavController().navigate(it)
                }
            }
        }
    }
}