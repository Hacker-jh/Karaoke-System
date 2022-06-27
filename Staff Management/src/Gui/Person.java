package Gui;

import java.sql.Blob;
import java.sql.Clob;

public class Person{
   public int pId;         //员工编号
   public String pName;       //员工姓名
   public String pSex;        //员工性别0,1
   public String plv;            //员工级别 1,2,3
   public int pAge;      //出生年月
   public String pPhone;    //手机号
   public Blob pPhoto;        //照片
   public Clob worker_info;  //员工考核信息
   public float pSalary;     //工资
   public String ispay;          //是否欠薪0,1
    //String pAssess;     //是否考核



    public Person(Person person){
        this.pId=person.pId;         //员工编号
        this.pName=person.pName;       //员工姓名
        this.pSex=person.pSex;        //员工性别
        this.pAge=person.pAge;      //出生年月
        this.plv=person.plv;            //员工级别

        this.pPhone=person.pPhone;    //手机号
        this.pPhoto=person.pPhoto;        //照片
        this.worker_info=person.worker_info;  //员工考核信息
        this.pSalary=person.pSalary;     //工资
        this.ispay=person.ispay;          //是否欠薪
    }
    public Person(){};
}
