package blendex.idiomasblendex.com.modulos

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.LinearSnapHelper
import blendex.idiomasblendex.com.Objects.Game
import blendex.idiomasblendex.com.Objects.GrammarVideo
import blendex.idiomasblendex.com.R
import blendex.idiomasblendex.com.adapters.GamesItemsAdapter
import blendex.idiomasblendex.com.adapters.GrammarItemsAdapter
import kotlinx.android.synthetic.main.activity_games.*
import kotlinx.android.synthetic.main.activity_grammar.*
import kotlinx.android.synthetic.main.activity_grammar.toolbar

class GrammarActivity : AppCompatActivity() {

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grammar)
        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar!!.title = "Regresar"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)


        rcViewGrammar.layoutManager = LinearLayoutManager(
            this,
            VERTICAL,
            false
        )

        val list =
            listOf(
                GrammarVideo("Primer video","https://idiomasblendex.com/wp-content/uploads/2019/05/Mi-experiencia-Blendex1-1024x643.jpg","https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8","desc"),
                GrammarVideo("Segundo video","https://idiomasblendex.com/wp-content/uploads/2019/05/Mi-experiencia-Blendex23-1024x680.jpg","https://appblendex.s3.amazonaws.com/ana/mod.m3u8","desc 2")
            )

        val adapter = GrammarItemsAdapter(list,applicationContext)
        rcViewGrammar.adapter=adapter
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rcViewGrammar)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
