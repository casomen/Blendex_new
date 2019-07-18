package blendex.idiomasblendex.com.games

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import blendex.idiomasblendex.com.Objects.GlideApp
import blendex.idiomasblendex.com.PrefUtil
import kotlinx.android.synthetic.main.activity_exe1.*
import org.jetbrains.anko.textColor
import com.tomergoldst.tooltips.ToolTipsManager
import com.tomergoldst.tooltips.ToolTip





class Exe1Activity : AppCompatActivity() {


    enum class TimerState{
        Stopped, Paused, Running
    }

    private lateinit var timer: CountDownTimer
    private var timerLengthSeconds: Long = 30L
    private var timerState = TimerState.Stopped

    private var secondsRemaining: Long = 30L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(blendex.idiomasblendex.com.R.layout.activity_exe1)

        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar!!.title = "Regresar"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        GlideApp.with(applicationContext)
            .load("https://static.idiomasblendex.com/HOME/aprender+italiano.jpg")
            .circleCrop()
            .into(image_P)

        /*Handler().postDelayed(
            {
                startTimer()
                timerState =  TimerState.Running
            },
            300
        )*/
        val mToolTipsManager = ToolTipsManager()

        val builder = ToolTip.Builder(this, profession_exe1, CoordinatorLayoutExe1,
            "Enfermera", ToolTip.POSITION_ABOVE).setBackgroundColor(Color.BLUE)

        profession_exe1.setOnClickListener {
            mToolTipsManager.show(builder.build())
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    /*private fun initTimer(){
        timerState = PrefUtil.getTimerState(this@Exe1Activity)
        if (timerState == TimerState.Stopped)
            setNewTimerLength()
        else
            setPreviousTimerLength()

        secondsRemaining = if (timerState == TimerState.Running || timerState == TimerState.Paused)
            PrefUtil.getSecondsRemaining(this)
        else
            timerLengthSeconds

        progress_countdown.progressDrawable.setColorFilter(
            Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN)
        textView_countdown.textColor = Color.BLACK
        updateCountdownUI()
    }*/

    private fun onTimerFinished(){
        timerState = TimerState.Stopped

       progress_countdown.progress = timerLengthSeconds.toInt()

       PrefUtil.setSecondsRemaining(timerLengthSeconds, this)
        secondsRemaining = timerLengthSeconds
            with(progress_countdown) {
                progressDrawable.setColorFilter(
                        Color.RED, android.graphics.PorterDuff.Mode.SRC_IN)
                textView_countdown.textColor = Color.RED


        }
        //updateCountdownUI()
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

    private fun setNewTimerLength(){
        Log.w("caso",timerLengthSeconds.toString())
        progress_countdown.max = timerLengthSeconds.toInt()
    }

    private fun setPreviousTimerLength(){
        timerLengthSeconds = PrefUtil.getPreviousTimerLengthSeconds(this)
        progress_countdown.max = timerLengthSeconds.toInt()
    }

    private fun updateCountdownUI(){
        val minutesUntilFinished = secondsRemaining / 60
        val secondsInMinuteUntilFinished = secondsRemaining - minutesUntilFinished * 60
        val secondsStr = secondsInMinuteUntilFinished.toString()
        textView_countdown.text = if (secondsStr.length == 2) secondsStr else "0$secondsStr"
        progress_countdown.progress = (timerLengthSeconds - secondsRemaining).toInt()
        if((timerLengthSeconds - secondsRemaining).toInt() == 20){
            progress_countdown.progressDrawable.setColorFilter(
                Color.YELLOW, android.graphics.PorterDuff.Mode.SRC_IN)
            textView_countdown.textColor = Color.BLACK
        }
    }


    override fun onResume() {
        super.onResume()
        //initTimer()
    }


    override fun onPause() {
        super.onPause()

        if (timerState == TimerState.Running){
            timer.cancel()
        }

    }

}
