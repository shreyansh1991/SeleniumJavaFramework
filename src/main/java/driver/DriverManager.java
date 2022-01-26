package driver;

import java.util.Objects;

import org.openqa.selenium.WebDriver;

public final class DriverManager {

	/**
	 * Private constructor to avoid external instantiation
	 */
	private DriverManager() {}

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>() ;


	public static WebDriver getDriver() {
		return driver.get();
	}


	static void setDriver(WebDriver driverref) {
		if(Objects.nonNull(driverref)) {
			driver.set(driverref);
		}
	}

	static void unload() {
		driver.remove();
	}

}