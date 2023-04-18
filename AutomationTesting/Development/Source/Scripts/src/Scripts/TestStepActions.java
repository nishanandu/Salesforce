package Scripts;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;

@SuppressWarnings({ "deprecation", "unused" })
public class TestStepActions extends GenericSkins {
	// ______________________________Method to launch
	// application______________________________________________
	public static void LaunchApplication() throws Exception {
		try {

			switch (sBrowserName.toUpperCase()) {
			case "CHROME":

				/*
				 * System.setProperty("webdriver.chrome.driver", sBrowsserDriverPath +
				 * "chromedriver.exe"); ChromeOptions options = new ChromeOptions();
				 * options.addArguments("--disable-extensions"); Map<String, Object> prefs = new
				 * HashMap<String, Object>(); prefs.put("credentials_enable_service", false);
				 * prefs.put("profile.password_manager_enabled", false);
				 * options.setExperimentalOption("prefs", prefs); driver = new
				 * ChromeDriver(options); break;
				 */

				System.setProperty("webdriver.chrome.driver", sBrowsserDriverPath + "chromedriver.exe");
				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("profile.default_content_settings.popups", 0);
				chromePrefs.put("download.prompt_for_download", "true");
				chromePrefs.put("download.default_directory", "C:\\Users\\Public\\Downloads");
				chromePrefs.put("credentials_enable_service", false);
				chromePrefs.put("profile.password_manager_enabled", false);
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--disable-extensions");
				HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
				options.setExperimentalOption("prefs", chromePrefs);
				options.addArguments("--test-type");
				options.addArguments("--disable-extensions"); // to disable browser extension pop up

				DesiredCapabilities caps = DesiredCapabilities.chrome();
				caps.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
				caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				caps.setCapability(ChromeOptions.CAPABILITY, options);
				driver = new ChromeDriver(caps);
				break;

			case "IE":

				System.setProperty("webdriver.ie.driver", sBrowsserDriverPath + "IEDriverServer.exe");
				DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
				cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				cap.setCapability("ie.ensureCleanSession", true);
				cap.setJavascriptEnabled(true);
				driver = new InternetExplorerDriver(cap);
				break;

			case "FIREFOX":

				System.setProperty("webdriver.gecko.driver", sBrowsserDriverPath + "geckodriver.exe");
				// driver = new MarionetteDriver();
				break;
			}

			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			driver.get(sAUTPath);
			System.out.println("Launching Application");
			sParentWindowHandle = driver.getWindowHandle();
			Thread.sleep(5000);
			Runtime.getRuntime().exec(sPathUtils + "SalesforceLogin.exe");
			Thread.sleep(10000);
			
			sActualResult = "Application launched";
		} catch (Exception error_message) {
			sActualResult = error_message.getMessage();
		}

		ResultComparision();

	}

