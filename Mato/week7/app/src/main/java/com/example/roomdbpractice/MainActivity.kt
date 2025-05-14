package com.example.roomdbpractice

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.roomdbpractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var list = ArrayList<Profile>()
    lateinit var db: ProfileDatabase
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: ProfileAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = ProfileDatabase.getInstance(this)!!



        Thread{
            val savedContacts = db.profileDao().getAll()
            if(savedContacts.isNotEmpty()){
                list.addAll(savedContacts)
            }
        }.start()

        binding.button.setOnClickListener {
            Thread {
                // 새 데이터 생성 및 삽입
                val newProfile = Profile("베어", "24", "0000")
                db.profileDao().insert(newProfile)

                // 최신 리스트 가져오기
                val updatedList = db.profileDao().getAll()

                // UI 쓰레드에서 리스트 갱신 및 화면 갱신
                runOnUiThread {
                    list.clear()
                    list.addAll(updatedList)
                    adapter.notifyDataSetChanged()
                }

                Log.d("Inserted Primary Key", updatedList.last().id.toString())
            }.start()
        }

        adapter = ProfileAdapter(this, list)
        binding.mainLv.adapter = adapter






    }
}