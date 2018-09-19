package com.gudaletsu.basicrpg

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_battle.*
import com.google.gson.Gson

class BattleActivity : AppCompatActivity() {

    private lateinit var player: Player
    private lateinit var battle: Battle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battle)

        // セーブデータを取得
        pullDataBese()
        if ( player == null ) {
            finish()    // nullなわけないので強制終了
        }

        // バトルクラスにプレイヤーを渡す
        battle = Battle(player)

        // ステータスを更新
        updateStatus()

        // 強制ボタンを非表示
        btn_force.visibility = View.INVISIBLE

        // 各種コマンドの定義
        btn_cmndAtk.setOnClickListener {
            clearMsg()
            attackOfPlayer(1.0)
            checkBattleEnd()
            attackOfEnemy(1.0)
            checkBattleEnd()
        }
        btn_cmndDef.setOnClickListener {
            clearMsg()
            attackOfEnemy(0.5)
            checkBattleEnd()
        }
        btn_cmndCure.setOnClickListener {
            clearMsg()
            cure()
            checkBattleEnd()
            attackOfEnemy(1.0)
            checkBattleEnd()
        }
        btn_cmndEsc.setOnClickListener {
            clearMsg()
            endBattle()
        }

        // 各ボタンの定義
        btn_force.setOnClickListener {
            clearMsg()
            endBattle()
        }
    }

    // プレイヤーの攻撃
    private fun attackOfPlayer(amount : Double) {
        var damage : Int = battle.attack(true, amount)
        if ( damage == 0 ) {
            txvw_mainMsg.text = "あなたの攻撃！\n" + "失敗した…"
        } else {
            txvw_mainMsg.text = "あなたの攻撃！\n" +
                                battle.getEnemy().getName() + "に" + damage.toString() + "のダメージ！"
        }
        updateStatus()
    }

    // 敵の攻撃
    private fun attackOfEnemy(amount : Double) {
        var damage: Int = battle.attack(false, amount)
        if ( damage == 0 ) {
            txvw_subMsg.text = battle.getEnemy().getName() + "の攻撃！\n" +
                                "失敗した！"
        } else {
            txvw_subMsg.text = battle.getEnemy().getName() + "の攻撃！\n" +
                                damage.toString() + "のダメージを受けた"
        }
        updateStatus()
    }

    // 回復
    private fun cure() {
        var cured : Int = battle.cure()
        if ( cured == 0 ) {
            txvw_mainMsg.text = "回復に失敗した…！"
        } else {
            txvw_mainMsg.text = "HPを" + cured.toString() + "回復した！"
        }
        updateStatus()
    }

    // セーブしてバトル終了
    private fun endBattle() {
        pushDataBese()
        finish()
    }

    // ステータスを更新
    private fun updateStatus() {
        txvw_sttsLv.text = player.getLv().toString()
        txvw_sttsHp.text = player.getHp().toString()
        txvw_sttsMp.text = player.getMp().toString()
        txvw_enemyLv.text = battle.enemy.getLv().toString()
    }

    // ゲームが終了しているか判定
    private fun checkBattleEnd() {
        if ( player.getHp() == 0 ) {
            die()
        } else if ( battle.enemy.getHp() == 0 ) {
            win()
        }
    }

    // プレイヤーが死んだ時の処理
    private fun die() {
        val msg : String = "あなたはしんでしまった…\n\n・プレイヤーLvが1下がります\n・手持ちのGoldを全て没収します\n\nマップに戻ります"
        this.player.lvDown()
        this.player.changeGold(-9999)
        displayForceBtn(msg)
    }

    // バトル後（勝利）の一連の処理
    private fun win() {
        val msg : String = "勝利！\n\n" +
                            this.battle.enemy.getExp().toString() + "ポイントの経験値を獲得した！\n" +
                            this.battle.enemy.getGold().toString() + "ゴールド手に入れた！"
        this.player.changeExp(this.battle.enemy.getExp())  // 敵の持つExpを加算
        this.player.checkLv()  // 経験値累計を元にレベルを変更
        this.player.checkStatus()  // レベルを元にステータスを変更
        this.player.changeGold(this.battle.enemy.getGold())    // 敵の持つゴールドを加算
        displayForceBtn(msg)
    }

    // データベースからセーブデータを受け取る
    private fun pullDataBese() {
        // SharedPreferencesのインスタンスを取得
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        // Gsonクラスのインスタンスを用意しておく
        val gson: Gson = Gson()

        pref.apply {
            // SharedPreferencesに保存されているJSON文字列を取得
            val saveDataStr = pref.getString("saveData", null)
            // JSON文字列をGSONで取り出し、Playerクラスに変換
            player = gson.fromJson(saveDataStr, Player::class.java)
        }
    }

    // データベースにセーブデータを保存する
    private fun pushDataBese() {
        // SharedPreferencesのインスタンスを取得
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()

        // Gsonクラスのインスタンスを用意しておく
        val gson: Gson = Gson()

        editor.apply {
            // playerインスタンスをJSON文字列に変換
            val saveDataStr = gson.toJson(player)
            // データベースに保存
            editor.putString("saveData", saveDataStr)
        }
    }

    // 他の操作を不可とする強制ボタンを表示する（タップでメイン画面に移動）
    private fun displayForceBtn(msg : String) {
        btn_force.text = msg
        btn_force.visibility = View.VISIBLE // 強制ボタンを表示
    }

    // 全てのメッセージ表示をnullにする
    private fun clearMsg() {
        txvw_mainMsg.text = null
        txvw_subMsg.text = null
        txvw_supplementMsg.text = null
        btn_force.text = null
    }

}
