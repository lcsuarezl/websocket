/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.websocket;

import java.util.List;
import org.apache.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
//import org.apache.logging.log4j.junit.LoggerContextRule;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
//import org.apache.logging.log4j.test.appender.ListAppender;




/**
 *
 * @author lsuarezl
 */
public class Log4j2Test {
    
    public Log4j2Test() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    //@Rule
    //public LoggerContextRule init = new LoggerContextRule("log4j2.xml");
 
    @Test
    public void testSomeAwesomeFeature() {
        //final LoggerContext ctx = init.getContext();
        //final Logger logger = init.getLogger("org.apache.logging.log4j.my.awesome.test.logger");
        //final Configuration cfg = init.getConfiguration();
        //final ListAppender app = init.getListAppender("List");
        //logger.warn("Test message");
        //final List<LogEvent> events = app.getEvents();
    }
}
