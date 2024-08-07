package org.example.playwright_basics;

import java.util.InputMismatchException;
import java.util.Scanner;

/* must import */
import com.microsoft.playwright.*;
import com.microsoft.playwright.BrowserType.LaunchOptions;

public class pwBasics {
    static Scanner sc = new Scanner(System.in);

    public static int initialUserChoice(Scanner sc){
        int attempts = 0;
        while(attempts < 2){
            System.out.println("Enter '1' for Playwright.class method, '2' for Naveen Automation method...");
            try{
                int userChoice = sc.nextInt();
                sc.nextLine();
                if(userChoice == 1 || userChoice == 2){
                    return userChoice;
                }else{
                    System.out.println("Invalid entry; please try again...");
                }
            }catch(InputMismatchException ime){
                System.out.println("Are you sure you want to quit the program? \nEnter '1' for Playwright.class method, '2' for Naveen Automation, anything else to quit...");
                sc.nextLine();
            }
            attempts++;
        }
        System.out.println("Invalid input received twice; quitting program...");
        return -1; 
    }
    public static int browserUserChoice(Scanner sc){
        int attempts = 0;
        while(attempts < 3){
            System.out.println("Enter '1' for Chromium, '2' for Firefox, or '3' for Safari...");
            try{
                int userChoice = sc.nextInt(); sc.nextLine();
                if(userChoice == 1 || userChoice == 2 || userChoice == 3){
                    return userChoice;
                }else{
                    System.out.println("Invalid entry; please enter '1' (Chromium), '2' (Firefox), or '3' (Safari)...");
                }
            }catch(InputMismatchException ime){
                System.out.println("Entering a non-Integer will quit the program; are you sure you want to quit? \nEnter '1' (Chromium), '2' (Firefox), or '3' (Safari)...");
                sc.nextLine();
            }
            attempts++;
        }
        System.out.println("Invalid input received three times; quitting program...");
        return -1;
    }

    public static void main(String[] args) {
        int again = 0;
        
        do {
            Browser browser;
            BrowserType browserType;
            Page page;
            boolean isChromeOrEdge = false;
            LaunchOptions lo = new LaunchOptions();

            
            int userChoice = initialUserChoice(sc);

            if (userChoice == 1) {
                /* create new Playwright; use a 'try' clock for safety */
                /* from Playwright.class at line 28 */
                try (Playwright playwright = Playwright.create()) {
                
                    int userBrowser = browserUserChoice(sc);

                    if(userBrowser == -1){
                        System.out.println("Quitting program...");
                        break;
                    }

                    switch(userBrowser){
                        case 1: System.out.println("Chromium..."); 
                                System.out.println("Enter '1' if you want to use 'Edge' or 'Chrome' instead of 'Chromium'; enter '2' if not...");
                                int specBrowser = sc.nextInt(); sc.nextLine();
                                if(specBrowser == 1){
                                    isChromeOrEdge = true;
                                    lo = new LaunchOptions();
                                    System.out.println("Enter '1' for Chrome, enter '2' for Edge...");
                                    specBrowser = sc.nextInt(); sc.nextLine();
                                    if(specBrowser == 1){
                                        System.out.println("Chrome browser selected...");
                                        lo.setChannel("chrome");
                                    }else{
                                        System.out.println("Edge browser selected...");
                                        lo.setChannel("msedge");
                                    }
                                }
                                    browserType = playwright.chromium(); break;
                        case 2: System.out.println("Firefox..."); browserType = playwright.firefox(); break;
                        case 3: System.out.println("Safari..."); browserType = playwright.webkit(); break;
                        default: browserType = playwright.chromium();
                    }

                    System.out.println("Enter '1' for headless mode, '2' for head mode...");
                    int headless = sc.nextInt(); sc.nextLine();

                    while(headless != 1 && headless != 2){
                        System.out.println("You must enter '1' for headless mode, '2' for head mode...");
                        headless = sc.nextInt(); sc.nextLine();
                    }

                    if(headless == 2){
                        System.out.println("Showing browser in 'head' mode...");
                        if(isChromeOrEdge){
                            lo.setHeadless(false).setSlowMo(1000);
                            browser = browserType.launch(lo);
                        }else{
                            browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000));
                        }
                        
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
                    System.out.println("Quitting program...");
                    break;
            }
    
            System.out.println("Enter '1' to run the program again, anything else to quit...");
            again = sc.nextInt();

        } while (again == 1);

        System.out.println("Quitting program...");
        sc.close();
    }
}
