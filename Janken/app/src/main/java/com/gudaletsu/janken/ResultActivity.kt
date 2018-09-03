package com.gudaletsu.janken

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    // じゃんけんの手
    val gu = 0
    val choki = 1
    val pa = 2

    // アクティビティ読み込み時に行う処理
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // MainActivityからintentを使って渡された値を取り出す
        // .getIntExtra(取り出したい情報のキー,取り出せなかった時のデフォルト値)
        // 文字列の場合はgetStringExtra、真偽値の場合はgetBooleanExtra
        val id = intent.getIntExtra("MY_HAND",0)

        // 自分の手を表す変数をint型で作成
        val myHand: Int

        // 変数idに入っている値によって条件分岐
        myHand = when(id) {

            // Rクラスとは、画面レイアウト、文字、画像ファイルなど各種リソースを参照するためのクラス。リソースをint型のIDで管理している。
            // ぐーを出した場合
            R.id.gu -> {

                // myHandImageにdrawable内の画像「gu」を描写
                myHandImage.setImageResource(R.drawable.gu)

                // 変数guを返してmyHandに格納（returnを省略している）
                gu
            }
            R.id.choki -> {
                myHandImage.setImageResource(R.drawable.choki)
                choki
            }
            R.id.pa -> {
                myHandImage.setImageResource(R.drawable.pa)
                pa
            }
            // nullを確実に回避するためにelseを設定
            else -> gu
        }

        // コンピューターの手を決める（ランダムver）
        // Math.random()はJavaのメソッドで、0以上1未満の乱数を返す。
        // この場合3をかけるので、0〜2.99。それをtoInt（Int型に変換）しているので0〜2のランダムな値が得られる。
        // val comHand = (Math.random() * 3).toInt()

        // コンピューターの手を決める（最強じゃんけんマシーンver）
        // getHandメソッドを呼んで最強ロジックに沿って手を取得
        val comHand = getHand()

        // コンピューターの手によって条件分岐
        when(comHand) {

            // ぐーなら、comHandImageにdrawable内の画像「gu」を描写
            gu -> comHandImage.setImageResource(R.drawable.com_gu)
            choki -> comHandImage.setImageResource(R.drawable.com_choki)
            pa -> comHandImage.setImageResource(R.drawable.com_pa)
        }

        // 勝敗の判定
        // comHand - myHand に3を足して整数に揃えたあと、3で割った余りによって勝敗が分かる
        val gameResult = ( comHand - myHand + 3 ) % 3
        when (gameResult) {

            // 0だった場合、テキストビュー「resultLabel」に、Rクラス（リソース）のstring「result_draw」を表示
            0 -> resultLabel.setText(R.string.result_draw)
            1 -> resultLabel.setText(R.string.result_win)
            2 -> resultLabel.setText(R.string.result_lose)
        }

        // backButtonのクリックイベントでfinish()を指定する
        // finish()は、現在のアクティビティを終了して前のアクティビティに戻る動作
        backButton.setOnClickListener { finish() }

        // じゃんけんの結果を保存する
        saveData(myHand, comHand, gameResult)
    }

    // 実績をセーブするメソッド（自分の手、COMの手、結果を引数で受け取る）
    private fun saveData(myHand: Int, comHand: Int, gameResult: Int) {

        // PreferenceManagerクラスのgetDefaultSharedPreferencesメソッドを使ってデフォルトの共有プリファレンスを取得
        val pref = PreferenceManager.getDefaultSharedPreferences(this)

        // 共有プリファレンス「pref」から値を取得。getInt（キー,設定項目が未設定の場合のデフォルト値）
        // 取得する値の型によってgetStringやgetBooleanを使う
        val gameCount = pref.getInt("GAME_COUNT", 0)
        val winningStreakCount = pref.getInt("WINNING_STREAK_COUNT", 0)
        val lastComHand = pref.getInt("LAST_COM_HAND", 0)
        val lastGameResult = pref.getInt("GAME_RESULT", -1)

        // 書き込みをするためにedit()メソッドを使用
        val editor = pref.edit()

        // putInt（キー,書き込む値）で、各種プリファレンスに渡す値を設定
        editor.putInt("GAME_COUNT", gameCount + 1)
              .putInt("WINNING_STREAK_COUNT",

                  // putIntの第二引数の箇所にそのままif文を使用する（Javaではできない）
                  // comが連想した場合は連勝数を1増やし、そうでない場合は0を書き込む
                  if ( lastGameResult == 2 && gameResult == 2 )
                      winningStreakCount + 1
                  else
                      0)

              // 結果を書き込み
              .putInt("LAST_MY_HAND", myHand)
              .putInt("LAST_COM_HAND", comHand)
              .putInt("BEFORE_LAST_COM_HAND", lastComHand)
              .putInt("GAME_RESULT", gameResult)

              // 保存を実行
              .apply()
    }

    private fun getHand(): Int {
        var hand = (Math.random() * 3).toInt()
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val gameCount = pref.getInt("GAME_COUNT", 0)
        val winningStreakCount = pref.getInt("WINNING_STREAK_COUNT", 0)
        val lastMyHand = pref.getInt("LAST_MY_HAND", 0)
        val lastComHand = pref.getInt("LAST_COM_HAND", 0)
        val beforeLastComHand = pref.getInt("BEFORE_LAST_COM_HAND", 0)
        val gameResult = pref.getInt("GAME_RESULT", -1)

        if ( gameCount == 1 ) {
            if ( gameResult == 2 ) {
                // 前回の勝負が1回目で、かつコンピュータが勝った場合
                // コンピュータは次に出す手を変える
                while ( lastComHand == hand ) {
                    hand = (Math.random() * 3).toInt()
                }
            } else if ( gameResult == 1 ) {
                // 前回の勝負が1回目で、かつコンピュータが負けた場合
                // 相手の出した手に勝つ手を出す
                hand = (lastMyHand - 1 + 3) % 3
            }
        } else if ( winningStreakCount > 0 ) {
            if ( beforeLastComHand == lastComHand ) {
                // 同じ手で連勝した場合は手を変える
                while ( lastComHand == hand ) {
                    hand = (Math.random() * 3).toInt()
                }
            }
        }
        return hand
    }





}
