<!--
function newImage(arg) {
     if (document.images) {
          rslt = new Image();
          rslt.src = arg;
          return rslt;
     }
}



function changeImages() {

     if (document.images && (preloadFlag == true)) {
          for (var i=0; i<changeImages.arguments.length; i+=2) {
               document[changeImages.arguments[i]].src = changeImages.arguments[i+1];
          }
     }

}


var preloadFlag = false;
if (document.images) {
	arrow_o = newImage("imgs/sm_arrow_o.gif");
	arrow = newImage("imgs/sm_arrow.gif");
	preloadFlag = true;
}

function Toggle(e)
{
    return true;
    Highlight(e);
}

function Highlight(e)
{
  var r = null;
  if (e.parentNode && e.parentNode.parentNode) {
    r = e.parentNode.parentNode;
  }
  else if (e.parentElement && e.parentElement.parentElement) {
    r = e.parentElement.parentElement;
  }

  //alert(e.parentNode.parentNode.className);
  if (r) {
    if (r.className == "datarow" || r.className == "datarowon") {
      r.className = "datarowhigh";
    }
  }
}
function OverRow(e,which)
{
  //alert ('which: ' + which);
  changeImages(which, ROOT + '/imgs/sm_arrow_o.gif');
  var r = null;
  if(e.className) {
    r = e;
  }
  else if (e.parentNode && e.parentNode.parentNode.className) {
    r = e.parentNode.parentNode;
  }
  else if (e.parentElement && e.parentElement.parentElement.className) {
    r = e.parentElement.parentElement;
  }
  else if (e.parentNode) {
    r = e.parentNode;
  }
  else if (e.parentElement) {
    r = e.parentElement;
  }
  if (r) {
    if (r.className == "datarow") {
      r.className = "datarowon";
    }
    if (r.className == "datarowhigh") {
      r.className = "datarowhighon";
    }
  }
}
function OutRow(e,which)
{
  var r = null;
  changeImages(which, ROOT + '/imgs/sm_arrow.gif');
  if(e.className) {
    r = e;
  }
  else if (e.parentNode && e.parentNode.parentNode.className) {
    r = e.parentNode.parentNode;
  }
  else if (e.parentElement && e.parentElement.parentElement.className) {
    r = e.parentElement.parentElement;
  }
  else if (e.parentNode) {
    r = e.parentNode;
  }
  else if (e.parentElement) {
    r = e.parentElement;
  }
    if (r) {
    if (r.className == "datarowon") {
      r.className = "datarow";
    }
    if (r.className == "datarowhighon") {
      r.className = "datarowhigh";
    }
  }
}
//-->
