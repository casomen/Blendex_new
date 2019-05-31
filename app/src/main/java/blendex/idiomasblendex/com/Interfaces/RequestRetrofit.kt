package blendex.idiomasblendex.com.Interfaces

import blendex.idiomasblendex.com.Objects.Student
import blendex.idiomasblendex.com.Objects.Word
import io.reactivex.Observable
import retrofit2.http.GET

interface RequestRetrofit {

    @GET("todas.php")
    fun getTodas() : Observable<List<Word>>

    @GET("Student/queryStudent.php")
    fun getUser() : Observable<Student>

}