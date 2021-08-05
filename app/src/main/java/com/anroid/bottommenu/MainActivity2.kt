package com.anroid.bottommenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity2 : AppCompatActivity() {

    init{
        instance = this
    }

    companion object{
        private var instance:MainActivity2? = null
        fun getInstance(): MainActivity2? {
            return instance
        }
    }

    private val fl: FrameLayout by lazy {
        findViewById(R.id.fl_)
    }

    private val bn: BottomNavigationView by lazy {
        findViewById(R.id.bn_)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        supportFragmentManager.beginTransaction().add(fl.id, HomeFragment()).commit()

        bn.setOnNavigationItemSelectedListener {  // BottomNavigationView에서 어떤 버튼 눌렀는지 셀렉트 리스너
            replaceFragment(  // Fragement 이동시키는 함수
                when (it.itemId) {
                    R.id.menu_home -> HomeFragment()  //home 메뉴 클릭 시 HomeFragment로 이동
                    R.id.menu_review -> ReviewFragment()  // review 메뉴 클릭 시 ReviewFragment로 이동
                    else -> MypageFragment()  // 그 외의 메뉴 (mypage) 클릭 시 MypageFragment로 ㅇ동
                }
            )
            true
        }
    }

    fun replaceFragment(fragment: Fragment) {  // Fragment 이동시키는 함수 선언
        supportFragmentManager.beginTransaction().replace(fl.id, fragment).commit()
    }



    fun reviewToMypage() {
        replaceFragment(MypageFragment())                 // Review Fragment에서 Mypage Fragment로 이동
        bn.menu.getItem(2).isChecked = true        // 바텀 네비게이션 메뉴 3번째(Mypage) 활성화
    }




    fun mypageToReview(fragment: Fragment, reviewList: Review) {
        val bundle = Bundle()                                                                // 인자로 전달받은 리뷰 리스트의 데이터들을 번들에 Put
        bundle.putInt("alias", reviewList.alias)
        bundle.putString("title", reviewList.title)
        bundle.putString("genre", reviewList.genre)
        bundle.putString("category", reviewList.category)
        bundle.putString("reviewContent", reviewList.review)
        bundle.putString("description", reviewList.description)
        bundle.putFloat("rating", reviewList.rating)
        bundle.putString("emotion", reviewList.emotion)
        bundle.putString("recommend", reviewList.recommend)
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.fl_, fragment).commit()      // 번들과 함께 Mypage Fragment에서 Review Fragment로 이동
        bn.menu.getItem(1).isChecked = true                                           // 바텀 네비게이션 메뉴 2번째(Review) 활성화
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {  // 액션 바에 옵션 메뉴 추가
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.report -> {  // "개발자 문의" 메뉴 클릭 시
                val email = Intent(Intent.ACTION_SEND)  // intent 이용해 이메일 어플리케이션 호출
                email.type = "plain/text"
                val address = arrayOf("yilllios.03@gmail.com")
                email.putExtra(Intent.EXTRA_EMAIL, address)  // 미리 개발자 이메일 주소 입력
                email.putExtra(Intent.EXTRA_SUBJECT, "문의합니다.")  // 미리 메일 제목 입력
                email.putExtra(Intent.EXTRA_TEXT, "문의할 내용을 입력해 주세요")  // 미리 힌트가 될 메일 내용 입력
                startActivity(email)
                return true
            }
            R.id.logout -> {  // "로그아웃" 메뉴 클릭 시
                val dlg = AlertDialog.Builder(this)
                dlg.setMessage("로그아웃 하시겠습니까?")  // 사용자에게 로그아웃 여부를 한 번 더 확인
                dlg.setPositiveButton("확인") { dialog, which ->  // "확인" 버튼 클릭 시
                    val intent= Intent(this, MainActivity::class.java)  // MainActivity(로그인화면)로 이동
                    startActivity(intent)
                    Toast.makeText(this, "로그아웃됨", Toast.LENGTH_SHORT).show()  // 토스트 메시지 출력
                }
                dlg.setNegativeButton("취소") {dialog, which ->  // "취소" 버튼 클릭 시
                    Toast.makeText(this, "취소됨", Toast.LENGTH_SHORT).show()  // 토스트 메시지 출력
                }
                dlg.show()  // 내용이 채워진 Dialog 호출
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
