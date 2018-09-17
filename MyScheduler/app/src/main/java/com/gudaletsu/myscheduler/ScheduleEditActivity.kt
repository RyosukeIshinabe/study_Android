package com.gudaletsu.myscheduler

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.format.DateFormat
import android.view.View
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_schedule_edit.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ScheduleEditActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_edit)

        // realmインスタンスを取得
        realm = Realm.getDefaultInstance()

        // スケジュールの更新処理
        // インテントとして渡ってきたschedule_idを取得（存在しなかった場合は-1）
        val scheduleId = intent?.getLongExtra("schedule_id", -1L)

        // schedule_idの有無によってデータの新規登録なのか更新なのかを判断する
        // -1ではない場合（つまりschedule_idが存在しなかった場合）→データの更新
        if (scheduleId != -1L) {

            // データベース内の、idフィールドがscheduleId変数と同じレコードを取得して、schedule変数に格納
            // 全件検索すると時間がかかるので、findAllではなくfindFirstを使用（該当のデータは1件しかないはずなので）
            val schedule = realm.where<Schedule>()
                    .equalTo("id", scheduleId).findFirst()

            // 取得したデータを、画面上のそれぞれのできストビュー上に指定の表記で表示
            dateEdit.setText(
                    DateFormat.format("yyyy/MM/dd", schedule?.date))
            titleEdit.setText(schedule?.title)
            detailEdit.setText(schedule?.detail)
            delete.visibility = View.VISIBLE
        } else {
            delete.visibility = View.INVISIBLE
        }



        // saveボタンを押した時の処理
        save.setOnClickListener {

            // scheduleIdの有無で処理を分ける
            when (scheduleId) {
                -1L -> {

                    // executeTransactionを使うと、トランザクションの開始、コミット、ロールバックを自動で行う
                    // Realm.Transactionインターフェースを実装したクラスでは、executeメソッドをオーバーライドして実行したいデータベース処理を記述する
                    // ただしRealm.TransactionはSAMインターフェイスなので（メソッドが1つ）、SAM変換により内部の処理だけ{}内に記述する
                    realm.executeTransaction {

                        // excuteメソッド内では、RealmQueryクラスのメソッドであるmaxを使って、Scheduleのidフィールドの最大値を取得
                        val maxId = realm.where<Schedule>().max("id")

                        // idの最大値に+1した値を、新規に登録するモデルのidとする
                        // ?: はエルビス演算子。変数maxIdはnullの可能性があるので、null以外の場合はその値をLong型にしたものを、nullの場合はLong型の0を取得し、+1している
                        val nextId = (maxId?.toLong() ?: 0L) + 1L

                        // RealmインスタンスのcreateObjectメソッドを使い、データを1行追加
                        // 1増やした後のIDを持つScheduleクラスのインスタンスを受け取るので、この後に各フィールドに値を設定することでデータの追加ができる
                        val schedule = realm.createObject<Schedule>(nextId)

                        // toDateという拡張関数（後述）を定義して、TextEditに入力された日付を表す文字列をDate型の関数に変換してから設定
                        dateEdit.text.toString().toDate("yyyy/MM/dd")?.let {
                            schedule.date = it
                        }
                        schedule.title = titleEdit.text.toString()
                        schedule.detail = detailEdit.text.toString()
                    }

                    // アラートダイアログ（ankoのlaert関数を使って簡略化）
                    alert("追加しました") {
                        yesButton { finish() }
                    }.show()

                }
                else -> {
                    // executeTransactionを使うと、トランザクションの開始、コミット、ロールバックを自動で行う
                    realm.executeTransaction {

                        // scheduleIdを使用して該当のデータを取得
                        val schedule = realm.where<Schedule>()
                                .equalTo("id", scheduleId).findFirst()

                        // 表示
                        dateEdit.text.toString().toDate("yyyy/MM/dd")?.let {
                            schedule?.date = it
                        }
                        schedule?.title = titleEdit.text.toString()
                        schedule?.detail = detailEdit.text.toString()
                    }
                    alert("修正しました") {
                        yesButton { finish() }
                    }.show()
                }
            }
        }

        // デリートボタンを押した時の処理
        delete.setOnClickListener{
            realm.executeTransaction {
                realm.where<Schedule>().equalTo("id", scheduleId)?.findFirst()?.deleteFromRealm()
            }
            alert("削除しました") {
                yesButton { finish() }
            }.show()
        }
    }

    fun String.toDate(pattern: String = "yyyy/MM/dd HH:mm"): Date? {
        val sdFormat = try {
            SimpleDateFormat(pattern)
        } catch (e: IllegalArgumentException) {
            null
        }
        val date = sdFormat?.let {
            try {
                it.parse(this)
            } catch (e: ParseException) {
                null
            }
        }
        return date
    }

    // アクティビティ終了時にRealmインスタンスを破棄
    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
