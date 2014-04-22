<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>????</title>

<link href="http://www.see-source.com/bootstrap/css/bootstrap.css" rel="stylesheet">

<link href="http://www.see-source.com/bootstrap/css/docs.css" rel="stylesheet"">

<script type="text/javascript" src="http://www.see-source.com/bootstrap/js/jquery.js"></script>

<script type="text/javascript" src="http://www.see-source.com/bootstrap/js/bootstrap-carousel.js"></script>

 <style type="text/css">
        <%//??????%>
        body {background-color:black}
       
        </style>
      
        
   <Script language="JavaScript">
<%//????%>
var someText = "Smart Car";	// ????
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
</SCRIPT>

</head>

<body onLoad="loadText()">
    <center>
      <div id="textDiv"></div>
       </center>


 <!--?????-->
  <%
   int i=0;
   for(;i<4;i++)
       out.print("<br>");
   %>
   


<div id="myCarousel" class="carousel slide">

<ol class="carousel-indicators">

<li data-target="#myCarousel" data-slide-to="0"  class="" ></li>

<li data-target="#myCarousel" data-slide-to="1" class=""></li>

<li data-target="#myCarousel" data-slide-to="2" class="active"></li>

</ol>

    
    
<div class="carousel-inner">

<div class="item">

 <center>
<img src="<%=request.getContextPath()%>/images/1.1.png" width="450" height="450" />  
    </center>
    
    <%
   int k=0;
   for(;k<4;k++)
       out.print("<br>");
   %>

<div class="carousel-caption">

 <center>
        <p>Car</p>
        </center>

</div>

</div>

<div class="item">

<center>
<img src="<%=request.getContextPath()%>/images/2.png" width="450" height="450" />
    </center> 
    <%
   int l=0;
   for(;l<4;l++)
       out.print("<br>");
   %>
<div class="carousel-caption">

 <center>
        <p>Bookshelf</p>
        </center>

</div>

</div>

<div class="item active">

<center>
        <a href="index.jsp" data-toggle="tooltip" title="go forward">
<img src="<%=request.getContextPath()%>/images/3.jpg" />  
 <a href="index.jsp" data-toggle="tooltip" title="go back">
   <img src="<%=request.getContextPath()%>/images/5.jpg" />
    <%
       out.print("<br>");
   %>
    <a href="index.jsp" data-toggle="tooltip" title="go left">
    <img src="<%=request.getContextPath()%>/images/6.jpg" />
     <a href="index.jsp" data-toggle="tooltip" title="go right">
    <img src="<%=request.getContextPath()%>/images/4.jpg" />
    
    
    </center> 

</div>

</div>

<a class="left carousel-control" href="#myCarousel" data-slide="prev">?</a>

<a class="right carousel-control" href="#myCarousel" data-slide="next">?</a>

</div>

</body>

</html>

<script type="text/javascript">

$('#myCarousel').carousel('next');

</script>