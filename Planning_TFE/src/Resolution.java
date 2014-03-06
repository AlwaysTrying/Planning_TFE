import java.sql.Date;


public class Resolution {
	
	
	//instance de la classe etudiant
	Etudiant[] etudiant = new Etudiant [3];
	Enseignant[] enseignant = new Enseignant[2];

	
	
	
	
	public Resolution()
	{
		
	}
	
	public void initialisation(){
		//idetudiant
		etudiant[0].setIdEtudiant(10);
		etudiant[1].setIdEtudiant(11);
		etudiant[2].setIdEtudiant(12);
		//idenseignant
		etudiant[0].setIdEnseignant(1);
		etudiant[1].setIdEnseignant(2);
		etudiant[2].setIdEnseignant(3);
		//idenseignant
		enseignant[0].setIdEnseignant(1);
		enseignant[1].setIdEnseignant(2);
		enseignant[2].setIdEnseignant(3);
		//disponibilite etudiant
		int [] dispo0 ={1,1,0,1,1,0,1,1,1,1,1};
		int [] dispo1= {1,1,0,1,1,0,1,1,1,1,1};
		int [] dispo2= {1,1,0,1,1,0,1,1,1,1,1};
		etudiant[0].setDispo_etudiant(dispo0);
		etudiant[1].setDispo_etudiant(dispo1);
		etudiant[2].setDispo_etudiant(dispo2);
		
		
		
	}

}
