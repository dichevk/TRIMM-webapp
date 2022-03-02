package nl.utwente.trimm.group42.seleniumtests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SeleniumTest {
	public static WebDriver driver;
	public static WebDriverWait var;

	public static void fallasleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void init() {
		WebDriverManager.chromedriver().version("83.0.4103.39").setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("enable-automation");
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-infobars");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--disable-browser-side-navigation");
		options.addArguments("--disable-gpu");
		driver = new ChromeDriver(options);
		var = new WebDriverWait(driver, 20);
	}

	public static void testLoginLogout() {
		driver.get("http://env-di-team42.paas.hosted-by-previder.com/TRIMM_visualizing_runners_data_42/");
		fallasleep(2000);
		var.until(ExpectedConditions.elementToBeClickable(By.id("open")));
		driver.findElement(By.id("open")).click();
		fallasleep(2000);
		var.until(ExpectedConditions.elementToBeClickable(By.linkText("Login")));
		driver.findElement(By.linkText("Login")).click();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.findElement(By.id("username")).sendKeys("JF");
		fallasleep(1000);
		driver.findElement(By.id("password")).sendKeys("secondrunner18");
		var.until(ExpectedConditions.elementToBeClickable(By.name("Log in")));
		driver.findElement(By.name("Log in")).click();
		fallasleep(2000);
		var.until(ExpectedConditions.elementToBeClickable(By.id("open")));
		fallasleep(2000);
		driver.findElement(By.id("open")).click();
		fallasleep(2000);
		var.until(ExpectedConditions.elementToBeClickable(By.linkText("Log out")));
		driver.findElement(By.linkText("Log out")).click();
		fallasleep(3000);

		// closing the browser
		driver.close();

		System.out.println("testLoginLogout successful");
	}

	public static void testAccountAccess() {
		driver.get("http://env-di-team42.paas.hosted-by-previder.com/TRIMM_visualizing_runners_data_42/");
		fallasleep(3000);
		var.until(ExpectedConditions.elementToBeClickable(By.id("rr")));
		// click on the radio buttons
		driver.findElement(By.id("rr")).click();
		fallasleep(3000);
		// click on the menu on the top-left
		var.until(ExpectedConditions.elementToBeClickable(By.id("open")));
		driver.findElement(By.id("open")).click();
		fallasleep(2000);
		var.until(ExpectedConditions.elementToBeClickable(By.linkText("Login")));
		driver.findElement(By.linkText("Login")).click();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.findElement(By.id("username")).sendKeys("JF");
		fallasleep(2000);
		driver.findElement(By.id("password")).sendKeys("secondrunner18");
		var.until(ExpectedConditions.elementToBeClickable(By.name("Log in")));
		driver.findElement(By.name("Log in")).click();
		var.until(ExpectedConditions.elementToBeClickable(By.id("open")));
		fallasleep(2000);
		driver.findElement(By.id("open")).click();
		var.until(ExpectedConditions.elementToBeClickable(By.linkText("Account")));
		driver.findElement(By.linkText("Account")).click();
		fallasleep(2000);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		var.until(ExpectedConditions.elementToBeClickable(By.id("open")));
		driver.findElement(By.id("open")).click();
		var.until(ExpectedConditions.elementToBeClickable(By.linkText("Log out")));
		driver.findElement(By.linkText("Log out")).click();
		fallasleep(3000);
		driver.close();
		System.out.println("testAccountAccess successful");
	}

	public static void testRunAccess() {
		driver.get("http://env-di-team42.paas.hosted-by-previder.com/TRIMM_visualizing_runners_data_42/");
		fallasleep(3000);
		var.until(ExpectedConditions.elementToBeClickable(By.id("open")));
		fallasleep(2000);
		driver.findElement(By.id("open")).click();
		fallasleep(2000);
		var.until(ExpectedConditions.elementToBeClickable(By.linkText("Login")));
		driver.findElement(By.linkText("Login")).click();
		driver.findElement(By.id("username")).sendKeys("JF");
		fallasleep(1000);
		driver.findElement(By.id("password")).sendKeys("secondrunner18");
		var.until(ExpectedConditions.elementToBeClickable(By.name("Log in")));
		driver.findElement(By.name("Log in")).click();
		fallasleep(2000);
		var.until(ExpectedConditions.elementToBeClickable(By.id("open")));
		fallasleep(2000);
		driver.findElement(By.id("open")).click();
		var.until(ExpectedConditions.elementToBeClickable(By.linkText("My Runs")));
		driver.findElement(By.linkText("My Runs")).click();
		var.until(ExpectedConditions.elementToBeClickable(By.className("run1-img")));
		driver.findElement(By.className("run1-img")).click();
		// click some buttons
		fallasleep(10000);
		var.until(ExpectedConditions.elementToBeClickable(By.id("someButton1")));
		driver.findElement(By.id("someButton1")).click();
		fallasleep(2000);
		var.until(ExpectedConditions.elementToBeClickable(By.id("someButton2")));
		driver.findElement(By.id("someButton2")).click();
		fallasleep(2000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		var.until(ExpectedConditions.elementToBeClickable(By.id("someButton8")));
		WebElement Element = driver.findElement(By.id("someButton8"));
		js.executeScript("arguments[0].scrollIntoView();", Element);
		var.until(ExpectedConditions.elementToBeClickable(By.id("someButton5")));
		 fallasleep(2000);
		driver.findElement(By.id("someButton5")).click();
		fallasleep(3000);
		var.until(ExpectedConditions.elementToBeClickable(By.id("open")));
		driver.findElement(By.id("open")).click();
		var.until(ExpectedConditions.elementToBeClickable(By.linkText("Log out")));
		driver.findElement(By.linkText("Log out")).click();
		fallasleep(3000);
		driver.close();
		System.out.println("testRunAccess successful");
	}

	public static void testCompareAccess() {
		driver.get("http://env-di-team42.paas.hosted-by-previder.com/TRIMM_visualizing_runners_data_42/");
		fallasleep(3000);
		var.until(ExpectedConditions.elementToBeClickable(By.id("open")));
		driver.findElement(By.id("open")).click();
		fallasleep(2000);
		var.until(ExpectedConditions.elementToBeClickable(By.linkText("Login")));
		driver.findElement(By.linkText("Login")).click();
		driver.findElement(By.id("username")).sendKeys("JF");
		fallasleep(2000);
		driver.findElement(By.id("password")).sendKeys("secondrunner18");
		var.until(ExpectedConditions.elementToBeClickable(By.name("Log in")));
		driver.findElement(By.name("Log in")).click();
		fallasleep(2000);
		var.until(ExpectedConditions.elementToBeClickable(By.id("open")));
		fallasleep(2000);
		driver.findElement(By.id("open")).click();
		fallasleep(2000);
		var.until(ExpectedConditions.elementToBeClickable(By.linkText("Compare Runs")));
		driver.findElement(By.linkText("Compare Runs")).click();
		fallasleep(3000);
		var.until(ExpectedConditions.elementToBeClickable(By.id("Pace1")));
		// fallasleep(2000);
		driver.findElement(By.id("Pace1")).click();
		fallasleep(3000);
		var.until(ExpectedConditions.elementToBeClickable(By.id("add")));
		driver.findElement(By.id("add")).click();
		fallasleep(3000);
		var.until(ExpectedConditions.elementToBeClickable(By.id("run1")));
		driver.findElement(By.id("run1")).click();
		fallasleep(3000);
		var.until(ExpectedConditions.elementToBeClickable(By.id("open")));
		driver.findElement(By.id("open")).click();
		var.until(ExpectedConditions.elementToBeClickable(By.linkText("Log out")));
		driver.findElement(By.linkText("Log out")).click();
		fallasleep(3000);
		driver.close();
		System.out.println("testCompareAccess successful");
	}

	public static void testEditacc() {
		driver.get("http://env-di-team42.paas.hosted-by-previder.com/TRIMM_visualizing_runners_data_42/");
		fallasleep(3000);
		var.until(ExpectedConditions.elementToBeClickable(By.id("open")));
		driver.findElement(By.id("open")).click();
		fallasleep(3000);
		var.until(ExpectedConditions.elementToBeClickable(By.linkText("Login")));
		driver.findElement(By.linkText("Login")).click();
		driver.findElement(By.id("username")).sendKeys("JF");
		fallasleep(2000);
		driver.findElement(By.id("password")).sendKeys("secondrunner18");
		fallasleep(1000);
		var.until(ExpectedConditions.elementToBeClickable(By.name("Log in")));
		driver.findElement(By.name("Log in")).click();
		fallasleep(3000);
		var.until(ExpectedConditions.elementToBeClickable(By.id("open")));
		fallasleep(2000);
		driver.findElement(By.id("open")).click();
		fallasleep(3000);
		var.until(ExpectedConditions.elementToBeClickable(By.linkText("Account")));
		driver.findElement(By.linkText("Account")).click();
		fallasleep(2000);
		var.until(ExpectedConditions.elementToBeClickable(By.id("edit")));
		fallasleep(2000);
		driver.findElement(By.id("edit")).click();
		fallasleep(6000);
		var.until(ExpectedConditions.elementToBeClickable(By.id("input1")));
		driver.findElement(By.id("input1")).clear();
		var.until(ExpectedConditions.elementToBeClickable(By.id("input1")));
		driver.findElement(By.id("input1")).sendKeys("jacob");
		fallasleep(2000);
		driver.findElement(By.id("input2")).clear();
		driver.findElement(By.id("input2")).sendKeys("jacobson");
		fallasleep(2000);
		driver.findElement(By.id("input3")).clear();
		driver.findElement(By.id("input3")).sendKeys("100");
		fallasleep(2000);
		driver.findElement(By.id("input4")).clear();
		driver.findElement(By.id("input4")).sendKeys("150");
		fallasleep(2000);
		var.until(ExpectedConditions.elementToBeClickable(By.id("save")));
		driver.findElement(By.id("save")).click();
		fallasleep(6000);
		var.until(ExpectedConditions.elementToBeClickable(By.id("open")));
		driver.findElement(By.id("open")).click();
		fallasleep(3000);
		driver.findElement(By.linkText("Log out")).click();
		fallasleep(2000);
		driver.close();
		System.out.println("testEditacc successful");
	}

	public static void main(String[] args) {
		// testing run access
		init();
		testRunAccess();
		// testing compare access
		init();
		testCompareAccess();

		// test editing the acc
		init();
		testEditacc();
		// testing login in as JF and the loging out
		init();
		testLoginLogout();
		// testing account access
		init();
		testAccountAccess();

	}

}