package blendex.idiomasblendex.com

import android.content.Context
import android.graphics.Color
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import blendex.idiomasblendex.com.db.AppDatabase
import blendex.idiomasblendex.com.db.objects.Programa_db
import blendex.idiomasblendex.com.epoxy.models.CarouselItemCustomViewModel_
import blendex.idiomasblendex.com.epoxy.models.ItemDataClass
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.epoxy.EpoxyVisibilityTracker
import blendex.idiomasblendex.com.epoxy.models.itemCustomView
import blendex.idiomasblendex.com.epoxy.views.carouselNoSnap
import com.airbnb.epoxy.kotlinsample.models.itemEpoxyHolder
import com.jakewharton.rxbinding2.widget.RxTextView.color
import kotlinx.android.synthetic.main.activity_logged.*
import org.jetbrains.anko.AlertBuilder
import org.jetbrains.anko.AlertBuilderFactory
import org.jetbrains.anko.selector

class LoggedActivity : AppCompatActivity() {

    private var db: AppDatabase? = null
    var ListPrograms = listOf<Programa_db>()
    private lateinit var recyclerView: EpoxyRecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged)
        recyclerView = findViewById(R.id.recycler_view)

        val epoxyVisibilityTracker = EpoxyVisibilityTracker()
        epoxyVisibilityTracker.attach(recyclerView)

        recyclerView.withModels {

            for (i in 0 until 100) {

                itemCustomView {
                    id("")
                    color(Color.RED)
                    Bcolor(Color.CYAN)
                    title("this is a green custom view item")
                    listener { _ ->
                        Toast.makeText(this@LoggedActivity, "clicked", Toast.LENGTH_LONG).show()
                    }
                }

                itemEpoxyHolder{
                    id("view itemEpoxyHolder $i")
                    title("this is a View Holder item")
                    listener {
                        Toast.makeText(this@LoggedActivity, "clicked", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                carouselNoSnap{
                    id("carousel $i")
                    models(mutableListOf<CarouselItemCustomViewModel_>().apply {
                        val lastPage = 10
                        for (j in 0 until lastPage) {
                            add(
                                CarouselItemCustomViewModel_()
                                    .id("carousel $i-$j")
                                    .title("Page $j / $lastPage")
                            )
                        }
                    })
                }

                ItemDataClass("tItemDataClass")
                    .id("data class $i")
                    .addTo(this)

            }

        }


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
