package com.ute.studentprofile

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ute.studentprofile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvTitle.apply {
            text = "BÀI 2 - SCOPE FUNCTIONS"
            textSize = 22f
        }

        displayStudent(
            "Lâm Hùng Thiên Doanh",
            3.75,
            "2415053122206@ute.udn.vn"
        )

        binding.btnLet.setOnClickListener {
            testLet()
        }

        binding.btnAlso.setOnClickListener {
            testAlso()
        }

        binding.btnRun.setOnClickListener {
            testRun()
        }
    }

    private fun displayStudent(
        name: String,
        gpa: Double,
        email: String
    ) {
        with(binding) {
            tvName.text = "Họ tên: $name"
            tvGpa.text = "GPA: $gpa"
            tvEmail.text = "Email: $email"
        }
    }

    private fun testLet() {
        val name = binding.edtName.text
            .toString()
            .takeIf { it.isNotBlank() }

        name?.let { validName ->
            binding.tvName.text = "Họ tên: $validName"

            Toast.makeText(
                this,
                "let đã xử lý tên: $validName",
                Toast.LENGTH_SHORT
            ).show()
        } ?: run {
            Toast.makeText(
                this,
                "Tên đang rỗng",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun testAlso() {
        val score = 3.75.also {
            Log.d("STUDENT_AUDIT", "GPA ban đầu: $it")
        }

        binding.tvGpa.text = "GPA: $score"

        Toast.makeText(
            this,
            "GPA hiện tại: $score",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun testRun() {
        val result = binding.edtName.text
            .toString()
            .takeIf { it.isNotBlank() }
            ?.run {
                uppercase()
            }
            ?: run {
                "CHƯA NHẬP TÊN"
            }

        binding.tvName.text = "Họ tên: $result"

        Toast.makeText(
            this,
            "run đã được thực hiện",
            Toast.LENGTH_SHORT
        ).show()
    }
}