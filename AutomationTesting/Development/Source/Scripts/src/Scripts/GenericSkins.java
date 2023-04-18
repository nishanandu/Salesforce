package Scripts;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@SuppressWarnings("rawtypes")
public class GenericSkins {
	// Declaration of Global Variables
	protected static String sProjectPath;
	protected static String sTestDataPath;
	protected static String sTestResultsPath;
	protected static String sPathOR;
	protected static String sTestResultsPathOR;
	protected static String sSystemIndependencyConfig;
	protected static String sLogTestResultsPath;
	protected static String sCurrentDateFolder;
	protected static String sScreenShotFolder;
	protected static String sScreenShotRegPackFolder;
	protected static String sScreenShotTCFolder;
	protected static String sDriverFile;
	protected static String sSheetRegressionPacks;
	protected static int iTotalRegressionPacks;

	protected static String sRegPackID;
	protected static String sRegPackName;
	protected static String sRegPackDesc;
	protected static String sRegPackRunMode;
	protected static String sFileTestData;
	protected static String sAUTPath;
	protected static String sPathUtils;
	protected static String sProjectName;
	protected static String sRunReference;
	protected static String sRunReferenceFlag;

	protected static int iColRegID;
	protected static int iColRegName;
	protected static int iColRegDesc;
	protected static int iColRegRunStatus;
	protected static int iColRegpackTestCasesExecuted;
	protected static int iColRegpackTestCasesPassed;
	protected static int iColRegpackTestCasesFailed;
	protected static int iColRegpackFailedTestCases;
	protected static int iColRegpackExecutionDurion;

	protected static int iColRegPackTCID;
	protected static int iColRegPackTCStatus;
	protected static int iColRegPackTCDesc;
	protected static int iColRegPackTCRunMode;
	protected static int iColRegPackTCTotalStepsExec;
	protected static int iColRegPackTCTotalStepsPassed;
	protected static int iColRegPackTCTotalStepsFailed;
	protected static int iColRegPackTCExecTime;

	protected static int iRegPackTestCases;

	protected static String sRegPackTestCaseID;
	protected static String sRegPackTestCaseDesc;
	protected static String sRegPackTestCaseRunMode;
	protected static String SRegPackTestCaseStatus;

	protected static int iTotalTestSteps;

	protected static int iColTestStepID;
	protected static int iColTestStepDesc;
	protected static int iColTestStepValidation;
	protected static int iColTestStepObjectName;
	protected static int iColTestStepOperation;
	protected static int iColTestStepData;
	protected static int iColExpectedResult;
	protected static int iColAtualResult;
	protected static int iColTestStepStatus;
	protected static int iColScreenShot;

	protected static int iColSummaryTestCaseID;
	protected static int iColSummaryTotalTestSteps;
	protected static int iColSummaryPassed;
	protected static int iColSummaryFailed;
	protected static int iColSummaryTestCaseStatus;
	protected static int iColSummaryTestCaseDuration;

	protected static String sTestStepID;
	protected static String sTestStepDesc;
	protected static String sTestStepValidation;
	protected static String sTestStepObjectName;
	protected static String sTestStepOperation;
	protected static String sTestStepData;
	protected static String sExpectedResult;
	protected static String sActualResult;
	protected static String sTestStepStatus;
	protected static String sScreeenShot;

	protected static String sBrowsserDriverPath;
	protected static String sObjectType;

	protected static String sObjListObjectName;
	protected static String sObjListObjectType;
	protected static String sObjListObjectLocator;
	protected static String sObjListObjectPath;

	protected static int iColObjListObjectName;
	protected static int iColObjListObjectType;
	protected static int iColObjListObjectLocator;
	protected static int iColObjListObjectPath;

	protected static int iTotalTestStepsExecuted;
	protected static int iTotalTestStepsPassed;
	protected static int iTotalTestStepsFailed;

