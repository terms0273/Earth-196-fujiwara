import L from 'leaflet';

// private用 厳密にはprivateではない
// * 同一ファイル内からは容易に呼び出される
// * getOwnPropertySymbolsからも参照される
const map = Symbol('map');
const init = Symbol('init');
const zoom = Symbol('zoom');

// Symbol対策
import "babel-polyfill";

/**
 * 地図領域表示用クラス
 * 
 * @export
 * @class Map
 */
export default class Map {

  /**
   * Creates an instance of Map.
   * 
   * @memberOf Map
   */
  constructor() {
    this[map] = {};
    this[zoom] = 16;
    this[init]();
  }

  /**
   * コンストラクタ寄り呼び出される初期化メソッド
   * Leaflet.jsを利用しベース地図及びLayerを生成する
   * 
   * @returns 失敗時のみ出力
   * 
   * @memberOf Map
   */
  [init]() {
    // map要素が無い場合は地図画面ではない
    if (!document.getElementById("map")) {
      console.log("this page is not map page");
      return;
    }

    let std = L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
      attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
    });

    let chiriin = L.tileLayer('https://cyberjapandata.gsi.go.jp/xyz/std/{z}/{x}/{y}.png', {
      attribution: "<a href='http://portal.cyberjapan.jp/help/termsofuse.html' target='_blank'>国土地理院</a>"
    });


    let pf = L.tileLayer('http://tile.openweathermap.org/map/precipitation_new/{z}/{x}/{y}.png?appid=9f70e15ce517f3eb8a5c50dabd8eaf57', {
      attribution: '<a href="">precipitation</a>',
      // opacity: 0.8
    });

    let pr = L.tileLayer('http://tile.openweathermap.org/map/pressure_new/{z}/{x}/{y}.png?appid=9f70e15ce517f3eb8a5c50dabd8eaf57', {
      attribution: '<a href="">pressure</a>',
      // opacity: 0.8
    });


    let wi = L.tileLayer('http://tile.openweathermap.org/map/wind_new/{z}/{x}/{y}.png?appid=9f70e15ce517f3eb8a5c50dabd8eaf57', {
      attribution: '<a href="">wind</a>',
      // opacity: 0.8
    });

    let te = L.tileLayer('http://tile.openweathermap.org/map/temp_new/{z}/{x}/{y}.png?appid=9f70e15ce517f3eb8a5c50dabd8eaf57', {
      attribution: '<a href="">tempressure</a>',
      // opacity: 0.8
    });

    this[map] = L.map("map", {
      center: [37.09, 138.52],
      zoom: this[zoom],
      layers: [std]
    });

    // 主題図レイヤーグループ化
    let baseMaps = {
      "Mapbox(osm)": std,
      "Mapbox(chiriin)": chiriin
    };

    // オーバレイヤーグループ化
    let overlayMaps = {
      'Precipitation': pf,
      'Pressure': pr,
      'Wind': wi,
      'Temperature': te
    };

    L.control.layers(baseMaps, overlayMaps).addTo(this[map]);

    // スケールバーを追加
    L.control.scale().addTo(this[map]);
    
    // リサイズ
    this[map].on('resize', () => {
      this[map].invalidateSize();
    });
  }

  /**
   * 地図移動用メソッド
   * 地域検索実行時に対象地域に地図の中心を移動する
   * 
   * @param {any} lat 緯度
   * @param {any} lon 経度
   * 
   * @memberOf Map
   */
  mapPan(lat, lon) {
    this[map].panTo([lat, lon]);
    this[map].setZoom(this[zoom]);
  }
}