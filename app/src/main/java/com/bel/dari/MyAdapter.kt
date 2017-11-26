package com.bel.dari

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

/**
 * Created by Wajdi on 28/10/2017.
 */
class MyAdapter (private var articles:ArrayList<Article>): RecyclerView.Adapter<MyAdapter.AritcleViewHolder>() {

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: AritcleViewHolder, position: Int) {

            val article:Article=articles[position]
        //=== call fun bind in object holder instance of class AritcleViewHolder ===*/
        holder.bind(article)

   /* holder.img.loadUrl(urls.imgUrl+article.img)
        var urls=Urls()
        Picasso.with(holder.img.context).load(urls.imgUrl+article.img).into(holder.img);
        holder.title.text=article.title*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AritcleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view,parent,false )
        return AritcleViewHolder(view)
    }
    /* === class AritcleViewHolder extend class RecyclerView.ViewHolder(item)
      and extends View.OnClickListene === */
   class AritcleViewHolder(item: View): RecyclerView.ViewHolder(item), View.OnClickListener {

        private var img = item.findViewById<ImageView>(R.id.img)
        private var title = item.findViewById<TextView>(R.id.title)
        private var article: Article? = null

        init {
            item.setOnClickListener(this)
        }
        //=== fun bind set data from object article into card_view by item ===/
        fun bind(article: Article) {
            this.article=article
            //== setText
            title.text = article.title
            //== holder.img.loadUrl(urls.imgUrl+article.img)
            val urls=Urls()
            Picasso.with(img.context).load(urls.imgUrl+article.img).into(img)
        }
        //=== end fun
        //=== override fun onClick of class View.OnClickListene ===/
        override fun onClick(view: View) {
            val intent = Intent(view.context, ArticlActivity::class.java)
            intent.putExtra("title",article!!.title)
            intent.putExtra("description",article!!.description)
            intent.putExtra("updated-at",article!!.updated_at )
            intent.putExtra("img",article!!.img )

            view.context.startActivity(intent)
        }
        //=== end override fun
    }
    //=== end class AritcleViewHolder
}