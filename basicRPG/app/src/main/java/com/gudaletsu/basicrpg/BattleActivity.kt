package com.gudaletsu.basicrpg

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.preference.PreferenceManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_battle.*
import com.google.gson.Gson

class BattleActivity : AppCompatActivity() {

    private var player: Player = Player()
    private lateinit var battle: Battle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battle)

        // セーブデータを取得
        pullDataBase()

        // バトルクラスにプレイヤーを渡す
        this.battle = Battle(this.player)

        // メッセージ欄を初期化する
        clearMsg()

        // 敵があらわれた
        apperEnemy()

        // ステータスを更新
        updateStatus()

        // 強制ボタンを非表示
        btn_force.visibility = View.INVISIBLE

        // 各種コマンドの定義
        btn_cmndAtk.setOnClickListener {
            Log.d("onClick", "[debug] atk command clicked")
            clearMsg()
            attackOfPlayer(1.0)
            if ( checkBattleEnd() ) {
                battleEnd()
            } else {
                attackOfEnemy(1.0)
                if ( checkBattleEnd() ) {
                    battleEnd()
                }
            }
        }
        btn_cmndDef.setOnClickListener {
            Log.d("onClick", "[debug] def command clicked")
            clearMsg()
            attackOfEnemy(0.5)
            if ( checkBattleEnd() ) {
                battleEnd()
            }
        }
        btn_cmndCure.setOnClickListener {
            Log.d("onClick", "[debug] cure command clicked")
            clearMsg()
            cure()
            attackOfEnemy(1.0)
            if ( checkBattleEnd() ) {
                battleEnd()
            }
        }
        btn_cmndEsc.setOnClickListener {
            Log.d("onClick", "[debug] esc command clicked")
            clearMsg()
            endBattleActivity()
        }

        // 各ボタンの定義
        btn_force.setOnClickListener {
            Log.d("onClick", "[debug] forced screen clicked")
            clearMsg()
            endBattleActivity()
        }

    }

    // プレイヤーの攻撃
    private fun attackOfPlayer(amount : Double) {
            Log.d("event", "[debug] you attack enemy")
        var damage : Int = this.battle.attack(true, amount)
        if ( damage == 0 ) {
            txvw_mainMsg.text = "あなたの攻撃！\n" + "失敗した…"
        } else {
            txvw_mainMsg.text = "あなたの攻撃！\n" +
                                this.battle.getEnemy().getName() + "に" + damage.toString() + "のダメージ！"
        }
        updateStatus()
    }

    // 敵の攻撃
    private fun attackOfEnemy(amount : Double) {
            Log.d("event", "[debug] enemy attacks you")
        var damage: Int = this.battle.attack(false, amount)
        if ( damage == 0 ) {
            txvw_subMsg.text = this.battle.getEnemy().getName() + "の攻撃！\n" +
                                "失敗した！"
        } else {
            txvw_subMsg.text = this.battle.getEnemy().getName() + "の攻撃！\n" +
                                damage.toString() + "のダメージを受けた"
        }
        updateStatus()
    }

    // 回復
    private fun cure() {
        var cured : Int = this.battle.cure()
        if ( cured == 0 ) {
            txvw_mainMsg.text = "回復に失敗した…"
        } else {
            txvw_mainMsg.text = "HPを" + cured.toString() + "回復した！"
        }
        updateStatus()
    }

    // セーブしてバトル終了
    private fun endBattleActivity() {
        pushDataBase()
        Log.d("move", "[debug] move to previous activity")
        finish()
    }

    // ステータスを更新
    private fun updateStatus() {
        txvw_sttsLv.text = this.player.getLv().toString()
        txvw_sttsHp.text = this.player.getHp().toString()
        txvw_sttsMp.text = this.player.getMp().toString()
        txvw_enemyLv.text = this.battle.enemy.getLv().toString()
        txvw_enemyName.text = this.battle.enemy.getName()
        Log.d("print", "[debug] status text updated")
    }

    // ゲームが終了しているか判定
    private fun checkBattleEnd() : Boolean {
        if ( this.player.getHp() == 0 || this.battle.enemy.getHp() == 0 ) {
            Log.d("event", "[debug] battle ends")
            return true
        } else {
            Log.d("event", "[debug] continue battle")
            return false
        }
    }

    // バトルの終わり方（勝利or敗北）を判定するメソッド
    private fun battleEnd() {
        if ( this.player.getHp() == 0 ) {
            die()
        } else if ( this.battle.enemy.getHp() == 0 ) {
            win()
        }
    }

    // プレイヤーが死んだ時の処理
    private fun die() {
        Log.d("event", "[debug] die event called")
        val msg = "あなたはしんでしまった…\n\n・プレイヤーLvが1下がります\n・手持ちのGoldを全て没収します\n\nマップに戻ります"
        this.player.lvDown()
        this.player.changeGold(-9999)
        this.player.rest()
        displayForceBtn(msg)
    }

    // バトル後（勝利）の一連の処理
    private fun win() {
        Log.d("event", "[debug] win event called")
        val msg : String = "勝利！\n\n" +
                            this.battle.enemy.getExp().toString() + "ポイントの経験値を獲得した！\n" +
                            this.battle.enemy.getGold().toString() + "ゴールド手に入れた！"
        this.player.changeExp(this.battle.enemy.getExp())  // 敵の持つExpを加算
        this.player.checkLv()  // 経験値累計を元にレベルを変更
        this.player.changeGold(this.battle.enemy.getGold())    // 敵の持つゴールドを加算
        displayForceBtn(msg)
    }

    // データベースからセーブデータを受け取る
    private fun pullDataBase() {
        // SharedPreferencesのインスタンスを取得
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        // Gsonクラスのインスタンスを用意しておく
        val gson: Gson = Gson()

        var saveDataStr = ""
        pref.apply { saveDataStr = getString("saveData", "") }

        if ( saveDataStr == "" ) {    // セーブデータがなかった場合
                Log.d("save", "[debug] there is not save date")
                Log.d("move", "[debug] move to previous activity")
            finish()    // そんなわけないので強制終了
        } else {
                Log.d("save", "[debug] date loaded successfully!")
                Log.d("save", "[debug] the retrieved JSON data is :" + saveDataStr)
            // JSON文字列をGSONで取り出し、Playerクラスに変換
            this.player = gson.fromJson(saveDataStr, Player::class.java)
            this.player.checkLv()    // 経験値を元にレベルを変更
            Log.d("save", "[debug] comfirm player data :\n"
                    + "playerLv: " + this.player.getLv() + "\n"
                    + "playerHp: " + this.player.getHp() + "\n"
                    + "playerMp: " + this.player.getMp() + "\n"
                    + "playerExp: " + this.player.getExp() + "\n"
                    + "playerGold: " + this.player.getGold() )
        }
    }

    // データベースにセーブデータを保存する
    private fun pushDataBase() {
        // SharedPreferencesのインスタンスを取得
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()
        // Gsonクラスのインスタンスを用意しておく
        val gson: Gson = Gson()

        // playerインスタンスをJSON文字列に変換
        val saveDataStr = gson.toJson(this.player)
            Log.d("save", "JSON data to save : " + saveDataStr)

        // データベースに保存
        editor.putString("saveData", saveDataStr).apply()

        // 本当に保存されたか確認（デバッグ用）
        var saveDataStrComfirm : String = ""
        pref.apply { saveDataStrComfirm = getString("saveData", "") }
        if ( saveDataStrComfirm == "" ) {    // セーブデータがなかった場合
            Log.d("save", "[debug] failed to save data")
        } else {
            Log.d("save", "[debug] date saved successfully!")
        }
    }

    // 他の操作を不可とする強制ボタンを表示する（タップでメイン画面に移動）
    private fun displayForceBtn(msg : String) {
        btn_force.text = msg
        btn_force.visibility = View.VISIBLE // 強制ボタンを表示
        Log.d("print", "[debug] forced screen displayed")
    }

    // 全てのメッセージ表示をnullにする
    private fun clearMsg() {
        txvw_mainMsg.text = null
        txvw_subMsg.text = null
        txvw_supplementMsg.text = null
        btn_force.text = null
        Log.d("print", "[debug] clear all text")
    }

    private fun apperEnemy() {
        txvw_mainMsg.text = this.battle.enemy.getName() + "があらわれた！"
    }

}
