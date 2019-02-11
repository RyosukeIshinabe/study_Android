import 'package:flutter/material.dart';
import 'style.dart';
import 'home.dart';
import 'favorite.dart';
import 'history.dart';
import 'settings.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key}) : super(key: key);
  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _menuIndex = 0; // 現在選択されているメニュー番号

  // 初回だけ呼ばれる処理
  void initState() {
    super.initState();
    _menuIndex = 0; // 最も左のメニューを表示
  }

  @override
  Widget build(BuildContext context) {

    // メニュー番号に応じて表示するコンテンツを取得
    Center mainContents = new Center();
    if ( _menuIndex == 1 ) {
      mainContents = Favorite.favoriteContents;
    } else if ( _menuIndex == 2 ) {
      mainContents = History.historyContents;
    } else if ( _menuIndex == 3 ) {
      mainContents = Settings.settingsContents;
    } else {
      mainContents = Home.homeContents;
    }

    return Scaffold(
      backgroundColor: AppStyle.bgColor,
      appBar: AppBar(
        title: Text("テストアプリ"),
      ),

      // 予めメニュー番号に応じて用意しておいたコンテンツを表示
      body: mainContents,

      // 下部メニュー
      bottomNavigationBar: new BottomNavigationBar(
        // 現在選択されている項目のインデックス。これに設定されたインデックス（int）のアイコンが選択状態で表示される
        currentIndex: _menuIndex,
        // itemsに全てのメニューアイコンが格納される
        items: <BottomNavigationBarItem>[
          // BottomNavigationBarItemウィジェットでアイコンとタイトルを指定する
          // タップした時のメソッドはアイコンごとに個別に指定はできず、bottomNavigationBar内のonTapプロパティで一括で指定するしかない
          BottomNavigationBarItem(
            icon: const Icon(Icons.home),
            title: Text('Home', style: AppStyle.smallTextStyle,),
          ),
          BottomNavigationBarItem(
            icon: const Icon(Icons.favorite),
            title: Text('Favorite', style: AppStyle.smallTextStyle,),
          ),
          BottomNavigationBarItem(
            icon: const Icon(Icons.history),
            title: Text('History', style: AppStyle.smallTextStyle,),
          ),
          BottomNavigationBarItem(
            icon: const Icon(Icons.settings),
            title: Text('Settings', style: AppStyle.smallTextStyle,),
          ),
        ],

        // タップされた時に呼ばれるメソッドを指定する。全てのアイコンで呼ばれるため、動作を変えたい場合はメンバ変数を使うこと
        // 引数には（ここには何も書かれていないが）、タップされたアイコンのインデックス番号（0〜）が自動で渡される
        onTap: tapBottomIcon,
      ),
    );
  }

  // 下部メニューがタップされた時に呼ばれるメソッド
  void tapBottomIcon(int value) {
    setState(() {
      _menuIndex = value;
    });
  }
}
