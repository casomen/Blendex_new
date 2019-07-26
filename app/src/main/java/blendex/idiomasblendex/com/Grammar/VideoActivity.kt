@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package blendex.idiomasblendex.com.Grammar

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View.GONE
import android.view.View.VISIBLE
import blendex.idiomasblendex.com.Exe1Fragment
import blendex.idiomasblendex.com.R
import blendex.idiomasblendex.com.ReproductorFragment
import kotlinx.android.synthetic.main.activity_exe.*
import kotlinx.android.synthetic.main.fragment_reproductor.*
import org.jetbrains.anko.toast

class VideoActivity : AppCompatActivity(), ReproductorFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var useController: Boolean = true
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

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        //return super.onTouchEvent(event)
        if (useController && ev!!.actionMasked == MotionEvent.ACTION_DOWN) {
            if (controls.isVisible) {
                controls.hide()

            } else {
                controls.show()
            }
            return true
        }
        return false
    }
}
