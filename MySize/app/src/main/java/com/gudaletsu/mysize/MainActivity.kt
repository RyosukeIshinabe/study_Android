package com.gudaletsu.mysize

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*
import android.view.View
import kotlinx.android.synthetic.main.activity_height.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    // アプリ起動時に呼ばれるメソッド
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
            長ったらしく書くパターン
         */

        // SharedPreferencesのインスタンスを取得
        val pref = PreferenceManager.getDefaultSharedPreferences(this)

        // スコープ関数applyを使って記述を簡略化
        pref.apply {
            // SharedPreferencesに保存されている値を定数に格納
            val editNeck = getString("NECK", "")
            val editSleeve = getString("SLEEVE", "")
            val editWaist = getString("WAIST", "")
            val editInseam = getString("INSEAM", "")

            // 上記で取得した定数を各TextViewに表示
            neck.setText(editNeck)
            sleeve.setText(editSleeve)
            waist.setText(editWaist)
            inseam.setText(editInseam)
            // EditableクラスではString型を単純に代入することができないためtextプロパティを使えない。代わりにsetTextを使う
        }

        /*
            pref変数を使わずに書くパターン
         */
//        PreferenceManager.getDefaultSharedPreferences(this).apply {
//            neck.setText(getString("NECK", ""))
//            sleeve.setText(getString("SLEEVE", ""))
//            waist.setText(getString("WAIST", ""))
//            inseam.setText(getString("INSEAM", ""))
//        }


        // saveボタンがクリックされたらonSaveTapped()メソッドを起動
        save.setOnClickListener { onSaveTapped() }

        heightButton.setOnClickListener {
            startActivity<HeightActivity>()
        }
    }

    // 各データを保存するメソッド
    private fun onSaveTapped() {

        // SharedPreferencesのインスタンスを取得
        val pref = PreferenceManager.getDefaultSharedPreferences(this)

        // エディターを用意
        val editor = pref.edit()

        // 各データを「キー, 値」で保存
        // EditableクラスだけどtoStringを使ってString型に変更することでtextプロパティが使えるようになる
        editor.putString("NECK", neck.text.toString())
                .putString("SLEEVE", sleeve.text.toString())
                .putString("WAIST", sleeve.text.toString())
                .putString("INSEAM", inseam.text.toString())
                .apply()    // 保存を実行
    }
}
