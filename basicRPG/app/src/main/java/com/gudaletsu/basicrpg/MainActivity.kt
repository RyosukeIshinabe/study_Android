package com.gudaletsu.basicrpg

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val player : Player = Player()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ステータスを更新
        updateStatus()

        // 十字キーを触れた時の処理
        btn_moveUp.setOnClickListener {
        }
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


}
