<%@ page import="jakarta.ws.rs.core.Request" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Sum Calculation</title>
</head>
<body>
<style>
  .form-container {
    margin: 0 auto;
    width: fit-content;
    text-align: left;
  }
</style>
<div class="form-container">

  <h2>Enter Two Numbers:</h2>
  <form action="SenderServletMul1" method="post">
    <label for="num1">Number 1:</label>
    <input type="number" id="num1" name="num1" required><br>

    <label for="num2">Number 2:</label>
    <input type="number" id="num2" name="num2" required><br>

    <br><input type="submit" value="Calculate Sum"><br>

<%--    <br><div class="result"><% String sum = (String) session.getAttribute("responseMessage"); %>--%>
<%--    SUM: <%= sum != null ? sum : "No response received" %></div>--%>

    <br><div class="result"><% String sum = (String) request.getAttribute("responseMessage"); %>
    SUM: <%= sum != null ? sum : "No response received" %></div>

  </form>
</div>
</body>

<%--<h2>Please enter your message for sender 2</h2>--%>
<%--<form action="SenderServlet" method="post">--%>
<%--  <label for="message2">Message</label>--%>
<%--  <input type="text" id="message2" name="message" required><br>--%>

<%--  &lt;%&ndash;  <label for="num2">Number 2:</label>&ndash;%&gt;--%>
<%--  &lt;%&ndash;  <input type="number" id="num2" name="num2" required><br>&ndash;%&gt;--%>

<%--  <input type="submit" value="Send">--%>
<%--  <div class="result"> message : <%= session.getAttribute("messageReceived") %> <span id="Result"></span></div>--%>
<%--</form>--%>
