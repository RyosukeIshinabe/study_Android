package com.gudaletsu.lightgame

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_sample_light_game.*
import java.lang.Math.min

// 用語
// セル: ゲーム上のオブジェクト(ランプ、スイッチ、ケーブル)を表示するViewのこと

// TODO レイアウトが適当。セル間のmarginとかもない。そこらへんなんとかしたい。

/** ゲーム画面（サンプル） */
class SampleLightGameActivity : AppCompatActivity() {
    /** ゲームマネージャー */
    private val gameManager = LightGameManager(getTestStageData())

    /** セルの大きさ */
    private var cellSize = 0
    /** セルエリアの左マージン */
    private var cellAreaMarginLeft = 0
    /** セルエリアの上マージン */
    private var cellAreaMarginTop = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_light_game)

        objectFrameView.post {
            // ここはobjectFrameViewの準備後(Viewサイズとかが設定された後)に呼ばれる。
            // サイズ情報を元にセルの大きさを計算する

            // セルを配置するviewのサイズ
            val frameWidth = objectFrameView.width
            val frameHeight = objectFrameView.height

            cellSize = min(frameWidth / gameManager.numCellCol, frameHeight / gameManager.numCellRow)
            // セルエリアが画面中央になるようにマージンを計算する
            cellAreaMarginLeft = (frameWidth - (gameManager.numCellCol * cellSize)) / 2
            cellAreaMarginTop = (frameHeight - (gameManager.numCellRow * cellSize)) / 2

            updateState()
        }
    }

    /**
     * ゲームの状態に応じてviewを更新する
     * (やり方がFlutterぽいが、適切かわからない)
     */
    private fun updateState() {
        // クリア状態であればクリアラベルを表示する
        clearLabel.visibility = if (gameManager.isClear) {
            View.VISIBLE
        } else {
            // TODO ここGONEにするとレイアウト崩れる、危ないので改善の必要ある
            View.INVISIBLE
        }

        // 過去のセルを一旦全部捨て、最新状態のセルを再度追加する
        objectFrameView.removeAllViews()
        gameManager.objects.forEach {
            val objectView = ImageView(this).apply {
                // セルをいい感じのポジションにする
                layoutParams = FrameLayout.LayoutParams(cellSize, cellSize).apply {
                    (this as ViewGroup.MarginLayoutParams).setMargins(
                        it.x * cellSize + cellAreaMarginLeft,
                        it.y * cellSize + cellAreaMarginTop,
                        0,
                        0
                    )
                }
            }
            when (it) {
                is Object.Lamp -> {
                    objectView.setBackgroundResource(if (it.isOn) R.drawable.right_on else R.drawable.right_off)
                    objectFrameView.addView(objectView)
                }
                is Object.Switch -> {
                    objectView.apply {
                        setBackgroundResource(R.drawable.lightswitch)
                        // クリア状態のときはスイッチを無効化する
                        if (!gameManager.isClear) {
                            setOnClickListener { _ ->
                                it.switch()
                                updateState()
                            }
                        }
                    }
                    objectFrameView.addView(objectView)
                }
                is Object.Cable -> {
                    val imageDrawableId = when (it) {
                        is Object.Cable.HorizontalCable -> R.drawable.line_horizontal
                        is Object.Cable.VerticalCable -> R.drawable.line_vertical
                        is Object.Cable.LeftUpCable -> R.drawable.line_corner_leftup
                        is Object.Cable.LeftToDownCable -> R.drawable.line_corner_leftdown
                        is Object.Cable.RightUpCable -> R.drawable.line_corner_rightup
                        is Object.Cable.RightDownCable -> R.drawable.line_corner_rightdown
                    }
                    objectView.setBackgroundResource(imageDrawableId)
                    objectFrameView.addView(objectView)
                }
            }
        }
    }
}