	protected static int iTotalTestCasesExecuted;
	protected static int iTotalTestCasesPassed;
	protected static int iTotalTestCasesFailed;
	protected static int iTotalTestCasesNoRun;
	protected static long iRegpackDuration;

	protected static Integer iOverallTestCases;
	protected static Integer iOverallTCExecuted;
	protected static Integer iOverallTCPassed;
	protected static Integer iOverallICFailed;
	protected static Integer iOverallTCNoRun;
	protected static long iOverallExecutionDuration;

	protected static String sPathReportsSource;

	protected static ArrayList aTestCaseID = new ArrayList();
	protected static ArrayList aTCDescr = new ArrayList();
	protected static ArrayList aTCStatus = new ArrayList();
	protected static ArrayList aTCExecDuration = new ArrayList();

	protected static ArrayList aTestStep = new ArrayList();
	protected static ArrayList aTestDescription = new ArrayList();
	protected static ArrayList aTestStepData = new ArrayList();
	protected static ArrayList aActualResult = new ArrayList();
	protected static ArrayList aExpectedResult = new ArrayList();
	protected static ArrayList aStepStatus = new ArrayList();
	protected static ArrayList aScreesnshots = new ArrayList();
	protected static ArrayList aTestStepsExecuted = new ArrayList();

	protected static ArrayList aRegPackName = new ArrayList();
	protected static ArrayList aRegPackTotalTestCases = new ArrayList();
	protected static ArrayList aRegPackTCExec = new ArrayList();
	protected static ArrayList aRegPackTCPassed = new ArrayList();
	protected static ArrayList aRegPackTCFailed = new ArrayList();
	protected static ArrayList aFailedTestCases = new ArrayList();
	protected static ArrayList aRegPackExecDuration = new ArrayList();

	public static ArrayList aOverallExecDuration = new ArrayList();

	public static String sRegPackFailedTestCases;

	protected static String sParentWindowHandle;
	protected static String sCurrentWindowHandle;

	protected static String sOverallExecutionStartTime;
	protected static String sOverallExecutionStopTime;
	protected static String sOverallExecutionDuration;

	protected static String sTCStartTime;
	protected static String sTCEndTime;
	protected static String sTCDuration;

	protected static String sRegPackStartTime;
	protected static String sRegPackEndTime;
	protected static String sRegPackDuration;
	protected static String sBrowserName;
	protected static String sScreenShotfor;
	// public static RemoteWebDriver driver;
	protected static WebDriver driver = null;
	protected static String sNodeURL;
	protected static String sNodeBrowser;
	protected static String sNodeName;
	protected static String sNodeRegPackName;

	// DB variables
	protected static String sComplexity;
	protected static String sDateTime;
	protected static String sReportsDBName;
	protected static String sReportsDBJDBCDriver;
	protected static String sReportsDBURL;
	protected static String sReportsDBUserName;
	protected static String sReportsDBPassword;
	protected static Connection conn;
	protected static Statement stmt;
	protected static DatabaseMetaData meta;
	protected static ResultSet rs;
	protected static String sProjectIdQuery;
	protected static String sProjectId;
	protected static String getCountQuery;
	protected static String getCount;
	protected static String sRunReferenceIdQuery;
	protected static String sRunReferenceId;
	protected static String sRegressionpackQuery;
	protected static String sRegressionpackId;
	protected static String sAutomationTestId;

	public static ArrayList<String> list = new ArrayList<String>();

	protected static String sPathTestLog;
	protected static String sPathRegPackLog;

	protected static String sRegPackRagStatus;
	protected static String sRunReferenceRagStatus;

	// ------------------------------------Method to create New
	// Folder-----------------------------------------
	protected static String createfolder(String path, String folder) throws IOException

