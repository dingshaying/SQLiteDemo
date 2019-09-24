package com.example.test.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.test.entity.Student;
import com.example.test.utils.MyDBHelper;

import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDao {

    private MyDBHelper helper;
    private SQLiteDatabase dbs;

    public StudentDAOImpl(Context context){
        helper=new MyDBHelper(context);
    }

    @Override
    public List<Student> selectAllStudents() {
        String sql = "select * from student";
        List<Student> students = null;


        // 1. 获取SQLiteDatabase对象
        dbs = helper.getReadableDatabase();

        // 2. 执行SQL查询
        Cursor cursor = dbs.rawQuery(sql, null);
        // 3. 处理结果
        if (cursor != null && cursor.getCount() > 0) {
            students = new ArrayList<>();
            while (cursor.moveToNext()) {
                Student student = new Student();
                student.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                student.setName(cursor.getString(cursor.getColumnIndex("name")));
                student.setAge(cursor.getInt(cursor.getColumnIndex("age")));
                student.setClass_name(cursor.getString(cursor.getColumnIndex("class_name")));
                students.add(student);
            }
            // 4. 关闭cursor
            cursor.close();
        }
        // 5. 返回结果
        return students;
    }


    //给CursorAdapter适配器使用
    public Cursor selectByCursor() {
        dbs = helper.getReadableDatabase();
        return dbs.query("student",null,null,null,null,null,null);
    }




    @Override
    public void insert(Student student) {
        dbs = helper.getWritableDatabase();
        String sql = "insert into student values(null,?,?,?)";
        dbs.execSQL(sql, new Object[]{
                student.getName(),
                student.getClass_name(),
                student.getAge()});
        dbs.close();
    }

    @Override
    public void update(Student student) {
        // 1. 获取dbs对象
        dbs = helper.getWritableDatabase();
         //2. 执行sql
        String sql = "update student set student_id=? where name=?";
        dbs.execSQL(sql, new Object[]{
                student.getName(),
                student.getClass_name(),
        });
    }

    @Override
    public void delete(String studentName) {
        // 1. 获取db对象
        dbs = helper.getWritableDatabase();
        // 2. 执行sql
        String sql = "delete from student where name=?";
        dbs.execSQL(sql, new Object[]{ studentName });

    }
}