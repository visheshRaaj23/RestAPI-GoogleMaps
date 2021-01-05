package pack3;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Read_PDF {
	

	@Test
	public void readPDFEngine() {

		try {
			System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
			WebDriver driver = new ChromeDriver();
			driver.navigate().to(
					"https://www.betterteam.com/downloads/job-application-form-template-download-standard-20170814.pdf");
			String url = driver.getCurrentUrl();
			URL readUrl = new URL(url);
			InputStream is = readUrl.openStream();
			BufferedInputStream biStream = new BufferedInputStream(is);
			PDDocument pdDocument = null;
			pdDocument = PDDocument.load(biStream);
			String pdfContent = new PDFTextStripper().getText(pdDocument);
			System.out.println(pdfContent);
			Assert.assertTrue(pdfContent.contains("Employment History "));
			Assert.assertTrue(pdfContent.contains("Signature Disclaimer"));

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
