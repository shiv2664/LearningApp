package com.shivam.learningapp.adapters

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.shivam.learningapp.R
import com.shivam.learningapp.adviews.NativeTemplateStyle
import com.shivam.learningapp.adviews.TemplateView

class LandMarksAdapter(private var imageList:ArrayList<Int>, private var placeList: ArrayList<String>, private var list: ArrayList<String>, private val context: Context)
    : RecyclerView.Adapter<RecyclerView.ViewHolder?>(){

    companion object {
        private const val IS_AD = 0
        private const val NOT_Ad = 1
    }

    override fun getItemViewType(position: Int): Int {

//        if ((position + 1) % 7 == 0 && (position + 1) != 1) {
//            return IS_AD
//        }
//        return NOT_Ad
        return NOT_Ad
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType==NOT_Ad){
            val view: View = LayoutInflater.from(context).inflate(R.layout.learn_monument_item, parent, false)
            ListItem1ViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(context).inflate(R.layout.my_ad_cardview, parent, false)
            AdViewHolder(view)
        }

    }

    override fun getItemCount(): Int{
//        Log.d("MyTag","Get Item Count : "+list.size.toString())
        return list.size
    }

    fun clearItems() {
        list.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            if (getItemViewType(position) == IS_AD) {
                val adv = holder as AdViewHolder
                loadAds(adv)
            } else {
                holder as ListItem1ViewHolder
                holder.textView.text = list[position]
                holder.textView2.text = placeList[position]
                holder.imageView.setBackgroundResource(imageList[position])
            }

    }

    inner class ListItem1ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageView: ImageView = itemView.findViewById(R.id.roundedImageView)
        var textView: TextView = itemView.findViewById(R.id.name)
        var textView2: TextView = itemView.findViewById(R.id.place)
    }

    inner class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val template: TemplateView = itemView.findViewById(R.id.id_ad_template_view)
        val rootLayout: RelativeLayout =itemView.findViewById(R.id.rootLayout)

        /*fun setNativeAd(nativeAd: NativeAd?) {
            if (nativeAd != null) {
                template.setNativeAd(nativeAd)
            }
        }*/

        init {
            val styles = NativeTemplateStyle.Builder().build()
            template.setStyles(styles)
        }
    }

    fun isConnectedToInternet(ctx: Context): Boolean {
        val connectivityManager = ctx
            .getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val ni = connectivityManager.activeNetworkInfo
        return ni != null && ni.isAvailable && ni.isConnected
    }

    private fun loadAds(adv : AdViewHolder){

//    sample    ca-app-pub-8438387212221904/8218418440

/*
        val adLoader: AdLoader = AdLoader.Builder(context,"ca-app-pub-8438387212221904/5091858939")
            .forNativeAd { ad : NativeAd ->
                // Show the ad.
                if (adv.itemView.rootView.visibility==View.GONE){
                    adv.itemView.rootView.visibility=View.VISIBLE
                }

                adv.template.setNativeAd(ad)

            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    // Handle the failure by logging, altering the UI, and so on.
                    if (adv.itemView.rootView.visibility==View.VISIBLE){
                        adv.itemView.rootView.visibility=View.GONE
                    }
                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                    // Methods in the NativeAdOptions.Builder class can be
                    // used here to specify individual options settings.
                    .build())
            .build()

        if (adLoader.isLoading){
            if (adv.itemView.rootView.visibility==View.VISIBLE){
                adv.itemView.rootView.visibility=View.GONE
            }
        }

        val adReq= AdRequest.Builder().build()
        adLoader.loadAds(adReq,2)
*/

    }


}
