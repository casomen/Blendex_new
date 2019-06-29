package blendex.idiomasblendex.com.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import blendex.idiomasblendex.com.Objects.GlideApp
import blendex.idiomasblendex.com.Objects.Slider
import kotlinx.android.synthetic.main.item_image_snap.view.*
import org.jetbrains.anko.toast

class miExperienciaAdapter(private val list:List<Slider>): RecyclerView.Adapter<miExperienciaAdapter.ListViewHolder>(){
    private var itemClickListener: ((view: View, position: Int) -> Unit)? = null

    class ListViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    {
        fun bindView(slider: Slider)
        {
            with(slider)
            {
                GlideApp.with(view.context)
                    .load(urlImage)
                    .into(view.imageSnap)

                view.setOnClickListener {
                    view.context.toast("Click ${urlImage}")
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ListViewHolder
    {
        val item = LayoutInflater.from(parent.context).inflate(blendex.idiomasblendex.com.R.layout.item_image_snap, parent, false)
        return ListViewHolder(item)
    }

    override fun getItemCount(): Int
    {
        return list.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int)
    {
        holder.bindView(list[position])
        holder.view.setOnClickListener {
            itemClickListener?.invoke(holder.itemView.imageSnap, holder.layoutPosition)
        }
    }

    fun setItemClickListener(listener: (view: View, position: Int) -> Unit) {
        itemClickListener = listener
    }
}