	// ________________________________Method to set Input
	// Data_____________________________________________________
	public static void InputData() throws Exception {
		String sActualData = "NULL";
		WebElement element = null;
		try {
			element = driver.findElement(getObjectLocator(sObjListObjectName, sObjListObjectLocator));

			switch (sObjListObjectType.toUpperCase()) {
			case "TEXTBOX":
				if (!(sTestStepData.equals("NA"))) {
					element.clear();

					element.sendKeys(sTestStepData);
				}

				else {
					JavascriptExecutor jexe = (JavascriptExecutor) driver;

					jexe.executeScript("document.getElementByname('Name').value='AutoTestORG';");
					element.clear();
				}

				sActualData = sTestStepData;
				break;

			case "DROPDOWN":

				Select dropdown = new Select(element);
				java.util.List<WebElement> Options = dropdown.getOptions();

				for (WebElement value : Options) {
					if (value.getText().trim().equalsIgnoreCase(sTestStepData.trim())) {
						value.click();
						sActualData = value.getText();
						break;
					} else {
						sActualData = value.getText();
					}
				}
				sActualData = dropdown.getFirstSelectedOption().getText();
				break;

			case "COMBOBOX":
				if (sTestStepObjectName.equalsIgnoreCase("Opportunities_InKindorgNameSelect")) {

					WebElement OrganizationDropdown = driver.findElement(By.xpath("(//div[@role='none'])[1]"));
					OrganizationDropdown.click();
				}

			case "DATEINCIDENT":

				// if Test data is Current date then assign test data with system current date
				if (sTestStepData.equalsIgnoreCase("Current Date")) {
					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					Calendar cal = Calendar.getInstance();
					sTestStepData = dateFormat.format(cal.getTime()).toString();
				}

				if (!sTestStepData.equalsIgnoreCase("NA")) {
					// Click on date pricer button
					element.click();

					String dateTime = sTestStepData;

					element.click();
					Thread.sleep(2000);
					// split the date into array
					String dd_mm_yyyy[] = dateTime.split("/");
					GregorianCalendar cal = new GregorianCalendar();
					int Current_Year = cal.get(Calendar.YEAR);
					System.out.println("Current Year:" + Current_Year);
					// diffrence between current year and test data year
					int year_diff = Integer.parseInt(dd_mm_yyyy[2]) - Current_Year;
					System.out.println("Year Diff:" + year_diff);
					Thread.sleep(2000);
					// LIst of date pickers in current page
					List<WebElement> DatePickers = driver
							.findElements(By.cssSelector(".xdsoft_datetimepicker.xdsoft_noselect.xdsoft_"));

					// Iterating through each date picker
					for (WebElement DatePicker : DatePickers) {
						boolean bDatePicker = DatePicker.isDisplayed();

						// check if date picker displayed
						if (bDatePicker == true) {
							System.out.println(DatePicker.getAttribute("style"));
							// click years
							DatePicker.findElement(By.cssSelector(".xdsoft_label.xdsoft_year")).click();
							Thread.sleep(2000);

							// List of years
							List<WebElement> year_list = DatePicker.findElements(By.cssSelector(".xdsoft_option"));
							// Iterate through each year
							for (WebElement value : year_list) {

								String iYear = value.getAttribute("data-value");
								// Check if year matches with test data year
								if (iYear.equals(dd_mm_yyyy[2])) {
									value.click();
									Thread.sleep(1000);
									break;
								}

							}

							// fetch month from date picker
							String month = DatePicker.findElement(By.cssSelector(".xdsoft_option.xdsoft_current"))
									.getAttribute("data-value");
							// convert month into integer
							int iMon = Integer.parseInt(month);
							// Initialize test data month to integer variable
							int aMonth = Integer.parseInt(dd_mm_yyyy[1]) - 1;
							// difference between test data month and date picker month
							int diff_month = aMonth - iMon;

							// if difference is not zero then select month
							if (diff_month != 0) {
								if (diff_month > 0) {
									// click on next button for 'diff_month' times
									for (int i = 0; i < diff_month; i++) {

										DatePicker.findElement(By.cssSelector(".xdsoft_next")).click();
										Thread.sleep(1000);
									}
								}
								if (diff_month < 0) {
									// click on prev button for 'diff_month' times
									for (int i = 0; i < (diff_month * (-1)); i++) {
										DatePicker.findElement(By.cssSelector(".xdsoft_prev")).click();
										Thread.sleep(1000);
									}
								}

							}

							WebElement calend = DatePicker.findElement(By.cssSelector(".xdsoft_calendar"));
							List<WebElement> day_list = calend.findElements(By.tagName("td"));

							for (WebElement value : day_list) {
								String sMonth = value.getAttribute("data-month");
								System.out.println("Month:" + sMonth);
								int iMonth = Integer.parseInt(sMonth);
								if (aMonth == iMonth) {
									String sDay = value.getAttribute("data-date");
									if (sDay.equals(dd_mm_yyyy[0])) {
										value.click();
										Thread.sleep(1000);
										// Constants.Actual_Result = "Date has been picked successfully";
										break;
									} else {
										// Constants.Actual_Result = "Unable to pick the date";
									}
								}
							}

							break;
						}

					}
				} else {
					element.clear();
					Thread.sleep(1000);
					element.click();

				}

				Thread.sleep(1000);
				sActualData = sTestStepData;
				System.out.println("sActualData:" + sActualData);
				break;

			case "WEBLIST":

				List<WebElement> eItems = element.findElements(By.tagName("option"));

				for (WebElement value : eItems) {
					if (value.getText().equalsIgnoreCase(sTestStepData)) {

						value.click();
						sActualData = value.getText();
						break;
					}

					else {
						sActualData = value.getText();
					}
				}

				break;
			case "LIGHTENINGCOMBOBOX":

				WebElement Dropdown = driver.findElement(getObjectLocator(sObjListObjectName, sObjListObjectLocator));

				java.util.List<WebElement> Options1 = Dropdown.findElements(By.tagName("lightning-base-combobox-item"));
				System.out.println("Number of items:" + Options1.size());
				for (WebElement option : Options1) {
					System.out.println("Item:" + option.getText().trim());
					if (option.getText().trim().equals(sTestStepData.trim())) {
						sActualData = option.getText();
						option.click();
						break;
					}
				}

				break;
			case "SFDROPDOWN":

				Thread.sleep(2000);
				// WebElement dropdownbox = driver
				// .findElement(getObjectLocator(sObjListObjectName,
				// sObjListObjectLocator));
				Thread.sleep(2000);
				java.util.List<WebElement> dropdowns = driver
						.findElements(getObjectLocator(sObjListObjectName, sObjListObjectLocator));
				System.out.println("No of Dropdowns" + dropdowns.size());
				for (WebElement drpdwn : dropdowns) {
					if (drpdwn.isDisplayed()) {
						System.out.println("Dropdown Displayed");
						java.util.List<WebElement> OptionsList = null;
						OptionsList = drpdwn.findElements(By.tagName("li"));

						// java.util.List<WebElement> OptionsList =
						// drpdwn.findElements(By.tagName("li"));
						System.out.println("Number of items:" + OptionsList.size());
						Actions actionDrp = new Actions(driver);

						for (WebElement option : OptionsList) {
							Thread.sleep(200);
							if (sTestStepObjectName.equalsIgnoreCase("EmailForwarding_DocumentType")
									|| sTestStepObjectName.equalsIgnoreCase(":PortfolioStatistics_DrawIndex2List")
									|| sTestStepObjectName.equalsIgnoreCase("PortfolioStatistics_DrawIndex1List")) {
								option = option.findElement(By.tagName("option"));
							}
							Actions action = new Actions(driver);
							String sValue = "";
							if (sTestStepOperation.equalsIgnoreCase("NA")) {
								sValue = option.getText().trim();

							} else {
								sValue = option.getAttribute(sTestStepOperation).trim();

							}
							System.out.println("Item:" + sValue);
							sActualData = sValue;
							if (sValue.equals(sTestStepData.trim())) {

								try {
									option.click();
								} catch (WebDriverException e) {
									action = new Actions(driver);
									action.sendKeys(Keys.ENTER).build().perform();
								}

								break;
							} else {
								action.sendKeys(Keys.ARROW_DOWN).build().perform();
							}
						}
						break;
					}
				}

				break;

			case "WEEKLYHOLIDAY":

				element.click();
				Thread.sleep(2000);
				WebElement eDropDown = driver
						.findElement(By.xpath("//ul[@class='multiselect-container dropdown-menu']"));
				eDropDown.findElement(By.xpath("//input[@class='form-control multiselect-search']")).clear();
				eDropDown.findElement(By.xpath("//input[@class='form-control multiselect-search']"))
						.sendKeys(sTestStepData);
				Thread.sleep(2000);
				List<WebElement> eDays = eDropDown.findElements(By.tagName("li"));
				String sDay;
				for (WebElement eDay : eDays) {
					sDay = eDay.getText();
					if (sDay.trim().equalsIgnoreCase(sTestStepData.trim())) {

						eDay.findElement(By.tagName("input")).click();
						sActualData = sDay;
						break;
					}
				}
				element.click();
				break;

			}

			if (sActualData.trim().equalsIgnoreCase(sTestStepData.trim())) {
				sActualResult = "Input value is correct";
			} else {
				sActualResult = "Input value is not correct:" + sActualData;

			}
		} catch (InvalidElementStateException error_message) {
			sActualResult = "Object is not editable";
			System.out.println("Exception:" + error_message.getMessage());
		} catch (Exception error_message) {
			sActualResult = error_message.getMessage();
			System.out.println("Exception:" + sActualResult);
		}

		ResultComparision();

	}

