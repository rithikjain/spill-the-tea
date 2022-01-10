package `in`.rithikjain.spillthetea.ui.splash

import `in`.rithikjain.spillthetea.R
import `in`.rithikjain.spillthetea.databinding.ActivityFirstTimeIntroBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstTimeIntroActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFirstTimeIntroBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFirstTimeIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment=supportFragmentManager.findFragmentById(R.id.intro_nav_host_fragment) as NavHostFragment
        navController=navHostFragment.navController

    }
}