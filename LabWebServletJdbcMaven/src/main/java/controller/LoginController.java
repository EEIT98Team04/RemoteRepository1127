package controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import model.CustomerBean;
import model.CustomerService;

@Controller
@RequestMapping("/secure/login.controller")
@SessionAttributes(names={"user"})
public class LoginController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping(
			method={RequestMethod.GET, RequestMethod.POST}
	)
	public String method(String username, String password, Model model) {
//讀取使用者輸入資料
//進行必要的資料型態轉換
//進行資料檢查
		Map<String, String> errors = new HashMap<>();
		model.addAttribute("errors", errors);

		if (username == null || username.trim().length() == 0) {
			errors.put("xxx1", "請輸入帳號(MVC)");
		}
		if (password == null || password.trim().length() == 0) {
			errors.put("xxx2", "請輸入帳號(MVC)");
		}

		if (errors != null && !errors.isEmpty()) {
			return "login.error";
		}		
		
//進行商業服務
		CustomerBean bean = customerService.login(username, password);
		
//依照執行結果挑選適當的View元件
		if(bean==null) {
			errors.put("xxx2", "登入失敗 (MVC)");
			return "login.error";
		} else {
			model.addAttribute("user", bean);
			return "login.success";
		}
	}
}
