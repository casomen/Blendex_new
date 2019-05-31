package blendex.idiomasblendex.com.Objects

import com.google.gson.annotations.SerializedName

data class

Word(@SerializedName("pagina")
                val pagina: String = "",
                @SerializedName("aud")
                val aud: String = "",
                @SerializedName("img")
                val img: String = "",
                @SerializedName("nativo")
                val nativo: String = "",
                @SerializedName("word_id")
                val wordId: String = "",
                @SerializedName("type")
                val type: String = "",
                @SerializedName("word")
                val word: String = "")