package com.gudaletsu.lightgame

import android.content.Intent
import android.media.AudioManager
import android.media.SoundPool
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_main.*
import android.media.AudioAttributes
import android.os.Handler
import android.support.v4.os.HandlerCompat.postDelayed


class GameActivity : AppCompatActivity() {

    private val light001 = Light()
    private val light002 = Light()
    private val light003 = Light()
    private var counter = 0
    private val perfectScore = 3
    private lateinit var soundPool: SoundPool   // 効果音再生用
    private var switchSound: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        /*
            サウンドの再生準備
         */
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
            .build()

        soundPool = SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            .setMaxStreams(2)
            .build()

        switchSound = soundPool.load(this, R.raw.switchsound, 1)

        /*
            スイッチが押された時の記述
         */
        btn_switch01.setOnClickListener {
            // play(ロードしたID, 左音量, 右音量, 優先度, ループ,再生速度)
            soundPool.play(switchSound, 1.0f, 1.0f, 0, 0, 1.0f)
            light001.switchLight()
            light003.switchLight()
            reloadAllLightImage()
            countUp()
            judge()
        }
        btn_switch02.setOnClickListener {
            soundPool.play(switchSound, 1.0f, 1.0f, 0, 0, 1.0f)
            light002.switchLight()
            light003.switchLight()
            reloadAllLightImage()
            countUp()
            judge()
        }
        btn_switch03.setOnClickListener {
            soundPool.play(switchSound, 1.0f, 1.0f, 0, 0, 1.0f)
            light003.switchLight()
            reloadAllLightImage()
            countUp()
            judge()
        }

        /*
            その他ボタンの挙動を記述
         */
        btn_cleared.setOnClickListener {
            save()
            finish()
        }

        btn_back.setOnClickListener {
            finish()
        }

        reloadAllLightImage()  // すべての電球の画像を更新
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        soundPool.release()
    }

    // 電球の画像を更新
    public fun reloadAllLightImage() {
        if ( light001.isLightsOn ) {
            imgvw_light01.setImageResource(R.drawable.right_on)
        } else {
            imgvw_light01.setImageResource(R.drawable.right_off)
        }
        if ( light002.isLightsOn ) {
            imgvw_light02.setImageResource(R.drawable.right_on)
        } else {
            imgvw_light02.setImageResource(R.drawable.right_off)
        }
        if ( light003.isLightsOn ) {
            imgvw_light03.setImageResource(R.drawable.right_on)
        } else {
            imgvw_light03.setImageResource(R.drawable.right_off)
        }
    }

    // カウンターを更新
    public fun countUp() {
        counter++
        txtvw_counter.text = counter.toString()
    }

    // クリアしたかどうか判定
    public fun judge() {
        if ( light001.isLightsOn && light002.isLightsOn && light003.isLightsOn ) {
            btn_cleared.visibility = View.VISIBLE
            txtvw_cleared.visibility = View.VISIBLE
        }
    }

    // セーブする。最短タップ数でクリアした場合、共有プリファレンスに2を保存。単にクリアした場合1を保存する。
    private fun save() {
        if ( counter <= perfectScore ) {
            // 共有プリファレンスに記録（まとめて書いてみた）
            PreferenceManager.getDefaultSharedPreferences(this)
                .edit() // 編集するおまじない
                .putInt("1", 2)    // データを書き込み
                .apply()    // 実行するおまじない
        } else {
            // 共有プリファレンスに記録（まとめて書いてみた）
            PreferenceManager.getDefaultSharedPreferences(this)
                .edit() // 編集するおまじない
                .putInt("1", 1)    // データを書き込み
                .apply()    // 実行するおまじない
        }
    }

}
