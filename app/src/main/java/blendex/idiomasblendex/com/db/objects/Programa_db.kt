package blendex.idiomasblendex.com.db.objects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Programs")
data class Programa_db(

    @ColumnInfo(name = "FechaVigenciaFinal") val fechaVigenciaFinal: String,
    @PrimaryKey @ColumnInfo(name = "contrato")   val contrato: String = "",
    @ColumnInfo(name = "programas")  val programas: String = "",
    @ColumnInfo(name = "Horario")  val horario: String = "",
    @ColumnInfo(name = "FechaVigenciaInicial")  val fechaVigenciaInicial: String,
    @ColumnInfo(name = "Estado")     val estado: String = "")
