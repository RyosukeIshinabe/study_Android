import 'package:flutter/material.dart';
import 'dart:math';
import 'trump.dart';
import 'poker.dart';

class Deck {
  List<Trump> cards = new List();

  // コンストラクタ
  Deck() {
    init();
  }

  // 未使用デッキの初期化
  void init() {
    for (int i = 0; i < Poker.MAX_MARK; i++) {
      for (int j = 0; j < Poker.MAX_NUMBER; j++) {
        this.cards.add(Trump(i, j));
      }
    }
    print('init trump test started.');
    print('this.deck [0] is ' + this.cards[0].cardToString() );
    print('this.deck [1] is ' + this.cards[1].cardToString() );
    print('this.deck [2] is ' + this.cards[2].cardToString() );
    print('test finished.');
  }

  /*
    カードを文字列やアイコンで表示するためのメソッド
   */

  // 引数で指定されたカードを表現する文字列を返す
  String cardToString(int cardNum) {
    return this.cards[cardNum].cardToString();
  }

  // 引数で指定されたカードを表現するアイコンを返す（シャッフル値のみ）
  IconData returnIconOfShuffle(int cardNum) {
    return this.cards[cardNum].returnIconOfShuffle();
  }

  // 引数で指定されたカードを表現するアイコンを返す（マークのみ）
  IconData returnIconOfMark(int cardNum) {
    return this.cards[cardNum].returnIconOfMark();
  }

  // 引数で指定されたカードを表現する文字列を返す
  String returnStringOfNumber(int cardNum) {
    return this.cards[cardNum].number.toString();
  }

  /*
    シャッフル値のゲッターとセッター
   */

  // 引数で指定されたカードのシャッフル値を取得する
  bool getShuffleStatus(int cardNum) {
    return this.cards[cardNum].shuffle;
  }

  // 引数で指定されたカードのシャッフル値を反転する
  void changeShuffleStatus(int cardNum) {
    this.cards[cardNum].changeShuffleStatus();
  }

  /*
    使用/未使用のゲッターとセッター
   */

  // 引数で指定されたカードの未使用値を取得する
  bool getUsedStatus(int cardNum) {
    return this.cards[cardNum].used;
  }

  // 引数で指定されたカードの未使用値を反転する
  void changeUsedStatus(int cardNum) {
    this.cards[cardNum].changeUsedStatus();
  }

  /*

   */

  // デッキ内のnull以外のカードの枚数を取得する
  int getDeckLength() {
    int count = 0;
    for ( int i = 0; i < this.cards.length; i++ ) {
      if ( cards[i] != null ) {
        count++;
      }
    }
    return count;
  }

  // デッキ内のフィールド=trueのカードの枚数を取得する
  int countFiledCard() {
    int count = 0;
    for ( int i = 0; i < this.cards.length; i++ ) {
      if ( this.cards[i].field == true ) {
        count++;
      }
    }
    return count;
  }

  // デッキ内の未使用=trueのカードの枚数を取得する
  int countNotUsedCard() {
    int count = 0;
    for ( int i = 0; i < this.cards.length; i++ ) {
      if ( this.cards[i].used == true ) {
        count++;
      }
    }
    return count;
  }

  // これはあとで消す
  // デッキの中からnull以外のランダムなカードを取り出して消す
  /*
  Trump takeCardFromDeckAndErase() {
    if ( this.getDeckLength() == 0 ) {	// 残り枚数が0ならnullを返す
      return null;
    }

    Random rand = new Random();	// ランダムな数字を作成するためのクラス
    int index;

    do {
      index = rand.nextInt(this.cards.length);	// ランダムな数字を作成して
      print('created rand number is: ' + index.toString());
    } while ( this.cards[index] == null );	// そのカードがnullだった場合do{}内を繰り返す

    print('taked card number is: ' + this.cards[index].cardToString() );
    Trump refuge = this.cards[index];	// 引いたカードを一旦避難
    this.cards[index] = null;	// 引いたカードの場所をnullにする
    return refuge;	// 避難していたカードをreturn
  }
  */

  // デッキの中からランダムな未使用カードを指定して使用済みにする
  void takeCardFromUnusedCard() {

    Random rand = new Random();	// ランダムな数字を作成するためのクラス
    int index;

    do {
      index = rand.nextInt(this.cards.length);	// ランダムな数字を作成して
      print('created rand number is: ' + index.toString());
    } while ( this.cards[index].used == true );	// そのカードが使用済みだった場合do{}内を繰り返す

    print('taked card number is: ' + this.cards[index].cardToString() );
    this.cards[index].changeFieldStatus();  // フィールドにある状態にする
    this.cards[index].changeUsedStatus(); // 使用済みにする
  }


  // デッキ内のフィールド=trueのカードを調べ、マーク（0〜3）をキー、枚数を値にした配列を返す
  List<int> getSameMarkOfCardFromDeck() {

    // マークの種類分の配列を用意
    List<int> sameMarkDeck = new List(Poker.MAX_MARK);
    for ( int i = 0; i < Poker.MAX_MARK; i++ ) {
      sameMarkDeck[i] = 0;
    }

    // デッキ内の全てのカードを調べて同じマークがあった場合インクリメント
    for ( int j = 0; j < this.getDeckLength(); j++ ) {
      if ( this.cards[j].field == true ) {
        int mark = this.cards[j].mark;
        sameMarkDeck[mark]++;
      }

    }
    return sameMarkDeck;
  }

  // デッキ内のフィールド=trueのカードを調べ、数字（1〜13）をキー、枚数を値にした配列を返す
  List<int> getSameNumOfCardFromDeck() {

    // 数字の種類分の配列を用意
    List<int> sameNumDeck = new List(Poker.MAX_NUMBER);
    for ( int i = 0; i < Poker.MAX_NUMBER; i++ ) {
      sameNumDeck[i] = 0;
    }

    // デッキ内の全てのカードを調べて同じマークがあった場合インクリメント
    for ( int j = 0; j < this.getDeckLength(); j++ ) {
      if ( this.cards[j].field == true ) {
        int num = this.cards[j].number;
        sameNumDeck[num]++;
      }
    }
    return sameNumDeck;
  }

  /*
  // デッキの末尾にカードを挿入する
  void insertCard(Trump trump) {
    for ( int i = 0; i < Poker.MAX_CARD; i++ ) {
      if ( this.cards[i] == null ) {
        this.cards[i] = trump;
        break;
      }
    }
  }
  */

  // デッキ内の任意のカードを破棄
  void setNull(int cardNum) {
    cards[cardNum] = null;
  }

  // デッキ内のカードを全て破棄
  void setNullAll() {
    for ( int i = 0; i < getDeckLength(); i++ ) {
      cards[i] = null;
    }
  }

  // フィールドの指定インデックス番目のカードを返す
  Trump pickFieldCard(int no) {
    int count = 0;
    for ( int i = 0; i < this.cards.length; i++ ) {
      if ( this.cards[i].field == true ) {
        if ( count == no ) {
          return this.cards[i];
        }
        count++;
      }
    }
    return this.cards[0];
  }

  // フィールドの指定インデックス番目のカードのshuffle値を変える
  void changeShuffleStatusOfFieldCard(int no) {
    int count = 0;
    for ( int i = 0; i < this.cards.length; i++ ) {
      if ( this.cards[i].field == true ) {
        if ( count == no ) {
          this.cards[i].changeShuffleStatus();
        }
        count++;
      }
    }
  }
}
