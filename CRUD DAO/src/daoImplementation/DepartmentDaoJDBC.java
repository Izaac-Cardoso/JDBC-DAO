package daoImplementation;

import dao.DepartmentDao;
import entities.Department;
import utility.DBException;
import utility.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    PreparedStatement pstmt = null;
    ResultSet result = null;
    Connection connection = null;

    @Override
    public void insert(Department department) {
        try(Connection connection = DataBaseConnection.connect()) {
            pstmt = connection.prepareStatement("""
                    INSERT INTO department (departmentID, departmentName)
                    VALUES (?, ?);""");

            pstmt.setInt(1, department.getId());
            pstmt.setString(2, department.getName());

            pstmt.executeUpdate();
        }
        catch(SQLException e) {
            throw new DBException(e.getMessage());
        }

        DataBaseConnection.closeStatement(pstmt);
    }

    @Override
    public void update(Department department) {

        try (Connection connection = DataBaseConnection.connect()) {
            pstmt = connection.prepareStatement("""
                    UPDATE department SET departmentName = ?
                    WHERE departmentID = ?
                    """);

            pstmt.setString(1, department.getName());
            pstmt.setInt(2, department.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }

        DataBaseConnection.closeStatement(pstmt);
    }

    @Override
    public void delete(int id) {
        try(Connection connection = DataBaseConnection.connect()) {
            pstmt = connection.prepareStatement("""
                    DELETE FROM department.*
                    WHERE departmentID = ?""");

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
        catch(SQLException e) {
            throw new DBException(e.getMessage());
        }

        DataBaseConnection.closeStatement(pstmt);
    }

    private Department createDepartment(ResultSet result) throws SQLException {
        return new Department(result.getInt("departmentID"), result.getString("departmentName"));
    }

    @Override
    public Department findById(int id) {
        Department department = null;

        try(Connection connection = DataBaseConnection.connect()) {
            pstmt = connection.prepareStatement("""
                    SELECT * FROM department
                    WHERE departmentID = ?""");

            pstmt.setInt(1, id);
            result = pstmt.executeQuery();

            if(result.next()) {
                System.out.println("Record found for id:" + id);
                department = createDepartment(result);
            } else {
                System.out.println("No record found for this id!");
            }
        }
        catch(SQLException e) {
            throw new DBException(e.getMessage());
        }

        DataBaseConnection.closeStatement(pstmt);
        DataBaseConnection.closeResultSet(result);

        return department;
    }

    @Override
    public List<Department> findAll() {
        Department department = null;
        List<Department> departments = new ArrayList<>();

        try(Connection connection = DataBaseConnection.connect()) {
            pstmt = connection.prepareStatement("""
                    SELECT * FROM department
                    ORDER BY departmentID;""");

            result = pstmt.executeQuery();
            while (result.next()) {
                department = createDepartment(result);
                departments.add(department);
            }
        }
        catch(SQLException e) {
            throw new DBException(e.getMessage());
        }

        DataBaseConnection.closeStatement(pstmt);
        DataBaseConnection.closeResultSet(result);

        return departments;
    }

    public void closeDataBase() {

        DataBaseConnection.disconnectDataBase();
    }

}
