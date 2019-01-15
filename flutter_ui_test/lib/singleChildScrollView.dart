import 'package:flutter/material.dart';

// singleChildScrollViewのテストです。
// singleChildScrollViewは、1つのウィジェットを内部に持てるコンテナです。
// 項目が増えて表示しきれなくなっても、内部のウィジェットの幅に応じて自動でスクロール対応します。

class SingleChildScrollViewTest extends StatelessWidget {

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
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('App Name'),
      ),

      body: SingleChildScrollView(

        // 縦方向か横方向かを指定する。横の場合はhorizontal
        scrollDirection: Axis.vertical,

        // 組み込むウィジェットはchildで用意。今回はカラム
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceAround,
          mainAxisSize: MainAxisSize.min,
          children: <Widget>[

            // 試しに色付きのタイルを並べてみる
            Container(
              color: Colors.lightBlue[50],
              height: 120.0,
              child: const Center(
                child: Text(
                  'One',
                  style: const TextStyle(fontSize: 32.0),
                )
              ),
            ),
            Container(
              color: Colors.lightBlue[100],
              height: 120.0,
              child: const Center(
                  child: Text(
                    'Two',
                    style: const TextStyle(fontSize: 32.0),
                  )
              ),
            ),
            Container(
              color: Colors.lightBlue[200],
              height: 120.0,
              child: const Center(
                  child: Text(
                    'Three',
                    style: const TextStyle(fontSize: 32.0),
                  )
              ),
            ),
            Container(
              color: Colors.lightBlue[300],
              height: 120.0,
              child: const Center(
                  child: Text(
                    'Four',
                    style: const TextStyle(fontSize: 32.0),
                  )
              ),
            ),
            Container(
              color: Colors.lightBlue[400],
              height: 120.0,
              child: const Center(
                  child: Text(
                    'Five',
                    style: const TextStyle(fontSize: 32.0),
                  )
              ),
            ),
            Container(
              color: Colors.lightBlue[500],
              height: 120.0,
              child: const Center(
                  child: Text(
                    'Six',
                    style: const TextStyle(fontSize: 32.0),
                  )
              ),
            ),
            Container(
              color: Colors.lightBlue[600],
              height: 120.0,
              child: const Center(
                  child: Text(
                    'Seven',
                    style: const TextStyle(fontSize: 32.0),
                  )
              ),
            ),
            Container(
              color: Colors.lightBlue[700],
              height: 120.0,
              child: const Center(
                  child: Text(
                    'Eight',
                    style: const TextStyle(fontSize: 32.0),
                  )
              ),
            ),
            Container(
              color: Colors.lightBlue[800],
              height: 120.0,
              child: const Center(
                  child: Text(
                    'Nine',
                    style: const TextStyle(fontSize: 32.0),
                  )
              ),
            ),
            Container(
              color: Colors.lightBlue[900],
              height: 120.0,
              child: const Center(
                  child: Text(
                    'Ten',
                    style: const TextStyle(fontSize: 32.0),
                  )
              ),
            ),
          ]
        ),
      ),
    );
  }
}