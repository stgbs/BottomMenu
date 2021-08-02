package com.anroid.bottommenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
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

        bn.setOnNavigationItemSelectedListener {
            replaceFragment(
                when (it.itemId) {
                    R.id.menu_home -> HomeFragment()
                    R.id.menu_review -> ReviewFragment()
                    R.id.menu_mypage -> MypageFragment()
                    R.id.menu_setting -> SettingFragment()
                    else -> LogoutFragment()
                }
            )
            true
        }
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(fl.id, fragment).commit()
    }

    fun reviewToMypage() {
        replaceFragment(MypageFragment())
        bn.menu.getItem(2).isChecked = true
    }

    fun mypageToReview(fragment: Fragment, reviewList: Review) {
        val bundle = Bundle()
        bundle.putInt("alias", reviewList.alias)
        bundle.putString("title", reviewList.title)
        //bundle.putByteArray("image", reviewList.image)
        bundle.putString("reviewContent", reviewList.review)
        bundle.putString("description", reviewList.description)
        bundle.putFloat("rating", reviewList.rating)
        bundle.putString("emotion", reviewList.emotion)
        bundle.putString("recommend", reviewList.recommend)
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.fl_, fragment).commit()
        bn.menu.getItem(1).isChecked = true
    }

}
