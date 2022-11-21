package com.artemissoftware.firegallery.screens.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.artemissoftware.firegallery.MainActivity
import com.artemissoftware.firegallery.R

class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContent {
//            FireGalleryTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    RootNavigationGraph(navController = rememberAnimatedNavController(), viewModel.scaffoldState)
//                }
//            }
//        }

        startNextAndFinish()
    }



    fun startNextAndFinish(){

        val action = intent.action
        val uri = intent.data

        if(action == Intent.ACTION_VIEW && uri != null){

            val intent = Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                putExtra("deep_link", uri.toString())
            }

            startActivity(intent)
            finish()

        }

    }

}