	{
		String path_newFolder = "";
		try {

			File file = new File(path + folder);

			if (!file.exists()) {
				if (file.mkdir()) {
					path_newFolder = path + folder + "//";
					System.out.println("Directory is created!" + folder);
				} else {
					System.out.println("Failed to create directory!" + folder);
					path_newFolder = "";
				}

			} else {
				path_newFolder = path + folder + "//";
				System.out.println("Directory already exist!" + folder);
			}
		}

		catch (Exception error_message) {
			// Log.error(error_message.getMessage());
		}

		return path_newFolder;

	}

	// ____________________________Method to copy the
	// file___________________________________________________
	protected static void Copy_File(String source, String destination) throws IOException

	{
		try {
			File sourceFile = new File(source);
			String name = sourceFile.getName();
			File targetFile = new File(destination + name);
			if (!(targetFile.exists())) {
				FileUtils.copyFile(sourceFile, targetFile);
				System.out.println("Copied file:" + name);
			} else {
				System.out.println("File already exists:" + name);
			}

		} catch (Exception error_message) {
			System.out.println("GenericSkins||CopyFile||" + error_message.getMessage());
		}

	}

	// ________________________________Method to initialize required folder
	// paths in test suite________________
	protected static void InitializeTestFolderPaths() {
		sProjectPath = sProjectPath + "//";
		sTestDataPath = sProjectPath + "Wizard//TestData//";
		sPathOR = sProjectPath + "Wizard//ObjectRepository//";
		sSystemIndependencyConfig = sProjectPath + "Config//SystemIndependencyConfig.xml";
		sBrowsserDriverPath = "BrowserDrivers//";
		sPathReportsSource = sProjectPath + "Reports//";
		sPathUtils = sProjectPath + "Wizard//Utils//";
		sDriverFile = "Driver.xlsx";
		sSheetRegressionPacks = "RegressionPacks";

		iOverallTestCases = 0;
		iOverallTCExecuted = 0;
		iOverallTCPassed = 0;
		iOverallICFailed = 0;
		iOverallTCNoRun = 0;
		iOverallExecutionDuration = 0;

		SimpleDateFormat sDateTimeDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// dd/MM/yyyy
		Date dNow = new Date();
		sDateTime = sDateTimeDate.format(dNow);

	}

	// _______________________________Method to initialize column numbers in
	// RegPacks list sheet________________
	protected static void InitializeRegPackColumns() {
		iColRegID = 0;
		iColRegName = 1;
		iColRegDesc = 2;
		iColRegRunStatus = 3;

	}

	// _______________________________Method to initialize column numbers in
	// regpack sheet_______
	protected static void InitializeTestCaseColumns() {
		iColRegPackTCID = 0;
		iColRegPackTCStatus = 3;
		iColRegPackTCDesc = 1;
		iColRegPackTCRunMode = 2;
	}

	// _______________________________Method to initialize column numbers in
	// test case sheet_______
	protected static void InitializeTestStepsColumns() {
		iColTestStepID = 0;
		iColTestStepDesc = 1;
		iColTestStepValidation = 2;
		iColTestStepObjectName = 3;
		iColTestStepOperation = 4;
		iColTestStepData = 5;
		iColExpectedResult = 6;
		iColAtualResult = 7;
		iColTestStepStatus = 8;
		iColScreenShot = 9;
	}

	// _______________________________Method to initialize column numbers in
	// object list_____________
	protected static void InitializeObjectListColumns() {
		iColObjListObjectName = 0;
		iColObjListObjectType = 1;
		iColObjListObjectLocator = 2;
		iColObjListObjectPath = 3;
	}

	// ______________________________Method to set test step initial
	// values__________________
	protected static void SetTestStepInitialValues() {
		sTestStepID = "";
		sTestStepDesc = "";
		sTestStepValidation = "";
		sTestStepObjectName = "";
		sTestStepOperation = "";
		sTestStepData = "";
		sExpectedResult = "";
		sActualResult = "NA";
		sTestStepStatus = "";

	}

