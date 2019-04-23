import 'package:flutter/material.dart';

class Trump {
  static final int KEY_OF_CLUB = 0;
  static final int KEY_OF_DIA = 1;
  static final int KEY_OF_HEART = 2;
  static final int KEY_OF_SPADE = 3;

  int mark;
  int number;
  bool shuffle;

  // コンストラクタ
  Trump(int mark, int number, bool shuffle) {
    this.mark = mark;
    this.number = number;
    this.shuffle = shuffle;
  }

  // カードを表現する文字列を返す
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
      markString = "□ CLUB";
    } else if ( this.mark == Trump.KEY_OF_DIA ) {
      markString = "★ DIA";
    } else if ( this.mark == Trump.KEY_OF_HEART ) {
      markString = "● HEART";
    } else if ( this.mark == Trump.KEY_OF_SPADE ) {
      markString = "△ SPADE";
    }
    // 数字用
    int dNum = this.number + 1; // 0が1、1が2...を表すため
    return shuffleStatus + "\n" + markString + "\n" + dNum.toString();
  }

  // シャッフル値を反転する
  void changeShuffleStatus() {
    this.shuffle = !this.shuffle;
  }
}
