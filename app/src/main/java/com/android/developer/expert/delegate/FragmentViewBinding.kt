package com.android.developer.expert.delegate

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T : ViewBinding> Fragment.viewBinding(
    noinline blockOnDestroy: T.() -> Unit = {},
) = FragmentViewBinding(T::class.java, this, blockOnDestroy)

class FragmentViewBinding<BindingClass : ViewBinding>(
    private val bindingClass: Class<BindingClass>,
    private val fragment: Fragment,
    private val blockOnDestroy: BindingClass.() -> Unit = {},
) : ReadOnlyProperty<Fragment, BindingClass>, LifecycleObserver {

    private var binding: BindingClass? = null

    init {
        fragment.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        fragment.viewLifecycleOwnerLiveData.observe(fragment) {
            it.lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                @Suppress("unused")
                fun onDestroyView() {
                    blockOnDestroy(binding ?: return)
                    binding = null
                }
            })
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Fragment, property: KProperty<*>): BindingClass {
        binding?.let { return it }

        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            error("Cannot access view bindings. View lifecycle is ${lifecycle.currentState}!")
        }

        val invoke = bindingClass.getMethod("bind", View::class.java)
            .invoke(null, thisRef.requireView()) as BindingClass
        return invoke.also { this.binding = it }
    }
}