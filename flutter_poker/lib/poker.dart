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
  int coin = 100; // 所持コイン。テストで固定
  int bet = 10; // ベット。テストで固定
  String card1; // カード用のテキストビュー
  String card2; // カード用のテキストビュー
  String card3; // カード用のテキストビュー
  String card4; // カード用のテキストビュー
  String card5; // カード用のテキストビュー

  // アプリ全体通して使用するテキストスタイルを予め設定し各ウィジェットで使い回す
  var defaultTextStyle = TextStyle(
    fontSize:12.0,
    fontWeight: FontWeight.w200,
    fontFamily: "Roboto",);
  var largeTextStyle = TextStyle(
    fontSize:16.0,
    fontWeight: FontWeight.w300,
    fontFamily: "Roboto",);

  // 最初だけ呼ばれる初期化メソッド
  @override
  void initState() {
    super.initState();
    shufflableTime = 1;  // シャッフル回数をリセット
    message = 'ゲームを開始します。' + '\n' +
        shufflableTime.toString() + '/' + Poker.maxShufflableTime.toString() + ' 回目のシャッフルです。';
    unUsedDeck = new Deck();  // 未使用デッキを初期化
    fieldDeck = new Deck(); // フィールドデッキを初期化
    moveCardFromUnUsedToField();  // 未使用デッキからフィールドにカードを補填
    card1 = fieldDeck.deck[0].cardToString();
    card2 = fieldDeck.deck[1].cardToString();
    card3 = fieldDeck.deck[2].cardToString();
    card4 = fieldDeck.deck[3].cardToString();
    card5 = fieldDeck.deck[4].cardToString();
  }

  // ウィジェット（画面の部品）の組み立て
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

          Padding(
            padding: EdgeInsets.only(top: 20.0),
          ),

          Text(
            "COIN: " + coin.toString(),
            style: largeTextStyle,
          ),
          Text(
            "BET: " + bet.toString(),
            style: largeTextStyle,
          ),

          Padding(
            padding: EdgeInsets.all(10.0),
          ),

          Text(
            "1ペア：***   2ペア：***" + "\n"
            "3カード：***   4カード：***",
            style: largeTextStyle,
          ),

          Padding(
            padding: EdgeInsets.all(10.0),
          ),

          // カードの行（1枚目と2枚目）
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            mainAxisSize: MainAxisSize.max,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[

              // 1枚目のカード
              RaisedButton(
                key:null,
                onPressed:cardPressed,
                color: Colors.black12,
                child: Padding(
                  padding: EdgeInsets.all(10.0),
                  child: Text(
                      card1,
                      style: largeTextStyle
                  ),
                ),
              ),

              // 2枚目のカード
              RaisedButton(
                key:null,
                onPressed:cardPressed,
                color: Colors.black12,
                child: Padding(
                  padding: EdgeInsets.all(10.0),
                  child: Text(
                      card2,
                      style: largeTextStyle
                  ),
                ),
              ),
            ]
          ),

          Padding(
            padding: EdgeInsets.all(5.0),
          ),

          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            mainAxisSize: MainAxisSize.max,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[

              // 3枚目のカード
              RaisedButton(
                key:null,
                onPressed:cardPressed,
                color: Colors.black12,
                child: Padding(
                  padding: EdgeInsets.all(10.0),
                  child: Text(
                      card3,
                      style: largeTextStyle
                  ),
                ),
              ),

              // 4枚目のカード
              RaisedButton(
                key:null,
                onPressed:cardPressed,
                color: Colors.black12,
                child: Padding(
                  padding: EdgeInsets.all(10.0),
                  child: Text(
                      card4,
                      style: largeTextStyle
                  ),
                ),
              ),

              // 5枚目のカード
              RaisedButton(
                key:null,
                onPressed:cardPressed,
                color: Colors.black12,
                child: Padding(
                  padding: EdgeInsets.all(10.0),
                  child: Text(
                      card5,
                      style: largeTextStyle
                  ),
                ),
              ),
            ]
          ),

          Padding(
            padding: EdgeInsets.all(10.0),
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
                  style: largeTextStyle
              ),
            ),
          ),

          Padding(
            padding: EdgeInsets.all(10.0),
          ),

          Text(
            message,
            style: largeTextStyle,
          ),

        ]
      ),
    );
  }

  // カードがタップされたときの処理
  void cardPressed() {
    print('----- カードがタップされました。-----');
    fieldDeck.deck[0].changeShuffleStatus();
    setState(() {
      card1 = fieldDeck.deck[0].cardToString();
    });
  }

  // カードのテキストを更新
  void reloadCard() {
    setState(() {
      card1 = fieldDeck.deck[0].cardToString();
      card2 = fieldDeck.deck[1].cardToString();
      card3 = fieldDeck.deck[2].cardToString();
      card4 = fieldDeck.deck[3].cardToString();
      card5 = fieldDeck.deck[4].cardToString();
    });
  }

  // シャッフルボタンをタップした時の処理
  void fixButtonPressed() {
    if ( shufflableTime == Poker.maxShufflableTime ) {
      gameSet();
    } else {
      eraseShuffleCard(); // シャッフル
      moveCardFromUnUsedToField();  // 足りないカードを補填
      reloadCard();  // カードのテキストを更新
      shufflableTime++;
      setState(() {
        message = 'シャッフルしました。' + '\n' +
            shufflableTime.toString() + '/' + Poker.maxShufflableTime.toString() + ' 回目のシャッフルです。';
      });
    }
  }

  // フィールドデッキに足りない枚数のカードを未使用デッキから引く
  void moveCardFromUnUsedToField() {
    setState(() {
      while ( fieldDeck.getDeckLength() < Poker.maxFieldDeck ) {
        fieldDeck.insertCard(unUsedDeck.takeCardFromDeckAndErase());
      }
    });
  }

  // フィールドデッキ内のシャッフル値がtrueのカードを消す
  void eraseShuffleCard() {
    for ( int i = 0; i < fieldDeck.getDeckLength(); i++ ) {
      if ( fieldDeck.deck[i].shuffle ) {
        unUsedDeck.setNull(i);
      }
    }
  }

  // ゲーム終了
  void gameSet() {
    print('----- ゲームが終了しました。-----');
  }
}




