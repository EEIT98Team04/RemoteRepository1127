package controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.ProductBean;
import model.ProductService;
import model.spring.PrimitiveNumberEditor;

@Controller
@RequestMapping("/pages/product.controller")
public class ProductController {
	
	@Autowired
	private ProductService productService;

	@InitBinder
	public void initialize(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(java.util.Date.class, "make",
				new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		
		webDataBinder.registerCustomEditor(int.class,
				new PrimitiveNumberEditor(java.lang.Integer.class, true));
		
		webDataBinder.registerCustomEditor(double.class, "price",
				new PrimitiveNumberEditor(java.lang.Double.class, true));
	}
	
	@RequestMapping(
			method= {RequestMethod.GET, RequestMethod.POST}
	)
	public String method(String prodaction,
			ProductBean bean, BindingResult bindingResult, Model model) {
//讀取使用者輸入資料
//進行必要的資料型態轉換
		Map<String, String> errors = new HashMap<>();
		model.addAttribute("errors", errors);
		
		if(bindingResult!=null && bindingResult.hasErrors()) {
			if(bindingResult.getFieldError("id")!=null) {
				errors.put("xxx1", "Id必須是整數");
			}
			if(bindingResult.getFieldError("price")!=null) {
				errors.put("xxx2", "Price必須是數字");
			}
			if(bindingResult.getFieldError("make")!=null) {
				errors.put("xxx3", "Make必須是日期(YYYY-MM-DD)");
			}
			if(bindingResult.getFieldError("expire")!=null) {
				errors.put("xxx4", "Expire必須是整數");
			}
		}

//進行資料檢查
		if("Insert".equals(prodaction) || "Update".equals(prodaction) || "Delete".equals(prodaction)) {
			if(bean==null || bean.getId()==0) {
				errors.put("xxx1", "請輸入Id以便執行"+prodaction);
			}
		}
		
		if (errors != null && !errors.isEmpty()) {
			return "product.error";
		}

//進行商業服務
//依照執行結果挑選適當的View元件
		if("Select".equals(prodaction)) {
			List<ProductBean> result = productService.select(bean);
			model.addAttribute("select", result);
			return "product.select";
		} else if("Insert".equals(prodaction)) {
			ProductBean result = productService.insert(bean);
			if(result==null) {
				errors.put("action", "Insert failed");
			} else {
				model.addAttribute("insert", result);
			}
			return "product.insert";
		} else if("Update".equals(prodaction)) {
			ProductBean result = productService.update(bean);
			if(result==null) {
				errors.put("action", "Update failed");
			} else {
				model.addAttribute("update", result);
			}
			return "product.update";
		} else if("Delete".equals(prodaction)) {
			boolean result = productService.delete(bean);
			if(result) {
				model.addAttribute("delete", 1);
			} else {
				model.addAttribute("delete", 0);
			}
			return "product.delete";
		} else {
			errors.put("action", "Unknown prodaction:"+prodaction);
			return "product.error";
		}
	}
}
