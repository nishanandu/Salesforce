package Scripts;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class Organizations extends GenericSkins{

	public static void InputValue() throws Exception{
			try {
				WebElement organizationName = driver.findElement(By.xpath("//input[@name='Name']"));
				organizationName.sendKeys("AutomationPurpose1");
}catch (Exception e) 
		{
    System.out.println("Inputvalue:"+e.getMessage());
    //Thread.sleep(20000);
    
}
	
	ResultComparision();
}
	public static void searchLeads() throws Exception {
		try {
			WebElement menuButton = driver.findElement(By.xpath("//div[@role='navigation']/child::one-app-launcher-header/child::button"));
			menuButton.click();
			Thread.sleep(8000);
			WebElement searchBox = driver.findElement(By.xpath("//input[@class='slds-input'][@part='input'][@type='search']"));
			searchBox.sendKeys("leads");
			Thread.sleep(4000);
			WebElement leadsValue = driver.findElement(By.xpath("//a[@class='al-menu-item slds-p-vertical--xxx-small']/child::div"));
			Thread.sleep(2000);
			JavascriptExecutor js=(JavascriptExecutor)driver;
			js.executeScript("arguments[0].click()", leadsValue);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		


	}

}

	
