package com.gudaletsu.myscheduler

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

// データを格納するモデルを作成する（要はデータベースのレコード。これを1つ作れば1行、複数作ってテーブルにする）
// RealmのモデルはRealmObjectクラスを継承して作成する
// この中にメンバ変数を設定し、それがフィールド（列）になる

// openを忘れずに
open class Schedule : RealmObject() {
    @PrimaryKey // プライマリーキー（ユニーク）を設定
    var id: Long = 0    // idの列
    var date: Date = Date() // 日付の列
    var title: String = ""  // スケジュールのタイトル
    var detail: String = "" // スケジュールの詳細
}