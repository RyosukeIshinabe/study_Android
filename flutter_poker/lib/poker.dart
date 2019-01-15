import 'package:flutter/material.dart';
import 'deck.dart';

class Poker extends StatefulWidget {
  static final int MAX_MARK = 4;
  static final int MAX_NUMBER = 13;
  static final int MAX_CARD = MAX_MARK * MAX_NUMBER;

  static final int maxFieldDeck = 5;  // フィールド上のデッキの最大数
  static final int maxShufflableTime = 2;  // シャッフルできる最大値

  @override
  _PokerState createState() => _PokerState();
}

class _PokerState extends State<Poker> {
  Deck unUsedDeck; // 未使用デッキ
  Deck fieldDeck; // フィールド上のデッキ
  String message; // 下部に表示されるメッセージ用変数
  int shufflableTime; // 現在のシャッフル回数

  // アプリ全体通して使用するテキストスタイルを予め設定し各ウィジェットで使い回す
  var defaultTextStyle = TextStyle(
    fontSize:12.0,
    fontWeight: FontWeight.w200,
    fontFamily: "Roboto",);

  // 最初だけ呼ばれる初期化メソッド
  @override
  void initState() {
    super.initState();
    shufflableTime = 1;  // シャッフル回数をリセット
    message = 'ゲームを開始します。' + '\n' +
        shufflableTime.toString() + '/' + Poker.maxShufflableTime.toString() + ' 回目のシャッフルです。';
    unUsedDeck.init();  // 未使用デッキを初期化
    moveCardFromUnUsedToField();  // 未使用デッキからフィールドにカードを補填
  }

  // ウィジェット（部品）の組み立て
  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        title: Text('Flutter Poker'),
        leading: BackButton(color: Colors.white),
      ),

      body: Column(
        mainAxisAlignment: MainAxisAlignment.start,
        mainAxisSize: MainAxisSize.max,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[

          Text(
            "COIN: ",
            style: defaultTextStyle,
          ),
          Text(
            "BET: ",
            style: defaultTextStyle,
          ),

          Padding(
            padding: EdgeInsets.all(20.0),
          ),

          Text(
            "1ペア：***   2ペア：***" + "\n"
            "3カード：***   4カード：***",
            style: defaultTextStyle,
          ),

          Padding(
            padding: EdgeInsets.all(20.0),
          ),

          // カードの行
          Row(
            mainAxisAlignment: MainAxisAlignment.start,
            mainAxisSize: MainAxisSize.max,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[

              // 1枚目のカード
              RaisedButton(
                key:null,
                onPressed:cardPressed,
                color: Colors.black12,
                child: Padding(
                  padding: EdgeInsets.all(5.0),
                  child: Text(
                      unUsedDeck.deck[0].cardToString(),
                      style: defaultTextStyle
                  ),
                ),
              ),

              // 2枚目のカード
              RaisedButton(
                key:null,
                onPressed:cardPressed,
                color: Colors.black12,
                child: Padding(
                  padding: EdgeInsets.all(5.0),
                  child: Text(
                      unUsedDeck.deck[1].cardToString(),
                      style: defaultTextStyle
                  ),
                ),
              ),

              // 3枚目のカード
              RaisedButton(
                key:null,
                onPressed:cardPressed,
                color: Colors.black12,
                child: Padding(
                  padding: EdgeInsets.all(5.0),
                  child: Text(
                      unUsedDeck.deck[2].cardToString(),
                      style: defaultTextStyle
                  ),
                ),
              ),

              // 4枚目のカード
              RaisedButton(
                key:null,
                onPressed:cardPressed,
                color: Colors.black12,
                child: Padding(
                  padding: EdgeInsets.all(5.0),
                  child: Text(
                      unUsedDeck.deck[3].cardToString(),
                      style: defaultTextStyle
                  ),
                ),
              ),

              // 5枚目のカード
              RaisedButton(
                key:null,
                onPressed:cardPressed,
                color: Colors.black12,
                child: Padding(
                  padding: EdgeInsets.all(5.0),
                  child: Text(
                      unUsedDeck.deck[4].cardToString(),
                      style: defaultTextStyle
                  ),
                ),
              ),

            ]
          ),

          Padding(
            padding: EdgeInsets.all(20.0),
          ),

          // シャッフル決定ボタン
          RaisedButton(
            key:null,
            onPressed:fixButtonPressed,
            color: Colors.black12,
            child: Padding(
              padding: EdgeInsets.all(10.0),
              child: Text(
                  "SHUFFLE",
                  style: defaultTextStyle
              ),
            ),
          ),

          Padding(
            padding: EdgeInsets.all(20.0),
          ),

          Text(
            message,
            style: defaultTextStyle,
          ),

        ]
      ),
    );
  }

  void cardPressed() {
    print('----- カードがタップされました。-----');
    setState(() {
      unUsedDeck.deck[0].changeShuffleStatus();
    });
  }

  // シャッフルボタンをタップした時の処理
  void fixButtonPressed() {
    if ( shufflableTime == Poker.maxShufflableTime ) {
      gameSet();
    } else {
      shufflableTime++;
      setState(() {
        message = 'シャッフルしました。' + '\n' +
            shufflableTime.toString() + '/' + Poker.maxShufflableTime.toString() + ' 回目のシャッフルです。';
      });
    }
  }

  // フィールドデッキに足りない枚数のカードを未使用デッキから引く
  void moveCardFromUnUsedToField() {
    while ( fieldDeck.getDeckLength() < Poker.maxFieldDeck ) {
      fieldDeck.insertCard(unUsedDeck.takeCardFromDeckAndErase());
    }
  }

  // フィールドデッキ内のシャッフル値がtrueのカードを消す
  void moveShuffleCardFromFieldToUsedDeck() {
    for ( int i = 0; i < Poker.maxFieldDeck; i++ ) {
      if ( fieldDeck.getShuffleStatus(i) ) {
        unUsedDeck.setNull(i);
      }
    }
  }

  // ゲーム終了
  void gameSet() {
    print('----- ゲームが終了しました。-----');
  }


}

