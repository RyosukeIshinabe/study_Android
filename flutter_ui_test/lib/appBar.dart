import 'package:flutter/material.dart';

// AppBarをカスタマイズするテストです。

class AppBarTest extends StatelessWidget {

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
  String _stars;
  int _star = 2;

  @override
  void initState() {
    _message = 'ok.';
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(

        // title：appBarに表示されるタイトルを表示する（通常はテキスト）
        title: Text('My App'),

        // leading：タイトルの左端に表示されるボタンやアイコン。戻るアイコンなど
        leading: BackButton(
          color: Colors.white,
        ),

        // actions：タイトルの右端に表示されるボタンやアイコン。ハンバーガーメニュなど
        actions: <Widget>[
          IconButton(
            icon: Icon(Icons.android),
            tooltip: 'add star...',
            onPressed: iconPressedA,
          ),
          IconButton(
            icon: Icon(Icons.favorite),
            tooltip: 'subtract star...',
            onPressed: iconPressedB,
          ),
        ],

        // bottom：タイトルの下に表示されるもの（あまり使わない）
        // 使う場合はPreferredSizeクラス使う
        bottom: PreferredSize(
          preferredSize: const Size.fromHeight(30.0),
          child: Center(
            child: Text(_stars,
              style: TextStyle(
                fontSize: 22.0,
                color:Colors.white,
              ),
            ),
          ),
        ),
      ),
      
      body: Center(
          child: Text(
            _message,
            style: const TextStyle(
              fontSize: 28.0,
            ),
          )
      ),
    );
  }

  void iconPressedA() {
    _message = 'tap "android".';
    _star++;
    update();
  }
  void iconPressedB() {
    _message = 'tap "favorite".';
    _star--;
    update();
  }

  void update() {
    _star = _star < 0 ? 0 : _star > 5 ? 5 : _star;
    setState(() {
      _stars = '★★★★★☆☆☆☆☆'.substring(5 - _star, 5 - _star + 5) ;
      _message = _message + '[$_star]';
    });
  }
}