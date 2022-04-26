package com.enjoy.book.action;

import com.enjoy.book.bean.Type;
import com.enjoy.book.biz.TypeBiz;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/type.let")
public class TypeServlet extends HttpServlet {
    TypeBiz typeBiz=new TypeBiz();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    /**
     * /type.let?type=add:添加(表单)
     * /type.let?type=modifypre 修改预备(req->转发->modifu.jsp)
     * /type.let?type=modify    修改(表单)
     * /type.let?type=remove&id=xx  删除(获取删除的类型编号)
     * /type_list.jsp 查询(所有类型数据:当前项目运行时候(监听器)，数据读取放到application对象)
     * servlet:
     *  request:同一个请求中传输信息
     *  session:同一个会话(用户)
     *  application:同一个运行的项目
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.设置编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        //获取各种对象
        PrintWriter out=resp.getWriter();
        ServletContext application=req.getServletContext();

        //3.根据用户的需求开始完成业务
        String type=req.getParameter("type");
        switch(type){
            case "add":
                add(req,resp,out,application);
                break;
            case "modifypre":
                modifyPre(req,resp,out,application);
                break;
            case "modify":
                modify(req,resp,out,application);
                break;
            case "remove":
                remove(req,resp,out,application);
                break;
        }
    }

    /**
     * type_add.jsp-->type.let?type=add-->ok-->type_list.jsp
     *                                      -->type_add.jsp
     * @param out
     * @param application
     */
    private void add(HttpServletRequest req, HttpServletResponse resp,PrintWriter out, ServletContext application) {
        //1.从表单中获取名字和父类型
        String typeName=req.getParameter("typeName");
        long parentId=Long.parseLong(req.getParameter("parentType"));
        //2.调用biz的添加方法
        int count=typeBiz.add(typeName,parentId);
        //3.更新application中的types
        if(count>0){
            List<Type> types=typeBiz.getAll();
            application.setAttribute("types",types);
            out.println("<script>alert('添加成功');location.href='type_list.jsp'</script>");
        }else{
            out.println("<script>alert('添加失败');location.href='type_add.jsp'</script>");
        }
        //4.提示结果
    }

    private void modifyPre(HttpServletRequest req, HttpServletResponse resp,PrintWriter out, ServletContext application) {

    }

    private void modify(HttpServletRequest req, HttpServletResponse resp,PrintWriter out, ServletContext application) {

    }

    private void remove(HttpServletRequest req, HttpServletResponse resp,PrintWriter out, ServletContext application) {
        //1.获取需要删除的id
        long id=Long.parseLong(req.getParameter("id"));
        //2.调用方法,biz异常

        //3.更新application

        //4.提示结果
    }

}
