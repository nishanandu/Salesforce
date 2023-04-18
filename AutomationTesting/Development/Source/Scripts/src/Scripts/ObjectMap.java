package Scripts;

public class ObjectMap extends GenericSkins
{
	protected static Object[][] objectlistdata=null;
   public static void GetObjectMap() throws Exception
   {
	   int iObjectListRowCount=0;
	   
	   try
	   {
		   TestDataImport.SetExcelFile(sTestResultsPathOR,"ObjectList.xlsx");
		   iObjectListRowCount= TestDataImport.GetRowCount("ObjectMap");
		   for(int iObjRow=1;iObjRow<=iObjectListRowCount;iObjRow++)
		   {
			   sObjListObjectName = TestDataImport.GetCellData("ObjectMap", iColObjListObjectName, iObjRow);
			   
			   if(sObjListObjectName.equalsIgnoreCase(DriverScript.sTestStepObjectName))
			   {
				   sObjListObjectType = TestDataImport.GetCellData("ObjectMap", iColObjListObjectType, iObjRow);
				   sObjListObjectLocator = TestDataImport.GetCellData("ObjectMap", iColObjListObjectLocator, iObjRow);
				   sObjListObjectPath = TestDataImport.GetCellData("ObjectMap", iColObjListObjectPath, iObjRow);
			       break;
			   }
		   }
	   }
	   catch(Exception error_message)
	   {
		   System.out.println("ObjectMap||error||"+error_message.getMessage());
	   }
	 
   }public static void getobjectlist() throws Exception
   {
	   System.out.println("sTestResultsPathOR:" + sTestResultsPathOR);
	   objectlistdata = TestDataImport.readExcel(sTestResultsPathOR,"ObjectList.xlsx","ObjectMap");
		System.out.println("length of data:" + objectlistdata.length);
		System.out.println("data:" + objectlistdata[2][0]);
		System.out.println("data:" + objectlistdata[2][1]);
		System.out.println("data:" + objectlistdata[2][2]);
		System.out.println("data:" + objectlistdata[2][3]);
		
   }
}


