package com.wh.everything.core.search;

import java.util.List;

import com.sun.jdi.PathSearchingVirtualMachine;
import com.wh.everything.core.dao.DataSourceFactory;
import com.wh.everything.core.dao.impl.FileIndexDaoImpl;
import com.wh.everything.core.model.Condition;
import com.wh.everything.core.model.Thing;
import com.wh.everything.core.search.impl.FileSearchImpl;

public interface FileSearch {

    List<Thing> serch(Condition condition);

}
