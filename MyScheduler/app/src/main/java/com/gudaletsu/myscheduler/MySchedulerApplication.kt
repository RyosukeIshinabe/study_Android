package com.gudaletsu.myscheduler

import android.app.Application
import io.realm.Realm

// Realmを使用する場合、データベースの設定処理がアプリケーション起動時に実行される必要がある
// そこで、アプリケーション実行時に行いたい処理をApplicationクラスを継承したクラスを用意してマニフェストXMLファイル追記する

class MySchedulerApplication : Application() {

    // ApplicationクラスのonCreateメソッドは、アプリの実行時、アクティビティよりも先に呼ばれる
    override fun onCreate() {
        super.onCreate()

        // Realmを初期化（引数には、Applicationを継承しているクラス内ならthisを指定）
        Realm.init(this)

        // このままでは実行されません。この後、AndroidManifests.xmlの application内に
        // android:name=".MySchedulerApplication" を書き足してください。

    }
}