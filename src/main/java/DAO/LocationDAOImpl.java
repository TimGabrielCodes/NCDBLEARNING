package DAO;

import Model.Location;
import Util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LocationDAOImpl implements LocationDAO{
    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStmt;

    @Override
    public List<Location> getAllLocation() throws SQLException, ClassNotFoundException {

        List<Location> locationList = new ArrayList<>();

        String sql = "select * from Location ";

        connection = DBUtil.openConnection();


        Statement statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            Location location = new Location();
            location.setId(resultSet.getInt("id"));
            location.setName(resultSet.getString("name"));
            location.setLabel(resultSet.getString("label"));
            location.setAddress(resultSet.getString("address"));
            locationList.add(location);
        }
        DBUtil.closeConnection();
        return locationList;
    }

    @Override
    public boolean saveLocation(Location location) {
        boolean flag;
        try {

            String sql = "insert into Location(name, label, address) "
                    + "values(?,?,?)";
            try {
                connection = DBUtil.openConnection();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(StaffDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setString(1,location.getName());
            preparedStmt.setString(2, location.getLabel());
            preparedStmt.setString(3, location.getAddress());

            preparedStmt.executeUpdate();
            flag = true;

        } catch (SQLException e) {
            e.printStackTrace();
            flag = false;

        }
        return flag;
    }

    @Override
    public boolean deleteLocation(int id) {
        boolean flag = false;
        try {
            String sql = "DELETE FROM Location WHERE id=" + id;
            connection = DBUtil.openConnection();
            preparedStmt = connection.prepareStatement(sql);
            preparedStmt.executeUpdate();
            flag = true;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StaffDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        DBUtil.closeConnection();
        return flag;
    }

    @Override
    public Location getLocation(int id) throws SQLException, ClassNotFoundException {
        String sql = "select * from Location where id =" + id;
    Location location = null;
        connection = DBUtil.openConnection();


        Statement statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            location = new Location();
            location.setId(resultSet.getInt("id"));
            location.setName(resultSet.getString("name"));
            location.setLabel(resultSet.getString("label"));
            location.setAddress(resultSet.getString("address"));

        }
        DBUtil.closeConnection();
        return location;
    }

    @Override
    public boolean updateLocation(Location location) {
        boolean flag = false;

        try {
            String sql = "update Location set name=?, label=?,address=? where id= ?";
            connection = DBUtil.openConnection();
            preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setString(1,location.getName());
            preparedStmt.setString(2, location.getLabel());
            preparedStmt.setString(3, location.getAddress());
            preparedStmt.setInt(4, location.getId());
            preparedStmt.executeUpdate();
            flag = true;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StaffDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }
}
