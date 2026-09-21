package com.ute.studentprofile

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ute.studentprofile.databinding.ActivityMainBinding
import com.ute.studentprofile.utils.gone
import com.ute.studentprofile.utils.toast
import com.ute.studentprofile.utils.trimmedText
import com.ute.studentprofile.utils.toAcademicRanking

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displayStudent(
            "Lâm Hùng Thiên Doanh",
            3.75,
            "2415053122206@ute.udn.vn"
        )

        val currentGpa = 3.75
        binding.tvRanking.text = currentGpa.toAcademicRanking()

        binding.btnUpdate.setOnClickListener {
            updateStudentName()
        }

        binding.btnTestLet.setOnClickListener {
            testLet()
        }

        binding.btnTestAlso.setOnClickListener {
            testAlso()
        }

        binding.btnTestRun.setOnClickListener {
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
            btnUpdate.isEnabled = true
        }
    }

    private fun updateStudentName() {
        val name = binding.edtName.trimmedText()

        if (name.isNotBlank()) {
            binding.tvName.text = "Họ tên: $name"
            toast("Đã cập nhật thông tin")
        } else {
            toast("Vui lòng nhập tên")
        }
    }

    private fun testLet() {
        val name: String? = binding.edtName
            .trimmedText()
            .takeIf { it.isNotBlank() }

        name?.let { validName ->
            binding.tvName.text = "Họ tên: $validName"

            toast("let đã xử lý tên: $validName")
        } ?: run {
            toast("Tên đang rỗng")
        }
    }

    private fun testAlso() {
        val score = 3.75
            .also {
                Log.d("STUDENT_AUDIT", "GPA ban đầu: $it")
            }
            .also {
                toast("GPA hiện tại: $it")
            }

        binding.tvGpa.text = "GPA: $score"
        binding.tvRanking.text = score.toAcademicRanking()
    }

    private fun testRun() {
        val result = binding.edtName
            .trimmedText()
            .takeIf { it.isNotBlank() }
            ?.run {
                uppercase()
            }
            ?: run {
                "CHƯA NHẬP TÊN"
            }

        binding.tvName.text = "Họ tên: $result"

        toast("run đã được thực hiện")
    }
}