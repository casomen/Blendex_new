package blendex.idiomasblendex.com.Interfaces

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import blendex.idiomasblendex.com.Objects.Programa
import blendex.idiomasblendex.com.Objects.Student
import io.reactivex.Flowable

interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudent(vararg student: Student)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPrograma(vararg programa: Programa)

    @Query("SELECT * from Student")
    fun getAll(): List<Student>

}