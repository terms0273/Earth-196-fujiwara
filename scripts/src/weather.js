import moment from 'moment';

const city = Symbol('city');
const jsonParse = Symbol('jsonParse');
const createUrl = Symbol('createUrl');
const createImgUrl = Symbol('createImgUrl');

// Symbol対策
import "babel-polyfill";

/**
 * 天候データ取得クラス
 * 
 * @export
 * @class Weather
 */
export default class Weather {

  /**
   * Creates an instance of Weather.
   * 
   * @memberOf Weather
   */
  constructor() {
    this[city] = "Tokyo";
  }

  /**
   * URL生成メソッド
   * 
   * @returns URL文字列
   * 
   * @memberOf Weather
   */
  [createUrl]() {
    return 'http://api.openweathermap.org/data/2.5/weather?q=' +
      this[city] +
      '&APPID=9f70e15ce517f3eb8a5c50dabd8eaf57';
  }

  /**
   * 地域情報設定メソッド
   * 
   * @param {any} inputCity 入力地域名
   * 
   * @memberOf Weather
   */
  setCity(inputCity) {
    this[city] = inputCity;
  }

  /**
   * 天気表示画像取得URL生成メソッド
   * 
   * @param {any} weather 天候ID
   * @returns URL文字列
   * 
   * @memberOf Weather
   */
  [createImgUrl](weather) {
    return '<div><img src="http://openweathermap.org/img/w/' + weather.icon + '.png">(' +
      weather.description + ')' +
      '</img></div>';
  }

  /**
   * 取得したJSONデータをパースし画面上に表示するメソッド
   * 
   * @param {any} json 取得データ
   * 
   * @memberOf Weather
   */
  [jsonParse](json) {
    let mo = moment;
    let jsonData = json; // json.body; // superagent版 jsonpにしたら違うのかも
    // 日付の設定
    let sunrise = moment(jsonData.sys.sunrise * 1000).locale(jsonData.sys.country);
    let sunset = moment(jsonData.sys.sunset * 1000).locale(jsonData.sys.country);

    let weather = jsonData.weather[0];

    $('#weather').html(this[createImgUrl](weather));
    $('#city-name').text('Current weather in ' + jsonData.name);
    $('#temperature').text((jsonData.main.temp - 273.15) + '℃');
    $('#sunrise').text(sunrise);
    $('#sunset').text(sunset);
    $('#pressure').text(jsonData.main.pressure + ' hpa');
    $('#humidity').text(jsonData.main.humidity + ' %');
    $('#wind').text(jsonData.wind.speed + ' m/s');
    $('#cloud').text(jsonData.clouds.all + ' %');
    // change()を呼び出すことでイベントを発火
    $('#latlon').text(jsonData.coord.lat + ',' + jsonData.coord.lon).change();
  }

  /**
   * Ajax問合せメソッド
   * 
   * @memberOf Weather
   */
  getRequest() {
    let url = this[createUrl]();
    $.ajax({
      url: url,
      dataType: "jsonp",
    }).then((json) => {
      this[jsonParse](json);
    }, (err) => {
      console.log(err.status + ":" + err.statusText);
      alert('取得出来ませんでした。');
    });
  }
}