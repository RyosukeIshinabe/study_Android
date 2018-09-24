package com.gudaletsu.basicrpg

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var player : Player = Player()
    private val field : Field = Field()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        updateStatus()  // ステータスを更新
        createMap() // マップを更新
        playerDrawing() // プレイヤーの画像を描画

        // 十字キーを触れた時の処理
        btn_moveUp.setOnClickListener {
            Log.d("onClick", "[debug] up key clicked")
            // 移動メソッドでtrueが返って来れば移動成功
            if ( this.field.walkPlayer(0) ) {
                // 現在位置からみてY軸方向+1のマスを再描画
                reDrowingMap((this.field.returnPlayerPosision(false)+1),this.field.returnPlayerPosision(true))
                playerDrawing() // プレイヤーの画像を再描画

                // エンカウント判定でtrueが返って来ればエンカウント
                if ( this.field.randomEncounter() ) {
                    Log.d("event", "[debug] encounted!")
                    // バトルアクティビティに移動
                    battleStart()
                } else {
                    Log.d("event", "[debug] not encounted")
                }
            }
        }
        btn_moveDown.setOnClickListener {
            Log.d("onClick", "[debug] down key clicked")
            if ( this.field.walkPlayer(2) ) {
                reDrowingMap((this.field.returnPlayerPosision(false)-1),this.field.returnPlayerPosision(true))
                playerDrawing()
                if ( this.field.randomEncounter() ) {
                    Log.d("event", "[debug] encounted!")
                    battleStart()
                } else {
                    Log.d("event", "[debug] not encounted")
                }
            }
        }
        btn_moveRight.setOnClickListener {
            Log.d("onClick", "[debug] right key clicked")
            if ( this.field.walkPlayer(1) ) {
                reDrowingMap(this.field.returnPlayerPosision(false),(this.field.returnPlayerPosision(true)-1))
                playerDrawing()
                if ( this.field.randomEncounter() ) {
                    Log.d("event", "[debug] encounted!")
                    battleStart()
                } else {
                    Log.d("event", "[debug] not encounted")
                }
            }
        }
        btn_moveLeft.setOnClickListener {
            Log.d("onClick", "[debug] left key clicked")
            if ( this.field.walkPlayer(3) ) {
                reDrowingMap(this.field.returnPlayerPosision(false),(this.field.returnPlayerPosision(true)+1))
                playerDrawing()
                if ( this.field.randomEncounter() ) {
                    Log.d("event", "[debug] encounted!")
                    battleStart()
                } else {
                    Log.d("event", "[debug] not encounted")
                }
            }
        }

        /*
        デバッグ用
        */
        // プレイヤーレベルを操作する
        debugIcon_up.setOnClickListener {
            debugPlayerLvControl(1)
        }
        debugIcon_down.setOnClickListener {
            debugPlayerLvControl(-1)
        }

    }

    @Override   // このアクティビティが再表示される度に読み込まれる処理
    override fun onResume() {
        super.onResume()    // これはおまじない（定型文）
        Log.d("onResume", "[debug] onResume main activity")

        // セーブデータを取得
        pullDataBase()

        // ステータスを更新
        updateStatus()
    }

    // ステータスを更新
    private fun updateStatus() {
        txvw_sttsName.text = this.player.getName().toString()
        txvw_sttsLv.text = this.player.getLv().toString()
        txvw_sttsHp.text = this.player.getHp().toString()
        txvw_sttsMp.text = this.player.getMp().toString()
        txvw_sttsAtk.text = this.player.getAtk().toString()
        txvw_sttsDef.text = this.player.getDef().toString()
        txvw_sttsGold.text = this.player.getGold().toString()
        txvw_sttsExp.text = this.player.getExp().toString()
        Log.d("print", "[debug] updated status text")
    }

    // ゲームスタートメソッド
    private fun battleStart() {
        // playerデータをデータベースに保存
        pushDataBase()
        // BattleActivityを開く
        val intent = Intent(this, BattleActivity::class.java)
        Log.d("move", "[debug] move to battle activity...")
        startActivity(intent)
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
                Log.d("setUp", "[debug] beginning first set-up of player instance")
            this.player = Player()   // Playerインスタンスを新規作成
        } else {
                Log.d("save", "[debug] date loaded successfully!")
                Log.d("save", "the retrieved JSON data is :\n" + saveDataStr)
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

    /*
        デバッグ用
     */
    // playerレベルを操作する（上げる場合は引数に正の値を、下げる場合は負の値を）
    private fun debugPlayerLvControl(amount: Int) {
            Log.d("debug", "[debug] level controller clicked")
        var addExp = this.player.getNecessaryExpForNextLevel(amount)
            Log.d("debug", "[debug] exp added : " + addExp.toString())
        this.player.changeExp(addExp)
        this.player.checkLv()    // 経験値を元にレベルを変更
        updateStatus()  // ステータスを更新
    }

    // プレイヤーアイコンの位置を移動する：現在は不使用
//    private fun translationIcon(upDown: Boolean, amount: Int) {
//        if (upDown) {   // 上下の移動
//            if (icon_chara.translationY > 960 && amount > 0) {
//                Log.d("move", "[debug] It can't move any further")
//            } else if (icon_chara.translationY < 80 && amount < 0) {
//                Log.d("move", "[debug] It can't move any further")
//            } else {
//                icon_chara.translationY += amount
//            }
//        } else {    // 左右の移動
//            if (icon_chara.translationX > 960 && amount > 0) {
//                Log.d("move", "[debug] It can't move any further")
//            } else if (icon_chara.translationX < 80 && amount < 0) {
//                Log.d("move", "[debug] It can't move any further")
//            } else {
//                icon_chara.translationX += amount
//            }
//        }
//    }

    // マップを取得する
    private fun createMap() {
        Log.d("set-up", "[debug] createMap called")

        // 地形画像のリソースIDを取得
        val rsidSea = resources.getIdentifier("maptile_sea", "drawable", packageName)
        val rsidGrass = resources.getIdentifier("maptile_grass", "drawable", packageName)

        // マップの数だけ繰り返す
        for (i in 0 until Field.COLUMN) {
            for (j in 0 until Field.ROW) {

                // 地形の値を取得　0=海　1=草原
                val terrain = this.field.getTerrain(i, j)
                Log.d("set-up", "[debug] map[" + i + "],[" + j + "]" + " = " + terrain)

                // マップ1マス分のviewIDの名前（String）を取得（mapA1、mapC13など）
                val mapName = "map" + this.field.getTerrainString(i, j)
                Log.d("set-up", "[debug] acquired mapName is ; " + mapName)

                // 取得したviewIDの名前がついたリソースを調べ、リソースIDを特定
                val rsid = resources.getIdentifier(mapName, "id", packageName)
                Log.d("set-up", "[debug] acquired resouceID is ; " + rsid)

                // 存在しないviewIDもしくはmapNameが出てきたらbreak
                if ( rsid == 0 || mapName == "" ) { break; }

                // 特定したリソースIDを持つイメージビューを生成
                var imageView = findViewById(rsid) as ImageView

                // イメージビューに地形の画像をセット
                if (terrain == 0) {
                    imageView.setImageResource(rsidSea)
                } else if (terrain == 1) {
                    imageView.setImageResource(rsidGrass)
                }
            }
        }
    }

    // 特定の位置のマスだけを再描画する（プレイヤーが通り過ぎたマス用）
    private fun reDrowingMap(y:Int, x:Int) {
        Log.d("set-up", "[debug] re-drowingMap called")
        // 地形画像のリソースIDを取得
        val rsidGrass = resources.getIdentifier("maptile_grass", "drawable", packageName)
        // マップ1マス分のviewIDの名前（String）を取得（mapA1、mapC13など）
        val mapName = "map" + this.field.getTerrainString(y, x)
        Log.d("set-up", "[debug] acquired mapName is ; " + mapName)
        // 取得したviewIDの名前がついたリソースを調べ、リソースIDを特定
        val rsid = resources.getIdentifier(mapName, "id", packageName)
        Log.d("set-up", "[debug] acquired resouceID is ; " + rsid)

        // 存在しないviewIDもしくはmapNameが出てこなければ続行
        if ( rsid != 0 || mapName != "" ) {
            // 特定したリソースIDを持つイメージビューを生成
            var imageView = findViewById(rsid) as ImageView
            // イメージビューに地形の画像をセット
            imageView.setImageResource(rsidGrass)
        }
    }

    // プレイヤーの位置を特定して描画する
    private fun playerDrawing() {
        Log.d("move", "[debug] playerDrawing called")

        // プレイヤー画像のリソースIDを取得
        val rsidPlayer = resources.getIdentifier("chara_king_with_bg", "drawable", packageName)

        // プレイヤーの現在位置を取得
        val currentX = this.field.returnPlayerPosision(true)
        val currentY = this.field.returnPlayerPosision(false)
        Log.d("move", "[debug] player's position is : [" + currentX + "][" + currentY + "]")

        // プレイヤーの現在位置にあるviewIDの名前（String）を取得（mapA1、mapC13など）
        val mapName = "map" + this.field.getTerrainString(currentY, currentX)
        Log.d("move", "[debug] acquired mapName is ; " + mapName)

        // 取得したviewIDの名前がついたリソースを調べ、リソースIDを特定
        val rsid = resources.getIdentifier(mapName, "id", packageName)
        Log.d("move", "[debug] acquired resouceID is ; " + rsid)

        // 存在しないviewIDもしくはmapNameが出てこなければ
        if ( rsid != 0 || mapName != "" ) {
            // 特定したリソースIDを持つイメージビューを生成し、画像をセット
            var imageView = findViewById(rsid) as ImageView
            imageView.setImageResource(rsidPlayer)
        }
    }



}
