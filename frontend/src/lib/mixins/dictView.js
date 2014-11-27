/**
 * Created by user on 2014/11/25.
 */

function showAdd(dict){
    var detailItems = dict.details;
    for(var i = 0;i<detailItems.length;i++) {
        var detail = detailItems[i];
        add(detail);
    }
}
var editCount = 0;
function add(detail) {
    editCount++;
    //自增id不同的HTML对象，并附加到容器最后

    var elementDiv = document.createElement("div");
    elementDiv.setAttribute("id","create"+editCount);
    elementDiv.setAttribute("class","col-md-offset-2 col-md-8");
    document.getElementById("editcontent").appendChild(elementDiv);

    var elementLabel = document.createElement("label");
    var textLable = document.createTextNode("值:");
    elementLabel.setAttribute("for","");
    elementLabel.setAttribute("class","control-label col-md-2");
    elementLabel.appendChild(textLable);

    var elementDiv2 = document.createElement("div");
    var elementInput = document.createElement("input");
    elementDiv2.setAttribute("class","col-md-3");
    elementInput.setAttribute("type","text");
    elementInput.setAttribute("readonly","readonly");
    elementInput.setAttribute("name","keyInput");
    elementInput.setAttribute("class","form-control");
    elementInput.setAttribute("value",detail?detail.key:'');
    elementDiv2.appendChild(elementInput);
    //-----------------------------------------------
    var elementLabel2 = document.createElement("label");
    var textLable2 = document.createTextNode("名称:");
    elementLabel2.setAttribute("for","");
    elementLabel2.setAttribute("class","control-label col-md-2");
    elementLabel2.appendChild(textLable2);


    var elementDiv3 = document.createElement("div");
    elementDiv3.setAttribute("class","col-md-3");
    var elementInput2 = document.createElement("input");
    elementInput2.setAttribute("type","text");
    elementInput2.setAttribute("readonly","readonly");
    elementInput2.setAttribute("class","form-control");
    elementInput2.setAttribute("name","valueInput");
    elementInput2.setAttribute("value",detail?detail.value:'');
    elementDiv3.appendChild(elementInput2);



    elementDiv.appendChild(elementLabel);
    elementDiv.appendChild(elementDiv2);
    elementDiv.appendChild(elementLabel2);
    elementDiv.appendChild(elementDiv3);

}