	// ________________________________Method to Click
	// Object____________________________________________________________
	public static void ClickObject() throws Exception {
		WebElement element = null;
		try {
			switch (sObjListObjectType.toUpperCase()) {
			case "WEBBUTTON":
				element = driver.findElement(getObjectLocator(sObjListObjectName, sObjListObjectLocator));
				Thread.sleep(1000);

				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", element);
				Thread.sleep(3000);

				sActualResult = "Clicked on object";

				break;

			case "WEBLINK":
				element = driver.findElement(getObjectLocator(sObjListObjectName, sObjListObjectLocator));
				Thread.sleep(100);
				element.click();
				sActualResult = "Clicked on object";
				break;

			case "CHECKBOX":
				element = driver.findElement(getObjectLocator(sObjListObjectName, sObjListObjectLocator));
				Thread.sleep(100);
				element.click();
				sActualResult = "Clicked on object";
				break;

			case "ALERT":

				Alert myalert = driver.switchTo().alert();
				if (sTestStepOperation.toUpperCase().equals("ACCEPT")) {
					myalert.accept();
				} else {
					myalert.dismiss();
				}
				sActualResult = "Clicked on object";
				break;

			case "WEBTABLE":

				switch (sTestStepOperation.toUpperCase()) {
				case "USLEGALQUEUE":

					String sAgreementTitle = "";
					String sContentDisplay = "False";
					Thread.sleep(2000);

					do {
						Thread.sleep(2000);
						WebElement WebTable = driver
								.findElement(getObjectLocator(sObjListObjectName, sObjListObjectLocator));

						List<WebElement> rows = WebTable.findElements(By.tagName("tr"));

						for (WebElement row : rows) {
							int col_cnt = 1;
							List<WebElement> cols = row.findElements(By.tagName("td"));
							for (WebElement col : cols) {
								System.out.println("Column text:" + col.getText());
								// Since agreement title is in 4th column,
								// initializing agreement tile value if col cnt
								// is 4
								if (col_cnt == 4) {
									sAgreementTitle = col.getText();

									break;
								}
								col_cnt++;
							}
							col_cnt = 1;
							if (sAgreementTitle.equalsIgnoreCase(sTestStepData)) {
								// System.out.println("Record found");
								for (WebElement col : cols) {
									if (col_cnt == 1) {
										System.out.println("Check the record");
										col.click();
										sContentDisplay = "true";
										System.out.println("Checked the record");
										Thread.sleep(1000);
										break;
									}
									col_cnt++;
								}
							}

						}

						// System.out.println("Content
						// DIsplay:"+sContentDisplay);
						if (sContentDisplay.equalsIgnoreCase("True")) {
							sActualResult = "Clicked on object";
							break;
						} else {
							sActualResult = "Details are not available";
							driver.findElement(By.linkText("Next")).click();
							System.out.println("Clicked on next button");
						}
					} while (sContentDisplay.equalsIgnoreCase("False"));
					break;

				}
				break;
			}

		} catch (Exception error_message) {
			sActualResult = error_message.getMessage();
			System.out.println("Exception:" + sActualResult);
		}

		ResultComparision();

	}