	// _______________________________Method to set test case initial
	// values_________________
	protected static void SetTestCaseInitialValues() {
		sRegPackTestCaseID = "";
		sRegPackTestCaseDesc = "";
		sRegPackTestCaseRunMode = "";

		iTotalTestSteps = 0;
		iTotalTestStepsExecuted = 0;
		iTotalTestStepsPassed = 0;
		iTotalTestStepsFailed = 0;

		SRegPackTestCaseStatus = "No Run";
		sTCDuration = "NA";
		sComplexity = "NA";

	}

	// _______________________________Method to set regression pack initial
	// values______________
	protected static void SetRegPackInitialValues() {
		sRegPackID = "";
		sRegPackName = "";
		sRegPackRunMode = "";
		sRegPackDuration = "NA";
		sRegPackFailedTestCases = "";

		iTotalTestCasesExecuted = 0;
		iTotalTestCasesPassed = 0;
		iTotalTestCasesFailed = 0;
		iTotalTestCasesNoRun = 0;
		iRegpackDuration = 0;

	}

	// ___________________________Method to set test case
	// status___________________________
	protected static void SetTestCaseStatus() {
		if (iTotalTestStepsFailed > 0) {
			SRegPackTestCaseStatus = "Failed";
			sRegPackFailedTestCases = sRegPackFailedTestCases + "<p>" + sRegPackTestCaseID + "</p>";
		} else if (iTotalTestStepsPassed == iTotalTestStepsExecuted) {
			SRegPackTestCaseStatus = "Passed";
		} else {
			SRegPackTestCaseStatus = "No Run";
		}

		iTotalTestCasesExecuted += 1;
		iOverallTCExecuted += 1;

		if (SRegPackTestCaseStatus.equalsIgnoreCase("Passed")) {
			iTotalTestCasesPassed += 1;
			iOverallTCPassed += 1;
		} else if (SRegPackTestCaseStatus.equalsIgnoreCase("Failed")) {
			iTotalTestCasesFailed += 1;
			iOverallICFailed += 1;
		} else {
			// iTotalTestCasesNoRun+=1;
		}

		if (iTotalTestSteps <= 100) {
			sComplexity = "Low";
		} else if (iTotalTestSteps <= 200) {
			sComplexity = "Medium";
		} else if (iTotalTestSteps <= 300) {
			sComplexity = "High";
		} else {
			sComplexity = "Complex";
		}

	}

	// _______________________________Method to set Regpack ArrayList
	// values____________________________________

	@SuppressWarnings("unchecked")
	protected static void SetRegPackArrayListValues() {
		aRegPackName.add(sRegPackName);
		aRegPackTotalTestCases.add(iRegPackTestCases);
		aRegPackTCExec.add(iTotalTestCasesExecuted);
		aRegPackTCPassed.add(iTotalTestCasesPassed);
		aRegPackTCFailed.add(iOverallICFailed);
		aFailedTestCases.add(sRegPackFailedTestCases);
		aRegPackExecDuration.add(sRegPackDuration);

		iOverallTestCases += iRegPackTestCases;
	}

	// _______________________________Method to set Regpack Test Case ArrayList
	// values____________________________________

	@SuppressWarnings("unchecked")
	protected static void SetRegPackTCArrayListValues() {
		aTestCaseID.add(sRegPackTestCaseID);
		aTCDescr.add(sRegPackTestCaseDesc);
		aTCStatus.add(SRegPackTestCaseStatus);
		aTCExecDuration.add(sTCDuration);
		aTestStepsExecuted.add(iTotalTestStepsExecuted);
	}

	// _______________________________Method to set Regpack Test Case ArrayList
	// values____________________________________

	@SuppressWarnings("unchecked")
	protected static void SetTestStepsArrayListValues() {

		aTestStep.add(sTestStepID);
		aTestDescription.add(sTestStepDesc);
		aTestStepData.add(sTestStepData);
		aActualResult.add(sActualResult);
		aExpectedResult.add(sExpectedResult);
		aStepStatus.add(sTestStepStatus);
		aScreesnshots.add(sScreenShotTCFolder + sTestStepID + ".jpg");
	}

