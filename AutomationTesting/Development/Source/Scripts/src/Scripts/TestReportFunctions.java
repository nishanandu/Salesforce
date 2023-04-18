package Scripts;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



import org.apache.poi.common.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;





public class TestReportFunctions extends GenericSkins
{
	//___________________________________Method to set status in Test Steps________________________
	protected static void SetTestStepsReport(int iRow) throws Exception
	{     
		 
		   String Actlresult = sActualResult;
	       if(Actlresult.contains(":"))
	       {
	    	   String search = ":";
	           Pattern error = Pattern.compile(search);
	           Matcher Aresultmatch = error.matcher(Actlresult);
	           
	           while (Aresultmatch.find()) 
	           {
	               
	        	   Actlresult = Actlresult.substring(0,Aresultmatch.start());
	                break;
	           }
	       }
	       TestDataImport.SetExcelFile(sTestResultsPath,sRegPackName+".xlsx");
		  TestDataImport.setCellData(sRegPackTestCaseID, iRow, iColAtualResult, Actlresult,"NA");
		  if(sTestStepStatus.equalsIgnoreCase("Passed"))
		  {
			  TestDataImport.setCellData(sRegPackTestCaseID, iRow, iColTestStepStatus, sTestStepStatus,"Green");
		  }
		  else if(sTestStepStatus.equalsIgnoreCase("Failed"))
		  {
			  TestDataImport.setCellData(sRegPackTestCaseID, iRow, iColTestStepStatus, sTestStepStatus,"Red");
		  }
		  else
		  {
			  TestDataImport.setCellData(sRegPackTestCaseID, iRow, iColTestStepStatus, sTestStepStatus,"NA");
		  }
		  TestDataImport.setCellData(sRegPackTestCaseID, iRow, iColScreenShot, sScreeenShot,"Blue");
			 
	}
	//___________________________________Method to set status in Test Cases________________________
	protected static void SetTestCaseReport(int iRow) throws Exception
	{
		
		
		 
		 TestDataImport.SetExcelFile(sTestResultsPath,sDriverFile);
		 if(SRegPackTestCaseStatus.equalsIgnoreCase("Passed"))
		 {
			 TestDataImport.setCellData(sRegPackID, iRow, iColRegPackTCStatus, SRegPackTestCaseStatus,"Green");
		 }
		 else if(SRegPackTestCaseStatus.equalsIgnoreCase("Failed"))
		 {
			 TestDataImport.setCellData(sRegPackID, iRow, iColRegPackTCStatus, SRegPackTestCaseStatus,"Red");
		 }
		 else
		 {
			 TestDataImport.setCellData(sRegPackID, iRow, iColRegPackTCStatus, SRegPackTestCaseStatus,"Blue");
		 }
		 
		 TestDataImport.setCellData(sRegPackID, iRow, iColRegPackTCTotalStepsExec, ""+iTotalTestStepsExecuted,"NA");
		 TestDataImport.setCellData(sRegPackID, iRow, iColRegPackTCTotalStepsPassed, ""+iTotalTestStepsPassed,"Green");
		 TestDataImport.setCellData(sRegPackID, iRow, iColRegPackTCTotalStepsFailed, ""+iTotalTestStepsFailed,"red");
		 TestDataImport.setCellData(sRegPackID, iRow, iColRegPackTCExecTime, sTCDuration,"NA");
			 
	}
	//___________________________________Method to set RegPack Summary________________________
	protected static void SetRegPacksReport(int iRow) throws Exception
	{
		if(iTotalTestCasesFailed==0)
		{
			sRegPackFailedTestCases = "NA";
		}
		else
		{
			sRegPackFailedTestCases = sRegPackFailedTestCases.replace("<p>", "");
			sRegPackFailedTestCases = sRegPackFailedTestCases.replace("</p>", ",");
			if(sRegPackFailedTestCases.endsWith(","))
			{
				sRegPackFailedTestCases = sRegPackFailedTestCases.substring(0, (sRegPackFailedTestCases.length()-1));
			}
		}
		 
		 TestDataImport.SetExcelFile(sTestResultsPath,sDriverFile);
		 TestDataImport.setCellData(sSheetRegressionPacks, iRow, iColRegpackTestCasesExecuted, ""+iTotalTestCasesExecuted,"Blue");
		 TestDataImport.setCellData(sSheetRegressionPacks, iRow, iColRegpackTestCasesPassed, ""+iTotalTestCasesPassed,"green");
		 TestDataImport.setCellData(sSheetRegressionPacks, iRow, iColRegpackTestCasesFailed, ""+iTotalTestCasesFailed,"Red");
		 TestDataImport.setCellData(sSheetRegressionPacks, iRow, iColRegpackFailedTestCases, sRegPackFailedTestCases,"NA");
		 TestDataImport.setCellData(sSheetRegressionPacks, iRow, iColRegpackExecutionDurion, sRegPackDuration,"NA");
			 
	} 
	
