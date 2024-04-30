//package com.intercom.task.jmsreciver.receiver;
//
//import com.intercom.task.jmsreciver.services.ReceiverService;
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.*;
//import jakarta.jms.*;
//import javax.naming.*;
//import java.io.*;
//
//@WebServlet("/ReceiverMultiple")
//public class ReceiverMultiple extends HttpServlet {
//
//    private QueueConnection connection;
//    private ReceiverService receiverService;
//
//    public void init() throws ServletException {
//        try {
//            // Initialize JMS connection and receiver service
//            InitialContext ctx = new InitialContext();
//            QueueConnectionFactory connectionFactory = (QueueConnectionFactory) ctx.lookup("myQueueConnectionFactory");
//            connection = connectionFactory.createQueueConnection();
//            receiverService = ReceiverService.newReplier(connection, "RequestQueue", "InvalidQueue");
//            connection.start();// Start the connection
//
//        } catch (NamingException | JMSException e) {
//            throw new ServletException("Failed to initialize JMS connection", e);
//        }
//    }
//
//    public void destroy() {
//        try {
//            // Close JMS connection
//            if (connection != null) {
//                connection.close();
//            }
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
//    }
//}
////
////
////import com.intercom.task.jmsreciver.services.ReceiverService;
////import jakarta.servlet.*;
////import jakarta.servlet.annotation.WebServlet;
////import jakarta.servlet.http.*;
////import jakarta.jms.*;
////import javax.naming.*;
////import java.io.*;
////
////@WebServlet("/ReceiverMultiple")
////public class ReceiverMultiple extends HttpServlet {
////
////    private QueueConnection connection;
////    private ReceiverService receiverService;
////
////    public void init() throws ServletException {
////        try {
////            // Initialize JMS connection and receiver service
////            InitialContext ctx = new InitialContext();
////            QueueConnectionFactory connectionFactory = (QueueConnectionFactory) ctx.lookup("myQueueConnectionFactory");
////            connection = connectionFactory.createQueueConnection();
////            receiverService = ReceiverService.newReplier(connection, "RequestQueue", "InvalidQueue");
////        } catch (NamingException | JMSException e) {
////            throw new ServletException("Failed to initialize JMS connection", e);
////        }
////    }
////
////    public void destroy() {
////        try {
////            // Close JMS connection
////            if (connection != null) {
////                connection.close();
////            }
////        } catch (JMSException e) {
////            e.printStackTrace();
////        }
////    }
////
////    protected void doGet(HttpServletRequest request, HttpServletResponse response)
////            throws ServletException, IOException {
////        try {
////            System.out.println("message recived");
////            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
////            Queue requestQueue = session.createQueue("RequestQueue");
////            QueueReceiver receiver = session.createReceiver(requestQueue);
////            receiver.setMessageListener(receiverService);
////            connection.start();
////            TextMessage message = (TextMessage) receiver.receive();
////            System.out.println("workiiiiiiiiiiiiiiing..........."+message.getText());
////        } catch (JMSException e) {
////            throw new ServletException("Failed to process incoming message", e);
////        }
////        response.setContentType("text/html");
////        PrintWriter out = response.getWriter();
////        out.println("<html><body>");
////        out.println("<h2>Message Servlet</h2>");
////        out.println("<p>This servlet is listening for messages...</p>");
////        out.println("<p>This servlet is listening for messages...</p>");
////        out.println("</body></html>");
////    }
////
////    protected void doPost(HttpServletRequest request, HttpServletResponse response)
////            throws ServletException, IOException {
////        // Process incoming message
////        try {
////            System.out.println("message recived");
////            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
////            Queue requestQueue = session.createQueue("RequestQueue");
////            QueueReceiver receiver = session.createReceiver(requestQueue);
////            receiver.setMessageListener(receiverService);
////            connection.start();
////            TextMessage message = (TextMessage) receiver.receive();
////            System.out.println("workiiiiiiiiiiiiiiing..........."+message.getText());
////        } catch (JMSException e) {
////            throw new ServletException("Failed to process incoming message", e);
////        }
////    }
////}
//
////
////import com.intercom.task.jmsreciver.services.ReceiverService;
////import jakarta.jms.*;
////import jakarta.servlet.ServletException;
////import jakarta.servlet.annotation.WebServlet;
////import jakarta.servlet.http.HttpServlet;
////import jakarta.servlet.http.HttpServletRequest;
////import jakarta.servlet.http.HttpServletResponse;
////
////import javax.naming.InitialContext;
////import java.io.ByteArrayOutputStream;
////import java.io.IOException;
////import java.io.PrintWriter;
////
////@WebServlet("/ReceiverMultiple")
////public class ReceiverMultiple extends HttpServlet
////{
////
////    private String _message;
////    private QueueReceiver _receiver;
////
////    public void init()
////    {
////        try
////        {
////            InitialContext ctx=new InitialContext();
////            QueueConnectionFactory connFactory =(QueueConnectionFactory)ctx.lookup("myQueueConnectionFactory");
////            QueueConnection queueConn = connFactory.createQueueConnection();
////            Queue queue = (Queue)ctx.lookup("RequestQueue");
////            QueueSession _session = queueConn.createQueueSession(false,
////                    Session.AUTO_ACKNOWLEDGE);
////            _receiver = _session.createReceiver(queue);
////            queueConn.start();
////        }
////        catch (Exception ex)
////        {
////            _message = ex2str(ex);
////        }
////    }
////
////    public static String ex2str(Throwable t)
////    {
////        try {
////            ByteArrayOutputStream os = new ByteArrayOutputStream();
////            PrintWriter pw = new PrintWriter(os);
////            t.printStackTrace(pw);
////            pw.flush();
////            return new String(os.toByteArray());
////        } catch (Throwable e) {
////            return t.toString();
////        }
////    }
////
////    public void doGet(HttpServletRequest request, HttpServletResponse response)
////            throws IOException, ServletException
////    {
////        response.setContentType("text/html");
////        PrintWriter writer = response.getWriter();
////
////        writer.println("<html>");
////        writer.println("<head>");
////        writer.println("<title>JMS Servlet</title>");
////        writer.println("</head>");
////        writer.println("<body bgcolor=white>");
////
////        writer.println("<h1>Message on queue/queue0</h1>");
////
////        if (_receiver == null) {
////            writer.println(_message);
////        } else {
////            try {
////                Message msg = _receiver.receiveNoWait();
////                if (msg != null) {
////                    if (msg instanceof TextMessage) {
////                        writer.println(((TextMessage)msg).getText());
////                    } else {
////                        writer.println("not text message");
////                    }
////                } else {
////                    writer.println("no message on queue");
////                }
////            } catch (JMSException ex) {
////                writer.println(ex.toString());
////            }
////        }
////
////        writer.println("<h1>Send New Message</h1>");
////
////        writer.print("<FORM method=POST ");
////        writer.println("action=\"http://localhost:8080/services/sender\">");
////        writer.print("<input type=text ");
////        writer.println("name=send size=20 maxlength=\"800\">");
////        writer.println("<input type=\"submit\" name=post value=\"send\">");
////        writer.println("</FORM>");
////
////        writer.println("</body>");
////        writer.println("</html>");
////    }
////}
//
////
////import java.io.PrintWriter;
////import java.io.IOException;
////import java.io.ByteArrayOutputStream;
////
////import javax.naming.InitialContext;
////import javax.servlet.ServletException;
////import javax.servlet.http.HttpServlet;
////import javax.servlet.http.HttpServletRequest;
////import javax.servlet.http.HttpServletResponse;
////
////import javax.jms.Queue;
////import javax.jms.Session;
////import javax.jms.Message;
////import javax.jms.TextMessage;
////import javax.jms.JMSException;
////import javax.jms.QueueSession;
////import javax.jms.QueueReceiver;
////import javax.jms.QueueConnection;
////import javax.jms.QueueConnectionFactory;
////
//////import com.sssw.jms.api.JMQQueue;
//////import com.sssw.jms.api.JMQQueueConnectionFactory;
////
////public class ReceiverMultiple extends HttpServlet
////{
////    // private static final String _iiop = JMQQueueConnectionFactory.IIOP_PROTOCOL;
////    private String _message;
////    private QueueReceiver _receiver;
////
////    public void init()
////    {
////        try
////        {
////            InitialContext ctx=new InitialContext();
////            QueueConnectionFactory connFactory =(QueueConnectionFactory)ctx.lookup("myQueueConnectionFactory");
////            QueueConnection queueConn = connFactory.createQueueConnection();
////            Queue queue = (Queue)ctx.lookup("myQueue");
////            QueueSession _session = queueConn.createQueueSession(false,
////                    Session.AUTO_ACKNOWLEDGE);
////            _receiver = _session.createReceiver(queue);
////            queueConn.start();
////        }
////        catch (Exception ex)
////        {
////            _message = ex2str(ex);
////        }
////    }
////
////    public static String ex2str(Throwable t)
////    {
////        try {
////            ByteArrayOutputStream os = new ByteArrayOutputStream();
////            PrintWriter pw = new PrintWriter(os);
////            t.printStackTrace(pw);
////            pw.flush();
////            return new String(os.toByteArray());
////        } catch (Throwable e) {
////            return t.toString();
////        }
////    }
////
////    public void doGet(HttpServletRequest request, HttpServletResponse response)
////            throws IOException, ServletException
////    {
////        response.setContentType("text/html");
////        PrintWriter writer = response.getWriter();
////
////        writer.println("<html>");
////        writer.println("<head>");
////        writer.println("<title>JMS Servlet</title>");
////        writer.println("</head>");
////        writer.println("<body bgcolor=white>");
////
////        writer.println("<h1>Message on queue/queue0</h1>");
////
////        if (_receiver == null) {
////            writer.println(_message);
////        } else {
////            try {
////                Message msg = _receiver.receiveNoWait();
////                if (msg != null) {
////                    if (msg instanceof TextMessage) {
////                        writer.println(((TextMessage)msg).getText());
////                    } else {
////                        writer.println("not text message");
////                    }
////                } else {
////                    writer.println("no message on queue");
////                }
////            } catch (JMSException ex) {
////                writer.println(ex.toString());
////            }
////        }
////
////        writer.println("<h1>Send New Message</h1>");
////
////        writer.print("<FORM method=POST ");
////        writer.println("action=\"http://localhost:8080/services/sender\">");
////        writer.print("<input type=text ");
////        writer.println("name=send size=20 maxlength=\"800\">");
////        writer.println("<input type=\"submit\" name=post value=\"send\">");
////        writer.println("</FORM>");
////
////        writer.println("</body>");
////        writer.println("</html>");
////    }
////}