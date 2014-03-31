

public class Etudiant {
	int y=11; //nombre de creneau possible
	
	protected String nomEtudiant,prenomEtudiant,sujetEtudiant;
	protected int idEtudiant=0,idEntreprise,idEnseignant1,idEnseignant2,idSoutenance;
	protected int[] dispo_etudiant= new int[y];
	
	/**
	 * constrcteur de la classe
	 */

	public int[] getDispo_etudiant() {
		return dispo_etudiant;
	}


	public void setDispo_etudiant(int[] dispo_etudiant) {
		this.dispo_etudiant = dispo_etudiant;
	}


	
	public Etudiant(int idEtudiant){
		this.idEtudiant=idEtudiant;
	
	}
	

	public String getNomEtudiant() {
		return nomEtudiant;
	}


	public void setNomEtudiant(String nomEtudiant) {
		this.nomEtudiant = nomEtudiant;
	}


	public String getPrenomEtudiant() {
		return prenomEtudiant;
	}


	public void setPrenomEtudiant(String prenomEtudiant) {
		this.prenomEtudiant = prenomEtudiant;
	}


	public String getSujetEtudiant() {
		return sujetEtudiant;
	}


	public void setSujetEtudiant(String sujetEtudiant) {
		this.sujetEtudiant = sujetEtudiant;
	}


	public int getIdEtudiant() {
		return idEtudiant;
	}


	public void setIdEtudiant(int idEtudiant) {
		this.idEtudiant = idEtudiant;
	}


	public int getIdEntreprise() {
		return idEntreprise;
	}


	public void setIdEntreprise(int idEntreprise) {
		this.idEntreprise = idEntreprise;
	}


	public int getIdEnseignant1() {
		return idEnseignant1;
	}

	public int getIdEnseignant2() {
		return idEnseignant2;
	}


	public void setIdEnseignant1(int idEnseignant1) {
		this.idEnseignant1 = idEnseignant1;
	}

	public void setIdEnseignant2(int idEnseignant2) {
		this.idEnseignant2 = idEnseignant2;
	}


	public int getIdSoutenance() {
		return idSoutenance;
	}


	public void setIdSoutenance(int idSoutenance) {
		this.idSoutenance = idSoutenance;
	}

	public static int[] dispo_total_possible(int []  vecteur){
		/// calculer le nbre des 1 dans le vecteur 
		int n =0;
		for (int i = 0; i<vecteur.length;i++){
			if (vecteur[i]==1) n++;
		}
		//System.out.println("n="+n);
		
		//// un tableau qui contient tous les dispos possibles (en tenant compte des dispo en paralléle)
		int[] tab = new int [n];
		int[] dispo = new int [n*3];
		int j=0;
		for (int i = 0; i<vecteur.length ; i++){
				if (vecteur[i]==1) {
					tab[j]=i; j++;
				}
		}
		int[] tab1 = new int [n];
		
		for (int i = 0; i<tab.length ; i++){
			tab1[i]= tab[i]+10;
	}
		int[] tab2 = new int [n];
		for (int i = 0; i<tab.length ; i++){
			tab2[i]= tab1[i]+10;
	}
		 /// concatenation des 3 tableaux
	
		for (int i = 0 ;i< tab.length ;i++){
			if (i<n){
				dispo[i]= tab[i];
				
			}
		}
			for (int i = 0 ;i< tab1.length ;i++){
			
					dispo[i+n]= tab1[i];
						
			}		
			for (int i = 0 ;i< tab2.length ;i++){
				
				dispo[i+2*n]= tab2[i];
					
		}
	
		return dispo;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
