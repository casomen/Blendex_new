package blendex.idiomasblendex.com

import android.content.Context
import android.graphics.Color
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
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

            txName.text = "${it.extras.getString("n")
                                            .toLowerCase().capitalize().trim()}"
        }

        db = AppDatabase.getInstance(this)!!
        GetPrograms(this).execute()

        LinearProgram.setOnClickListener {
            var p = mutableListOf("")
            for (programs in ListPrograms){
                p.add(programs.programas.replace(" - "," ").toLowerCase().capitalize())
            }
            selector("Selecciona el programa actual", p) { dialogInterface, i ->
                currentProgram.text = p[i]
            }
        }
    }


    private class GetPrograms(var context: LoggedActivity) : AsyncTask<Void, Void, List<Programa_db>>() {
        override fun doInBackground(vararg params: Void?): List<Programa_db> {
            return context.db!!.programDao().getAll()
        }
        override fun onPostExecute(result: List<Programa_db>) {
            context.currentProgram.text = result[0].programas.replace(" - "," ").toLowerCase().capitalize().trim()
            context.ListPrograms = result
        }
    }
}
