<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pending Leave Applications</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f8fc;
            color: #333;
            margin: 0;
            padding: 0;
        }
        h2 {
            text-align: center;
            color: #1e3a8a;
            margin: 20px 0;
        }
        table {
            width: 90%;
            margin: 20px auto;
            border-collapse: collapse;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        }
        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: center;
        }
        th {
            background-color: #1e3a8a;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #e0f2fe;
        }
        tr:hover {
            background-color: #cce4ff;
        }
        .highlight-row {
            background-color: #a5d8ff !important;
        }
        .action-button {
            display: block;
            margin: 20px auto;
            padding: 10px 20px;
            background-color: #1e3a8a;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
        }
        .action-button:disabled {
            background-color: #9aaedb;
            cursor: not-allowed;
        }
        .note {
            text-align: center;
            font-size: 14px;
            color: #555;
        }
    </style>
    <script>
        function enableButton(row) {
            let rows = document.querySelectorAll("table tr");
            rows.forEach(r => r.classList.remove("highlight-row"));
            row.classList.add("highlight-row");
            document.getElementById("approveBtn").disabled = false;
        }
    </script>
</head>
<body>

<h2>My Reporting Employees' Pending Leave Applications Section</h2>

<table>
    <tr>
        <th>Employee ID</th>
        <th>Employee Name</th>
        <th>Leave ID</th>
        <th>Number of Days</th>
        <th>Start Date</th>
        <th>End Date</th>
        <th>Leave Type</th>
        <th>Status</th>
        <th>Reason</th>
        <th>Leave Balance</th>
    </tr>
    <c:forEach var="leave" items="${leavePending}">
        <tr onclick="enableButton(this)">
            <td>${leave.empId}</td>
            <td>${leave.empName}</td>
            <td>${leave.leaveId}</td>
            <td>${leave.noOfDays}</td>
            <td>${leave.leaveStDt}</td>
            <td>${leave.leaveEndDt}</td>
            <td>${leave.leaveType}</td>
            <td>${leave.leaveStatus}</td>
            <td>${leave.leaveReason}</td>
            <td>${leave.leaveBal}</td>
        </tr>
    </c:forEach>
</table>

<button id="approveBtn" class="action-button" disabled onclick="location.href='ApproveDeny.jsp'">Approve/Deny</button>

<div class="note">
    <p>Selecting a row highlights the leave row and enables the button. Clicking the button goes to the "Approve/Deny" screen.</p>
    <p>Default sorted by employee ID ascending, and within an employee, by the start date descending.</p>
</div>

</body>
</html>
