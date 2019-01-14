import 'package:flutter/material.dart';

// TextFieldのテストです。
// TextFieldは、ユーザーが自由に編集可能な入力欄です。
// 今回のテストでは、文字を入力した後決定ボタンを押すことで、別の文字表示欄に表示してみます。
// TextFieldをはじめとする入力を行うウィジェットには、自身の中に値を保管する機能はありません。
// そのため、Controllerクラスを使用し、その中に値を代入したり取り出したりします。
// TextFieldには、TextEditingControllerクラスが用意されています。

class TextFieldTest extends StatelessWidget {

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
  var _message;
  var _message2;

  // TextFieldに入力された値を管理するクラスがTextEditingController
  final controller = TextEditingController();
  final controller2 = TextEditingController();

  void initState() {
    _message = 'ready.';
    _message2 = 'ready.';
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        title: new Text('App Name'),
      ),
      body:
      new Center(
        child:
        new Column(
            mainAxisAlignment: MainAxisAlignment.start,
            mainAxisSize: MainAxisSize.max,
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: <Widget>[

              new Padding(
                padding: const EdgeInsets.all(20.0),
                child: Text(
                  _message,
                  style: TextStyle(fontSize: 32.0, fontWeight: FontWeight.w400 ),
                ),
              ),

              new Padding(
                padding: const EdgeInsets.all(10.0),
                child: TextField(
                  // TextFieldウィジェットでは、必ずコントローラーを指定する
                  // コントローラーのtextプロパティに値を保存される
                  controller: controller,
                  style: TextStyle(fontSize: 28.0, color: const Color(0xFFFF0000), fontWeight: FontWeight.w400),
                ),
              ),

              FlatButton(
                onPressed: buttonPressed,
                padding: EdgeInsets.all(10.0),
                color: Colors.lightBlueAccent,
                child: Text(
                  'Submit',
                  style: TextStyle(fontSize: 32.0, color: const Color(0xFF000000), fontWeight: FontWeight.w400),
                ),
              ),



              // ここから下では、submitボタンをclickするのではなく、onChangedイベントを感知して同じ動作を行ってみます

              new Padding(
                padding: EdgeInsets.all(20.0),
                child: Text(
                  _message2,
                  style: TextStyle(fontSize: 32.0, fontWeight: FontWeight.w400 ),
                ),
              ),

              new Padding(
                padding: EdgeInsets.all(20.0),
                child: TextField(
                  // TextFieldのonChangedプロパティでは、値が変更されるたびに指定したメソッドが呼ばれる
                  onChanged: textChanged,
                  controller: controller2,
                  style: TextStyle(fontSize: 28.0, color: const Color(0xFFFF0000), fontWeight: FontWeight.w400),
                ),
              ),



            ]
        ),
      ),
    );
  }

  // ボタンを押した時に呼ばれるメソッド。メソッド名はなんでもいい
  void buttonPressed() {
    setState(() {
      // コントローラーのtextプロパティから値を取り出す
      _message = 'you said: ' + controller.text;
    });
  }

  // 値が変更されたことを検知して呼ばれるメソッド。メソッド名はなんでもいい
  void textChanged(String val) {
    setState(() {
      _message2 = val.toUpperCase();
    });
  }
}