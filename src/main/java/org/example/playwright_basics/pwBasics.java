package org.example.playwright_basics;

import java.util.Scanner;

/* must import */
import com.microsoft.playwright.*;

public class pwBasics {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int again = 0;
        Browser browser;
        BrowserType browserType;
        Page page;

        do {
            System.out.println("Enter '1' to use Playwright.class method, where you must select the browser type & headless state; click '2' to use Naveen Automation method: ");
            int userChoice = sc.nextInt();
            sc.nextLine();

            if (userChoice == 1) {
                /* create new Playwright; use a 'try' clock for safety */
                /* from Playwright.class at line 28 */
                try (Playwright playwright = Playwright.create()) {
                    System.out.println("Select a browser: enter 1 for Chromium, 2 for Firefox, 3 for Safari...");
                    int userBrowser = sc.nextInt(); sc.nextLine();

                    while(userBrowser != 1 && userBrowser != 2 && userBrowser != 3){
                        System.out.println("You must enter 1 for Chromium, 2 for Firefox, or 3 for Safari...");
                        userBrowser = sc.nextInt(); sc.nextLine();
                    }

                    switch(userBrowser){
                        case 1: System.out.println("Chromium..."); browserType = playwright.chromium(); break;
                        case 2: System.out.println("Firefox..."); browserType = playwright.firefox(); break;
                        case 3: System.out.println("Safari..."); browserType = playwright.webkit(); break;
                        default: browserType = playwright.chromium();
                    }

                    System.out.println("Enter 1 for headless mode, 2 for head mode: ");
                    int headless = sc.nextInt(); sc.nextLine();

                    while(headless != 1 && headless != 2){
                        System.out.println("You must enter 1 for headless mode, 2 for head mode...");
                        headless = sc.nextInt(); sc.nextLine();
                    }

                    if(headless == 2){
                        System.out.println("Showing browser in 'head' mode...");
                        browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000));
                    }else{
                        System.out.println("Proceeding with headless mode...");
                        browser = browserType.launch();
                    }

                    page = browser.newPage();

                    System.out.println("Enter the name of the site with its domain to navigate to the website...");
                    String userURL = sc.nextLine();

                    page.navigate("http://" + userURL);

                    String title = page.title();
                    String url = page.url();
                    System.out.println("Page title is '" + title + "'; page URL is '" + url + "'....");

                    browser.close();
                    playwright.close();
                }
            } else if (userChoice == 2) {
                /* from Naveen Automation: https://www.youtube.com/watch?v=s3qvLyKM1as&t=138s */
                /* 15 min */
                Playwright playwright = Playwright.create();
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
                page = browser.newPage();
                page.navigate("https://amazon.com");

                String title = page.title();
                String url = page.url();
                System.out.println("Page title is '" + title + "'; page URL is '" + url + "'....");

                browser.close();
                playwright.close();
            } else {
                System.out.println(
                        "Invalid entry: please enter '1' for Playwright.class method, '2' for Naveen Automation method; any other key will quit the program...");
                userChoice = sc.nextInt();
                sc.next();

                if (userChoice == 1 || userChoice == 2) {
                    System.out.println("Re-running program...");
                    again = 1;
                } else {
                    System.out.println("Quitting program...");
                    again = 0;
                }
            }

            System.out.println("Enter '1' to run the program again, anything else to quit...");
            again = sc.nextInt();

        } while (again == 1);
        System.out.println("Quitting program...");
        sc.close();
    }
}
