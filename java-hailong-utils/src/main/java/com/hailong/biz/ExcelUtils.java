package com.hailong.biz;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

/**
 * Created by Administrator on 2021/1/1.
 */
public class ExcelUtils {
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


    public static int getColIndex(Row row, String flag){

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
}
