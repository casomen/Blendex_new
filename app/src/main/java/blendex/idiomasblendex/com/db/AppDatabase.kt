package blendex.idiomasblendex.com.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import blendex.idiomasblendex.com.db.daos.ProgramDao
import blendex.idiomasblendex.com.db.daos.studentDao
import blendex.idiomasblendex.com.db.objects.Programa_db
import blendex.idiomasblendex.com.db.objects.Student_db


@Database(entities = [Student_db::class,Programa_db::class], version = 4)
abstract class AppDatabase : RoomDatabase() {

    abstract fun studentDao(): studentDao
    abstract fun programDao(): ProgramDao

    companion object {
        private const val DATABASE_NAME = "blendex.db"
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()
            }
            return INSTANCE
        }
    }

    fun destroyInstance() {
        INSTANCE = null
    }
}