/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.websocket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Test
{
    private final Logger logger = LogManager.getLogger(Test.class);

    public Test(String serialPortName) {
        System.out.println("##########################################################3");
        System.out.println(logger.isInfoEnabled());
        logger.entry();
        logger.info("info! {}", serialPortName);
        logger.error("error! {}", serialPortName);
        logger.debug("debug! {}", serialPortName);
    }

    public static void main(String args[])
    {
        Test h1 = new Test("1001");
    }
}
