package blendex.idiomasblendex.com.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import blendex.idiomasblendex.com.Objects.Game
import blendex.idiomasblendex.com.R


class GamesItemsAdapter(private val list: List<Game>, val context: Context): RecyclerView.Adapter<GamesItemsAdapter.ListViewHolder>(){

    class ListViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bindView(g: Game){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ListViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return ListViewHolder(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bindView(list[position])
    }




}