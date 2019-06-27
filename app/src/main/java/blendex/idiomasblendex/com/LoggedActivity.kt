package blendex.idiomasblendex.com

import android.graphics.Color
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import blendex.idiomasblendex.com.db.AppDatabase
import blendex.idiomasblendex.com.db.objects.Programa_db
import kotlinx.android.synthetic.main.activity_logged.*
import org.jetbrains.anko.AlertBuilder
import org.jetbrains.anko.AlertBuilderFactory
import org.jetbrains.anko.selector

class LoggedActivity : AppCompatActivity() {

    private var db: AppDatabase? = null
    var ListPrograms = listOf<Programa_db>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged)
        intent?.let {

            txName.text = "Bienvenido\n${it.extras.getString("n")
                                            .toLowerCase().capitalize().trim()}"
        }
        db = AppDatabase.getInstance(this)!!
        GetPrograms(this).execute()
        CardProgram.setOnClickListener {
            var p = mutableListOf("")
            for (programs in ListPrograms){
                p.add(programs.programas)
            }

            selector("", p) { dialogInterface, i ->
                currentProgram.text = p[i]
            }
        }
    }


    private class GetPrograms(var context: LoggedActivity) : AsyncTask<Void, Void, List<Programa_db>>() {
        override fun doInBackground(vararg params: Void?): List<Programa_db> {
            return context.db!!.programDao().getAll()
        }
        override fun onPostExecute(result: List<Programa_db>) {
            context.ListPrograms = result
        }
    }
}
