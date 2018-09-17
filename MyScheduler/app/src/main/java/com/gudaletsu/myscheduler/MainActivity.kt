package com.gudaletsu.myscheduler

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import io.realm.Realm
import org.jetbrains.anko.startActivity

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    // データベースはRealmクラスを使用する
    // Realmクラスのプロパティを用意しておく。後から初期化するのでlateinit修飾子をつけておく
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        /*
            データベースの準備
         */

        // Realmクラスに用意されたメソッドgetDefaultInstanceで、Realmクラスのインスタンスを取得
        realm = Realm.getDefaultInstance()

        // Realmインスタンスからデータを取得するクエリを発行する。whereメソッドの<>でモデルの型を指定する
        // これでschedule変数内に、全てのScheduleデータが格納される
        val schedules = realm.where(Schedule::class.java).findAll()

        // ScheduleAdapterクラスのインスタンスを作成し、schedulesを渡してlistViewに設定（javaではsetAdapter）
        listView.adapter = ScheduleAdapter(schedules)

        /*
            画面遷移
         */

        // fabボタン（右下の丸いボタン）を押した時に画面遷移する
        fab.setOnClickListener { view ->
            startActivity<ScheduleEditActivity>()
        }

        // リストビューが押された時の処理はsetOnItemClickListenerで指定する（SAM変換で記載）
        // 引数1：タップされた項目を含むリストビューのインスタンス
        // 引数2：タップされた項目
        // 引数3：タップされた項目のリスト上の位置
        // 引数4：タップされた項目のID
        listView.setOnItemClickListener { parent, view, position, id ->

            // 第三引数として渡されてくるpositionをgetItemAtPositionメソッドに渡すと、
            // リスト内の指定された位置にあるデータが取得できる（そしてそれはScheduleインスタンス）
            val schedule = parent.getItemAtPosition(position) as Schedule

            // 取得したScheduleインスタンスからidを取得し、インテントにschedule_idとして格納することで
            // idを遷移先のアクティビティに渡している
            startActivity<ScheduleEditActivity>(
                    "schedule_id" to schedule.id )
        }
    }


    // オプションメニューを作成する処理。今回は使わないので削除
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }

    // オプションメニューを作成する処理。今回は使わないので削除
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

    // アクティビティ終了時にRealmインスタンスを破棄し、リソースを解放する
    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
