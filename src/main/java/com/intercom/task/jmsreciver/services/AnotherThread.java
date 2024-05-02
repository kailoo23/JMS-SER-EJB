package com.intercom.task.jmsreciver.services;

import jakarta.annotation.Resource;
import jakarta.jms.*;

import java.util.ArrayList;
import java.util.List;


public class AnotherThread  implements Runnable{


    private static final List<String> receivedMessages = new ArrayList<>();
    @Resource(lookup = "myQueueConnectionFactory")
    private QueueConnectionFactory connectionFactory;
    private static int counter = 0;
    int sum = 0;

    private void sendResponse(Queue replyToQueue, TextMessage originalMessage , QueueConnection connection , int sum )  {

        try {
            System.out.println("sent from the other thread"+replyToQueue);
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
    @Override
    public void run() {
        //sendResponse();
    }
}
