import dao.DaoFactory;
import dao.DepartmentDao;
import dao.SellerDao;
import entities.Department;
import entities.Seller;

import java.util.Date;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        SellerDao sellerDao = DaoFactory.createSellerDao();
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        /**
         * System.out.println("--------FindById test--------");
         *         Seller seller = sellerDao.findById(1);
         *         System.out.println(seller);
         */

        /**
         * System.out.println("\n--------FindByDepartment test--------");
         *          Department department = new Department(1, null);
         *          List<Seller> sellers = sellerDao.findByDepartment(department);
         *          for(Seller s : sellers) {
         *              System.out.println(s);
         *          }
         */

        /**
         * System.out.println("--------FindAll test--------");
         *          List<Seller> sellers = sellerDao.findAll();
         *          for(Seller s : sellers) {
         *             System.out.println(s);
         *          }
         */

        /**
         *  System.out.println("--------Insert test--------");
         *          Seller newSeller = new Seller(12, "Willinam Blanc", "will.blanc@example.com", new Date(), 4000, new Department(3,null));
         *          sellerDao.insert(newSeller);
         *          System.out.println("Inserted ID:" + newSeller.getId());
         */

         System.out.println("--------Update test--------");
         Seller seller = sellerDao.findById(12);
         seller.setId(8);
         seller.setSalary(3820);
         seller.setDepartment(new Department(1,"Physics"));
         sellerDao.update(seller);
         System.out.println("Seller updated. Seller:" + seller.getName() + " of new id:" + seller.getId() + ", salary raised to $" + seller.getSalary());

    }
}