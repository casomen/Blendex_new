package blendex.idiomasblendex.com

import android.Manifest
import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.customtabs.CustomTabsClient
import android.support.customtabs.CustomTabsServiceConnection
import android.support.customtabs.CustomTabsSession
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import blendex.idiomasblendex.com.Adapters.GamesAdapter
import blendex.idiomasblendex.com.Adapters.SliderAdapter
import blendex.idiomasblendex.com.Adapters.miExperienciaAdapter
import blendex.idiomasblendex.com.Objects.Game
import kotlinx.android.synthetic.main.content_main.*
import blendex.idiomasblendex.com.Objects.Slider
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity(){


    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rcViewHome.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rcViewExperiencia.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rcViewPractic.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        val list =
        listOf(
                Slider("Italiano","https://static.idiomasblendex.com/HOME/aprender+italiano.jpg","https://idiomasblendex.com/italiano/","#f6b7fe"),
                Slider("Frances","https://static.idiomasblendex.com/HOME/clases+para+aprender+frances+en+medellin.jpg","https://idiomasblendex.com/frances/","#f7d20a")
                )

        val listExp =
            listOf(
                Slider("Italiano","https://idiomasblendex.com/wp-content/uploads/2019/05/Mi-experiencia-Blendex23-1024x680.jpg","",""),
                Slider("Frances","https://idiomasblendex.com/wp-content/uploads/2019/05/Mi-experiencia-Blendex1-1024x643.jpg","",""),
                Slider("1","https://idiomasblendex.com/wp-content/uploads/2019/05/Mi-experiencia-Blendex4-683x1024.jpg","","")

            )

        val listGame =
            listOf(
                Game("Italiano","https://www.phoneworld.com.pk/wp-content/uploads/2018/12/maxresdefault-3.jpg")

            )

        val adapter = SliderAdapter(list,applicationContext)
        rcViewHome.adapter=adapter
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rcViewHome)

        val adapterExperiencia = miExperienciaAdapter(listExp)
        adapterExperiencia.setItemClickListener { view, position ->
            val intent= Intent(this,FullscreenActivity::class.java)
            val options= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptionsCompat.makeSceneTransitionAnimation(this,view,view.transitionName).toBundle()
            } else {
                ActivityOptionsCompat.makeSceneTransitionAnimation(this,view,"image").toBundle()
            }

            intent.putExtra("res_name",listExp[position].urlImage)
            intent.putExtra("p",position.toString())
            val listImage: ArrayList<String> = ArrayList()
            for(slider in listExp){
                listImage.add(slider.urlImage)
            }
            intent.putStringArrayListExtra("Lista", listImage)
            startActivity(intent,options)
        }
        rcViewExperiencia.adapter = adapterExperiencia

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


        val adapterGame = GamesAdapter(listGame)
        rcViewPractic.adapter=adapterGame

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = ""
            actionBar.elevation = 4.0F
            actionBar.setDisplayShowHomeEnabled(true)
            actionBar.setDisplayUseLogoEnabled(true)
        }


    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
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


    fun openFacebook(v:View) {
        if (packageManager.isAppInstalled("com.facebook.katana")){
            val uri = "fb://page/166056713566462"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        }else{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/idiomasblendex")))

        }
    }

    fun openInstagram(v:View) {
        if (packageManager.isAppInstalled("com.instagram.android")){
            val uri = Uri.parse("http://instagram.com/_u/idiomasblendex")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }else{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/idiomasblendex/")))
        }
    }

    fun PhoneCall(v:View){
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.setData(Uri.parse("tel:0345405000"))

        //SOLICITAR PERMISO....
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        startActivity(callIntent)
    }

    fun Register(v:View){
        val i = Intent(this,RegisterActivity::class.java)
        startActivity(i)
    }

    fun PackageManager.isAppInstalled(packageName: String): Boolean =
        getInstalledApplications(PackageManager.GET_META_DATA)
            .firstOrNull { it.packageName == packageName } != null
}
