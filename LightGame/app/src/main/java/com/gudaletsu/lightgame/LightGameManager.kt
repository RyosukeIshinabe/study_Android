package com.gudaletsu.lightgame

/**
 * ゲーム画面上のオブジェクト
 * @property x 位置座標 x
 * @property y 位置座標 y
 */
sealed class Object(val x: Int, val y: Int) {
    /**
     * ランプ
     * @property isOn 点灯しているか
     */
    class Lamp(x: Int, y: Int) : Object(x, y) {
        var isOn = false

        /** ランプの状態を切り替える */
        fun switch() {
            isOn = !isOn
        }
    }

    /**
     * スイッチ
     * @property connectLamps 接続しているランプ
     */
    class Switch(x: Int, y: Int, private val connectLamps: Set<Lamp>) : Object(x, y) {
        /** 接続しているランプのスイッチを切り替える */
        fun switch() {
            connectLamps.forEach {
                it.switch()
            }
        }
    }

    /** ケーブル */
    sealed class Cable(x: Int, y: Int) : Object(x, y) {
        class HorizontalCable(x: Int, y: Int) : Cable(x, y)
        class VerticalCable(x: Int, y: Int) : Cable(x, y)
        class LeftUpCable(x: Int, y: Int) : Cable(x, y)
        class LeftToDownCable(x: Int, y: Int) : Cable(x, y)
        class RightUpCable(x: Int, y: Int) : Cable(x, y)
        class RightDownCable(x: Int, y: Int) : Cable(x, y)
    }
}

/**
 * ゲームマネージャー
 * @param stageData ステージデータ
 */
class LightGameManager(stageData: StageData) {
    /** セルエリアの横のセルの数 */
    val numCellCol = stageData.numCellCol
    /** セルエリアの縦のセルの数 */
    val numCellRow = stageData.numCellRow

    /** ステージi上のランプのセット */
    private val lamps = stageData.objects.filter { it is Object.Lamp }.map { it as Object.Lamp }
    /** ステージi上のスイッチのセット */
    private val switchs = stageData.objects.filter { it is Object.Switch }.map { it as Object.Switch }
    /** ステージi上のケーブルのセット */
    private val cables = stageData.objects.filter { it is Object.Cable }.map { it as Object.Cable }

    /** クリア状態か */
    val isClear get() = lamps.all { it.isOn }

    /** ステージ上のオブジェクトのセットを返す */
    val objects get(): Set<Object> = mutableSetOf<Object>().apply {
        addAll(lamps)
        addAll(switchs)
        addAll(cables)
    }
}

/**
 * ステージデータ
 * @property numCellCol セルエリアの横のセルの数
 * @property numCellRow セルエリアの縦のセルの数
 * @property objects オブジェクトのセット
 */
data class StageData(val numCellCol: Int, val numCellRow: Int, val objects: Set<Object>)

/** テストステージデータを取得する */
fun getTestStageData(): StageData {
    val numCellCol = 4
    val numCellRow = 7

    val lamp1 = Object.Lamp(0, 0)
    val lamp2 = Object.Lamp(1, 3)
    val lamp3 = Object.Lamp(0, 6)
    val lamps = listOf(lamp1, lamp2, lamp3)

    val switchs = listOf(
        Object.Switch(0, 2, setOf(lamp1, lamp2)),
        Object.Switch(3, 3, setOf(lamp2)),
        Object.Switch(0, 4, setOf(lamp2, lamp3))
    )

    val cables = listOf(
        Object.Cable.VerticalCable(0, 1),
        Object.Cable.LeftToDownCable(1, 2),
        Object.Cable.HorizontalCable(2, 3),
        Object.Cable.LeftUpCable(1, 4),
        Object.Cable.VerticalCable(0, 5)
    )

    val objects = mutableSetOf<Object>().apply {
        addAll(lamps)
        addAll(switchs)
        addAll(cables)
    }

    return StageData(numCellCol, numCellRow, objects)
}