package com.gudaletsu.basicrpg

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var player : Player
    private val field : Field = Field()
    private val battle : Battle = Battle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // セーブデータを取得
        pullDataBese()
        if ( player == null ) {
            player = Player()
        }

        // ステータスを更新
        updateStatus()

        // 十字キーを触れた時の処理
        btn_moveUp.setOnClickListener {
            // キャラクターを移動させる
            // あとでかく
            // エンカウント判定
            if ( this.field.randomEncounter() ) {
                // バトルアクティビティに移動
                battleStart()
            }
        }
        btn_moveDown.setOnClickListener {
            // キャラクターを移動させる
            // あとでかく
            // エンカウント判定
            if ( this.field.randomEncounter() ) {
                // バトルアクティビティに移動
                battleStart()
            }
        }
        btn_moveRight.setOnClickListener {
            // キャラクターを移動させる
            // あとでかく
            // エンカウント判定
            if ( this.field.randomEncounter() ) {
                // バトルアクティビティに移動
                battleStart()
            }
        }
        btn_moveLeft.setOnClickListener {
            // キャラクターを移動させる
            // あとでかく
            // エンカウント判定
            if ( this.field.randomEncounter() ) {
                // バトルアクティビティに移動
                battleStart()
            }
        }
    }

    @Override   // このアクティビティが再表示される度に読み込まれる処理
    override fun onResume() {
        super.onResume()    // これはおまじない（定型文）

        // セーブデータを取得
        pullDataBese()
        if ( player == null ) {
            player = Player()
        }

        // ステータスを更新
        updateStatus()
    }

    // ステータスを更新
    private fun updateStatus() {
        txvw_sttsLv.text = player.getLv().toString()
        txvw_sttsHp.text = player.getHp().toString()
        txvw_sttsMp.text = player.getMp().toString()
        txvw_sttsAtk.text = player.getAtk().toString()
        txvw_sttsDef.text = player.getDef().toString()
        txvw_sttsGold.text = player.getGold().toString()
        txvw_sttsExp.text = player.getExp().toString()
    }

    // ゲームスタートメソッド
    private fun battleStart() {
        // playerデータをデータベースに保存
        pushDataBese()
        // BattleActivityを開く
        startActivity(intent)
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




}
