package javascriptexecutor_Practice;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class Demo_Aa1 {

	public static WebDriver driver;

	public static String getPropertyValue() throws Exception {
		Properties properties;
		InputStream stream = null;
		String baseUrl = null;
		try {
			File file = new File("./data/application.properties");
			stream = new FileInputStream(file);
			properties = new Properties();
			properties.load(stream);
			Reporter.log(properties.getProperty("application_url"), true);
			baseUrl = properties.getProperty("application_url");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return baseUrl;
	}

	@BeforeMethod
	public void launchBrowser() throws Exception {
		DesiredCapabilities ds = DesiredCapabilities.chrome();
		ds.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		ds.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		ChromeOptions options = new ChromeOptions();
		options.merge(ds);
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		// String url="https://rahulshettyacademy.com/AutomationPractice/";
		String url = this.getPropertyValue();
		driver.navigate().to(url);
	}

	@Test
	public void testJavaScript() throws Exception {
		// Date:03/28/2021(Sunday)
		int totalAmount = 0;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,460);");
		Thread.sleep(2000);
		js.executeScript("document.querySelector('.tableFixHead').scrollTop=5000");
		Thread.sleep(4000);
		List<WebElement> amountList = driver.findElements(By.cssSelector(".tableFixHead td:nth-child(4)"));
		for (WebElement amount : amountList) {
			totalAmount = totalAmount + Integer.parseInt(amount.getText());
		}
		System.out.println("AfterTest-->" + totalAmount);
		String totalAmountActual = ((driver.findElement(By.xpath("//div[@class='totalAmount']")).getText()
				.split(":"))[1]).trim();
		Reporter.log(totalAmountActual, true);
		if (Integer.parseInt(totalAmountActual) == totalAmount) {
			Assert.assertTrue(true);
		} else {
			Assert.assertFalse(true);
		}
		driver.close();

	}

	@Test
	public void testAutosuggestion() throws Exception {
		// Date:03/28/2021(Sunday)
		Scanner scan = new Scanner(System.in);
		Reporter.log("Enter the Country Name", true);
		String country = scan.next();
		driver.findElement(By.cssSelector("input[id='autocomplete']")).sendKeys(country);
		String xpath = "//ul/li[contains(@class,'ui-menu-item')]";
		WebDriverWait wait = new WebDriverWait(driver, 6);
		List<WebElement> countryMatches = driver.findElements(By.xpath(xpath));
		wait.until(ExpectedConditions.visibilityOfAllElements(countryMatches));
		for (WebElement countryInd : countryMatches) {
			if (countryInd.getText().trim().contains(country)) {
				countryInd.click();
				Assert.assertTrue(true);
				break;
			} else {
				continue;
			}
		}
		Thread.sleep(3000);
		scan.close();
		driver.close();
	}

	@Test
	public void testLinksCount() throws Exception {
		int totalPageLinkCount = driver.findElements(By.tagName("a")).size();
		System.out.println("Total count of Links are" + totalPageLinkCount);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,7000);");
		WebElement footerDriver = driver.findElement(By.id("gf-BIG"));
		int footerLinks = footerDriver.findElements(By.tagName("a")).size();
		System.out.println("Footer Links Present:" + footerLinks);
		WebDriverWait wait = new WebDriverWait(driver, 6);
		List<WebElement> firstColumnLinks = footerDriver.findElements(
				By.xpath("//a[contains(text(),'Discount Coupons')]/ancestor::li/following-sibling::li//a"));
		wait.until(ExpectedConditions.visibilityOfAllElements(firstColumnLinks));
		System.out.println(firstColumnLinks.size());
		for (WebElement links : firstColumnLinks) {
			links.sendKeys(Keys.chord(Keys.CONTROL, Keys.ENTER));
		}
		Set<String> windowsLaunched = driver.getWindowHandles();
		String parentWindow = driver.getWindowHandle();
		System.out.println(windowsLaunched.size());
		Iterator<String> iteratorObj = windowsLaunched.iterator();
		while (iteratorObj.hasNext()) {
			driver.switchTo().window(iteratorObj.next());
			System.out.println(driver.getTitle());
		}
		driver.switchTo().window(parentWindow);
	}

	@Test
	public void testCalendar() {
		driver.findElement(By.xpath("//input[@name='travel_date']")).click();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		WebElement calendarMonth = driver
				.findElement(By.xpath("//div[@class='datepicker-days']//th[@class='datepicker-switch']"));
		while (!calendarMonth.getText().contains("June")) {
			driver.findElement(By.xpath("//div[@class='datepicker-days']//th[@class='next']")).click();
		}
		List<WebElement> activeDates = driver
				.findElements(By.xpath("//div[contains(@class,'datepicker-days')]//tbody/tr/td[@class='day']"));
		wait.until(ExpectedConditions.visibilityOfAllElements(activeDates));
		for (WebElement date : activeDates) {
			if (date.getText().equalsIgnoreCase("23")) {
				date.click();
				break;
			} else {
				continue;
			}
		}

	}

	@Test
	public void amazonAccountHover() {
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(By.xpath("//a[@id='nav-link-accountList']"))).contextClick().build()
				.perform();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		WebElement searchBoxElement = driver.findElement(By.xpath("//input[contains(@id,'twotabsearchtextbox')]"));
		wait.until(ExpectedConditions.visibilityOf(searchBoxElement));
		// -----><Importent>
		action.moveToElement(searchBoxElement).click().keyDown(Keys.SHIFT).sendKeys("iphone").doubleClick().build()
				.perform();
	}

	@Test
	public void loginPageWindowSwitchPractice() throws Exception {
		driver.findElement(By.className("blinkingText")).click();
		String parentWindow = driver.getWindowHandle();
		Set<String> windowsInvoked = driver.getWindowHandles();
		for (String window : windowsInvoked) {
			if (!window.equals(parentWindow)) {
				driver.switchTo().window(window);
				System.out.println(driver.getTitle());
				break;
			}
		}
		String trainerEmail = driver.findElement(By.xpath("//p[contains(text(),'email us')]//a")).getText();
		driver.switchTo().window(parentWindow);
		System.out.println(driver.getTitle());
		driver.findElement(By.id("username")).sendKeys(trainerEmail);
	}

	@Test
	public void dragAndDropFrames() throws Exception {
		WebElement iFrame = driver.findElement(By.className("demo-frame"));
		driver.switchTo().frame(iFrame);
		Actions action = new Actions(driver);
		action.dragAndDrop(driver.findElement(By.id("draggable")), driver.findElement(By.id("droppable"))).build()
				.perform();
	}

	@Test
	public void takeScreenShotTest() throws Exception {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File("./target/screenshots/screenshot23.png"));
	}

	@Test
	public void testBrokenLinks() throws Exception {
		// Xpath://div[@id='gf-BIG']//li/a
		// Css :$('li[class*="gf-li"] a')
		SoftAssert softAssert = new SoftAssert();
		List<WebElement> footerLinks = driver.findElements(By.xpath("//div[@id='gf-BIG']//li/a"));
		for (WebElement link : footerLinks) {
			String linkUrl = link.getAttribute("href");
			HttpURLConnection conn = (HttpURLConnection) new URL(linkUrl).openConnection();
			conn.setRequestMethod("HEAD");
			conn.connect();
			int responseCode = conn.getResponseCode();
			Reporter.log(link.getText() + " link Have the Status code of:" + responseCode,true);
			softAssert.assertTrue(responseCode<400,link.getText() + " Is Broken!!Please re-verify and try Again");
			/*
			 * if (responseCode >= 400) { Assert.assertTrue(false);
			 * Reporter.log(link.getText() + " Is Broken!!Please re-verify and try Again",
			 * true); }
			 */

		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		Thread.sleep(3000);
		driver.quit();
	}
}
