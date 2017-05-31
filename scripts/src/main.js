import Weather from './weather';
import Map from './map';

$(function() {
  // 初期化処理
  let weather = new Weather();
  let map = new Map();
  const lat = 0;
  const lon = 1;

  // 初回の発火
  weather.getRequest();
  
  let searchExec = (elemId) => {
    let newCity = $(elemId).val();
    weather.setCity(newCity);
    weather.getRequest();
  };

  // イベント設定 検索ボタンクリック
  $('#search-city').click(() => {
    searchExec('#input-city');
  });
  $('#fixed-search-city').click(() => {
    searchExec('#fixed-input-city');
  });

  // イベント設定 検索ボックスエンターキー押下
  $('#input-city').keypress((e) => {
    if (e.which === 13) {
      searchExec('#input-city');
    }
  });

  $("#latlon").change(() => {
    let latlon = $("#latlon").text().split(",");
    map.mapPan(latlon[lat], latlon[lon]);
  });

  $("#map-area").on('show', () => {
    currentTab = "map";
  });
});