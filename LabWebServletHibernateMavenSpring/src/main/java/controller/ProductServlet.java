package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ProductBean;
import model.ProductService;

public class ProductServlet extends HttpServlet {
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private ProductService productService;
	@Override
	public void init() throws ServletException {
		productService = new ProductService();
		
	}
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
//讀取使用者輸入資料
		String temp1 = request.getParameter("id");
		String name = request.getParameter("name");
		String temp2 = request.getParameter("price");
		String temp3 = request.getParameter("make");
		String temp4 = request.getParameter("expire");
		String prodaction = request.getParameter("prodaction");
		
//進行必要的資料型態轉換
		Map<String, String> errors = new HashMap<>();
		request.setAttribute("errors", errors);

		int id = 0;
		if(temp1!=null && temp1.length()!=0) {
			try {
				id = Integer.parseInt(temp1);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				errors.put("xxx1", "Id必須是整數");
			} 
		}
		
		double price = 0;
		if(temp2!=null && temp2.length()!=0) {
			try {
				price = Double.parseDouble(temp2);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				errors.put("xxx2", "Price必須是數字");
			}
		}
		
		java.util.Date make = null;
		if(temp3!=null && temp3.length()!=0) {
			try {
				make = simpleDateFormat.parse(temp3);
			} catch (ParseException e) {
				e.printStackTrace();
				errors.put("xxx3", "Make必須是日期(YYYY-MM-DD)");
			}
		}
		
		int expire = 0;
		if(temp4!=null && temp4.length()!=0) {
			try {
				expire = Integer.parseInt(temp4);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				errors.put("xxx4", "Expire必須是整數");
			} 
		}
		
//進行資料檢查
		if("Insert".equals(prodaction) || "Update".equals(prodaction) || "Delete".equals(prodaction)) {
			if(temp1==null || temp1.length()==0) {
				errors.put("xxx1", "請輸入Id以便執行"+prodaction);
			}
		}
		
		if(errors!=null && !errors.isEmpty()) {
			request.getRequestDispatcher(
					"/pages/product.jsp").forward(request, response);
			return;
		}
		
//進行商業服務
		ProductBean bean = new ProductBean();
		bean.setId(id);
		bean.setName(name);
		bean.setPrice(price);
		bean.setMake(make);
		bean.setExpire(expire);
		
//依照執行結果挑選適當的View元件
		if("Select".equals(prodaction)) {
			List<Object[]> result = productService.select(bean);
			request.setAttribute("select", result);
			request.getRequestDispatcher("/pages/display.jsp").forward(request, response);
		} else if("Insert".equals(prodaction)) {
			ProductBean result = productService.insert(bean);
			if(result==null) {
				errors.put("action", "Insert failed");
			} else {
				request.setAttribute("insert", result);
			}
			request.getRequestDispatcher("/pages/product.jsp").forward(request, response);
		} else if("Update".equals(prodaction)) {
			ProductBean result = productService.update(bean);
			if(result==null) {
				errors.put("action", "Update failed");
			} else {
				request.setAttribute("update", result);
			}
			request.getRequestDispatcher("/pages/product.jsp").forward(request, response);
		} else if("Delete".equals(prodaction)) {
			boolean result = productService.delete(bean);
			if(result) {
				request.setAttribute("delete", 1);
			} else {
				request.setAttribute("delete", 0);
			}
			request.getRequestDispatcher("/pages/product.jsp").forward(request, response);
		} else {
			errors.put("action", "Unknown prodaction:"+prodaction);
			request.getRequestDispatcher("/pages/product.jsp").forward(request, response);
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
