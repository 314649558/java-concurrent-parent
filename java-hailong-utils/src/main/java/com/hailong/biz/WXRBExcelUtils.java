package com.hailong.biz;

import com.hailong.common.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2021/1/1.
 */
public class WXRBExcelUtils {
    public static Map<String,String> readJxlZC(File xlsFile) throws Exception{
        if(xlsFile==null){
            return null;
        }

        Map<String,String> hmap=new HashMap<>();
        Workbook workbook=WorkbookFactory.create(xlsFile);
        int sheetCount = workbook.getNumberOfSheets();
        for (int i = 0; i < sheetCount; i++){
            Sheet sheet = workbook.getSheetAt(i);

            if("维修收银日报".equals(sheet.getSheetName())){
                // 获得行数
                int rows = sheet.getLastRowNum() + 1;

                if(rows<5){
                    return null;
                }
                int totalColIndex=getColIndex(sheet.getRow(2),"合计金额");

                int seqColIndex=getColIndex(sheet.getRow(2),"序号");
                double hejiAmt=0;

                for (int k=3;k<rows;k++){
                    Row row = sheet.getRow(k);

                    if(row==null){
                        continue;
                    }

                    String cellValue = getCellValue(row.getCell(seqColIndex));
                    System.out.println("cellValue  --------------:"+cellValue);
                    if("".equals(cellValue) && hejiAmt==0){
                        hejiAmt=getAmt(getCellValue(row.getCell(totalColIndex)));
                    }

                    if(hejiAmt>0){
                        break;
                    }

                }
                //System.out.println("贷款总金额:"+daikAmt+"   合计金额:"+hejiAmt);
                hmap.put("totalAmt",String.valueOf(hejiAmt));
                hmap.put("filename",xlsFile.getName());
            }

        }
        return hmap;
    }


    public static void writeJxlZC(List<Map<String,String>> dataLst,String writePath) throws Exception{
        //HSSFWorkbook workbook = new HSSFWorkbook();
        XSSFWorkbook workbook = new XSSFWorkbook();
        //HSSFSheet sheet = workbook.createSheet();
        XSSFSheet sheet = workbook.createSheet();

        XSSFRow headRow = sheet.createRow(0);

        headRow.createCell(0).setCellValue("合计");
        headRow.createCell(1).setCellValue("文件名称");
        for (int row = 0; row < dataLst.size(); row++){
            XSSFRow rows = sheet.createRow(row+1);

            Map<String,String> dataRow=dataLst.get(row);

            if(dataRow==null||dataRow.size()==0){
                continue;
            }
            rows.createCell(0).setCellValue(dataRow.get("totalAmt"));
            rows.createCell(1).setCellValue(dataRow.get("filename"));
        }

        File xlsFile = new File(writePath);
        FileOutputStream outputStream=new FileOutputStream(xlsFile);
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
        System.out.println("写入完成");
    }


    public static String getCellValue(Cell cell){


        if(cell==null){
            return "";
        }

        CellType cellType=cell.getCellType();


        String cellValue="";
        try{
            if(cellType==CellType.FORMULA){
                try {
                    cellValue = cell.getStringCellValue();
                } catch (IllegalStateException e) {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                }
            }else if (cellType==CellType.NUMERIC){
                cellValue = String.valueOf(cell.getNumericCellValue());
            }else{
                cellValue=cell.getStringCellValue();
            }
        }catch (Exception e){
            return "";
        }



        return cellValue;
    }


    public static int getColIndex(Row row,String flag){

        if(row==null||flag==null || flag==""){
            return -1;
        }

        int lastCellNum = row.getLastCellNum()+1;

        for (int i=0;i<lastCellNum;i++){

            String cellValue = getCellValue(row.getCell(i));

            //System.out.println("cellValue:"+cellValue);
            if(cellValue==null||cellValue==""){
                continue;
            }
            if(flag.equals(cellValue)){
                return i ;
            }
        }
        return -1;
    }



    public static double getAmt(String amt){
        try {
            return Double.valueOf(amt);
        }catch (Exception e){
            //System.out.println("getAmt:"+amt);
            return 0;
        }
    }




    public static void main(String[] args) throws Exception{

        List<Map<String, String>> list=new ArrayList<>();

        for (int i=1;i<=11;i++) {
            String path="C:\\Users\\Administrator\\Desktop\\2020年收银表\\"+i+"月";
            System.out.println(path);
            File[] files = FileUtils.listFiles(path);
            for(File file:files){
                Map<String, String> stringStringMap = WXRBExcelUtils.readJxlZC(file);
                list.add(stringStringMap);
            }
        }


        System.out.println(list);

        writeJxlZC(list,"F:\\wx.xlsx");

    }



}



