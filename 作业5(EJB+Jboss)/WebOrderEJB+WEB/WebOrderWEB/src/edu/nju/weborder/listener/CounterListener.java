package edu.nju.weborder.listener;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import java.io.*;

@WebListener
public class CounterListener implements ServletContextListener, ServletContextAttributeListener {

    int counter;

    @Override
    public void attributeAdded(ServletContextAttributeEvent servletContextAttributeEvent) {
        System.out.println("attribute added");
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent servletContextAttributeEvent) {

    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent servletContextAttributeEvent) {
        System.out.println("attribute modified");
        String name = servletContextAttributeEvent.getName();
        if(name.equals("webCounter")){
            writeCounter(servletContextAttributeEvent);
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        String filePath = getClass().getResource("/counter.txt").getPath();
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            counter = Integer.parseInt(reader.readLine());
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("webCounter", Integer.toString(counter));
        System.out.println("Application Initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("Application closed");
    }

    synchronized private void writeCounter(ServletContextEvent servletContextEvent){
        ServletContext servletContext = servletContextEvent.getServletContext();
        counter = Integer.parseInt((String) servletContext.getAttribute("webCounter"));
        String filePath = getClass().getResource("/counter.txt").getPath();

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(Integer.toString(counter));
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