	// Method to Scroll up and down in a page//
	public static void Scroll() throws Exception {
		WebElement element = null;
		try {
			if (sTestStepObjectName.equalsIgnoreCase("Leads_GuidanceNotContacted")
					|| sTestStepObjectName.equalsIgnoreCase("Opportunities_GuidanceForSuccess")
					|| sTestStepObjectName.equalsIgnoreCase("Opportunities_ActiveChkbox")
					|| sTestStepObjectName.equalsIgnoreCase("Opportunities_StatusPending")
					|| sTestStepObjectName.equalsIgnoreCase("Opportunities_AmountCheck")
					|| sTestStepObjectName.equalsIgnoreCase("Campaigns_CampaignMembersClick")
					|| sTestStepObjectName.equalsIgnoreCase("Campaigns_AddNewRecords")) {
				switch (sTestStepOperation.toUpperCase()) {
				case "UP":
					Actions a = new Actions(driver);

					// scroll up a page
					a.sendKeys(Keys.PAGE_UP).build().perform();
					break;
				case "DOWN":
					Actions a1 = new Actions(driver);
					a1.sendKeys(Keys.PAGE_DOWN).build().perform();
					break;
				}
			}
			sActualResult = "scroll done successfully";

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultComparision();
	}

	// ________________________Method to upload//
	// file_____________________________________//

	public static void FileUpload() throws Exception {
		WebElement element = null;
		try {
			if (sTestStepObjectName.equalsIgnoreCase("Leads_UploadCsv")
					|| sTestStepObjectName.equalsIgnoreCase("Campaigns_UploadCsv")
					|| sTestStepObjectName.equalsIgnoreCase("Program_ChooseFileButton")) {
				WebElement uploadFile = driver.findElement(By.xpath("//form[@enctype='multipart/form-data']/input"));
				uploadFile.sendKeys("D:\\GWP\\SalesForce\\AutomationTesting\\Development\\wizard\\Utils\\LeadsImport.csv");
				Thread.sleep(1000);

				switch (sTestStepOperation.toUpperCase()) {
				case "UPLOAD":
					System.out.println("File uploaded successfully");
					break;
				case "CAMPAIGNUPLOAD":
					WebElement uploadFile1 = driver.findElement(By.xpath("//input[@name=\"file\"]"));
					uploadFile1
							.sendKeys("D:\\GWP\\SalesForce\\AutomationTesting\\Development\\wizard\\Utils\\Campaignfile.csv");
					Thread.sleep(1000);
					System.out.println("File uploaded successfully");
					break;
				case "PROGRAMSUPLOAD":
					WebElement uploadFile2 = driver.findElement(By.xpath("//input[@name=\"file\"]"));
					uploadFile2.sendKeys("D:\\GWP\\SalesForce\\AutomationTesting\\Development\\wizard\\Utils\\ProgramsSF.csv");
					Thread.sleep(1000);
					System.out.println("File uploaded successfully");
					break;
				}

				sActualResult = "File uploaded successfully";

			}
		} catch (

		Exception error_message) {
			sActualResult = error_message.getMessage();
		}

		ResultComparision();

	}

	// ________________________________Method to check object
	// existence____________________________________
	public static void CheckObjectExistence() throws Exception {
		WebElement element = null;
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			switch (sTestStepOperation.toUpperCase()) {
			case "OBJECT":

				wait.until(ExpectedConditions
						.visibilityOfElementLocated(getObjectLocator(sObjListObjectName, sObjListObjectLocator)));
				element = driver.findElement(getObjectLocator(sObjListObjectName, sObjListObjectLocator));
				if (element.isDisplayed() == true) {
					sActualResult = "Object exist";
				} else {
					sActualResult = "Object does not exist";
				}
				break;

			case "ALERT":

				wait.until(ExpectedConditions.alertIsPresent());
				sActualResult = "Object exist";
				break;
			case "CLICKABLE":
				wait.until(ExpectedConditions
						.elementToBeClickable(getObjectLocator(sObjListObjectName, sObjListObjectLocator)));
				element = driver.findElement(getObjectLocator(sObjListObjectName, sObjListObjectLocator));

			}
		} catch (Exception error_message) {
			sActualResult = error_message.getMessage();
		}

		ResultComparision();
	}

	// ---------------------------------Method to wait for
	// sometime------------------------------------------------------------
	public static void Wait() throws Exception {
		int iTime = Integer.parseInt(sTestStepData);
		try {
			if (sTestStepObjectName.equalsIgnoreCase("Leads_WebToLeadCLick")) {
				driver.navigate().refresh();
			} else
				Thread.sleep(iTime * 1000);
			sActualResult = "Waited for sometime";
		}

		catch (Exception e) {
			sActualResult = e.getMessage();
		}
		ResultComparision();

	}

	// method to input value
	protected static void InputValue() throws Exception {

		Organizations.InputValue();
	}

	// ________________________________Method to check object
	// property_________________________________________________
	public static void CheckObjectProperty() throws Exception {
		String sActualData = "";
		WebElement element = null;
		Thread.sleep(2000);
		try {

			if (sTestStepData.equalsIgnoreCase("Current Date")) {
				DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
				Calendar cal = Calendar.getInstance();
				sTestStepData = dateFormat.format(cal.getTime()).toString();
			}

			switch (sObjListObjectType.toUpperCase()) {
			case "TEXTFIELD":
				element = driver.findElement(getObjectLocator(sObjListObjectName, sObjListObjectLocator));
				switch (sTestStepOperation.toUpperCase()) {
				case "VALUE":
					sActualData = element.getAttribute("value");
					break;

				case "GETTEXT":
					sActualData = element.getText();
					break;
				case "ENABLE":
					if (element.isEnabled()) {
						sActualData = "True";
					} else {
						sActualData = "False";
					}
					break;
				}
				break;
			case "TEXTBOX":
				element = driver.findElement(getObjectLocator(sObjListObjectName, sObjListObjectLocator));
				switch (sTestStepOperation.toUpperCase()) {
				case "VALUE":
					sActualData = element.getAttribute("value");
					break;

				case "GETTEXT":
					sActualData = element.getText();
					break;
				case "ENABLE":
					if (element.isEnabled()) {
						sActualData = "True";
					} else {
						sActualData = "False";
					}
					break;
				}
				break;
			case "DATEINCIDENT":
				element = driver.findElement(getObjectLocator(sObjListObjectName, sObjListObjectLocator));
				switch (sTestStepOperation.toUpperCase()) {
				case "VALUE":
					sActualData = element.getAttribute("value");
					break;

				case "GETTEXT":
					sActualData = element.getText();
					break;
				case "ENABLE":
					if (element.isEnabled()) {
						sActualData = "True";
					} else {
						sActualData = "False";
					}
					break;
				}
				break;

			case "WEBBUTTON":
				element = driver.findElement(getObjectLocator(sObjListObjectName, sObjListObjectLocator));
				switch (sTestStepOperation.toUpperCase()) {
				case "VALUE":
					sActualData = element.getAttribute("value");
					break;

				case "GETTEXT":
					sActualData = element.getText();
					break;
				case "ENABLE":
					if (element.isEnabled()) {
						sActualData = "True";
					} else {
						sActualData = "False";
					}
					break;
				}
				break;

			case "DROPDOWN":
				element = driver.findElement(getObjectLocator(sObjListObjectName, sObjListObjectLocator));
				Select dropdown = new Select(element);
				switch (sTestStepOperation.toUpperCase()) {
				case "GETITEM":
					sActualData = dropdown.getFirstSelectedOption().getText();
					break;

				case "GETCOUNT":
					List<WebElement> sItems = dropdown.getOptions();
					sActualData = "" + sItems.size();
					break;
				case "ENABLE":
					if (element.isEnabled()) {
						sActualData = "True";
					} else {
						sActualData = "False";
					}
				}
				break;

			case "CHECKBOX":
				element = driver.findElement(getObjectLocator(sObjListObjectName, sObjListObjectLocator));
				switch (sTestStepOperation.toUpperCase()) {
				case "SELECTED":
					if (element.isSelected() == true) {
						sActualData = "Yes";
					} else {
						sActualData = "No";
					}

					break;

				case "VISIBLE":
					if (element.isDisplayed() == true) {
						sActualData = "Yes";
					} else {
						sActualData = "No";
					}

					break;

				}
				break;

			case "RADIOBUTTON":
				element = driver.findElement(getObjectLocator(sObjListObjectName, sObjListObjectLocator));
				switch (sTestStepOperation.toUpperCase()) {
				case "SELECTED":
					if (element.isSelected() == true) {
						sActualData = "Yes";
					} else {
						sActualData = "No";
					}

					break;

				case "VISIBLE":
					if (element.isDisplayed() == true) {
						sActualData = "Yes";
					} else {
						sActualData = "No";
					}

					break;

				}
				break;

			case "ALERT":

				Alert myalert = driver.switchTo().alert();
				switch (sTestStepOperation.toUpperCase()) {
				case "GETTEXT":
					sActualData = myalert.getText();
					break;
				}
				break;

			case "WEBLIST":
				element = driver.findElement(getObjectLocator(sObjListObjectName, sObjListObjectLocator));
				List<WebElement> Options = element.findElements(By.tagName("option"));
				switch (sTestStepOperation.toUpperCase()) {
				case "GETTEXT":
					for (WebElement value : Options) {
						// System.out.println("Value1:"+value.getText());
						if (value.getText().equalsIgnoreCase(sTestStepData)) {
							sActualData = value.getText();
							break;
						} else {
							sActualData = value.getText();
						}
					}
					break;

				}
				break;

			case "WEEKLYHOLIDAY":
				element = driver.findElement(getObjectLocator(sObjListObjectName, sObjListObjectLocator));
				switch (sTestStepOperation.toUpperCase()) {
				case "VALUE":
					sActualData = element.getAttribute("value");
					break;

				case "GETTEXT":
					sActualData = element.getText();
					break;
				case "TITLE":
					sActualData = element.getAttribute("title");
					break;
				}
				break;

			case "POPUP":
				element = driver.findElement(getObjectLocator(sObjListObjectName, sObjListObjectLocator));
				switch (sTestStepOperation.toUpperCase()) {
				case "VALUE":
					sActualData = element.getAttribute("value");
					break;

				case "GETTEXT":
					sActualData = element.getText();
					break;
				case "ENABLE":
					if (element.isEnabled()) {
						sActualData = "True";
					} else {
						sActualData = "False";
					}
					break;
				}
				break;

			}

			if (sActualData.equals("")) {
				sActualData = "NA";
			}
			System.out.println("sActualData:" + sActualData);
			System.out.println("sTestStepData:" + sTestStepData);
			if (sActualData.trim().equalsIgnoreCase(sTestStepData.trim())) {
				sActualResult = "property is correct";
			} else {
				sActualResult = "property is not correct:" + sActualData;
			}

		} catch (Exception error_message) {
			sActualResult = error_message.getMessage();
		}

		ResultComparision();

	}

