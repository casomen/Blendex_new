package blendex.idiomasblendex.com

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.Toast
import blendex.idiomasblendex.com.Interfaces.RequestRetrofit
import blendex.idiomasblendex.com.Objects.Student
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class RegisterActivity : AppCompatActivity() {
    private var mCompositeDisposable: CompositeDisposable? = null

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

        mCompositeDisposable = CompositeDisposable()

        register()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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
            editText.setText("$dayOfMonth/${monthOfYear + 1}/$y")
            //Toast.makeText(this, """$dayOfMonth - ${monthOfYear + 1} - $year""", Toast.LENGTH_LONG).show()
        }, year, month, day)
        dpd.show()
    }

    fun register(){
        val BASE_URL = "https://app.idiomasblendex.com/api/"
        val requestInterface = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RequestRetrofit::class.java)

        mCompositeDisposable?.add(requestInterface.getUser()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResponse, this::handleError))
    }

    private fun handleResponse(student: Student) {
                toast("s"+student.tipoDocumento)
                Log.w("caso",student.nombres)

        for (o in student.o){
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
}
