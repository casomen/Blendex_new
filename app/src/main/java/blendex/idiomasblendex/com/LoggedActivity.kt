package blendex.idiomasblendex.com

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridLayout.VERTICAL
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import blendex.idiomasblendex.com.Adapters.ModuloAdapter
import blendex.idiomasblendex.com.Adapters.SliderAdapter
import blendex.idiomasblendex.com.Objects.Modulo
import blendex.idiomasblendex.com.Objects.Slider
import blendex.idiomasblendex.com.db.AppDatabase
import blendex.idiomasblendex.com.db.objects.Programa_db
import kotlinx.android.synthetic.main.activity_logged.*
import kotlinx.android.synthetic.main.content_main.*

import org.jetbrains.anko.selector

class LoggedActivity : AppCompatActivity() {

    private var db: AppDatabase? = null
    var ListPrograms = listOf<Programa_db>()

    @SuppressLint("WrongConstant")
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


        val list =
            listOf(
                Slider("Italiano","https://static.idiomasblendex.com/HOME/aprender+italiano.jpg","https://idiomasblendex.com/italiano/","#f6b7fe"),
                Slider("Frances","https://static.idiomasblendex.com/HOME/clases+para+aprender+frances+en+medellin.jpg","https://idiomasblendex.com/frances/","#f7d20a")
            )

        val list2 =
            listOf(
                Modulo("Grammar","https://static.idiomasblendex.com/HOME/aprender+italiano.jpg"),
                Modulo("Vocabulary","https://static.idiomasblendex.com/HOME/clases+para+aprender+frances+en+medellin.jpg"),
                        Modulo("Games","https://static.idiomasblendex.com/HOME/aprender+italiano.jpg"),
        Modulo("Tutorias","https://static.idiomasblendex.com/HOME/clases+para+aprender+frances+en+medellin.jpg")
            )

        val adapter = SliderAdapter(list,applicationContext)
        rcViewMihome.adapter=adapter
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rcViewHome)

        rcViewMihome.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        rcViewModulo.layoutManager = GridLayoutManager(this,2,VERTICAL,false)
        val adapter2 = ModuloAdapter(list2,applicationContext)
        rcViewModulo.adapter=adapter2


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
