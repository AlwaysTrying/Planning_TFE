import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class ReadExcel {
	private String inputFile;

	  public void setInputFile(String inputFile) {
	    this.inputFile = inputFile;
	  }

	  String matrice [][] = new String [45][7]; 
	  
	  public void read() throws IOException  {
	    File inputWorkbook = new File(inputFile);
	    Workbook w;
	    try {
	      w = Workbook.getWorkbook(inputWorkbook);
	      // Get the first sheet
	      Sheet sheet = w.getSheet(0);
	      
	      int nbc = sheet.getColumns(); // nbre de colonne
	      int nbl = sheet.getRows(); // nbre de ligne
	     

	      for (int j = 2; j < nbl; j++) {
	    	  
	    	  for (int i = 0; i < nbc; i++) {  
	    		  
	    		  Cell cell = sheet.getCell(i, j);

	    		  matrice [j][i] = cell.getContents();
	    	  }
  	      }

	    } catch (BiffException e) {
	      e.printStackTrace();
	    }
	  }
	  
	public String [][] get_matrice() {
		return matrice;
		
	}


}
