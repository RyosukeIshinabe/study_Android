package com.gudaletsu.janken

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    // アクティビティ読み込み時に行う処理
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ビュー「gu」をクリックした時に発動するメソッドを指定（ラムダ式とSAM変換を使用）
        // onJankenButtonTappedに渡しているのは暗黙の引数it（タップされたViewオブジェクト自体が渡される）
        gu.setOnClickListener { onJankenButtonTapped(it) }
        choki.setOnClickListener { onJankenButtonTapped(it) }
        pa.setOnClickListener{ onJankenButtonTapped(it) }

        // 勝負情報をアプリ起動時にリセットする
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()
        editor.clear().apply()
    }

    // ビューを受け取って行う処理（nullが来る可能性もあるので[?]付き
    fun onJankenButtonTapped(view: View?) {

        /*
            Ankoを使わずに書いた場合
         */

        // intentクラスを作成
        // 第一引数で読み出し元のインスタンスを指定（この場合はこの画面、つまりMainActivityなのでthis）
        // 第二引数で呼び出したいアクティビティを指定。Javaでは「クラス名.class」だが、Kotlinでは「クラス名::class.java」でClassのインスタンスを取得
        val intent = Intent(this, ResultActivity::class.java)

        // intentに、次のアクティビティに引き渡すデータを設定する
        // putExtra（キー, 引き渡す値）。ここではMY_HANDをキーにして（別にどんな名前でも良い）、onJankenButtonTappedの引数で渡されたビューのIDを保存している（null許容型なので[?]）
        intent.putExtra("MY_HAND", view?.id )

        // startActivity(インテント)で先ほどセットしたアクティビティを呼び出す
        startActivity(intent)

        /*
            Ankoを使って書いた場合
         */

        // startActivity<起動したいActivity>でインテントを作成せず遷移できる。
        // ()で遷移先に渡したいデータを指定。「キーto値」の書き方でkotlinのPairクラスを使用
        // import org.jetbrains.anko.startActivity が自動補完されてるか確認を。
        startActivity<ResultActivity>("MY_HAND" to view?.id)
    }
}
