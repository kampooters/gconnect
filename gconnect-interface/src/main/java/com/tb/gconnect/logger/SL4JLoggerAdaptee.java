package com.tb.gconnect.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

public class SL4JLoggerAdaptee implements ILogger {

    private Logger logger;


    public SL4JLoggerAdaptee(Class clazz){
        this.logger = LoggerFactory.getLogger(clazz);
    }

    public SL4JLoggerAdaptee(){
        this.logger = LoggerFactory.getLogger(SL4JLoggerAdaptee.class);
    }

    @Override
    public String getName() {
        return logger.getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    @Override
    public void trace(String var1) {
        logger.trace(var1);
    }

    @Override
    public void trace(String var1, Object var2) {

        logger.trace(var1,var2);
    }

    @Override
    public void trace(String var1, Object var2, Object var3) {
        logger.trace(var1, var2, var3);
    }

    @Override
    public void trace(String var1, Object... var2) {
        logger.trace(var1, var2);
    }

    @Override
    public void trace(String var1, Throwable var2) {
        logger.trace(var1, var2);
    }

    @Override
    public boolean isTraceEnabled(Marker var1) {
        return logger.isTraceEnabled(var1);
    }

    @Override
    public void trace(Marker var1, String var2) {
        logger.trace(var1, var2);
    }

    @Override
    public void trace(Marker var1, String var2, Object var3) {
        logger.trace(var1, var2, var3);
    }

    @Override
    public void trace(Marker var1, String var2, Object var3, Object var4) {

    }

    @Override
    public void trace(Marker var1, String var2, Object... var3) {

    }

    @Override
    public void trace(Marker var1, String var2, Throwable var3) {

    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public void debug(String var1) {
        logger.debug(var1);
    }

    @Override
    public void debug(String var1, Object var2) {
        logger.debug(var1, var2);
    }

    @Override
    public void debug(String var1, Object var2, Object var3) {
    }

    @Override
    public void debug(String var1, Object... var2) {

    }

    @Override
    public void debug(String var1, Throwable var2) {

    }

    @Override
    public boolean isDebugEnabled(Marker var1) {
        return false;
    }

    @Override
    public void debug(Marker var1, String var2) {

    }

    @Override
    public void debug(Marker var1, String var2, Object var3) {

    }

    @Override
    public void debug(Marker var1, String var2, Object var3, Object var4) {

    }

    @Override
    public void debug(Marker var1, String var2, Object... var3) {

    }

    @Override
    public void debug(Marker var1, String var2, Throwable var3) {

    }

    @Override
    public boolean isInfoEnabled() {
        return false;
    }

    @Override
    public void info(String var1) {
        logger.info(var1);
    }

    @Override
    public void info(String var1, Object var2) {
        logger.info(var1, var2);
    }

    @Override
    public void info(String var1, Object var2, Object var3) {
        logger.info(var1, var2, var3);
    }

    @Override
    public void info(String var1, Object... var2) {

    }

    @Override
    public void info(String var1, Throwable var2) {

    }

    @Override
    public boolean isInfoEnabled(Marker var1) {
        return false;
    }

    @Override
    public void info(Marker var1, String var2) {

    }

    @Override
    public void info(Marker var1, String var2, Object var3) {

    }

    @Override
    public void info(Marker var1, String var2, Object var3, Object var4) {

    }

    @Override
    public void info(Marker var1, String var2, Object... var3) {

    }

    @Override
    public void info(Marker var1, String var2, Throwable var3) {

    }

    @Override
    public boolean isWarnEnabled() {
        return false;
    }

    @Override
    public void warn(String var1) {
        logger.warn(var1);
    }

    @Override
    public void warn(String var1, Object var2) {

        logger.warn(var1, var2);
    }

    @Override
    public void warn(String var1, Object... var2) {

        logger.warn(var1, var2);
    }

    @Override
    public void warn(String var1, Object var2, Object var3) {

    }

    @Override
    public void warn(String var1, Throwable var2) {

    }

    @Override
    public boolean isWarnEnabled(Marker var1) {
        return false;
    }

    @Override
    public void warn(Marker var1, String var2) {

    }

    @Override
    public void warn(Marker var1, String var2, Object var3) {

    }

    @Override
    public void warn(Marker var1, String var2, Object var3, Object var4) {

    }

    @Override
    public void warn(Marker var1, String var2, Object... var3) {

    }

    @Override
    public void warn(Marker var1, String var2, Throwable var3) {

    }

    @Override
    public boolean isErrorEnabled() {
        return false;
    }

    @Override
    public void error(String var1) {

        logger.error(var1);
    }

    @Override
    public void error(String var1, Object var2) {
        logger.error(var1, var2);
    }

    @Override
    public void error(String var1, Object var2, Object var3) {
        logger.error(var1, var2, var3);
    }

    @Override
    public void error(String var1, Object... var2) {
        logger.error(var1, var2);
    }

    @Override
    public void error(String var1, Throwable var2) {

    }

    @Override
    public boolean isErrorEnabled(Marker var1) {
        return false;
    }

    @Override
    public void error(Marker var1, String var2) {

    }

    @Override
    public void error(Marker var1, String var2, Object var3) {

    }

    @Override
    public void error(Marker var1, String var2, Object var3, Object var4) {

    }

    @Override
    public void error(Marker var1, String var2, Object... var3) {

    }

    @Override
    public void error(Marker var1, String var2, Throwable var3) {

    }
}
