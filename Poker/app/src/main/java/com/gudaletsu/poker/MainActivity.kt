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

    // アプリ起動時に最初の1回だけ呼ばれる処理
    // アクティビティが戻ってきた時に再読み込みするものはonResumeに書くこと！
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
            STARTボタンが押された時の処理を定義
         */
        btn_gameStart.setOnClickListener {
            // ユーザーが入力したBETを受け取って変数に代入
            var inputBetStr = txvw_inputBet.text.toString()
            // intに変換（数字以外が入力されたらnullが入るようにする）
            var inputBetInt: Int? = inputBetStr.toIntOrNull()

            // nullじゃなく、かつ1よりも多ければ
            if ( inputBetInt != null && inputBetInt >= 1) {
                // BETよりも所持コインが多ければ
                if ( prefCoin >= inputBetInt ) {
                    // BETをメンバ変数に保存しておいてゲームスタートメソッドへ
                    bet = inputBetInt
                    gameStart(it)
                // BETよりも所持コインが少なければ
                } else {
                    txtvw_errorMsg.text = "そんな持ってねぇだろ"
                }
            // nullなら
            } else {
                txtvw_errorMsg.text = "変なの入力すんな"
            }
        }
    }

    @Override   // このアクティビティが再表示される度に読み込まれる処理
    override fun onResume() {
        super.onResume()    // これはおまじない（定型文）

        // SharedPreferencesのインスタンスを取得
        val pref = PreferenceManager.getDefaultSharedPreferences(this)

        // SharedPreferencesに保存されているコインを取得
        pref.apply {
            prefCoin = getInt("COIN", 100)
        }

        // 取得したコインをテキストビューに表示
        txvw_myCoin.text = prefCoin.toString()
    }

    // ゲームスタートメソッド
    private fun gameStart(view: View?) {
        // インテントを用意
        val intent = Intent(this, GameActivity::class.java)
        // インテントにBETを保存しておく
        intent.putExtra("BET", bet)
        // GameActivityを開く
        startActivity(intent)
    }

}
