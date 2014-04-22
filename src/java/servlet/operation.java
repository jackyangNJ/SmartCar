/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import smartcar.Interactor.InteractorIf;
import smartcar.Interactor.Interactor;
import smartcar.core.Point;


/**
 *
 * @author Administrator
 */
public class operation extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session=request.getSession(true);
        String number=request.getParameter("operation");
        int temp=number.charAt(0)-'0';                       //获得整型值
        /*Interactor a=new Interactor();
        InteractorIf b=(InteractorIf)a;*/
        //控制行走
        if(temp!=0){
            //赋予速度和角度
            if(temp==7){
                
                //设置小车的速度和角度
                String speed=request.getParameter("speed");
                //int Speed=Integer.parseInt(speed);                
                String angular=request.getParameter("angular");
               // int Angular=Integer.parseInt(angular);
              /*  b.setCar(speed,angle);
                */
                session.setAttribute("Speed", speed);
                session.setAttribute("Angular",angular);
                session.setAttribute("opNumber", temp);
            }
            //顺时针旋转
            else if(temp==5){
            //b.setCarClockwise();
                session.setAttribute("opNumber", temp);
            }
            //逆时针旋转
            else if(temp==6){
             //  b.setCarCounterClockwise
                session.setAttribute("opNumber", temp);
            }
            else{
            session.setAttribute("opNumber", temp);
        
        //给小车传递方向
       /* b.setOperation(temp);
        */
            }
        }
        //自主行走
        else{
            //获得目的地
            String x=request.getParameter("destination_x");
            String y=request.getParameter("destination_y");
            session.setAttribute("return_destination_x",x);
            session.setAttribute("return_destination_y",y);
            //给小车传递目的地址
            /*float x_des = Float.parseFloat(x);
            float y_des=Float.parseFloat(y);
            Point location=new Point();
            location.set(x_des, y_des);
            b.setCarAutoDriveDestination(location);*/
        }
        RequestDispatcher dispatcher=request.getRequestDispatcher("/Interface/Control.jsp");
        dispatcher.forward(request, response);
        
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet operation</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet operation at " + request.getContextPath() + "</h1>");
            out.println("<center>");
            out.println(temp);
            out.println("</center>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
