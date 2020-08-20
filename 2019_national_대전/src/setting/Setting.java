package setting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.stream.IntStream;

import javax.swing.JOptionPane;

public class Setting {

	static Connection con;
	static Statement stmt;
	
	public static void main(String[] args) throws SQLException, IOException {
		
		con = DriverManager.getConnection("jdbc:mysql://localhost/?serverTimezone=UTC&allowLoadLocalInfile=true", "root", "1234");
		stmt = con.createStatement();
		
		stmt.execute("DROP DATABASE IF EXISTS wedding");
		stmt.execute("CREATE SCHEMA IF NOT EXISTS `wedding` DEFAULT CHARACTER SET utf8");
		stmt.execute("USE `wedding` ;");
		
		// Table
		stmt.execute("CREATE TABLE IF NOT EXISTS `wedding`.`weddingtype` (\r\n" + 
				"  `weddingtype_index` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
				"  `weddingtype_name` VARCHAR(15) NULL,\r\n" + 
				"  PRIMARY KEY (`weddingtype_index`))");
		
		stmt.execute("CREATE TABLE IF NOT EXISTS `wedding`.`weddinghall` (\r\n" + 
				"  `weddinghall_index` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
				"  `weddinghall_name` VARCHAR(20) NULL,\r\n" + 
				"  `weddinghall_address` VARCHAR(50) NULL,\r\n" + 
				"  `weddinghall_accommodate` INT(11) NULL,\r\n" + 
				"  `weddinghall_fee` INT(11) NULL,\r\n" + 
				"  PRIMARY KEY (`weddinghall_index`))");
		
		stmt.execute("CREATE TABLE IF NOT EXISTS `wedding`.`weddinghall_weddingtype` (\r\n" + 
				"  `weddinghall_index` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
				"  `weddingtype_index` INT(11) NOT NULL,\r\n" + 
				"  PRIMARY KEY (`weddinghall_index`, `weddingtype_index`),\r\n" + 
				"  INDEX `FK_ww_wt_ww_idx` (`weddingtype_index` ASC) VISIBLE,\r\n" + 
				"  CONSTRAINT `FK_ww_w_ww`\r\n" + 
				"    FOREIGN KEY (`weddinghall_index`)\r\n" + 
				"    REFERENCES `wedding`.`weddinghall` (`weddinghall_index`)\r\n" + 
				"    ON DELETE NO ACTION\r\n" + 
				"    ON UPDATE NO ACTION,\r\n" + 
				"  CONSTRAINT `FK_ww_wt_ww`\r\n" + 
				"    FOREIGN KEY (`weddingtype_index`)\r\n" + 
				"    REFERENCES `wedding`.`weddingtype` (`weddingtype_index`)\r\n" + 
				"    ON DELETE NO ACTION\r\n" + 
				"    ON UPDATE NO ACTION)");
		
		stmt.execute("CREATE TABLE IF NOT EXISTS `wedding`.`mealtype` (\r\n" + 
				"  `mealtype_index` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
				"  `mealtype_name` VARCHAR(5) NULL,\r\n" + 
				"  `mealtype_price` INT(11) NULL,\r\n" + 
				"  PRIMARY KEY (`mealtype_index`))");
		
		stmt.execute("CREATE TABLE IF NOT EXISTS `wedding`.`reservation` (\r\n" + 
				"  `reservation_code` INT(11) NOT NULL,\r\n" + 
				"  `weddinghall_index` INT(11) NULL,\r\n" + 
				"  `reservation_personnel` INT(11) NULL,\r\n" + 
				"  `weddingtype_index` INT(11) NULL,\r\n" + 
				"  `mealtype_index` INT(11) NULL,\r\n" + 
				"  `album` INT(11) NULL,\r\n" + 
				"  `letter` INT(11) NULL,\r\n" + 
				"  `dress` INT(11) NULL,\r\n" + 
				"  `date` DATE NULL,\r\n" + 
				"  `pay` INT(11) NULL,\r\n" + 
				"  PRIMARY KEY (`reservation_code`),\r\n" + 
				"  INDEX `FK_r_w_r_idx` (`weddinghall_index` ASC) VISIBLE,\r\n" + 
				"  INDEX `FK_r_wt_r_idx` (`weddingtype_index` ASC) VISIBLE,\r\n" + 
				"  INDEX `FK_r_mt_r_idx` (`mealtype_index` ASC) VISIBLE,\r\n" + 
				"  CONSTRAINT `FK_r_w_r`\r\n" + 
				"    FOREIGN KEY (`weddinghall_index`)\r\n" + 
				"    REFERENCES `wedding`.`weddinghall` (`weddinghall_index`)\r\n" + 
				"    ON DELETE NO ACTION\r\n" + 
				"    ON UPDATE NO ACTION,\r\n" + 
				"  CONSTRAINT `FK_r_wt_r`\r\n" + 
				"    FOREIGN KEY (`weddingtype_index`)\r\n" + 
				"    REFERENCES `wedding`.`weddingtype` (`weddingtype_index`)\r\n" + 
				"    ON DELETE NO ACTION\r\n" + 
				"    ON UPDATE NO ACTION,\r\n" + 
				"  CONSTRAINT `FK_r_mt_r`\r\n" + 
				"    FOREIGN KEY (`mealtype_index`)\r\n" + 
				"    REFERENCES `wedding`.`mealtype` (`mealtype_index`)\r\n" + 
				"    ON DELETE NO ACTION\r\n" + 
				"    ON UPDATE NO ACTION)");
		
		stmt.execute("CREATE TABLE IF NOT EXISTS `wedding`.`weddinghall_mealtype` (\r\n" + 
				"  `weddinghall_index` INT(11) NOT NULL,\r\n" + 
				"  `mealtype_index` INT(11) NOT NULL,\r\n" + 
				"  PRIMARY KEY (`weddinghall_index`, `mealtype_index`),\r\n" + 
				"  INDEX `FK_wm_m_wm_idx` (`mealtype_index` ASC) VISIBLE,\r\n" + 
				"  CONSTRAINT `FK_wm_w_wm`\r\n" + 
				"    FOREIGN KEY (`weddinghall_index`)\r\n" + 
				"    REFERENCES `wedding`.`weddinghall` (`weddinghall_index`)\r\n" + 
				"    ON DELETE NO ACTION\r\n" + 
				"    ON UPDATE NO ACTION,\r\n" + 
				"  CONSTRAINT `FK_wm_m_wm`\r\n" + 
				"    FOREIGN KEY (`mealtype_index`)\r\n" + 
				"    REFERENCES `wedding`.`mealtype` (`mealtype_index`)\r\n" + 
				"    ON DELETE NO ACTION\r\n" + 
				"    ON UPDATE NO ACTION)");
		
		stmt.execute("DROP USER IF EXISTS 'user'@'localhost'");
		stmt.execute("CREATE USER 'user'@'localhost' IDENTIFIED BY '1234'");
		stmt.execute("GRANT SELECT, UPDATE, DELETE, INSERT ON `wedding`.* TO 'user'@'localhost'");
		stmt.execute("FLUSH PRIVILEGES");
		
		// Data Importing
		String[] tables = "weddinghall,weddingtype,mealtype,weddinghall_mealtype,weddinghall_weddingtype,reservation".split(",");
		int[] count = {5, 2, 3, 2, 2, 10};
		
		for (int i = 0; i < tables.length; i++) {
			var param = String.join(", ", IntStream.range(0, count[i]).mapToObj(x -> "?").toArray(String[]::new));
			
			try (var pst = con.prepareStatement("INSERT INTO " + tables[i] + " VALUES(" + param + ")")) {
				var lines = Files.readAllLines(Paths.get("./지급자료/" + tables[i] + ".txt"));
				
				for (int j = 1; j < lines.size(); j++) {
					var split = lines.get(j).split("\t");
					
					for (int k = 0; k < split.length; k++) {
						pst.setObject(k + 1, split[k]);
					}
					
					pst.execute();
				}
				
			}
		}
		
		JOptionPane.showMessageDialog(null, "DB 생성이 완료 되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
	}

}
