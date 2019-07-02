package blendex.idiomasblendex.com.modulos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import blendex.idiomasblendex.com.R
import kotlinx.android.synthetic.main.activity_games.*

class GamesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games)
        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar!!.title = "Regresar"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
    }
}
