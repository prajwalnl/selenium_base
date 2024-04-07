package org.example.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SeleniumTest {
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver","D:\\zzProjects\\selenium-jars-drivers\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    //Open browser
    @Test
    void Open_Browser(){
        driver.get("https://www.google.com");
        Assert.assertEquals(driver.getTitle(),"Google");
    }

    //Open multiple tabs
    @Test
    void Open_Multiple_Tabs() throws InterruptedException {
        driver.get("https://www.google.com");
        // Open a new tab
        ((JavascriptExecutor) driver).executeScript("window.open();");

        // Get all window handles
        Set<String> windowHandles = driver.getWindowHandles();
        List<String> handlesList = new ArrayList<>(windowHandles);

        // Switch to the second tab
        driver.switchTo().window(handlesList.get(1));

        // Close the second tab
        driver.close();

        // Switch back to the first tab
        driver.switchTo().window(handlesList.get(0));

        // Close the first tab
        driver.close();
    }

    //Click on a button
    @Test
    void Click_A_Button(){
        driver.get("https://www.google.com");
        WebElement button = driver.findElement(By.xpath("(//input[@value=\"I'm Feeling Lucky\"])[2]"));
        button.click();
        Assert.assertTrue(driver.getTitle().contains("Google Doodles"));
    }

    //Backward traversing
    @Test
    void Backward_Traversing(){
        driver.get("https://www.google.com");

        WebElement lang1 = driver.findElement(By.xpath("//a[.='Advertising']/../a[1]"));

        Assert.assertEquals(lang1.getText(),"About");
    }

    //Sibling function traversing
    @Test
    void Sibling_Function_Traversing(){
        driver.get("https://www.google.com");

        WebElement followingEle = driver.findElement(By.xpath("//a[.='Advertising']/following::a[1]"));
        WebElement precedingEle = driver.findElement(By.xpath("//a[.='Advertising']/preceding::a[1]"));

        Assert.assertEquals(followingEle.getText(),"Business");
        Assert.assertEquals(precedingEle.getText(),"About");
    }

    //Driver navigation and refresh
    @Test
    void Driver_Navigation_Refresh(){
        driver.get("https://www.fb.com");

        driver.navigate().to("https://www.google.com");

        driver.navigate().back();
        driver.navigate().forward();
        driver.navigate().refresh();
        Assert.assertEquals(driver.getTitle(),"Google");
    }

    //WebElement operations functions
    @Test
    void WebElement_Functions_Operations(){
        driver.get("https://www.google.com");

        WebElement searchInput = driver.findElement(By.name("q"));

        Assert.assertTrue(searchInput.isDisplayed());  //is displayed
        Assert.assertTrue(searchInput.isEnabled());    // is enabled

        searchInput.clear();
        searchInput.sendKeys("TestNG");   //sendkeys send keys
        searchInput.sendKeys(Keys.ENTER);              // send keyboard commands

        driver.navigate().refresh();

        List<WebElement> searchResults = driver.findElements(By.tagName("h3"));    // find elements
        Assert.assertFalse(searchResults.isEmpty());
        String firstResultText = searchResults.get(0).getText();
        Assert.assertFalse(firstResultText.isEmpty());
    }

    //Copy, Cut, Paste etc keyboard shortcuts browser
    @Test
    void Keyboard_Shortcuts(){
        driver.get("https://www.google.com");

        WebElement searchInput = driver.findElement(By.name("q"));

        searchInput.sendKeys("TestNG");   //sendkeys send keys
        searchInput.sendKeys(Keys.CONTROL+"a");
        searchInput.sendKeys(Keys.CONTROL+"c");

        searchInput.sendKeys(Keys.CONTROL+"v");
        searchInput.sendKeys(Keys.CONTROL+"v");

        searchInput.sendKeys(Keys.ENTER);

        WebElement searchInput2 = driver.findElement(By.xpath("//textarea[@name='q']"));

        Assert.assertEquals(searchInput2.getText(),"TestNGTestNG");
    }

    //Font-size, element location, height , width
    @Test
    void Element_Information_Properties(){
        driver.get("https://www.google.com");

        WebElement element = driver.findElement(By.xpath("//div[.='India']"));
        String fontSize = element.getCssValue("font-size");
        System.out.println(fontSize);

        Point loc = element.getLocation();
        System.out.println(loc);

        int x = loc.getX(), y = loc.getY();
        System.out.println("X:"+x+", Y:"+y);

        int h = element.getSize().getHeight();
        int w = element.getSize().getWidth();
        System.out.println("H:"+h+", W:"+w);
    }

    //Handle multiple and find elements
    @Test
    void Find_Elements(){
        driver.get("https://demoqa.com/links");

        var elements = driver.findElements(By.xpath("//div[@id='linkWrapper']/p"));

        for(WebElement ele : elements){
            System.out.print(ele.getText()+", ");
        }

        System.out.println("\n"+elements.size());
    }

    //Handle single select dropdown
    @Test
    void Handle_Single_Select_Dropdown(){
        // Open the website with dropdown
        driver.get("https://the-internet.herokuapp.com/dropdown");

        // Find the dropdown element
        WebElement dropdownElement = driver.findElement(By.id("dropdown")); // replace "dropdown" with the actual ID

        // Create Select object
         Select dropdown = new Select(dropdownElement);

        // Get all options from the dropdown
        List<WebElement> options = dropdown.getOptions();

        // Select an option by index
        dropdown.selectByIndex(1);

        // Select an option by value
        dropdown.selectByValue("2");

        // Select an option by visible text
        dropdown.selectByVisibleText("Option 1");

        // Get all selected options
        List<WebElement> selectedOptions = dropdown.getAllSelectedOptions();
        Assert.assertEquals(selectedOptions.get(0).getText(), "Option 1");
    }

    //Handle multi select dropdown
    @Test
    void Handle_Multi_Select_Dropdown(){
        // Open the website with dropdown
        driver.get("https://mdbootstrap.com/docs/standard/extended/multiselect/");

        // Find the dropdown element
        WebElement dropdownElement = driver.findElement(By.xpath("(//select[@class='select'])[1]"));

        // Create Select object
        Select dropdown = new Select(dropdownElement);

        // Get all options from the dropdown
        List<WebElement> options = dropdown.getOptions();

        // Select an option by index
        dropdown.selectByIndex(1);
        // Deselect an option by index
        dropdown.deselectByIndex(1);

        // Select an option by value
        dropdown.selectByValue("1");
        // Deselect an option by value
        dropdown.deselectByValue("1");

        // Select an option by visible text
        dropdown.selectByVisibleText("Three");
        // Deselect an option by visible text
        dropdown.deselectByVisibleText("Three");

        // Get all selected options
        List<WebElement> selectedOptions = dropdown.getAllSelectedOptions();
        Assert.assertTrue(selectedOptions.isEmpty(), "No options should be selected");

        // Deselect all options
        dropdown.deselectAll();
        Assert.assertTrue(selectedOptions.isEmpty(), "All options should be deselected");
    }

    //Mouse hover actions
    @Test
    void Mouse_Hover_Actions(){
        driver.get("https://the-internet.herokuapp.com/hovers");

        Actions act = new Actions(driver);

        WebElement ele = driver.findElement(By.xpath("(//img[@alt='User Avatar'])[1]"));
        act.moveToElement(ele).perform();

        WebElement ele2 = driver.findElement(By.xpath("(//img[@alt='User Avatar'])[2]"));
        act.moveToElement(ele2).perform();
    }

    //Drag and drop actions
    @Test
    void Drag_And_Drop_Actions(){
        driver.get("https://the-internet.herokuapp.com/drag_and_drop");

        Actions act = new Actions(driver);

        WebElement ele1 = driver.findElement(By.xpath("//div[@id='column-a']"));
        WebElement ele2 = driver.findElement(By.xpath("//div[@id='column-b']"));
        act.dragAndDrop(ele2,ele1).perform();
    }

    //Mouse click actions
    void Mouse_Click_Actions(){
        driver.get("https://demoqa.com/buttons");

        Actions act = new Actions(driver);

        WebElement doubleClick = driver.findElement(By.id("doubleClickBtn"));
        WebElement rightClick = driver.findElement(By.id("rightClickBtn"));

        act.doubleClick(doubleClick).perform();
        act.contextClick(rightClick).perform();

        String doubleClickMsg = driver.findElement(By.id("doubleClickMessage")).getText();
        String rightClickMsg = driver.findElement(By.id("rightClickMessage")).getText();

        Assert.assertEquals(doubleClickMsg,"You have done a double click");
        Assert.assertEquals(rightClickMsg,"You have done a right click");
    }


    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}