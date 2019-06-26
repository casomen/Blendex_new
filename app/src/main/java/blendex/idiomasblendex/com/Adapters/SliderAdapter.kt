package blendex.idiomasblendex.com.Adapters

import android.content.ComponentName
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsServiceConnection
import androidx.browser.customtabs.CustomTabsSession
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import blendex.idiomasblendex.com.Objects.GlideApp
import blendex.idiomasblendex.com.Objects.Slider
import blendex.idiomasblendex.com.R
import kotlinx.android.synthetic.main.adapter_pager.view.*
import org.jetbrains.anko.toast



class SliderAdapter(private val list:List<Slider>, val context:Context): RecyclerView.Adapter<SliderAdapter.ListViewHolder>(){

    val CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome"


    private var mCustomTabsServiceConnection: CustomTabsServiceConnection? = null
    private var mClient: CustomTabsClient? = null
    private var mCustomTabsSession: CustomTabsSession? = null

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bindView(list[position])

        mCustomTabsServiceConnection = object : CustomTabsServiceConnection() {
            override fun onCustomTabsServiceConnected(componentName: ComponentName, customTabsClient: CustomTabsClient) {
                //Pre-warming
                mClient = customTabsClient
                mClient?.warmup(0L)
                mCustomTabsSession = mClient?.newSession(null)
            }

            override fun onServiceDisconnected(name: ComponentName) {
                mClient = null
            }
        }

        CustomTabsClient.bindCustomTabsService(context, CUSTOM_TAB_PACKAGE_NAME, mCustomTabsServiceConnection)

        holder.view.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                loadCustomTabForSite(list[position].urlWeb, Color.parseColor(list[position].color))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ListViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(blendex.idiomasblendex.com.R.layout.adapter_pager, parent, false)
        return ListViewHolder(item)
    }

    class ListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {





        fun bindView(slider: Slider){
           /* val multi = MultiTransformation<Bitmap>(
                RoundedCornersTransformation(30, 0, RoundedCornersTransformation.CornerType.ALL)
            ) .apply(bitmapTransform(multi)) //border de image*/
            with(slider){
                GlideApp.with(view.context)
                    .load(urlImage)
                    .into(view.imageView)
                with(slider){
                    view.nameTextView.text = nameImage
                }
            }
        }


    }


    fun loadCustomTabForSite(url: String, color: Int = Color.BLUE) {
        val customTabsIntent = CustomTabsIntent.Builder(mCustomTabsSession)
            .setToolbarColor(color)
            .setShowTitle(true)
            .build()

        customTabsIntent.launchUrl(context, Uri.parse(url))


    }

}