package dao;

import entities.Department;
import entities.Seller;

import java.sql.SQLException;
import java.util.List;

public interface SellerDao {

    public void insert(Seller seller);

    public void update(Seller seller);

    public void delete(int id);

    public Seller findById(int id) throws SQLException;

    public List<Seller> findAll() throws SQLException ;

    public List<Seller> findByDepartment(Department department) throws SQLException;

}
