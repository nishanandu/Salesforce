package Scripts;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestReportDB extends GenericSkins  {
   // JDBC driver name and database URL
  
   //  Database credentials
  

 protected static void CreateDB() throws Exception {
		try {
			// Register JDBC driver
			Class.forName(sReportsDBJDBCDriver);

			// Open a connection
			System.out.println("Connecting to database..."+sReportsDBURL);
			
			conn = DriverManager.getConnection(sReportsDBURL, sReportsDBUserName, sReportsDBPassword);
			// Execute a query
			System.out.println("Creating database..."+sReportsDBName);
						
			stmt = conn.createStatement();

			String createdatabase = "CREATE DATABASE "+sReportsDBName;
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet rs = meta.getCatalogs();

			while (rs.next()) {
				
				String listofDatabases = rs.getString("TABLE_CAT");
				list.add(listofDatabases);
				if (list.contains(sReportsDBName)) 
				{
					break;
				}
			}

			// check for DB exists
			if (list.contains(sReportsDBName)) {
				// isDBExists = true;
				System.out.println("Database already exists");
				

			} else {
				// isDBExists = false;
				stmt.executeUpdate(createdatabase);
				System.out.println("Database is created");
				
			}
			//Thread.sleep(10000);
			
		}

		catch (Exception sqlException) 
		{
             System.out.println("TestReportsDB:"+sqlException.getMessage());
             //Thread.sleep(20000);
             
		}
	}
	
 //______________________________Method to create tables___________________________________
 protected static void CreateTables() throws Exception
 {
		CreateTableAutomationProject();
		CreateTableRunReferenceHistory();
		CreateTableRegressionPacksHistory();
		CreateTableAutomationTestsHistory();
		CreateTableTestStepsHistory();
		CreateTableAppusers();
		CreateTableUserProjects();
		CreateTableRunDetails();
		CreateViewRunReferenceHistory();
		CreateViewDetailedStattistic();
		
 }

	//______________________________Method to  create Automation Project table_______________________________
	protected static void CreateTableAutomationProject() throws Exception 
	{
		try {

			String sAutomationProject = "CREATE TABLE automationprojects (Id INT(20) PRIMARY KEY AUTO_INCREMENT,"
					+ "ProjectName VARCHAR(50),"
					+ "Workspace VARCHAR(200));";
			stmt.executeUpdate("use "+sReportsDBName);
			stmt.executeUpdate(sAutomationProject);
		} catch (SQLException e) {
			System.out.println(e.getMessage() + "CreateTableAutomationProject");
			//Thread.sleep(20000);
		}
		//Thread.sleep(20000);
	}
	
	//______________________________Method to  create Run Reference HIstory table_____________________________
		protected static void CreateTableRunReferenceHistory() throws Exception 
		{
			try {

				String sRunreferenceHistory = "create table runreferencehistory(ID INT PRIMARY KEY AUTO_INCREMENT,"
						+ "ProjectId INT," + "RunReferenceName VARCHAR(50)," + "NumberOfTests INT," + "Executed INT,"
						+ "Passed INT," + "Failed INT," + "NoRun INT," + "RAG VARCHAR(10),"+"Duration VARCHAR(50),"
						+ "StartDateTime DATETIME,"
						+ "EndDateTime DATETIME,"
						+ "CONSTRAINT FK_automationprojectid FOREIGN KEY (ProjectId) REFERENCES automationprojects(Id));";
				stmt.executeUpdate("use "+sReportsDBName);
				stmt.executeUpdate(sRunreferenceHistory);
			} catch (SQLException e) {
				System.out.println(e.getMessage() + "CreateTableRunReferenceHistory");
			}
			//Thread.sleep(20000);
		}

		//__________________________Method to create Regression Pack History Table____________________
		protected static void CreateTableRegressionPacksHistory() throws Exception 
		{
			try {

				String sRegressionpacksHistory = "create table regressionpackshistory("
						+ "ID INT PRIMARY KEY AUTO_INCREMENT," + "RunReferenceId INT,"
						+ "RegressionPackReference VARCHAR(50)," + "RegressionPackName VARCHAR(200),"
						+ "RegressionPackDescription nvarchar(1000)," + "NumberOfTests INT," + "Executed INT,"
						+ "Passed INT," + "Failed VARCHAR(50)," + "NoRun INT," + "RAG VARCHAR(10),"+"Duration VARCHAR(50),"
						+ "StartDateTime DATETIME,"
						+ "EndDateTime DATETIME,"
						+ "CONSTRAINT FK_runRefHistoryid FOREIGN KEY (RunReferenceId) REFERENCES runreferencehistory(Id));";
				stmt.executeUpdate("use "+sReportsDBName);
				stmt.executeUpdate(sRegressionpacksHistory);
			} catch (SQLException e) {
				System.out.println(e.getMessage() + "CreateTableRegressionPacksHistory");
			}		
			//Thread.sleep(20000);
		}

		//__________________________Method to create Automation Test History Table________________________________________
		protected static void CreateTableAutomationTestsHistory() throws Exception 
		{
			try {

				String sAutomationtestHistory = "create table automationtestshistory("
						+ "ID INT PRIMARY KEY AUTO_INCREMENT," + "RegressionPackId INT,"
						+ "AutomationTestRefrence VARCHAR(255)," + "AutomationTestDescription nvarchar(1000),"
						+ "Degree VARCHAR(200)," + "NumberOfSteps INT," + "Passed INT," + "Failed INT,"
						+ "AutomationTestStatus VARCHAR(50)," + "Duration VARCHAR(50)," + "StartDateTime DATETIME,"
						+ "EndDateTime DATETIME,"
						+ "CONSTRAINT FK_regPakcHistoryid FOREIGN KEY (RegressionPackId) REFERENCES regressionpackshistory(Id));";
				stmt.executeUpdate("use "+sReportsDBName);
				stmt.executeUpdate(sAutomationtestHistory);
			} catch (SQLException e) {
				System.out.println(e.getMessage() + "CreateTableAutomationTestsHistory");
				//Thread.sleep(20000);
			}
			//Thread.sleep(20000);
		}
		
		//__________________________Method to create Test Steps History Table________________________________________
		protected static void CreateTableTestStepsHistory() throws Exception {
			try {

				String sTestStepHistory = "create table teststepshistory(" + "ID INT PRIMARY KEY AUTO_INCREMENT,"
						+ "RegressionPackId INT," + "AutomationTestRefrence VARCHAR(255),"
						+ "TestStepReference VARCHAR(255)," + "TestStepDescription nvarchar(1000),"
						+ "StepData nvarchar(1000)," +"Operation VARCHAR(50)," + "ExpectedResult VARCHAR(255)," + "ActualResult NVARCHAR(4000),"
						+ "TestStepStatus VARCHAR(50)," + "ScreenshotPath NVARCHAR(1000),"
						+ "CONSTRAINT FK_autoTestHistoryid FOREIGN KEY (RegressionPackId) REFERENCES regressionpackshistory(Id));";
				stmt.executeUpdate("use "+sReportsDBName);
				stmt.executeUpdate(sTestStepHistory);
			} catch (SQLException e) {
				System.out.println(e.getMessage() + "CreateTableTestStepsHistory");
			}
			//Thread.sleep(20000);
		}
		


		//__________________________Method to create Test Steps History Table________________________________________
		protected static void CreateTableAppusers() throws Exception {
			try {

				String sTestStepHistory = "CREATE TABLE `appuser` (`Id` INT(11) NOT NULL AUTO_INCREMENT,`FirstName` VARCHAR(50) NOT NULL,`Surname` VARCHAR(50) NOT NULL,`LoginId` VARCHAR(20) NOT NULL,`LoginPwd` VARCHAR(200) NOT NULL,`DefaultPrjId` INT(11) NULL DEFAULT NULL,`Active` BIT(1) NULL DEFAULT NULL,PRIMARY KEY (`Id`))";
				stmt.executeUpdate("use "+sReportsDBName);
				stmt.executeUpdate(sTestStepHistory);
			} catch (SQLException e) {
				System.out.println(e.getMessage() + "CreateTableAppusers");
			}
			//Thread.sleep(20000);
		}
		
		//__________________________Method to create Test Steps History Table________________________________________
		protected static void CreateTableUserProjects() throws Exception {
			try {

				String sTestStepHistory = "CREATE TABLE IF NOT EXISTS `userprojects` ("
										  +"`userid` int(11) NOT NULL,"
										  +"`projectid` int(11) NOT NULL,"
										  +"`active` tinyint(1) DEFAULT '1',"
										  +"UNIQUE KEY `uk_userid_prjid` (`userid`,`projectid`),"
										  +"KEY `fk_projectid` (`projectid`),"
										  +"CONSTRAINT `fk_userid` FOREIGN KEY (`userid`) REFERENCES `appuser` (`id`),"
										  +"CONSTRAINT `fk_projectid` FOREIGN KEY (`projectid`) REFERENCES `automationprojects` (`Id`)"
										+") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
				stmt.executeUpdate("use "+sReportsDBName);
				stmt.executeUpdate(sTestStepHistory);
			} catch (SQLException e) {
				System.out.println(e.getMessage() + "CreateTableUserProjects");
			}
			//Thread.sleep(20000);
		}
				
		//__________________________Method to create Test Steps History Table________________________________________
		protected static void CreateTableRunDetails() throws Exception {
			try {

				String sTestStepHistory = "CREATE TABLE `run_details` ("+
																		"`Id` INT(20) NOT NULL AUTO_INCREMENT,"+
																		"`RUN_REFERENCE` VARCHAR(50) NULL DEFAULT NULL,"+
																		"`INITIATED_DATE` DATETIME NULL DEFAULT NULL,"+
																		"`INITIATED_BY` VARCHAR(20) NULL DEFAULT NULL,"+
																		"`RESULT_STORED` VARCHAR(5) NULL DEFAULT NULL,"+
																		"`STATUS` VARCHAR(15) NULL DEFAULT NULL,"+
																		"`ProjectId` INT(20) NOT NULL,"+
																		"PRIMARY KEY (`Id`)"+
																	    ")";
				stmt.executeUpdate("use "+sReportsDBName);
				stmt.executeUpdate(sTestStepHistory);
			} catch (SQLException e) {
				System.out.println(e.getMessage() + "CreateTableRunDetails");
			}
			//Thread.sleep(20000);
		}
		
		//__________________________Method to create Test Steps History Table________________________________________
		protected static void CreateViewRunReferenceHistory() throws Exception {
			try {

				String sTestStepHistory = "create view runrefhistory as SELECT"
																        +"runref.projectId AS ProjectId,"
																        +"runRef.RunReferenceName AS ReferenceName,"
																        +"runRef.NumberOfTests AS Test,"
																        +"runRef.Executed,"
																        +"runRef.Passed,"
																        +"runRef.Failed,"
																        +"runRef. NoRun,"
																        +"'RED' AS RAG,"
																        +"runRef.Duration"
																        +"FROM runreferencehistory runRef JOIN automationprojects project on runref.projectId = project.Id ;";
				stmt.executeUpdate("use "+sReportsDBName);
				stmt.executeUpdate(sTestStepHistory);
			} catch (SQLException e) {
				System.out.println(e.getMessage() + "CreateTableTestStepsHistory");
			}
			//Thread.sleep(20000);
		}
		
		//__________________________Method to create Test Steps History Table________________________________________
		protected static void CreateViewDetailedStattistic() throws Exception {
			try {

				String sTestStepHistory = "create view detailedstatistic as  SELECT"
																        +"`runref`.`ProjectId` AS `ProjectId`,"
																        +"`runref`.`ID` as runrefId,"
																        +"`regpack`.`id` AS `regPackId`,"
																        +"`regpack`.`RegressionPackReference` AS `referenceName`,"
																        +"`regpack`.`RegressionPackName` AS `Name`,"
																        +"`regpack`.`NumberOfTests` AS `Test`,"
																        +"`regpack`.`Executed` AS `Executed`,"
																        +"`regpack`.`Passed` AS `Passed`,"
																        +"`regpack`.`Failed` AS `Failed`,"
																        +"`regpack`.`NoRun` AS `NoRun`,"
																        +"regPack.RAG AS `RAG`,"
																        +"`regpack`.`Duration` AS `Duration`"
																        +"FROM `runreferencehistory` `runRef` JOIN `regressionpackshistory` `regPack` on `runref`.`ID` = `regpack`.`RunReferenceId`";
				stmt.executeUpdate("use "+sReportsDBName);
				stmt.executeUpdate(sTestStepHistory);
			} catch (SQLException e) {
				System.out.println(e.getMessage() + "CreateViewDetailedStattistic");
			}
			//Thread.sleep(20000);
		}


	//___________________________________________________________Method to Insert/Update Automation Project table______________________________________________
	protected static void QueryAutomationProjectTable() throws Exception {
		try {

			System.out.println(sProjectName);
			stmt.executeUpdate("use "+sReportsDBName);
			String query = "select count(*) as count from automationprojects where ProjectName = '" + sProjectName+ "'";

			ResultSet rs1 = stmt.executeQuery(query);
			String count = null;
			if (rs1.next()) {
				count = rs1.getString(1);
			}

			if (count != null && count.equals("0")) {

				String sql = "INSERT INTO automationprojects (Id, ProjectName) VALUES (null, '"+sProjectName+"')";
				stmt.executeUpdate(sql);
			}
		

		} catch (Exception e) {
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
			
		}
	}

	//__________________________________________________Method to insert/Update Run Reference history table___________________________________________________
	protected static void QueryRunReferenceHistoryTable() throws Exception 
	{
		try {

			stmt.executeUpdate("use "+sReportsDBName);
			sProjectIdQuery = "select id from automationprojects where ProjectName = '" + sProjectName + "'";
			ResultSet rsProjectId = stmt.executeQuery(sProjectIdQuery);
			if (rsProjectId.next()) {
				sProjectId = rsProjectId.getString(1);
			}
			getCountQuery = "SELECT count(*) as count FROM runreferencehistory WHERE ProjectId = '" + sProjectId
					+ "' AND RunReferenceName = '" + sRunReference + "'";
			ResultSet rsCount = stmt.executeQuery(getCountQuery);
			System.out.println(sRunReference);

			if (rsCount.next()) {
				getCount = rsCount.getString(1);
			}
			Timestamp();
			
			if (getCount != null && getCount.equals("0")) {

				String sql = "INSERT INTO runreferencehistory (Id, ProjectId,RunReferenceName,StartDateTime) VALUES (null,'"+sProjectId+"','"+sRunReference+"','"+sDateTime+"')";
				
				stmt.executeUpdate(sql);
			}

			else {

				if (getCount != null && getCount.equals("1")) {
					UpdateRunRefHistoryTable();
					GetOverallExecution();
					
					
					String updatequery = "UPDATE runreferencehistory SET NumberOfTests = '" + iOverallTestCases
							+ "', Executed = '" + iOverallTCExecuted + "', Passed = '" + iOverallTCPassed
							+ "', Failed = '" + iOverallICFailed + "', NoRun = '" + iOverallTCNoRun 
							+ "', RAG = '" + sRunReferenceRagStatus 
							+ "', Duration = '" + sOverallExecutionDuration + "', EndDateTime = '" + sDateTime + "' WHERE ProjectId = '"
							+ sProjectId + "' AND RunReferenceName = '" + sRunReference + "'";
					updatequery = updatequery.replaceAll("\'null\'", "null");
					stmt.executeUpdate(updatequery);
				}
			}

		} catch (Exception e) {
			System.err.println("Got an exception!");
			System.err.println(e.getMessage() + "QueryRunReferenceHistoryTable");
		}
	}
	
	//_____________________________________Method to update RunReference history table_________________________________________________
	
    protected static void UpdateRunRefHistoryTable() throws Exception
    {
    	try
    	{
    		 stmt.executeUpdate("use "+sReportsDBName);
    		 String supdatedvalue = "select SUM(NumberOfTests) AS Totaltests, SUM(Executed) AS Totalexecuted, SUM(Passed) AS Totalpassed, SUM(Failed) AS Totalfailed, SUM(NoRun) AS Totalnorun from regressionpackshistory where RunReferenceId = '"+sRunReferenceId+"'";
    	     ResultSet rsGetTotalCount = stmt.executeQuery(supdatedvalue);
    	     if (rsGetTotalCount.next()) {
    	     iOverallTestCases = rsGetTotalCount.getInt(1);
    	     iOverallTCExecuted = rsGetTotalCount.getInt(2);
    	     iOverallTCPassed = rsGetTotalCount.getInt(3);
    	     iOverallICFailed = rsGetTotalCount.getInt(4);
    	     iOverallTCNoRun = rsGetTotalCount.getInt(5);
    	     }
    	     GetOverallExecution();
    	     UpdateRunReferenceRAGStatus();
    	     //System.out.println("sOverallExecutionDuration:"+sOverallExecutionDuration);
    	}
    	catch(Exception error_message)
    	{
    		System.out.println("UpdateRunRefHistoryTable||"+error_message.getMessage());
    		Thread.sleep(5000);
    	}
    }
    
	//_____________________________________Method to Insert?Update Regression pack history table______________________________________
    protected static void QueryRegressionPacksHistory() throws Exception {
		try {

			stmt.executeUpdate("use "+sReportsDBName);
			sRunReferenceIdQuery = "select ID  from runreferencehistory where ProjectId = '" + sProjectId + "' AND RunReferenceName = '" + sRunReference + "'";
			ResultSet rsRRId = stmt.executeQuery(sRunReferenceIdQuery);
			if (rsRRId.next()) {
				sRunReferenceId = rsRRId.getString(1);
			}
			getCountQuery = "SELECT count(*) as count FROM regressionpackshistory WHERE RunReferenceId = '"
					+ sRunReferenceId + "' AND RegressionPackReference = '" + sRegPackID + "'";
			ResultSet rsCount1 = stmt.executeQuery(getCountQuery);

			if (rsCount1.next()) {
				getCount = rsCount1.getString(1);
			}
			Timestamp();
			if (getCount != null && getCount.equals("0")) {
				String sql = "INSERT INTO regressionpackshistory (Id, RunReferenceId,RegressionPackReference,RegressionPackName,StartDateTime) VALUES (Null,'"+sRunReferenceId+"','"+sRegPackID+"','"+sRegPackName+"','"+sDateTime+"')";
				
				stmt.executeUpdate(sql);
			}

			else {
				if (getCount != null && getCount.equals("1")) {

					UpdateRegPackHistoryTable();
					GetRegPackExecution();
					String updatequery = "UPDATE regressionpackshistory SET RegressionPackDescription = '" + sRegPackDesc
							+ "', NumberOfTests = '" + iRegPackTestCases + "', Executed = '" + iTotalTestCasesExecuted
							+ "', Passed = '" + iTotalTestCasesPassed + "', Failed = '" + iTotalTestCasesFailed
							+ "', NoRun = '" + iTotalTestCasesNoRun 
							+ "', RAG = '" + sRegPackRagStatus 
							+ "', Duration = '" + sRegPackDuration
							+ "', EndDateTime = '" + sDateTime + "' WHERE RunReferenceId = '" + sRunReferenceId
							+ "' AND RegressionPackReference = '" + sRegPackID + "'";
					updatequery = updatequery.replaceAll("\'null\'", "null");
					stmt.executeUpdate(updatequery);
				}
			}

		} catch (Exception e) {
			System.err.println("Got an exception!");
			System.err.println(e.getMessage() + "QueryRegressionPacksHistory");
		}
	}
//_____________________________________Method to update RunReference history table_________________________________________________
	
    protected static void UpdateRegPackHistoryTable() throws Exception
    {
    	try
    	{
    		 stmt.executeUpdate("use "+sReportsDBName);
    		 String sQuery = "select count(*) from automationtestshistory where RegressionPackId='"+sRegressionpackId+"'";
    		 int iCnt=0;
    	     /*ResultSet rsGetTotalCount = stmt.executeQuery(sQuery);
    	     if (rsGetTotalCount.next()) {
    	    	 iCnt=rsGetTotalCount.getInt(1);
    	     }
    	     iRegPackTestCases=iCnt;*/
    	     
    	     sQuery = "select count(*) from automationtestshistory where RegressionPackId='"+sRegressionpackId+"' and AutomationTestStatus in ('Passed','Failed')";
    		 iCnt=0;
    	     ResultSet rsGetTotalExeCount = stmt.executeQuery(sQuery);
    	     if (rsGetTotalExeCount.next()) {
    	    	 iCnt=rsGetTotalExeCount.getInt(1);
    	     }
    	     iTotalTestCasesExecuted = iCnt;
    	     
    	     iCnt=0;
    	     sQuery = "select count(*) from automationtestshistory where RegressionPackId='"+sRegressionpackId+"' and AutomationTestStatus = 'Passed'";
    		
    	     ResultSet rsGetTotalPassCount = stmt.executeQuery(sQuery);
    	     if (rsGetTotalPassCount.next()) {
    	    	 iCnt=rsGetTotalPassCount.getInt(1);
    	     }
    	     iTotalTestCasesPassed = iCnt;
    	     
    	     iCnt=0;
    	     sQuery = "select count(*) from automationtestshistory where RegressionPackId='"+sRegressionpackId+"' and AutomationTestStatus = 'Failed'";
    	     ResultSet rsGetTotalFailCount = stmt.executeQuery(sQuery);
    	     if (rsGetTotalFailCount.next()) {
    	    	 iCnt=rsGetTotalFailCount.getInt(1);
    	     }
    	     iTotalTestCasesFailed=iCnt;
    	     iTotalTestCasesNoRun = iRegPackTestCases-iTotalTestCasesExecuted;
    	     iCnt=0;
    	     UpdateRegPackRAGStatus();
    	   
    	  
    	}
    	catch(Exception error_message)
    	{
    		System.out.println("UpdateRegPackHistoryTable||"+error_message.getMessage());
    		Thread.sleep(5000);
    	}
    }

	//____________________________Method to insert/Update automation test history table_______________________________
	protected static void QueryAutomationTestsHistory() throws Exception {
		try {

			stmt.executeUpdate("use "+sReportsDBName);
			sRegressionpackQuery = "select ID  from regressionpackshistory where RunReferenceId = '" + sRunReferenceId
					+ "' and RegressionPackName = '"+sRegPackName+"'";

			ResultSet rsRPId = stmt.executeQuery(sRegressionpackQuery);
			if (rsRPId.next()) {
				sRegressionpackId = rsRPId.getString(1);
			}
			getCountQuery = "SELECT count(*) as count FROM automationtestshistory WHERE RegressionPackId = '"
					+ sRegressionpackId + "' AND AutomationTestRefrence = '" + sRegPackTestCaseID + "'";
			ResultSet rsCount2 = stmt.executeQuery(getCountQuery);
			if (rsCount2.next()) {
				getCount = rsCount2.getString(1);
			}
			Timestamp();
			sRegPackTestCaseDesc=sRegPackTestCaseDesc.replace("'", "");
			if (getCount != null && getCount.equals("0")) {

				String sql = "INSERT INTO automationtestshistory (Id, RegressionPackId,AutomationTestRefrence,AutomationTestDescription,Degree,NumberOfSteps,Passed,Failed,AutomationTestStatus,Duration,StartDateTime,EndDateTime) VALUES (Null,'"+sRegressionpackId+"','"+sRegPackTestCaseID+"','"+sRegPackTestCaseDesc+"','"+sComplexity+"','"+iTotalTestSteps+"','"+iTotalTestStepsPassed+"','"+iTotalTestStepsFailed+"','"+SRegPackTestCaseStatus+"','"+sTCDuration+"','"+sDateTime+"','"+sDateTime+"')";
				stmt.executeUpdate(sql);
			}

			else {

				if (getCount != null && getCount.equals("1")) {

					String updatequery = "UPDATE automationtestshistory SET AutomationTestDescription = '"
							+ sRegPackTestCaseDesc + "', Degree = '" + sComplexity + "', NumberOfSteps = '"
							+ iTotalTestSteps + "', Passed = '" + iTotalTestStepsPassed + "', Failed = '"
							+ iTotalTestStepsFailed + "', AutomationTestStatus = '" + SRegPackTestCaseStatus
							+ "', Duration = '" + sTCDuration + "', StartDateTime = '" + sDateTime+ "', EndDateTime = '" + sDateTime
							+ "' WHERE RegressionPackId = '" + sRegressionpackId + "' AND AutomationTestRefrence = '"
							+ sRegPackTestCaseID + "'";
					updatequery = updatequery.replaceAll("\'null\'", "null");
					stmt.executeUpdate(updatequery);
				}
			}

		} catch (Exception e) {
			System.err.println("Got an exception!");
			System.err.println(e.getMessage() + "QueryAutomationTestsHistory");
		}
	}

	//_________________________________Method to Insert/Udpate TestStepHistory table____________________________________
	protected static void QueryTestStepsHistory() throws Exception {
		try {

			stmt.executeUpdate("use "+sReportsDBName);
			sRegressionpackQuery = "select ID  from automationtestshistory where RegressionPackID = '" + sRegressionpackId
					+ "' and AutomationTestRefrence = '"+sRegPackTestCaseID+"'";
			ResultSet rsRPId = stmt.executeQuery(sRegressionpackQuery);
			if (rsRPId.next()) {
				sAutomationTestId = rsRPId.getString(1);
			}
			getCountQuery = "SELECT count(*) as count FROM teststepshistory WHERE RegressionPackId = '"
					+ sRegressionpackId + "' AND AutomationTestRefrence = '" + sRegPackTestCaseID
					+ "' AND TestStepReference = '" + sTestStepID + "'";
			ResultSet rsCount2 = stmt.executeQuery(getCountQuery);

			if (rsCount2.next()) {
				getCount = rsCount2.getString(1);
			}
			
			
		    if(sActualResult.contains(":"))
		    {
			    String search = ":";
			    Pattern error = Pattern.compile(search);
			    Matcher Aresultmatch = error.matcher(sActualResult);
			           
			    while (Aresultmatch.find()) 
			    {
			               
			    	sActualResult = sActualResult.substring(0,Aresultmatch.start());
			    	sActualResult = sActualResult.replace("'", "");
			        break;
			    }
		    }
		    
		    sTestStepDesc=sTestStepDesc.replace("'", "");

			if (getCount != null && getCount.equals("0")) {

				String sql = "INSERT INTO teststepshistory (Id, RegressionPackId,AutomationTestRefrence,TestStepReference,TestStepDescription,StepData,Operation,ExpectedResult,ActualResult,TestStepStatus,ScreenshotPath) VALUES (Null,'"+sRegressionpackId+"','"+sRegPackTestCaseID+"','"+sTestStepID+"','"+sTestStepDesc+"','"+sTestStepData+"','"+sTestStepOperation+"','"+sExpectedResult+"','"+sActualResult+"','"+sTestStepStatus+"','"+sScreeenShot+"')";
				
				stmt.executeUpdate(sql);
			}

			else {

				if (getCount != null && getCount.equals("1")) {

					String updatequery = "UPDATE teststepshistory SET TestStepDescription = '" + sTestStepDesc
							+ "', StepData = '" + sTestStepData + "', ExpectedResult = '" + sExpectedResult
							+ "', ActualResult = '" + sActualResult + "', TestStepStatus = '" + sTestStepStatus
							+ "', ScreenshotPath = '" + sScreeenShot + "' WHERE RegressionPackId = '"
							+ sRegressionpackId + "' AND AutomationTestRefrence = '" + sRegPackTestCaseID
							+ "' AND TestStepReference = '" + sTestStepID + "'";
					stmt.executeUpdate(updatequery);
				}
			}

		} catch (Exception e) {
			System.err.println("Got an exception!");
			System.err.println(e.getMessage() + "QueryTestStepsHistory");
		}
	}
	
	//________________________Method to close DB Connection_______________________________________________________
	public static void CloseDBConnection()
	{
		try
		{
			if (stmt != null)
		      {
				stmt.close();
				stmt = null;
		      }
			if (rs != null)
		      {
		        rs.close();
		        rs = null;
		      }
			if (conn != null)
		      {
		        conn.close();
		        conn = null;
		      }
		}
		catch(Exception error_message)
		{
			System.out.println("Catch||CloseDB||"+error_message.getMessage());
		}
	}
	
  
	
	//_________________Method to get overall execution duration from DB_______________________________________________
	
	protected static void GetOverallExecution()
	{
		try
    	{
			 String sRegDuration;
			 Long lRegDuration;
			 Long lOverallDuration = 0L;
			 int iTotalRegPacks=0;
			// ArrayList aRegDuration = new ArrayList();
    		 stmt.executeUpdate("use "+sReportsDBName);
    		
    		 String sQuery = "select Duration from regressionpackshistory where RunReferenceId="+sRunReferenceId;
    		 String sCnt ="select count(*) from regressionpackshistory where RunReferenceId="+sRunReferenceId;
    		 ResultSet rsCount = stmt.executeQuery(sCnt);
    		 if (rsCount.next()) 
    	     {
    			 iTotalRegPacks = rsCount.getInt(1);
    	    	
    	     }
    		 rsCount=null;
    		
    	     ResultSet rsGetTotalCount = stmt.executeQuery(sQuery);
    	    
    	     for(int i=1;i<=iTotalRegPacks;i++)
    	     {   rsGetTotalCount.next();
    	    	 sRegDuration = rsGetTotalCount.getString(1);
    	    	 
    	    	 lRegDuration = GetTimeSeconds(sRegDuration);
    	    	
    	    	 lOverallDuration+=lRegDuration;
    	    	 
    	     }
    	    
    	     sOverallExecutionDuration= ConvertTimeReport(lOverallDuration);
    	    
    	}
    	catch(Exception error_message)
    	{
    		System.out.println("GetOverallExecution||"+error_message.getMessage());
    		
    	}
	}
	
	//_________________Method to get overall execution duration from DB_______________________________________________
	
	
		protected static void GetRegPackExecution()
		{
			try
	    	{
				 String sTestDuration;
				 Long lTestDuration;
				 Long lRegPackDuration = 0L;
				 int iTotalTestCases=0;
				// ArrayList aRegDuration = new ArrayList();
	    		 stmt.executeUpdate("use "+sReportsDBName);
	    		
	    		 String sQuery = "select Duration from automationtestshistory where RegressionPackId="+sRegressionpackId;
	    		 String sCnt ="select count(*) from automationtestshistory where RegressionPackId="+sRegressionpackId;
	    		 ResultSet rsCount = stmt.executeQuery(sCnt);
	    		 if (rsCount.next()) 
	    	     {
	    			 iTotalTestCases = rsCount.getInt(1);
	    	    	
	    	     }
	    		 rsCount=null;
	    		
	    	     ResultSet rsGetTotalCount = stmt.executeQuery(sQuery);
	    	    
	    	     for(int i=1;i<=iTotalTestCases;i++)
	    	     {   rsGetTotalCount.next();
	    	         sTestDuration = rsGetTotalCount.getString(1);
	    	    	 
	    	         lTestDuration = GetTimeSeconds(sTestDuration);
	    	    	
	    	         lRegPackDuration+=lTestDuration;
	    	    	 
	    	     }
	    	    
	    	     sRegPackDuration= ConvertTimeReport(lRegPackDuration);
	    	    
	    	}
	    	catch(Exception error_message)
	    	{
	    		System.out.println("GetRegPackExecution||"+error_message.getMessage());
	    		
	    	}
		}
	
	//____________________Method to convert time into seconds___________________________________________________________________
	
	protected static long GetTimeSeconds(String sTime)
	{
		
		int iSize=0;
	    
		String[] aTime = sTime.split(" ");
		iSize=aTime.length;
		
		long iSeconds=0;
			switch(iSize)
			{
			   case 2:
				   iSeconds = Integer.parseInt(aTime[0]);
				   //System.out.println("iSeconds:"+iSeconds);
				   break;
				   
			   case 4:
				  
				   iSeconds = Integer.parseInt(aTime[0])*60;
				   iSeconds+=Integer.parseInt(aTime[2]);
				   break;
				   
			   case 6:
					  
				   iSeconds = Integer.parseInt(aTime[0])*3600;
				   iSeconds+=Integer.parseInt(aTime[2])*60;
				   iSeconds+=Integer.parseInt(aTime[4]);
				   break;
				   
			   case 8:
				   iSeconds+= Integer.parseInt(aTime[0])*24*3600;  
				   iSeconds+= Integer.parseInt(aTime[2])*3600;
				   iSeconds+=Integer.parseInt(aTime[4])*60;
				   iSeconds+=Integer.parseInt(aTime[6]);
				   break;
				  
						   
			}
		
		iSeconds = iSeconds*1000;
		
		return iSeconds;
		
	}
	
	
	//___________________________________________Method to get record time____________________________________________________
	
	protected static void Timestamp()throws Exception
    {
    	try
    	{
    		  SimpleDateFormat sDateTimeDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
    		  Date dNow = new Date();
    		  sDateTime = sDateTimeDate.format(dNow);
    	}
    	catch(Exception error_message)
    	{
    		System.out.println(error_message.getMessage());
    		Thread.sleep(5000);
    	}
    }
	
//___________________________Method to set RAG Status in RunreferenceHistory table________________________________________
    
    protected static void UpdateRunReferenceRAGStatus() throws Exception
    {
    	try
    	{	int iRegpackcount = 0;
    		int iGreencount = 0;
    		int iAmbercount = 0;
    		int iRedcount = 0;
    		
    		String RegPackCount = "select count(*) from regressionpackshistory where RunReferenceId = '"+sRunReferenceId+"'";
   		    ResultSet rsGetTotalRegpackCount = stmt.executeQuery(RegPackCount);
   		    if (rsGetTotalRegpackCount.next()) {
   		    	iRegpackcount=rsGetTotalRegpackCount.getInt(1);
   	        }    		
    		String RagCountgreen = "select count(*) from regressionpackshistory where RunReferenceId = '"+sRunReferenceId+"' and RAG = 'GREEN'";
   		    ResultSet rsGetTotalGreenCount = stmt.executeQuery(RagCountgreen);
   		    if (rsGetTotalGreenCount.next()) {
   			    iGreencount=rsGetTotalGreenCount.getInt(1);
   	        }
    		String RagCountamber = "select count(*) from regressionpackshistory where RunReferenceId = '"+sRunReferenceId+"' and RAG = 'AMBER'";
   		    ResultSet rsGetTotalAmberCount = stmt.executeQuery(RagCountamber);
   		    if (rsGetTotalAmberCount.next()) {
   		    	iAmbercount=rsGetTotalAmberCount.getInt(1);
   	        }
    		String RagCountred = "select count(*) from regressionpackshistory where RunReferenceId = '"+sRunReferenceId+"' and RAG = 'RED'";
   		    ResultSet rsGetTotalRedCount = stmt.executeQuery(RagCountred);
   		    if (rsGetTotalRedCount.next()) {
   		    	iRedcount=rsGetTotalRedCount.getInt(1);
   	        }
   		    
   	    	if(iRegpackcount == iGreencount){
   	    		
   	    		sRunReferenceRagStatus = "GREEN";
   	    	}
   	    	else if(iRedcount >=1){
   	    		
   	    		sRunReferenceRagStatus = "RED";
   	    		
   	    	}
   	    	else if(iAmbercount <=2){
   	    		
   	    		sRunReferenceRagStatus = "AMBER";
   	    		
   	    	}
   	    	else{
   	    		sRunReferenceRagStatus = "RED";
   	    	}
   	    
    	}
    	catch(Exception e){
    		
    	}
    }
    
//___________________________Method to set RAG Status in RegressionPackHistory table________________________________________
    
    protected static void UpdateRegPackRAGStatus() throws Exception
    {
    	try
    	{
    	     int iLowTCfailedcount = 0;
    	     int iMediumTCfailedcount = 0;
    	     int iHighTCfailedcount = 0;
    	     int iComplexTCfailedcount = 0;
    	     int ipassedpercentage = 0;
    	     
    	     
    		 stmt.executeUpdate("use "+sReportsDBName);
    		 //String sQuery = "select count(*) from automationtestshistory where RegressionPackId='"+sRegressionpackId+"'";
    		 //String PassedTCCount = "select count(*) from automationtestshistory where RegressionPackId = '"+sRegressionpackId+"' and AutomationTestStatus = 'Passed'";
    		 String LowTCCount = "select count(*) from automationtestshistory where RegressionPackId = '"+sRegressionpackId+"' and AutomationTestStatus = 'failed' and Degree = 'Low'";
    		 ResultSet rsGetTotalLowFailCount = stmt.executeQuery(LowTCCount);
    	     if (rsGetTotalLowFailCount.next()) {
    	    	 iLowTCfailedcount=rsGetTotalLowFailCount.getInt(1);
    	     }
    		 
    		 String MediumTCCount = "select count(*) from automationtestshistory where RegressionPackId = '"+sRegressionpackId+"' and AutomationTestStatus = 'failed' and Degree = 'Medium'";
    		 ResultSet rsGetTotalMediumFailCount = stmt.executeQuery(MediumTCCount);
    		 if (rsGetTotalMediumFailCount.next()) {
    			 iMediumTCfailedcount=rsGetTotalMediumFailCount.getInt(1);
    	     }
    		 
    		 String HighTCCount = "select count(*) from automationtestshistory where RegressionPackId = '"+sRegressionpackId+"' and AutomationTestStatus = 'failed' and Degree = 'High'";
    		 ResultSet rsGetTotalHighFailCount = stmt.executeQuery(HighTCCount);
    		 if (rsGetTotalHighFailCount.next()) {
    			 iHighTCfailedcount=rsGetTotalHighFailCount.getInt(1);
    	     }
    		 
    		 String ComplexTCCount = "select count(*) from automationtestshistory where RegressionPackId = '"+sRegressionpackId+"' and AutomationTestStatus = 'failed' and Degree = 'Complex'";
    		 ResultSet rsGetTotalComplexFailCount = stmt.executeQuery(ComplexTCCount);
    		 if (rsGetTotalComplexFailCount.next()) {
    			 iComplexTCfailedcount=rsGetTotalComplexFailCount.getInt(1);
    	     }
    		 
    		ipassedpercentage = (iTotalTestCasesPassed*100)/iTotalTestCasesExecuted;
    		 
	    	if(iTotalTestCasesExecuted == iTotalTestCasesPassed){
	    		
	    		sRegPackRagStatus = "GREEN";
	    	}
	    	else if(!(iHighTCfailedcount==0) || !(iComplexTCfailedcount ==0)) {
	    		
	    		   sRegPackRagStatus = "RED";
	    		
	    	}
	    	else if(iLowTCfailedcount <=5 && iMediumTCfailedcount <=3){
	    		
	    		   sRegPackRagStatus = "AMBER";
	    	}
	    	else if(ipassedpercentage >= 80){
	    		
	 		   sRegPackRagStatus = "AMBER";
	    	}
	    	else{
	    		
	    		sRegPackRagStatus = "RED";    		
	    	}

    	}
    	catch(Exception e){
    		
    	}
    }	
}

