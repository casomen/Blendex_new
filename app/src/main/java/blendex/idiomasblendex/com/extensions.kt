package blendex.idiomasblendex.com

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.widget.Toast

fun Activity.toast(mensaje:String){
    Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show()
}

fun LinearSnapHelper.findPosition(layoutManager: RecyclerView.LayoutManager ): Int? {
        val newPosition = findSnapView(layoutManager)?.let { layoutManager.getPosition(it) }
        return newPosition
}