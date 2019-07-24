package blendex.idiomasblendex.com.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import blendex.idiomasblendex.com.Grammar.VideoActivity
import blendex.idiomasblendex.com.Objects.GlideApp
import blendex.idiomasblendex.com.Objects.GrammarVideo
import blendex.idiomasblendex.com.R
import blendex.idiomasblendex.com.games.ExeActivity
import kotlinx.android.synthetic.main.item_game.view.*
import kotlinx.android.synthetic.main.item_grammar_video.view.*
import org.jetbrains.anko.startActivity

class GrammarItemsAdapter (private val list: List<GrammarVideo>, val context:Context): RecyclerView.Adapter<GrammarItemsAdapter.ListViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ListViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_grammar_video, parent, false)
        return ListViewHolder(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bindView(list[position])
    }

    class ListViewHolder (val view: View): RecyclerView.ViewHolder(view){
        fun bindView(video: GrammarVideo){
            with(video){
                GlideApp.with(view.context)
                    .load(video.urlImg)
                    .into(view.item_image)

                view.item_text_view.text = nameVideo
                view.setOnClickListener {
                    view.context.startActivity<VideoActivity>("name" to video.nameVideo,"urlVideo" to video.urlVideo)
                }
            }
        }
    }

}