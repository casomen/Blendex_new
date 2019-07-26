package blendex.idiomasblendex.com

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_video.*
import kotlinx.android.synthetic.main.custom_playback_control.*
import kotlinx.android.synthetic.main.fragment_reproductor.*
import org.jetbrains.anko.support.v4.selector
import org.jetbrains.anko.support.v4.toast


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "name"
private const val ARG_PARAM2 = "urlVideo"
private lateinit var player: SimpleExoPlayer

private val trackSelectionFactory = AdaptiveTrackSelection.Factory()
private var trackSelector: DefaultTrackSelector? = null
/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ReproductorFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ReproductorFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ReproductorFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var name: String? = null
    private var urlVideo: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(ARG_PARAM1)
            urlVideo = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reproductor, container, false)
        //toast("Hola $name, URL: $urlVideo")
        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ReproductorFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(name: String, urlVideo: String) =
            ReproductorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, name)
                    putString(ARG_PARAM2, urlVideo)
                }
            }
    }

   /* private fun initializePlayer() {
        trackSelector = DefaultTrackSelector(trackSelectionFactory)
        mediaDataSourceFactory = DefaultDataSourceFactory(activity!!.applicationContext, Util.getUserAgent(activity!!.applicationContext, "mediaPlayerSample"))

        val dataSpec = DataSpec(RawResourceDataSource.buildRawResourceUri(R.raw.himym))
        val rawDataSource = RawResourceDataSource(activity!!.applicationContext)
        rawDataSource.open(dataSpec)

        val mediaSource = ExtractorMediaSource.Factory(mediaDataSourceFactory)
            .createMediaSource(rawDataSource.uri)


        val subtitleFormat = Format.createTextSampleFormat(
            null, // An identifier for the track. May be null.
            MimeTypes.APPLICATION_SUBRIP, // The mime type. Must be set correctly.
            C.SELECTION_FLAG_DEFAULT, // Selection flags for the track.
            "en"
        )

        val subtitleSourceMediaSource = SingleSampleMediaSource.Factory(mediaDataSourceFactory)
            .createMediaSource(Uri.parse("https://firebasestorage.googleapis.com/v0/b/tyrapp-62319.appspot.com/o/himym.srt?alt=media&token=65509e43-c015-47a2-9dd3-7d47c5a250f6"), subtitleFormat, C.TIME_UNSET)


        val mergedSource = MergingMediaSource(mediaSource, subtitleSourceMediaSource)

        player = ExoPlayerFactory.newSimpleInstance(activity!!.applicationContext, trackSelector)

        with(player) {
            prepare(mergedSource, false, false)
            playWhenReady = true
        }

        playerView.setShutterBackgroundColor(Color.TRANSPARENT)
        playerView.player = player
        playerView.requestFocus()
    }*/

    private fun initializePlayer(){
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT_WATCH) {
            urlVideo = "http" + (urlVideo?.substring(5) ?: "no found")
        }

        trackSelector = DefaultTrackSelector(trackSelectionFactory)

        player = ExoPlayerFactory.newSimpleInstance(activity!!.applicationContext, trackSelector)
// Create a data source factory.
val dataSourceFactory = DefaultHttpDataSourceFactory(Util.getUserAgent(activity!!.applicationContext, "app-name"))

        val source = HlsMediaSource
            .Factory(dataSourceFactory)
            .createMediaSource(Uri.parse(urlVideo))

        with(player) {
            prepare(source, false, false)
            playWhenReady = true
            controls.player = this
            player.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
        }


        //playerView.setShutterBackgroundColor(Color.TRANSPARENT)
        playerView.player = player
        playerView.requestFocus()
        playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM

    }

    private fun releasePlayer() {
        player.release()
        trackSelector = null
    }

    override fun onStart() {
        super.onStart()

        if (Util.SDK_INT > 23) initializePlayer()
    }

    override fun onResume() {
        super.onResume()

        if (Util.SDK_INT <= 23) initializePlayer()

        exo_filter.setOnClickListener {
            val options = listOf("Subtitles", "Calidad baja", "Calidad Alta")
            selector("Options", options) { _, i ->
                //toast("So you're living in ${options[i]}, right?")
                when(i){
                    0 -> toast("Se activaran los ${options[i]} crear activarSubtitles()")
                    1 -> toast("Se activaran la ${options[i]} crear changeCalidadBaja()")
                    2 -> toast("Se activaran la ${options[i]} crear changeCalidadalta()")
                }
            }
        }
        exo_fullscreen_icon.setOnClickListener {
            toolbar.visibility = GONE
        }
    }



    override fun onPause() {
        super.onPause()

        if (Util.SDK_INT <= 23) releasePlayer()
    }

    override fun onStop() {
        super.onStop()

        if (Util.SDK_INT > 23) releasePlayer()
    }



}
