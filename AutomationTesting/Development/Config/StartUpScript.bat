title StartUpScript
set automationTestScriptsLocation=D:\GWP\SalesForce\AutomationTesting\Development\Source\Scripts
set TestNGLocation=D:\GWP\SalesForce\AutomationTesting\Development\Config
set TestNG=testng.xml

cd %automationTestScriptsLocation%
set classpath=%automationTestScriptsLocation%\bin;%automationTestScriptsLocation%\Library\*
java org.testng.TestNG %TestNGLocation%\%TestNG%
pause


