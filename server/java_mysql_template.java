// 
import java.sql.*;
import java.util.Scanner;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class Test {
    
    final static String user = "root";
    final static String password = "0596";
    final static String db = "db_class";
    // final static String user = "dbd0927";
    // final static String password = "Apple123";
    // final static String db = "dbd0927";
    final static String jdbc = "jdbc:mysql://localhost/"+db+"?user="+user+"&password="+password;

    public static void main(String[] args) {
        // Load Driver
        Connection con = null;                              
        try
        {
            System.out.println("driver loaded");
            Class.forName ("com.mysql.jdbc.Driver");
        }
        catch(Exception e)
        {
            System.out.println("JDBC MySQL driver failed to load");
        }

        // Establish a connection
        try
        {
            // con = DriverManager.getConnection("jdbc:mysql://omega.uta.edu:3306/axd0230?user=axd0230&password=Welcome2015");
            con = DriverManager.getConnection(jdbc);
            System.out.println("connected");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("Failed to connect to the database");
        }

        Scanner in = new Scanner(System.in);
        ResultSet rs;
        Statement statement1;
        int choice;
        
        // Menu
        while(true)                                                             
        {
            System.out.println("Choose an option");
            System.out.println("Enter 1 : View of Unassigned Pilot Flights");
            System.out.println("Enter 2 : View of Due For Maintenance Planes");
            System.out.println("Enter 3 : View of Pilot Fly Assignments");
            System.out.println("Enter 4 : View of Pilot FlightLegs Count");
            System.out.println("Enter 5 : Quit");

            choice = in.nextInt();
            rs = null;
            statement1 = null;
            
            // Switch on option
            switch(choice){
            case 1:
                try{
                    statement1 = con.createStatement();
                    statement1.executeUpdate(
                        "CREATE VIEW UnassignedPilotFlights AS select FL.FLNO , FL.Seq, FL.FDate "                          
                      + "FROM FlightLeg AS F, FlightLegInstance AS FL " 
                      + "WHERE FL.FLNO = F.FLNO AND FL.Pilot = NULL");

                    rs = statement1.executeQuery("SELECT * FROM UnassignedPilotFlights");
                    
                    System.out.println("FLNO" + "\t" + "Seq" +"\t"+"FDate");
                    while (rs.next()){
                        String str = rs.getString("FLNO")+ "\t" +rs.getString("Seq")+"\t"+rs.getString("FDate");
                        System.out.println(str);
                    }
                    System.out.println();
 
                }catch(SQLException e){
                    e.printStackTrace();
                    
                } break;

            case 2:
                try{
                    statement1 = con.createStatement();
                    statement1.executeUpdate(
                        "CREATE VIEW DueForMaintenance AS SELECT ID , Maker, Model, LastMaint "                         
                      + "FROM Plane " 
                      + "WHERE DATEDIFF(NOW(), Plane.LastMainT) > 60");

                    rs = statement1.executeQuery("SELECT * FROM DueForMaintenance");
                    
                    System.out.println("ID" + "\t" + "Maker" +"\t"+"Model"+"\t"+"LastMaint");
                    while (rs.next()){
                        String str = rs.getString("ID")+ "\t" +rs.getString("MAKER")+"\t"+rs.getString("MODEL")+"\t"+rs.getString("LastMaint");
                        System.out.println(str);
                    }
                    System.out.println();
 
                }catch(SQLException e){
                    e.printStackTrace();
                    
                } break;
                
            case 3:
                try{
                    statement1 = con.createStatement();
                    statement1.executeUpdate(
                        "CREATE VIEW PilotFlyAssignments AS SELECT P.ID, P.Name, FLI.FLNO, FL.FromA, FL.ToA, FLI.FDate "                            
                      + "FROM Pilot AS P, FlightLeg AS FL, FlightLegInstance AS FLI " 
                      + "WHERE FLI.FLNO = FL.FLNO AND FLI.Seq = FL.Seq AND FLI.Pilot = P.ID");

                    rs = statement1.executeQuery("SELECT * FROM PilotFlyAssignments");
                    
                    System.out.println("ID" + "\t" + "Name" +"\t"+"FLNO"+"\t"+"FromA"+"\t"+"ToA"+"\t"+"FDate");
                    while (rs.next()){
                        String str = rs.getString("ID")+ "\t" +rs.getString("Name")+"\t"+rs.getString("FLNO")+"\t"+rs.getString("FromA")+ "\t" +rs.getString("ToA")+"\t"+rs.getString("FDate");
                        System.out.println(str);
                    }
                    System.out.println();
 
                }catch(SQLException e){
                    e.printStackTrace();
                    
                } break;

            case 4:
                try{
                    statement1 = con.createStatement();
                    statement1.executeUpdate(
                        "CREATE VIEW PilotFlightLegsCount AS SELECT P.ID, P.Name, CONCAT(YEAR(FLI.FDate),'-', MONTH(FLI.FDate)) AS `Year_Month`, COUNT(*) "                         
                      + "FROM Pilot AS P, FlightLegInstance AS FLI " 
                      + "WHERE FLI.Pilot = P.ID "
                      + "GROUP BY `Year_Month`");

                    rs = statement1.executeQuery("SELECT * FROM PilotFlightLegsCount");
                    
                    System.out.println("ID" + "\t" + "Name" +"\t"+"Year_Month"+"\t"+"COUNT(*)");
                    while (rs.next()){
                        String str = rs.getString("ID")+ "\t" +rs.getString("Name")+"\t"+rs.getString("Year_Month")+"\t\t"+rs.getString("COUNT(*)");
                        System.out.println(str);
                    }
                    System.out.println();
 
                }catch(SQLException e){
                    e.printStackTrace();
                    
                } break;
                
            case 5:
                return;
            // default:
            }       
        }
    }
}

