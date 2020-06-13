package database;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class DB {
  private String password;
  private String username;

  public DB() {
    // Default Constructor
  }

  public DB(String username, String password) {
    this.username = username;
    this.password = password;
    // Detect if files already exist in source path of .jar
    String temp;
    try {
      temp = getProgramPath();
      File tmpDir = new File(temp + "/dbData");
      boolean exists = tmpDir.exists();
      if (!exists) {
        String d = temp + "/dbData/";
        System.out.println("Making directory at " + d);
        File dir = new File(d); // The name of the directory to create
        dir.mkdir(); // Creates the directory
      }
      // Check to see if the database exists.
      if (checkDBExists()) {
        // System.out.println("Database exists");
        Connection connection = connectDB(this.username, this.password);
        connection.close();
      } else { // Create files
        System.out.println("Running first time setup");
        runSetup();
      }
    } catch (IOException | SQLException e) {
      e.printStackTrace();
    }
  }

  // Turns on built in users and creates the necessary admin user.
  @SuppressWarnings({"SqlResolve", "RedundantSuppression"})
  private static void turnOnBuiltInUsers(Connection connection, String username, String pass)
      throws SQLException {
    // System.out.println("Turning on authentication.");
    Statement s = connection.createStatement();
    // Setting and Confirming requireAuthentication
    s.executeUpdate(
        "CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.connection.requireAuthentication', 'true')");
    ResultSet rs =
        s.executeQuery(
            "VALUES SYSCS_UTIL.SYSCS_GET_DATABASE_PROPERTY('derby.connection.requireAuthentication')");
    rs.next();
    // System.out.println("Value of requireAuthentication is " + rs.getString(1));
    // Setting authentication scheme to Derby
    s.executeUpdate(
        "CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.authentication.provider', 'BUILTIN')");

    // Creating users
    // s.executeUpdate("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY(" + "'derby.user." + username +
    // "', '" + pass + "')");
    String un = "'" + username + "'";
    String pw = "'" + pass + "'";
    s.executeUpdate(
        String.format(
            "CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.user.%s',%s)", username, pw));
    s.executeUpdate("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.user.guest', 'test')");

    // System.out.println("Successfully created user");

    // Setting default connection mode to no access
    // (user authorization)
    s.executeUpdate(
        "CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.defaultConnectionMode', 'noAccess')");
    // Confirming default connection mode
    rs =
        s.executeQuery(
            "VALUES SYSCS_UTIL.SYSCS_GET_DATABASE_PROPERTY('derby.database.defaultConnectionMode')");
    rs.next();

    // System.out.println("Value of defaultConnectionMode is " + rs.getString(1));

    // Defining read-only users
    s.executeUpdate(
        "CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.readOnlyAccessUsers', 'guest')");
    // Defining full access users
    s.executeUpdate(
        String.format(
            "CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.fullAccessUsers',%s)",
            un));

    // Confirming full-access users
    rs =
        s.executeQuery(
            "VALUES SYSCS_UTIL.SYSCS_GET_DATABASE_PROPERTY('derby.database.fullAccessUsers')");
    rs.next();

    // System.out.println("Value of fullAccessUsers is " + rs.getString(1));

    // Confirming read-only users
    rs =
        s.executeQuery(
            "VALUES SYSCS_UTIL.SYSCS_GET_DATABASE_PROPERTY('derby.database.readOnlyAccessUsers')");
    rs.next();

    // System.out.println("Value of readOnlyAccessUsers is " + rs.getString(1));

    // We would set the following property to TRUE only
    // when we were ready to deploy.
    s.executeUpdate(
        "CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY("
            + "'derby.database.propertiesOnly', 'false')");
    s.close();
  }

  private boolean checkDBExists() {
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver"); // Register JDBC Driver
      // System.out.println("Creating a connection...");
      Connection conn =
          DriverManager.getConnection(
              "jdbc:derby:Mdb;create=false", "admin", "password"); // Open a connection
      ResultSet resultSet = conn.getMetaData().getCatalogs();
      while (resultSet.next()) {
        String databaseName = resultSet.getString(1);
        // System.out.println(databaseName);
        if (databaseName.equals("Mdb") || databaseName.equals("Mdb")) {
          return true;
        }
      }
      resultSet.close();
    } catch (Exception e) {
      System.out.println("SQLException caught in checkDBExists()");
      return false;
    }
    // System.out.println("End of checkDBExists()");
    return true;
  }
  // Performs black magic to get a file path on the user's computer to store the csv files
  public String getProgramPath() throws SQLException, IOException {
    URL url = DB.class.getProtectionDomain().getCodeSource().getLocation();
    String jarPath = URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8);
    return new File(jarPath).getParentFile().getPath().replace(File.separator, "/");
  }

  public Connection connectDB(String user, String pass) {
    // System.out.println("-------Embedded Apache Derby Connection Testing --------");
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    } catch (ClassNotFoundException e) {
      System.out.println("Apache Derby Driver not found. Add the classpath to your module.");
      System.out.println("For IntelliJ do the following:");
      System.out.println("File | Project Structure, Modules, Dependency tab");
      System.out.println("Add by clicking on the green plus icon on the right of the window");
      System.out.println(
          "Select JARs or directories. Go to the folder where the database JAR is located");
      System.out.println("Click OK, now you can compile your program and run it.");
      e.printStackTrace();
      return null;
    }
    // System.out.println("Apache Derby driver registered!");
    Connection connection = null;
    try {
      // substitute your database name for myDB
      connection =
          DriverManager.getConnection(
              String.format("jdbc:derby:Mdb;create=true;user=%s;password=%s", user, pass));
      // Turn on built in users to ensure proper connection.
      turnOnBuiltInUsers(connection, this.username, this.password);
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return null;
    }
    // System.out.println("Apache Derby connection established!");
    return connection;
  }

  //First time setup fcn
  private void runSetup() throws SQLException {
    try {
      Connection connection = connectDB(this.username, this.password);
      turnOnBuiltInUsers(connection, this.username, this.password);
      Statement stmt = connection.createStatement();
      // Create tables
      // Create tables
      String query =
          "CREATE TABLE data(player VARCHAR(255) NOT NULL, score INT, unlockedKeys INT, unlockedSounds INT, selectedSound INT, PRIMARY KEY (player))";
      stmt.execute(query);
      // Add default user to Employee table
      String sql = "INSERT INTO DATA(PLAYER, SCORE, UNLOCKEDKEYS, UNLOCKEDSOUNDS) VALUES (?, ?, ?, ?)";
      PreparedStatement ps = connection.prepareStatement(sql);
      ps.setString(1,"defaultUser");
      ps.setInt(2,0);
      ps.setInt(3,0);
      ps.setInt(4,0);
      ps.executeUpdate();
      ps.close();
      stmt.close();
    } catch (SQLException e) {
      System.out.println("Error in runSetup().");
      e.printStackTrace();
    }
  }
}
