package blendex.idiomasblendex.com.epoxy.models

import android.widget.TextView
import blendex.idiomasblendex.com.R
import blendex.idiomasblendex.com.epoxy.models.helpers.KotlinModel

// This does not require annotations or annotation processing.
// The data class is required to generated equals/hashcode which Epoxy needs for diffing.
// Views are easily declared via property delegates
data class ItemDataClass(
    val title: String
) : KotlinModel(R.layout.data_class_item) {

    val titleView by bind<TextView>(R.id.title)

    override fun bind() {
        titleView.text = title
    }
}
