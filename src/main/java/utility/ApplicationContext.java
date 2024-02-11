package utility;

import connection.DBConfig;
import repository.admin.AdminRepository;
import repository.admin.AdminRepositoryImpel;
import repository.category.CategoryRepository;
import repository.category.CategoryRepositoryImpel;
import repository.subCategory.SubCategoryRepository;
import repository.subCategory.SubCategoryRepositoryImpel;
import repository.product.ProductRepository;
import repository.product.ProductRepositoryImpel;
import repository.factor.FactorRepository;
import repository.factor.FactorRepositoryImpel;
import repository.user.UserRepository;
import repository.user.UserRepositoryImpel;
import service.Factor.FactorService;
import service.Factor.FactorServiceImpel;
import service.admin.AdminService;
import service.admin.AdminServiceImpel;
import service.category.CategoryService;
import service.subCategory.SubCategoryService;
import service.category.CategoryServiceImpel;
import service.product.ProductService;
import service.product.ProductServiceImpel;
import service.subCategory.SubCategoryServiceImpel;
import service.user.UserService;
import service.user.UserServiceImpel;
import java.sql.Connection;

public class ApplicationContext {

    public static final Connection CONNECTION;
    public static final AdminRepository ADMIN_REPOSITORY;
    public static final UserRepository USER_REPOSITORY;
    public static final CategoryRepository CATEGORY_REPOSITORY;
    public static final SubCategoryRepository SUB_CATEGORY_REPOSITORY;
    public static final ProductRepository PRODUCT_REPOSITORY;
    public static final FactorRepository FACTOR_REPOSITORY;


    public static final AdminService ADMIN_SERVICE;
    public static final UserService USER_SERVICE;
    public static final CategoryService CATEGORY_SERVICE;
    public static final SubCategoryService SUB_CATEGORY_SERVICE;
    public static final ProductService PRODUCT_SERVICE;
    public static final FactorService FACTOR_SERVICE;


    static {
        CONNECTION = DBConfig.getConnection();

        ADMIN_REPOSITORY = new AdminRepositoryImpel(CONNECTION);
        USER_REPOSITORY = new UserRepositoryImpel(CONNECTION);
        CATEGORY_REPOSITORY = new CategoryRepositoryImpel(CONNECTION);
        SUB_CATEGORY_REPOSITORY = new SubCategoryRepositoryImpel(CONNECTION);
        PRODUCT_REPOSITORY = new ProductRepositoryImpel(CONNECTION);
        FACTOR_REPOSITORY = new FactorRepositoryImpel(CONNECTION);

        ADMIN_SERVICE = new AdminServiceImpel(ADMIN_REPOSITORY);
        USER_SERVICE = new UserServiceImpel(USER_REPOSITORY);
        CATEGORY_SERVICE = new CategoryServiceImpel(CATEGORY_REPOSITORY);
        SUB_CATEGORY_SERVICE = new SubCategoryServiceImpel(SUB_CATEGORY_REPOSITORY);
        PRODUCT_SERVICE = new ProductServiceImpel(PRODUCT_REPOSITORY);
        FACTOR_SERVICE = new FactorServiceImpel(FACTOR_REPOSITORY);
    }

    public static AdminService getAdminServiceImpel(){return ADMIN_SERVICE;}
    public static UserService getUserServiceImpel(){return USER_SERVICE;}
    public static CategoryService getCategoryServiceImpel(){return CATEGORY_SERVICE;}
    public static SubCategoryService getSubCategoryServiceImpel(){return SUB_CATEGORY_SERVICE;}
    public static ProductService getProductServiceImpel(){return PRODUCT_SERVICE;}
    public static FactorService getFactorServiceImpel(){return FACTOR_SERVICE;}

}
