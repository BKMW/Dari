package com.bel.dari

import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {
    //======== method get All ==============//
        @get:GET("get_articles")
        var getArticles: Call<ArrayList<Article>>
    //======== method get All by category ========//
    @GET("get_category/{category}")
    fun getArticlesByCategory(
            @Path("category") category: String): Call<ArrayList<Article>>
    //======== method post to insert token to data base =======//
    @POST("register")
    fun insertToken(@Body user: User): Call<User>


}
