package setting;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.stream.IntStream;

import javax.swing.JOptionPane;

public class Setting {

	public static void main(String[] args) throws Exception {
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/?serverTimezone=UTC", "root", "1234");
		Statement stmt = con.createStatement();
		
		stmt.execute("DROP DATABASE IF EXISTS Hotel");
		stmt.execute("CREATE DATABASE Hotel");
		
		stmt.execute("CREATE TABLE IF NOT EXISTS `Hotel`.`member` (\r\n" + 
				"  `M_no` INT NOT NULL AUTO_INCREMENT,\r\n" + 
				"  `M_id` VARCHAR(10) NULL,\r\n" + 
				"  `M_name` VARCHAR(10) NULL,\r\n" + 
				"  `M_pw` INT(11) NULL,\r\n" + 
				"  `M_birth` DATE NULL,\r\n" + 
				"  `M_phone` VARCHAR(20) NULL,\r\n" + 
				"  PRIMARY KEY (`M_no`))\r\n" + 
				"ENGINE = InnoDB;");
		
		stmt.execute("CREATE TABLE IF NOT EXISTS `Hotel`.`hotel` (\r\n" + 
				"  `H_no` INT NOT NULL AUTO_INCREMENT,\r\n" + 
				"  `H_name` VARCHAR(30) NULL,\r\n" + 
				"  `H_address` VARCHAR(30) NULL,\r\n" + 
				"  `H_grade` INT NULL,\r\n" + 
				"  `H_tax` INT NULL,\r\n" + 
				"  PRIMARY KEY (`H_no`))\r\n" + 
				"ENGINE = InnoDB;");
		
		stmt.execute("CREATE TABLE IF NOT EXISTS `Hotel`.`review` (\r\n" + 
				"  `R_no` INT NOT NULL AUTO_INCREMENT,\r\n" + 
				"  `RH_no` INT NULL,\r\n" + 
				"  `RH_id` VARCHAR(30) NULL,\r\n" + 
				"  `R_review` VARCHAR(30) NULL,\r\n" + 
				"  `RH_grade` INT NULL,\r\n" + 
				"  `RH_date` DATE NULL,\r\n" + 
				"  PRIMARY KEY (`R_no`))\r\n" + 
				"ENGINE = InnoDB;");
		
		stmt.execute("CREATE TABLE IF NOT EXISTS `Hotel`.`wishlist` (\r\n" + 
				"  `W_no` INT NOT NULL AUTO_INCREMENT,\r\n" + 
				"  `W_id` VARCHAR(30) NULL,\r\n" + 
				"  `WH_no` INT NULL,\r\n" + 
				"  PRIMARY KEY (`W_no`))\r\n" + 
				"ENGINE = InnoDB;");
		
		stmt.execute("CREATE TABLE IF NOT EXISTS `Hotel`.`list` (\r\n" + 
				"  `L_no` INT NOT NULL AUTO_INCREMENT,\r\n" + 
				"  `L_id` VARCHAR(20) NULL,\r\n" + 
				"  `L_name` VARCHAR(30) NULL,\r\n" + 
				"  `L_checkin` VARCHAR(10) NULL,\r\n" + 
				"  `L_checkout` VARCHAR(10) NULL,\r\n" + 
				"  `L_adult` INT NULL,\r\n" + 
				"  `L_child` INT NULL,\r\n" + 
				"  `L_total` INT NULL,\r\n" + 
				"  PRIMARY KEY (`L_no`))\r\n" + 
				"ENGINE = InnoDB;");
		
		stmt.execute("DROP USER IF EXISTS 'user'@'localhost'");
		stmt.execute("CREATE USER 'user'@'localhost' IDENTIFIED BY '1234'");
		stmt.execute("GRANT SELECT, UPDATE, DELETE, INSERT ON Hotel.* TO 'user'@'localhost'");
		stmt.execute("FLUSH PRIVILEGES");
		
		var tables = "hotel,list,member,review,wishlist".split(",");
		var columns = new int[] {5, 8, 6, 6, 3};
		
		for (int i = 0; i < tables.length; i++) {
			String values = String.join(", ", IntStream.range(0, columns[i]).mapToObj(x -> "?").toArray(String[]::new));
			try (var pst = con.prepareStatement("INSERT INTO hotel." + tables[i] + " VALUES(" + values + ")")) {
				var lines = Files.readAllLines(Paths.get("./DataFiles/" + tables[i] + ".txt"));
				
				for (int j = 1; j < lines.size(); j++) {
					String line = lines.get(j);
					var split = line.split("\t");
					
					for (int k = 0; k < split.length; k++) {
						pst.setObject(k + 1, split[k]);
					}
					
					pst.execute();
				}
			}
		}
		
		JOptionPane.showMessageDialog(null, "DB 초기화 완료.");
		
	}

}
