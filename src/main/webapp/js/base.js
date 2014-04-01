(function ($) {
    var $line = $("#line");
    var Resource = {
        type : "max",//max,new,pdf,word,ppt,other
        limit : 30,
        keyword : "",
        setType : function(type){
            this.type = type;
            this.refresh();
        },
        setKeyword : function(keyword){
            this.keyword = keyword;
            this.refresh();
        },
         setProperty : function(keyword,type){
             this.keyword = keyword;
             this.type = type;
             this.refresh();
         } ,
        refresh : function(){
            $.post("resourceOperation/get.json",{type:this.type,limit:this.limit,keyword :this.keyword},function(data){
                var rl = data.resources,html = "",nav = "";
                for(var i=0;i<data.pageCount;i++){
                    nav += '<li><a href="javascript:void(0)" '+ 'page-no="' + (i+1)+ '"' +(i==0?'class="cur"':'') +'></a></li>';
                }
                if(data.pageCount == 0){
                    nav += '<span style="color:grey;font-weight: bold;font-size: 13px">搜索结果为空...</span>';
                }
                for(var i=0;i<rl.length;i++ ){
                   if(i%10 == 0) {
                       html += '<li class="main-item"><ul class="sub-list">';
                   }
                   html += '<li class="sub-item "><b>'
                           + '<img src="../img/' + rl[i].resourceType + '.png" class="book-cover"/> <span class="why-reco">'+rl[i].resourceRealName.replace(Resource.keyword,"<span style='color:black'>"+Resource.keyword +"</span>") +'</span> </b>'
                           + '<p uuid="' +rl[i].uploadUserId +'"><a href="javascript:void(0)" class="preview" r-id="'+ rl[i].id +'">预览</a>&nbsp;<a href="resourceOperation/download.html?resourceId=' + rl[i].id +'">下载</a></p></li> ';
                   if(i%10 == 9 || i == rl.length-1) {
                       html += '</ul></li>';
                   }
                }
                $("#main-list").html(html).css("left",0);
                $("#nav-list").html(nav);

            });
        }
    };
    function initUploadBtn(){
        $("#Filedata").commonUpload({"width":"157px", "height":"45px", "onUploadSuccess":function (file, data, response) {
            Resource.refresh();
        }});
    }
    function initPage(){
       $("#main-list").delegate("a","click",function(e){
           var $this = $(this);
           if(!user){
               alert("请您先登录!");
               e.preventDefault();
               e.stopPropagation();
               return;
           }else if(user == $this.parent("p").attr("uuid")){ }
           else if(!confirm("下载或预览要扣掉 2 金币，确定此操作?")){
               e.preventDefault();
               e.stopPropagation();
               return;
           }
           if($this.hasClass("preview")){
               var id = $this.attr("r-id");
               previewDoc(id);
           }
           if(user != $this.parent("p").attr("uuid")){
               var $login_mark = $("#login-mark");
               $login_mark.text($login_mark.text()-2);
           }
       });
        $("#nav-list").delegate("a","click",function(){
            var pageNo = $(this).attr("page-no");
            switchPage(pageNo);
        });
    }

    function switchPage(pageNo){
        $("#main-list").css({"left":0-(pageNo-1)*775 +"px"},200);
        $("#nav-list a").each(function(){
          var $this = $(this);
          if($this.hasClass("cur")){
              $this.removeClass("cur");
          }
          if($this.attr("page-no") == pageNo){
              $this.addClass("cur");
          }
        });
    }
    function bindQueryInput(){
        var $max = $("#b-l-title a[status=max]");
        $("#query").bind("input",function(){
            var val = $.trim($(this).val());
            Resource.keyword = val;
            $("#back").click();
            $max.click();
        });
    }

    function bindBackBtn(){
        $("#back").bind("click",function(e){
           $("#preview-box").hide();
           $("#p-title").hide();
           $("#b-l-title").show();
           $("#b-l .line").show();
           $("#upload-box").show();
           $("#rd-box").hide();
           e.stopImmediatePropagation();
           return;
        });
    }

    function previewDoc(id){

        $.get("resourceOperation/preview.json",{resourceId:id},function(data){
            if(data.isImg){
                var link = $("#main-list").find("[r-id="+id+"]");
                var left = link.offset().left,top = link.offset().top;
                var $img_view = $("#img-view");
                if($img_view.is(":visible")){
                    $img_view.hide();
                    return;
                }
                $img_view.show().css({"left":left-14,"top":top+20}).find("img")
                    .attr("src","resourceOperation/imgp.json?resourceId=" + id);
                return;
            }
            $("#preview-box").show();
            $("#b-l-title").hide();
            $("#p-title").show();
            $("#b-l .line").hide();
            $("#upload-box").hide();
            $("#rd-box").show();
            $("#upload-name").text(data.uploaderName);
            $("#upload-time").text(data.uploadTime.split(" ")[0]);
            $("#r-name").text(data.resourceName);
            var resourceSize = data.resourceSize;
            resourceSize = resourceSize<1000 ? resourceSize + "KB" : (resourceSize/10000).toFixed(2) + "MB";
            $("#r-size").text(resourceSize);
            $("#download-times").text(data.downloadTimes);
            var fp = new FlexPaperViewer(
                'FlexPaper_1.4.5_flash/FlexPaperViewer',
                'viewerPlaceHolder', { config : {
                    SwfFile : data.swfPath,
                    Scale : 1,
                    ZoomTransition : 'easeOut',
                    ZoomTime : 0.5,
                    ZoomInterval : 0.2,
                    FitPageOnLoad : true,
                    FitWidthOnLoad :true,
                    PrintEnabled : true,
                    FullScreenAsMaxWindow : false,
                    ProgressiveLoading : false,
                    MinZoomSize : 0.2,
                    MaxZoomSize : 5,
                    SearchMatchAll : true,
                    InitViewMode : 'Portrait',
                    ViewModeToolsVisible : true,
                    ZoomToolsVisible : true,
                    NavToolsVisible : true,
                    CursorToolsVisible : true,
                    SearchToolsVisible : true,
                    localeChain: 'en_US'
                }});
        })
    }
    $(".b-title").delegate("span", "click", function (e,p) {
        var $this = $(this),$user_slide = $("#user-slide");
        if($this.parent("#b-r-title").size()!=0){
            if($this.is("#login")) {
                $user_slide.animate({left:"0px"},200);
            }else{
                $user_slide.animate({left:"-277px"},200);
            }
        }else{
            Resource.setType($this.find("a").attr("status"));
        }
        var $line = $this.parents('.b-title').prev(".line") ,left = $this.position().left - 1, width = $this.width();
        $line.stop(true, true).animate({left:left, width:width / 1 + 1}, 200);
        $this.siblings().find("a").css("color", "#555").end().end().find("a").css("color", "#25b686");
    });
    (function($){
        var $username = $("#username"),$password = $("#password"),$log_m = $("#log-msg");
        $("#log-btn").bind("click",function(){
            if($.trim($username.val())=="") {
                $log_m.text("用戶名不能为空!").stop(true,true).fadeIn(200).fadeOut(5000);
                return;
            }
            if($.trim($password.val())=="") {
                $log_m.text("密碼不能为空!").stop(true,true).fadeIn(200).fadeOut(5000);
                return;
            }
            var param = {username:$.trim($username.val()),password:$.trim($password.val())};
            $.get("login.json",param,function(data){
                if(data.login == "ERROR"){
                    $log_m.text("用户名或密码错误!").stop(true,true).fadeIn(200).fadeOut(5000);
                }else{
                    $("#login-box").find("input").val("");
                    $("#user-box").hide();
                    $("#upload-box").show().find("#login-username").text(data.user.userName).end().find("#login-mark").text(data.user.userMark);
                    $("#b-r-title").hide();
                    $("#b-r-title-1").show();
                    $("#b-r .line").hide();
                    user = data.user.id;
                    initUploadBtn();
                }
            });
        })
    })(jQuery);
    (function($){
        $("#logout").bind("click",function(){
            $.get("logout.json",function(data){
                if(data.logout=="OK"){
                    $("#user-box").show();
                    $("#upload-box").hide();
                    $("#b-r-title").show();
                    $("#b-r-title-1").hide();
                    $("#b-r .line").show();
                    user = "";
                }
            });
        });
    })(jQuery);
    (function($){
        var $reg_name = $("#reg-name"),$reg_pwd = $("#reg-pwd"),$reg_rpwd = $("#reg-rpwd"),$reg_m = $("#reg-msg");
        $("#reg-btn").bind("click",function(){
            var $reg_name_val = $.trim($reg_name.val()),$reg_pwd_val = $.trim($reg_pwd.val()),$reg_rpwd_val = $.trim($reg_rpwd.val());
            if($reg_name_val == "") {
                $reg_m.text("用戶名不能為空!").stop(true,true).fadeIn(200).fadeOut(5000);
                return;
            }
            if($reg_pwd_val == ""){
                $reg_m.text("密碼不能为空!").stop(true,true).fadeIn(200).fadeOut(5000);
                return;
            }
            if($reg_rpwd_val == ""){
                $reg_m.text("请再次确认密码!").stop(true,true).fadeIn(200).fadeOut(5000);
                return;
            }
            if($reg_rpwd_val != $reg_pwd_val){
                $reg_m.text("两次输入的密码不同!").stop(true,true).fadeIn(200).fadeOut(5000);
                return;
            }
            var param = {username:$reg_name_val,password:$reg_pwd_val};
            $.post("reg.json",param,function(data){
                if(data.register == "exists"){
                    $reg_m.text("用户名已被使用!").stop(true,true).fadeIn(200).fadeOut(5000);
                    return;
                }
                if(data.register == "OK"){
                    $reg_m.html("<span style='color:green'>注册成功!</span> &nbsp;<a href='javascript:$(\"#login\").click()'>登陆</a>").stop(true,true).fadeIn(200).fadeOut(10000);
                    $("#reg-box").find("input").val("");
                }
            });
        });
    })(jQuery);
    $(function(){
        Resource.refresh();
        user && initUploadBtn();
        initPage();
        bindQueryInput();
        bindBackBtn();
        $("body").bind("click",function(e){
            var $t = $(e.target);
            if($t.parent("#img-view").size()!=0 || $t.is("#img-view")){
                return;
            }
            $("#img-view").hide();
        });
    });
})(jQuery);
