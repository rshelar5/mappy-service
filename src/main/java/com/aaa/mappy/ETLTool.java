package com.aaa.mappy;

import com.aaa.mappy.entity.Product;
import com.aaa.mappy.entity.PureIngredient;
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
import java.util.HashMap;
import java.util.Iterator;

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
            
            HashMap<String, Product> mapOfProducts = new HashMap<>();

            File baseFolder = getFile(baseFolderPath);
            File[] xlsxFiles = baseFolder.listFiles();
            if (Util.notNull(baseFolder) && baseFolder.isDirectory()) {
                for (File xlsxFile : xlsxFiles) {
                    System.out.println(xlsxFile.getName());

                    switch (xlsxFile.getName()){

                        case "MAIN_ING-IBC.xlsx":
                            loadHazmatData(xlsxFile, mapOfProducts);
                            break;
                        case "IBC tool v 6 pure substance.xlsx":
                            loadPureIngredients(xlsxFile);
                            break;
                        case "Essential oils.xlsx":
                            loadEssentialOidData(xlsxFile, mapOfProducts);
                            break;
                    }
                }
                for(Product product : mapOfProducts.values()) {
                	productSupportService.save(product);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPureIngredients(File xlsxFile) {
        try{
            FileInputStream fileInputStream = new FileInputStream(xlsxFile);
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0).getWorkbook().getSheetAt(0);
            for(Row row : sheet){
            if(row.getRowNum()!= 0){
                PureIngredient pureIngredient = new PureIngredient();
                pureIngredient.setCASNumber(row.getCell(1).toString());
                pureIngredient.setProperShippingName(row.getCell(2).toString());
                pureIngredient.setHazmatCategory(row.getCell(3).toString());
                pureIngredient.setUNID(row.getCell(4).toString());
                pureIngredient.setHazardClass(row.getCell(5).toString());
                pureIngredient.setPackagingGroup(row.getCell(6).toString());
                pureIngredient.setLEHS(row.getCell(8).toString());
                this.productSupportService.save(pureIngredient);
            }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadEssentialOidData(File file, HashMap<String, Product> mapOfProducts) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            for(Row row: sheet){
                if(row.getRowNum()!= 0){
                	String psn = row.getCell(1).toString();
                    String classification = row.getCell(2).toString();
                    if(mapOfProducts.containsKey(psn)) {
                    	Product product = mapOfProducts.get(psn);
                    	product.setClassification(classification);
                    }
                    else {
                    	Product product = new Product();
                    	product.setProperShippingName(psn);
                    	product.setClassification(classification);
                    	mapOfProducts.put(psn, product);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadHazmatData(File file, HashMap<String, Product> mapOfProducts) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            for(Row row: sheet){
                if(row.getRowNum()!=0){
                    String psn = row.getCell(0).toString();
                    String hazmatClassification = row.getCell(2).toString();
                    if(mapOfProducts.containsKey(psn)) {
                    	Product product = mapOfProducts.get(psn);
                    	product.setHazmatClassification(hazmatClassification);
                    }
                    else {
                    	Product product = new Product();
                    	product.setProperShippingName(psn);
                    	product.setHazmatClassification(hazmatClassification);
                    	mapOfProducts.put(psn, product);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static int showMenu(BufferedReader reader) {
        System.out.println("1.Load data");
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