	//______________________________________AddColumnInExcelSheet__________________________________________________
	protected static int AddColumninExcelSheet(String sFileName,String sSheetName, String sColumnName, String sPrevColumnName) throws Exception
	{
		
		XSSFWorkbook ExcelWbook;
		XSSFSheet ExcelWsheet;
		XSSFRow Row;
		XSSFCell Cell;
		XSSFFont font;
		CellStyle style;
		String sActualColumnName="";
		
		int iRowCount = 0;
		int iColCount = 0;
		int iColNum=0;
		
		
		FileInputStream File = new FileInputStream(sFileName);
		ExcelWbook = new XSSFWorkbook(File); 
		ExcelWsheet = ExcelWbook.getSheet(sSheetName);
		font= ExcelWbook.createFont();
		style = ExcelWbook.createCellStyle();
		iRowCount = TestDataImport.GetRowCount(sSheetName);
		
		for(int iRow=0;iRow<iRowCount;iRow++)
		{
			Row = ExcelWsheet.getRow(iRow);
			iColCount = Row.getLastCellNum();
			for(int iCol=0;iCol<iColCount;iCol++)
			{
				Cell = Row.getCell(iCol);
				int type = Cell.getCellType();
	 			
	 			 //System.out.println("Cell type:"+type);
	 			  switch(type)
	 			  {
	 			    case 0: double x = Cell.getNumericCellValue();
	 			            int y = (int) x;
	 			            sActualColumnName = ""+y;
	 			            break;
	 			            
	 			    case 1: sActualColumnName = Cell.getStringCellValue();  
	 			            break;
	 			    case 2: sActualColumnName = "";
	 			            break;
	 			  }
	 			  
	 			  if(sActualColumnName.equals(sPrevColumnName))
	 			  {
	 				 iColNum = iCol+1;
	 				 Cell = Row.getCell(iCol+1);
	  	             if (Cell == null) 
	  	             {
	  	            	Cell = Row.createCell(iCol+1);
	  	            	font.setBoldweight(Font.BOLDWEIGHT_BOLD);	
	  	  			    style.setFont(font);
  	  			        Cell.setCellStyle(style);
	  	            	Cell.setCellValue(sColumnName);
	  	            	ExcelWsheet.autoSizeColumn(iColNum);
	  	            	break;
	  	             }
	  	             break;
	 			  }	
	 			 
			}
		}
		
		FileOutputStream fileOut = new FileOutputStream(sFileName);
		ExcelWbook.write(fileOut);
        fileOut.close();
		return iColNum;
		
	}
	
	
	//__________________________________________CreateSummarySheet_________________________________________
	protected static void CreateSummarySheet(String sFileName) throws Exception
	{
		XSSFWorkbook ExcelWbook;
		XSSFSheet ExcelWsheet;
		XSSFRow Row;
	    XSSFFont font;
		CellStyle style;
		
		FileInputStream File = new FileInputStream(sFileName);
		ExcelWbook = new XSSFWorkbook(File);
		ExcelWsheet = ExcelWbook.getSheetAt(0);
		if(!(ExcelWsheet.getSheetName().equalsIgnoreCase("Summary")))
		{
			ExcelWsheet = ExcelWbook.createSheet("Summary");
			ExcelWbook.setSheetOrder(ExcelWsheet.getSheetName(), 0);
			Row = ExcelWsheet.createRow(0);
			font= ExcelWbook.createFont();
			style = ExcelWbook.createCellStyle();
			//font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			Row.createCell(0).setCellStyle(style);
			Row.getCell(0).setCellValue("Test Case ID");
			
			
			Row.createCell(1).setCellValue("Status");
			Row.getCell(1).setCellStyle(style);
			
			Row.createCell(2).setCellValue("Total Test Steps");
			Row.getCell(2).setCellStyle(style);
			
			
			Row.createCell(3).setCellValue("Passed");
			Row.getCell(3).setCellStyle(style);
			
			Row.createCell(4).setCellValue("Failed");
			Row.getCell(4).setCellStyle(style);
			
			Row.createCell(5).setCellValue("Execution Duration");
			Row.getCell(5).setCellStyle(style);
			
			for(int i=0;i<6;i++)
			{
				ExcelWsheet.autoSizeColumn(i);
			}
		}
		
		
		FileOutputStream fileOut = new FileOutputStream(GenericSkins.sTestResultsPath+GenericSkins.sFileTestData);
          
        ExcelWbook.write(fileOut);
          
        fileOut.close();
        
        iColSummaryTestCaseID=0;
        iColSummaryTestCaseStatus=1;
        iColSummaryTotalTestSteps=2;
        iColSummaryPassed=3;
        iColSummaryFailed=4;
        iColSummaryTestCaseDuration=5;
		
	}
	
