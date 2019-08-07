package com.wh.everything.core.dao.impl;

import com.wh.everything.core.dao.DataSourceFactory;
import com.wh.everything.core.dao.FileIndexDao;
import com.wh.everything.core.model.Condition;
import com.wh.everything.core.model.FileType;
import com.wh.everything.core.model.Thing;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FileIndexDaoImpl implements FileIndexDao {

    private final DataSource dataSource;

    public FileIndexDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void index(Thing thing) {

        Connection connection = null;
        PreparedStatement statement = null;

        try{
            //1.获取连接
            connection = dataSource.getConnection();
            //2.准备SQL语句
            String sql = "insert into file_index(name,path,depth,file_type)values (?,?,?,?)";

            //3.准备命令
            statement = connection.prepareStatement(sql);
            //4.设置参数
            statement.setString(1,thing.getName());
            statement.setString(2,thing.getPath());
            statement.setInt(3,thing.getDepth());
            //FileType.DOC ->DOC
            statement.setString(4,thing.getFileType().name());

            statement.executeUpdate();
        }catch (SQLException e){

        }finally {
            closeResource(null,statement,connection);
        }
    }

    @Override
    public List<Thing> search(Condition condition) {


        List<Thing> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try{
            connection = dataSource.getConnection();

            //name  :  like
            //fileType :  =
            //limit  :  limit offset
            //orderByAsc  :  order by
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder
                    .append("select name,path,depth,file_type from file_index");
            //name匹配：前模糊，后模糊，前后模糊
            stringBuilder
                    .append(" where ").append(" name like '%")
                    .append(condition.getName()).append("%' ");
            if (condition.getFileType() != null) {
                stringBuilder
                        .append("and file_type = '")
                        .append(condition.getFileType().toUpperCase())
                        .append("'");
            }
            //limit ， order必选
            stringBuilder
                    .append(" order by depth ")
                    .append(condition.getOrderByAsc() ? "asc" : "desc");
            stringBuilder
                    .append(" limit ")
                    .append(condition.getLimit())
                    .append(" offset 0 ");
            System.out.println(stringBuilder.toString());
            statement = connection.prepareStatement(stringBuilder.toString());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                //数据库中的行记录变成java中的对象
                Thing thing = new Thing();
                thing.setName(resultSet.getString("name"));
                thing.setPath(resultSet.getString("path"));
                thing.setDepth(resultSet.getInt("depth"));
                String fileType = resultSet.getString("file_type");
                thing.setFileType(FileType.lookupByName(fileType));

                list.add(thing);
            }
        }catch(SQLException e){

        }finally{
            closeResource(resultSet,statement,connection);
        }
        return list;
    }

    private void closeResource(ResultSet resultSet, PreparedStatement statement, Connection connection){
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection!= null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