public class Test2 {

    final static String user = "root";
    final static String password = "0596";
    final static String db = "db_class";
    // final static String user = "dbd0927";
    // final static String password = "Apple123";
    // final static String db = "dbd0927";
    final static String jdbc = "jdbc:mysql://localhost/"+db+"?user="+user+"&password="+password;

    public static void main(String[] args) {
        // Load Driver
        Connection con = null;                              
        try
        {
            System.out.println("driver loaded");
            Class.forName ("com.mysql.jdbc.Driver");
        }
        catch(Exception e)
        {
            System.out.println("JDBC MySQL driver failed to load");
        }

        // Establish a connection
        try
        {
            // con = DriverManager.getConnection("jdbc:mysql://omega.uta.edu:3306/axd0230?user=axd0230&password=Welcome2015");
            con = DriverManager.getConnection(jdbc);
            System.out.println("connected");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("Failed to connect to the database");
        }

        Scanner in = new Scanner(System.in);
        ResultSet rs;
        Statement statement1;
        int choice;
        
        // Menu
        while(true)                                                             
        {
            System.out.println("Choose an option");
            System.out.println("Enter 1 : Check if Pilot is busy on a certain day and show the pilot assignments for this day.");
            System.out.println("Enter 2 : Assign a Pilot to a flight leg instance");
            System.out.println("Enter 3 : Add a Pilot");
            System.out.println("Enter 4 : Quit");

            choice = in.nextInt();
            rs = null;
            statement1 = null;
            
            // Switch on option
            switch(choice){
            case 1:
                System.out.print("Enter Pilot Name: ");
                String name = in.next();

                System.out.print("Enter Date (yyyy-mm-dd): ");
                String date = in.next();

                try{
                    statement1 = con.createStatement();
                    rs = statement1.executeQuery(
                        "SELECT * "                         
                      + "FROM PilotFlyAssignments " 
                      + "WHERE FDate = '" + date + "' AND Name = '" + name + "'");
                    
                    System.out.println("ID" + "\t" + "Name" +"\t"+"FLNO"+"\t"+"FromA"+"\t"+"ToA"+"\t"+"FDate");
                    while (rs.next()){
                        String str = rs.getString("ID")+ "\t" +rs.getString("Name")+"\t"+rs.getString("FLNO")+"\t"+rs.getString("FromA")+ "\t" +rs.getString("ToA")+"\t"+rs.getString("FDate");
                        System.out.println(str);
                    }
                    rs.close();
                    System.out.println();
 
                }catch(SQLException e){
                    e.printStackTrace();
                    
                } break;

            case 2:
                System.out.print("Enter Pilot ID: ");
                int PID = in.nextInt();

                System.out.print("Enter a flight sequence: ");
                int seq = in.nextInt();
                System.out.print("Enter a flight number: ");
                int flight_num = in.nextInt();
                System.out.print("Enter Date (yyyy-mm-dd): ");
                date = in.next();
                
                try{
                    statement1 = con.createStatement();
                    statement1.executeUpdate(
                        "UPDATE FlightLegInstance "
                      + "SET Pilot = " + PID 
                      + " WHERE Seq = " + seq + " AND FLNO = " + flight_num + " AND FDate = '" + date + "'");

                    System.out.println("Pilot successfully assigned");
                    System.out.println();
 
                }catch(SQLException e){
                    e.printStackTrace();
                    
                } break;
                
            case 3:
                System.out.print("Enter Pilot ID: ");
                PID = in.nextInt();
                System.out.print("Enter Pilot Name: ");
                name = in.next();
                System.out.print("Enter Date Hired (yyyy-mm-dd): ");
                date = in.next();

                boolean error = false; 
                
                try{
                    statement1 = con.createStatement();
                    ResultSet rs_new = statement1.executeQuery("SELECT * FROM Pilot");

                    while (rs_new.next()){
                        System.out.println(rs_new.getInt("ID") + " " + rs_new.getString("Name"));
                        if (rs_new.getInt("ID") == PID){
                            System.out.println("Error, pilot ID already exists in database.\n");
                            error = true;
                            break;
                        }
                        else if (rs_new.getString("Name").equals(name)){
                            System.out.println("Pilot name already exists, do you wish to continue? (Y/N): ");
                            String answer = in.next();

                            if (answer.equals("N")){
                                error = true;
                                System.out.println();
                                break;
                            }
                        }
                    }

                    if (error){
                        break;
                    }

                    statement1.executeUpdate(
                            "INSERT INTO Pilot " 
                          + "VALUES(" + PID + ", '" + name  + "', '" + date + "')");

                    rs_new.close();
                    System.out.println("Pilot successfully added");
                    System.out.println();
 
                }catch(SQLException e){
                    e.printStackTrace();
                    
                } break;

            case 4:
                return;
            // default:
            }       
        }
    }
}