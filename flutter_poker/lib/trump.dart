import 'package:flutter/material.dart';

class Trump {
  static final int KEY_OF_CLUB = 0;
  static final int KEY_OF_DIA = 1;
  static final int KEY_OF_HEART = 2;
  static final int KEY_OF_SPADE = 3;

  int mark;
  int number;
  bool shuffle;  // 次のシャッフルで交換するかどうか
  bool field;   // フィールド上に表示されていかどうか
  bool used;    // 使用済みかどうか

  // コンストラクタ
  Trump(int mark, int number) {
    this.mark = mark;
    this.number = number;
    this.shuffle = false;
    this.field = false;
    this.used = false;
  }

  // カードを表現する文字列を返す（すべて文字列で返す用）
  String cardToString() {
    // シャッフル値用
    String shuffleStatus;
    if ( this.shuffle ) {
      shuffleStatus = "[CHANGE]";
    } else {
      shuffleStatus = "[STAY]";
    }
    // マーク用
    String markString;
    if ( this.mark == Trump.KEY_OF_CLUB ) {
      markString = "CLUB";
    } else if ( this.mark == Trump.KEY_OF_DIA ) {
      markString = "DIA";
    } else if ( this.mark == Trump.KEY_OF_HEART ) {
      markString = "HEART";
    } else if ( this.mark == Trump.KEY_OF_SPADE ) {
      markString = "SPADE";
    }
    // 数字用
    int dNum = this.number + 1; // 0が1、1が2...を表すため
    return shuffleStatus + "\n" + markString + "\n" + dNum.toString();
  }

  // カードを表現する文字列を返す（シャッフル値のみをアイコンで返す）
  IconData returnIconOfShuffle() {
    IconData shuffleIcon;
    if ( this.shuffle ) {
      shuffleIcon = Icons.autorenew;
    } else {
      shuffleIcon = Icons.file_download;
    }
    return shuffleIcon;
  }

  // カードを表現する文字列を返す（トランプのマークのみをマークで返す）
  IconData returnIconOfMark() {
    IconData markIcon;
    if ( this.mark == Trump.KEY_OF_CLUB ) {
      markIcon = Icons.wb_cloudy;
    } else if ( this.mark == Trump.KEY_OF_DIA ) {
      markIcon = Icons.wb_sunny;
    } else if ( this.mark == Trump.KEY_OF_HEART ) {
      markIcon = Icons.favorite;
    } else if ( this.mark == Trump.KEY_OF_SPADE ) {
      markIcon = Icons.star;
    }
    return markIcon;
  }

  // シャッフル値を反転する
  void changeShuffleStatus() {
    this.shuffle = !this.shuffle;
  }

  // フィールド値を反転する
  void changeFieldStatus() {
    this.field = !this.field;
  }

  // 未使用/使用済みを反転する
  void changeUsedStatus() {
    this.used = !this.used;
  }
}