	//_____________________________________SetSummarySheet_______________________________________________
	protected static void SetSummarySheet(String sFileName,String sSheetName,int iRow) throws Exception
	{
		
		XSSFWorkbook ExcelWbook;
		XSSFSheet ExcelWsheet;
		XSSFRow Row;
		XSSFCell Cell;
		XSSFHyperlink file_link;
		XSSFFont font1, font2, font4, font5;
	    CellStyle style1, style2, style4, style5;
	    
		FileInputStream File = new FileInputStream(sFileName);
		ExcelWbook = new XSSFWorkbook(File);
		ExcelWsheet = ExcelWbook.getSheet(sSheetName);
		//System.out.println("SetSummarySheet||SRegPackTestCaseStatus:"+SRegPackTestCaseStatus);
		font1= ExcelWbook.createFont();
		style1 = ExcelWbook.createCellStyle();
        ExcelWsheet.createRow(iRow);
        Row = ExcelWsheet.getRow(iRow);
        Row.createCell(iColSummaryTestCaseID);
        Cell = Row.getCell(iColSummaryTestCaseID);
        CreationHelper createHelper = ExcelWbook.getCreationHelper();
        file_link = (XSSFHyperlink)createHelper.createHyperlink(Hyperlink.LINK_DOCUMENT);
        //file_link=new XSSFHyperlink(XSSFHyperlink.LINK_DOCUMENT);
        file_link.setAddress("'"+sRegPackTestCaseID+"'!A1");
        Cell.setHyperlink(file_link);
        font1.setColor(IndexedColors.BLUE.getIndex());
        style1.setFont(font1);
        Cell.setCellStyle(style1);
        Cell.setCellValue(sRegPackTestCaseID);
        
        font2= ExcelWbook.createFont();
		style2 = ExcelWbook.createCellStyle();
        
        if(SRegPackTestCaseStatus.equalsIgnoreCase("Passed"))
        {
        	font2.setColor(IndexedColors.GREEN.getIndex());
    		style2.setFont(font2);
    		Row.createCell(iColSummaryTestCaseStatus);
    		XSSFCell Cell2 = Row.getCell(iColSummaryTestCaseStatus);
    		Cell2.setCellStyle(style2);
    		Cell2.setCellValue(SRegPackTestCaseStatus);
        }
        else if(SRegPackTestCaseStatus.equalsIgnoreCase("Failed"))
        {
        	font2.setColor(IndexedColors.RED.getIndex());
    		style2.setFont(font2);
    		Row.createCell(iColSummaryTestCaseStatus);
    		XSSFCell Cell2 = Row.getCell(iColSummaryTestCaseStatus);
    		
    		Cell2.setCellStyle(style2);
    		Cell2.setCellValue(SRegPackTestCaseStatus);
        }
        else
        {   
        	Row.createCell(iColSummaryTestCaseStatus);
        	XSSFCell Cell2 = Row.getCell(iColSummaryTestCaseStatus);
        	Cell2.setCellValue(SRegPackTestCaseStatus);
        }
        
        Row.createCell(iColSummaryTotalTestSteps);
        XSSFCell Cell3 =  Row.getCell(iColSummaryTotalTestSteps);
        Cell3.setCellValue(iTotalTestStepsExecuted);
        
        font4= ExcelWbook.createFont();
		style4 = ExcelWbook.createCellStyle();
		font4.setColor(IndexedColors.GREEN.getIndex());
		style4.setFont(font4);
        Row.createCell(iColSummaryPassed);
        XSSFCell Cell4 = Row.getCell(iColSummaryPassed);
        Cell4.setCellStyle(style4);
        Cell4.setCellValue(iTotalTestStepsPassed);
        
        font5= ExcelWbook.createFont();
		style5 = ExcelWbook.createCellStyle();
		font5.setColor(IndexedColors.RED.getIndex());
		style5.setFont(font5);
        Row.createCell(iColSummaryFailed);
        XSSFCell Cell5 =  Row.getCell(iColSummaryFailed);
        Cell5.setCellStyle(style5);
        Cell5.setCellValue(iTotalTestStepsFailed);
        
        Row.createCell(iColSummaryTestCaseDuration);
        XSSFCell Cell6 =  Row.getCell(iColSummaryTestCaseDuration);
        Cell6.setCellValue(sTCDuration);
        
        for(int i=0;i<6;i++)
		{
			ExcelWsheet.autoSizeColumn(i);
		}
        
        FileOutputStream fileOut = new FileOutputStream(sFileName);
        
        ExcelWbook.write(fileOut);
           
        fileOut.close();
  	
	}
	
