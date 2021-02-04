@file:Suppress("ClassName")

package com

import com.android.developer.expert.App
import com.android.developer.expert.presentation.MainActivity
import com.android.developer.expert.presentation.detail.DetailActivity
import com.android.developer.expert.presentation.search.SearchActivity
import com.android.developer.expert.presentation.splash.SplashActivity
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

typealias MainActivity = ma
typealias DetailActivity = da
typealias SearchActivity = sa

class a : App()

class sa : SearchActivity()

class ma : MainActivity()

class sh : SplashActivity()

class da : DetailActivity()

@GlideModule
class agm : AppGlideModule()