	// ________________________________Method to set data config
	// values________________________
	protected static void SetDataConfigValues() throws Exception {
		sBrowserName = FetchDataConfig("BrowserName", "DataConfig", 0);
		sScreenShotfor = FetchDataConfig("CaptureScreenShot", "DataConfig", 0);
		sRunReference = FetchDataConfig("RunReference", "DataConfig", 0);
		sRunReferenceFlag = FetchDataConfig("RunReferenceFlag", "DataConfig", 0);
		// System.out.println(sBrowserName+"||"+sScreenShotfor+"||"+sRunReference);

	}

	// _______________________________________Method to create test results
	// folder__________________________
	protected static void CreateTestResultsFolder() throws Exception {
		// Create a folder inside test results folder named as per current run
		// date
		DateFormat datetimeFormat = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String[] sNewdateforlder;
		String new_log_folder = datetimeFormat.format(cal.getTime()).toString();
		new_log_folder = new_log_folder.replace(":", ".");
		sNewdateforlder = new_log_folder.split(" ");

		// Create a folder with current date inside test results folder
		sTestResultsPath = createfolder(sTestResultsPath, sNewdateforlder[0]);

		// Create a folder with RunReference inside test results folder
		sTestResultsPath = createfolder(sTestResultsPath, sRunReference);

		// Create a folder inside current date folder named as per current date
		// and time stamp
		sTestResultsPath = createfolder(sTestResultsPath, sNodeName + " " + new_log_folder);
	}

	// _____________________________________Method to compare actual result and
	// expected result___________________
	protected static void ResultComparision() {
		if (sActualResult == null) {
			sActualResult = "N/A";
		}
		if (sExpectedResult == null) {
			sExpectedResult = "N/A";
		}
		if (sExpectedResult.equalsIgnoreCase(sActualResult)) {
			
			sTestStepStatus = "Passed";
		} else {
			sTestStepStatus = "Failed";
		}

		iTotalTestStepsExecuted += 1;
		if (sTestStepStatus.equalsIgnoreCase("Passed")) {
			iTotalTestStepsPassed += 1;
		} else {
			iTotalTestStepsFailed += 1;
		}

		if (sScreenShotfor.equalsIgnoreCase("Passed") && sTestStepStatus.equalsIgnoreCase("Passed")) {
			screenshot();

		} else if (sScreenShotfor.equalsIgnoreCase("Failed") && sTestStepStatus.equalsIgnoreCase("Failed")) {
			screenshot();
		} else if (sScreenShotfor.equalsIgnoreCase("All")) {
			screenshot();
		} else {
			System.out.println("No ScreenShot");
			sScreeenShot = "NA";
		}

	}

	// ___________________Method to get current date and
	// time________________________________
	protected static String GetCurrentTime() {
		String sTime;
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();

		sTime = dateFormat.format(cal.getTime()).toString();
		return sTime;
	}

	// ___________________________________Method to get Time
	// Reports_____________________________________________

	protected static void GetTimeReport(long iTCDuration) {

		iRegpackDuration = iRegpackDuration + iTCDuration;
		sRegPackDuration = ConvertTimeReport(iRegpackDuration);
		iOverallExecutionDuration = iOverallExecutionDuration + iTCDuration;
		sOverallExecutionDuration = ConvertTimeReport(iOverallExecutionDuration);
		// System.out.println("iRegpackDuration:"+iTCDuration);
		// System.out.println("iRegpackDuration:"+iRegpackDuration);
		// System.out.println("iOverallExecutionDuration:"+iOverallExecutionDuration);

	}

