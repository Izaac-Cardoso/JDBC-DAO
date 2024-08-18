package dao;

import daoImplementation.DepartmentDaoJDBC;
import daoImplementation.SellerDaoJDBC;

public class DaoFactory {

    public static DepartmentDao createDepartmentDao() {
        return new DepartmentDaoJDBC();
    }

    public static SellerDao createSellerDao() {
        return new SellerDaoJDBC();
    }
}
