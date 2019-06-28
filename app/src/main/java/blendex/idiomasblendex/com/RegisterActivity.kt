package blendex.idiomasblendex.com

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.Toast
import blendex.idiomasblendex.com.Interfaces.RequestRetrofit
import blendex.idiomasblendex.com.Objects.Student
import blendex.idiomasblendex.com.db.objects.Student_db
import blendex.idiomasblendex.com.db.AppDatabase
import blendex.idiomasblendex.com.db.objects.Programa_db
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class RegisterActivity : AppCompatActivity() {
   // private var mCompositeDisposable: CompositeDisposable? = null
    private val BASE_URL = "https://app.idiomasblendex.com/api/"
    //private var mDbThread: DbThread? = null
   // private val mUiHandler = Handler()
    private var db: AppDatabase? = null

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Regresar"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
        //mCompositeDisposable = CompositeDisposable()
        db = AppDatabase.getInstance(this)!!
        btnRegister.setOnClickListener {
            //val format = SimpleDateFormat("dd/MM/yyy")
            register(editTextID.text.toString(),editTextDATE.text.toString())
        }



        doAsync {
            uiThread {
                alert("Hi, I'm Roy", "Have you tried turning it off and on again?") {
                    yesButton { toast("Oh…") }
                    noButton {}
                }.show()
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onStart() {
        super.onStart()
        GetDataFromDb(this).execute()
        GetPrograms(this).execute()

    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    fun clickDataPicker(view: View) {
        val c = Calendar.getInstance()
        val year = 2007
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this,R.style.ThemeOverlay_MaterialComponents_Dialog_Alert_custom,DatePickerDialog.OnDateSetListener { view, y, monthOfYear, dayOfMonth ->
            // Display Selected date in Toast
            editTextDATE.setText("${monthOfYear + 1}/$dayOfMonth/$y")
            //Toast.makeText(this, """$dayOfMonth - ${monthOfYear + 1} - $year""", Toast.LENGTH_LONG).show()
        }, year, month, day)
        dpd.show()
    }

    @Suppress("UNUSED_PARAMETER")
    fun register(Id:String, BirthDate:String){

        toast("hola $Id como $BirthDate")

        /*val requestInterface = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RequestRetrofit::class.java)*/

      /*  mCompositeDisposable?.add(requestInterface.getUser(Id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResponse, this::handleError))*/

        val requestInterface = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RequestRetrofit::class.java)
        val call = requestInterface.getUser(Id,BirthDate)
        call.enqueue(object : retrofit2.Callback<Student>{
            override fun onFailure(call: Call<Student>, t: Throwable) {

            }

            override fun onResponse(call: Call<Student>, response: Response<Student>) {
                if(response.isSuccessful){
                    val s = response.body()
                    s.let {
                        if (s != null) {
                            //añadir
                            val std = Student_db(s.apellidos,s.fechaNacimiento,s.identificacion,s.telefonoAlterno,s.nombres,s.telefonoPpal,s.tipoDocumento)
                            InsertTask(this@RegisterActivity,std).execute()
                            //save(std)
                            toast("nombre:${s.nombres} apellidos:${s.apellidos}-" +
                                    "${s.ListProgramas.get(0).programa.programa} - ${s.ListProgramas.get(0).programa.estado} ")

                            for (i in s.ListProgramas) {
                                Log.w("CASO","${i.programa.programa} - ${i.programa.estado} ")
                                Log.w("CASO","${i.programa.fechaVigenciaInicial} - ${i.programa.fechaVigenciaFinal} ")
                                val programU = Programa_db(i.programa.fechaVigenciaFinal.date,
                                    i.programa.contrato,
                                    i.programa.programa,
                                    i.programa.fechaVigenciaInicial.date,
                                    i.programa.estado)
                                InsertPrograms(this@RegisterActivity,programU).execute()
                            }

                        }

                    }

                }
            }

        })

    }

    private fun handleResponse(student: Student) {
                toast("s"+student.tipoDocumento)
                Log.w("caso",student.nombres)

        for (o in student.ListProgramas){
            Log.w("caso",o.programa.programa)
            Log.w("caso",o.programa.horario)
            Log.w("caso",o.programa.fechaVigenciaFinal.date)
        }
    }

    private fun handleError(error: Throwable) {
        Log.d("caso", error.message)
        Log.d("caso", error.localizedMessage)
        Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
    }

    private class InsertTask(var context:RegisterActivity, var student: Student_db) : AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            context.db!!.studentDao().insert(student)
            return true
        }
        override fun onPostExecute(bool: Boolean?) {
            if (bool!!) {
                Toast.makeText(context, "Added to Database", Toast.LENGTH_LONG).show()
            }
        }
    }

    private class InsertPrograms(var context:RegisterActivity, var programa: Programa_db) : AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            context.db!!.programDao().insert(programa)
            return true
        }
        override fun onPostExecute(bool: Boolean?) {
            if (bool!!) {
                Toast.makeText(context, "Added Program to Database", Toast.LENGTH_LONG).show()
            }
        }
    }



    private class GetDataFromDb(var context: RegisterActivity) : AsyncTask<Void, Void, List<Student_db>>() {
        override fun doInBackground(vararg params: Void?): List<Student_db> {
            return context.db!!.studentDao().getAll()
        }
        override fun onPostExecute(chapterList: List<Student_db>?) {
            if (chapterList!!.isNotEmpty()) {
                Log.w("caso",chapterList[0].nombres)
            }
        }
    }

    private class GetPrograms(var context: RegisterActivity) : AsyncTask<Void, Void, List<Programa_db>>() {
        override fun doInBackground(vararg params: Void?): List<Programa_db> {
            return context.db!!.programDao().getAll()
        }
        override fun onPostExecute(result: List<Programa_db>?) {
            if (result!!.isNotEmpty()) {
                Log.w("caso",result[0].contrato)
                for (word in result) {
                    Log.w("CASO","${word.programas} ")
                }
            }
        }
    }


}
