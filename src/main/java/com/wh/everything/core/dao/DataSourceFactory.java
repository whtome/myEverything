package com.wh.everything.core.dao;


import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 */
public class DataSourceFactory {

   /**
    * 数据源（单例）
    */
   private static volatile DruidDataSource dataSource;

   private DataSourceFactory(){}

   public static DataSource dataSource() {
      if (dataSource == null) {
         synchronized (DataSourceFactory.class) {
            if (dataSource == null) {
               //实例化
               dataSource = new DruidDataSource();
               dataSource.setDriverClassName("org.h2.Driver");
               //url,username,pssword
               //采用H2的嵌入式数据库，数据库以本地文件的形式存储，只需要提供url接口

                //JDBC规范中H2  jdbc:h2:filepath  ->存储到本地文件
                //JDBC规范中H2  jdbc:h2:~/filepath  ->存储到当前用户的home目录
                //JDBC规范中H2  jdbc:h2://ip:port/databaseName ->存储到服务器
               //获取当前工作目录
               String workDir = System.getProperty("user.dir");
               dataSource.setUrl("jdbc:h2:" + workDir + File.separator + "myEverything");
            }
         }
      }
      return dataSource;
   }

   public static void initDatabase(){

       //1.获取数据源
       DataSource dataSource = DataSourceFactory.dataSource();
       //2.获取SQL语句
       //读取classpath路径下的文件
       try(InputStream in = DataSourceFactory.class.getClassLoader().getResourceAsStream("myEverything.sql");){
           if (in == null) {
               throw new RuntimeException("读取出错，请检查");
           }

           StringBuilder stringBuilder = new StringBuilder();
           try( BufferedReader reader = new BufferedReader(new InputStreamReader(in));){
               String line = null;
               while((line= reader.readLine()) != null){
                   if(!line.startsWith("--")){
                       stringBuilder.append(line);
                   }
               }
           }

           //3.获取数据库连接和名称执行SQL
           String sql = stringBuilder.toString();
           //JDBC
           Connection connection = dataSource.getConnection();
           PreparedStatement statement = connection.prepareStatement(sql);
           statement.execute();

           statement.close();
           connection.close();

       }catch(IOException e){

       } catch (SQLException e) {
           e.printStackTrace();
       }

   }

}
