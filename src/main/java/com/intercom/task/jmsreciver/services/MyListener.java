package com.intercom.task.jmsreciver.services;

import jakarta.annotation.Resource;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;

import jakarta.jms.*;

import java.util.ArrayList;
import java.util.List;


// test any thing
@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(
                        propertyName = "destinationType",
                        propertyValue = "jakarta.jms.Queue"
                ),
                @ActivationConfigProperty(
                        propertyName = "destinationLookup",
                        propertyValue = "myQueue"
                )
        }
)
public class MyListener implements MessageListener {

    private static final List<String> receivedMessages = new ArrayList<>();
    @Resource(lookup = "myQueueConnectionFactory")
    private QueueConnectionFactory connectionFactory;
    private static int counter = 0;
    int sum = 0;

    @Override
    public void onMessage(Message message) {
        try (QueueConnection connection = connectionFactory.createQueueConnection()) {

            if (message instanceof TextMessage) {
                System.out.println("message received processing...." + ((TextMessage) message).getText());
                processReceivedMessage((TextMessage) message ,connection );
            } else {
                System.out.println("Received message of unexpected type: " + message.getClass().getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in onMessage method: " + e.getMessage());
        }
    }


    private void processReceivedMessage(TextMessage textMessage ,QueueConnection connection) {
        try {
            String[] nums = textMessage.getText().split(",");
            int num1 = Integer.parseInt(nums[0]);
            int num2 = Integer.parseInt(nums[1]);
            sum = num1 + num2;


            String requestText = textMessage.getText();
            System.out.println("Received message: " + requestText);
            receivedMessages.add(requestText);

            Destination replyTo = textMessage.getJMSReplyTo();
            System.out.println("Replying at >>>>>> " + replyTo);

            if (replyTo instanceof Queue) {
               // Thread.sleep(20000);
                sendResponse((Queue) replyTo, textMessage , connection);
            } else {
                System.out.println("Invalid reply-to destination");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error processing message: " + e.getMessage());
        }
    }

    private void sendResponse(Queue replyToQueue, TextMessage originalMessage ,QueueConnection connection)  {

        try {
            counter++;
            System.out.println(receivedMessages);
            System.out.println(replyToQueue);
            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            QueueSender sender = session.createSender(replyToQueue);

            TextMessage messageSent = session.createTextMessage();
            messageSent.setJMSCorrelationID(originalMessage.getJMSMessageID());
            messageSent.setText(" " + sum);
            System.out.println("sent response " +sum);
            connection.start();
            sender.send(messageSent);
            System.out.println("send response success");

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error on the sendResponse methode " + e.getMessage());
        }
    }


}
//@Override
//public void onMessage(Message message) {
//    try (QueueConnection connection = connectionFactory.createQueueConnection()) {
//
//        if (message instanceof TextMessage ) {
//            TextMessage requestMessage = (TextMessage) message;
//            System.out.println("Message received: " + requestMessage.getText());
//            String messageContent = requestMessage.getText();
//
//            if (messageContent.startsWith("Sum = ")) {
//                String numberString = messageContent.substring(messageContent.indexOf("=") + 1).trim();
//                sum = Integer.parseInt(numberString);
//                System.out.println("sum received again will not send : " + sum);
//
//            } else {
//                String[] nums = requestMessage.getText().split(",");
//                int num1 = Integer.parseInt(nums[0]);
//                System.out.println("num1: " + num1);
//                int num2 = Integer.parseInt(nums[1]);
//                System.out.println("num2: " + num2);
//                sum = num1 + num2;
//                System.out.println("sum: " + sum);
//                Destination replyDestination = requestMessage.getJMSReplyTo();
//                System.out.println(replyDestination.toString());
//                // MessageProducer replyProducer = session.createProducer(replyDestination);
//                // TextMessage replyMessage = connectionFactory.createContext().createTextMessage();
//                QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
//                QueueSender sender = session.createSender((Queue) replyDestination);
//
//                TextMessage messageSent = session.createTextMessage();
//                messageSent.setText("Sum = "+sum);
//                sender.send(message);
//
//            }
//
//        } else {
//            System.out.println("Invalid message detected");
//        }
//    } catch (JMSException e) {
//
//        e.printStackTrace();
//    }
//
//
//}


//
//@Override
//public void onMessage(Message message) {
//    try {
//
//        if (message instanceof TextMessage) {
//            System.out.println("message received processing...." + ((TextMessage) message).getText());
//            processReceivedMessage((TextMessage) message);
//        } else {
//            System.out.println("Received message of unexpected type: " + message.getClass().getName());
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//        System.out.println("error in onMessage method: " + e.getMessage());
//    }
//}
//
//
//private void processReceivedMessage(TextMessage textMessage) {
//    try {
//        String[] nums = textMessage.getText().split(",");
//        int num1 = Integer.parseInt(nums[0]);
//        int num2 = Integer.parseInt(nums[1]);
//        sum = num1 + num2;
//
//
//        String requestText = textMessage.getText();
//        System.out.println("Received message: " + requestText);
//        receivedMessages.add(requestText);
//
//        Destination replyTo = textMessage.getJMSReplyTo();
//        System.out.println("Replying at>>>>>> " + replyTo);
//
//        if (replyTo instanceof Queue) {
//
//            Thread.sleep(2000);
//            sendResponse((Queue) replyTo, textMessage);
//        } else {
//            System.out.println("Invalid reply-to destination");
//        }
//
//    } catch (Exception e) {
//        e.printStackTrace();
//        System.out.println("Error processing message: " + e.getMessage());
//    }
//}
//
//private void sendResponse(Queue replyToQueue, TextMessage originalMessage) throws Exception {
//    JMSProducer producer = connectionFactory.createContext().createProducer();
//
//    counter++;
//
//    TextMessage responseMessage = connectionFactory
//            .createContext()
//            .createTextMessage("" + sum);
//
//    responseMessage.setJMSCorrelationID(originalMessage.getJMSCorrelationID());
//    producer.send(replyToQueue, responseMessage);
//
//    System.out.println("send response success");
//}

//    @Resource(lookup = "ReplyQueue")
//    private Queue queueRes;
//


//    @Override
//    public void init() throws ServletException {
//        try (QueueConnection connection = connectionFactory.createQueueConnection()) {
//            session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
//            MessageConsumer consumer = session.createConsumer(queueRes);
//            consumer.setMessageListener(this);
//            connection.start();
//        } catch (JMSException e) {
//            throw new ServletException(e);
//        }
//    }

//
//import jakarta.annotation.Resource;
//import jakarta.inject.Named;
//import jakarta.jms.*;
//        import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jdk.jfr.Name;
//@WebServlet
//public class MyListener extends HttpServlet implements MessageListener {
//    @Resource(lookup = "myQueueConnectionFactory")
//    private QueueConnectionFactory connectionFactory;
//
//    @Resource(lookup = "ReplyQueue")
//    private Queue queueRes;
//
//    private QueueSession session;
//
//    @Override
//    public void init() throws ServletException {
//        try (QueueConnection connection = connectionFactory.createQueueConnection()) {
//            session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
//            MessageConsumer consumer = session.createConsumer(queueRes);
//            consumer.setMessageListener(this);
//            connection.start();
//        } catch (JMSException e) {
//            throw new ServletException(e);
//        }
//    }
//
//    @Override
//    public void onMessage(Message message) {
//        try {
//            if (message instanceof TextMessage ) {
//                TextMessage requestMessage = (TextMessage) message;
//                String[] nums = requestMessage.getText().split(",");
//                int num1 = Integer.parseInt(nums[0]);
//                int num2 = Integer.parseInt(nums[1]);
//                int sum = num1 + num2;
//
//                Destination replyDestination = requestMessage.getJMSReplyTo();
//                MessageProducer replyProducer = session.createProducer(replyDestination);
//
//                TextMessage replyMessage = session.createTextMessage();
//                replyMessage.setText("Sum = " + sum);
//                replyMessage.setJMSCorrelationID(requestMessage.getJMSCorrelationID());
//                replyProducer.send(replyMessage);
//            } else {
//                System.out.println("Invalid message detected");
//            }
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
//    }
//}