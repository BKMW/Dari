package com.bel.dari

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.gms.ads.*
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.view.View


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    // private lateinit var mAdView : AdView
    // private lateinit var mInterstitialAd: InterstitialAd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        //=========== fun get all article to re'cycleView ==========//
        getArticles()
        //========== Admob inisialize ==========//
        MobileAds.initialize(this, "ca-app-pub-6846643358446035~9902061599")
        /// Interstitial /////
       /* mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-6846643358446035/5238205508"
        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }
        }*/
        //======== fun admob banner ========//
        bannerAdmob()
        //======== ProgressBar =========//
        progressBar.visibility=View.GONE

        //=========== fun get and set token Firebase =======//
        tokenFireBase()
    }
    //////////////========================end onCreate()===============////////////
    //========== admob banner ===========//
    private fun bannerAdmob(){
        //mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    //============function to operation with FireBase FCM ==============//
    private fun tokenFireBase(){
        FirebaseMessaging.getInstance().subscribeToTopic("dari")
        FirebaseInstanceId.getInstance().token
    }
    //============function get all articles ==============//
    private fun getArticles(){
        // must be change access modifier  to correct :) in class Urls of var baseUrl
        val urls=Urls()
        Retrofit.Builder().baseUrl(urls.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
                .getArticles
                .enqueue(object : Callback<ArrayList<Article>> {
                    override fun onFailure(call: Call<ArrayList<Article>>?, t: Throwable?) {
                        Toast.makeText(this@MainActivity,"تاكد من اعدادات الانترنت", Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<ArrayList<Article>>?, response: Response<ArrayList<Article>>?) {



                        rec.layoutManager= LinearLayoutManager(this@MainActivity, LinearLayout.VERTICAL,false)
                        val articles:ArrayList<Article> = response!!.body()!!
                        val adapter = MyAdapter(articles)
                        rec.adapter= adapter
                    //========= progressBar ====================//
                        //val progressBar = findViewById<ProgressBar>(R.id.progressBar)as ProgressBar
                       /* adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                            override fun onChanged() {
                                //progressBar.visibility = View.GONE
                                progressBar.setVisibility(View.GONE)
                            }
                        })*/
                     //============= end progressBar ===========//
                    }


                })

    }
    //============== end fun getArticles =============================//

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }*/

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.

        when (item.itemId) {
            R.id.nav_home -> {
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)

                ////fragment
                /*setTitle("منوعات")
                 var category = Category()
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment, category)
                        .commit()*/



            }
            R.id.nav_divers -> {
                val intent = Intent(this,CategoryActivity::class.java)
                intent.putExtra("Category","منوعات")
                intent.putExtra("id","1")
                startActivity(intent)

                ////fragment
                /*setTitle("منوعات")
                 var category = Category()
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment, category)
                        .commit()*/



            }
            R.id.nav_swites -> {
                val intent = Intent(this,CategoryActivity::class.java)
                intent.putExtra("Category","حلويات")
                intent.putExtra("id","5")

                startActivity(intent)


            }
            R.id.nav_kitchen -> {
                val intent = Intent(this,CategoryActivity::class.java)
                intent.putExtra("Category","مطبخك")
                intent.putExtra("id","2")

                startActivity(intent)


            }
            R.id.nav_health -> {
                val intent = Intent(this,CategoryActivity::class.java)
                intent.putExtra("Category","صحتك")
                intent.putExtra("id","3")

                startActivity(intent)

            }
            R.id.nav_beaty -> {
                val intent = Intent(this,CategoryActivity::class.java)
                intent.putExtra("Category","جمالك")
                intent.putExtra("id","4")

                startActivity(intent)


            }
            R.id.nav_share -> {
                try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("market://details?id="+packageName)
                    startActivity(intent)
                }catch (e:ActivityNotFoundException){
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("https://play.google.com/store/apps/details?id="+packageName)
                    startActivity(intent)

                }

            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
