package blendex.idiomasblendex.com

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import blendex.idiomasblendex.com.db.AppDatabase
import blendex.idiomasblendex.com.db.objects.Student_db

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
                // After the splash screen duration, route to the right activities
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
                context.scheduleSplashScreen()
            }else{
                context.scheduleSplashScreen()
            }

        }
    }

}
