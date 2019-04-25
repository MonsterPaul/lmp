(function (doc, win) {
  var docEl = doc.documentElement,
    resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
    recalc = function () {
      var clientWidth = docEl.clientWidth;
      if (!clientWidth) return;
      docEl.style.fontSize = 20 * (clientWidth / 750) + 'px';
    };

  if (!doc.addEventListener) return;
  win.addEventListener(resizeEvt, recalc, false);
  doc.addEventListener('DOMContentLoaded', recalc, false);
})(document, window);


function GetQueryString(name) {
  var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
  var r = window.location.search.substr(1).match(reg);
  if (r != null) return unescape(r[2]); return null;

}
function cancelBubble(e) {
  var evt = e ? e : window.event;
  if (evt.stopPropagation) { //W3C 
    evt.stopPropagation();
  } else { //IE      
    evt.cancelBubble = true;
  }
}

//iphone返回不刷新解决方法
function pushHistory() {
  window.addEventListener("popstate", function (e) {
    //alert("后退");
    self.location.reload();
  }, false);
  var state = {
    title: "",
    url: "#"
  };
  window.history.replaceState(state, "", "#");
};

var common_src = 'http://jx.renrenxin.top/appApi/appUsers/'


