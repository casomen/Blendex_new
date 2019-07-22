package blendex.idiomasblendex.com

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_exe1.*
import kotlinx.android.synthetic.main.fragment_exe1.progress_countdown
import kotlinx.android.synthetic.main.fragment_exe1.textView_countdown
import kotlinx.android.synthetic.main.fragment_exe1__r.*
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.textColor


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Exe1_RFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Exe1_RFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class Exe1_RFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private val TIMER_STATE_ID_B = "blendex.idiomasblendex.com.time"
    private val SECONDS_REMAINING_ID_B = "blendex.idiomasblendex.com.seconds_remaining"
    var contador = 0
    enum class TimerState{
        Stopped, Paused, Running
    }

    private lateinit var timer: CountDownTimer
    private var timerLengthSeconds: Long = 21L
    private var timerState = TimerState.Stopped

    private var secondsRemaining: Long = 21L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exe1__r, container, false)
        Handler().postDelayed(
            {
                startTimer()
                timerState =  TimerState.Running
            },
            200
        )

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
         * @return A new instance of fragment Exe1_RFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Exe1_RFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }


    @SuppressLint("ShowToast")
    override fun onResume() {
        super.onResume()
        initTimer()

        exe1_false.setOnClickListener {
            restartQuestion()
        }
        exe1_true.setOnClickListener {
            restartQuestion()
        }
    }

    override fun onPause() {
        super.onPause()

        if (timerState == TimerState.Running){
            timer.cancel()
        }

    }

    private fun initTimer(){
        timerState = getTimerState(activity!!.applicationContext)
        if (timerState == TimerState.Stopped)
            progress_countdown.max = timerLengthSeconds.toInt()

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
            getSecondsRemaining(activity!!.applicationContext)
        else
            timerLengthSeconds

        progress_countdown.progressDrawable.setColorFilter(
            Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN)
        textView_countdown.textColor = Color.BLACK
        updateCountdownUI()
    }

    private fun getTimerState(context: Context): TimerState {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val ordinal = preferences.getInt(TIMER_STATE_ID_B, 0)
        return TimerState.values()[ordinal]
    }


    private fun getSecondsRemaining(context: Context): Long{
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getLong(SECONDS_REMAINING_ID_B, 0)
    }

    private fun setSecondsRemaining(seconds: Long, context: Context){
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.putLong(SECONDS_REMAINING_ID_B, seconds)
        editor.apply()
    }



    private fun updateCountdownUI(){
        val minutesUntilFinished = secondsRemaining / 60
        val secondsInMinuteUntilFinished = secondsRemaining - minutesUntilFinished * 60
        val secondsStr = secondsInMinuteUntilFinished.toString()
        textView_countdown.text = if (secondsStr.length == 2) secondsStr else "0$secondsStr"
        progress_countdown.progress = (secondsRemaining).toInt()
        //Log.d("TIMEPO","${(timerLengthSeconds - secondsRemaining).toInt()}")
        if((timerLengthSeconds - secondsRemaining).toInt() == 10){
            progress_countdown.progressDrawable.setColorFilter(
                Color.YELLOW, android.graphics.PorterDuff.Mode.SRC_IN)
            textView_countdown.textColor = Color.BLACK
        }

        if((timerLengthSeconds - secondsRemaining).toInt() == 1){

            progress_countdown.progressDrawable.setColorFilter(
                Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN)
            textView_countdown.textColor = Color.BLACK
        }

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

        setSecondsRemaining(timerLengthSeconds, activity!!.applicationContext)
        secondsRemaining = timerLengthSeconds
        with(progress_countdown) {
            progressDrawable.setColorFilter(
                Color.RED, android.graphics.PorterDuff.Mode.SRC_IN)
        }
        textView_countdown.textColor = Color.RED
        //activity!!.supportFragmentManager.beginTransaction().replace(R.id.ContainerFragment,Exe1_RFragment()).commit()
        restartQuestion(contador++)
    }

    private fun restartQuestion(indexQ: Int = 0){

        question1.text = "Hola $indexQ"

        if (timerState == TimerState.Running){
            timer.cancel()
            onTimerFinished()
            }

        if (timerState == TimerState.Stopped){
            startTimer()
        }


    }

}
