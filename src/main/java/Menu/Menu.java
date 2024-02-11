package Menu;

import model.*;
import service.Factor.FactorService;
import service.admin.AdminService;
import service.category.CategoryService;
import service.product.ProductService;
import service.subCategory.SubCategoryService;
import service.user.UserService;
import utility.ApplicationContext;
import utility.Validation;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Menu {

    private final Scanner sc = new Scanner(System.in);

    UserService userService = ApplicationContext.getUserServiceImpel();
    AdminService adminService = ApplicationContext.getAdminServiceImpel();
    CategoryService categoryService = ApplicationContext.getCategoryServiceImpel();
    SubCategoryService subCategoryService = ApplicationContext.getSubCategoryServiceImpel();
    ProductService productService = ApplicationContext.getProductServiceImpel();
    FactorService factorService = ApplicationContext.getFactorServiceImpel();

    public int getNumberFromUser() {
        int num = 0;
        try {
            num = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        } finally {
            sc.nextLine();
        }
        return num;
    }

    public float getFloatFromUser() {
        float num = 0;
        try {
            num = sc.nextFloat();
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        } finally {
            sc.nextLine();
        }
        return num;
    }

    public String getStringFromUser() {
        String input = null;
        try {
            input = sc.nextLine();
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
        return input;
    }

    private String getPasswordFromUser() {
        String password;
        while (true) {
            System.out.println("Please enter your password:");
            System.out.println("Hint: it has to be between 8 to 10 and must contain at least 1 lower and upper case and 1 digit and 1 char ");
            password = getStringFromUser();
            if (Validation.isValidPassword(password))
                break;
            else
                System.out.println("plz enter a valid password");
        }
        return password;
    }
    private String getEmailFromUser() {
        String email;
        while (true) {
            System.out.println("Please enter your email:");
            email = getStringFromUser();
            if (Validation.isValidEmail(email))
                break;
            else
                System.out.println("plz enter a valid email");
        }
        return email;
    }
    public void startMenu() {
        System.out.println("************************** WELCOME TO MAKTAB ONLINE SHOP ***************************");
        System.out.println("press 1 for sign up");
        System.out.println("press 2 for log in");
        System.out.println("press 3 if you are admin");
        int startMenu = getNumberFromUser();
        if (startMenu > 3 || startMenu < 1) {
            System.out.println("plz enter valid number");
            startMenu();
        }
        switch (startMenu) {
            case 1 -> saveUser();
            case 2 -> logInUser();
            case 3 -> adminLogIn();
        }
    }
    public User takeUserInfo() {
        System.out.println("plz enter your full name");
        String name = getStringFromUser();
        System.out.println("plz enter your username");
        String username = getStringFromUser();
        String email = getEmailFromUser();
        String password = getPasswordFromUser();
        return new User(name, username, email, password);
    }
    public void saveUser() {
        User user = takeUserInfo();
        int sve = 0;
        try {
            sve = userService.save(user);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (sve != 0) {
            System.out.println("welcome to the shop");
            System.out.println("now plz log in");
            logInUser();
        } else {
            System.out.println("something wrong plz try again");
            saveUser();
        }
    }
    public void logInUser() {
        System.out.println("plz enter your username");
        String username = getStringFromUser();
        System.out.println("plz enter your password");
        String password = getStringFromUser();
        User user = null;
        try {
            user = userService.logIn(username, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (user != null) {
            System.out.println("welcome dear " + user.getName());
            userBodyMenu(user);
        } else {
            System.out.println("wrong username or password");
            logInUser();
        }

    }
    public void adminLogIn() {
        System.out.println("plz enter your username");
        String username = getStringFromUser();
        System.out.println("plz enter your password");
        String password = getStringFromUser();

        Admin admin = null;
        try {
            admin = adminService.login(username, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (admin != null) {
            System.out.println("welcome admin " + admin.getName());
            adminBodyMenu();
        } else {
            System.out.println("wrong username or password");
            adminLogIn();
        }
    }
    public void userBodyMenu(User user) {

        boolean flag = true;
        int userId = user.getId();

        try {
            try {
                userService.saveUserInnerTable(userId);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            int idF = numOfFactor(userId);

            while (flag) {

                Product product = shop();
                if (product == null)
                    System.out.println("this product is out of number,sorry");
                else {
                    int idP = product.getId();
                    try {
                        factorService.saveFactorInnerTable(idF, idP);
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                }
                System.out.println("do you want to see yor factor?(y,n)");
                String seeFactor = getStringFromUser();
                if (seeFactor.equals("y")) {
                    showOneFactor(idF);
                    System.out.println("do you want to delete from your factor?(y,n)");
                    String deleteFromFactor = getStringFromUser();
                    if (deleteFromFactor.equals("y")) {
                        String nameOfDeletedProduct = deleteProductFromFactor();
                        Product productDelete = null;
                        try {
                            productDelete = productService.findByName(nameOfDeletedProduct);
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                        increaseNumOfProduct(productDelete);
                    }
                }
                System.out.println("do you want more products(y,n)");
                String choose = getStringFromUser();

                if (!choose.equals("y")) {
                    flag = false;
                }
            }
            showOneFactor(idF);
            System.out.println("plz pay right now or you will die!");
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
    }
    public void showOneFactor(int idF){
        int [] productsId = null;
        Product product = null;
        int finalPrice = 0;
        try {
            productsId = factorService.productsOfOneFactor(idF);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        System.out.println("your factor:");
        for (int i = 0; i < productsId.length; i++) {
            try {
                product = productService.findById(productsId[i]);
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
            finalPrice += product.getProductPrice();
            System.out.print("name: " + product.getProductName());
            System.out.print("    price: " + product.getProductPrice());
            System.out.println();
        }
        System.out.println("your final factor price is: " + finalPrice);
    }
    public Product shop() {

        showAllCategories();

        Category category = takeOneCategory();

        int id = category.getId();
        showOneCategorySubCategories(id);

        SubCategory subCategory = takeOneSubCategory();

        int idSub = subCategory.getId();
        showOneSubCategoryProduct(idSub);

        return takeOneProduct();
    }
    public int numOfFactor(int id){
        int [] nums = null;
        int numOfFactor = 0;
        try {
            nums = factorService.numOfFactor(id);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        assert nums != null;
        for (int i = 0; i < nums.length; i++) {
            int temp = nums[i];
            if(temp > numOfFactor)
                numOfFactor = temp;
        }
        return numOfFactor;
    }
    public Category takeOneCategory() {
        System.out.println("plz enter the name of category");
        String name = getStringFromUser();
        Category category = null;
        try {
            category = categoryService.findByName(name);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return category;
    }
    public SubCategory takeOneSubCategory() {
        System.out.println("plz enter the name of sub category");
        String name = getStringFromUser();
        SubCategory subCategory = null;
        try {
            subCategory = subCategoryService.findByName(name);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return subCategory;
    }
    public Product takeOneProduct() {
        System.out.println("plz enter the name of product");
        String name = getStringFromUser();
        Product product  = null;
        try {
            product = productService.findByName(name);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        assert product != null;
        int count = product.getProductNumber();
        if(count > 0) {
            decreaseNumOfProduct(product);
            return product;
        }
        else
            return null;
    }
    public void decreaseNumOfProduct(Product product){
        int newCount = product.getProductNumber() - 1;
        String name = product.getProductName();
        try {
            productService.editProductNumber(name,newCount);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void increaseNumOfProduct(Product product){
        int newCount = product.getProductNumber() + 1;
        String name = product.getProductName();
        try {
            productService.editProductNumber(name,newCount);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void showOneCategorySubCategories(int id) {
        SubCategory[] subCategories = null;
        try {
            subCategories = subCategoryService.showOneCategorySubCategories(id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            assert subCategories != null;
            for (SubCategory subCategory : subCategories) {
                System.out.println(subCategory.toString());
            }
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
    }
    public void showOneSubCategoryProduct(int id) {
        Product[] products = null;
        try {
            products = productService.showOneSubCategoryProducts(id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        assert products != null;
        for (Product product:products) {
            System.out.println(product.toString());
        }
    }
    public void adminBodyMenu() {
        System.out.println("press 1 to asses category menu");
        System.out.println("press 2 to asses sub category menu");
        System.out.println("press 3 to asses products menu");
        System.out.println("press 4 to exit");
        int chooseAdmin = getNumberFromUser();
        if (chooseAdmin > 4 || chooseAdmin < 1) {
            System.out.println("plz enter valid number");
            adminBodyMenu();
        }
        switch (chooseAdmin) {
            case 1 -> categoryMenu();
            case 2 -> subCategoryMenu();
            case 3 -> productsMenu();
            case 4 -> System.out.println("good by");
        }
    }
    public void categoryMenu() {
        System.out.println("press 1 to add one category");
        System.out.println("press 2 to edit one category");
        System.out.println("press 3 to delete one category");
        System.out.println("press 4 to back");
        int chooseCategory = getNumberFromUser();
        if (chooseCategory > 4 || chooseCategory < 1) {
            System.out.println("plz enter valid number");
            categoryMenu();
        }
        switch (chooseCategory) {
            case 1 -> saveOneCategory();
            case 2 -> editOneCategory();
            case 3 -> deleteCategoryCheck();
            case 4 -> adminBodyMenu();
        }
    }
    public void subCategoryMenu() {
        System.out.println("press 1 to add one sub category");
        System.out.println("press 2 to edit one sub category");
        System.out.println("press 3 to delete one sub category");
        System.out.println("press 4 to back");
        int chooseSubCategory = getNumberFromUser();
        if (chooseSubCategory > 4 || chooseSubCategory < 1) {
            System.out.println("plz enter valid number");
            subCategoryMenu();
        }
        switch (chooseSubCategory) {
            case 1 -> saveOneSubCategory();
            case 2 -> editSubCategory();
            case 3 -> deleteOneSubCategoryFromInnerTable();
            case 4 -> adminBodyMenu();
        }
    }
    public void productsMenu() {
        System.out.println("press 1 to add products");
        System.out.println("press 2 to edit one product");
        System.out.println("press 3 to delete one product");
        System.out.println("press 4 to back");
        int chooseProduct = getNumberFromUser();
        if (chooseProduct > 4 || chooseProduct < 1) {
            System.out.println("plz enter valid number");
            productsMenu();
        }
        switch (chooseProduct) {
            case 1 -> saveOneProduct();
            case 2 -> editProducts();
            case 3 -> deleteOneProduct();
            case 4 -> adminBodyMenu();
        }
    }
    public void saveOneCategory() {
        System.out.println("plz enter the category name");
        String name = getStringFromUser();
        Category category = new Category(name);
        int saveCategory = 0;
        try {
            saveCategory = categoryService.save(category);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (saveCategory != 0) {
            System.out.println("category saved");
            adminBodyMenu();
        } else {
            System.out.println("something wrong try again");
            saveOneCategory();
        }
    }
    public void editOneCategory() {

        showAllCategories();

        System.out.println("plz enter the name of category you want to edit");
        String oldName = getStringFromUser();
        System.out.println("plz enter the category new name");
        String newName = getStringFromUser();

        int editName = 0;
        try {
            editName = categoryService.editName(oldName, newName);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (editName != 0) {
            System.out.println("name changed");
            adminBodyMenu();
        } else {
            System.out.println("wrong name,plz try again");
            editOneCategory();
        }
    }
    public void showAllCategories() {
        Category[] categories = null;

        try {
            categories = categoryService.showAllCategories();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        assert categories != null;
        for (Category category : categories) {
            System.out.println(category.toString());
        }
    }
    public void deleteCategoryCheck() {
        try {
            System.out.println("plz check if there is any sub category in product table first");
            System.out.println("is there any sub category in product table?(y,n)");


            showAllProducts();
            String checkInput = getStringFromUser();

            if (checkInput.equals("y")) {
                deleteOneSubCategoryFromInnerTable();
                deleteCategoryCheck();
            } else {
                deleteOneCategoryFromInnerTable();
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            deleteCategoryCheck();
        }
    }
    public void deleteOneCategoryFromInnerTable() {

        showAllCategories();

        System.out.println("plz enter the name of category you want to delete");
        String name = getStringFromUser();

        Category category = null;
        try {
            category = categoryService.findByName(name);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        assert category != null;
        int id = category.getId();

        try {
            categoryService.deleteFromInnerTable(id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        deleteOneCategory(name);
    }
    public void deleteOneCategory(String name) {

        int deleteCategory = 0;
        try {
            deleteCategory = categoryService.delete(name);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (deleteCategory != 0) {
            System.out.println("category deleted");
            adminBodyMenu();
        } else {
            System.out.println("wrong name,plz try again");
            deleteOneCategoryFromInnerTable();
        }
    }
    public Category loudOneCategory() {

        showAllCategories();

        System.out.println("plz enter the category name");
        String categoryName = getStringFromUser();
        Category category = new Category();
        try {
            category = categoryService.findByName(categoryName);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return category;
    }
    public void saveOneSubCategory() {

        System.out.println("plz enter the sub category name");
        String name = getStringFromUser();
        Category category = loudOneCategory();

        SubCategory subCategory = new SubCategory(name, category);

        int saveSubCategory = 0;
        try {
            saveSubCategory = subCategoryService.save(subCategory);
        } catch (SQLException ew) {
            System.out.println(ew.getMessage());
        }
        if (saveSubCategory != 0) {
            System.out.println("sub category saved");
            adminBodyMenu();
        } else {
            System.out.println("something wrong try again");
            categoryMenu();
        }
    }
    public void editSubCategory() {
        System.out.println("press 1 to edit name");
        System.out.println("press 2 to edit category");
        System.out.println("press 3 to back");
        int chooseEditSubBranch = getNumberFromUser();
        if (chooseEditSubBranch > 3 || chooseEditSubBranch < 1) {
            System.out.println("plz enter valid number");
            editSubCategory();
        }
        switch (chooseEditSubBranch) {
            case 1 -> editOneSubCategoryName();
            case 2 -> editSubCategoryCategory();
            case 3 -> subCategoryMenu();
        }
    }
    public void editOneSubCategoryName() {

        showAllSubCategories();

        System.out.println("plz enter the name of sub category you want to edit name");
        String name = getStringFromUser();
        System.out.println("plz enter the sub category new name");
        String newName = getStringFromUser();

        int editSubCategoryName = 0;
        try {
            editSubCategoryName = subCategoryService.editName(name, newName);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (editSubCategoryName != 0) {
            System.out.println("name changed");
            adminBodyMenu();
        } else {
            System.out.println("something wrong try again");
            editOneSubCategoryName();
        }
    }
    public void editSubCategoryCategory() {

        showAllSubCategories();

        try {
            System.out.println("plz enter the name of sub category you want to edit category");
            String name = getStringFromUser();

            System.out.println("all categories");
            showAllCategories();
            System.out.println("plz enter the sub category new category name");
            String newName = getStringFromUser();

            Category category = null;
            try {
                category = categoryService.findByName(newName);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            assert category != null;
            int newFk = category.getId();
            int editSubCategoryCategory = 0;
            try {
                editSubCategoryCategory = subCategoryService.editCategoryFk(newFk, name);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            if (editSubCategoryCategory != 0) {
                System.out.println("category changed");
                adminBodyMenu();
            } else {
                System.out.println("something wrong try again");
                editSubCategoryCategory();
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            editSubCategoryCategory();
        }
    }
    public void deleteOneSubCategoryFromInnerTable() {

        showAllSubCategories();

        try {
            System.out.println("plz enter the name of sub category you want to delete");
            String name = getStringFromUser();

            int id = 0;
            try {
                id = subCategoryService.findByName(name).getId();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            try {
                subCategoryService.deleteFromInnerTable(id);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            deleteOneSubCategory(name);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            deleteOneSubCategoryFromInnerTable();
        }
    }
    public void deleteOneSubCategoryFromInnerTableCheck() {

        showAllSubCategories();

        System.out.println("plz enter the name of sub category you want to delete");
        String name = getStringFromUser();

        int id = 0;
        try {
            id = subCategoryService.findByName(name).getId();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        int check = 0;
        try {
            check = subCategoryService.deleteFromInnerTable(id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deleteOneSubCategory(String name) {


        int deleteOneSubCategory = 0;
        try {
            deleteOneSubCategory = subCategoryService.delete(name);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (deleteOneSubCategory != 0) {
            System.out.println("branch deleted");
            adminBodyMenu();
        } else {
            System.out.println("something wrong try again");
            deleteOneSubCategoryFromInnerTable();
        }
    }
    public void showAllSubCategories() {
        SubCategory[] subCategories = null;

        try {
            subCategories = subCategoryService.showAllSubCategory();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            assert subCategories != null;
            for (SubCategory subCategory : subCategories) {
                System.out.println(subCategory.toString());
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
    public void saveOneProduct() {
        System.out.println("plz enter the product name");
        String productName = getStringFromUser();
        System.out.println("plz enter the price of the product");
        double price = getFloatFromUser();
        System.out.println("plz enter the number of the product");
        int number = getNumberFromUser();
       showAllSubCategories();
        System.out.println("plz enter the name of sub category");
        String subCategoryName = getStringFromUser();
        SubCategory subCategory = null;
        try {
            subCategory = subCategoryService.findByName(subCategoryName);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Product product = new Product(productName, price, number, subCategory );

        int saveOneProduct = 0;
        try {
            saveOneProduct = productService.save(product);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (saveOneProduct != 0) {
            System.out.println("product saved");
            adminBodyMenu();
        } else {
            System.out.println("something wrong try again");
            saveOneProduct();
        }
    }
    public void editProducts() {
        System.out.println("press 1 to edit product name");
        System.out.println("press 2 to edit product price");
        System.out.println("press 3 to edit product number");
        System.out.println("press 4 to edit product sub branch");
        System.out.println("press 5 to back");
        int chooseEditProduct = getNumberFromUser();
        if (chooseEditProduct > 5 || chooseEditProduct < 1) {
            System.out.println("plz enter valid number");
            editProducts();
        }
        switch (chooseEditProduct) {
            case 1 -> editOneProductName();
            case 2 -> editProductPrice();
            case 3 -> editProductNumber();
            case 4 -> editProductSubCategory();
            case 5 -> productsMenu();
        }
    }
    public void showAllProducts() {
        Product[] products = null;

        try {
            products = productService.showAllProducts();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        for (int i = 0; i < Objects.requireNonNull(products).length; i++) {
            System.out.println(products[i].toString());
        }
    }
    public void editOneProductName() {

        showAllProducts();

        System.out.println("plz enter the name of product you want to edit name");
        String name = getStringFromUser();
        System.out.println("plz enter the product new name");
        String newName = getStringFromUser();

        int editProductName = 0;
        try {
            editProductName = productService.editName(name, newName);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (editProductName != 0) {
            System.out.println("name changed");
            adminBodyMenu();
        } else {
            System.out.println("something wrong try again");
            editOneProductName();
        }
    }
    public void editProductPrice() {

        showAllProducts();

        System.out.println("plz enter the name of product you want to edit price");
        String name = getStringFromUser();
        System.out.println("plz enter the product new price");
        float newPrice = getFloatFromUser();

        int editProductPrice = 0;
        try {
            editProductPrice = productService.editProductPrice(name, newPrice);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (editProductPrice != 0) {
            System.out.println("price changed");
            adminBodyMenu();
        } else {
            System.out.println("something wrong try again");
            editProductPrice();
        }
    }
    public void editProductNumber() {

        showAllProducts();

        System.out.println("plz enter the name of product you want to edit number");
        String name = getStringFromUser();
        System.out.println("plz enter the product new number");
        int newNumber = getNumberFromUser();

        int editProductNumber = 0;
        try {
            editProductNumber = productService.editProductNumber(name, newNumber);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (editProductNumber != 0) {
            System.out.println("number changed");
            adminBodyMenu();
        } else {
            System.out.println("something wrong try again");
            editProductNumber();
        }
    }
    public void editProductSubCategory() {

        showAllProducts();

        try {
            System.out.println("plz enter the name of product you want to edit sub category");
            String name = getStringFromUser();

            System.out.println("all sub categories");
            showAllSubCategories();
            System.out.println("plz enter the product new sub category name");
            String newName = getStringFromUser();

            SubCategory subCategory = null;
            try {
                subCategory = subCategoryService.findByName(newName);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            assert subCategory != null;
            int newFk = subCategory.getId();
            int editProductSubBranch = 0;
            try {
                editProductSubBranch = productService.editProductSubCategory(newFk, name);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            if (editProductSubBranch != 0) {
                System.out.println("sub branch changed");
                adminBodyMenu();
            } else {
                System.out.println("something wrong try again");
                editProductSubCategory();
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            editProductSubCategory();
        }
    }
    public String deleteProductFromFactor(){
        System.out.println("plz enter the name of product you want to delete");
        String name = getStringFromUser();

        Product product = null;
        try{
            product = productService.findByName(name);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        assert product != null;
        int productId = product.getId();
        int deleteFactorProduct = 0;
        try{
            deleteFactorProduct = productService.deleteFromInnerTable(productId);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return name;
    }
    public void deleteOneProduct() {

        showAllProducts();

        String name = deleteProductFromFactor();

        int deleteProduct = 0;
        try {
            deleteProduct = productService.delete(name);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (deleteProduct != 0) {
            System.out.println("product deleted");
            adminBodyMenu();
        } else {
            System.out.println("something wrong try again");
            deleteOneProduct();
        }
    }
}

