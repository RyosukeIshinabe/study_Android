import 'package:flutter/material.dart';

// TabBarとTabBarViewのテストです。
// AppBarの下部にタブがあり、画面を左右にスワイプすることで画面が変わるウィジェットです。
// タブ部分がTabBar、タブと連動したコンテンツ部分がTabBarViewです。

class TabBarTest extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: 'Generated App',
      theme: new ThemeData(
        primarySwatch: Colors.blue,
        primaryColor: const Color(0xFF2196f3),
        accentColor: const Color(0xFF2196f3),
        canvasColor: const Color(0xFFfafafa),
      ),
      home: new MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key}) : super(key: key);
  @override
  _MyHomePageState createState() => new _MyHomePageState();
}

// SingleTickerProviderStateMixinは、Tickerを1つ持つTickerProviderのクラス
// ここでは、SingleTickerProviderStateMixinをミックスイン（他のクラスの機能を組み込む仕組み）している
// これにより、このクラス自身がTickerProviderとして使えるようになる
class _MyHomePageState extends State<MyHomePage> with SingleTickerProviderStateMixin {

  // TabBarに指定するテキストのリストを準備。テキストはTabクラスのtextプロパティで指定する
  final List<Tab> tabs = <Tab>[
    Tab(text: 'Recomend'),
    Tab(text: 'New'),
    Tab(text: 'Ranking'),
  ];

  // TabBarとTabBarViewを連動させるためのコントローラーを準備
  TabController _tabController;
  // ちなみに（本サンプルでは使用していないが）TabControllerには、現在選択されているタブのインデックスを示すindexプロパティがあり
  // これを使って現在のタブを調べ、タブに応じた処理を行うこともできる

  // 初回だけ呼ばれる初期化メソッド
  void initState() {
    super.initState();
    // 変数_tabControllerにTabControllerのコンストラクタを適用
    _tabController = TabController(
      // vsync：TickerProviderクラスのインスタンスを指定する
      // これはアニメーションのコールバック呼び出しに関するTickerクラスを生成するためのもので、
      // これを指定することでTabBarとTabBarViewの動きが連動できるようになる
      // ここでは、_MyHomePageStateクラス自身を指定している
      // （SingleTickerProviderStateMixinをミックスインしているため可能）
      vsync: this,
      // length：タブの数を指定する。ここでは予め作成したTabのListの要素数を指定
      length: tabs.length
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(

      //  TabBar部分は、AppBarのbottomプロパティに埋め込む
      appBar: AppBar(
        title: Text('App Name'),
        bottom: TabBar(
          // controller：TabControllerクラスのインスタンスを指定し、タブ操作の制御を担当する
          // ここで設定したものをTabBarViewの方にも設定することで操作が連動する
          controller: _tabController,
          // tabs：タブ部分に表示する文字を、TabクラスのListで指定する
          tabs: tabs,
        ),
      ),

      body: TabBarView(
        // controller：TabBarのcontrollerプロパティで指定したものを指定する
        controller: _tabController,
        // childrenに実際のコンテンツを指定する
        // ここでは、tabsのmapメソッドでコンテンツをIterable（繰り返し可能な）にまとめて生成している
        // mapメソッドは、引数に関数を指定することで、リストにある各要素に繰り返し関数を適用する
        // 要はtabsからtabインスタンスを1個ずつ取り出してcreateTabメソッドに渡し、
        // ウィジェットとして戻ってきたものをリストにまとめてchildrenに設定している
        children: tabs.map((Tab tab) {
          return createTab(tab);
        }).toList(),
      )
     );
  }

  // コンテンツを作成し、ウィジェットを返すメソッド
  Widget createTab(Tab tab) {
    return Center(
      child: Text(
        'this is ' + tab.text + ' Tab.',
        style: const TextStyle(
          fontSize: 32.0,
          color: Colors.blue
        ),
      ),
    );
  }
}