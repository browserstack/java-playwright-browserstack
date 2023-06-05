package tests;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import runners.BstackRunner;

public class LocalTest extends BstackRunner {

	@Test
	void bstackSampleLocalTest() {
		try {
			page.navigate("http://bs-local.com:45454/");
			String validateContent = page.title();
			assertTrue(validateContent.contains("BrowserStack Local"), "Local content not validated!");
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
	}
}
