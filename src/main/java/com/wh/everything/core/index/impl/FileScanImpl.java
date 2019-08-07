package com.wh.everything.core.index.impl;


import com.wh.everything.config.EverythingPlusConfig;
import com.wh.everything.core.dao.DataSourceFactory;
import com.wh.everything.core.dao.impl.FileIndexDaoImpl;
import com.wh.everything.core.index.FileScan;
import com.wh.everything.core.interceptor.FileInterceptor;
import com.wh.everything.core.interceptor.impl.FileIndexInterceptor;
import com.wh.everything.core.interceptor.impl.FilePrintInterceptor;
import com.wh.everything.core.model.Thing;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FileScanImpl implements FileScan {

    //DAO
    private EverythingPlusConfig config = EverythingPlusConfig.getInstance();

    private LinkedList<FileInterceptor> interceptors = new LinkedList<>();

    @Override
    public void index(String path) {

        File file = new File(path);
        List<File> fileList = new ArrayList<>();
        if (file.isFile()) {
            //D:\a\b\xx.pdf   -> D:\a\b
            if (config .getExcludPath().contains(file.getParent())) {
                return;
            }

        }else {
            if (config.getExcludPath().contains(path)) {
                return;
            }else {
                File[] files = file.listFiles();
                if (files != null) {
                    for (File f : files) {
                        index(f.getAbsolutePath());
                    }
                }
            }
        }
        //File Directory
        for (FileInterceptor interceptor : this.interceptors) {
            interceptor.apply(file);
        }
    }

    @Override
    public void interceptor(FileInterceptor fileInterceptor) {
        this.interceptors.add(fileInterceptor);
    }

}
