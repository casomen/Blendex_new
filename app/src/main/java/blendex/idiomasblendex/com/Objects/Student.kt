package blendex.idiomasblendex.com.Objects

import com.google.gson.annotations.SerializedName

data class Student(@SerializedName("0")
                   val ListProgramas : List<Item>,
                   @SerializedName("Apellidos")
                   val apellidos: String = "",
                   @SerializedName("Fecha_Nacimiento")
                   val fechaNacimiento: String = "",
                   @SerializedName("Identificacion")
                   val identificacion: String = "",
                   @SerializedName("TelefonoAlterno")
                   val telefonoAlterno: String = "",
                   @SerializedName("Nombres")
                   val nombres: String = "",
                   @SerializedName("TelefonoPpal")
                   val telefonoPpal: String = "",
                   @SerializedName("Tipo_Documento")
                   val tipoDocumento: String = "")