	//______________________Method to add columns in RegPacks sheet in driver file_____________________
	
	protected static void AddColumnsInRegPacksSheet() throws Exception
	{
		 iColRegpackTestCasesExecuted=AddColumninExcelSheet(sTestResultsPath+sDriverFile, sSheetRegressionPacks, "Test Cases Executed", "Run Mode");
	     iColRegpackTestCasesPassed=AddColumninExcelSheet(sTestResultsPath+sDriverFile, sSheetRegressionPacks, "Passed", "Test Cases Executed");
	     iColRegpackTestCasesFailed=AddColumninExcelSheet(sTestResultsPath+sDriverFile, sSheetRegressionPacks, "Failed", "Passed");
	     iColRegpackFailedTestCases=AddColumninExcelSheet(sTestResultsPath+sDriverFile, sSheetRegressionPacks, "Failed TestCases", "Failed");
	     iColRegpackExecutionDurion=AddColumninExcelSheet(sTestResultsPath+sDriverFile, sSheetRegressionPacks, "Execution Duration", "Failed TestCases");
	     
	}
	
	//______________________Method to add columns in RegPack TC sheet in driver file_____________________
	
	protected static void AddColumnsInRegPackTCSheet() throws Exception
	{
		  iColRegPackTCTotalStepsExec=TestReportFunctions.AddColumninExcelSheet(sTestResultsPath+sDriverFile, sRegPackID, "Total Test Steps", "Status");
   		  iColRegPackTCTotalStepsPassed=TestReportFunctions.AddColumninExcelSheet(sTestResultsPath+sDriverFile, sRegPackID, "Passed", "Total Test Steps");
   		  iColRegPackTCTotalStepsFailed=TestReportFunctions.AddColumninExcelSheet(sTestResultsPath+sDriverFile, sRegPackID, "Failed", "Passed");
   		  iColRegPackTCExecTime=TestReportFunctions.AddColumninExcelSheet(sTestResultsPath+sDriverFile, sRegPackID, "Execution Duration", "Failed");
   		  
	}
	
	   
	 //________________________________Method to Generate Js file____________________________________________
	    
