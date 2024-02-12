package kr.co.lion.homeworkmemoapp

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.inputmethod.InputMethodManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kr.co.lion.homeworkmemoapp.databinding.ActivityInputBinding
import kr.co.lion.homeworkmemoapp.databinding.ActivityMainBinding
import java.text.DateFormat
import java.util.Date
import kotlin.concurrent.thread

class InputActivity : AppCompatActivity() {

    lateinit var activityInputBinding: ActivityInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityInputBinding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(activityInputBinding.root)


        setToolbar()
        initView()
        setView()
    }

    // 툴바 세팅(toolbarMain)
    fun setToolbar() {
        activityInputBinding.apply {
            toolbarInput.apply {
                // 제목
                title = "메모 작성"
                setTitleTextColor(Color.WHITE)

                // 뒤로 가기 설정
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    setResult(RESULT_CANCELED)
                    finish()
                }
                // 메뉴 세팅
                inflateMenu(R.menu.menu_input)
                setOnMenuItemClickListener {

                    when (it.itemId) {
                        // Done
                        R.id.menu_item_input_done -> {
                            // 유효성 검사 메서드를 호출한다.
                            checkInput()
                        }
                    }
                    true
                }
            }
        }
    }

    // View 들의 초기 상태를 설정한다.
    fun initView(){
        activityInputBinding.apply {
            // 이름 입력칸에 포커스를 준다.
            Util.showSoftInput(activityInputBinding.textFieldInputTitle, this@InputActivity)
        }
    }

    // View 설정
    fun setView() {
        activityInputBinding.apply {
            // 뷰에 포커스를 준다.
            textFieldInputTitle.requestFocus()
            // 키보드를 올린다.
            // 이 때, View를 지정해야한다.

            Util.showSoftInput(textFieldInputTitle,this@InputActivity)


            // 수학 입력칸
            // 엔터키를 누르면 입력 완료 처리를 한다.
            textFieldInputContent.setOnEditorActionListener { v, actionId, event ->
                checkInput()

                true
            }
        }
    }

//    // 입력 유효성 검사
    fun checkInput() {
        activityInputBinding.apply {
            // 제목
            val title = textFieldInputTitle.text.toString()
            if (title.trim().isEmpty()) {
                Util.showInfoDialog(
                    this@InputActivity,
                    "제목 입력 오류",
                    "제목을 입력해주세요"
                ) { dialogInterface: DialogInterface, i: Int ->
                    Util.showSoftInput(textFieldInputTitle, this@InputActivity)
                }
                return
            }

            // 내용
            val content = textFieldInputContent.text.toString()
            if (content.trim().isEmpty()) {
                Util.showInfoDialog(
                    this@InputActivity,
                    "내용 입력 오류",
                    "내용을 입력해주세요"
                ) { dialogInterface: DialogInterface, i: Int ->
                    Util.showSoftInput(textFieldInputContent, this@InputActivity)
                }
                return
            }

            // 저장처리
            addMemoData()
            // 액티비티를 종료한다.
            finish()
        }
    }

    // 저장처리
    fun addMemoData() {
        activityInputBinding.apply {
            toolbarInput.apply {
                setOnMenuItemClickListener {
                    val memo = Memo()
                    memo.title = textFieldInputTitle.text.toString()
                    memo.content = textFieldInputContent.text.toString()
                    memo.writeDate = System.currentTimeMillis().toString()

                    Util.memoList.add(memo)
                }
            }
        }
    }
}