package blendex.idiomasblendex.com

import android.app.Activity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast

fun Activity.toast(mensaje:String){
    Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show()
}

fun LinearSnapHelper.findPosition(layoutManager: RecyclerView.LayoutManager ): Int? {
        val newPosition = findSnapView(layoutManager)?.let { layoutManager.getPosition(it) }
        return newPosition
}