package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class DropdownUtil extends ElementUtil {

    public DropdownUtil(WebDriver driver) {
        super(driver);
    }

    public void doSelectByIndex(By locator, int index) {
        Select select = new Select(getElement(locator));
        select.selectByIndex(index);

    }

    public boolean selectByVisibleText(By locator, String text) {
        Select select = new Select(getElement(locator));
        select.selectByVisibleText(text);
        return isDropDownValueSelected(select, text);
    }

    public boolean doSelectByValue(WebElement locator, String value) {
        Select select = new Select(locator);
        select.selectByValue(value);
        return isDropDownValueSelected(select, value);
    }

    public boolean doSelectByValue(By locator, String value) {
        Select select = new Select(getElement(locator));
        select.selectByValue(value);
        return isDropDownValueSelected(select, value);
    }

    public boolean isDropDownValueSelected(Select select, String expValue) {
        if (select.getFirstSelectedOption().getText().contains(expValue)) {
            System.out.println(expValue + ": is selected");
            return true;
        }
        return false;
    }

    public void doSelectDropDown(By locator, String value) {
        Select select = new Select(getElement(locator));
        List<WebElement> optionsList = select.getOptions();
        iterateDropDownAndSelect(optionsList, value);
    }

    public void selectDropDownWithoutSelect(By locator, String value) {
        List<WebElement> optionsList = getElements(locator);
        iterateDropDownAndSelect(optionsList, value);

    }

    private void iterateDropDownAndSelect(List<WebElement> optionsList, String value) {
        System.out.println("total options: " + optionsList.size());
        for (WebElement e : optionsList) {
            String text = e.getText();
            System.out.println(text);
            if (text.equals(value)) {
                e.click();
                break;
            }
        }
    }


}
