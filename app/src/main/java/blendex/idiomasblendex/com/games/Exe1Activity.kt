package blendex.idiomasblendex.com.games

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import blendex.idiomasblendex.com.Objects.GlideApp
import blendex.idiomasblendex.com.R
import kotlinx.android.synthetic.main.activity_exe1.*

class Exe1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exe1)

        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar!!.title = "Regresar"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        GlideApp.with(applicationContext)
            .load("https://static.idiomasblendex.com/HOME/aprender+italiano.jpg")
            .circleCrop()
            .into(image_P)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
