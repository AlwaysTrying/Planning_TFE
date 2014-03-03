import java.io.File; 
import java.util.*; 
import javax.swing.*;
import javax.swing.filechooser.*;

public class FiltreSimple extends FileFilter{
	//Description et extension accept�e par le filtre
	private String description;
	private String extension;
	//Constructeur � partir de la description et de l'extension accept�e
	public FiltreSimple(String description, String extension){
		if(description == null || extension ==null){
			throw new NullPointerException("La description (ou extension) ne peut �tre null.");	
		}
		this.description = description;
		this.extension = extension;
	}
	//Impl�mentation de FileFilter
	public boolean accept(File file){
		if(file.isDirectory()) { 
			return true; 
		} 
		String nomFichier = file.getName().toLowerCase(); 
		
		return nomFichier.endsWith(extension);
	}
	public String getDescription(){
		return description;
	}
	

}