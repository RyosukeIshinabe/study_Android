package com.gudaletsu.lightgame

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.TextView



class MainActivity : AppCompatActivity() {

    private val saveData = IntArray(4)  // あとで使う。セーブデータを配列で管理する用

    // アプリ起動時に1回だけ読み込まれる処理
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ゲームスタートボタンを押した時のリスナーを用意
        btn_gameStart_001.setOnClickListener {
            // インテントを用意
            val intent = Intent(this, GameActivity::class.java)
            // GameActivityを開く
            startActivity(intent)
        }

        saveData[0] = 1 // 0版は無条件で1でいいや
    }

    @Override   // このアクティビティが再表示される度に読み込まれる処理
    override fun onResume() {
        super.onResume()    // これはおまじない（定型文）
        loadData()
        displayData()
    }

    // 共有プリファレンスに入っていたセーブデータを取得してsaveDate変数に格納
    fun loadData() {
        // SharedPreferencesのインスタンスを取得
        val pref = PreferenceManager.getDefaultSharedPreferences(this)

        // セーブデータの配列数分 繰り返す
        for (i in 1..(saveData.size-1)) {
            // SharedPreferencesに保存されているデータを取得
            pref.apply {
                saveData[i] = getInt(i.toString(), 0)
            }
        }
    }

    // saveDate変数をテキストビューに反映
    fun displayData() {

        // セーブデータの配列数分 繰り返す
        for (i in 1..(saveData.size-1)) {

            // 「txtvw_perfect_i」のリソースを探してIDを取得
            val viewId = resources.getIdentifier("txtvw_perfect_" + i.toString(), "id", packageName)
            // 探したIDからtextViewを見つけ出す
            val textView = findViewById(viewId) as TextView

            // セーブデータに格納されたintに応じて表示する文言を決定
            if ( saveData[i] == 2 ) {
                textView.text = "Perfect!"
            } else if ( saveData[i] == 1 ) {
                textView.text = "Cleared!"
            } else {
                textView.text = "not cleared"
            }
        }
    }


}
