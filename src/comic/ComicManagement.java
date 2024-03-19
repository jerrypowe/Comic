/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 *
 * @author Admin
 */
public class ComicManagement {
    private String comicList_filePath;
    private int numberofComics;
    private ArrayList<ComicBook> comicList;

    public String result="";
    public ComicManagement(String comicList_FILE) throws ComicException {
        if (comicList_FILE.equals("")) {
            throw new ComicException("File path of comicList.txt cannot be empty!");
        }else{
            this.comicList_filePath = comicList_FILE;
            this.comicList = new ArrayList<>();
            this.numberofComics = 0;
        }
    }
    
    public ArrayList<ComicBook> loadComicList() throws IOException, ComicException {
        File comicList_file = new File(comicList_filePath);
        ArrayList<ComicBook> tempList = new ArrayList<>();
        
        if (!comicList_file.exists()) {
            comicList_file.createNewFile();
            System.out.println("Data file does not exist. "+
                               "Creating data file comicList.txt... "+
                               "Done!");
            this.numberofComics = 0;
        }else{
            System.out.println("Data file comicList.txt has been found. "+
                               "Loading data from the file...");
            try(FileReader reader = new FileReader(comicList_file);
                BufferedReader buffer = new BufferedReader(reader)){
                String id, title, rentalPrice, author, volume; 
                
                this.numberofComics = Integer.parseInt(buffer.readLine());
                
                for (int i = 0; i < this.numberofComics; i++) {
                    id = buffer.readLine();
                    title = buffer.readLine();
                    rentalPrice = buffer.readLine();
                    author = buffer.readLine();
                    volume = buffer.readLine();
                    
                    tempList.add(new ComicBook(Integer.parseInt(id),
                                                    title,
                                                    Double.parseDouble(rentalPrice),
                                                    author,
                                                    Integer.parseInt(volume)));
                }
                
                boolean isDuplicated = false;
                int currentId, nextId, currentVolume, nextVolume;
                String currentTitle, nextTitle;
                
                for (int i=0; i<tempList.size()-1; i++) {
                    for (int j = i+1; j < tempList.size(); j++) {
                    currentId = tempList.get(i).getId();
                    nextId = tempList.get(j).getId();
                    currentVolume = tempList.get(i).getVolume();
                    nextVolume = tempList.get(j).getVolume();
                    currentTitle = tempList.get(i).getTitle();
                    nextTitle = tempList.get(j).getTitle();
                    if (currentId == nextId || (currentVolume == nextVolume && currentTitle.equals(nextTitle))){
                        isDuplicated = true;
                        throw new ComicException("The file contains duplicated data (ID or same Title with same Volume).");
                    }
                        
                    }
                }
                
                if (!isDuplicated) {
                    for (ComicBook tempComic : tempList) {
                        this.comicList.add(tempComic);
                    }
                }
                
            }
                
        }
        System.out.println("Done! [" + this.numberofComics + " comic books]");
        return this.comicList;
    }
    
    
    public int addComic(String title, double rentalPrice, String author, int volume) throws ComicException{
        boolean isExisted = false;
        for (ComicBook comic : this.comicList) {
            if (comic.getTitle().equals(title) && comic.getVolume() == volume) {
                isExisted = true;
                throw new ComicException("New comic entry has already existed in the database.");
            }
        }
        if (!isExisted) {
            this.comicList.add(new ComicBook(++this.numberofComics, title, rentalPrice, author, volume));
        }
        return this.numberofComics;
    }
            
    
    public void showComicList(){
        ArrayList<String> showInfo = new ArrayList<>();
        String wall = "|";
        int id, volume;
        String title, author;
        double rentalPrice;
        showInfo.add("+-------+-------------------------------------+---------------+-------------------------------------+--------+");
        showInfo.add("| ID    | Title                               | Rental Price  | Author                              | Volume |");               
        showInfo.add("+-------+-------------------------------------+---------------+-------------------------------------+--------+");
        for (ComicBook comicBook : this.comicList ) {
            id = comicBook.getId();
            title = comicBook.getTitle();
            rentalPrice = comicBook.getRentalPrice();
            author = comicBook.getAuthor();
            volume = comicBook.getVolume();
            
            showInfo.add(String.format(wall + "  %04d " + wall + " %-33s   " + wall + "      %6.2f $ " + wall + " %-33s   " + wall + "   %4d " + wall, id, title, rentalPrice, author, volume));
        }                                                                
        showInfo.add("+-------+-------------------------------------+---------------+-------------------------------------+--------+");
        showInfo.add(String.format("| TOTAL | %-4d entry(s)                                                                                      |", this.comicList.size()));
        showInfo.add("+-------+-------------------------------------+---------------+-------------------------------------+--------+");
    
        for (String info : showInfo) {
            System.out.println(info);
        }
    }
    
//    public int deleteComic(int id) {
//        boolean checkID = false;
//        for (ComicBook comic : this.comicList) {
//            if (comic.getId() == id) {
//                this.comicList.remove(comic);
//                checkID = true;
//                this.numberofComics--;
//            }
//        }
//        if (!checkID) {
//            System.out.println("ComicException: " + "Comic with ID " + id + "cannnot be found in the comic list.");
//        }
//        showComicList();
//    
//    return this.numberofComics;
//    }
    public int deleteComic(int id) {
    Iterator<ComicBook> iterator = this.comicList.iterator();
    while (iterator.hasNext()) {
        ComicBook comic = iterator.next();
        if (comic.getId() == id) {
            iterator.remove();
            this.numberofComics--;
            return this.numberofComics;
        }
    }
    System.out.println("ComicException: Comic with ID " + id + " cannot be found in the comic list.");
    showComicList();
    return this.numberofComics;
}

    
public void UpdateTextFile() {
  
        // Mảng dữ liệu để nhập vào tệp văn bản
        String[] data = {"Line 1", "Line 2", "Line 3"};
        int id, volume;
        String title, author;
        // Đường dẫn tới tệp văn bản
        String filePath = "comicList_filePath";

        try {
            // Xóa toàn bộ dữ liệu trong tệp văn bản
            BufferedWriter clearWriter = new BufferedWriter(new FileWriter(this.comicList_filePath, false));
            clearWriter.write(""); // Ghi lại dữ liệu trống
            clearWriter.close();

            // Nhập các dữ liệu từ mảng vào từng dòng trong tệp văn bản
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.comicList_filePath, true));
            writer.write(numberofComics);
            for (ComicBook line : this.comicList) {
                result += String.format("%d\n",line.getId()) ;
                result += String.format("%s\n",line.getTitle());
                result += String.format("%s\n",line.getRentalPrice());
                result += String.format("%s\n",line.getAuthor());
                result += String.format("%s\n", line.getVolume());
                writer.write(result);
                writer.newLine(); // Thêm ký tự xuống dòng
            }
            writer.close();

            System.out.println("Data has been updated in the text file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    
}
