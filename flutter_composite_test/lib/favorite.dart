import 'package:flutter/material.dart';
import 'style.dart';

class Favorite {
  static int _selectedListIndex = 0;

  static Widget contents = new Center(

    child: Column(
        mainAxisAlignment: MainAxisAlignment.start,
        mainAxisSize: MainAxisSize.max,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[

          Padding(
            padding: EdgeInsets.all(20.0),
          ),

          ListView(
            shrinkWrap: true,
            padding: const EdgeInsets.all(20.0),
            children: <Widget>[

              // ListTileはListView専用の便利なウィジェット
              ListTile(
                // ListTile左側に表示されるアイコン
                leading: const Icon(Icons.android),
                // ListTileに表示される文字列
                title: const Text('あははははあああん'),
                // selectedがtrueとなっている項目が選択状態になる
                // ここでは、indexに入っている値と[1]を比べている
                selected: _selectedListIndex == 1,
                // タップされたらindexに1を代入する
                onTap: () {
                  _selectedListIndex = 1;
                },
              ),

              ListTile(
                leading: const Icon(Icons.favorite),
                title: const Text('second item'),
                selected: _selectedListIndex == 2,
                onTap: () {
                  _selectedListIndex = 2;
                },
              ),
              ListTile(
                leading: const Icon(Icons.favorite_border),
                title: const Text('third item'),
                selected: _selectedListIndex == 3,
                onTap: () {
                  _selectedListIndex = 3;
                },
              ),
            ],
          ),
        ]
    ),
  );

}