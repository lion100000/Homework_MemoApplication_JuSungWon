package kr.co.lion.homeworkmemoapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.homeworkmemoapp.databinding.ActivityMainBinding
import kr.co.lion.homeworkmemoapp.databinding.ActivityModifyBinding

class ModifyActivity : AppCompatActivity() {

    lateinit var activityModifyBinding: ActivityModifyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityModifyBinding = ActivityModifyBinding.inflate(layoutInflater)
        setContentView(activityModifyBinding.root)

        setToolbar()
        initView()

    }

    fun setToolbar(){
        activityModifyBinding.apply {
            toolbarModify.apply {
                // 타이틀
                title = "메모 수정"
                // Back
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    setResult(RESULT_CANCELED)
                    finish()
                }
                // 메뉴
                inflateMenu(R.menu.menu_modify)
                setOnMenuItemClickListener {
                    modifyData()
                    finish()
                    true
                }
            }
        }
    }

    fun initView(){
        activityModifyBinding.apply {
            toolbarModify.apply {

                // 순서값을 추출한다.
                val position = intent.getIntExtra("position", 0)
                // position 번째 객체를 추출한다.
                val memo = Util.memoList[position]
                // 공통
                textFieldModifyTitle.setText(memo.title)
                textFieldModifyContent.setText("${memo.content}")
            }
        }
    }

    // 수정 처리
    fun modifyData(){
        // 위치값을 가져온다.
        val position = intent.getIntExtra("position", 0)
        // position 번째 객체를 가져온다.
        val memo = Util.memoList[position]

        activityModifyBinding.apply {
            // 공통
            memo.title = textFieldModifyTitle.text.toString()
            memo.content = textFieldModifyContent.text.toString()

        }
    }
}