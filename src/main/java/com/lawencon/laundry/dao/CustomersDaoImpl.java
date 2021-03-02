package com.lawencon.laundry.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.laundry.model.Customers;
import com.lawencon.laundry.model.Profiles;

/**
 * @author Imron Rosyadi
 */

@Repository(value = "cust")
public class CustomersDaoImpl extends BaseDao implements CustomersDao {

	@Override
	public List<Customers> getListCustomers() throws Exception {
		List<Customers> listCustomers = new ArrayList<>();
		String sql = bBuilder("SELECT c.cust_code, c.cust_name, c.cust_phone, c.cust_address, p.pro_name",
				"	FROM tbl_m_customers c INNER JOIN tbl_m_profiles p ON p.id = c.id_profile").toString();
		List<?> listObj = entityManager.createNativeQuery(sql).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			Customers c = new Customers();
			c.setCustCode((String) objArr[0]);
			c.setCustName((String) objArr[1]);
			c.setCustPhone((String) objArr[2]);
			c.setCustAddress((String) objArr[3]);

			Profiles p = new Profiles();
			p.setProfileName((String) objArr[4]);
			c.setIdProfile(p);

			listCustomers.add(c);
		});

		return listCustomers;
	}

	@Override
	public Customers findByCode(String code) throws Exception {
		String sql = bBuilder("SELECT c.cust_code, c.cust_name, c.cust_phone, c.cust_address, p.pro_name",
				"	FROM tbl_m_customers c INNER JOIN tbl_m_profiles p ON p.id = c.id_profile",
				"   WHERE c.cust_code = ? ").toString();
		List<Customers> listCustomers = new ArrayList<>();
		List<?> listObj = entityManager.createNativeQuery(sql).setParameter(1, code).getResultList();
		listObj.forEach(val -> {
			Object[] objArr = (Object[]) val;
			Customers c = new Customers();
			c.setCustCode((String) objArr[0]);
			c.setCustName((String) objArr[1]);
			c.setCustPhone((String) objArr[2]);
			c.setCustAddress((String) objArr[3]);

			Profiles p = new Profiles();
			p.setProfileName((String) objArr[4]);
			c.setIdProfile(p);

			listCustomers.add(c);
		});
		return listCustomers.size() > 0 ? listCustomers.get(0) : null;
	}

	@Override
	public Customers insertCustomer(Customers cust) throws Exception {
		String sql = bBuilder("INSERT INTO tbl_m_customers(cust_code, cust_name, cust_phone, cust_address, id_profile)",
				"	VALUES (?, ?, ?, ?, (SELECT id FROM tbl_m_profiles WHERE pro_code = ?))").toString();

		entityManager.createNativeQuery(sql, Customers.class).setParameter(1, cust.getCustCode())
				.setParameter(2, cust.getCustName()).setParameter(3, cust.getCustPhone())
				.setParameter(4, cust.getCustAddress()).setParameter(5, cust.getIdProfile().getProfileCode())
				.executeUpdate();

		return cust;
	}

	@Override
	public void update(Customers cust) throws Exception {
		String sql = bBuilder(
				"UPDATE tbl_m_customers SET cust_code = ?, cust_name = ?, cust_phone = ?, cust_address = ?, ",
				" id_profile = (SELECT id FROM tbl_m_profiles WHERE pro_code = ?) WHERE id = ? ").toString();
		entityManager.createNativeQuery(sql).setParameter(1, cust.getCustCode()).setParameter(2, cust.getCustName())
				.setParameter(3, cust.getCustPhone()).setParameter(4, cust.getCustAddress())
				.setParameter(5, cust.getIdProfile().getProfileCode()).setParameter(6, cust.getId()).executeUpdate();
	}

	@Override
	public void delete(Long id) throws Exception {
		String sql = "DELETE FROM tbl_m_customers WHERE id = ?";
		entityManager.createNativeQuery(sql, Customers.class).setParameter(1, id).executeUpdate();
	}

	@Override
	public Customers getCustomerById(Long id) throws Exception {
		String sql = "SELECT * FROM tbl_m_customers WHERE id = ?1 ";
		entityManager.createNativeQuery(sql, Customers.class).setParameter(1, id);
		return null;
	}
}
