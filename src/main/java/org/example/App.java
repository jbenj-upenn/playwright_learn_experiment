package org.example;
// import java.nio.file.Paths;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class App {
    public static void main(String[] args) {
        try(Playwright playwright = Playwright.create()){
            // SCREENSHOT ------------
            
            // *** tests are headless by default; below sets the test to head and opens an visible browser
            // Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50));
            // Page page = browser.newPage();
            // page.navigate("https://playwright.dev/");
            // page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example.png")));

            // **************************
            // from https://playwright.dev/java/docs/writing-tests
            // Writing Tests ------------------
            
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate("http://playwright.dev");

            // Expect a title to contain a substring ---------
            try{
                assertThat(page).hasTitle(Pattern.compile("Playwright"));
                System.out.println("Title assertion passed; page title contains 'Playwright' in it...");
            }catch(AssertionError e){
                System.out.println("Title assertion failed: " + e.getMessage());
            }
        
            // Create locator - all five are valid

            // Locator getStarted = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Get Started"));
            // Locator getStarted = page.locator("text=Get Started");
            // Locator getStarted = page.getByText("Get Started");
            // Locator getStarted = page.getByText("Get started", new Page.GetByTextOptions().setExact(true));
            Locator getStarted = page.locator(".getStarted_Sjon");
            
            System.out.println("Get Started locator: " + getStarted.textContent());

            // Expect an attribute to be strictly equal to value
            try{
                assertThat(getStarted).hasAttribute("href", "/docs/intro");
                System.out.println("'Get Started' link contains href with '/docs/intro'...");
            }catch(AssertionError e){
                System.out.println("'Get Started' link href assertion failed: " + e.getMessage());
            }

            // Click the 'Get Started' link -------------
            getStarted.click();

            // Expects page to have a heading with 'Installation' as name -------------------------
            try{
                // either assertion works; the first is the original ----------
                // using the following shows three potential elements with the code for how to locate them... :-0

                /* 1. */ assertThat(page.getByRole(AriaRole.HEADING, 
                new Page.GetByRoleOptions().setName("Installation"))).isVisible();
                
                /* 2.  assertThat(page.locator("text=Installation")).isVisible(); */

                System.out.println("Heading assertion passed: 'Installation' heading is visible...");
            }catch(AssertionError e){
                System.out.println("Heading assertion failed: " + e.getMessage());
            }

            page.close();
            browser.close();

        }catch(Exception e){
            System.out.println("An unexpected error has occurred: " + e.getMessage());
        }

    
    }
}