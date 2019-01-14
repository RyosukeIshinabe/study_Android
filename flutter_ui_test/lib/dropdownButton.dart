import 'package:flutter/material.dart';

// DropdownButtonのテストです。
// DropdownButtonは、プルダウンメニューです。
// ここでは、選択した文字列を別の場所のテキストビューに表示させてみます。

class DropdownButtonTest extends StatelessWidget {

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

            // DropdownButtonウィジェット
            // onChangedは、選択が変更されるたびに呼ばれるメソッド
            // ここでは、選択した文字列をそのままpopupSelectedに渡している
            new DropdownButton<String>(
              onChanged: (String value) => popupSelected(value),
              value: _selected,
              style: new TextStyle(
                  fontSize:28.0,
                  color: Colors.black,
                  fontWeight: FontWeight.w400,
                  fontFamily: "Roboto"),
              items: <DropdownMenuItem<String>>[
                const DropdownMenuItem<String>(
                    // valueで値を指定し、childでメニュー内に組み込まれるウィジェットを指定する（ここではtextだが、アイコンなどでもOK）
                    value: "One",
                    child: const Text("One")),
                const DropdownMenuItem<String>(
                    value: "Two",
                    child: const Text("Two")),
                const DropdownMenuItem<String>(
                    value: "Three",
                    child: const Text("Three")),
              ],
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