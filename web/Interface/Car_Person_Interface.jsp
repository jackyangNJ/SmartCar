<%-- 
    Document   : Car_Person_Interface
    Created on : 2014-3-13, 22:24:12
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%--导入java包--%>
<%@page language="java" import="src.SmartMap"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
         <link rel="stylesheet" href="css/bootstrap.css">
         <link href="http://www.see-source.com/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="http://www.see-source.com/bootstrap/css/docs.css" rel="stylesheet">
<script type="text/javascript" src="http://www.see-source.com/bootstrap/js/jquery.js"></script>
<script type="text/javascript" src="http://www.see-source.com/bootstrap/js/bootstrap-carousel.js"></script>
        <style type="text/css">
        <%//设置背景颜色%>
        body {background-color:black}
       
        </style>
        <!--
    <meta http-equiv="refresh" content="11;url=/IFTTT/UserAndServer/Checklogin.jsp">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">   -->
        
   <Script language="JavaScript">
<%//闪烁文本%>
var someText = "Smart Car";	// 修改内容
var aChar;
var aSentence;
var i=0;	 // a counter

//var colors = new Array("FF0000","FFFF66","FF3399","00FFFF","FF9900","00FF00"); // the colors
var colors = new Array("FF0000","FFFF66","00FF00");
var aColor;	 // the chosen color

function loadText()
{
// randomly choose color
aColor = colors[Math.floor(Math.random()*colors.length)];

aChar = someText.charAt(i);
if (i == 0)
aSentence = aChar;
else
aSentence += aChar;	

// 50 iterations max.
if (i < 50)	i++;

// For IE
if (document.all)
{
textDiv.innerHTML= "<font color='#"+aColor+"' face='Tahoma' size='23'><i>"+aSentence+"</i></font>";
setTimeout("loadText()",50);
}

// For Netscape Navigator 4
else if (document.layers) 
{
document.textDiv.document.write("<font color='#"+aColor+"' face='Tahoma' 	 size='23'><i>"+aSentence+"</i></font>");
document.textDiv.document.close();
setTimeout("loadText()",50);
}

// For other
else if (document.getElementById)
{
document.getElementById("textDiv").innerHTML = "<font color='#"+aColor+"' 	 face='Tahoma'size='23'><i>"+aSentence+"</i></font>";
setTimeout("loadText()",50);
}
}

var times=11; 
clock(); 
function clock() 
{  if(times!=0){
   window.setTimeout('clock()',1000);
   times=times-1; 
   time.innerHTML =times; 
 }  
} 

</SCRIPT>
        
  







        
    <!--闪烁文本-->    
    </head>
    <body onLoad="loadText()">
  <%--定义表单--%>
   <form name="Web_control" action="" method="post">
            <%
        SmartMap map=new SmartMap();
        map.set(1);
        int x=map.get();
        out.print(x);
        %>
        
    <center>
      <div id="textDiv"></div>
       </center>
   

    
    <!--输出换行符-->
  <%
   int i=0;
   for(;i<4;i++)
       out.print("<br>");
   %>
   
     <!--旋转木马图片事例--> 
   <div id="myCarousel" class="carousel slide">
<!--   
<ol class="carousel-indicators">
<li data-target="#myCarousel" data-slide-to="0" class=""></li>
<li data-target="#myCarousel" data-slide-to="1" class=""></li>

</ol>
 -->
<div class="carousel-inner">
    
<div class="item">   
    <center>
<image src="<%=request.getContextPath()%>/images/1.png" id="img1" width="450" height="450" onclick="change(2)">  
    </center>
    
    <%
   int k=0;
   for(;k<4;k++)
       out.print("<br>");
   %>
    <div class="carousel-caption">
        <center>
        <p><%--Car--%>
        </p>
        </center>
</div>
</div>

    

    

<div class="item">
 <center>
<img src="<%=request.getContextPath()%>/images/2.png" id="img2" width="450" height="450" />
    </center> 
    <%
   int l=0;
   for(;l<4;l++)
       out.print("<br>");
   %>
    <div class="carousel-caption">
        <center>
        <p><%--Bookshelf--%>
        </p>
        </center>
</div>
</div>

    
    <div class="item"> 
    <center>
        <%
   for(int h=0;h<4;h++)
       out.print("<br>");
   %>
  <image src="<%=request.getContextPath()%>/images/3.png" id="img3">
 
    </center> 
 
</div>
    

 
    <!--超链接
<a class="left carousel-control" href="#myCarousel" data-slide="prev">‹</a>
<a class="right carousel-control" href="#myCarousel" data-slide="next">›</a>-->
</div>
    <%--用于传递操作值--%>

    </body>
    
    <script language=javascript>
        
  function change(flag){
     var src = document.getElementById("img1").src;
		var str = src.split("/");
		var index = str[str.length-1].split(".")[0];
		var nextIndex = parseInt(index)+1;
		var previousIndex = (parseInt(index)-1);
		
		var newStr = "";

		for(var i=0;i<str.length-1;i++)
		{
			newStr = newStr + str[i] + "/";
		}
		//查看前一张
		if(flag == 1)
		{
			document.getElementById("img1").src = newStr + previousIndex + "." + str[str.length-1].split(".")[1];
		}
                //查看
		else
		{
                  if(index==3){
                  document.Web_control.action="Control.jsp";
                  document.Web_control.submit();
                  }
                  else{
                  document.getElementById("img1").src = newStr + nextIndex + "." + str[str.length-1].split(".")[1];
              }
		}
  }
    </script>
    
</html>

<script type="text/javascript">
$('#myCarousel').carousel('next');
</script>

