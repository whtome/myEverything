package com.wh.everything.core.search.impl;

import com.wh.everything.core.dao.FileIndexDao;
import com.wh.everything.core.model.Condition;
import com.wh.everything.core.model.Thing;
import com.wh.everything.core.search.FileSearch;

import java.util.ArrayList;
import java.util.List;



public class FileSearchImpl implements FileSearch {


    private final FileIndexDao fileIndexDao;

    public FileSearchImpl(FileIndexDao fileIndexDao) {
        this.fileIndexDao = fileIndexDao;
    }

    @Override
    public List<Thing> serch(Condition condition) {

        //数据库的处理逻辑
        return this.fileIndexDao.search(condition);
    }
}
