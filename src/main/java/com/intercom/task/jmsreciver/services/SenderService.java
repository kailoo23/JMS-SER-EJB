//package com.intercom.task.jmsreciver.services;
//
//import jakarta.annotation.Resource;
//import jakarta.jms.*;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import javax.naming.NamingException;
//
//public class SenderService {
//
//    private static final Logger logger = LogManager.getLogger(SenderService.class);
//    private QueueSession session;
//    private Destination requestQueueName;
////    @Resource(lookup = "myQueue")
////    private Queue requestQueue;
//
//    @Resource(lookup = "ReplyQueue")
//    private Queue replyQueue;
//
//    //    @Resource(lookup = "myQueue")
////    private Queue invalidQueue;
//    private MessageProducer requestProducer;
//    private MessageConsumer replyConsumer;
//    private MessageProducer invalidProducer;
//
//    protected SenderService() {
//        super();
//    }
//
//    public static SenderService newRequestor(QueueConnection connection, String requestQueueName,
//                                             String replyQueueName, String invalidQueueName)
//            throws JMSException, NamingException {
//        SenderService requestor = new SenderService();
//        requestor.initialize(connection, requestQueueName, replyQueueName, invalidQueueName);
//        return requestor;
//    }
//
//    protected void initialize(QueueConnection connection, String requestQueueName,
//                              String replyQueueName, String  invalidQueueName)
//            throws NamingException, JMSException {
//        session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
//        Queue requestQueue = session.createQueue(requestQueueName);
//        Queue replyQueue = session.createQueue(replyQueueName);
//        Queue invalidQueue = session.createQueue(invalidQueueName);
//
//        requestProducer = session.createProducer(requestQueue);
//        replyConsumer = session.createConsumer(replyQueue);
//        invalidProducer = session.createProducer(invalidQueue);
//
//    }
//
//    public void send(String massage) throws JMSException {
//        TextMessage requestMessage = session.createTextMessage();
//        requestMessage.setText(massage);
//        requestMessage.setJMSReplyTo(replyQueue);
//        requestProducer.send(requestMessage);
//        System.out.println("Sent request");
//        System.out.println("\tTime:       " + System.currentTimeMillis() + " ms");
//        System.out.println("\tMessage ID: " + requestMessage.getJMSMessageID());
//        System.out.println("\tCorrel. ID: " + requestMessage.getJMSCorrelationID());
//        System.out.println("\tReply to:   " + requestMessage.getJMSReplyTo());
//        System.out.println("\tContents:   " + requestMessage.getText());
//    }
//
//    public void receiveSync() throws JMSException {
//        Message msg = replyConsumer.receive();
//        if (msg instanceof TextMessage) {
//            TextMessage replyMessage = (TextMessage) msg;
//            System.out.println("Received reply ");
//            System.out.println("\tTime:       " + System.currentTimeMillis() + " ms");
//            System.out.println("\tMessage ID: " + replyMessage.getJMSMessageID());
//            System.out.println("\tCorrel. ID: " + replyMessage.getJMSCorrelationID());
//            System.out.println("\tReply to:   " + replyMessage.getJMSReplyTo());
//            System.out.println("\tContents:   " + replyMessage.getText());
//        } else {
//            System.out.println("Invalid message detected");
//            System.out.println("\tType:       " + msg.getClass().getName());
//            System.out.println("\tTime:       " + System.currentTimeMillis() + " ms");
//            System.out.println("\tMessage ID: " + msg.getJMSMessageID());
//            System.out.println("\tCorrel. ID: " + msg.getJMSCorrelationID());
//            System.out.println("\tReply to:   " + msg.getJMSReplyTo());
//
//            msg.setJMSCorrelationID(msg.getJMSMessageID());
//            invalidProducer.send(msg);
//
//            System.out.println("Sent to invalid message queue");
//            System.out.println("\tType:       " + msg.getClass().getName());
//            System.out.println("\tTime:       " + System.currentTimeMillis() + " ms");
//            System.out.println("\tMessage ID: " + msg.getJMSMessageID());
//            System.out.println("\tCorrel. ID: " + msg.getJMSCorrelationID());
//            System.out.println("\tReply to:   " + msg.getJMSReplyTo());
//        }
//    }
//
//    public void close() throws JMSException {
//        if (session != null) {
//            session.close();
//        }
//    }
//}