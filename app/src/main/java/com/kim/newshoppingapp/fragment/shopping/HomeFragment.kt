package com.kim.newshoppingapp.fragment.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.kim.newshoppingapp.adapters.HomeViewpagerAdapter
import com.kim.newshoppingapp.databinding.FragmentHomeBinding
import com.kim.newshoppingapp.databinding.FragmentIntroductionBinding
import com.kim.newshoppingapp.fragment.categories.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(){
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoriesFragment = arrayListOf<Fragment>(
            MainCategoryFragment(),
            AccessoryFragment(),
            BaseCategoryFragment(),
            ChairFragment(),
            CupboardFragment(),
            FunitureFragment(),
            TableFragment()
        )

        val viewPager2Adapter = HomeViewpagerAdapter(categoriesFragment, childFragmentManager, lifecycle)
        binding.viewPager.adapter = viewPager2Adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.text= "Main"
                1 -> tab.text= "chair"
                2 -> tab.text= "cupboard"
                3 -> tab.text= "Accessory"
                4 -> tab.text= "Furniture"
                5 -> tab.text= "table"
            }
        }.attach()
    }
}