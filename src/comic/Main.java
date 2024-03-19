/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class Main {
    ArrayList<ComicBook> comicList;
    int numberOfComics;
    String filePath = "comicEntries.txt";
    ComicManagement comicManager;
    Scanner sc = new Scanner(System.in);
    
    public void loadData(){
        comicList = new ArrayList<>();
        try {
            comicManager = new ComicManagement(filePath);
            comicList = comicManager.loadComicList();
            numberOfComics = comicList.size();
            
            
        } catch (ComicException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void showList (){
        comicManager.showComicList();
    }
    
    public void delete() {
       numberOfComics = comicManager.deleteComic(99);
    }
    
    public int getInteger(String message, String errorMessage){
        boolean isValid = false;
        int input = 0;
        do {            
            try {
                System.out.print(message);
                input = Integer.parseInt(sc.nextLine());
                if (input <= 0 || input >= 9999) {
                    throw new Exception();
                }
                else
                    isValid = true;
            } catch (Exception e) {
                System.out.println(errorMessage);
            }
            
        } while (!isValid);
        
            return input;
    }
    public double getDouble(String message, String errorMessage){
        boolean isValid = false;
        double input = 0;
        do {            
            try {
                System.out.print(message);
                input = Double.parseDouble(sc.nextLine());
       
                if (input <= 0 || input >= 1000) {
                    throw new Exception();
                }
                else
                    isValid = true;
            } catch (Exception e) {
                System.out.println(errorMessage);
            }
            
        } while (!isValid);
        
            return input;
    }
    
    public String getString(String message, String errorMessage){
        String input = "";
        boolean isValid = false;
        String pattern = "^\\s+$";
        do {            
            try {
                System.out.print(message);
                input = sc.nextLine();
                if (input.equals("") || input.matches(pattern)) {
                    throw new Exception();
                }
                else {
                    isValid = true;
                    input = toUpperFirstLetter(input);
                }
                
            } catch (Exception e) {
                System.out.println(errorMessage);
            }
        } while (!isValid);
        
        return input;
    }
    
    public String toUpperFirstLetter(String unmodifiedString){
        
        char[] chars = unmodifiedString.toLowerCase().toCharArray();
        boolean isFound = false;
        for (int i = 0; i < chars.length; i++) {
            if (!isFound && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                isFound = true;
            } else if (!Character.isLetter(chars[i])) { 
                isFound = false;
          }
        }
        return String.valueOf(chars);
    }
   public int searchNewId() {
        boolean checkID = false;
        int nid;
        int id = 0;
        for ( nid = 0 ;  nid < comicList.get(comicList.size()-1).getId(); nid ++){
        for (ComicBook comic : this.comicList) {
            if (comic.getId() != nid) {             
                checkID = true;
                nid = comic.getId();    
                id = nid;
            }
        }
        }
        if (!checkID) {
            id = (comicList.get(comicList.size()-1).getId()) + 1 ;
        }       
    
    return id;
    }
   public  void SearchTitle(){
       String Title;
       Title = getString("Enter Title of the comic: ", "Accept a string with at least 1 visible character and at most 33 visible characters! Please try again.");
       int b = 0;
       int a = 0;
       ArrayList<String> showInfo = new ArrayList<>();
       ArrayList<ComicBook> CM = new ArrayList<>();
       for( int i = 0 ; i < comicList.size();i++){
           if(comicList.get(i).getTitle().contains(Title)==true){
               b = 1;
               
               CM.add(comicList.get(i));
               
           }
       }
       if(b==0){
           System.out.println("Not Found title. Please enter another title!");
       }else{
        String wall = "|";
        int id, volume;
        String title, author;
        double rentalPrice;
        showInfo.add("+-------+-------------------------------------+---------------+-------------------------------------+--------+");
        showInfo.add("| ID    | Title                               | Rental Price  | Author                              | Volume |");               
        showInfo.add("+-------+-------------------------------------+---------------+-------------------------------------+--------+");
        for (ComicBook comicBook : CM ) {
            id = comicBook.getId();
            title = comicBook.getTitle();
            rentalPrice = comicBook.getRentalPrice();
            author = comicBook.getAuthor();
            volume = comicBook.getVolume();
            
            showInfo.add(String.format(wall + "  %04d " + wall + " %-33s   " + wall + "      %6.2f $ " + wall + " %-33s   " + wall + "   %4d " + wall, id, title, rentalPrice, author, volume));
        }                                                                
        showInfo.add("+-------+-------------------------------------+---------------+-------------------------------------+--------+");
        showInfo.add(String.format("| TOTAL | %-4d entry(s)                                                                                      |", CM.size()));
        showInfo.add("+-------+-------------------------------------+---------------+-------------------------------------+--------+");
    
        for (String info : showInfo) {
            System.out.println(info);
        }
       }
   }
   public void SearchAuthor(){
       String Author;
       Author = getString("Please enter Author's name: ","Accept a string with at least 1 visible character and at most 30 visible characters! Please try again.");
       int b = 0;
       int a = 0;
       ArrayList<String> showInfo = new ArrayList<>();
       ArrayList<ComicBook> CM = new ArrayList<>();
       for( int i = 0 ; i < comicList.size();i++){
           if(comicList.get(i).getAuthor().toLowerCase().equals(Author.toLowerCase())==true){
               b = 1;
               
               CM.add(comicList.get(i));
               
           }
       }
       if(b==0){
           System.out.println("Not Found Author's name. Please enter another Author's name!");
       }else{
        String wall = "|";
        int id, volume;
        String title, author;
        double rentalPrice;
        showInfo.add("+-------+-------------------------------------+---------------+-------------------------------------+--------+");
        showInfo.add("| ID    | Title                               | Rental Price  | Author                              | Volume |");               
        showInfo.add("+-------+-------------------------------------+---------------+-------------------------------------+--------+");
        for (ComicBook comicBook : CM ) {
            id = comicBook.getId();
            title = comicBook.getTitle();
            rentalPrice = comicBook.getRentalPrice();
            author = comicBook.getAuthor();
            volume = comicBook.getVolume();
            
            showInfo.add(String.format(wall + "  %04d " + wall + " %-33s   " + wall + "      %6.2f $ " + wall + " %-33s   " + wall + "   %4d " + wall, id, title, rentalPrice, author, volume));
        }                                                                
        showInfo.add("+-------+-------------------------------------+---------------+-------------------------------------+--------+");
        showInfo.add(String.format("| TOTAL | %-4d entry(s)                                                                                      |", CM.size()));
        showInfo.add("+-------+-------------------------------------+---------------+-------------------------------------+--------+");
    
        for (String info : showInfo) {
            System.out.println(info);
        }
           
       
       
}}
   public void SetPriceRental(){
       int id;
       id = getInteger("Enter ID of Comic: ","Accept positive integer less than 9999 only! Please try again.");
       double reprice;
//       reprice = sc.nextDouble();
       reprice = getDouble("Enter Rental Price of the comic: ", "Accept positive number less than 1000 only! Please try again.");
       for(int i = 0; i<comicList.size()-1;i++){
           ComicBook comic = comicList.get(i);
           if(comic!=null&&comicList.get(i).getId()== id){
               comicList.get(i).setRentalPrice(reprice);
           }
       }
   
   }
  
    public void add() {
        int volume;
        int ID;
        double rentalPrice;
        String author, title;
        boolean isValid = false;
       
        
        do {            
            try {
                title = getString("Enter Title of the comic: ", "Accept a string with at least 1 visible character and at most 33 visible characters! Please try again.");
                rentalPrice = getDouble("Enter Rental Price of the comic: ", "Accept positive number less than 1000 only! Please try again.");
                author = getString("Enter Author's Name: ", "Accept a string with at least 1 visible character and at most 33 visible characters! Please try again.");
                volume = getInteger("Enter Volume of the comic: ", "Accept positive integer less than 1000 only! Please try again.");

                numberOfComics = comicManager.addComic(title, rentalPrice, author, volume);
                ID = searchNewId();
                
                ComicBook b = new ComicBook(ID, title, rentalPrice, author, volume);
//                this.comicList.add(b);
                isValid = true;
            } catch (ComicException ex) {
                   System.out.println(ex.getMessage());
            }
            
        } while (!isValid);
    }
    public void solve(){
         loadData();
        Scanner scanner = new Scanner(System.in);
        String choice;

        do {
            System.out.println("COMIC BOOK RENTAL SHOP");
            System.out.println("1. Add new comic book.");
            System.out.println("2. Search book by title.");
            System.out.println("3. Search book of an author.");
            System.out.println("4. Update book rental price.");
            System.out.println("5. Delete comic book.");
            System.out.println("6. Quit.");
            System.out.print("Please select a function: ");
            choice = scanner.nextLine();
            
            
            switch (choice) {
                case "1":
                    add();
                    showList();
                    comicManager.UpdateTextFile();
                    break;
                case  "2":
                    SearchTitle();
                    
                   
                    break;
                case  "3":
                    
                    SearchAuthor();
                  
                    break;
                case  "4":
                    SetPriceRental();
                    showList();
                    break;
                case  "5":
                   int id = 0;
                   id = getInteger("Enter ID of Comic: ","Accept positive integer less than 9999 only! Please try again.");
                   comicManager.deleteComic(id);
                   comicManager.showComicList();
                   comicManager.UpdateTextFile();
                    break;
                case  "6":
                    
                    System.out.println("Exiting program...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        } while (true);
    }
    
    public static void main(String[] args) {
        Main operator = new Main();
        
        operator.loadData();
        operator.solve();
//        operator.delete();
        operator.showList();
        
    }
    
}
