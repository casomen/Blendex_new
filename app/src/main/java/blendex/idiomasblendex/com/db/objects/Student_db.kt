package blendex.idiomasblendex.com.db.objects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Students")
data class Student_db(
    @ColumnInfo(name = "Apellidos") @SerializedName("Apellidos") val apellidos: String = "",
    @ColumnInfo(name = "Fecha_Nacimiento") @SerializedName("Fecha_Nacimiento") val fechaNacimiento: String = "",
    @PrimaryKey @ColumnInfo(name = "Identificacion") @SerializedName("Identificacion") val identificacion: String = "",
    @ColumnInfo(name = "TelefonoAlterno")  @SerializedName("TelefonoAlterno") val telefonoAlterno: String = "",
    @ColumnInfo(name = "Nombres") @SerializedName("Nombres") val nombres: String = "",
    @ColumnInfo(name = "TelefonoPpal")  @SerializedName("TelefonoPpal") val telefonoPpal: String = "",
    @ColumnInfo(name = "Tipo_Documento") @SerializedName("Tipo_Documento") val tipoDocumento: String = "")