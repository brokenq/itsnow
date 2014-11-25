/**
 * Created by user on 2014/11/23.
 */

function add() {
    var count= 0 ;
    count++;
    //自增id不同的HTML对象，并附加到容器最后
    //
    var elementDiv = document.createElement("div");
    elementDiv.setAttribute("id","create"+count);
    elementDiv.setAttribute("class","form-group has-info");
    document.getElementById("newcontent").appendChild(elementDiv);

    var elementLabel = document.createElement("label");
    var textLable = document.createTextNode("值");
    elementLabel.setAttribute("for","");
    elementLabel.setAttribute("class","col-xs-12 col-sm-3 control-label no-padding-right");
    elementLabel.appendChild(textLable);

    var elementDiv2 = document.createElement("div");
    var elementInput = document.createElement("input");
    elementDiv2.setAttribute("class","col-xs-12 col-sm-2");
    elementInput.setAttribute("type","text");
    elementInput.setAttribute("name","keyInput");
   // elementInput.setAttribute("ng-model","dict.key");
    elementDiv2.appendChild(elementInput);
    //-----------------------------------------------
    var elementLabel2 = document.createElement("label");
    var textLable2 = document.createTextNode("名称");
    elementLabel2.setAttribute("for","");
    elementLabel2.setAttribute("class","col-xs-12 col-sm-1 control-label no-padding-right");
    elementLabel2.appendChild(textLable2);


    var elementDiv3 = document.createElement("div");
    elementDiv3.setAttribute("class","col-xs-12 col-sm-2");
    var elementInput2 = document.createElement("input");
    elementInput2.setAttribute("type","text");
    elementInput2.setAttribute("name","valueInput");
//    elementInput2.setAttribute("ng-model","dict.value");
    elementDiv3.appendChild(elementInput2);

    var elementButton = document.createElement("button");
    var elementA = document.createElement("a");
    elementA.setAttribute("onclick","del("+count+")");
    elementA.setAttribute("id",count+"");
    var textA = document.createTextNode("删除");
    elementA.appendChild(textA);
    elementButton.appendChild(elementA);

    elementDiv.appendChild(elementLabel);
    elementDiv.appendChild(elementDiv2);
    elementDiv.appendChild(elementLabel2);
    elementDiv.appendChild(elementDiv3);
    elementDiv.appendChild(elementButton);

   // document.getElementById("newcontent").innerHTML="";
   // document.getElementById("newcontent").insertAdjacentHTML("beforeEnd", elementDiv);
}
//删除指定元素
function del(count){
//   alert("wwww");
    var elementA = document.getElementById(count);
    //console.log(elementA);
    //console.log(elementA.parentNode.parentNode);
   document.getElementById('newcontent').removeChild(elementA.parentNode.parentNode);

}