	// ________________________________Method to switch
	// window/frame____________________________________

	public static void Switch() throws Exception {
		try {
			switch (sObjListObjectType.toUpperCase()) {
			case "WINDOW":
				if (sTestStepOperation.equalsIgnoreCase("CHILD")) {
					String sActualWindowTitle = "";
					sCurrentWindowHandle = driver.getWindowHandle();
					Thread.sleep(2000);
					ArrayList<String> windowHandles = new ArrayList<String>(driver.getWindowHandles());
					System.out.println("Number of windows:" + windowHandles.size());
					for (String window : windowHandles) {
						System.out.println("Window:" + window.toString());

						if (!(window.equals(sCurrentWindowHandle))) {

							sActualWindowTitle = driver.switchTo().window(window).getTitle();
							System.out.println("Title:" + sActualWindowTitle);
							if (sActualWindowTitle.trim().equalsIgnoreCase(sObjListObjectPath)) {
								driver.switchTo().window(window);

								driver.manage().window().maximize();
								sActualResult = "Switched to new window";
								Thread.sleep(1000);
								break;
							} else {
								sActualResult = "Available Window:" + sActualWindowTitle;
								driver.switchTo().window(sCurrentWindowHandle);
							}

						} else {
							sActualResult = "New window not available";
						}
					}
				} else if (sTestStepOperation.equalsIgnoreCase("Parent")) {
					driver.switchTo().window(sParentWindowHandle);
					sActualResult = "Switched to parent window";
				} else {
					driver.switchTo().window(sCurrentWindowHandle);
					sActualResult = "Switched to parent window";
				}

				break;

			case "FRAME":
				if (sTestStepObjectName.equalsIgnoreCase("Leads_WebToLeadFrame")) {
					WebElement frame1 = driver.findElement(By.xpath("(//iframe)[1]"));
					driver.switchTo().frame(frame1);
				} else if (sTestStepObjectName.equalsIgnoreCase("Leads_WebToLeadFrame2")) {
					WebElement frame2 = driver.findElement(By.xpath("(//iframe)[1]"));
					driver.switchTo().frame(frame2);
				} else if (sTestStepObjectName.equalsIgnoreCase("Campaigns_Frame1")) {
					WebElement frame3 = driver.findElement(By.xpath("(//iframe)[1]"));
					driver.switchTo().frame(frame3);
				} else if (sTestStepObjectName.equalsIgnoreCase("Programs_Frames1")) {
					WebElement frame4 = driver.findElement(By.xpath("(//iframe)[1]"));
					driver.switchTo().frame(frame4);
				}

				else
					driver.switchTo().frame(sObjListObjectPath.trim());
				Thread.sleep(500);

				sActualResult = "Switched frame";
				break;

			case "DEFAULTCONTENT":

				driver.switchTo().defaultContent();
				sActualResult = "Switched default content";
				break;
			}
		} catch (Exception error_message) {
			sActualResult = error_message.getMessage();
		}

		ResultComparision();
	}

