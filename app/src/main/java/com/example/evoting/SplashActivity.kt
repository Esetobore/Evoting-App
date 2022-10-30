package com.example.evoting

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView


class SplashActivity : AppCompatActivity() {
    private lateinit var lottieAnime: LottieAnimationView
    private lateinit var appName : TextView
    private lateinit var img : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        img = findViewById(R.id.img)
        lottieAnime = findViewById(R.id.lottie)
        appName = findViewById(R.id.app_name)

        img.animate().translationY(-2500F).setDuration(1000).startDelay = 5000
        appName.animate().translationY(2000F).setDuration(1000).startDelay = 5000
        lottieAnime.animate().translationY(1500F).setDuration(1000).startDelay = 5000

        Handler().postDelayed({
                startActivity(Intent(this, LoginActivity::class.java))
                              },
            6000
        )

    }
}