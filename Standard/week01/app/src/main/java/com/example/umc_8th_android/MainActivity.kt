package com.example.umc_8th_android

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_8th_android.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val buttons = listOf(binding.button1, binding.button2, binding.button3, binding.button4, binding.button5)

        for (button in buttons) {
            button.setOnClickListener {
                val intent = Intent(this, SecondActivity::class.java)
                startActivity(intent)

            }
        }
    }


}