@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package blendex.idiomasblendex.com.Grammar

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import blendex.idiomasblendex.com.Exe1Fragment
import blendex.idiomasblendex.com.R
import blendex.idiomasblendex.com.ReproductorFragment
import kotlinx.android.synthetic.main.activity_exe.*
import org.jetbrains.anko.toast

class VideoActivity : AppCompatActivity(), ReproductorFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar!!.title = "Regresar"
        actionbar.setDisplayHomeAsUpEnabled(true)

        intent?.let {
            with(it) {
                val name = extras.getString("name")
                val url = extras.getString("urlVideo")
                supportFragmentManager.beginTransaction().replace(R.id.ContainerFragment,
                    ReproductorFragment.newInstance(name?:"Falta nombre" ,url?:"Falta url")).commit()            }
        }


    }


    override fun onSupportNavigateUp(): Boolean {
        // onBackPressed()
        /*if (supportFragmentManager.backStackEntryCount > 0) {
           supportFragmentManager.popBackStack()
           true
       } else {*/
        super.onBackPressed()
        return  false
        // }

    }
}
