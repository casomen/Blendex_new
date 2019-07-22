package blendex.idiomasblendex.com

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import blendex.idiomasblendex.com.Objects.GlideApp
import blendex.idiomasblendex.com.games.Exe1Activity
import com.tomergoldst.tooltips.ToolTip
import com.tomergoldst.tooltips.ToolTipsManager
import kotlinx.android.synthetic.main.activity_exe.*
import kotlinx.android.synthetic.main.activity_exe.view.*
import kotlinx.android.synthetic.main.activity_exe1.*
import kotlinx.android.synthetic.main.fragment_exe1.*
import kotlinx.android.synthetic.main.fragment_exe1.profession_exe1
import kotlinx.android.synthetic.main.fragment_exe1.progress_countdown
import kotlinx.android.synthetic.main.fragment_exe1.textView_countdown
import kotlinx.android.synthetic.main.fragment_exe1.view.*
import kotlinx.android.synthetic.main.fragment_exe1.view.profession_exe1
import kotlinx.android.synthetic.main.fragment_exe1.view.progress_countdown
import kotlinx.android.synthetic.main.fragment_exe1.view.textView_countdown
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.textColor
import java.util.*
import kotlinx.android.synthetic.main.activity_exe1.linearLayoutExe1 as linearLayoutExe11


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Exe1Fragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Exe1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class Exe1Fragment : Fragment(),ToolTipsManager.TipListener{
    private val mToolTipsManager = ToolTipsManager(this)
    private val TAG_ = "CASO"

    override fun onTipDismissed(view: View?, anchorViewId: Int, byUser: Boolean) {
        if (anchorViewId == R.id.profession_exe1) {
            mToolTipsManager.dismiss(anchorViewId)//OCULTA EL TIP
        }

    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    enum class TimerState{
        Stopped, Paused, Running
    }

    private lateinit var timer: CountDownTimer
    private var timerLengthSeconds: Long = 30L
    private var timerState = TimerState.Stopped

    private var secondsRemaining: Long = 30L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_exe1, container, false)
        GlideApp.with(view.context)
            .load("https://static.idiomasblendex.com/HOME/aprender+italiano.jpg")
            .circleCrop()
            .into(view.image_P)
        Handler().postDelayed(
            {
                startTimer()
                timerState =  TimerState.Running
            },
            200
        )
        val builder = ToolTip.Builder(
            this.context!!, view.profession_exe1, view.coordinator2!!,
            "Enfermera", ToolTip.POSITION_ABOVE).setBackgroundColor(Color.BLUE)


        view.profession_exe1.setOnClickListener {
                mToolTipsManager.show(builder.build())
        }

        view.setOnClickListener {
            mToolTipsManager.dismissAll()
        }

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

    @SuppressLint("ShowToast")
    override fun onResume() {
        super.onResume()
        initTimer()
    }

    override fun onPause() {
        super.onPause()

        if (timerState == TimerState.Running){
            timer.cancel()
        }

    }

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
         * @return A new instance of fragment Exe1Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Exe1Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun initTimer(){
        timerState = PrefUtil.getTimerState(activity!!.applicationContext)
        if (timerState == TimerState.Stopped)
            setNewTimerLength()
        else
            setPreviousTimerLength()


        Handler().postDelayed(
            {
                if (timerState == TimerState.Stopped){
                    toast("Comienza nuevamente")
                    activity!!.onBackPressed()
                }
            },
            250
        )

        secondsRemaining = if (timerState == TimerState.Running || timerState == TimerState.Paused)
            PrefUtil.getSecondsRemaining(activity!!.applicationContext)
        else
            timerLengthSeconds

        progress_countdown.progressDrawable.setColorFilter(
            Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN)
        textView_countdown.textColor = Color.BLACK
        updateCountdownUI()
    }


    private fun startTimer(){
        timerState = TimerState.Running

        timer = object : CountDownTimer(secondsRemaining * 1000, 1000) {
            override fun onFinish() = onTimerFinished()

            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining = millisUntilFinished / 1000
                updateCountdownUI()
            }
        }.start()
    }

    @SuppressLint("ShowToast")
    private fun onTimerFinished(){
        timerState = TimerState.Stopped

        progress_countdown.progress = timerLengthSeconds.toInt()

        PrefUtil.setSecondsRemaining(timerLengthSeconds, activity!!.applicationContext)
        secondsRemaining = timerLengthSeconds
        with(progress_countdown) {
            progressDrawable.setColorFilter(
                Color.RED, android.graphics.PorterDuff.Mode.SRC_IN)
            visibility = GONE


        }
        textView_countdown.textColor = Color.RED
        textView_countdown.text = "->"
        //updateCountdownUI()
        //toast("Termino")

        activity!!.supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(android.R.anim.slide_in_left,R.anim.abc_fade_out)
            .replace(R.id.ContainerFragment,Exe1_RFragment()).commit()
    }

    private fun setNewTimerLength(){
        Log.w("caso",timerLengthSeconds.toString())
        progress_countdown.max = timerLengthSeconds.toInt()
    }

    private fun setPreviousTimerLength(){
        timerLengthSeconds = PrefUtil.getPreviousTimerLengthSeconds(activity!!.applicationContext)
        progress_countdown.max = timerLengthSeconds.toInt()
    }

    private fun updateCountdownUI(){
        val minutesUntilFinished = secondsRemaining / 60
        val secondsInMinuteUntilFinished = secondsRemaining - minutesUntilFinished * 60
        val secondsStr = secondsInMinuteUntilFinished.toString()
        textView_countdown.text = if (secondsStr.length == 2) secondsStr else "0$secondsStr"
        progress_countdown.progress = (secondsRemaining).toInt()
        if((timerLengthSeconds - secondsRemaining).toInt() == 20){
            progress_countdown.progressDrawable.setColorFilter(
                Color.YELLOW, android.graphics.PorterDuff.Mode.SRC_IN)
            textView_countdown.textColor = Color.BLACK
        }
    }

    fun restartQuestion(){

    }

}