	protected static String ConvertTimeReport(long iDuration) {

		long diffSeconds;
		long diffMinutes;
		long diffHours;
		long diffDays;
		String sDuration;
		String sDay = "Days";
		String sHour = "Hours";
		String sMinute = "Minutes";
		String sSecond = "Seconds";

		diffSeconds = iDuration / 1000 % 60;
		diffMinutes = iDuration / (60 * 1000) % 60;
		diffHours = iDuration / (60 * 60 * 1000) % 24;
		diffDays = iDuration / (24 * 60 * 60 * 1000);
		// System.out.println("DiffDays:"+diffDays);
		// System.out.println("iDuration:"+iDuration);
		// System.out.println("iDurationcal:"+(24 * 60 * 60 * 1000));
		if (diffDays == 1) {
			sDay = "Day";
		}
		if (diffHours == 1) {
			sHour = "Hour";
		}
		if (diffMinutes == 1) {
			sMinute = "Minute";
		}
		if (diffSeconds == 1) {
			sSecond = "Second";
		}

		if (!(diffDays == 0)) {
			sDuration = diffDays + " " + sDay + " " + diffHours + " " + sHour + " " + diffMinutes + " " + sMinute + " "
					+ diffSeconds + " " + sSecond;

		} else if (!(diffHours == 0)) {
			sDuration = diffHours + " " + sHour + " " + diffMinutes + " " + sMinute + " " + diffSeconds + " " + sSecond;

		} else if (!(diffMinutes == 0)) {
			sDuration = diffMinutes + " " + sMinute + " " + diffSeconds + " " + sSecond;

		} else {
			sDuration = diffSeconds + " " + sSecond;
		}

		return sDuration;

	}

	// ____________________________________Method to calculate difference
	// between two dates and times______________
	protected static String GetDateTimeDiff(String sStartTime, String sEndTime) throws Exception {
		String sDuration;
		SimpleDateFormat format = new SimpleDateFormat("MM_DD_YYYY HH:mm:ss");

		Date d1 = format.parse(sStartTime);
		Date d2 = format.parse(sEndTime);
		// System.out.println("d1:"+d1.getTime()/1000);
		long diff = d2.getTime() - d1.getTime();
		GetTimeReport(diff);
		sDuration = ConvertTimeReport(diff);
		// System.out.println("Difference in seconds:"+diff/1000);
		return sDuration;
	}

	// ____________________________Methiod to read System Independency Config
	// file__________________________________

	public static String ReadSystemIndependncyFile(String sNodeName) {

		String sValue = "";
		try {

			File fXmlFile = new File(sSystemIndependencyConfig);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = dBuilder.parse(fXmlFile);

			NodeList nList = ((org.w3c.dom.Document) doc).getElementsByTagName(sNodeName);

			for (int temp = 0; temp < nList.getLength(); temp++) {
				org.w3c.dom.Node nNode = nList.item(temp);
				// Element eElement = (Element) nList.item(temp);
				// System.out.println("value of node:"+nNode.getTextContent());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					sValue = nNode.getTextContent().trim();
				}
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			sValue = null;
		}
		return sValue;
	}

	// ________________________Method to take
	// screenshots_____________________________________________________
	public static void screenshotcopy() {
		try {
			Robot robot = new Robot();
			String format = "jpg";
			String fileName = sTestStepID + "." + format;
			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
			// System.out.println("Path
			// Screenshot:"+Constants.Path_ScreenShot+fileName);
			ImageIO.write(screenFullImage, format, new File(sScreenShotTCFolder + fileName));
			sScreeenShot = sScreenShotTCFolder + fileName;
			sScreeenShot = sScreeenShot.replaceAll("//", "/");
			// System.out.println("A full screenshot saved!");
		} catch (Exception error_message) {
			System.err.println(error_message.toString());

		}
	}

	// ____________________________Method to fetch data from Data Config
	// sheet_____________________________
	protected static String FetchDataConfig(String sParameterName, String sSheetName, int iCol) throws Exception {

		String sParameterValue = "";
		int iRowParameterName = TestDataImport.GetRowContains(sParameterName, iCol, sSheetName);
		sParameterValue = TestDataImport.GetCellData(sSheetName, iCol + 1, iRowParameterName);

		return sParameterValue;
	}

