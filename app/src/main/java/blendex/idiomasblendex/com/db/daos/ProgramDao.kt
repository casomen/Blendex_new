package blendex.idiomasblendex.com.db.daos


import androidx.room.*
import blendex.idiomasblendex.com.db.objects.Programa_db


@Dao
interface ProgramDao {

    @Query("SELECT * FROM Programs")
    fun getAll(): List<Programa_db>

    @Insert
    fun insertAll(program: List<Programa_db>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(program: Programa_db)

    @Delete
    fun delete(program: Programa_db)

}
