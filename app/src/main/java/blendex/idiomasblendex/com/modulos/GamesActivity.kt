package blendex.idiomasblendex.com.modulos

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.LinearSnapHelper
import blendex.idiomasblendex.com.Objects.Game
import blendex.idiomasblendex.com.Objects.GlideApp
import blendex.idiomasblendex.com.R
import blendex.idiomasblendex.com.adapters.GamesItemsAdapter
import kotlinx.android.synthetic.main.activity_exe1.*
import kotlinx.android.synthetic.main.activity_games.*
import kotlinx.android.synthetic.main.activity_games.toolbar
import kotlinx.android.synthetic.main.item_image_snap.view.*

class GamesActivity : AppCompatActivity() {

    @SuppressLint("WrongConstant", "CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games)
        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar!!.title = "Regresar"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)


        rcViewGames.layoutManager = LinearLayoutManager(
            this,
            VERTICAL,
            false
        )
        val list =
            listOf(
                Game("01","1. Making new friends","Read Jaqueline’s ID card and select TRUE or  FALSE","https://static.idiomasblendex.com/HOME/aprender+italiano.jpg"),
                        Game("02","2. Parts of Speech","Read Mario’s letter and classify the words in bold into the appropriate \n" +
                                "category.","https://static.idiomasblendex.com/HOME/aprender+italiano.jpg")

        )

        val adapter = GamesItemsAdapter(list,applicationContext)
        rcViewGames.adapter=adapter
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rcViewGames)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
