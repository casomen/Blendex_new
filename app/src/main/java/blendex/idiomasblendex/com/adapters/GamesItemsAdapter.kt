package blendex.idiomasblendex.com.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import blendex.idiomasblendex.com.Objects.Game
import blendex.idiomasblendex.com.R
import blendex.idiomasblendex.com.games.Exe1Activity
import blendex.idiomasblendex.com.games.ExeActivity
import blendex.idiomasblendex.com.modulos.GrammarActivity
import kotlinx.android.synthetic.main.item_game.view.*
import kotlinx.android.synthetic.main.item_image_snap.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class GamesItemsAdapter(private val list: List<Game>, val context: Context): RecyclerView.Adapter<GamesItemsAdapter.ListViewHolder>(){

    class ListViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bindView(g: Game){
            with(g)
            {
                blendex.idiomasblendex.com.Objects.GlideApp.with(view.context)
                    .load(urlImage)
                    .into(view.image_game)

                view.title.text = g.nameImage
                view.desc.text = g.description

                view.setOnClickListener {

                    when {
                        cod_Game.contains("01") -> view.context.startActivity<ExeActivity>("n" to "Exe1Fragment","name" to g.nameImage)
                        cod_Game.contains("02") -> view.context.startActivity<ExeActivity>("n" to "otra")
                        else -> view.context.toast("Click $nameImage")

                    }
                    }

            }
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