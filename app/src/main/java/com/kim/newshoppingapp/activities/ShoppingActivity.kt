package com.kim.newshoppingapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kim.newshoppingapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)
    }
}