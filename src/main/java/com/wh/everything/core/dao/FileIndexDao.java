package com.wh.everything.core.dao;

import com.wh.everything.core.model.Condition;
import com.wh.everything.core.model.Thing;

import java.util.List;


//业务层访问数据库的CRUD
public interface FileIndexDao {

    //插入
    void index(Thing thing);

    //检索
    List<Thing> search(Condition condition);
}
