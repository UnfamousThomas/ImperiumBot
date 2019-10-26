package guild.imperium.utils.mysql;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import guild.imperium.utils.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("Duplicates")
public class MySQLManager {
    private static HikariDataSource dataSource;


        public static void init(String ip, String db, String user, String password){
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl("jdbc:mysql://" + ip + ":3306/" + db);
            hikariConfig.setUsername(user);
            hikariConfig.setPassword(password);
            hikariConfig.setMaxLifetime(300 * 1000);
            hikariConfig.setIdleTimeout(120 * 1000);
            hikariConfig.setConnectionTimeout(10 * 1000);
            hikariConfig.setMinimumIdle(5);
            hikariConfig.setMaximumPoolSize(10);
            hikariConfig.setLeakDetectionThreshold(2000);
            try {
                dataSource = new HikariDataSource(hikariConfig);
            } catch (Exception e) {
                Logger.log(Logger.Level.ERROR, "Could not connect to SQL with error: ", e);
            }
        }

    public static Connection getConnection() throws SQLException{
        return dataSource.getConnection();
    }

    public static void createTable(String name, String info) {
        new Thread(() -> {
            try (Connection resource = getConnection(); PreparedStatement statement = resource.prepareStatement("CREATE TABLE IF NOT EXISTS " + name + "(" + info + ");")) {
                statement.execute();
            } catch (SQLException exception) {
                Logger.log(Logger.Level.ERROR, "An error occurred while creating database table " + name + ".", exception);

            }
        }).start();
    }

    public static ResultSet execute(String query, Object... values) {
        new Thread(() -> {
            try (Connection resource = getConnection(); PreparedStatement statement = resource.prepareStatement(query)) {
                for (int i = 0; i < values.length; i++) {
                    statement.setObject((i + 1), values[i]);
                }
                statement.execute();
            } catch (SQLException exception) {
                Logger.log(Logger.Level.ERROR, "An error occurred while executing an update on the database.");
                Logger.log(Logger.Level.ERROR, "MySQL#execute : " + query, exception);

            }
        }).start();
        return null;
    }

    public static void select(String query, SelectCall callback, Object... values) {
        new Thread(() -> {
            try (Connection resource = getConnection(); PreparedStatement statement = resource.prepareStatement(query)) {
                for (int i = 0; i < values.length; i++) {
                    statement.setObject((i + 1), values[i]);
                }
                callback.call(statement.executeQuery());
            } catch (SQLException exception) {
                Logger.log(Logger.Level.ERROR, "An error occurred while executing a query on the database.");
                Logger.log(Logger.Level.ERROR, "MySQL#select : " + query, exception);

            }
        }).start();
    }

    public static void selectSync(String query, SelectCall callback, Object... values){
        try (Connection resource = getConnection(); PreparedStatement statement = resource.prepareStatement(query)) {
            for (int i = 0; i < values.length; i++) {
                statement.setObject((i + 1), values[i]);
            }
            callback.call(statement.executeQuery());
        } catch (SQLException exception) {
            Logger.log(Logger.Level.ERROR, "An error occurred while executing a query on the database (synchronized).");
            Logger.log(Logger.Level.ERROR, "MySQL#select : " + query, exception);
        }
    }

    public static void executeSync(String query, Object... values){
        try (Connection resource = getConnection(); PreparedStatement statement = resource.prepareStatement(query)) {
            for (int i = 0; i < values.length; i++) {
                statement.setObject((i + 1), values[i]);
            }
            statement.execute();
        } catch (SQLException exception) {
            Logger.log(Logger.Level.ERROR, "An error occurred while executing an update on the database (synchronized).");
            Logger.log(Logger.Level.ERROR, "MySQL#execute : " + query, exception);

        }
    }
}