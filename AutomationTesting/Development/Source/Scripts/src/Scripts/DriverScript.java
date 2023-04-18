package Scripts;



import javax.swing.JFrame;
import javax.swing.JOptionPane;



import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class DriverScript extends GenericSkins {
	@BeforeTest
	@Parameters({ "browser", "NodeURL", "NodeName", "RegPackName" })
	public void remote(@Optional("Chrome") String browser, @Optional("NA") String NodeURL,
			@Optional("Log") String NodeName, @Optional("Null") String RegPackName) {
		sNodeBrowser = browser;
		sNodeURL = NodeURL;
		sNodeName = NodeName;
		sNodeRegPackName = RegPackName;

	}

	@Test
	public void main() throws Exception {

		// Initialization of project location
		JFrame frame = new JFrame("InputDialog");
		// prompt the user to enter their project location
		sProjectPath = JOptionPane.showInputDialog(frame, "Enter Automation Test Suite Path:",
				"D://GWP//SalesForce//AutomationTesting//Development");

		// Initialization of TestSuite folders
		InitializeTestFolderPaths();
		InitializeRegPackColumns();
		InitializeTestCaseColumns();
		InitializeTestStepsColumns();
		InitializeObjectListColumns();

		// Set SystemIndpendencyConfig File values
		LoadSystemIndependencyConfig();

		if (sRunReferenceFlag.equalsIgnoreCase("Yes")) {
			// Create CAT Run History DB
			TestReportDB.CreateDB();

			// Create tables in DB
			TestReportDB.CreateTables();

			// Insert Row in automation projects table
			TestReportDB.QueryAutomationProjectTable();
			// Insert Row in RunReference history table
			TestReportDB.QueryRunReferenceHistoryTable();

		}

		// Create Test Results Folder
		sTestResultsPath = createfolder(sProjectPath, "TestResults");
		// Create current date and time log folder inside test results folder
		CreateTestResultsFolder();

		// Copy Driver file from test data folder to current log folder
		Copy_File(sTestDataPath + sDriverFile, sTestResultsPath);

		// Create a folder 'ObjectRepository inside current log folder
		sTestResultsPathOR = createfolder(sTestResultsPath, "ZObjectRepository");

		// Create a folder 'TestLog' inside current log folder
		sPathTestLog = createfolder(sTestResultsPath, "TestLog");

		// Copy ObjectList file to current log folder
		Copy_File(sPathOR + "ObjectList.xlsx", sTestResultsPathOR);

		// Copy Reports source folder into TestREsults
		Copy_Folder(sProjectPath + "Wizard//Reports", sTestResultsPath + "Reports");

		// Create screenshot folder
		sScreenShotFolder = createfolder(sTestResultsPath + "Reports//", "ScreenShots");

		// Set Driver File
		TestDataImport.SetExcelFile(sTestResultsPath, sDriverFile);

		// Get the number of regression packs from sheet "RegressionPack"
		iTotalRegressionPacks = TestDataImport.GetRowCount(sSheetRegressionPacks) - 1;

		// Add summary columns in Regression packs sheet
		TestReportFunctions.AddColumnsInRegPacksSheet();

		TestDataImport.SetExcelFile(sTestResultsPath, sDriverFile);
		// Read the regression pack names along with run mode
		// Read the object list
				 ObjectMap.getobjectlist();

		for (int iRowRegPack = 1; iRowRegPack <= iTotalRegressionPacks; iRowRegPack++) {
			SetRegPackInitialValues();

			sRegPackID = TestDataImport.GetCellData(sSheetRegressionPacks, iColRegID, iRowRegPack);
			sRegPackName = TestDataImport.GetCellData(sSheetRegressionPacks, iColRegName, iRowRegPack);
			sRegPackRunMode = TestDataImport.GetCellData(sSheetRegressionPacks, iColRegRunStatus, iRowRegPack);
			sRegPackDesc = TestDataImport.GetCellData(sSheetRegressionPacks, iColRegDesc, iRowRegPack);

			// Check the condition if the run mode of RegPack is yes or no
			if (sRegPackRunMode.equalsIgnoreCase("Yes")) {
				if (sRunReferenceFlag.equalsIgnoreCase("Yes")) {

					// Insert Row in RegressionPack history table
					TestReportDB.QueryRegressionPacksHistory();
				}

				// Add columns in RegPack TC sheet in driver file
				TestReportFunctions.AddColumnsInRegPackTCSheet();

				// Read the number of test cases from the RegPack id sheet
				iRegPackTestCases = TestDataImport.GetRowCount(sRegPackID) - 1;
				// sRegPackStartTime = GetCurrentTime();

				// Create screenshot folder
				sScreenShotRegPackFolder = createfolder(sScreenShotFolder, sRegPackName);
				// Create RegPack folder inside TestLog
				sPathRegPackLog = createfolder(sPathTestLog, sRegPackName);

				for (int iRowRegPackTestCase = 1; iRowRegPackTestCase <= iRegPackTestCases; iRowRegPackTestCase++) {
					SetTestCaseInitialValues();
					sRegPackTestCaseID = TestDataImport.GetCellData(sRegPackID, iColRegPackTCID, iRowRegPackTestCase);
					sRegPackTestCaseDesc = TestDataImport.GetCellData(sRegPackID, iColRegPackTCDesc,
							iRowRegPackTestCase);
					sRegPackTestCaseRunMode = TestDataImport.GetCellData(sRegPackID, iColRegPackTCRunMode,
							iRowRegPackTestCase);

					// check the condition if the test case run mode is yes or no
					if (sRegPackTestCaseRunMode.equalsIgnoreCase("Yes")) {
						// Insert Automation Test History
						if (sRunReferenceFlag.equalsIgnoreCase("Yes")) {

							// Insert Row in Automation Test history table
							TestReportDB.QueryAutomationTestsHistory();
						}

						// Copy RegPack file from test data folder to current log folder
						Copy_File(sTestDataPath + sRegPackName + ".xlsx", sTestResultsPath);

						// set the excel file named as RegPack Name
						TestDataImport.SetExcelFile(sTestResultsPath, sRegPackName + ".xlsx");

						// Create summary SHeet
						TestReportFunctions.CreateSummarySheet(sTestResultsPath + sRegPackName + ".xlsx");

						// Create screenshot folder
						sScreenShotTCFolder = createfolder(sScreenShotRegPackFolder, sRegPackTestCaseID);

						// Read the test steps from the sheet named as per TestCaseID
						iTotalTestSteps = TestDataImport.GetRowCount(sRegPackTestCaseID) - 1;
						sTCStartTime = GetCurrentTime();

						// Create a log file with TestCaseID inside TestLog folder
						CreateFile(sPathRegPackLog, sRegPackTestCaseID + ".txt");

						WriteFile(sPathRegPackLog, sRegPackTestCaseID + ".txt",
								"************************************************************************************************");
						WriteFile(sPathRegPackLog, sRegPackTestCaseID + ".txt",
								"\t\t\t\t\t\t" + sRegPackName + " || " + sRegPackTestCaseID);
						WriteFile(sPathRegPackLog, sRegPackTestCaseID + ".txt",
								"************************************************************************************************");

						for (int iRowTestStep = 1; iRowTestStep <= iTotalTestSteps; iRowTestStep++) {
							// Set test steps initial values
							SetTestStepInitialValues();

							sTestStepID = TestDataImport.GetCellData(sRegPackTestCaseID, iColTestStepID, iRowTestStep);
							sTestStepDesc = TestDataImport.GetCellData(sRegPackTestCaseID, iColTestStepDesc,
									iRowTestStep);
							sTestStepValidation = TestDataImport.GetCellData(sRegPackTestCaseID, iColTestStepValidation,
									iRowTestStep);
							sTestStepObjectName = TestDataImport.GetCellData(sRegPackTestCaseID, iColTestStepObjectName,
									iRowTestStep);
							sTestStepOperation = TestDataImport.GetCellData(sRegPackTestCaseID, iColTestStepOperation,
									iRowTestStep);
							sTestStepData = TestDataImport.GetCellData(sRegPackTestCaseID, iColTestStepData,
									iRowTestStep);
							sExpectedResult = TestDataImport.GetCellData(sRegPackTestCaseID, iColExpectedResult,
									iRowTestStep);

							System.out.println(
									"______________" + sRegPackTestCaseID + "||" + sTestStepID + "____________");
							System.out.println("sTestStepValidation:" + sTestStepValidation);
							System.out.println("sTestStepObjectName:" + sTestStepObjectName);
							System.out.println("sTestStepData:" + sTestStepData);

							WriteFile(sPathRegPackLog, sRegPackTestCaseID + ".txt",
									"______________" + sTestStepID + "____________");
							WriteFile(sPathRegPackLog, sRegPackTestCaseID + ".txt",
									"TestStepValidation:" + sTestStepValidation);
							WriteFile(sPathRegPackLog, sRegPackTestCaseID + ".txt",
									"TestStepObjectName:" + sTestStepObjectName);
							WriteFile(sPathRegPackLog, sRegPackTestCaseID + ".txt", "TestStepData:" + sTestStepData);
							WriteFile(sPathRegPackLog, sRegPackTestCaseID + ".txt",
									"ExpecetedResult:" + sExpectedResult);

							// Call function GetObject to retrieve object type and property value
							if (!(sTestStepObjectName.equalsIgnoreCase("NA"))) {
								ObjectMap.GetObjectMap();
							}

							// Call the function TestStepDefinition to perform test step action
							TestStepDefinitions.TestStepDefinition();
							System.out.println("sTestStepStatus:" + sTestStepStatus);
							WriteFile(sPathRegPackLog, sRegPackTestCaseID + ".txt", "ActualResult:" + sActualResult);
							WriteFile(sPathRegPackLog, sRegPackTestCaseID + ".txt",
									"TestStepStatus:" + sTestStepStatus);

							// Set the test step actual result and status in Excel sheet
							TestReportFunctions.SetTestStepsReport(iRowTestStep);

							// Add test step details in array lists
							SetTestStepsArrayListValues();

							// Update TestStep History
							if (sRunReferenceFlag.equalsIgnoreCase("Yes")) {
								// Insert Row in RegressionPack history table
								TestReportDB.QueryTestStepsHistory();
							}
						}

						sTCEndTime = GetCurrentTime();
						sTCDuration = GetDateTimeDiff(sTCStartTime, sTCEndTime);
						SetTestCaseStatus();
						TestReportFunctions.SetSummarySheet(sTestResultsPath + sRegPackName + ".xlsx", "Summary",
								iRowRegPackTestCase);
						TestReportFunctions.SetTestCaseReport(iRowRegPackTestCase);

						WriteFile(sPathRegPackLog, sRegPackTestCaseID + ".txt",
								"************************************************************************************************");
						WriteFile(sPathRegPackLog, sRegPackTestCaseID + ".txt",
								"\t\t\t\t\t\t" + SRegPackTestCaseStatus.toUpperCase());
						WriteFile(sPathRegPackLog, sRegPackTestCaseID + ".txt",
								"************************************************************************************************");

						// update Automation Test History
						if (sRunReferenceFlag.equalsIgnoreCase("Yes")) {
							// Insert Row in RegressionPack history table
							TestReportDB.QueryAutomationTestsHistory();
						}
					} else {
						TestReportFunctions.SetTestCaseReport(iRowRegPackTestCase);
					}
					// Add test case details in array lists
					SetRegPackTCArrayListValues();

				}

				SetRegPackArrayListValues();

				// Update Regression Pack History
				if (sRunReferenceFlag.equalsIgnoreCase("Yes")) {

					// Insert Row in RegressionPack history table
					TestReportDB.QueryRegressionPacksHistory();
				}

			}

			TestDataImport.SetExcelFile(sTestResultsPath, sDriverFile);

			TestReportFunctions.SetRegPacksReport(iRowRegPack);
		}

		// Update Run Reference History
		if (sRunReferenceFlag.equalsIgnoreCase("Yes")) {
			TestReportDB.QueryRunReferenceHistoryTable();
			TestReportDB.CloseDBConnection();
		}

		// Call the function to create js files providing the execution details in HTML
		// report
		// TestReportFunctions.CreateChartReportsJs(""+iOverallTestCases,""+iOverallTCExecuted,""+iOverallTCPassed,""+iOverallICFailed,""+(iOverallTestCases-iOverallTCExecuted),sOverallExecutionStartTime,sOverallExecutionStopTime,sOverallExecutionDuration);
		// TestReportFunctions.CreateExecutionReportJs();
		Thread.sleep(5000);
		// Email notification
		// if(sRunReferenceFlag.equalsIgnoreCase("Yes"))
		// {
		// EmailNotifications.SendNotification();
		// }
		// Execute the HTML file from test results
		// File htmlFile = new File(sTestResultsPath+"//Reports//main.html");
		// Desktop.getDesktop().browse(htmlFile.toURI());

	}

	@AfterTest
	public void closeApplication()
	{
		TestStepActions.CloseApplication();
	}
	

}
