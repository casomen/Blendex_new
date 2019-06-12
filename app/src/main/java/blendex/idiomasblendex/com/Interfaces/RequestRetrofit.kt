package blendex.idiomasblendex.com.Interfaces

import blendex.idiomasblendex.com.Objects.Student
import blendex.idiomasblendex.com.Objects.Word
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RequestRetrofit {


    @GET("todas.php")
    fun getTodas() : Observable<List<Word>>

    @GET("Student/queryStudent.php")
    fun getUser(@Query("id") id: String,@Query("fecha") fecha:String) : Call<Student>



}