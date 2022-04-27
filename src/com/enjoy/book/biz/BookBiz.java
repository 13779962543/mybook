package com.enjoy.book.biz;

import com.enjoy.book.bean.Book;
import com.enjoy.book.bean.Type;
import com.enjoy.book.dao.BookDao;
import com.enjoy.book.dao.TypeDao;

import java.sql.SQLException;
import java.util.List;

public class BookBiz {
    //BookDao对象
    BookDao bookDao = new BookDao();
    public List<Book> getBookByTypeId(long typeId){

        try {
            return bookDao.getBookByTypeId(typeId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public int add(long typeId, String name, double price, String desc, String pic,
                   String publish, String author, long stock, String address){
        int count=0;
        try {
            count=bookDao.add(typeId,name,price,desc,pic,publish,author,stock,address);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;

    }
    public int add(Book book){
        return add(book.getTypeId(),book.getName(),book.getPrice(),book.getDesc(),book.getPic(),book.getPublish(),book.getAuthor(),book.getStock(),book.getAddress());
    }

    public int modify(long id, long typeId, String name, double price, String desc, String pic,
                      String publish, String author, long stock, String address){
        int count=0;
        try {
            count= bookDao.modify(id,typeId,name,price,desc,pic,publish,author,stock,address);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;
    }

    public int remove(long id){
        int count=0;
        try {
            count= bookDao.remove(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;
    }

    public List<Book> getByPage(int pageIndex, int pageSize){
        TypeDao typeDao=new TypeDao();
        List<Book> books=null;
        try {
            books=bookDao.getByPage(pageIndex,pageSize);
            //处理type对象的数据问题
            for(Book book:books){
                long typeId=book.getTypeId();
                book.getType();//null
                //根据typeid找到对应的type对象
                Type type=typeDao.getById(typeId);
                //设置给book，setType()
                book.setType(type);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return books;
    }

    public Book getById(long id){
        try {
            return bookDao.getById(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    /**
     * 由行数算页数
     * @return
     */
    public int getPageCount(int pageSize){
        int pageCount=0;
        try {
            //1.获取行数
            int rowCount=bookDao.getCount();
            //2.根据行数得到页数,每页多少条
            pageCount=(rowCount-1)/pageSize+1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return pageCount;
    }
}