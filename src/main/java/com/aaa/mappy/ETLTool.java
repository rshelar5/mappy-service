package com.aaa.mappy;

import com.aaa.mappy.entity.Product;
import com.aaa.mappy.etl.tool.Util;
import com.aaa.mappy.services.ProductSupportService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;

import static org.springframework.util.ResourceUtils.getFile;

@SpringBootApplication
public class ETLTool implements CommandLineRunner {


    @Autowired
    ProductSupportService productSupportService;

    private static boolean exitTool = false;

    public static void main(String[] args) {

        SpringApplication.run(ETLTool.class, args);


        //show menu crud operation


        //read xlsx

        //print data from xlsx

        //save in DB

    }



    private  void loadData(BufferedReader reader) {
        try {
            System.out.println("Enter base folder for loading xlsx files ");
            String baseFolderPath ;

            baseFolderPath = reader.readLine();

            File baseFolder = getFile(baseFolderPath);
            File[] xlsxFiles = baseFolder.listFiles();
            if (Util.notNull(baseFolder) && baseFolder.isDirectory()) {
                for (File xlsxFile : xlsxFiles) {
                    System.out.println(xlsxFile.getName());

                    switch (xlsxFile.getName()){

                        case "MAIN_ING-IBC.xlsx":
                            loadHazmatData(xlsxFile);
                            break;
                        case "IBC tool v 6 pure substance.xlsx":
                            break;
                        case "Essential oils.xlsx":
                            loadEssentialOidData(xlsxFile);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadEssentialOidData(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            for(Row row: sheet){
                if(row.getRowNum()!= 1){
                    Product product = new Product();
                    for(Cell cell: row){

                        switch (cell.getColumnIndex()){
                            case 1:
                                product.setProperShippingName(cell.getStringCellValue());
                                break;
                            case 2:
                                product.setClassification(cell.getStringCellValue());
                                break;
                        }
                    }
                    this.productSupportService.save(product);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadHazmatData(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            for(Row row: sheet){
                Product product = new Product();
                for(Cell cell: row){

                    switch (cell.getColumnIndex()){
                        case 0:
                            product.setProperShippingName(cell.getStringCellValue());
                            break;
                        case 2:
                            product.setHazmatClassification(cell.getStringCellValue());
                            break;
                    }
                }
                this.productSupportService.save(product);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static int showMenu(BufferedReader reader) {
        System.out.println("1.Load data");
        System.out.println("2.Clear data");
        System.out.println("3.Update data");
        System.out.println("0.Exit tool");
        //TODO : fix for non int input
        try {
            return Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void run(String... args) throws Exception {

        int choice;
        do {

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            choice = showMenu(reader);
            switch (choice) {
                case 0:
                    exitTool = true;
                    System.out.println("Exiting tool.");
                    break;
                case 1:
                    loadData(reader);
                    break;
                default:
                    System.out.println("Please enter valid choice");

            }

        } while (!exitTool);
    }
}
