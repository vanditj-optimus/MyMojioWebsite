package com.mojio.test;
import static com.mojio.test.DriverScript.APP_LOGS;
import static com.mojio.test.DriverScript.CONFIG;
import static com.mojio.test.DriverScript.OR;
















import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;


public class Keywords {

	public WebDriver driver;

	int i=0;
	String CDT2 = null;
	String winHandleBefore = null;
	String text = null;
	String text2=null;

	//-------------------------------------Browser Keywords-----------------------------------------

	//Open the browser
	public String openBrowser(String object,String data){  
		APP_LOGS.debug("Opening browser");
		if(CONFIG.getProperty(data).equals("Mozilla"))
			driver=new FirefoxDriver();
		else if(CONFIG.getProperty(data).equals("IE"))
		{
			System.setProperty("webdriver.ie.driver",System.getProperty("user.dir")+("\\IEDriverServer.exe"));
			DesiredCapabilities dc = DesiredCapabilities.internetExplorer();
			dc.setCapability("nativeEvents", false);

			driver=new InternetExplorerDriver(dc);
		}
		else if(CONFIG.getProperty(data).equals("Chrome"))
		{ System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+("\\chromedriver.exe"));

		driver=new ChromeDriver();
		}
		long implicitWaitTime=Long.parseLong(CONFIG.getProperty("implicitwait"));
		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		return Constants.KEYWORD_PASS;

	}

	//Navigate to a URL
	public String navigate(String object,String data){		
		APP_LOGS.debug("Navigating to URL");
		try{
			driver.navigate().to(CONFIG.getProperty(data));
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" -- Not able to navigate";
		}
		return Constants.KEYWORD_PASS;
	}


	//Close the browser
	public  String closeBroswer(String object, String data){
		APP_LOGS.debug("Closing the browser");
		try{
			driver.quit();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+"Unable to close browser. Check if its open"+e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	//---------------------------------------Click--------------------------------------------------

	//Click using the link text
	public String clickLinkByLinkText(String object,String data){
		APP_LOGS.debug("Click using the link text");
		try{
			driver.findElement(By.linkText(OR.getProperty(object))).click();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" -- Not able to click on link"+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	//clicking using ID attribute
	public  String clickByID(String object,String data){
		APP_LOGS.debug("clicking using ID attribute");
		try{
			driver.findElement(By.id(OR.getProperty(object))).click();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" -- Not able to click on Button"+e.getMessage();
		}

		return Constants.KEYWORD_PASS;
	}

	//clicking using xpath attribute
	public  String clickByXpath(String object,String data){
		APP_LOGS.debug("Clicking using xpath attribute");
		try{
			driver.findElement(By.xpath(OR.getProperty(object))).click();
		}catch(Exception e){
			e.printStackTrace();
			return Constants.KEYWORD_FAIL+" -- Not able to click on Button"+e.getMessage();
		}

		return Constants.KEYWORD_PASS;
	}

	//-------------------------------------Verify---------------------------------------------------


	//Verify if the text is present in page
	public String isTextPresent(String Object, String data){
		APP_LOGS.debug("Verify if the text is present in page");
		try{
			boolean b = driver.getPageSource().contains(data);
			if(b==true)
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL;
		}
		catch(Exception e){
			return e.getMessage();
		}
	}


	//Verify the text is not present in the page
	public String textNotPresent(String object, String data){
		APP_LOGS.debug("Verify the text is not present in the page");
		try{
			boolean b = driver.getPageSource().contains(data);
			if(b==true)
				return Constants.KEYWORD_FAIL;
			else
				return Constants.KEYWORD_PASS;
		}
		catch(Exception e){
			return e.getMessage();
		}
	}

	//Verify the current Date Time Text
	public String verifyCurrentDateByXpath(String object, String data){
		APP_LOGS.debug("Verify the current Date Time Text");
		try{
			String expected=currentDate1();
			String actual=driver.findElement(By.xpath(OR.getProperty(object))).getText();
			if(actual.contains(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL;
		}
		catch(Exception e){
			return Constants.KEYWORD_FAIL + e.getMessage();
		}
	}

	//Verify the current Date Time Text
	public String verifyCurrentDatePresent(String Object, String data){
		APP_LOGS.debug("Verify the current Date Time Text");
		try{
			String data1=currentDate1();
			boolean b = driver.getPageSource().contains(data1);
			if(b==true)
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL;
		}
		catch(Exception e){
			return e.getMessage();
		}
	}

	//Verify the Current url
	public  String verifyCurrentUrl(String object,String data){
		APP_LOGS.debug("Verify the Current url");
		String currenturl=null;
		String url=null;
		try{
			currenturl = driver.getCurrentUrl();
			url=OR.getProperty(object);
			if(currenturl.equals(url))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Not able get the URL";
		}
	}


	//Function to verify if element is present  -- By xpath
	public String verifyElementPresentXpath(String object, String data){
		APP_LOGS.debug("Function to verify if element is present  -- By xpath");
		try{
			driver.findElement(By.xpath(OR.getProperty(object)));
		}catch(Exception e){
			return e.getMessage();				
		}
		return Constants.KEYWORD_PASS;
	}

	//Function to verify if element is present  -- By Id
	public String verifyElementPresentId(String object, String data){
		APP_LOGS.debug("Function to verify if element is present  -- By xpath");
		try{
			driver.findElement(By.id(OR.getProperty(object)));
		}catch(Exception e){
			return e.getMessage();				
		}
		return Constants.KEYWORD_PASS;
	}

	//Function to verify if element is not present  -- By xpath
	public String elementNotPresentXpath(String object, String data){
		APP_LOGS.debug("Function to verify if element is not present  -- By xpath");
		try{
			Boolean b=driver.findElement(By.xpath(OR.getProperty(object))).isDisplayed();
			if(b==false)
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();								
		}
	}

	//Function to verify if element is not present  -- By id
	public String elementNotPresentid(String object, String data){
		APP_LOGS.debug("Function to verify if element is not present  -- By id");
		try{
			Boolean b=driver.findElement(By.id(OR.getProperty(object))).isDisplayed();
			if(b==false)
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();								
		}
	}


	//Verify values selected in the  dropdown
	public String verifyDropdownValueByXpath(String object, String data){
		APP_LOGS.debug("Verify values selected in the  dropdown");
		try{
			Select select = new Select(driver.findElement(By.xpath(OR.getProperty(object))));   
			String SelectedValue=select.getFirstSelectedOption( ).getText( );
			if(SelectedValue.equals(data))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL;
		}catch (Exception e){
			return Constants.KEYWORD_FAIL + e.getMessage();
		}
	}

	//Verify if the text is present in a object
	public String verifyTextByXpath(String object, String data){
		APP_LOGS.debug("Verify if the text is present in a object");
		try{
			String actual=driver.findElement(By.xpath(OR.getProperty(object))).getText();
			String expected=data;
			if(actual.equals(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL+" -- text not verified "+actual+" -- "+expected;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}
	}

	//Verify if the text is present in a object using ID
	public String verifyTextById(String object, String data){
		APP_LOGS.debug("Verify if the text is present in a object using ID");
		try{
			String actual=driver.findElement(By.id(OR.getProperty(object))).getText();
			String expected=data;
			if(actual.equals(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL+" -- text not verified "+actual+" -- "+expected;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}
	}

	//Function to verify if element is Enabled  -- By id
	public String verifyElementEnabledId(String object, String data){
		APP_LOGS.debug("Function to verify if element is Enabled  -- By id");
		try{
			Boolean b=driver.findElement(By.id(OR.getProperty(object))).isEnabled();
			if(b==true)
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();								
		}
	}

	//Function to verify if element is Disabled  -- By Id
	public String verifyElementDisabledId(String object, String data){
		APP_LOGS.debug("Function to verify if element is Diabled  -- By Id");
		try{
			Boolean b=driver.findElement(By.id(OR.getProperty(object))).isEnabled();
			if(b==true)
				return Constants.KEYWORD_FAIL;
			else
				return Constants.KEYWORD_PASS;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();	
		}
		
	}
		
		// Verify Mojio Id selected was assigned to the vehicle (My Vehicles)
		
		public String verifyMojioId(String object, String data) {
			APP_LOGS.debug ("Verify if the vehicle was assigned to the right Mojio ");
			try{
				String data1= "359878765633665";
				Select select = new Select(driver.findElement(By.xpath(OR.getProperty(object))));
				WebElement option = select.getFirstSelectedOption(); 
				String data2 = option.getText() ;	
				System.out.println(data2);
				if (data2.equalsIgnoreCase(data1))
				return Constants.KEYWORD_PASS;
				else
					return Constants.KEYWORD_FAIL;
	           }
				catch(Exception e){
					return Constants.KEYWORD_FAIL+e.getMessage();								
		       }
	}



	//----------------------------------------------Input--------------------------------------------

	//Write the email using current date and time
	public String writeCurrentDateEmailById(String object, String data){
		APP_LOGS.debug("Write the email using current date and time");
		try{
			String data1=currentDate1();
			String data2=data1+"@testing.com";
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data2);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	// Verify if the vehicle was assigned to the right Mojio
	
	public String verifyVehicleId(String object, String data) {
		APP_LOGS.debug ("Verify if the vehicle was assigned to the right Mojio ");
		try{
			String data1=currentDate1();
			Select select = new Select(driver.findElement(By.id(OR.getProperty(object))));
			WebElement option = select.getFirstSelectedOption(); 
			String data2 = option.getText() ;	
			if (data1.equalsIgnoreCase(data2))
			return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL;
           }
			catch(Exception e){
				return Constants.KEYWORD_FAIL+e.getMessage();								
	       }
	}

			//Write the IMEI using current date and time
	
	public String writeCurrentDateIMEIById(String object, String data){
		APP_LOGS.debug("Write the IMEI using current date and time");
		try{
			String data1=currentDate1();
			String data2=data1.substring(3);
			String data4="2345";
			String data3="999"+data2+data4;
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data3);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	//Write the Iccid and Msisdn using current date and time
	public String writeCurrentDateSimCsvById(String object, String data){
		APP_LOGS.debug("Write the Iccid and Msisdn using current date and time");
		try{
			String data1=currentDate1();
			String data2=data1.substring(3);
			String data3=null;
			if(Integer.parseInt(data)==1)
				data3=data2+"1"+","+data2+"2";
			else
				data3=data2+"3"+","+data2+"4";
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data3);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	

	//Write the Iccid using current date and time
	public String writeCurrentDateICCIDById(String object, String data){
		APP_LOGS.debug("Write the Iccid using current date and time");
		try{
			String data1=currentDate1();
			String data2=data1.substring(3);
			String data3=null;
			data3=data2+"1";
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data3);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	//Write the email using current date and time by Xpath
	public String writeCurrentDateEmailByXpath(String object, String data){
		APP_LOGS.debug("Write the email using current date and time by xpath");
		try{
			String data1=currentDate1();
			String data2=data1+"@testing.com";
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data2);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}


	//Input the current date 
	public String writeCurrentDateById(String object, String data){
		APP_LOGS.debug("Input the current date");
		try{
			String data1=currentDate1();
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data1);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();

		}
		return Constants.KEYWORD_PASS;

	}

	//function to return current date and time
	public String currentDate1() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("MMMddHHmmss");   
		Date now = new Date();
		String CDT1 = sdfDate.format(now);
		if(i==0){
			CDT2=CDT1;
			i=i+1;
			return CDT2;
		}
		else
			return CDT2;

	}

	//function to return date in mm/dd/yyyy format
	public String getDate(int period)
	{
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("MM/dd/YYYY");
		currentDate.add(Calendar.DAY_OF_MONTH, period);
		String date = formatter.format(currentDate.getTime());
		return date;
	}

	//Input the date 
	public String writeDateById(String object, String data){
		APP_LOGS.debug("Input the date");
		try{
			int data4=Integer.parseInt(data);
			String data5=getDate(data4);
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data5);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	//Write data in text box, text area
	public  String writeInInput(String object,String data){
		APP_LOGS.debug("Write data in text box, text area");

		try{
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}
	
	// Write the imported IMEI
	
			public  String writeInIMEIAfterImport(String object,String data){
				APP_LOGS.debug("Write the imported IMEI");
				try{
				    String	data1 = "359878765633665" ;
					driver.findElement(By.id(OR.getProperty(object))).sendKeys(data1); 
				   }
					catch(Exception e){
						return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();

	               }
				    return Constants.KEYWORD_PASS;
    }

	//Write data in text box, text area by Xpath
	public  String writeInInputByXpath(String object,String data){
		APP_LOGS.debug("Write data in text box, text area by xpath");

		try{
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}
		
		
		
	



	//------------------------------------------Dropdown Functions-----------------------------------------------

	//Choose value from dropdown based on value
	public String selectDropdownValueByValueId(String object, String data){
		APP_LOGS.debug("Choose value from dropdown based on value");
		try{
			Select select = new Select(driver.findElement(By.id(OR.getProperty(object))));
			select.selectByValue(data);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Not able to choose value based on value attrobute";
		}
		return Constants.KEYWORD_PASS;
	}  

	//Choose value from dropdown based on visible text
	public String selectDropdownValueByVisibleTextId(String object, String data){
		APP_LOGS.debug("Choose value from dropdown based on visible text");
		try{
			Select select = new Select(driver.findElement(By.id(OR.getProperty(object))));
			select.selectByVisibleText(data);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Not able choose value based on visible text";
		}
		return Constants.KEYWORD_PASS;
	}  

	//Choose value from dropdown based on visible text using Xpath
	public String selectDropdownValueByVisibleTextXpath(String object, String data){
		APP_LOGS.debug("Choose value from dropdown based on visible text using Xpath");
		try{
			Select select = new Select(driver.findElement(By.xpath(OR.getProperty(object))));
			select.selectByVisibleText(data);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Not able choose value based on visible text";
		}
		return Constants.KEYWORD_PASS;
	}  

	//Choose value from dropdown based on value
	public String selectDropdownValueByValueName(String object, String data){
		APP_LOGS.debug("Choose value from dropdown based on value using Name");
		try{
			Select select = new Select(driver.findElement(By.name(OR.getProperty(object))));
			select.selectByValue(data);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Not able to choose value based on value attrobute using Name";
		}
	
	 return Constants.KEYWORD_PASS; 
	 
	  }
		
		// Select an IMEI value from My Vehicles drop-down
		public String selectIMEIValue(String object, String data){
			APP_LOGS.debug("Choose value from dropdown based on value using Name");
			try{
				String data1 = "359878765633665";
			     driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			   }
			     catch(Exception e){
						return Constants.KEYWORD_FAIL+" Not able to choose value based on value attrobute using Name";
					}
					return Constants.KEYWORD_PASS;
	    } 

	//------------------------------------------Get Text of Web element and verify It------------------------------------------

	//Function to retrieve text from read-only field-- By ID
	public String getTextId(String object, String data){
		APP_LOGS.debug("Function to retrieve text from read-only field- By ID");
		try{
			text =driver.findElement(By.id(OR.getProperty(object))).getText();
		}catch(Exception e){
			return Constants.KEYWORD_PASS+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	//Function to verify the Text of the acquired text using Id
	public String verifyAcquiredTextId(String object, String data){
		APP_LOGS.debug("Function to verify the Text of the acquired text");
		String text1=null;
		try{
			text1 =driver.findElement(By.id(OR.getProperty(object))).getText();
			if(text.equals(text1))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL;
		}catch(Exception e){
			return Constants.KEYWORD_PASS+e.getMessage();
		}
	}

	//Function to retrieve text from read-only field-- By Xpath
	public String getTextXpath(String object, String data){
		APP_LOGS.debug("Function to retrieve text from read-only field- By Xpath");
		try{
			text2 =driver.findElement(By.xpath(OR.getProperty(object))).getText();
		}catch(Exception e){
			return Constants.KEYWORD_PASS+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	//Function to verify the Text of the acquired text using Xpath
	public String verifyAcquiredTextXpath(String object, String data){
		APP_LOGS.debug("Function to verify the Text of the acquired text");
		String text1=null;
		try{
			text1 =driver.findElement(By.xpath(OR.getProperty(object))).getText();
			if(text2.equals(text1))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL;
		}catch(Exception e){
			return Constants.KEYWORD_PASS+e.getMessage();
		}
	}

	//----------------------------------------Clear Field--------------------------------------------


	//Function to clear text using ID
	public String clearFieldById(String object, String data){
		APP_LOGS.debug("Function to clear text using ID");
		try{
			driver.findElement(By.id(OR.getProperty(object))).clear();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	//---------------------------------------------New Window----------------------------------------------
	//Click on a new window
	public String clickNewWindow(String object, String data){
		APP_LOGS.debug("Click on a new window");
		try{
			winHandleBefore = driver.getWindowHandle();
			for(String winHandle : driver.getWindowHandles()){
				driver.switchTo().window(winHandle);
			}		
			driver.findElement(By.id(OR.getProperty(object))).click();
			driver.switchTo().window(winHandleBefore);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Not able to accept the alert";
		}
		return Constants.KEYWORD_PASS;
	}

	//Function to accept the alert
	public String acceptAlert(String object, String data){
		APP_LOGS.debug("Function to accept the alert");
		try{
			driver.switchTo().alert().accept();
		}catch(Exception e){
			e.printStackTrace();
			return Constants.KEYWORD_FAIL+ "Not able to accept the alert"+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	//clicking using xpath attribute and then accepting the alert
	public  String acceptAlertOnBtnByXpath(String object,String data){
		APP_LOGS.debug("clicking using xpath attribute and then accepting the alert");
		try{
			driver.findElement(By.xpath(OR.getProperty(object))).click();
			Alert alert= driver.switchTo().alert();
			alert.accept();

		}catch(Exception e){
			e.printStackTrace();
			return Constants.KEYWORD_FAIL+" -- Not able to click on Button"+e.getMessage();
		}

		return Constants.KEYWORD_PASS;
	}

	//clicking using xpath attribute and then accepting the alert
	public  String acceptAlertOnBtnById(String object,String data){
		APP_LOGS.debug("clicking using xpath attribute and then accepting the alert");
		try{
			driver.findElement(By.id(OR.getProperty(object))).click();
			Alert alert= driver.switchTo().alert();
			alert.accept();

		}catch(Exception e){
			e.printStackTrace();
			return Constants.KEYWORD_FAIL+" -- Not able to click on Button"+e.getMessage();
		}

		return Constants.KEYWORD_PASS;
	}


	//Navigate to new Window
	public String navigateNewWindow(String object, String data){
		APP_LOGS.debug("Navigate to new Window");
		try{
			winHandleBefore = driver.getWindowHandle();

			for(String winHandle : driver.getWindowHandles()){
				driver.switchTo().window(winHandle);
			}					
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Not able to accept the alert";
		}
		return Constants.KEYWORD_PASS;
	}
	//Return to Old Window
	public String returnOldWindow(String object, String data){
		APP_LOGS.debug("Click on a new window");
		try{
			driver.close();
			driver.switchTo().window(winHandleBefore);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Not able to accept the alert";
		}
		return Constants.KEYWORD_PASS;
	}

	//Pause the execution for 5 seconds
	public String pause(String object, String data){
		APP_LOGS.debug("Pause the execution for 5 seconds");
		try{
			Thread.sleep(5000);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Not able pause the execution";
		}
		return Constants.KEYWORD_PASS;
	}

	//-----------------------------Capture Screenshot----------------------------------

	public void captureScreenShot(String Filename) throws IOException {
		APP_LOGS.debug("taking screen shot according to config");

		try{
			File scrFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"//Screenshots//"+Filename+".jpg"));
		}
		catch(Exception e)
		{
			APP_LOGS.debug("not able to take screen shots"+e.getMessage());
		}
	}

	//-------------------------------------Scroll down the page----------------------------------------------------

	public String scrollPageBottom(String object, String data) {
		APP_LOGS.debug("Scrooling down the page");
		try{
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollTo(0,Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight));");
		} catch(Exception e){
			return Constants.KEYWORD_FAIL+" Not able scroll down the page";
		}
		return Constants.KEYWORD_PASS;
	}


	public String scrollPageSectionBottom(String object, String data) {
		APP_LOGS.debug("Scroling down the section of the page");
		try{
			WebElement target = driver.findElement(By.id(OR.getProperty(object)));        
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",target); 
			Thread.sleep(500);
		} catch(Exception e){
			return Constants.KEYWORD_FAIL+" Not able scroll down the page";
		}
		return Constants.KEYWORD_PASS;
	}

	//----------------------------------Browse Button-------------------------------------------------------------------

	//Browse the Event file
	public String browseEventFile(String object, String data) {
		APP_LOGS.debug("Browse the Event file");
		try{
			WebElement filepath=driver.findElement(By.id(OR.getProperty(object)));
			filepath.sendKeys(System.getProperty("user.dir")+"\\src\\com\\mojio\\config\\events.json");
		} catch(Exception e){
			return Constants.KEYWORD_FAIL+" Not able Browse the File";
		}
		return Constants.KEYWORD_PASS;
	}

	//Browse the Report file
	public String browseReportFile(String object, String data) {
		APP_LOGS.debug("Browse the Report file");
		try{
			WebElement filepath=driver.findElement(By.id(OR.getProperty(object)));
			filepath.sendKeys(System.getProperty("user.dir")+"\\src\\com\\mojio\\config\\events.json");
		} catch(Exception e){
			return Constants.KEYWORD_FAIL+" Not able Browse the File";
		}
		return Constants.KEYWORD_PASS;
	}
	
	 

}

