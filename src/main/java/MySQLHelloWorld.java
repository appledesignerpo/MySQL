import java.sql.*;
//TODO Doplnit novy slpec napr.rokVydania a upravit kod
public class MySQLHelloWorld {



    // Metoda odstran (DELETE)
    public static void odstran(Connection conn) throws SQLException {
        // the mysql insert statement
        String query = "DELETE FROM kniha WHERE id = ?"; // odstranujme z knihy zaznam riadko kde je id 1
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, 1); // id 1 rep. 2,3,4,5 ....
        // execute the preparedstatement
        preparedStmt.execute();
        preparedStmt.close(); // uzatvorenie
    }

    // Metoda loz reprezentuje (C)reate
    public static void vloz(Connection conn) throws SQLException {
// the mysql insert statement
        String query = "INSERT INTO kniha (nazov, autor, rokvydania, isbn) VALUES (?, ?, ?, ?  )";
        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString (1, "Do stredu zeme 3");
        preparedStmt.setString (2, "Anton Kostrab 3");
        preparedStmt.setInt (3, 2023);
        preparedStmt.setDouble (4, 789654326);

        // execute the preparedstatement
        preparedStmt.execute();
        preparedStmt.close(); // uzatvorenie
    }



// Metoda vyber Reprezentuje (R)ead v CRUD operaciach
    public static void vyber(Connection conn) throws SQLException {
        // our SQL SELECT query.
        // if you only need a few columns, specify them by name instead of using "*"
        String query = "SELECT * FROM kniha";
        // create the java statement
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        // execute the query, and get a java resultset
        ResultSet rs = preparedStmt.executeQuery(query);
        // iterate through the java resultset
        while (rs.next())
        {
            int id = rs.getInt("id");
            String nazov = rs.getString("nazov");
            String autor = rs.getString("autor");
            int rokVydania = rs.getInt("rokVydania");
            double isbn = rs.getDouble("isbn");

            // print the results
            System.out.println(id + " " + nazov + " " + autor + " " + rokVydania+ " " + isbn);
        }
        preparedStmt.close();

    }


//Metoda aktializuj reprezentuje (U)pdate
    public static void aktualizuj(Connection conn) throws SQLException {
        // create the java mysql update preparedstatement
        String query = "UPDATE kniha SET nazov = ?, autor = ? ,  rokVydania = ?,  isbn = ? WHERE id = ?";
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString(1, "Já, NE robot");
        preparedStmt.setString(2, "Anton Kostrab");
        preparedStmt.setInt(3, 2000);
        preparedStmt.setInt(4, 1);
        preparedStmt.setDouble(5, 486238753);
        // execute the java preparedstatement
        preparedStmt.executeUpdate();
        preparedStmt.close(); // uzatvorenie
    }

    public static void main(String[] args) {
        Connection conn;
        String url = "jdbc:mysql://localhost:3306/test_db";
        String username = "root";
        String password = "rootroot";
        System.out.println("Connecting database...");
        try {
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        System.out.println("SELECT all data FROM book");
        System.out.println("Vyber (SELECT resp. READ)  všetkych knih so vsetkymi stplcami - poliami tabulky");
        try {
            vyber(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

       System.out.println("\nAktualizuj (UPDATE) knihu s id = 1 - zmen jej nazov a  autora");
        System.out.println("--------------------------------");
        try {
            aktualizuj(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("\nVlož (INSERT resp. CREATE) novu knihu");
        System.out.println("--------------------------------");
        try {
            vloz(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




        System.out.println("\nOstraň (DELETE) ktora ma id = 1");
        try {
            odstran(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        System.out.println("Database connection is going to close");
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

