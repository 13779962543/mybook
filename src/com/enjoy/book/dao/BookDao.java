package com.enjoy.book.dao;

import com.enjoy.book.bean.Book;
import com.enjoy.book.util.DBHelper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BookDao {
    QueryRunner runner=new QueryRunner();

    /**
     * 根据类型查询对应的书籍信息
     * @param typeId
     * @return
     * @throws SQLException
     */
    public List<Book> getBookByTypeId(long typeId) throws SQLException {
        Connection conn= DBHelper.getConnection();
        String sql="select * from book where typeId=?";
        List<Book> books = runner.query(conn,sql,new BeanListHandler<Book>(Book.class),typeId);
        conn.close();
        return books;
    }

    public static void main(String[] args) {
        List<Book> books= null;
        try {
            books = new BookDao().getBookByTypeId(2);
            System.out.println(books.size());//[]:books对象存在，但是没有数据
            for(Book book:books){
                System.out.println(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
