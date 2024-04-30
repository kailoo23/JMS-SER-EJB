package com.intercom.task.jmsreciver.senders;

import jakarta.annotation.Resource;
import jakarta.jms.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/SenderServletMul1")
public class SenderServletMul1 extends HttpServlet {
//    private static final Logger logger = LogManager.getLogger(SenderServletMul1.class);

    @Resource(lookup = "myQueueConnectionFactory")
    private QueueConnectionFactory connectionFactory;

    @Resource(lookup = "myQueue")
    private Queue queue;

    @Resource(lookup = "RequestQueue")
    private Queue queueRes;

    private static String responseMessage;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (QueueConnection connection = connectionFactory.createQueueConnection()) {
            int num1 = Integer.parseInt(request.getParameter("num1"));
            int num2 = Integer.parseInt(request.getParameter("num2"));
            String mess = num1 + "," + num2;

            sendAndReceiveMessage(mess, connection);
            if (responseMessage != null) {
              //  request.getSession().setAttribute("responseMessage", responseMessage);
                request.setAttribute("responseMessage", responseMessage);
                RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                dispatcher.forward(request, response);
            } else {
                request.getSession().setAttribute("responseMessage", "nothing yet");

            }
          //  response.sendRedirect("index.jsp");
        } catch (JMSRuntimeException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to send or receive message");
        } catch (Exception e) {

            e.printStackTrace();
            System.out.println(e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to send or receive message");
        }
    }

    private void sendAndReceiveMessage(String messageText, QueueConnection connection) throws Exception {

        try {
            connection.start();
            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            QueueSender sender = session.createSender(queue);
            QueueReceiver receiver = session.createReceiver(queueRes);

            TextMessage messageSent = session.createTextMessage();


            //messageSent.setJMSCorrelationID(UUID.randomUUID().toString());
            messageSent.setJMSReplyTo(queueRes);
            messageSent.setText(messageText);

            sender.send(messageSent);
            System.out.println("Message sent: " + messageSent.getText() + " with Correlation ID: " + messageSent.getJMSMessageID());

            // Thread.sleep(10000);


            Thread.sleep(1000);
            TextMessage messageReceived = (TextMessage) receiver.receive(20000);

            if (messageReceived != null) {
                responseMessage = messageReceived.getText();
                System.out.println(responseMessage);
                System.out.println("Message received: " + messageReceived.getText() + " with Correlation ID: " + messageReceived.getJMSCorrelationID());

            } else {
                System.out.println(messageReceived);
                System.out.println("No message received within timeout!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("  error on the sendAndReceiveMessage methode  -> " + e.getMessage());
            throw e;
        }
        connection.close();
    }

    public static String getReceivedMessage() {
        return responseMessage;
    }
}


//        try {
//           // Queue responseQueue = jmsContext.createTemporaryQueue();
//
//            TextMessage message = jmsContext.createTextMessage(messageText);
//            message.setJMSCorrelationID(uniqueSenderId);
//            message.setJMSReplyTo(queueRes);
//
//            JMSProducer producer = jmsContext.createProducer();
//
//            producer.send(queue, message);
//
//            JMSConsumer consumer = jmsContext.createConsumer(queueRes);
//            Message reply = consumer.receive(10000);
//
//            TextMessage textMessage = (TextMessage) reply;
//            responseMessage = textMessage.getText();
//            System.out.println(" Reply Received -------> " + responseMessage);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("  Message Not Send  -> " + e.getMessage());
//            throw e;
//        }


//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        try {
//            int num1 = Integer.parseInt(request.getParameter("num1"));
//            int num2 = Integer.parseInt(request.getParameter("num2"));
//
//            try (QueueConnection connection = connectionFactory.createQueueConnection()) {
//                QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
//                QueueSender sender = session.createSender(queue);
//                QueueReceiver receiver = session.createReceiver(queueRes);
//
//                TextMessage messageSent = session.createTextMessage();
//                messageSent.setJMSCorrelationID(UUID.randomUUID().toString());
//                messageSent.setJMSReplyTo(queueRes);
//                messageSent.setText(num1 + "," + num2);
//
//                sender.send(messageSent);
//                System.out.println("Message sent: " + messageSent.getText() + " with Correlation ID: " + messageSent.getJMSCorrelationID());
//
//
//                TextMessage messageReceived = (TextMessage) receiver.receive();
//                System.out.println("Message received: " + messageReceived.getText() + " with Correlation ID: " + messageReceived.getJMSCorrelationID());
//
//                response.getWriter().println(messageReceived.getText());
//            }catch (JMSException e){
//                System.out.println("JMS Error: " + e.getMessage());
//                response.getWriter().println("JMS Error: " + e.getMessage());
//            }
//        } catch (Exception e) {
//            System.out.println("Error: " + e.getMessage());
//            response.getWriter().println("Error: " + e.getMessage());
//        }
//    }

