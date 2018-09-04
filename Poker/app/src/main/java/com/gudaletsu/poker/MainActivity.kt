package com.gudaletsu.poker

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var bet: Int = 10
    private var prefCoin: Int = 0

    // アプリ起動時に呼ばれる処理
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
            共有プリファレンスの処理
         */

        // SharedPreferencesのインスタンスを取得
        val pref = PreferenceManager.getDefaultSharedPreferences(this)

        // SharedPreferencesに保存されているコインを取得
        pref.apply {
            prefCoin = getInt("COIN", 100)
        }

        /*
            情報の表示
         */

        // コインを表示
        txvw_myCoin.setText(prefCoin.toString())

        /*
            メソッド
         */

        // STARTボタンが押された時の処理
        btn_gameStart.setOnClickListener {
            if ( prefCoin >= bet ) {
                gameStart(it)
            } else {
                txtvw_errorMsg.setText("コインが足りません。")
            }
        }

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
