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
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.app_bar_category.*
import kotlinx.android.synthetic.main.content_category.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CategoryActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout_cat, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout_cat.addDrawerListener(toggle)
        toggle.syncState()
        nav_view_cat.setNavigationItemSelectedListener(this)

        //===== get variable from intent category and it id =====//
        val  data=intent
        val category=data.extras.getString("Category")
        val id=data.extras.getString("id")
        //======== setTitle(category)======//
        title=category
        //========= fun get Articles By Category ==========//
        getArticlesByCategory(id)
        //====== Admob inisialize ========//
        MobileAds.initialize(this, "ca-app-pub-6846643358446035~9902061599")
        //======== fun admob banner ========//
        bannerAdmob()
        //======== ProgressBar =========//
        progressBar.visibility=View.GONE

    }
    /////////////////======= end onCreate()=================/////////////////////
    //========== admob banner ===========//
    private fun bannerAdmob(){
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }
    //========== get articles by category =======//
    private fun getArticlesByCategory(category:String){
        // must be change access modifier  to correct :) in class Urls of var baseUrl
        val urls=Urls()
        Retrofit.Builder().baseUrl(urls.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
                .getArticlesByCategory(category)
                .enqueue(object : Callback<ArrayList<Article>> {
                    override fun onFailure(call: Call<ArrayList<Article>>?, t: Throwable?) {
                        Toast.makeText(this@CategoryActivity,"تاكد من اعدادات الانترنت", Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<ArrayList<Article>>?, response: Response<ArrayList<Article>>?) {



                        rec_category.layoutManager= LinearLayoutManager(this@CategoryActivity, LinearLayout.VERTICAL,false)
                        val articles:ArrayList<Article> = response!!.body()!!
                        val adapter = MyAdapter(articles)
                        rec_category.adapter= adapter
                        //=========progressBar========//
                       /* adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                            override fun onChanged() {
                                progressBar.visibility = View.GONE
                            }
                        })*/
                       //========end progressBar=========//

                    }


                })

    }
    //=========== end fun get Articles =======//


    override fun onBackPressed() {
        if (drawer_layout_cat.isDrawerOpen(GravityCompat.START)) {
            drawer_layout_cat.closeDrawer(GravityCompat.START)
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
                }catch (e: ActivityNotFoundException){
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("https://play.google.com/store/apps/details?id="+packageName)
                    startActivity(intent)

                }

            }

        }

        drawer_layout_cat.closeDrawer(GravityCompat.START)
        return true
    }
}
