import 'package:flutter/material.dart';

// PopupMenuButtonのテストです。
// PopupMenuButtonは、画面右上によくある[…]を縦にしたマークのボタンです。
// 通常はAppBarなどに配置するのが一般的ですが、使い方の確認としてみてみます。

class PopupMenuButtonTest extends StatelessWidget {

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

  String _message;
  String _selected = 'One';

  // 最初だけ呼ばれるメソッド
  void initState() {
    _message = 'ready.';
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        title: new Text('App Name'),
      ),
      body:
      new Column(
          mainAxisAlignment: MainAxisAlignment.start,
          mainAxisSize: MainAxisSize.max,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[

            new Text(
              _message,
              style: new TextStyle(fontSize:32.0,
                  fontWeight: FontWeight.w400,
                  fontFamily: "Roboto"),
            ),

            // レイアウト調整のために空白のみを表示
            new Padding(
              padding: EdgeInsets.all(10.0),
            ),

            Align(
              alignment: Alignment.centerRight,
              child: PopupMenuButton(
                onSelected: (String value) => popupSelected(value),

                // 実際のリストはここで作成。BuildContextのインスタンスをPopupMenuEntryに渡している
                itemBuilder: (BuildContext context) => <PopupMenuEntry<String>>[
                  const PopupMenuItem(child: const Text("One"), value: "One",),
                  const PopupMenuItem(child: const Text("Two"), value: "Two",),
                  const PopupMenuItem(child: const Text("Three"), value: "Three",),
                ]
              ),
            )
          ]
      ),
    );
  }

  void popupSelected(String value) {
    setState(() {
      _selected = value;
      _message = 'select $_selected';
    });
  }

}