package com.swein.framework.tools.util.debug.log;


import com.swein.framework.tools.util.debug.log.factroy.basiclog.BasicLog;

/**
 * Created by seokho on 19/04/2017.
 */

public interface ILogFactory {
    BasicLog getBasicLog();
}