	// ______________________________________Method to close
	// application__________________________________________________
	protected static void CloseApplication() {
		try {
			driver.close();
			driver.quit();
			sActualResult = "Application closed";
		} catch (Exception error_message) {
			sActualResult = error_message.getMessage();
		}

		ResultComparision();
	}

	// -----------------------------------Method to perform
	// actions------------------------------------------------------

	public static void Actions() throws Exception {
		try {
			WebElement element = driver.findElement(getObjectLocator(sObjListObjectName, sObjListObjectLocator));

			Actions action = new Actions(driver);
			System.out.println(sTestStepOperation);

			switch (sTestStepOperation.toUpperCase()) {
			case "RIGHTCLICK":

				action.moveToElement(element);
				Thread.sleep(1000);
				action.contextClick(element).build().perform();
				break;

			case "MOUSEHOVER":
				System.out.println("inside mousehover");
				action.moveToElement(element);
				action.build().perform();
				Thread.sleep(1000);
				break;

			case "CLICK":

				Thread.sleep(1000);
				action.click().perform();
				break;

			case "DOUBLECLICK":
				action.moveToElement(element);
				Thread.sleep(1000);
				action.doubleClick(element).build().perform();
				break;

			}

			sActualResult = "Action Performed";

		} catch (Exception error_message) {
			sActualResult = error_message.getMessage();
		}
		System.out.println(sActualResult);

		ResultComparision();
	}

	// Method to open html file

	public static void OpenFile() throws Exception {
		try {
			WebElement element = driver.findElement(getObjectLocator(sObjListObjectName, sObjListObjectLocator));
			element.click();

			switch (sTestStepOperation.toUpperCase()) {
			case "OPENFILE":
				driver.get("D:\\SalesforceAutomation\\Automation\\FileUpload\\web.html");
				driver.manage().window().maximize();

				Thread.sleep(8000);
				String parentWindow1 = driver.getWindowHandle();
				System.out.println(parentWindow1);

				Thread.sleep(3000);
				driver.switchTo().window(parentWindow1);
				break;

			}

			sActualResult = "File opened successfully";

		} catch (Exception error_message) {
			sActualResult = error_message.getMessage();
		}
		System.out.println(sActualResult);

		ResultComparision();
	}

	// ----------------------------------Method to pass
	// keys------------------------------------------------------------------
	public static void KeyEvent() throws Exception {
		try {
			switch (sTestStepData.toUpperCase()) {

			case "DOWN":

				driver.findElement(getObjectLocator(sObjListObjectName, sObjListObjectLocator)).sendKeys(Keys.DOWN);
				break;

			case "TAB":

				driver.findElement(getObjectLocator(sObjListObjectName, sObjListObjectLocator)).sendKeys(Keys.TAB);
				break;

			case "ENTER":

				Robot robot1 = new Robot();
				robot1.keyPress(KeyEvent.VK_ENTER);
				robot1.keyRelease(KeyEvent.VK_ENTER);
				break;
			// driver.findElement(getObjectLocator(sObjListObjectName,sObjListObjectLocator)).sendKeys(Keys.ENTER);

			case "PAGEDOWN":

				// driver.findElement(getObjectLocator(sObjListObjectName,sObjListObjectLocator)).sendKeys(Keys.PAGE_DOWN);
				Robot robot2 = new Robot();
				robot2.keyPress(KeyEvent.VK_PAGE_DOWN);
				break;

			case "PAGEUP":

				Robot robot3 = new Robot();
				robot3.keyPress(KeyEvent.VK_PAGE_UP);
				// driver.findElement(getObjectLocator(sObjListObjectName,sObjListObjectLocator)).sendKeys(Keys.PAGE_UP);
				break;

			}
			sActualResult = "Key event performed";
		}

		catch (Exception error_message) {
			sActualResult = error_message.getMessage();
		}
		ResultComparision();

	}

