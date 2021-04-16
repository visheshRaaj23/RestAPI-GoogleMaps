package javastreams;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javascriptexecutor_Practice.Demo_Aa1;

public class WebStreamsPractice {

	public static WebDriver driver;

	@BeforeMethod
	public void launchBrowser() throws Exception {
		DesiredCapabilities ds = DesiredCapabilities.chrome();
		ds.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		ds.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		ChromeOptions options = new ChromeOptions();
		options.merge(ds);
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		String _url = Demo_Aa1.getPropertyValue();
		driver.navigate().to(_url);
	}

	@Test
	public void sortWebTables() {
		driver.findElement(By.xpath("//span[contains(text(),'Veg/fruit name')]/following-sibling::span")).click();
		List<WebElement> stuffsName = driver.findElements(By.xpath("//tbody/tr/td[1]"));
		List<String> collectedList = stuffsName.stream().map(s -> s.getText().trim()).collect(Collectors.toList());
		List<String> sortedList = collectedList.stream().sorted().collect(Collectors.toList());
		Assert.assertTrue(collectedList.equals(sortedList));

	}

	@Test
	public void filterWebTables() {
		List<String> veganPriceRequired = Arrays.asList("Cheese", "Dragon fruit");
		for (String vegetable : veganPriceRequired) {
			driver.findElement(By.xpath("//input[@id='search-field']")).sendKeys(vegetable, Keys.ENTER);
			boolean filterResult = driver.findElements(By.xpath("//tbody/tr/td[1]")).stream().map(s -> s.getText())
					.allMatch(x -> x.contains(vegetable));
			Assert.assertTrue(filterResult);
			if (filterResult) {
				driver.findElement(By.xpath("//input[@id='search-field']")).clear();
			}

		}
	}

	@Test
	public void getPriceTest() {
		Map<String, Integer> priceList = new HashMap<String, Integer>();
		while (driver.findElements(By.xpath("//a[@aria-label='Next']/parent::li[@class='disabled']")).isEmpty()) {
			List<WebElement> stuffsName = driver.findElements(By.xpath("//tbody/tr/td[1]"));
			List<WebElement> stuffsPrice = driver.findElements(By.xpath("//tbody/tr/td[2]"));
			for (int i = 0; i < stuffsName.size(); i++) {
				priceList.put(stuffsName.get(i).getText().trim(),
						Integer.parseInt(stuffsPrice.get(i).getText().trim()));
			}
			boolean nextButton = driver.findElements(By.xpath("//a[@aria-label='Next']/parent::li[@class='disabled']"))
					.isEmpty();
			System.out.println(nextButton);
			if (driver.findElements(By.xpath("//a[@aria-label='Next']/parent::li[@class='disabled']")).isEmpty()) {
				driver.findElement(By.xpath("//li/a[@aria-label='Next']")).click();
			} else {
				break;
			}
		}
		List<String> veganPriceRequired = Arrays.asList("Cheese", "Dragon fruit");

		for (String vegan : priceList.keySet()) {

			if (veganPriceRequired.contains(vegan)) {
				Reporter.log(vegan + " is priced at $" + priceList.get(vegan), true);
			} else {
				continue;
			}

		}

	}

	public Integer getPriceOfVeggie() {
		int price = 0;
		return price;

	}

	@AfterMethod
	public void tearDown() throws Exception {
		Thread.sleep(3000);
		driver.quit();
	}

}
