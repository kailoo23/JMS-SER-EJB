//package com.intercom.task.jmsreciver.services;
//
//import jakarta.annotation.Resource;
//import jakarta.jms.*;
//
//import javax.naming.InitialContext;
//import javax.naming.NamingException;
//
//
//public class ReceiverService {
//
//    private String messageText = "default value";
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
////    Connection connection, String requestQueueName, String invalidQueueName
//    public static ReceiverService newReplier() {
//
//        ReceiverService replier = new ReceiverService();
//        replier.messageReceiveReply();
//
//        return replier;
//    }
//
//    public String messageReceiveReply(){
//
//        try {
//
//            try (QueueConnection connection = connectionFactory.createQueueConnection()) {
//
//
//                session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
//
//                QueueReceiver qreceiver = session.createReceiver(queue);
//                Queue invalidQueue = session.createQueue("InvalidQueue");
//                invalidProducer = session.createProducer(invalidQueue);
//
//                connection.start();
//
//                TextMessage message = (TextMessage) qreceiver.receive();
//                messageText = message.getText();
//
//
//                // response.getWriter().println(messageText);
//                System.out.println("----------------from receiver servlet"+messageText);
//                Thread.sleep(10000);
//
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//
//        } catch (JMSException e) {
//            e.printStackTrace();
//            //response.getWriter().println("Error: " + e.getMessage());
//        }
//
//
//        return messageText;
//    }
////    private QueueSession session;
////    private MessageProducer invalidProducer;
////    public ReceiverService() {
////        super();
////    }
////
////    public static ReceiverService newReplier(QueueConnection connection, String requestQueueName, String invalidQueueName)
////            throws JMSException, NamingException {
////        System.out.println("..................... New Replier caled");
////        ReceiverService replier = new ReceiverService();
////        replier.initialize(connection, requestQueueName, invalidQueueName);
////
////        return replier;
////    }
////
////    protected void initialize(QueueConnection connection, String requestQueueName, String invalidQueueName)
////            throws NamingException, JMSException {
////        System.out.println("....................intializer caleed");
////        session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
////        Queue requestQueue = session.createQueue(requestQueueName);
////        Destination invalidQueue = session.createQueue(invalidQueueName);
////
////        MessageConsumer requestConsumer = session.createConsumer(requestQueue);
////        MessageListener listener = this;
////        requestConsumer.setMessageListener(listener);
////        invalidProducer = session.createProducer(invalidQueue);
//////        requestQueue = session.createQueue("RequestQueue");
//////        QueueReceiver receiver = session.createReceiver(requestQueue);
//////        TextMessage message = (TextMessage) receiver.receive();
//////        ReceiverService replier = new ReceiverService();
//////        System.out.println(message);
//////        replier.onMessage(message);
////
////
////
////    }
////
////    public void onMessage(Message message) {
////        try {
////            if ((message instanceof TextMessage) && (message.getJMSReplyTo() != null)) {
////                TextMessage requestMessage = (TextMessage) message;
////
////                System.out.println("Received request");
////                System.out.println("\tTime:       " + System.currentTimeMillis() + " ms");
////                System.out.println("\tMessage ID: " + requestMessage.getJMSMessageID());
////                System.out.println("\tCorrel. ID: " + requestMessage.getJMSCorrelationID());
////                System.out.println("\tReply to:   " + requestMessage.getJMSReplyTo());
////                System.out.println("\tContents:   " + requestMessage.getText());
////
////                String contents = requestMessage.getText();
////                Destination replyDestination = message.getJMSReplyTo();
////                MessageProducer replyProducer = session.createProducer(replyDestination);
////
////                TextMessage replyMessage = session.createTextMessage();
////                replyMessage.setText(contents);
////                replyMessage.setJMSCorrelationID(requestMessage.getJMSMessageID());
////                replyProducer.send(replyMessage);
////
////                System.out.println("Sent reply");
////                System.out.println("\tTime:       " + System.currentTimeMillis() + " ms");
////                System.out.println("\tMessage ID: " + replyMessage.getJMSMessageID());
////                System.out.println("\tCorrel. ID: " + replyMessage.getJMSCorrelationID());
////                System.out.println("\tReply to:   " + replyMessage.getJMSReplyTo());
////                System.out.println("\tContents:   " + replyMessage.getText());
////            } else {
////                System.out.println("Invalid message detected");
////                System.out.println("\tType:       " + message.getClass().getName());
////                System.out.println("\tTime:       " + System.currentTimeMillis() + " ms");
////                System.out.println("\tMessage ID: " + message.getJMSMessageID());
////                System.out.println("\tCorrel. ID: " + message.getJMSCorrelationID());
////                System.out.println("\tReply to:   " + message.getJMSReplyTo());
////
////                message.setJMSCorrelationID(message.getJMSMessageID());
////                invalidProducer.send(message);
////
////                System.out.println("Sent to invalid message queue");
////                System.out.println("\tType:       " + message.getClass().getName());
////                System.out.println("\tTime:       " + System.currentTimeMillis() + " ms");
////                System.out.println("\tMessage ID: " + message.getJMSMessageID());
////                System.out.println("\tCorrel. ID: " + message.getJMSCorrelationID());
////                System.out.println("\tReply to:   " + message.getJMSReplyTo());
////                System.out.println("\tContents:   " + message);
////            }
////        } catch (JMSException e) {
////            e.printStackTrace();
////        }
////    }
//}