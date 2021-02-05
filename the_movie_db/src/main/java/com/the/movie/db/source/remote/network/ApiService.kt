package com.the.movie.db.source.remote.network

import com.the.movie.db.source.remote.network.services.MovieApiService
import com.the.movie.db.source.remote.network.services.SearchApiService
import com.the.movie.db.source.remote.network.services.TvApiService

interface ApiService : MovieApiService, TvApiService, SearchApiService