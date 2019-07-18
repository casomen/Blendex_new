package blendex.idiomasblendex.com.games

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import blendex.idiomasblendex.com.Exe1Fragment
import blendex.idiomasblendex.com.Exe1_RFragment
import blendex.idiomasblendex.com.R
import kotlinx.android.synthetic.main.activity_exe.*
import kotlinx.android.synthetic.main.activity_logged.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ExeActivity : AppCompatActivity(), Exe1Fragment.OnFragmentInteractionListener,Exe1_RFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exe)

        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar!!.title = "Regresar"
        actionbar.setDisplayHomeAsUpEnabled(true)

        intent?.let {

            with(it) {
                val fragment = extras.getString("n")
                when {
                    fragment.contains("Exe1Fragment") -> supportFragmentManager.beginTransaction().replace(R.id.ContainerFragment,Exe1Fragment()).commit()
                    //cod_Game.contains("02") -> view.context.startActivity<ExeActivity>("n" to "Exe1Fragment")
                    else -> toast("Click")
                }
            }
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
