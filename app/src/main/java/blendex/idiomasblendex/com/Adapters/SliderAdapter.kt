package blendex.idiomasblendex.com.Adapters

import android.content.Context
import android.graphics.Bitmap
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import blendex.idiomasblendex.com.Objects.GlideApp
import blendex.idiomasblendex.com.Objects.GlideOptions.bitmapTransform
import blendex.idiomasblendex.com.Objects.Slider
import blendex.idiomasblendex.com.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import kotlinx.android.synthetic.main.adapter_pager.view.*
import org.jetbrains.anko.toast
import java.util.*
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import android.os.Build
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.bumptech.glide.request.target.SimpleTarget

import android.annotation.SuppressLint
import com.bumptech.glide.request.transition.Transition
import java.io.File


class SliderAdapter(private val list:List<Slider>): RecyclerView.Adapter<SliderAdapter.ListViewHolder>(){
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder?.bindView(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ListViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(blendex.idiomasblendex.com.R.layout.adapter_pager, parent, false)
        return ListViewHolder(item)
    }

    class ListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(slider: Slider){
           /* val multi = MultiTransformation<Bitmap>(
                RoundedCornersTransformation(30, 0, RoundedCornersTransformation.CornerType.ALL)
            ) .apply(bitmapTransform(multi)) //border de image*/
            with(slider){
                GlideApp.with(view.context)
                    .load(urlImage)
                    .into(view.imageView)
                with(slider){
                    view.nameTextView.text = nameImage
                view.setOnClickListener {
                    view.context.toast("Click ${nameImage}")

                }
                }
            }
        }

    }

}