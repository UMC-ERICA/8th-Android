package com.example.thread_practice

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val a = A()
        val b = B()

        a.start()
        a.join()
        b.start()
    }

    class A : Thread(){
        override fun run() {
            super.run()
            for(i in 1..1000){
                Log.d("test", "first: $i")
            }
        }
    }

    class B : Thread(){
        override fun run() {
            super.run()
            for(i in 1000 downTo 1){
                Log.d("test", "second: $i")
            }
        }
    }

}