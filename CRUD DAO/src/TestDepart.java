import dao.DaoFactory;
import dao.DepartmentDao;
import dao.SellerDao;
import entities.Department;

import java.sql.SQLException;
import java.util.List;

public class TestDepart {

    public static void main(String[] args) throws SQLException {

        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        /**
         * System.out.println("-----Test 1: findById-----");
         * Department dep = departmentDao.findById(3);
         * System.out.println(dep);
         */

        /**
         *  System.out.println("-----Test 2: findAll-----");
         *  List<Department> dep = departmentDao.findAll();
         *  for(Department d : dep) {
         *      System.out.println(d);
         *  }
         */

        /**
         * System.out.println("-----Test 3: insert-----");
         * Department dep = new Department(5, "Nutrition");
         * departmentDao.insert(dep);
         * System.out.println("New id: " + dep.getId() + ", Department of " + dep.getName());
         */


          System.out.println("-----Test 4: update-----");
          Department dep = departmentDao.findById(5);
          dep.setName("Health Care");
          departmentDao.update(dep);
          System.out.println("Department updated, new department name: " + dep.getName());



    }
}
