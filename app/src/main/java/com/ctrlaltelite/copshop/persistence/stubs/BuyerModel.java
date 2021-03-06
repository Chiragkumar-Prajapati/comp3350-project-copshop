package com.ctrlaltelite.copshop.persistence.stubs;

import com.ctrlaltelite.copshop.persistence.IBuyerModel;
import com.ctrlaltelite.copshop.objects.BuyerAccountObject;
import com.ctrlaltelite.copshop.persistence.database.IDatabase;

import java.util.Hashtable;
import java.util.List;

public class BuyerModel implements IBuyerModel {
    private static String TABLE_NAME = "Buyers";
    private IDatabase database;

    public BuyerModel(IDatabase database) {
        this.database = database;

        // Initialize mock data
    }

    @Override
    public String createNew(BuyerAccountObject newAccount) {
        Hashtable<String, String> newRow = new Hashtable<>();

        newRow.put("firstName", newAccount.getFirstName());
        newRow.put("lastName", newAccount.getLastName());
        newRow.put("streetAddress", newAccount.getAddress().getStreetAddress());
        newRow.put("postalCode", newAccount.getAddress().getPostalCode());
        newRow.put("province", newAccount.getAddress().getProvince());
        newRow.put("email", newAccount.getEmail());
        newRow.put("password", newAccount.getPassword());


        return this.database.insertRow(TABLE_NAME, newRow);
    }

    @Override
    public boolean update(String id, BuyerAccountObject updatedAccount) {
        boolean success;

        success = (null != this.database.updateColumn(TABLE_NAME, id, "firstName", updatedAccount.getFirstName()));
        success = success && (null != this.database.updateColumn(TABLE_NAME, id, "lastName", updatedAccount.getLastName()));
        success = success && (null != this.database.updateColumn(TABLE_NAME, id, "streetAddress", updatedAccount.getAddress().getStreetAddress()));
        success = success && (null != this.database.updateColumn(TABLE_NAME, id, "postalCode", updatedAccount.getAddress().getPostalCode()));
        success = success && (null != this.database.updateColumn(TABLE_NAME, id, "province", updatedAccount.getAddress().getProvince()));
        success = success && (null != this.database.updateColumn(TABLE_NAME, id, "email", updatedAccount.getEmail()));
        success = success && (null != this.database.updateColumn(TABLE_NAME, id, "password", updatedAccount.getPassword()));

        return success;
    }

    @Override
    public BuyerAccountObject fetch(String id) {
        BuyerAccountObject account = new BuyerAccountObject(
                id,
                this.database.fetchColumn(TABLE_NAME, id, "firstName"),
                this.database.fetchColumn(TABLE_NAME, id, "lastName"),
                this.database.fetchColumn(TABLE_NAME, id, "streetAddress"),
                this.database.fetchColumn(TABLE_NAME, id, "postalCode"),
                this.database.fetchColumn(TABLE_NAME, id, "province"),
                this.database.fetchColumn(TABLE_NAME, id, "email"),
                this.database.fetchColumn(TABLE_NAME, id, "password")
        );

        return account;
    }

    @Override
    public BuyerAccountObject findByEmail(String email) {
        List<String> users = this.database.findByColumnValue(TABLE_NAME, "email", email);

        // We should only get back one user max, ignore extras
        if (!users.isEmpty()) {
            String id = users.get(0);
            return this.fetch(id);
        }

        return null;
    }

    @Override
    public boolean checkEmailPasswordMatch(String email, String inputPassword) {
        List<String> users = this.database.findByColumnValue(TABLE_NAME, "email", email);

        // We should only get back one user max, ignore extras
        if (!users.isEmpty()) {
            String id = users.get(0);
            String password = this.database.fetchColumn(TABLE_NAME, id, "password");
            return password.equals(inputPassword);
        }

        return false;
    }
}