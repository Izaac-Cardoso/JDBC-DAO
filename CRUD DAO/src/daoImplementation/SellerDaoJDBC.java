package daoImplementation;

import dao.SellerDao;
import entities.Department;
import entities.Seller;
import utility.DBException;
import utility.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SellerDaoJDBC implements SellerDao {

    PreparedStatement pstmt = null;
    ResultSet result = null;

    @Override
    public void insert(Seller seller) {
        try(Connection connection = DataBaseConnection.connect()) {
            PreparedStatement pstmt = connection.prepareStatement(
            """
            INSERT INTO seller (id, name, email, birthday, salary, department_id)
            VALUES (?, ?, ?, ?, ?, ?)""");

            pstmt.setInt(1, seller.getId());
            pstmt.setString(2, seller.getName());
            pstmt.setString(3, seller.getEmail());
            pstmt.setDate(4,new java.sql.Date(seller.getBirthDate().getTime()));
            pstmt.setDouble(5, seller.getSalary());
            pstmt.setInt(6, seller.getDepartment().getId());

            pstmt.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        DataBaseConnection.closeStatement(pstmt);
        DataBaseConnection.closeResultSet(result);

    }

    @Override
    public void update(Seller seller) {

        try(Connection connection = DataBaseConnection.connect()) {
            pstmt = connection.prepareStatement(
                    """
                    UPDATE seller SET name = ?, email = ?, birthday = ?, salary = ?, department_id = ?
                    WHERE id = ?""");

            pstmt.setInt(6, seller.getId());
            pstmt.setString(1, seller.getName());
            pstmt.setString(2, seller.getEmail());
            pstmt.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            pstmt.setDouble(4, seller.getSalary());
            pstmt.setInt(5, seller.getDepartment().getId());

            pstmt.executeUpdate();
        }
        catch(SQLException e) {
            throw new DBException(e.getMessage());
        }

        DataBaseConnection.closeStatement(pstmt);
        DataBaseConnection.closeResultSet(result);

    }

    @Override
    public void delete(int id) {
        try(Connection connection = DataBaseConnection.connect()) {
            pstmt = connection.prepareStatement("DELETE FROM seller.* WHERE seller.Id = ?");
            pstmt.setInt(1, id);

            pstmt.executeUpdate();
        }
        catch(SQLException e) {
            throw new DBException(e.getMessage());
        }

        DataBaseConnection.closeStatement(pstmt);
        DataBaseConnection.closeResultSet(result);
    }

    @Override
    public Seller findById(int id) {

        Seller seller = null;
        try(Connection connection = DataBaseConnection.connect()) {
            pstmt = connection.prepareStatement("""
                     SELECT seller.*, departmentName
                     FROM seller
                     JOIN department ON seller.department_id = departmentID
                     WHERE seller.id = ?""");

            pstmt.setInt(1, id);
            result = pstmt.executeQuery();

            if(result.next()) {
                Department dep = createDepartment(result);
                System.out.println("Record found for ID:" + id);
                seller = createSeller(result, dep);
            } else {
                System.out.println("No record found for ID:" + id);
            }
        }
        catch(SQLException e) {
            System.out.println("Element not found!");
            e.printStackTrace();
        }

        DataBaseConnection.closeStatement(pstmt);
        DataBaseConnection.closeResultSet(result);

        return seller;
    }

    private Seller createSeller(ResultSet result, Department dep) throws SQLException {
        return new Seller(result.getInt("id"), result.getString("name"),
                          result.getString("email"), result.getDate("birthday"),
                          result.getDouble("salary"), dep);
    }

    private Department createDepartment(ResultSet rs) throws SQLException {
        return new Department(rs.getInt("department_id"), rs.getString("departmentName"));
    }

    @Override
    public List<Seller> findByDepartment(Department department) throws SQLException {

        Seller seller = null;
        List<Seller> sellers = new ArrayList<>();
        Map<Integer, Department> mapDepartment = new HashMap<>();

        try(Connection connection = DataBaseConnection.connect()) {
            pstmt = connection.prepareStatement("""
                    SELECT seller.*, departmentName
                    FROM  seller
                    JOIN department ON seller.department_id = departmentID
                    WHERE departmentID = ?
                    ORDER BY seller.name;""");

            pstmt.setInt(1, department.getId());
            result = pstmt.executeQuery();

            while(result.next()) {
                /**
                 * mapDepartment will keep track whether a department has already been instantiated.
                 * we can have multiple seller objects, but only one department object of a given id,
                 * as this is a one-to-many relationship.
                 * Remember to use the child-table's foreign key when iterating over resultset.
                 */
                Department dep = mapDepartment.get(result.getInt("department_id"));
                if(dep == null) {
                    dep = createDepartment(result);
                    mapDepartment.put(result.getInt("department_id"), dep);
                }

                seller = createSeller(result, dep);
                sellers.add(seller);
            }
        }
        catch(SQLException e) {
            System.out.println("Element not found!");
            e.printStackTrace();
        }

        DataBaseConnection.closeStatement(pstmt);
        DataBaseConnection.closeResultSet(result);

        return sellers;
    }

    @Override
    public List<Seller> findAll() {
        Seller seller = null;
        List<Seller> sellers = new ArrayList<>();
        Map<Integer, Department> mapDepartment = new HashMap();

        try(Connection connection = DataBaseConnection.connect()) {
            pstmt = connection.prepareStatement("""
                    SELECT seller.*, departmentName
                    FROM seller
                    JOIN department ON seller.department_id = departmentID
                    ORDER BY seller.name
                    """);

            ResultSet result = pstmt.executeQuery();
            while(result.next()) {
                Department dep = mapDepartment.get(result.getInt("department_id"));
                if(dep == null) {
                    dep = createDepartment(result);
                    mapDepartment.put(result.getInt("department_id"), dep);
                }
                seller = createSeller(result, dep);
                sellers.add(seller);
            }
        }
        catch(SQLException e) {
            System.out.println("Elements not found!");
            e.printStackTrace();
        }

        DataBaseConnection.closeStatement(pstmt);
        DataBaseConnection.closeResultSet(result);

        return sellers;
    }

    public void closeDataBase() {
        try {
            pstmt.close();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DataBaseConnection.disconnectDataBase();
    }
}
