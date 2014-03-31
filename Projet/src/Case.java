
import java.util.Vector;



	public class Case 
	{
	int id_case; /// presente le val de la case4
	
	Etudiant etu;
	Vector<Integer> vecteurEnseignant ; // represente les id des enseignants(encadreur + jury)
	Case(int id_case){
		this.id_case=id_case;
		vecteurEnseignant = new Vector();
	}
	public Etudiant getEtudiant()
	{
		return etu;
	}
	
	public void setEtudiant(Etudiant etu)
		{
			this.etu=etu;
	}
	
	public int getId_case() {
		return id_case;
	}
	public void setId_case(int id_case) {
		this.id_case = id_case;
	}
	
	public Vector<Integer> getVecteurEnseignant() {
		return vecteurEnseignant;
	}
	public void setVecteurEnseignant(Vector<Integer> vecteurEnseignant) {
	this.vecteurEnseignant = vecteurEnseignant;
	}

}




