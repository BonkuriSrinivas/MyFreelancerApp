package com.nextclick.grocery.modal;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LoginDao {

    // LoginDatils
    @Insert
    void insertUsers(Login login);

    @Insert
    void insertAll(Login[] login);

    @Query("select count(*) from tbl_Login")
    int logincount();

    @Query("select id,UserId,Password from tbl_Login")
    List<Login> getLoginDatils();

    @Insert
    void insertProducts(Productdetails productDetails);

    @Insert
    void insertAllProducts(Productdetails[] productDetails);

    @Query("select count(*) from tbl_ProductCategories")
    int productscount();

    @Query("SELECT id,Category,Name,Discription,Price,Quantity,Discount,Cartvalue FROM tbl_ProductCategories ")
    List<Productdetails> getAllProducts();

    @Query("SELECT id,Category,Name,Discription,Price,Quantity,Discount,Cartvalue FROM tbl_ProductCategories where Category ='Groceries' ")
    List<Productdetails> getGroceryProducts();

    @Query("SELECT id,Category,Name,Discription,Price,Quantity,Discount,Cartvalue FROM tbl_ProductCategories where Category ='Fruits' ")
    List<Productdetails> getFruitsProducts();

    @Query("SELECT id,Category,Name,Discription,Price,Quantity,Discount,Cartvalue FROM tbl_ProductCategories where Category ='Clothes' ")
    List<Productdetails> getClothesProducts();

    @Query("SELECT id,Category,Name,Discription,Price,Quantity,Discount,Cartvalue FROM tbl_ProductCategories where Category ='Shoes' ")
    List<Productdetails> getShoeProducts();

    @Query("SELECT id,Category,Name,Discription,Price,Quantity,Discount,Cartvalue FROM tbl_ProductCategories where Category ='Electronics' ")
    List<Productdetails> getElectronicsProducts();

    @Query("SELECT id,Category,Name,Discription,Price,Quantity,Discount,Cartvalue FROM tbl_ProductCategories where Category ='Others' ")
    List<Productdetails> getOthersProducts();

    @Query("UPDATE tbl_ProductCategories  SET Cartvalue = :cartvalue WHERE Name =:name")
    void updateCart(int cartvalue, String name);

    @Query("SELECT id,Category,Name,Discription,Price,Quantity,Discount,Cartvalue FROM tbl_ProductCategories where Cartvalue = 1 ")
    List<Productdetails> getCartProducts();
}
