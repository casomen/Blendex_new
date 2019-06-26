package blendex.idiomasblendex.com.db.daos


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import blendex.idiomasblendex.com.db.objects.Student_db

/**
 * Created by axier on 7/2/18.
 */

@Dao
interface studentDao {

    @Query("SELECT * FROM Students")
    fun getAll(): List<Student_db>

    @Insert
    fun insertAll(students: List<Student_db>)

    @Insert
    fun insert(student: Student_db)

    @Delete
    fun delete(student: Student_db)

}
