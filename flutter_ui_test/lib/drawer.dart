import 'package:flutter/material.dart';

// Drawerのテストです。
// Drawerは、画面左上のハンバーガーアイコンをタップすると左からメニューが出てくるよくあるやつです。
// Scaffoldにはdrawerプロパティがあり、ここにDrawerウィジェットを指定します。

class DrawerTest extends StatelessWidget {

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

class _MyHomePageState extends State<MyHomePage> {

  List _items = <Widget>[]; // メニュー用のリスト
  String _message;
  int _tapped = 0;

  void initState() {
    super.initState();
    _message = 'ready.';

    // メニュー用のリストを作成。リストはListTileクラスを使用
    for (var i = 0; i < 5; i++ ) {
      var item = ListTile(
        leading: const Icon(Icons.android),
        title: Text('No, $i'),
        // onTap：タップした時のメソッドを指定
        // ここではメンバ変数_tappedにリスト番号を代入し、tapItem()メソッドを呼び出し
        onTap: (){
          _tapped = i;
          tapItem();
        }
      );
      _items.add(item);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(

      appBar: AppBar(
        title: Text('App Name'),
      ),

      body: Center(
        child: Text(
          _message,
          style: TextStyle(
            fontSize: 32.0,
          ),
        ),
      ),

      // ドロワーはこのようにScaffold自体のプロパティで指定する
      // childにはListViewを指定する
      drawer: Drawer(
        child: ListView(
          shrinkWrap: true,
          padding: const EdgeInsets.all(20.0),
          children: _items,
        ),
      ),
    );
  }

  void tapItem() {
    // Navigator.pop(context)で、現れたドロワーを閉じる
    // 前の状態に戻す時にNavigator.popは多用される
    Navigator.pop(context);
    setState(() {
      _message = 'tapped:[$_tapped]';
    });
  }
}