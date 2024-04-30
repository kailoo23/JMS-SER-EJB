//package com.intercom.task.jmsreciver.receiver;
//
//import com.intercom.task.jmsreciver.services.ReceiverService;
//import jakarta.annotation.Resource;
//import jakarta.jms.*;
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.*;
//import java.io.IOException;
//
//@WebServlet("/ReceiverServlet")
//public class ReceiverServletMul extends HttpServlet  {
//    @Resource(lookup = "myQueueConnectionFactory")
//    private QueueConnectionFactory connectionFactory;
//
//    @Resource(lookup ="myQueue")
//    private Queue queue;
//    @Resource(lookup ="ReplyQueue")
//    private Queue queueRes;
//    private MessageProducer invalidProducer;
//    private QueueSession session;
//
//    ReceiverService receiverService = new ReceiverService();
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        response.getWriter().println( receiverService.messageReceiveReply());
//        doPost(request,response);
//
//    }
//
//
//
////    MessageConsumer requestConsumer = session.createConsumer(queue);
////    MessageListener listener = this;
////    requestConsumer.setMessageListener(listener);
//
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        try {
////            try (QueueConnection connection = connectionFactory.createQueueConnection()) {
////                QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
////                QueueSender sender = session.createSender(queueRes);
////
////                TextMessage message = session.createTextMessage();
////                message.setText("Sum = "+sum);
////                sender.send(message);
////                System.out.println("msg sent");
////            }
//            response.sendRedirect("SenderServlet");
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.getWriter().println("Error: " + e.getMessage());
//        }
//    }
//}