	// --------------------------------------Method to Validate
	// webtables-----------------
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void WebTable() throws Exception {
		WebElement element = null;
		try {
			int iRow;
			int iRowCnt = 0;
			String sCellValue = "";

			String[] sOperation = sTestStepOperation.split(";");
			String operation = sOperation[0];
			String sActivity = sOperation[1];
			String sCSSValue = "";
			boolean bWebTableStatus = false;

			element = driver.findElement(getObjectLocator(sObjListObjectName, sObjListObjectLocator));
			do {
				Thread.sleep(4000);

				// To locate rows of table.
				List<WebElement> rows_table = element.findElements(By.tagName("tr"));
				// To calculate no of rows In table.
				iRowCnt = rows_table.size();
				// Loop will execute till the last row of table.
				for (iRow = 1; iRow < iRowCnt; iRow++) {

					// To locate columns(cells) of that specific row.
					List<WebElement> Columns_row = rows_table.get(iRow).findElements(By.tagName("td"));
					ArrayList sActualColValue = new ArrayList();
					System.out.println("Actual Number of cols:" + Columns_row.size());
					for (WebElement eCol : Columns_row) {

						sCSSValue = eCol.getCssValue("display");
						if (!(sCSSValue.equalsIgnoreCase("none"))) {

							sCellValue = eCol.getText();
							// System.out.println("Cellvalue:"+sCellValue);
							if ((!(sCellValue.equals("")))) {
								if (!(sCellValue.equals(null))) {
									sActualColValue.add(sCellValue);
								}

							}
						}
					}

					boolean bstatus = false;

					String[] sColValue = sTestStepData.split(";");
					System.out.println("ExpecLength:" + sColValue.length);
					System.out.println("ActualLength:" + sActualColValue.size());
					if (sColValue.length == sActualColValue.size()) {
						for (int cell = 0; cell < sColValue.length; cell++) {
							// System.out.println("Iteration:"+cell);
							String cellvalue = sColValue[cell];
							String sArrayValue = sActualColValue.get(cell).toString();
							// System.out.println("Before cellvalue:"+cellvalue);
							/*
							 * if(sArrayValue.equalsIgnoreCase("")) { sArrayValue="NA"; } else
							 * if(sArrayValue.equals(null)) { sArrayValue="NA"; }
							 */
							if (cellvalue.equalsIgnoreCase("NA")) {
								cellvalue = "";
							}
							if (cellvalue.equalsIgnoreCase("Current Date")) {
								DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
								Calendar cal = Calendar.getInstance();
								cellvalue = dateFormat.format(cal.getTime()).toString();
								System.out.println("cellvalue:" + cellvalue);
							}
							// System.out.println("After cellvalue:"+cellvalue);
							if ((sArrayValue.trim().equalsIgnoreCase(cellvalue.trim()))) {

								bstatus = true;
							} else {

								bstatus = false;
								break;
							}
						}
						if (bstatus == true) {
							bWebTableStatus = true;

							switch (operation.toUpperCase()) {
							case "EDIT":

								WebElement Edit = rows_table.get(iRow);
								sTestStepObjectName = sActivity;
								ObjectMap.GetObjectMap();
								Edit.click();
								rows_table.get(iRow)
										.findElement(getObjectLocator(sObjListObjectName, sObjListObjectLocator))
										.click();
								Thread.sleep(5000);
								break;

							case "DELETE":
								WebElement Delete = rows_table.get(iRow);
								sTestStepObjectName = sActivity;
								ObjectMap.GetObjectMap();
								Delete.click();
								rows_table.get(iRow)
										.findElement(getObjectLocator(sObjListObjectName, sObjListObjectLocator))
										.click();
								Thread.sleep(5000);
								break;

							case "VIEW":
								WebElement View = rows_table.get(iRow);
								sTestStepObjectName = sActivity;
								ObjectMap.GetObjectMap();
								View.click();
								rows_table.get(iRow)
										.findElement(getObjectLocator(sObjListObjectName, sObjListObjectLocator))
										.click();
								Thread.sleep(5000);
								break;
							case "CHECK":
								rows_table.get(iRow).click();
								break;
							}
							break;
						} else {

							bWebTableStatus = false;
						}
					} else {
						System.out.println("Column Length Doesn't Matched");
						sActualResult = "Column Length Doesn't Matched";
						bWebTableStatus = false;
						break;
					}

				}
				if (bWebTableStatus == false)

				{
					if ((sActualResult.equalsIgnoreCase("Column Length Doesn't Matched"))) {
						sActualResult = "Record does not exist";
						break;
					} else {
						sTestStepObjectName = "Webtable_Nextbutton";
						ObjectMap.GetObjectMap();
						String sAttrvalue = driver
								.findElement(getObjectLocator(sObjListObjectName, sObjListObjectLocator))
								.getAttribute("class");
						if (!(sAttrvalue.contains("disabled"))) {

							driver.findElement(getObjectLocator(sObjListObjectName, sObjListObjectLocator)).click();

							System.out.println("Clicked on next button");
						}
						sActualResult = "Record does not exist";
					}

				} else {
					sActualResult = "Webtable Validated Successfully";
					break;
				}

			}

			while (bWebTableStatus == false);
		}

		catch (NoSuchElementException error_message) {
			sActualResult = "Record does not exist";
			System.out.println("Exception:" + error_message);
		} catch (Exception error_message) {
			sActualResult = error_message.getMessage();
			System.out.println("Exception:" + sActualResult);
		}

		ResultComparision();
	}

	// Method to create leads in SalesForce
	public static void CreateLeads() throws Exception {
		WebElement element = null;
		try {
			element = driver.findElement(getObjectLocator(sObjListObjectName, sObjListObjectLocator));

			if (sTestStepObjectName.equals("Leads_CreateLeads")) {
				WebElement importButton = driver.findElement(By.xpath("//a[@title='Import']"));
				Thread.sleep(2000);
				JavascriptExecutor jsImportButton = (JavascriptExecutor) driver;
				jsImportButton.executeScript("arguments[0].click()", importButton);
				Thread.sleep(3000);

				WebElement frame1 = driver.findElement(By.xpath("(//iframe)[1]"));
				driver.switchTo().frame(frame1);
				Thread.sleep(15000);

				WebElement leadsButton = driver
						.findElement(By.xpath("(//table[@class='list-view list-view-links']/tbody/tr)[2]/td/a"));
				JavascriptExecutor jsLeadsButton = (JavascriptExecutor) driver;
				jsLeadsButton.executeScript("arguments[0].click()", leadsButton);

				Thread.sleep(2000);
				WebElement AddNewRecordButton = driver.findElement(By.xpath("//a[text()='Add new records']"));
				JavascriptExecutor jsAddNewRecordButton = (JavascriptExecutor) driver;
				jsAddNewRecordButton.executeScript("arguments[0].click()", AddNewRecordButton);
				Thread.sleep(2000);
				switch (sTestStepOperation.toUpperCase()) {

				case "CREATE":

					System.out.println("Created leads");

					break;
				}

				sActualResult = "Leads created Successfully";
			}
		} catch (Exception e) {
			sActualResult = e.getMessage();
		}
		ResultComparision();

	}

	// Method to search Modules in SalesForce//---------------------------

