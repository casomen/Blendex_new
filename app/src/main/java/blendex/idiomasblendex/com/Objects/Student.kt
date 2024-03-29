package blendex.idiomasblendex.com.Objects

import androidx.room.ColumnInfo
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Student(
    @Ignore @SerializedName("0") val ListProgramas : List<Item>,
    @ColumnInfo(name = "Apellidos") @SerializedName("Apellidos") val apellidos: String = "",
    @ColumnInfo(name = "Fecha_Nacimiento") @SerializedName("Fecha_Nacimiento") val fechaNacimiento: String = "",
    @PrimaryKey @ColumnInfo(name = "Identificacion") @SerializedName("Identificacion") val identificacion: String = "",
    @ColumnInfo(name = "TelefonoAlterno")  @SerializedName("TelefonoAlterno") val telefonoAlterno: String = "",
    @ColumnInfo(name = "Nombres") @SerializedName("Nombres") val nombres: String = "",
    @ColumnInfo(name = "TelefonoPpal")  @SerializedName("TelefonoPpal") val telefonoPpal: String = "",
    @ColumnInfo(name = "Tipo_Documento") @SerializedName("Tipo_Documento") val tipoDocumento: String = "")

