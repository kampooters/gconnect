package com.tb.gconnect.logger;

import org.slf4j.Marker;


/**
 * @author abdul.rehman4 12th/07/2019
 * @version 1.0
 * @since v1.0
 *
 * This class provides structure for ADAPTER
 * The {@link ILogger} provides the structure for ADAPTEE, by default we use {@link SL4JLoggerAdaptee} as default adaptee
 */
public interface ILogManager {
    String ROOT_LOGGER_NAME = "ROOT";

    String getName();

    public void requestReceived(String path, Object requestDTO);
    public void requestServed(String path, Object respDTO);

    boolean isTraceEnabled();

    void trace(String var1);

    void trace(String var1, Object var2);

    void trace(String var1, Object var2, Object var3);

    void trace(String var1, Object... var2);

    void trace(String var1, Throwable var2);

    boolean isTraceEnabled(Marker var1);

    void trace(Marker var1, String var2);

    void trace(Marker var1, String var2, Object var3);

    void trace(Marker var1, String var2, Object var3, Object var4);

    void trace(Marker var1, String var2, Object... var3);

    void trace(Marker var1, String var2, Throwable var3);

    boolean isDebugEnabled();

    void debug(String var1);

    void debug(String var1, Object var2);

    void debug(String var1, Object var2, Object var3);

    void debug(String var1, Object... var2);

    void debug(String var1, Throwable var2);

    boolean isDebugEnabled(Marker var1);

    void debug(Marker var1, String var2);

    void debug(Marker var1, String var2, Object var3);

    void debug(Marker var1, String var2, Object var3, Object var4);

    void debug(Marker var1, String var2, Object... var3);

    void debug(Marker var1, String var2, Throwable var3);

    boolean isInfoEnabled();

    void info(String var1);

    void info(String var1, Object var2);

    void info(String var1, Object var2, Object var3);

    void info(String var1, Object... var2);

    void info(String var1, Throwable var2);

    boolean isInfoEnabled(Marker var1);

    void info(Marker var1, String var2);

    void info(Marker var1, String var2, Object var3);

    void info(Marker var1, String var2, Object var3, Object var4);

    void info(Marker var1, String var2, Object... var3);

    void info(Marker var1, String var2, Throwable var3);

    boolean isWarnEnabled();

    void warn(String var1);

    void warn(String var1, Object var2);

    void warn(String var1, Object... var2);

    void warn(String var1, Object var2, Object var3);

    void warn(String var1, Throwable var2);

    boolean isWarnEnabled(Marker var1);

    void warn(Marker var1, String var2);

    void warn(Marker var1, String var2, Object var3);

    void warn(Marker var1, String var2, Object var3, Object var4);

    void warn(Marker var1, String var2, Object... var3);

    void warn(Marker var1, String var2, Throwable var3);

    boolean isErrorEnabled();

    void error(String var1);

    void error(String var1, Object var2);

    void error(String var1, Object var2, Object var3);

    void error(String var1, Object... var2);

    void error(String var1, Throwable var2);

    boolean isErrorEnabled(Marker var1);

    void error(Marker var1, String var2);

    void error(Marker var1, String var2, Object var3);

    void error(Marker var1, String var2, Object var3, Object var4);

    void error(Marker var1, String var2, Object... var3);

    void error(Marker var1, String var2, Throwable var3);
}
