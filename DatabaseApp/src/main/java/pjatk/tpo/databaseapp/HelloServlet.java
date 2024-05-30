package pjatk.tpo.databaseapp;

import java.io.*;
import java.sql.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    String DBurl;
    String DBuser;
    String DBpassword;

    public void init() {
        DBurl = "jdbc:oracle:thin:@//db-oracle02.pjwstk.edu.pl:1521/baza.pjwstk.edu.pl";
        DBuser = "";
        DBpassword = "";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        ResultSet resultSet = null;
        Connection connection;


        PrintWriter out = response.getWriter();

        {
            out.println("""
                    <!DOCTYPE html>
                    <html lang="pl">
                    <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>employees</title>
                        <style>
                            body {
                                font-family: Arial, sans-serif;
                                margin: 80px;
                                background-color: #f4f4f4;
                                color: #333;
                                 background-image: url('backgroundOfficeLight.jpg');
                                background-size: cover;
                                background-repeat: no-repeat;
                                background-attachment: fixed;
                            }
                                        
                            .dark-mode {
                                background-color: #333;
                                color: #f4f4f4;
                                background-image: url('backgroundOffice.jpg');
                                background-size: cover;
                                background-repeat: no-repeat;
                                background-attachment: fixed;
                            }
                                        
                            h1 {
                                text-align: center;
                                color: inherit;
                            }
                                        
                            form {
                                display: flex;
                                justify-content: space-between;
                                align-items: center;
                                padding: 20px;
                                background-color: #ffffff;
                                border-radius: 5px;
                                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                                margin-bottom: 20px;
                            }
                                        
                            .dark-mode form {
                                background-color: #383838;
                            }
                                        
                            .form-group {
                                display: flex;
                                flex-direction: column;
                                margin-right: 15px;
                            }
                                        
                            .form-group label {
                                margin-bottom: 5px;
                                font-weight: bold;
                                color: inherit;
                            }
                                        
                            .form-group input {
                                padding: 8px;
                                border: 1px solid #7d7d7d;
                                border-radius: 4px;
                                width: 200px;
                                background-color: inherit;
                                color: inherit;
                            }
                                        
                            button {
                                padding: 10px 20px;
                                background-color: #28a745;
                                color: #ffffff;
                                border: none;
                                border-radius: 4px;
                                cursor: pointer;
                                font-size: 16px;
                            }
                                        
                            button:hover {
                                background-color: #218838;
                            }
                                        
                            .red {
                                background-color: #dc3545;
                            }
                                        
                            .red:hover {
                                background-color: #c82333;
                            }
                                        
                            table {
                                width: 100%;
                                border-collapse: collapse;
                                margin-top: 20px;
                                background-color: #ffffff;
                                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                            }
                                        
                            .dark-mode table {
                                background-color: #383838;
                            }
                                        
                            table, th, td {
                                border: 1px solid #ddd;
                            }
                                        
                            .dark-mode table, .dark-mode th, .dark-mode td {
                                border: 1px solid #3F3F3FFF;
                            }
                                        
                            th, td {
                                padding: 12px;
                                text-align: left;
                            }
                                        
                            th {
                                background-color: #f8f8f8;
                            }
                                        
                            .dark-mode th {
                                background-color: #333232;
                            }
                                        
                            .dark-mode-switch {
                                display: flex;
                                justify-content: flex-end;
                                margin-bottom: 20px;
                            }
                                        
                            .dark-mode-switch input {
                                margin-left: 10px;
                            }
                        </style>
                    </head>
                    <body class="dark-mode">
                                        
                    <div class="dark-mode-switch">
                        <label for="darkMode">Dark mode:</label>
                        <input type="checkbox" id="darkMode" checked="checked">
                    </div>
                                        
                    <h1>Employees</h1>
                                        
                    <form id="dataForm">
                        <div class="form-group">
                            <label for="name">Name:</label>
                            <input type="text" id="name" name="name">
                        </div>
                                        
                        <div class="form-group">
                            <label for="mgr">Manager:</label>
                            <input type="text" id="mgr" name="mgr">
                        </div>
                                        
                        <div class="form-group">
                            <label for="salFrom">Salary from:</label>
                            <input type="number" id="salFrom" name="salFrom">
                        </div>
                        <div class="form-group">
                            <label for="salTo">Salary to:</label>
                            <input type="number" id="salTo" name="salTo">
                        </div>
                                        
                        <div class="form-group">
                            <label for="Dname">Departament name:</label>
                            <input type="text" id="Dname" name="Dname">
                        </div >
                        <div class="form-group">
                            <button type="submit">Submit</button>
                            <button class="red" onclick="history.back()">Go back</button>
                        </div>
                                        
                    </form>
                                        
                    <table id="dataTable">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Job</th>
                            <th>Manager</th>
                            <th>Salary</th>
                            <th>Department</th>
                        </tr>
                        </thead>
                        <tbody>""");

        }
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(DBurl, DBuser, DBpassword);
            Statement statement = connection.createStatement();
            String nameForm = request.getParameter("name");
            String mgrForm = request.getParameter("mgr");
            String salFromForm = request.getParameter("salFrom");
            String salToForm = request.getParameter("salTo");
            String DnameForm = request.getParameter("Dname");

            String querry = "SELECT e.EMPNO, e.ename, e.job, mngr.ENAME AS \"MGR\", e.sal, dept.dname from emp e inner join DEPT  on dept.DEPTNO = e.DEPTNO LEFT join EMP mngr on e.MGR = mngr.EMPNO";
            boolean hasWhere = false;


            if (nameForm != null && !nameForm.isEmpty()) {
                querry += " WHERE e.ename = '" + nameForm.toUpperCase() + "'";
                hasWhere = true;
            }
            if (mgrForm != null && !mgrForm.isEmpty()) {
                if (!hasWhere) {
                    querry += " Where";
                    hasWhere = true;
                } else {
                    querry += " AND";
                }
                querry += " mngr.ename = '" + mgrForm.toUpperCase() + "'";
            }
            if (salFromForm != null && !salFromForm.isEmpty()) {
                if (!hasWhere) {
                    querry += " Where";
                    hasWhere = true;
                } else {
                    querry += " AND";
                }
                querry += " e.sal >= " + salFromForm;
            }
            if (salToForm != null && !salToForm.isEmpty()) {
                if (!hasWhere) {
                    querry += " Where";
                    hasWhere = true;
                } else {
                    querry += " AND";
                }
                querry += " e.sal <= " + salToForm;
            }
            if (DnameForm != null && !DnameForm.isEmpty()) {
                if (!hasWhere) {
                    querry += " Where";
                    hasWhere = true;
                } else {
                    querry += " AND";
                }
                querry += " dept.dname = '" + DnameForm.toUpperCase() + "'";
            }
            System.out.println(querry);
            resultSet = statement.executeQuery(querry);
            while (resultSet.next()) {
                int empno = resultSet.getInt("empno");
                String ename = resultSet.getString("ename");
                String job = resultSet.getString("job");
                String mgr = resultSet.getString("mgr");
                String sal = resultSet.getString("sal");
                String dname = resultSet.getString("dname");
                out.printf("<tr><td>%d</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>", empno, ename, job, mgr, sal, dname);
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        {
            out.println("""
                                           </tbody>
                                              </table>
                                              
                                              <script>
                                                  function setCookie(cname, cvalue, exdays) {
                                                             const d = new Date();
                                                             d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
                                                             let expires = "expires=" + d.toUTCString();
                                                             document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
                                                         }
                                                     
                                                         if (!document.cookie.includes('darkMode=false')) {
                                                             document.body.classList.add('dark-mode');
                                                             document.getElementById("darkMode").checked = true;
                                                         } else {
                                                             document.body.classList.remove('dark-mode');
                                                             document.getElementById("darkMode").checked = false;
                                                     
                                                         }
                                                     
                                                         document.getElementById('darkMode').addEventListener("load", function () {
                                                         });
                                                     
                                                         document.getElementById('darkMode').addEventListener('change', function () {
                                                             if (this.checked) {
                                                                 document.body.classList.add('dark-mode');
                                                                 document.cookie = 'darkMode=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;SameSite=None;secure';
                                                             } else {
                                                                 document.body.classList.remove('dark-mode');
                                                                 setCookie("darkMode", false, 1);
                                                             }
                                                         });
                                              </script>
                                              </body>
                                              </html>
                    """);
        }
    }
    public void destroy() {
    }
}