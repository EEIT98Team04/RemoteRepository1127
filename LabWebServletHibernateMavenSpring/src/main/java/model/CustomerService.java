package model;

import java.util.Arrays;

import model.dao.CustomerDAOHibernate;
import model.hibernate.HibernateUtil;

public class CustomerService {
	private CustomerDAO customerDao;
	public CustomerService() {
		customerDao = new CustomerDAOHibernate(HibernateUtil.getSessionFactory());
	}
	public static void main(String[] args) {
		try {
			HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

			CustomerService customerService = new CustomerService();
			CustomerBean login = customerService.login("Babe", "B");
			System.out.println("login="+login);
			
			boolean change = customerService.changePassword("Ellen", "EEE", "E");
			System.out.println("change="+change);
			
			HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
			HibernateUtil.getSessionFactory().getCurrentSession().close();
		} finally {
			HibernateUtil.closeSessionFactory();
		}
	}
	public CustomerBean login(String username, String password) {
		CustomerBean bean = customerDao.select(username);
		if(bean!=null) {
			if (password!=null && password.length()!=0) {
				byte[] pass = bean.getPassword();
				byte[] temp = password.getBytes();
				if (Arrays.equals(pass, temp)) {
					return bean;
				} 
			}
		}
		return null;
	}
	public boolean changePassword(String username, String oldPassword, String newPassword) {
		CustomerBean bean = this.login(username, oldPassword);
		if(bean!=null) {
			if(newPassword!=null && newPassword.length()!=0) {
				byte[] temp = newPassword.getBytes();
				return customerDao.update(
						temp, bean.getEmail(), bean.getBirth(), username);
			}
		}
		return false;
	}
}
