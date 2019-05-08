package blendex.idiomasblendex.com

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.View.GONE
import blendex.idiomasblendex.com.Adapters.SliderAdapter
import blendex.idiomasblendex.com.Objects.GlideApp
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.content_main.*
import blendex.idiomasblendex.com.Objects.Slider
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rcViewHome.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        val list =
        listOf(
                Slider("Italiano","https://static.idiomasblendex.com/HOME/aprender+italiano.jpg"),
                Slider("Frances","https://static.idiomasblendex.com/HOME/clases+para+aprender+frances+en+medellin.jpg")
                )
        val adapter = SliderAdapter(list)
        rcViewHome.adapter=adapter
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rcViewHome)
        GlideApp.with(this)
            .load("https://static.idiomasblendex.com/HOME/aprender+italiano.jpg")
            .into(imagenLarga)
        buy.setOnClickListener { toast("Click en buy") }
        save.visibility = GONE
        //val i = rcViewHome.layoutManager.getPosition()
       /* /** Email validation: */
        val userObservable = RxTextView.textChanges(edit_login_user)
            .map { text -> Patterns.emailPattern.matcher(text).matches() }

        userObservable.distinctUntilChanged()
            .map { a -> if (a) println("Verdadero") else println("Falso")  }
            .subscribe {}*/

        plus.setOnClickListener {
            var i = snapHelper.findPosition(rcViewHome.layoutManager as LinearLayoutManager)
            val total =   list.size
            if (i != null) {
                i += 1
                if(i==total){
                    newScrollTo(0)
                }else{
                    newScrollTo(i)
                }
            }
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = ""
            actionBar.elevation = 4.0F
            actionBar.setDisplayShowHomeEnabled(true)
            actionBar.setDisplayUseLogoEnabled(true)
        }


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_tools -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    private fun newScrollTo(pos: Int) {
        val vh = rcViewHome.findViewHolderForLayoutPosition(pos)
        if (vh != null) {
            rcViewHome.smoothScrollToPosition(pos)
        } else {
            rcViewHome.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    rcViewHome.removeOnScrollListener(this)
                    if (dx == 0) {
                        newScrollTo(pos)
                    }
                }
            })
            rcViewHome.scrollToPosition(pos)
        }
    }
}
