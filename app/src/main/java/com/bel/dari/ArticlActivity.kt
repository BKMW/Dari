package com.bel.dari

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.google.android.gms.ads.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_articl.*
import kotlinx.android.synthetic.main.content_articl.*

class ArticlActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articl)
        setSupportActionBar(toolbar)
        //====== add button back =======//
        //getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //====== Admob inisialize ========//
        MobileAds.initialize(this, "ca-app-pub-6846643358446035~9902061599")
        //======== fun admob banner ========//
        bannerAdmob()
        //======= to display long text on txtview ======//
        //description.movementMethod = ScrollingMovementMethod()
       //=========== get variable from intent==============//
        val  data=intent
        val titleArticle=data.extras.getString("title")
        //val updatedAt=data.extras.getString("updated_at")
        val imgArticle=data.extras.getString("img")
        val descriptionArticle=data.extras.getString("description")
        //======setTitle(category)=====//
        title=titleArticle
        //======= set variable into view of this activity ========//
        val urls = Urls()
        Picasso.with(this).load(urls.imgUrl+imgArticle).into(img)
        //updated_at.text=updatedAt
        title_article.text=titleArticle
        description.text=descriptionArticle
    }
    /////////////////======= end onCreate()=================/////////////////////
    //========== admob banner ===========//
    private fun bannerAdmob(){
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }
    //======== override fun add button back =========//
    override fun onOptionsItemSelected(item: MenuItem):Boolean {
        val id:Int = item.itemId
        if(id==android.R.id.home) {
            this.finish()
        }

        return super.onOptionsItemSelected(item)
    }
    //==============end override fun

}
