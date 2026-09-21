package com.ute.studentprofile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ute.studentprofile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.tvWelcome.text = "Chào mừng bạn đến với ViewBinding!"

        binding.btnShow.setOnClickListener {
            binding.tvStudent.text = "MSSV: 2415053122206\nHọ tên: Lâm Hưng Thiên Doanh"
        }
    }
}