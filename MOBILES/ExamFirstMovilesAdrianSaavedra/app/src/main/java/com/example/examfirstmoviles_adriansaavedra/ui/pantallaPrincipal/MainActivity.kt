package com.example.examfirstmoviles_adriansaavedra.ui.pantallaPrincipal

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.examfirstmoviles_adriansaavedra.R
import com.example.examfirstmoviles_adriansaavedra.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            setContentView(root)
            val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHost.navController
            bottomNavigationView.setupWithNavController(navController)
            setSupportActionBar(topAppBar)
            setupActionBarWithNavController(navController)
            val appBarConfiguration = AppBarConfiguration(
                setOf(R.id.playersFragment, R.id.momentsFragment)
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
        }

    }

    override fun onSupportNavigateUp() = navController.navigateUp() || super.onSupportNavigateUp()
}