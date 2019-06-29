package blendex.idiomasblendex.com.modulos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import blendex.idiomasblendex.com.R
import kotlinx.android.synthetic.main.activity_vocabulary.*

class VocabularyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vocabulary)
        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar!!.title = "Regresar"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
