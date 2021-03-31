package javascriptexecutor_Practice;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Demo_Aa1 {

	public static WebDriver driver;

	@BeforeMethod
	public void launchBrowser() throws Exception {
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		// String url="https://rahulshettyacademy.com/AutomationPractice/";
		String url = "https://www.path2usa.com/travel-companions";
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

	@AfterMethod
	public void tearDown() throws Exception {
		Thread.sleep(3000);
		driver.quit();
	}
}
