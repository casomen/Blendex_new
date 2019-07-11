package blendex.idiomasblendex.com

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import blendex.idiomasblendex.com.db.AppDatabase
import blendex.idiomasblendex.com.db.objects.Programa_db
import blendex.idiomasblendex.com.db.objects.Student_db
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop
import org.jetbrains.anko.startActivity
import java.io.Serializable

class SplashActivity : AppCompatActivity() {
    private var db: AppDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        db = AppDatabase.getInstance(this)!!
        //scheduleSplashScreen()
        GetDataFromDb(this).execute()

    }



    fun scheduleSplashScreen() {
        val splashScreenDuration = 500L
        Handler().postDelayed(
            {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            },
            splashScreenDuration
        )
    }


    private class GetDataFromDb(var context: SplashActivity) : AsyncTask<Void, Void, List<Student_db>>() {
        override fun doInBackground(vararg params: Void?): List<Student_db> {
            return context.db!!.studentDao().getAll()
        }
        override fun onPostExecute(chapterList: List<Student_db>?) {
            if (chapterList!!.isNotEmpty()) {
                Log.w("caso","este esta logeado${chapterList[0].nombres}-${chapterList[0].apellidos}")
                context.scheduleLogged(chapterList[0].nombres,chapterList[0].apellidos)
            }else{
                context.scheduleSplashScreen()
            }

        }
    }


    fun scheduleLogged(nom: String, ap: String) {
        val splashScreenDuration =200L
        Handler().postDelayed(
            {
                // After the splash screen duration, route to the right activities
                //val intent = Intent(this, LoggedActivity::class.java)

                //startActivity(intent)
                //finish()
                //startActivity<LoggedActivity>("n" to nom, "a" to ap)
                startActivity<LoggedActivity>("n" to nom, "a" to ap)


            },
            splashScreenDuration
        )
    }


}
