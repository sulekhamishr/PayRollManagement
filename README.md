<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee and Leave Info</title>
</head>
<body>
 <!-- Adding Blue and White Theme CSS -->
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f8fc; /* Light blue background */
            color: #333; /* Dark text for readability */
            margin: 0;
            padding: 0;
        }
        
        h1 {
            text-align: center;
            color: #1e3a8a; /* Deep blue */
            margin-top: 20px;
        }

        table {
            width: 90%;
            margin: 20px auto;
            border-collapse: collapse;
            border: 1px solid #ddd;
        }

        th, td {
            padding: 12px;
            text-align: center;
            border: 1px solid #ddd;
        }

        th {
            background-color: #1e3a8a; /* Deep blue for header */
            color: white; /* White text for header */
        }

        tr:nth-child(even) {
            background-color: #e0f2fe; /* Lighter blue for even rows */
        }

        tr:hover {
            background-color: #cce4ff; /* Hover effect on rows */
        }

        a {
            text-decoration: none;
            color: #1e3a8a; /* Deep blue color for links */
            font-weight: bold;
        }

        a:hover {
            color: #2563eb; /* Lighter blue on hover */
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
    </style>
</head>
<body>


    <jsp:useBean id="employDao" class="com.java.lms.dao.EmployDaoImpl" />
    <jsp:useBean id="leaveDao" class="com.java.lms.dao.LeaveDaoImpl" />

    <c:set var="empId" value="${param.empId}" />
    <c:set var="mgrId" value="${param.mgrId}" />
    <c:set var="myInfo" value="${employDao.searchEmployDao(empId)}" />
    <c:set var="myManagerInfo" value="${employDao.searchEmployDao(mgrId)}" />
    <c:set var="leaveInfo" value="${leaveDao.showLeaveHistory(empId)}" />
    <c:set var="leavePending" value="${leaveDao.pendingLeaveList(empId)}"/> 
    

    <h2 align="center">Employee Information</h2>
    <a href="ApplyLeave.jsp?empId=${param.empId }">Apply Leave</a>
    <table border="3" align="center">
        <tr>
            <td>
                <p>Employee Id: <b>${myInfo.empId}</b></p>
                <p>Name: <b>${myInfo.empName}</b></p>
                <p>Email: <b>${myInfo.empMail}</b></p>
                <p>Mobile No: <b>${myInfo.empMobno}</b></p>
                <p>Department: <b>${myInfo.empDept}</b></p>
                <p>Date Of Join: <b>${myInfo.empJoinDate}</b></p>
                <p>Manager Id: <b>${myInfo.empMgrId}</b></p>
                <p>Leave Balance: <b>${myInfo.empLeaveBal}</b></p>
            </td>
            <td>
                <p>Manager Id: <b>${myManagerInfo.empId}</b></p>
                <p>Name: <b>${myManagerInfo.empName}</b></p>
                <p>Email: <b>${myManagerInfo.empMail}</b></p>
                <p>Mobile No: <b>${myManagerInfo.empMobno}</b></p>
                <p>Department: <b>${myManagerInfo.empDept}</b></p>
                <p>Date Of Join: <b>${myManagerInfo.empJoinDate}</b></p>
                <p>Manager’s Manager Id: <b>${myManagerInfo.empMgrId}</b></p>
                <p>Leave Balance: <b>${myManagerInfo.empLeaveBal}</b></p>
            </td>
        </tr>
    </table>

    <h2 align="center">Leave History</h2>
    <table border="3" align="center">
        <tr>
            <th>Leave ID</th>
            <th>No. of Days</th>
            <th>Manager Comments</th>
            <th>Emp ID</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Type</th>
            <th>Status</th>
            <th>Reason</th>
        </tr>
        <c:forEach var="leave" items="${leaveInfo}">
            <tr>
                <td>${leave.leaveId}</td>
                <td>${leave.noOfDays}</td>
                <td>${leave.leaveMgrCmts}</td>
                <td>${leave.empId}</td>
                <td>${leave.leaveStDt}</td>
                <td>${leave.leaveEndDt}</td>
                <td>${leave.leaveType}</td>
                <td>${leave.leaveStatus}</td>
                <td>${leave.leaveReason}</td>
            </tr>
        </c:forEach>
    </table>
    
    <h2 align="center">View Leave History</h2>
    <table border="3" align="center">
    
      <tr>
            <th>Leave ID</th>
            <th>No. of Days</th>
            <th>Emp ID</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Type</th>
            <th>Status</th>
            <th>Reason</th>
        </tr>
        <c:forEach var="leavePending" items="${leavePending}">
        <tr>
                <td>${leavePending.leaveId}</td>
                <td>${leavePending.noOfDays}</td>
                <td>${leavePending.empId}</td>
                <td>${leavePending.leaveStDt}</td>
                <td>${leavePending.leaveEndDt}</td>
                <td>${leavePending.leaveType}</td>
                <td>${leavePending.leaveStatus}</td>
                <td>${leavePending.leaveReason}</td>
            </tr>
        </c:forEach>
    </table>
    
   
    </tr>
</body>
</html>
