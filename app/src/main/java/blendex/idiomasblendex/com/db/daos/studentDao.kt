package blendex.idiomasblendex.com.db.daos


import androidx.room.*
import blendex.idiomasblendex.com.db.objects.Student_db


@Dao
interface studentDao {

    @Query("SELECT * FROM Students")
    fun getAll(): List<Student_db>

    @Insert
    fun insertAll(students: List<Student_db>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(student: Student_db)

    @Delete
    fun delete(student: Student_db)

}
