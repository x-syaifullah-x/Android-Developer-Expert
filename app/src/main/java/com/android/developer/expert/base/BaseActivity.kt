package com.android.developer.expert.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.android.developer.expert.App
import com.android.developer.expert.di.multibinding.ViewModelFactory
import javax.inject.Inject

abstract class BaseActivity<ActivityViewBinding : ViewBinding> : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    open fun isInject(): Boolean = false

    abstract val binding: ActivityViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        @Suppress("UNCHECKED_CAST")
        if (isInject()) (application as App).appComponent.inject(this as BaseActivity<ViewBinding>)
    }

    /* Forward onOptionsItemSelected to fragment */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        try {
            val navHostFragment = supportFragmentManager.fragments.first() as NavHostFragment
            val fragment = navHostFragment.childFragmentManager.fragments
            fragment.forEach { it.onOptionsItemSelected(item) }
        } finally {
            return super.onOptionsItemSelected(item)
        }
    }
}


