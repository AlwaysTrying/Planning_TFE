import java.io.File;
import java.io.IOException;

import javax.naming.NamingException;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class ReadExcel {

	private String inputFile;

	  public  void setInputFile(String inputFile) {
	    this.inputFile = inputFile;
	  }

	  public  String[][] matrice  ; 
	  
	  
	  public String [][] getMatriceEnseignant() throws IOException{

		  	File inputWorkbook = new File(inputFile);
		    Workbook w;
		    try {
		      w = Workbook.getWorkbook(inputWorkbook);
		      ;
		      // Get the first sheet
		      Sheet sheet = w.getSheet(0);
		      
		      int nbc = sheet.getColumns(); // nbre de colonne
		      
		      int nbl = sheet.getRows(); // nbre de ligne
			  String [][] matrice = new String [nbl][nbc];
		     
		      for (int j =0; j < nbl; j++) { 
		    	  for (int i = 0; i < nbc; i++) {      		 
		    		  Cell cell = sheet.getCell(i, j);
		    		  
		    		  matrice [j][i] = cell.getContents();
		    		 
		    	  }
	  	      }
		      
		    } catch (BiffException e) {
		      e.printStackTrace();
		    }
		  
		 return matrice; 
		  
		  
	  }
	 
	  public void read() throws IOException  {
	    File inputWorkbook = new File(inputFile);
	    Workbook w;
	    try {
	      w = Workbook.getWorkbook(inputWorkbook);
	      ;
	      // Get the first sheet
	      Sheet sheet = w.getSheet(0);
	      
	      int nbc = sheet.getColumns(); // nbre de colonne
	      //System.out.println("nbc = "+ nbc);
	      int nbl = sheet.getRows(); // nbre de ligne
	    //  System.out.println("nbl = "+ nbl);
	      matrice = new String [nbl][nbc]; 
	      
	      for (int j =0; j < nbl; j++) {
	    	  
	    	  for (int i = 0; i < nbc; i++) {      		 
	    		  Cell cell = sheet.getCell(i, j);
	    		  matrice [j][i] = cell.getContents();
	    		  //System.out.println("M["+(j-2)+"]["+i+"] = " + matrice [j-2][i]);
	    	  }
  	      }
	      //System.out.println("M["+43+"]["+0+"] = " + matrice [43][0]);
	      //System.out.println("fichier lu! ");
	      
	    } catch (BiffException e) {
	      e.printStackTrace();
	    }
	  }
	  
	public String [][] get_matrice() {
		return matrice;
		
	}
	
	public int get_nbreligne_fichier(){
		return ( matrice.length);
	}
	public int get_nbrecolonne_fichier(){
		return ( matrice[0].length);
	}
	
	public static void main(String[] args) throws NamingException {
		String inputFile="C:/Users/SAMSUNG/Desktop/FichierExcel.xls";
		//( inputFile);
	}

}