	    public static void CreateChartReportsJs(String sOverallTestCases, String eetc, String setc, String sftc, String sntc, String stime, String etime, String rtime) throws Exception
	    {
	      String jsValue1 = "var OverallTestCases = "+sOverallTestCases+"; \n var totalTestCaseExecuted = "+eetc+";\nvar barChartValuePassed = " +setc +";\nvar barChartValueFailed = " +sftc +";\nvar barChartValueNoRun = " +sntc +";\nvar StartTime = \"" +stime+"\";\nvar EndTime = \"" +etime +"\";\nvar RunTime = " +"\""+rtime+"\"" +";";
	      //String jsValue2 = "var dataSet_table1 = [[ "+eetc+","+setc+","+sftc+","+stime+","+etime+","+"\""+rtime+"\""+" ],];";
	      try (FileWriter file = new FileWriter(sTestResultsPath+"//Reports//"+"js//overallchartreports.js")) 
	    	{
	    										
	            file.write(jsValue1);
	            file.flush();

	        } 
	    	catch (IOException e)
	    	{
	            e.printStackTrace();
	        }

	    
	    }
	    
	//________________________________Method to Generate Regression pack Summary Table Js file____________________________________________
	 @SuppressWarnings("unused") 
	 public static void CreateExecutionReportJs() throws Exception
	 {
	     
	        //System.out.println("Calling Regression Pack summary......calling Regression Pack summary.....calling Regression Pack summary");
	        String jsValue1 ="";
	        String jsValue2 ="";
	        String jsValue4="";
	        String jsValue5="";
	        
	        int iTCNum=0;
	        int iTSNum=0;
	        int iExecutedTCTSCnt=0;
		    int iRegPackTotalTestCases=0;
	        int iRegPackTestCaseCnt=0;
	       
	       String sTestCaseDescr="";
	       String sTestStepsDescr="";
	       String sScreenShot="";
	       String sfailedresults="";
	       
	       jsValue1 = "var dataSet_table2 = [";
	       
	       for(int k=0;k<aRegPackName.size();k++)
	       {  
	    	   jsValue4="";
	           int cnt=(int) aRegPackTotalTestCases.get(k);
	         
	           int x;
	           int y=0;
	           for(x=iTCNum;y<cnt;y++)
	           {
			     //System.out.println("TestCase Number:"+x);
			     int a=0;
			     int b=0;
					    
			     jsValue5="";
			     if(!(aTCStatus.get(x)=="NO RUN"))
			     {
			      
			      int iTSCnt = (int) aTestStepsExecuted.get(iExecutedTCTSCnt);
			     // System.out.println("tscNT:"+iTSCnt);
			     
			      for(a=iTSNum;b<iTSCnt;b++)
			      {
			      
			       sTestStepsDescr=(String) aTestDescription.get(a);
			       sTestStepsDescr=sTestStepsDescr.replace("'", "");
			       sTestStepsDescr=sTestStepsDescr.replace("\"", "");
			       String text = (String) aScreesnshots.get(a);
			       String wordToFind = "ScreenShots";
			       Pattern word = Pattern.compile(wordToFind);
			       Matcher match = word.matcher(text);
		
			       while (match.find()) 
			       {
			           
			            sScreenShot = text.substring(match.start(), text.length());
			       }
			   
			       String Actlresult = (String) aActualResult.get(a);
			       if(Actlresult.contains(":")){
			    	   String search = ":";
			           Pattern error = Pattern.compile(search);
			           Matcher Aresultmatch = error.matcher(Actlresult);
			           
			           while (Aresultmatch.find()) 
			           {
			               
			                sfailedresults = Actlresult.substring(0,Aresultmatch.start());
			                break;
			           }
			           
			           jsValue5=jsValue5+"[\""+aTestStep.get(a)+"\",\""+sTestStepsDescr+"\",\""+aTestStepData.get(a)+"\",\""+aExpectedResult.get(a)+"\",\""+sfailedresults+"\",\""+aStepStatus.get(a)+"\",\""+sScreenShot+"\"],";
			           a++;
			       }
			       else{
			       jsValue5=jsValue5+"[\""+aTestStep.get(a)+"\",\""+sTestStepsDescr+"\",\""+aTestStepData.get(a)+"\",\""+aExpectedResult.get(a)+"\",\""+aActualResult.get(a)+"\",\""+aStepStatus.get(a)+"\",\""+sScreenShot+"\"],";
			           a++;
			       }
			      }
			      iExecutedTCTSCnt++;
			      iTSNum=a;
			     }
			     sTestCaseDescr=(String) aTCDescr.get(x);
			     sTestCaseDescr=sTestCaseDescr.replace("'", "");
			     sTestCaseDescr=sTestCaseDescr.replace("\"", "");
			     jsValue4=jsValue4+"[\""+aTestCaseID.get(x)+"\",\""+sTestCaseDescr+"\",\""+aTCStatus.get(x)+"\",\""+aTCExecDuration.get(x)+"\",["+jsValue5+"]],";
			     x++;
	           }
			    iTCNum=x;
			    iRegPackTestCaseCnt = (int)aRegPackTotalTestCases.get(k)+1;
			    iRegPackTotalTestCases = (int)aRegPackTotalTestCases.get(k)+1;
	          if(aFailedTestCases.get(k)=="")
	          {
	              jsValue2=jsValue2+ "[\""+aRegPackName.get(k)+"\""+","+"\""+aRegPackTotalTestCases.get(k)+"\""+","+"\""+aRegPackTCExec.get(k)+"\""+","+"\""+aRegPackTCPassed.get(k)+"\""+","+"\""+aRegPackTCFailed.get(k)+"\""+","+"\""+"NA"+"\",[\""+aRegPackName.get(k)+"\",\""+aRegPackTotalTestCases.get(k)+"\",\""+aRegPackTCExec.get(k)+"\",\""+aRegPackTCPassed.get(k)+"\",\""+aRegPackTCFailed.get(k)+"\",\""+aRegPackExecDuration.get(k)+"\"], ["+jsValue4+"]],";

	          }
	          else
	          {
	              jsValue2=jsValue2+ "[\""+aRegPackName.get(k)+"\""+","+"\""+aRegPackTotalTestCases.get(k)+"\""+","+"\""+aRegPackTCExec.get(k)+"\""+","+"\""+aRegPackTCPassed.get(k)+"\""+","+"\""+aRegPackTCFailed.get(k)+"\""+","+"\""+aFailedTestCases.get(k)+"\",[\""+aRegPackName.get(k)+"\",\""+aRegPackTotalTestCases.get(k)+"\",\""+aRegPackTCExec.get(k)+"\",\""+aRegPackTCPassed.get(k)+"\",\""+aRegPackTCFailed.get(k)+"\",\""+aRegPackExecDuration.get(k)+"\"],["+jsValue4+"]],";
	    
	          }
	       
	   }
	   
	    try (FileWriter file = new FileWriter(sTestResultsPath+"//Reports//"+"js//regressionpacksummary.js")) 
	    {
	    	 									
	           //System.out.println(jsValue1 +jsValue2);
	           file.write(jsValue1+jsValue2+"]");
	           file.flush();

	     } 
	    catch (IOException e) 
	    {
	           e.printStackTrace();
	    }

	    
	}
	 
	
}
