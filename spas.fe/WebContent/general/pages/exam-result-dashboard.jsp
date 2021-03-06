<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="com.joelkingsley.rmkcet.spas.fe.beans.ExamResult"%>
<%@ page import ="com.joelkingsley.rmkcet.spas.fe.beans.Exam"%>
<%@ page import ="com.joelkingsley.rmkcet.spas.fe.beans.Student"%>
<%@ page import ="com.joelkingsley.rmkcet.spas.fe.beans.Batch"%>
<%@ page import ="com.joelkingsley.rmkcet.spas.fe.beans.Department"%>
<%@ page import ="com.joelkingsley.rmkcet.spas.fe.beans.ExamType"%>
<%@ page import ="com.joelkingsley.rmkcet.spas.fe.beans.Subject"%>
<%@ page import ="com.joelkingsley.rmkcet.spas.fe.beans.Semester"%>
<html>

<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="/spas.fe/general/css/header.css">
    <link rel="stylesheet" href="/spas.fe/general/css/navbar.css">
    <link rel="stylesheet" href="/spas.fe/general/css/dashboard.css">
    <link rel="stylesheet" href="/spas.fe/general/css/fonts.css">
    <link rel="stylesheet" href="/spas.fe/general/css/student-dashboard.css">
    <link rel="stylesheet" href="/spas.fe/administrator/css/administrator.css">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

</head>

<body>
    <header>
        <div id="ztech-logo">
            <img src="/spas.fe/assets/spas.png">
        </div>
    </header>
    <section class="navbar">
        <div class="courier bold">
            <a href="" class="active menu-button">Dashboard</a>
            <div class="dropdown-content dashboard-drop">
                <a href="/spas.fe/studentDashboard">Students</a>
                <a href="/spas.fe/examDashboard">Exams</a>
                <a href="/spas.fe/examResultDashboard">Exam Results</a>
            </div>
        </div>
        <div class="courier bold">
            <a href="" class="menu-button">Manage</a>
            <div class="dropdown-content lt-drop">
                <a href="/spas.fe/manageBatch">Batch</a>
                <a href="/spas.fe/manageDepartment">Department</a>
                <a href="/spas.fe/manageSubject">Subject</a>
                <a href="/spas.fe/manageStudent">Student</a>
                <a href="/spas.fe/manageSemester">Semester</a>
                <a href="/spas.fe/manageExamType">Exam Type</a>
                <a href="/spas.fe/manageExam">Exam</a>
                <a href="/spas.fe/manageExamResult">Exam Result</a>
            </div>
        </div>
        <div class="courier bold">
            <a href="" class="menu-button">Users</a>
            <div class="dropdown-content gt-drop">
                <a href="/spas.fe/manageUser">Manage User</a>
            </div>
        </div>
    </section>
    <section class="courier">
        <div class="body-title">EXAM</div>
        <table class="student-table">
            <tr>
                <th>Exam Result ID</th>
                <th>Exam ID</th>
                <th>Register Number</th>
                <th>Student Name</th>
                <th>Marks/Grade</th>
            </tr>
            <% 
            if (request.getAttribute("examResults") != null) {
                ArrayList<ExamResult> examResults = (ArrayList<ExamResult>) request.getAttribute("examResults");
                        for(int i=0; i < examResults.size(); i++) {
                            out.println("<tr>");
                            out.println("<td>" + examResults.get(i).getExamResultID() + "</td>");
                            out.println("<td>" + examResults.get(i).getExam().getExamID() + "</td>");
                            out.println("<td>" + examResults.get(i).getStudent().getRegisterNumber() + "</td>");
                            out.println("<td>" + examResults.get(i).getStudent().getStudentName() + "</td>");
                            out.println("<td>" + examResults.get(i).getMarks() + "/" + examResults.get(i).getGrade() + "</td>");
            
                            out.println("</tr>");
                        }
            }
         %>
        </table>
    </section>
</body>

</html>