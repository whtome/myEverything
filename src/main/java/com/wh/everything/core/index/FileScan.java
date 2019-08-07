package com.wh.everything.core.index;

import com.wh.everything.core.interceptor.FileInterceptor;
import com.wh.everything.core.model.Thing;

public interface FileScan {

    //遍历path
    void index(String path);

    //遍历的拦截器
    void interceptor(FileInterceptor fileInterceptor);
}
