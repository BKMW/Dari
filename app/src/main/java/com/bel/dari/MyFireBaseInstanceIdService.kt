package com.bel.dari

import com.google.firebase.iid.FirebaseInstanceIdService
import com.google.firebase.iid.FirebaseInstanceId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class MyFireBaseInstanceIdService: FirebaseInstanceIdService() {
    override fun onTokenRefresh() {
        //=== Get updated InstanceID token.
        val token = FirebaseInstanceId.getInstance().token!!
        //To displaying token on logcat
        //Log.d("TOKEN", "Refreshed token: " + refreshedToken!!)
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

        //======= call func send token and insert it into  db table users ========//
        val user=User(token)
        sendRegistrationToServer(user)
    }
    //======= end override fun
    //======= func send token and insert it into  db table users ========//
    private fun sendRegistrationToServer(user:User){
        val urls=Urls()
        Retrofit.Builder().baseUrl(urls.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
                .insertToken(user)
                .enqueue(object : Callback<User> {
                    override fun onFailure(call: Call<User>?, t: Throwable?) {
                        // Toast.makeText(this@MainActivity,"Failed", Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<User>?, response: Response<User>?) {
                        //Toast.makeText(this@MainActivity,"success", Toast.LENGTH_LONG).show()
                    }

                })
    }
    //====== end fun

}