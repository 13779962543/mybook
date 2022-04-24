package com.enjoy.book.action;

import com.enjoy.book.bean.User;
import com.enjoy.book.biz.UserBiz;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user.let")
public class UserServlet extends HttpServlet {
    //构建UserBiz对象
    UserBiz userBiz=new UserBiz();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    /**
     * /user.let?type=login 登录 /:项目的根目录-->web
     * web
     *   /-login.html
     *   /-user.let?type=login
     *   /-index.jsp
     * /user.let?type=exit 安全退出
     * /user.let?type=modifyPwd 修改密码
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session=req.getSession();
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out=resp.getWriter();

        //1.判断用户请求的类型为login
        String method=req.getParameter("type");
        switch(method){
            case "login":
                //2.从login.html中获取用户名和密码
                String name=req.getParameter("name");
                String pwd=req.getParameter("pwd");
                String userCode=req.getParameter("valcode");

                //2.2 提取session中的验证码,进行判断
                String code=session.getAttribute("code").toString();
                //不区分大小写
                if(!code.equalsIgnoreCase(userCode)){
                    out.println("<script>alert('验证码输入错误');location.href='login.html';</script>");
                    return;
                }

                //3.调用UserBiz的getUser方法，根据用户名和密码获取对应的用户对象
                User user=userBiz.getUser(name,pwd);

                //4.判断用户对象是否为null
                if(user==null){
                    //  4.1 如果是null表示用户名或密码不正确，提示错误，回到用户界面
                 out.println("<script>alert('用户名或密码不存在');location.href='login.html';</script>");
                }else{
                   //  4.2 非空，表示登录成功，将用户对象保存到session中，提示登录成功后，将页面跳转到index.jsp
                 session.setAttribute("user",user);
                 out.println("<script>alert('登录成功');location.href='index.jsp';</script>");
                }
                break;
        }
    }
}
