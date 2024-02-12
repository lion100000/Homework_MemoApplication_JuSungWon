package kr.co.lion.homeworkmemoapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kr.co.lion.homeworkmemoapp.databinding.ActivityShowBinding

class ShowActivity : AppCompatActivity() {

    lateinit var activityShowBinding: ActivityShowBinding

    // Activity 런처
    lateinit var modifyActivityLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityShowBinding = ActivityShowBinding.inflate(layoutInflater)
        setContentView(activityShowBinding.root)

        setLauncher()
        setToolbar()
    }

    // 런처 설정
    fun setLauncher(){
        // ModifyActivity 런처
        val contract1 = ActivityResultContracts.StartActivityForResult()
        modifyActivityLauncher = registerForActivityResult(contract1){

        }
    }

    // 툴바 설정
    fun setToolbar(){
        activityShowBinding.apply {
            toolbarShow.apply {
                // 타이틀
                title = "메모 정보"
                // back
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    setResult(RESULT_CANCELED)
                    finish()
                }
                // 메뉴
                inflateMenu(R.menu.menu_show)
                setOnMenuItemClickListener {
                    // 사용자가 선택한 메뉴 항목의 id로 분기한다.
                    when(it.itemId){
                        // 수정
                        R.id.menu_item_show_modify -> {
                            // ModifyActivity 실행
                            val modifyIntent = Intent(this@ShowActivity, ModifyActivity::class.java)

                            // 동물 순서값을 저정한다.
                            val position = intent.getIntExtra("position", 0)
                            modifyIntent.putExtra("position", position)

                            modifyActivityLauncher.launch(modifyIntent)
                        }
                        // 삭제
                        R.id.menu_item_show_delete -> {
                            // 항목 순서 값을 가져온다.
                            val position = intent.getIntExtra("position", 0)
                            // 항목 번째 객체를 리스트에서 제거한다.
                            Util.memoList.removeAt(position)
                            finish()
                        }
                    }

                    true
                }
            }
        }
    }

    // 뷰 설정
    fun setView(){
        activityShowBinding.apply {
            textFieldShowTitle.apply {
                // 항목 순서값을 가져온다.
                val position = intent.getIntExtra("position",0)
                // 포지션 번째 객체를 추출한다.
                val memo = Util.memoList[position]


            }

//            textFieldShowTitle.text = "${memoData.Title}"
//            append("학년 : ${memoData?.grade}학년\n")
//            append("\n")
//            append("국어 점수 : ${memoData?.kor}점\n")
//            append("영어 점수 : ${studentData?.eng}점\n")
//            append("수학 점수 : ${studentData?.math}점\n")
//            append("\n")
//
//            val total = studentData!!.kor + studentData!!.math + studentData!!.eng
//            append("총점 : ${total}점\n")
//            append("평균 : ${total / 3}점")
        }
    }
}