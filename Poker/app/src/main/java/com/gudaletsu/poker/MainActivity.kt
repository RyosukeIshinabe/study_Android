package com.gudaletsu.poker

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var bet: Int = 10
    private var myCoin: Int = 100

    // アプリ起動時に呼ばれる処理
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // STARTボタンが押された時の処理
        btn_gameStart.setOnClickListener {
            if ( myCoin >= bet ) {
                gameStart(it)
            } else {
                txtvw_errorMsg.setText("コインが足りません。")
            }
        }

        // 共有プリファレンスから所持コインを取得する処理（あとで書く）
    }

    // betを入力する処理（あとで書く）


    // ゲームスタートメソッド（GameActivityを開く）
    fun gameStart(view: View?) {
        // インテントを用意
        val intent = Intent(this, GameActivity::class.java)
        // インテントにBETを保存
        intent.putExtra("BET", bet)
        startActivity(intent)
    }

}