	public static void SearchModule() throws Exception {
		WebElement element = null;
		try {
			element = driver.findElement(getObjectLocator(sObjListObjectName, sObjListObjectLocator));
			if (sTestStepObjectName.equalsIgnoreCase("Opportunities_SearchOpportunities")
					|| sTestStepObjectName.equalsIgnoreCase("Contacts_SeacrhContacts")
					|| sTestStepObjectName.equalsIgnoreCase("Organization_SeacrhOrganization")
					|| sTestStepObjectName.equalsIgnoreCase("Leads_MenuButton")
					|| sTestStepObjectName.equalsIgnoreCase("Campaigns_MenuButton")
					|| sTestStepObjectName.equalsIgnoreCase("Programs_MenuButton")) {
				WebElement menuButton = driver.findElement(
						By.xpath("//div[@role='navigation']/child::one-app-launcher-header/child::button"));
				menuButton.click();
				Thread.sleep(8000);
				WebElement searchBox = driver
						.findElement(By.xpath("//input[@class='slds-input'][@part='input'][@type='search']"));
				searchBox.sendKeys(sTestStepData);
				Thread.sleep(4000);

				switch (sTestStepOperation.toUpperCase()) {

				case "SEARCHCONTACTS":

					WebElement ContactValue = driver.findElement(By.xpath("//div[@class=\"slds-size_small\"]"));
					Thread.sleep(2000);
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].click()", ContactValue);
					System.out.println("Searched Contacts");

					break;
				case "SEARCHOPPORTUNITIES":
					WebElement Opportunities = driver.findElement(By.xpath("//div[@class=\"slds-size_small\"]"));
					Thread.sleep(2000);
					JavascriptExecutor js1 = (JavascriptExecutor) driver;
					js1.executeScript("arguments[0].click()", Opportunities);
					System.out.println("Searched Opportunities");

					break;
				case "SEARCHORGANIZATIONS":
					WebElement Organization = driver.findElement(By.xpath("//div[@class=\"slds-size_small\"]"));
					Thread.sleep(2000);
					JavascriptExecutor js2 = (JavascriptExecutor) driver;
					js2.executeScript("arguments[0].click()", Organization);
					System.out.println("Searched Organization");

					break;
				case "SEARCHLEADS":
					WebElement Leads = driver.findElement(By.xpath("//div[@class=\"slds-size_small\"]"));
					Thread.sleep(2000);
					JavascriptExecutor j = (JavascriptExecutor) driver;
					j.executeScript("arguments[0].click()", Leads);
					System.out.println("Searched Leads");

					break;
				case "SEARCHCAMPAIGNS":
					WebElement Campaignvalue = driver.findElement(
							By.xpath("(//a[@class='al-menu-item slds-p-vertical--xxx-small']/child::div)[1]"));
					Thread.sleep(2000);
					JavascriptExecutor j1 = (JavascriptExecutor) driver;
					j1.executeScript("arguments[0].click()", Campaignvalue);
					System.out.println("Searched Campaigns");
					break;
				case "SEARCHPROGRAMS":
					WebElement ProgramValue = driver.findElement(By.xpath("//div[@class=\"slds-size_small\"]"));
					Thread.sleep(2000);
					JavascriptExecutor js4 = (JavascriptExecutor) driver;
					js4.executeScript("arguments[0].click()", ProgramValue);
					System.out.println("Searched Programs");
					break;

				}

				sActualResult = "Module Searched Successfully";
			}
		} catch (Exception e) {
			sActualResult = e.getMessage();
		}
		ResultComparision();

	}

	// _____________________________________Method to check file existence for
	// ASIS______________________________________
	public static void CheckFileExistence() {
		try {
			Thread.sleep(10000);
			String sFileName = sTestStepData;
			String sDate;
			DateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy");
			Calendar cal = Calendar.getInstance();
			sDate = dateFormat.format(cal.getTime()).toString();
			sFileName = sFileName + "_" + sDate;
			switch (sTestStepOperation.toUpperCase()) {
			case "XLS":
				sFileName = sFileName + ".xls";
				break;
			case "PDF":
				sFileName = sFileName + ".pdf";
				break;
			case "CSV":
				sFileName = sFileName + ".csv";
				break;
			}
			System.out.println("File:C://Users//mohammadfaheem.s//Downloads//" + sFileName);
			File file = new File("C://Users//mohammadfaheem.s//Downloads//" + sFileName);
			if (file.exists()) {
				sActualResult = "File exist";

				file.delete();
			} else {
				sActualResult = "File does not exist:" + sFileName;
			}
		} catch (Exception error_message) {
			sActualResult = error_message.getMessage();
			System.out.println(sActualResult);
		}
		ResultComparision();
	}
	// _____________________________________Method to check file existence for
	// ASIS______________________________________
	// public static void WorkingCalendar() throws Exception
	// {
	// WorkingCalendar.workingcalender();
	// }

	// --------------------------------------Method to read Object
	// type----------------------------------------------------
	protected static By getObjectLocator(String objectName, String objectType) throws Exception {
		// Find the object by xpath
		if (objectType.equalsIgnoreCase("XPATH")) {
			// String property = OR.getProperty(objectName);
			// System.out.println("Object property:"+property);
			return By.xpath(sObjListObjectPath);
		}
		// Find the object by class
		else if (objectType.equalsIgnoreCase("CLASSNAME")) {

			return By.className(sObjListObjectPath);

		}
		// Find the object by name
		else if (objectType.equalsIgnoreCase("NAME")) {

			return By.name(sObjListObjectPath);

		}

		// Find the object by css
		else if (objectType.equalsIgnoreCase("CSS")) {

			// System.out.println("Object property:"+property);
			return By.cssSelector(sObjListObjectPath);

		}
		// Find the object by link
		else if (objectType.equalsIgnoreCase("LINK")) {

			return By.linkText(sObjListObjectPath);

		}

		// Find by linktext
		else if (objectType.equalsIgnoreCase("LINKTEXT")) {

			return By.linkText(sTestStepData);

		}
		// Fine the object by tagname
		else if (objectType.equalsIgnoreCase("TAGNAME")) {
			return By.tagName(sObjListObjectPath);
		}
		// Find the object by partial link Text
		else if (objectType.equalsIgnoreCase("PARTIALLINK")) {
			return By.partialLinkText(sObjListObjectPath);

		}

		else if (objectType.equalsIgnoreCase("ID")) {

			return By.id(sObjListObjectPath);

		} else

		{
			throw new Exception("Wrong object type");
		}
	}

}
