package Scripts;

public class TestStepDefinitions extends TestStepActions {
	public static void TestStepDefinition() throws Exception 
   {
	   
	   switch(GenericSkins.sTestStepValidation.toUpperCase())
	   {
	       case "LAUNCHAPPLICATION":
                 LaunchApplication(); 
                 break;
	       case "CHECKOBJECTPROPERTY":
                 CheckObjectProperty();
                 break;
           case "INPUTDATA":
                 InputData();
                 break;
           case "CLICKOBJECT":
                 ClickObject();
                 break;
           case "CHECKOBJECTEXISTENCE":
                 CheckObjectExistence(); 
                 break;
           case "SWITCH":
        	     Switch(); 
                 break;          
           case "CLOSEAPPLICATION":
      	         CloseApplication(); 
                 break;
           case "WAIT":
     	         Wait(); 
     	         break;
           case "FILEUPLOAD":
        	   FileUpload();
        	   break;
           case "OPENFILE":
               OpenFile();
                 break;
           case "ACTIONS":
                 Actions(); 
                 break;
           case "SCROLL":
        	   Scroll();
        	   break;
           case "KEYEVENT":
        	     KeyEvent();
        	     break;
           case "WEBTABLE":
        	     WebTable();
        	     break;
           case "CHECKFILEEXISTENCE":
        	     CheckFileExistence();
      	         break;
           case "INPUTVALUE":
        	   InputValue();
    	         break;
           case "CREATELEADS":
        	   CreateLeads();
        	   break;
           case "SEARCHMODULE":
        	   SearchModule();
        	   break;
        	  
         //  case "WORKINGCALENDAR":
      	       //  WorkingCalendar();
    	       //  break;
	   }
   }

}
