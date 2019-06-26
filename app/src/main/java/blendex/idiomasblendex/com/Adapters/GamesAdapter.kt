package blendex.idiomasblendex.com.Adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import blendex.idiomasblendex.com.Objects.Game
import blendex.idiomasblendex.com.Objects.GlideApp
import blendex.idiomasblendex.com.R
import kotlinx.android.synthetic.main.adapter_pager.view.*

class GamesAdapter (private val list: List<Game>): RecyclerView.Adapter<GamesAdapter.ListViewHolder>(){

    class ListViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bindView(game:Game){
            with(game){
                GlideApp.with(view.context)
                    .load(urlImage)
                    .into(view.imageView)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ListViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.adapter_pager_game, parent, false)
        return ListViewHolder(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bindView(list[position])
    }




}