package `in`.rithikjain.spillthetea.ui.splash

import `in`.rithikjain.spillthetea.databinding.ActivityPostsBinding
import `in`.rithikjain.spillthetea.databinding.ActivitySplashBinding
import `in`.rithikjain.spillthetea.ui.MainActivity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("SpillTeaPref", MODE_PRIVATE)
        if (sharedPreferences.getBoolean("isFirstTime", true)) {
            val intent = Intent(this, FirstTimeIntroActivity::class.java)
            startActivity(intent)
            sharedPreferences.edit().putBoolean("isFirstTime", false).commit()

            finish()
        }else{
            val intent =Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}