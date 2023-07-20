package com.nt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EmployeeSearchServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	//get PrintWriter
		PrintWriter pw=res.getWriter();
	//set response content type
		res.setContentType("text/html");
		
	//read form data
		int no=Integer.parseInt(req.getParameter("eno"));
		
	//load jdbc driver class
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch(Exception e) { 
			e.printStackTrace();
		}
		try(PrintWriter pw1=res.getWriter();
				Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "tiger");
				PreparedStatement ps=con.prepareStatement("SELECT EMPNO,ENAME,JOB,SAL,DEPTNO FROM EMP WHERE EMPNO=?");
				){
			//set value to query params
			ps.setInt(1, no);
			//execute the query
			try(ResultSet rs=ps.executeQuery()){
			//process the ResultSet object
			if(rs.next()) {
				pw1.print("<table border='1' bgcolor='cyan'align='center'>");
				pw1.print("<tr><th>Empno</th><th>Ename</th><th>job</th><th>salary</th><th>depno</th></tr>");
				pw1.print("<tr><td>"+rs.getInt(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td><td>"+rs.getDouble(4)+"</td><td>"+rs.getDouble(5)+"</td></tr>");
				pw1.println("</table");
			}
			else {
				pw1.print("<h1 style='color:red'>No records Found</h1>");
			}
			//add home hyperlink
			pw.println("<h1 style='text-align:center'><a href='input.html'>Home</a></h1>");
		}//try2
	}//try1 
		
		catch(Exception e) {
			e.printStackTrace();
		}
	}//method
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}//method

}//class
