package blendex.idiomasblendex.com.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import blendex.idiomasblendex.com.Objects.GlideApp
import blendex.idiomasblendex.com.Objects.Slider
import kotlinx.android.synthetic.main.adapter_pager.view.*
import org.jetbrains.anko.toast



class SliderAdapter(private val list:List<Slider>): RecyclerView.Adapter<SliderAdapter.ListViewHolder>(){
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bindView(list[position])
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