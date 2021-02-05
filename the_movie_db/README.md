## THE MOVIE DB

#### Api
* [TheMovieDB](https://developers.themoviedb.org/3/getting-started/introduction)

#### Network

* ##### Movie Api Service
    * getDiscoverMovie -> PageResponse<MovieResult>
    * getMovie -> MovieResponse
    * getRecommendationMovie -> PageResponse<MovieResult>

* ##### Tv Api Service
    * getDiscoverTv -> PageResponse<TvResult>
    * getTv -> TvResponse
    * getRecommendationTv -> PageResponse<TvResult>

* ##### Search Api Service
    * searchMulti -> PageResponse<MultiResult>

* ##### Remote Movie Data Sorce
    * getDiscover(page:Int) : ApiResponse<PageResponse<MovieResult>>
    * getMovieWithMovieRecommendation(id: Int) : Flow<ApiResponse<MovieWithRecommendation>>

* ##### Remote Tv Data Sorce
    * getDiscover(page:Int) : ApiResponse<PageResponse<TvResult>>
    * getTvWithTvRecommendation(id: Int) : Flow<ApiResponse<TvWithRecommendation>>

* ##### Remote Search Data Sorce
    * search() : Flow<ApiResponse<PageResponse<MultiResult>>>


* ##### Security
    * Obfuscation With ProGuard
    * Encryption Database
    * Use Certificate Pinning And Dns Resolver Connection To Server.