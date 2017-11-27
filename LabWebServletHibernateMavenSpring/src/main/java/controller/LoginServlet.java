package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CustomerBean;
import model.CustomerService;

public class LoginServlet extends HttpServlet {
	private CustomerService customerService;
	@Override
	public void init() throws ServletException {
		customerService = new CustomerService();
	}
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
//讀取使用者輸入資料
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
//進行必要的資料型態轉換
//進行資料檢查
		Map<String, String> errors = new HashMap<>();
		request.setAttribute("errors", errors);
		
		if(username==null || username.trim().length()==0) {
			errors.put("xxx1", "請輸入帳號");
		}
		if(password==null || password.trim().length()==0) {
			errors.put("xxx2", "請輸入帳號");
		}
		
		if(errors!=null && !errors.isEmpty()) {
			request.getRequestDispatcher(
					"/secure/login.jsp").forward(request, response);
			return;
		}
		
//進行商業服務
		CustomerBean bean = customerService.login(username, password);

//依照執行結果挑選適當的View元件
		if(bean==null) {
			errors.put("xxx2", "登入失敗");
			request.getRequestDispatcher(
					"/secure/login.jsp").forward(request, response);
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("user", bean);
			
			String path = request.getContextPath();
			response.sendRedirect(path+"/index.jsp");
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
