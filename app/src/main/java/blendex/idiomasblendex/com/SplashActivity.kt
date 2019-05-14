package blendex.idiomasblendex.com

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        scheduleSplashScreen()
    }

    private fun scheduleSplashScreen() {
        val splashScreenDuration = 500L
        Handler().postDelayed(
            {
                // After the splash screen duration, route to the right activities
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            },
            splashScreenDuration
        )
    }
}
