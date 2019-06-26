package blendex.idiomasblendex.com.Objects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.google.gson.annotations.SerializedName
@Entity(foreignKeys = arrayOf(
    ForeignKey(
    entity = Student::class,
    parentColumns = arrayOf("Identificacion"),
    childColumns = arrayOf("Identificacion"))
)
)
data class Programa(

    @ColumnInfo(name = "FechaVigenciaFinal") @SerializedName("FechaVigenciaFinal") val fechaVigenciaFinal: FechaVigenciaFinal,
    @ColumnInfo(name = "contrato")@SerializedName("contrato")
                    val contrato: String = "",
    @ColumnInfo(name = "programa")@SerializedName("programa")
                    val programa: String = "",
    @ColumnInfo(name = "Horario") @SerializedName("Horario")
                    val horario: String = "",
    @ColumnInfo(name = "FechaVigenciaInicial") @SerializedName("FechaVigenciaInicial")
                    val fechaVigenciaInicial: FechaVigenciaInicial,
    @ColumnInfo(name = "Estado") @SerializedName("Estado")
                    val estado: String = "",
    @ColumnInfo(name = "Identificacion") val userId: Long)
