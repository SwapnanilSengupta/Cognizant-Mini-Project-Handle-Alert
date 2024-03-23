package Mini_project;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;

public class Test_Alert_Website {

	public static Properties p;
	public static WebDriver driver;

	public void setCellData(String second_result, String third_result) throws IOException {
		String xlfile = System.getProperty("user.dir") + "\\ExcelData\\Alert_Result.xlsx";

		FileInputStream fi = new FileInputStream(xlfile);

		XSSFWorkbook wb = new XSSFWorkbook(fi);

		XSSFSheet ws = wb.getSheet("Sheet1");

		XSSFRow row = ws.createRow(1);

		XSSFCell cell_0 = row.createCell(0);

		XSSFCell cell_1 = row.createCell(1);

		cell_0.setCellValue(second_result);

		cell_1.setCellValue(third_result);

		FileOutputStream fo = new FileOutputStream(xlfile);

		wb.write(fo);

		wb.close();

		fi.close();

		fo.close();

	}

	public void WebdriverSetup() throws IOException {

		String browser_name = p.getProperty("driver");

		// If browser is Chrome
		if (browser_name.equals("chrome")) {
			driver = new ChromeDriver();
		}
		// If the browser is Edge
		else if (browser_name.equals("edge")) {
			driver = new EdgeDriver();
		}

		else {
			System.out.println("Wrong Browser");
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get("https://demo.automationtesting.in/Alerts.html");

		driver.manage().window().maximize();

		WebElement SwitchTo = driver.findElement(By.cssSelector("li[class='dropdown active']>a[class='dropdown-toggle']"));

		WebElement Alerts = driver.findElement(By.cssSelector("a[href='Alerts.html']"));

		Actions act = new Actions(driver);
		// Hover to SwitchTo Then click Alert
		act.moveToElement(SwitchTo).moveToElement(Alerts).click().perform();

	}

	public void Alert_With_Ok_label() {
		// Click on Alert With Ok label
		driver.findElement(By.cssSelector("a[href='#OKTab']")).click();
		// Click on corresponding button
		driver.findElement(By.cssSelector("button[class='btn btn-danger']")).click();

		try {
			Alert ok_alert = driver.switchTo().alert();

			ok_alert.accept();

			System.out.println("Pop Up Alert Appeared");

			System.out.println("-------------------------------");

		} catch (NoAlertPresentException Ex) {
			System.out.println("No Pop Up Alert Appeared");

			System.out.println("-------------------------------");
		}
	}

	public String Alert_With_Ok_and_Cancel_label(Properties p) {
		// Click on Alert With Ok & Cancel label
		driver.findElement(By.cssSelector("a[href='#CancelTab']")).click();
		// Click on corresponding button
		driver.findElement(By.cssSelector("button[class='btn btn-primary']")).click();
		String message = "";
		try {
			Alert OkandCancel = driver.switchTo().alert();

			OkandCancel.dismiss();

			message = driver.findElement(By.id("demo")).getText();

			System.out.println("Confirm Box Pop Up Appeared");

			System.out.println(message);

			System.out.println("-------------------------------");

		} catch (NoAlertPresentException Ex) {
			System.out.println("Confirm Box Not Pop Up Appeared");

			System.out.println("-------------------------------");
		}
		return message;

	}

	public String Alert_with_TextBox_Label(Properties p) throws IOException {
		// Click on Alert with TextBox Label
		String message = "";

		driver.findElement(By.cssSelector("a[href='#Textbox']")).click();
		// Click on corresponding button
		driver.findElement(By.cssSelector("button[class='btn btn-info']")).click();
		try {
			Alert alert_with_textbox = driver.switchTo().alert();

			alert_with_textbox.sendKeys(p.getProperty("name"));

			alert_with_textbox.accept();

			message = driver.findElement(By.id("demo1")).getText();

			System.out.println("Prompt Box Appeared");

			System.out.println(message);

			System.out.println("-------------------------------");
		} catch (NoAlertPresentException Ex) {
			System.out.println("No Prompt Box Appeared");

			System.out.println("-------------------------------");
		}
		return message;
	}

	public void WebdriverClose() {
		driver.quit();
	}

	public static void main(String[] args) throws IOException {
		FileReader file = new FileReader(".//src//test//resources//driver.properties");

		p = new Properties();

		p.load(file);

		Test_Alert_Website taw = new Test_Alert_Website();

		taw.WebdriverSetup();

		taw.Alert_With_Ok_label();

		String second_result = taw.Alert_With_Ok_and_Cancel_label(p);

		String third_result = taw.Alert_with_TextBox_Label(p);

		taw.setCellData(second_result, third_result);

		taw.WebdriverClose();
	}

}
