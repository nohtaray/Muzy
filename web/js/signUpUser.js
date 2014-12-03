function read_id(){
    var myVal=document.forms[0].elements["email"].value;
    if(myVal.length<1||myVal.length>31){
    alert("1～31字の半角英数字にして下さい！！");
    document.getElementById(".baloonOK").style.visibility="visible";
    document.getElementById("#baloonOK").style.visibility="visible";
    return false;
    }
 //   if(!myVal.match(/^[A-Za-z]\w+/)){
   // alert("4～31字の半角英数字にして下さい！！\n最初の文字はアルファベットとしてください！！");
   // return false;
   //  }
     /*
    var a = new Ajax.Request({
        type     : 'POST',
        dataType : 'text',
        url      : 'UniqueCheck', //ここにServletのアドレス
        //ここがServletに渡される値
        data     : {
                     email   : myVal,
                     Flg  : true
                   }
    }).done(function(data) {

        $("#disp_area").html(data);
    }).fail(function(data) {
        // ajax error
        $("#disp_area").html('XYZ');
    }).always(function(data) {
        // ajax complete
    });
    }
    */
///onFailureの処理を書く
    new Ajax.Request("UniqueCheck",{method:"post",onSuccess:function(httpObj){set_ok(httpObj);},
                                                  onError:function(httpObj){set_no(httpObj);}});
    //return false;
    function set_ok(){
        document.getElementById("baloonOK").style.visibility="visible";
    }  
    function set_no(){
        document.getElementById("baloonOK").style.visibility="visible";
    }
        
     
     function xx(){
        $("caution").style.display="block";
     }
     function yy(){
        $("caution").style.display="none";
     }
     function zz(obj){
        btn=document.forms[0].elements["bt"];
        if(obj.value!=""){
            obj.style.backgroundColor="#FFFF99";
            btn.disabled=false;
        }else{
            obj.style.backgroundColor="white";
            btn.disabled=true;
        }
    }
}