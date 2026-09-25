package com.ute.studentprofile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.ute.studentprofile.databinding.ActivityMainBinding
import com.ute.studentprofile.model.Student
import com.ute.studentprofile.utils.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val defaultStudent = Student(
        id = "2415053122206",
        name = "Lâm Hưng Thiên Doanh",
        className = "126TLTTD01",
        email = "2415053122206@sv.ute.udn.vn",
        gpa = 3.75
    )

    private var currentStudent = defaultStudent

    companion object {
        private const val KEY_STUDENT_DATA = "KEY_STUDENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        savedInstanceState?.let { bundle ->
            (bundle.getSerializable(KEY_STUDENT_DATA) as? Student)?.let { savedStudent ->
                currentStudent = savedStudent
            }
        }

        bindStudentData(currentStudent)
        setupRealtimeValidation()

        binding.btnUpdateGpa.setOnClickListener {
            handleUpdateGpa()
        }

        binding.btnReset.setOnClickListener {
            showResetConfirmationDialog()
        }

        binding.btnSendReport.setOnClickListener {
            sendAcademicReportEmail()
        }
    }

    private fun bindStudentData(student: Student) {
        with(binding) {
            tvStudentName.text = student.name
            tvStudentDetails.text = "MSSV: ${student.id} | Lớp: ${student.className}"
            tvStudentEmail.text = "Email: ${student.email}"

            tvGpaBadge.text = "${student.gpa} GPA - ${student.gpa.toAcademicRanking()}"
            tvGpaBadge.setTextColor(student.gpa.toRankingColor())

            edtGpaInput.setText(student.gpa.toString())
        }
    }

    private fun setupRealtimeValidation() {
        binding.edtGpaInput.doOnTextChanged { text, _, _, _ ->
            val input = text?.toString()?.trim() ?: ""
            if (input.isNotEmpty()) {
                binding.edtGpaInput.error = null
                val tempScore = input.toDoubleOrNull()
                if (tempScore != null && tempScore in 0.0..4.0) {
                    binding.tvPreviewRanking.text = "Dự kiến: ${tempScore.toAcademicRanking()}"
                    binding.tvPreviewRanking.show()
                } else {
                    binding.tvPreviewRanking.gone()
                }
            } else {
                binding.tvPreviewRanking.gone()
            }
        }
    }

    private fun handleUpdateGpa() {
        val rawInput = binding.edtGpaInput.trimmedText()
        val newGpa = rawInput.toDoubleOrNull()

        if (newGpa == null || newGpa !in 0.0..4.0) {
            binding.edtGpaInput.error = "Vui lòng nhập GPA hợp lệ (0.0 - 4.0)"
            binding.edtGpaInput.requestFocus()
            toast("Điểm số không hợp lệ, vui lòng kiểm tra lại!")
            return
        }

        binding.edtGpaInput.error = null
        binding.tvPreviewRanking.gone()

        currentStudent = currentStudent.copy(gpa = newGpa)
        bindStudentData(currentStudent)
        toast("Đã cập nhật GPA thành công!")
    }

    private fun showResetConfirmationDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("Xác nhận khôi phục")
            setMessage("Bạn có chắc chắn muốn đặt lại điểm GPA ban đầu (${defaultStudent.gpa}) không?")
            setPositiveButton("Đồng ý") { dialog, _ ->
                currentStudent = defaultStudent
                bindStudentData(currentStudent)
                binding.tvPreviewRanking.gone()
                binding.edtGpaInput.error = null
                toast("Đã khôi phục dữ liệu mặc định!")
                dialog.dismiss()
            }
            setNegativeButton("Hủy") { dialog, _ ->
                dialog.dismiss()
            }
        }.show()
    }

    private fun sendAcademicReportEmail() {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:${currentStudent.email}")
            putExtra(
                Intent.EXTRA_SUBJECT,
                "[Báo cáo học tập] Sinh viên ${currentStudent.name} - MSSV ${currentStudent.id}"
            )
            putExtra(
                Intent.EXTRA_TEXT,
                """
                Kính gửi Sinh viên / Phụ huynh,
                
                Dưới đây là thông tin báo cáo kết quả học tập hiện tại:
                - Họ và tên: ${currentStudent.name}
                - MSSV: ${currentStudent.id}
                - Lớp sinh hoạt: ${currentStudent.className}
                - Điểm tích lũy GPA: ${currentStudent.gpa} / 4.0
                - Xếp loại học lực: ${currentStudent.gpa.toAcademicRanking()}
                
                Trân trọng!
                """.trimIndent()
            )
        }

        try {
            startActivity(emailIntent)
        } catch (e: Exception) {
            toast("Không tìm thấy ứng dụng Email nào trên thiết bị!")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY_STUDENT_DATA, currentStudent)
    }
}