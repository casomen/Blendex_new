package blendex.idiomasblendex.com.games

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import blendex.idiomasblendex.com.*
import kotlinx.android.synthetic.main.activity_exe.*
import kotlinx.android.synthetic.main.activity_logged.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "UNREACHABLE_CODE")
class ExeActivity : AppCompatActivity(),
    Exe1Fragment.OnFragmentInteractionListener,
    Exe2Fragment.OnFragmentInteractionListener,
    Exe1_RFragment.OnFragmentInteractionListener,
    StatisticsFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: String) {
        Log.w("CASO", "CARLOS! $uri")
    }

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
                val name = extras.getString("name")
                when {
                    fragment.contains("Exe1Fragment") -> supportFragmentManager.beginTransaction().replace(R.id.ContainerFragment,Exe1Fragment.newInstance(name?:"Falta nombre" ,"Carlos")).commit()
                    fragment.contains("Exe2Fragment") -> supportFragmentManager.beginTransaction().replace(R.id.ContainerFragment,Exe2Fragment.newInstance(name?:"Falta nombre" ,"Carlos")).commit()
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
