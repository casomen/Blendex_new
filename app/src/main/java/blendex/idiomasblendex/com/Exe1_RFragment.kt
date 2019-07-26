package blendex.idiomasblendex.com

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.media.AudioManager
import android.media.SoundPool
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.preference.PreferenceManager
import android.util.Log
import android.util.SparseIntArray
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import blendex.idiomasblendex.com.Objects.exe1
import kotlinx.android.synthetic.main.fragment_exe1.*
import kotlinx.android.synthetic.main.fragment_exe1.progress_countdown
import kotlinx.android.synthetic.main.fragment_exe1.textView_countdown
import kotlinx.android.synthetic.main.fragment_exe1__r.*
import org.jetbrains.anko.audioManager
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.textColor
import kotlin.math.log


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "name"
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
class Exe1_RFragment : Fragment(), StatisticsFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // TODO: Rename and change types of parameters
    private var name: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private val TIMER_STATE_ID_B = "blendex.idiomasblendex.com.time"
    private val SECONDS_REMAINING_ID_B = "blendex.idiomasblendex.com.seconds_remaining"
    var correct = 0
    var incorrect = 0
    var contador = 0
    enum class TimerState{
        Stopped, Paused, Running
    }
    val h = Handler()
    private lateinit var timer: CountDownTimer
    private var timerLengthSeconds: Long = 21L
    private var timerState = TimerState.Stopped
    val preguntas_respuestas = mutableListOf<exe1>()
    private var secondsRemaining: Long = 21L

    private lateinit var soundPool: SoundPool
    private lateinit var soundsMap: SparseIntArray
    private val correctSound = 1
    private val errorSound = 2
    private var mAudioManager: AudioManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_exe1__r, container, false)
        Handler().postDelayed(
            {
                startTimer()
                timerState =  TimerState.Running
            },
            200
        )

        @Suppress("DEPRECATION")
        soundPool = SoundPool(4, AudioManager.STREAM_MUSIC, 0)
        soundsMap = SparseIntArray()
        soundsMap.put(correctSound, soundPool.load(activity!!.applicationContext, R.raw.correct_sound, 1))
        soundsMap.put(errorSound, soundPool.load(activity!!.applicationContext, R.raw.error_sound, 1))

        preguntas_respuestas.add(exe1("Jacqueline is twenty years old","0|1"))
        preguntas_respuestas.add(exe1("She works as a nurse","1|0"))
        preguntas_respuestas.add(exe1("Jacqueline likes dancing","1|0"))
        preguntas_respuestas.add(exe1("She lives in Belen neighborhood","0|1"))
        preguntas_respuestas.add(exe1("She likes to paint ","0|1"))
        preguntas_respuestas.add(exe1("Her last name is Thompson","1|0"))
        preguntas_respuestas.add(exe1("She likes to cook","1|0"))
        preguntas_respuestas.add(exe1("Jacqueline is thirty- three years old","1|0"))


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
            startQuestion(contador++,"0")
        }
        exe1_true.setOnClickListener {
            startQuestion(contador++,"1")

        }
    }

    override fun onPause() {
        super.onPause()

        if (timerState == TimerState.Running){
            timer.cancel()
        }
        h.removeCallbacksAndMessages(null)

    }



    private fun initTimer(){
        initQuestion()
        timerState = getTimerState(activity!!.applicationContext)
        if (timerState == TimerState.Stopped)
            progress_countdown.max = timerLengthSeconds.toInt()

        Handler().postDelayed(
            {
                if (timerState == TimerState.Stopped){
                    toast(resources.getText(R.string.try_again))
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
        //Log.w("CASO","secondsRemaining: $secondsRemaining")
        if (secondsRemaining.toInt() == 0){
        toast("TIEMPO AGOTADO PERDISTE")
        playSound(errorSound, 1.0f)
            //activity!!.onBackPressed()


            activity!!.supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left,R.anim.abc_fade_out)
                .replace(R.id.ContainerFragment,StatisticsFragment.newInstance(name?:"no hay nombre",correct.toString(),incorrect.toString())).commit()
        }

       // setSecondsRemaining(timerLengthSeconds, activity!!.applicationContext)
        secondsRemaining = timerLengthSeconds
        with(progress_countdown) {
            progressDrawable.setColorFilter(
                Color.RED, android.graphics.PorterDuff.Mode.SRC_IN)
        }
        textView_countdown.textColor = Color.RED
        //activity!!.supportFragmentManager.beginTransaction().replace(R.id.ContainerFragment,Exe1_RFragment()).commit()
        //restartQuestion(contador++)
        //Log.w("CASO","secondsRemaining: $secondsRemaining")
        //if (secondsRemaining.toInt() == 0){
          //  toast("PERDISTE")
           // playSound(errorSound, 1.0f)
        //}


    }

    private fun initQuestion(){
        preguntas_respuestas.shuffle()
        question1.text = preguntas_respuestas[0].q
        //Log.w("PREGUNTAS", "_------------------------------------")
        //Log.w("PREGUNTAS", "total ${preguntas_respuestas.size}")
        //Log.w("CASO", "${preguntas_respuestas[0].q} Respuesta: ${preguntas_respuestas[0].a}")

    }

    private fun startQuestion(indexQ: Int = 0, op:String = ""){

        //question1.text = "Hola $indexQ"

        if(op != "") {
            if (indexQ <= 4) {
                val r = preguntas_respuestas[0].a
                val res = r.substring(0, 1)
                val resError = r.substring(2, 3)

                //Log.w("CASO", "Respuesta: $res - desicion: $op - R: $r - pregunta ${preguntas_respuestas[0].q}")
                //Log.w("CASO", "RES: $r OP: $op")

                if (op.contentEquals(res)) {
                    playSound(correctSound, 1.0f)
                    toast(resources.getText(R.string.Well_done))
                    correct++
                }else{
                    playSound(errorSound, 1.0f)
                    toast(resources.getText(R.string.you_lose))
                    incorrect++
                }

                h.postDelayed(
                    {
                        preguntas_respuestas.removeAt(0)
                        initQuestion()
                    },
                    1000 // value in milliseconds
                )

                if(indexQ == 4){
                    //toast("Comienza nuevamente")
                    //activity!!.onBackPressed()
                    activity!!.supportFragmentManager
                        .beginTransaction()
                        .setCustomAnimations(android.R.anim.slide_in_left,R.anim.abc_fade_out)
                        .replace(R.id.ContainerFragment,StatisticsFragment.newInstance(name?:"no hay nombre",
                            correct.toString(),incorrect.toString())).commit()
                }

                if (timerState == TimerState.Running) {
                    timer.cancel()
                    onTimerFinished()
                }

                if (timerState == TimerState.Stopped) {
                    startTimer()
                }


            }

        }

    }

    fun playSound(sound: Int, fSpeed: Float) {
        val mgr = activity!!.applicationContext.audioManager
        val streamVolumeCurrent = mgr
            .getStreamVolume(AudioManager.STREAM_MUSIC).toFloat()
        val streamVolumeMax = mgr
            .getStreamMaxVolume(AudioManager.STREAM_MUSIC).toFloat()
        val volume = streamVolumeCurrent / streamVolumeMax
        soundPool.play(soundsMap.get(sound), volume, volume, 1, 0, fSpeed)
    }

}
