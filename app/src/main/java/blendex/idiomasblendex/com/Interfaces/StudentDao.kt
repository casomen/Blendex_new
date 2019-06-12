package blendex.idiomasblendex.com.Interfaces

import android.arch.persistence.room.Insert
import blendex.idiomasblendex.com.Objects.Programa
import blendex.idiomasblendex.com.Objects.Student

interface StudentDao {

    @Insert
    fun insertStudent(student: Student)

    @Insert
    fun insertPrograma(programa: Programa)
}