import moment from 'moment';

const city = Symbol('city');
const jsonParse = Symbol('jsonParse');
const createUrl = Symbol('createUrl');
const createImgUrl = Symbol('createImgUrl');
const animationWrapper = Symbol('animationWrapper');
const appendAlert = Symbol('appendAlert');

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
    // let mo = moment;
    let jsonData = json;
    // 日付の設定
    let sunrise = moment(jsonData.sys.sunrise * 1000).locale(jsonData.sys.country);
    let sunset = moment(jsonData.sys.sunset * 1000).locale(jsonData.sys.country);

    let weather = jsonData.weather[0];
    
    this[animationWrapper]($('#weather'), () => {
      $('#weather').html(this[createImgUrl](weather));
    });    
    this[animationWrapper]($('#city-name'), () => {
      $('#city-name').text('Current weather in ' + jsonData.name);
    });
    this[animationWrapper]($('#temperature'), () => {
      $('#temperature').text((jsonData.main.temp - 273.15) + '℃');
    });
    this[animationWrapper]($('#sunrise'), () => {
      $('#sunrise').text(sunrise);
    });
    this[animationWrapper]($('#sunset'), () => {
      $('#sunset').text(sunset)
    });
    this[animationWrapper]($('#pressure'), () => {
      $('#pressure').text(jsonData.main.pressure + ' hpa');
    });
    this[animationWrapper]($('#humidity'), () => {
      $('#humidity').text(jsonData.main.humidity + ' %');
    });
    this[animationWrapper]($('#wind'), () => {
      $('#wind').text(jsonData.wind.speed + ' m/s');
    });
    this[animationWrapper]($('#cloud'), () => {
      $('#cloud').text(jsonData.clouds.all + ' %');
    });
    this[animationWrapper]($('#latlon'), () => {
      // change()を呼び出すことで変更イベントを発火
      $('#latlon').text(jsonData.coord.lat + ',' + jsonData.coord.lon).change();
    });
  }
  
    /**
   * 汎用アニメーションメソッド
   * 
   * @param {any} elem 更新対象DOMエレメント
   * @param {any} callback フェードイン後発火コールバック関数
   * 
   * @memberOf Weather
   */
  [animationWrapper](elem, callback) {
    elem.fadeOut(400, () => {
      callback();
      elem.fadeIn(400);
    });    
  }
  
  [appendAlert](mes) {
    let alertDiv = '<div id="danger" class="uk-alert-danger" uk-alert>' + 
            '<a class="uk-alert-close" uk-close></a>' + 
            '<p>取得出来ませんでした:' + mes + '</p>' + 
            '</div>';
    $("#alert-area").hide().html(alertDiv).fadeIn(400);  
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
      $("#danger").fadeOut(() => { 
        $("#danger").remove();
      });
      this[jsonParse](json);
    }, (err) => {
      console.log(err.status + ":" + err.statusText);
      this[appendAlert](err.status + "," + err.statusText);
    });
  }
}