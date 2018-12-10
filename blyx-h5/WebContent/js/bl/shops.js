var domain = 'http://mgr.hnkbmd.com';
var IMAGE_PREFIX = 'http://yx.hnkbmd.com';
var pageNum = 1, pageSize = 15;
var provinceName, cityName, areaName;

var gc = new BMap.Geocoder();   
var geolocation = new BMap.Geolocation();    
geolocation.getCurrentPosition(function(r) {   //定位结果对象会传递给r变量  
  if(this.getStatus() == BMAP_STATUS_SUCCESS) {  //通过Geolocation类的getStatus()可以判断是否成功定位。  
    var pt = r.point;   
    gc.getLocation(pt, function(rs){
      var addComp = rs.addressComponents;
      $(".local span:not(.rg)").text(addComp.province + addComp.city + addComp.district);
      provinceName = addComp.province;
      cityName = addComp.city;
      areaName = addComp.district;
      loadData(pageNum, pageSize, provinceName, cityName, areaName);
    });
  } else {
    provinceName = null;
    cityName = null;
    areaName = null;
    loadData(pageNum, pageSize, provinceName, cityName, areaName);
  }
},  
  {enableHighAccuracy: true}  
)
mui.init({
  pullRefresh : {
    container : '#pullrefresh',
    down : {
      //style : 'circle',
      //callback: pulldownRefresh
    },
    up : {
      auto : false,
      contentrefresh : '正在加载...',
      contentnomore:'没有更多店铺了',
      callback : pullupRefresh
    }
  }
});
mui('.mui-scroll-wrapper').scroll({
  deceleration: 0.0005
});

function pulldownRefresh() {
  setTimeout(function() {
    pageNum = 1;
    $(".itemlist").html("");
    loadData(pageNum, pageSize);
    mui('#pullrefresh').pullRefresh().refresh(true);
    mui('#pullrefresh').pullRefresh().endPulldownToRefresh(true);
    pageNum++;
  }, 10);
}
function pullupRefresh() {
  setTimeout(function() {
    loadData(pageNum, pageSize);
    pageNum++;
  }, 10);
}

function loadData(pageNum, pageSize, provinceName, cityName, areaName){
  $.ajax({
    url: domain+"/api/distributionRegion/queryDistributionRegion",
    data: {provinceName: provinceName, cityName: cityName, areaName: areaName, state:1, 'pager.pageNum': pageNum, 'pager.pageSize': pageSize},
    type: 'post',
    dataType: 'json',
    success: function(res){
      if(res.code==0){
        var items = res.data;
        if(null != items && items.length > 0){
          mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
          for(var i in items){
            var item = items[i];
            var a = ""; 
            if(item.distributor.provinceName) a += item.distributor.provinceName;
            if(item.distributor.cityName) a += item.distributor.cityName;
            if(item.distributor.areaName) a += item.distributor.areaName;
            if(item.distributor.townName) a += item.distributor.townName;
            var li = '\
                <li class="clearfix" href="index.html?py='+item.py+'">\
                  <div class="shop clearfix">\
                    <img src="'+(item.distributor.head ? ((item.distributor.head.indexOf("http")==0 ? '' : IMAGE_PREFIX) + item.distributor.head) : 'images/head.png')+'" class="userimg">\
                    <p><label>ID：</label><span class="shopcode">'+item.distributor.shopCode+'</span></p>\
                    <p><label><span class="mui-icon mui-icon-home"></span>：</label><span class="shopname">'+item.distributor.shopName+'</span></p>\
                    <p class="t">\
                      <label> <span class="mui-icon mui-icon-location"></span>：</label><span class="shopaddress">'+a+item.distributor.address+'</span></p>\
                    <span class="fr">购买指数<br><b class="soldNum">'+item.distributor.soldNum+'</b><br>粉丝数<br><b class="fansNum">'+item.distributor.fansNum+'</b></span>\
                  </div>\
                </li>';
            $(".mui-scroll ul").append(li);
          }
          $("li[href]").on("tap", function(){
            var href=$(this).attr("href");
            document.location.href=href;
          });
        }else{
          mui('#pullrefresh').pullRefresh().endPullupToRefresh(true);
        }
      }else{
        alert(res.code)
      }
    }
  });
}