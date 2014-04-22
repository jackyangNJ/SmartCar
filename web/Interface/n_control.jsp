<%-- 
    Document   : Control
    Created on : 2014-4-12, 13:56:46
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" import="smartcar.*,smartcar.map.*,smartcar.Interactor.*,smartcar.core.*"%>
<html>
    <head>
        <style type="text/css">
     l1{
   position:absolute;
    top:250px;
    left:470px;
    }
     l2{
   position:absolute;
    top:600px;
    left:600px;
    }
ul
{
float:left; 
list-style-type:none; 
margin:20px; 
}

</style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
       <form name="control_operation" action="/Smart_Car/operation" method="post">
         
           <center>
               <font size="4" color="orange"><i>控制行走</i></font>
               <br><br>
                <INPUT type="text" style="height:30px;width:60px" name="speed">
        &nbsp;&nbsp;
        <INPUT type="text" style="height:30px;width:60px" name="angular">
        </br></br>
        <br>
        <INPUT type="submit" style="height:20px;width:100px;" name="loginButton" value="速度和角度确认" onclick="information_control()">
        </br>
        <br><br>
        <a data-toggle="tooltip" title="go forward">
         <image src="<%=request.getContextPath()%>/images/4.jpg" id="img3" width="50" height="50" onclick="direction_control(1)"> 
    
 <a data-toggle="tooltip" title="go left">
   <image src="<%=request.getContextPath()%>/images/7.jpg" width="50" height="50" id="img6" onclick="direction_control(3)">
 
    <a data-toggle="tooltip" title="go right">
    <image src="<%=request.getContextPath()%>/images/5.jpg" width="50" height="50" id="img4" onclick="direction_control(4)">
 
   
     <a data-toggle="tooltip" title="go back">
    <image src="<%=request.getContextPath()%>/images/6.jpg" width="50" height="50" id="img5" onclick="direction_control(2)">
    
    <a data-toggle="tooltip" title="clockwise rotation">
    <image src="<%=request.getContextPath()%>/images/8.jpg" width="50" height="50" id="img8" onclick="direction_control(5)">
    
    <a data-toggle="tooltip" title="anti_clockwise">
    <image src="<%=request.getContextPath()%>/images/9.jpg" width="50" height="50" id="img9" onclick="direction_control(6)">
    
    </br></br>
    </center>
    
        <%
        for(int i=0;i<5;i++)
            out.print("<br>");
        %>
    <l1>
    
        <%
        //获取小车当前位置
        Interactor a=new Interactor();
        InteractorIf d=(InteractorIf)(a);
        Point e=d.getCarCurrentLocation();
        float x=e.getX();
        float y=e.getY();
        int carlocation_x=0;
        int carlocation_y=0;
        //out.println(carlocation_x);
        SmartMapInterface b=(SmartMapInterface)(new SmartMap());
        SmartMapInfo c=b.getMap();
        int numx=c.getNumofx();
        int numy=c.getNumofy();       //列数
        Node[][] map=c.getMap();
        for(int i=0;i<numx;i++){
        
         %>
        <%    for(int j=0;j<numy;j++)
           {   %>                        
           <%
               if(!map[i][j].getblack()){
                   if(i==carlocation_x&&j==carlocation_y){
                       
            %>
    <ul>
        <li style="color:#ff0000" type="disc"></li>
    </ul>
        <%
               }
        else{%>
              <ul>
        <li type="circle"></li>
    </ul> 
        <%
                   }
               }
               else{
                   if(i==carlocation_x&&j==carlocation_y){                       
                   %>
        <ul>
        <li style="color:#ff0000" type="disc"></li>
    </ul>
                   <%
                   }
                   else{%>
                   <ul>
        <li type="square"></li>
    </ul> 
        <%
                   }
           }
        %>
        <%
           }
       out.print("<br>");
        
        }
        %>  
          
      </l1>
         
        <l2>       
           <font size="4" color="orange"><i>&nbsp&nbsp&nbsp&nbsp&nbsp自主行走</i></font>
           <br><br>
        <INPUT type="text" style="height:30px;width:60px" name="destination_x">
        &nbsp;&nbsp;
        <INPUT type="text" style="height:30px;width:60px" name="destination_y">
        </br></br>
        <center>
        <br>
        <INPUT type="submit" style="height:20px;width:70px;" name="loginButton" value="终点确认" >
        </br>
            </center> 
        </l2
       
        
  <canvas id="myCanvas"></canvas>

<script language=javascript>

var canvas=document.getElementById('myCanvas');
var ctx=canvas.getContext('2d');
//绘制没有填充的矩形
ctx.strokeRect(0,0,5,5);
  
</script>  
    
    
    <%
   String temp4=(String)session.getAttribute("Speed");
   String temp5=(String)session.getAttribute("Angular");
   if(temp4!=null){
      // int temp4_i=Integer.parseInt(temp4);
       out.println(temp4);
   }
   if(temp5!=null){
      // int temp5_i=Integer.parseInt(temp5);
        out.println(temp5);
   }
    session.removeAttribute("Speed");
    session.removeAttribute("Angular");
    %>
    
    <%
    Integer temp1=(Integer)session.getAttribute("opNumber");
    if(temp1!=null)
    out.println(temp1);
    String temp2=(String)session.getAttribute("return_destination_x");
    String temp3=(String)session.getAttribute("return_destination_y");
    
    if(temp2!=null)
        out.println(temp2);
    if(temp3!=null)
        out.println(temp3);
    
    session.removeAttribute("return_destination_x");
    session.removeAttribute("return_destination_y");
    session.removeAttribute("opNumber");
    
    %>
    
 <input type="hidden" name="operation" id="op" value="0">       
        <script language="javascript">  
        function direction_control(a){  
 document.getElementById("op").value=a;
 document.control_operation.action="/WebApplication2/operation";
 document.control_operation.submit();
  }
   function information_control(){  
 document.getElementById("op").value=7;
 document.control_operation.action="/WebApplication2/operation";
 document.control_operation.submit();
  }
  </script>
        
        
        
    </body>
</html>
