package dao;

import entities.Department;

import java.util.List;

public interface DepartmentDao {

    public void insert(Department obj);

    public void update(Department obj);

    public void delete(int id);

    public Department findById(int id);

    public List<Department> findAll();
}
