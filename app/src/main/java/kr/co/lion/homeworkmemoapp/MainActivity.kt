package kr.co.lion.homeworkmemoapp

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kr.co.lion.homeworkmemoapp.databinding.ActivityMainBinding
import kr.co.lion.homeworkmemoapp.databinding.RowMainBinding
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding : ActivityMainBinding

    // Activity 런처
    lateinit var inputActivityLauncher : ActivityResultLauncher<Intent>
    lateinit var showActivityLauncher : ActivityResultLauncher<Intent>


    // RecycerView를 구성하기 위한 리스트
    val recyclerViewList = mutableListOf<Memo>()
    // 현재 항목을 구성하기 위해 사용한 객체가 Util.animalList의 몇번째 객체인지를 담을 리스트
    val recyclerViewIndexList = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        setLauncher()
        setToolbar()
        setView()
        setEvent()
    }

    // 런처 설정
    fun setLauncher(){
        // InputActivity 런처
        val contract1 = ActivityResultContracts.StartActivityForResult()
        inputActivityLauncher = registerForActivityResult(contract1){

        }

        // ShowActivity 런처
        val contract2 = ActivityResultContracts.StartActivityForResult()
        showActivityLauncher = registerForActivityResult(contract2){

        }
    }

    override fun onResume() {
        super.onResume()
        activityMainBinding.apply {
            // 리사이클러뷰 갱신
            recyclerViewMain.adapter?.notifyDataSetChanged()

        }
    }

    // 툴바 세팅(toolbarMain)
    fun setToolbar(){
        activityMainBinding.apply {
            toolbarMain.apply {
                // 제목
                title = "메모 관리"
                setTitleTextColor(Color.WHITE)

                // 메뉴 세팅
                inflateMenu(R.menu.menu_main)
                setOnMenuItemClickListener {

                    // InputActivity를 실행한다.
                    val inputIntent = Intent(this@MainActivity, InputActivity::class.java)
                    inputActivityLauncher.launch(inputIntent)

                    true
                }
            }
        }
    }

    // 뷰 세팅
    fun setView(){
        activityMainBinding.apply {
            // RecyclerView
            recyclerViewMain.apply {
                // 어뎁터
                adapter = RecyclerViewMainAdapter()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(this@MainActivity)
                // 데코레이션
                val deco = MaterialDividerItemDecoration(this@MainActivity, MaterialDividerItemDecoration.VERTICAL)
                addItemDecoration(deco)
            }
        }
    }
    // 이벤트 설정
    fun setEvent(){
        activityMainBinding.apply {
            // FloatActionButton
            toolbarMain.setOnMenuItemClickListener {
                // InputActivity를 실행한다.
                val inputIntent = Intent(this@MainActivity, InputActivity::class.java)
                inputActivityLauncher.launch(inputIntent)

                true
            }
        }
    }

    // RecyclerView의 어뎁터
    inner class RecyclerViewMainAdapter : RecyclerView.Adapter<RecyclerViewMainAdapter.ViewHolderMain>(){
        // ViewHolder
        inner class ViewHolderMain (rowMainBinding: RowMainBinding) : RecyclerView.ViewHolder(rowMainBinding.root){
            val rowMainBinding:RowMainBinding

            init {
                this.rowMainBinding = rowMainBinding
                // 항목 클릭시 전체가 클릭이 될 수 있도록
                // 가로 세로 길이를 설정해준다.
                this.rowMainBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMain {
            val rowMainBinding = RowMainBinding.inflate(layoutInflater)
            val viewHolderMain = ViewHolderMain(rowMainBinding)

            return viewHolderMain
        }

        override fun getItemCount(): Int {
            return recyclerViewList.size
        }

        override fun onBindViewHolder(holder: ViewHolderMain, position: Int) {
            // position 번째 객체를 추출한다.
            val memo = recyclerViewList[position]
            // 메모의 제목을 설정한다.
            holder.rowMainBinding.textViewRowTitle.text = memo.title
            holder.rowMainBinding.textViewRowDate.text = memo.writeDate

            // 항목을 누르면 ShowActivity를 실행한다.
            holder.rowMainBinding.root.setOnClickListener {
                val showIntent = Intent(this@MainActivity, ShowActivity::class.java)
                // 현재 번째의 순서값을 담아준다.
                // showIntent.putExtra("position", position)

                // 사용자가 선택한 항목을 구성하기 위해 사용한 객체가
                // Util.animalList 리스트에 몇번째에 있는 값인지를 담아준다.
                showIntent.putExtra("position", recyclerViewIndexList[position])

                val obj = Util.memoList[recyclerViewIndexList[position]]
                // List의 제네릭이 Animal이므로 객체를 추출하면 Memo 타입이다.
                // Animal 이 Parcelable을 구현하고 있기때문에 Intent에 담을 수 있다.
                showIntent.putExtra("obj", obj)

                showActivityLauncher.launch(showIntent)
            }

        }
    }


}