package blendex.idiomasblendex.com.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import blendex.idiomasblendex.com.Objects.GlideApp
import blendex.idiomasblendex.com.Objects.Modulo
import blendex.idiomasblendex.com.R
import kotlinx.android.synthetic.main.item_image.view.*
import org.jetbrains.anko.toast

class ModuloAdapter(private val list: List<Modulo>, val context: Context): RecyclerView.Adapter<ModuloAdapter.ListViewHolder>(){

    class ListViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bindView(mod: Modulo){
            with(mod){
                GlideApp.with(view.context)
                    .load(urlImage)
                    .into(view.item_image)
                view.item_text_view.text = mod.nameImage
                view.setOnClickListener {
                    view.context.toast("Hola programa $nameImage")
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ListViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ListViewHolder(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bindView(list[position])
    }




}