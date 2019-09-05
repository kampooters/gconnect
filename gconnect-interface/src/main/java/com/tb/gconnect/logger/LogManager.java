package com.tb.gconnect.logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tb.gconnect.common.constants.Constants;
import org.slf4j.Marker;

/**
 * @author abdul.rehman4 12th/07/2019
 * @version 1.0
 * @since v1.0
 * {@link LogManager} performs the all utility stuff like message formating, logs etc
 *
 * This class implements the ADAPTER and SINGLETON PATTERN.
 * The {@link ILogger} provides the structure for ADAPTEE, by default we use {@link SL4JLoggerAdaptee} as default adaptee
 */
public class LogManager implements ILogManager {


    /**
     * Remember the implementation of {@link ILogger}  should be thread safe
     */
    private  ILogger logger;

    /**
     * {@link ObjectMapper is thread Safe}
     */
    private ObjectMapper objectMapper;


    /**
     * Its for autowiring
     */
    public LogManager(){
        this.logger = new SL4JLoggerAdaptee(this.getClass());
        if(null == this.objectMapper){
            this.objectMapper = new ObjectMapper();
        }
    }

    public LogManager(ILogger logAdaptee, String adapter){
        this.logger = logAdaptee;
        if(null == this.objectMapper){
            this.objectMapper = new ObjectMapper();
        }
    }

    /**
     * To log the specific class messages
     * @param clazz
     */
    public LogManager(Class clazz){
        this.logger = new SL4JLoggerAdaptee(clazz);
        if(null == this.objectMapper){
            this.objectMapper = new ObjectMapper();
        }
    }




    public void requestReceived(String path, Object requestDTO){
       try{
           String reqStr = "";
           if(null != requestDTO){
               reqStr = objectMapper.writeValueAsString(requestDTO);

           }
           logger.info(Constants.REQUEST_RECEIVED +" at" +path +" Body: "+reqStr);
       }catch (Exception e){
           logger.error(e.getMessage(),e);
       }
    }

    public void requestServed(String path, Object respDTO){
        try{
            logger.info(Constants.REQUEST_SERVED +" at" +path +" Response: "+objectMapper.writeValueAsString(respDTO));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }

    public String format(Class claz, String method){
        return "[ "+claz.getName()+" ][ "+method+" ] ";
    }


    public String getName() {
        return null;
    }

    public boolean isTraceEnabled() {
        return false;
    }

    public void trace(String s) {

    }

    public void trace(String s, Object o) {

    }

    public void trace(String s, Object o, Object o1) {

    }

    public void trace(String s, Object... objects) {

    }

    public void trace(String s, Throwable throwable) {

    }

    public boolean isTraceEnabled(Marker marker) {
        return false;
    }

    public void trace(Marker marker, String s) {

    }

    public void trace(Marker marker, String s, Object o) {

    }

    public void trace(Marker marker, String s, Object o, Object o1) {

    }

    public void trace(Marker marker, String s, Object... objects) {

    }

    public void trace(Marker marker, String s, Throwable throwable) {

    }

    public boolean isDebugEnabled() {
        return false;
    }

    public void debug(String s) {

    }

    public void debug(String s, Object o) {

    }

    public void debug(String s, Object o, Object o1) {

    }

    public void debug(String s, Object... objects) {

    }

    public void debug(String s, Throwable throwable) {

    }

    public boolean isDebugEnabled(Marker marker) {
        return false;
    }

    public void debug(Marker marker, String s) {

    }

    public void debug(Marker marker, String s, Object o) {

    }

    public void debug(Marker marker, String s, Object o, Object o1) {

    }

    public void debug(Marker marker, String s, Object... objects) {

    }

    public void debug(Marker marker, String s, Throwable throwable) {

    }

    public boolean isInfoEnabled() {
        return false;
    }

    public void info(String s) {
        logger.info(s);
    }

    public void info(String s, Object o) {
        logger.info(s,o);
    }

    public void info(String s, Object o, Object o1) {
        logger.info(s,o,o1);
    }

    public void info(String s, Object... objects) {
        logger.info(s,objects);
    }

    public void info(String s, Throwable throwable) {
        logger.info(s,throwable);

    }

    public boolean isInfoEnabled(Marker marker) {
        return false;
    }

    public void info(Marker marker, String s) {

    }

    public void info(Marker marker, String s, Object o) {

    }

    public void info(Marker marker, String s, Object o, Object o1) {

    }

    public void info(Marker marker, String s, Object... objects) {

    }

    public void info(Marker marker, String s, Throwable throwable) {

    }

    public boolean isWarnEnabled() {
        return false;
    }

    public void warn(String s) {

    }

    public void warn(String s, Object o) {

    }

    public void warn(String s, Object... objects) {

    }

    public void warn(String s, Object o, Object o1) {

    }

    public void warn(String s, Throwable throwable) {

    }

    public boolean isWarnEnabled(Marker marker) {
        return false;
    }

    public void warn(Marker marker, String s) {

    }

    public void warn(Marker marker, String s, Object o) {

    }

    public void warn(Marker marker, String s, Object o, Object o1) {

    }

    public void warn(Marker marker, String s, Object... objects) {

    }

    public void warn(Marker marker, String s, Throwable throwable) {

    }

    public boolean isErrorEnabled() {
        return false;
    }

    public void error(String s) {

        logger.error(s);
    }

    public void error(String s, Object o) {
        logger.error(s,o);
    }

    public void error(String s, Object o, Object o1) {
        logger.error(s,o,o1);
    }

    public void error(String s, Object... objects) {

    }

    public void error(String s, Throwable throwable) {
        logger.error(s, throwable);
    }

    public boolean isErrorEnabled(Marker marker) {
        return false;
    }

    public void error(Marker marker, String s) {

    }

    public void error(Marker marker, String s, Object o) {

    }

    public void error(Marker marker, String s, Object o, Object o1) {

    }

    public void error(Marker marker, String s, Object... objects) {

    }

    public void error(Marker marker, String s, Throwable throwable) {

    }
}
