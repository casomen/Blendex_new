package blendex.idiomasblendex.com.Objects

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Student")
data class Student(
    @SerializedName("0")
                   val ListProgramas : List<Item>,
    @ColumnInfo(name = "Apellidos") @SerializedName("Apellidos") val apellidos: String = "",
    @ColumnInfo(name = "Fecha_Nacimiento") @SerializedName("Fecha_Nacimiento") val fechaNacimiento: String = "",
@PrimaryKey @ColumnInfo(name = "Identificacion") @SerializedName("Identificacion") val identificacion: String = "",
    @ColumnInfo(name = "TelefonoAlterno")  @SerializedName("TelefonoAlterno") val telefonoAlterno: String = "",
    @ColumnInfo(name = "Nombres") @SerializedName("Nombres") val nombres: String = "",
    @ColumnInfo(name = "TelefonoPpal")  @SerializedName("TelefonoPpal") val telefonoPpal: String = "",
    @ColumnInfo(name = "Tipo_Documento") @SerializedName("Tipo_Documento") val tipoDocumento: String = "")