	// ____________________________Method to copy the complete
	// folder___________________________________________________
	public static void Copy_Folder(String source, String destination) throws IOException {
		File srcFolder = new File(source);
		File destFolder = new File(destination);
		// make sure source exists
		if (!srcFolder.exists()) {
			System.exit(0);

		} else {
			try {
				copyFolder(srcFolder, destFolder);
			} catch (IOException e) {
				e.printStackTrace();

				System.exit(0);
			}
		}

	}

	// ___________________________________Method to copy folder from source to
	// destination________________________________________________________________
	public static void copyFolder(File src, File dest) throws IOException {
		try {
			if (src.isDirectory()) {

				// if directory not exists, create it
				if (!dest.exists()) {
					dest.mkdir();

				}

				String files[] = src.list();

				for (String file : files) {

					File srcFile = new File(src, file);
					File destFile = new File(dest, file);

					copyFolder(srcFile, destFile);
				}

			} else {

				InputStream in = new FileInputStream(src);
				OutputStream out = new FileOutputStream(dest);

				byte[] buffer = new byte[1024];

				int length;
				// copy the file content in bytes
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}

				in.close();
				out.close();
			}
		} catch (Exception error_message) {
			System.out.println("Copy folder:" + error_message.getMessage());
		}

	}

	// _____________________________Method to read file config.xml___________________________________________________________________________
	
	protected static void LoadSystemIndependencyConfig() {

		sAUTPath = ReadSystemIndependncyFile("AUTPath");
		sProjectName = ReadSystemIndependncyFile("ProjectName");
		sBrowserName = ReadSystemIndependncyFile("LocalBrowserName");
		sScreenShotfor = ReadSystemIndependncyFile("CaptureScreenShot");
		sRunReference = ReadSystemIndependncyFile("RunReference");
		sRunReferenceFlag = ReadSystemIndependncyFile("RunReferenceFlag");
		sReportsDBURL = ReadSystemIndependncyFile("ReportsDBURL");
		sReportsDBJDBCDriver = ReadSystemIndependncyFile("ReportsDBJDBCDriver");
		sReportsDBName = ReadSystemIndependncyFile("ReportsDBName");
		sReportsDBUserName = ReadSystemIndependncyFile("ReportsDBUserName");
		sReportsDBPassword = ReadSystemIndependncyFile("ReportsDBPassword");

	}

	// _______________________________Method to create
	// file____________________________________________________
	protected static void CreateFile(String sPath, String sFileName) {
		try {
			File file = new File(sPath + sFileName);

			if (file.exists()) {
				System.out.println("File already exists:" + sFileName);
			} else {
				file.createNewFile();
			}

		} catch (Exception error_messasge) {
			System.out.println("Create File:" + sFileName + ":" + error_messasge.getMessage());
		}
	}

	// _____________________________Method to write a
	// file____________________________________________________________
	protected static void WriteFile(String sPath, String sFileName, String sContent) {
		try {

			// File file = new File(sPath+sFileName);
			BufferedWriter out = new BufferedWriter(new FileWriter(sPath + sFileName, true));
			out.write("\n");
			out.write("\n" + sContent);
			out.close();

		} catch (Exception error_message) {
			System.out.println("Write File:" + sFileName + ":" + error_message.getMessage());
		}
	}
	
	public static void screenshot()  {
		String fileName = sTestStepID;
		String format = ".jpg";
		try {

			// Convert web driver object to TakeScreenshot

			TakesScreenshot scrShot = ((TakesScreenshot) driver);

			// Call getScreenshotAs method to create image file

			File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

			// Move image file to new destination

			File DestFile = new File(sScreenShotTCFolder + fileName + format);

			// Copy file at destination

			FileUtils.copyFile(SrcFile, DestFile);
		} catch (Exception e) {

		}

		sScreeenShot = sScreenShotTCFolder + fileName + format;
		sScreeenShot = sScreeenShot.replaceAll("//", "/");

	}

}
