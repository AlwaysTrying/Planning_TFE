import java.sql.Date;
import java.util.Vector;

public class Enseignant {
	

	protected String nomEnseignant,prenomEnseignant,fonctionEnseignant;
	protected Date date_choisie ;

	
	protected String []disponibilite = new String [4];
	protected int [] vecteurDispo = new int[y]; /// vecteur de 11 
	 
	protected int idEnseignant;
	
	public Enseignant(int idEnseignant){
		this.idEnseignant=idEnseignant;
	
	}
	
	public int[] getvecteurDispo(){
		return vecteurDispo;
	}
	public void setvecteurDispo(int [] vecteurDispo){
		this.vecteurDispo=vecteurDispo;
		
	}

	public String getNomEnseignant() {
		return nomEnseignant;
	}

	public void setNomEnseignant(String nomEnseignant) {
		this.nomEnseignant = nomEnseignant;
	}

	public String getPrenomEnseignant() {
		return prenomEnseignant;
	}

	public void setPrenomEnseignant(String prenomEnseignant) {
		this.prenomEnseignant = prenomEnseignant;
	}

	public String[] getDisponibilite() {
		return disponibilite;
	}

	public void setDisponibilite(String[] disponibilité) {
		this.disponibilite = disponibilité;
	}

	public int getIdEnseignant() {
		return idEnseignant;
	}

	public void setIdEnseignant(int idEnseignant) {
		this.idEnseignant = idEnseignant;
	}

	public String getFonctionEnseignant() {
		return fonctionEnseignant;
	}

	public void setFonctionEnseignant(String fonctionEnseignant) {
		this.fonctionEnseignant = fonctionEnseignant;
	}
	
	public Date getDate_choisie() {
		return date_choisie;
	}

	public void setDate_choisie(Date date_choisie) {
		this.date_choisie = date_choisie;
	}

	static int y =11;   //la taille du vecteur de disponibiliteibilité:elle depend de la durée de la soutenance->à modifier selon le besoin
    static int i,k,l,j,m;
    
    /**
	 * fonction permettant de retourner le vecteur de disponibiliteibilité de 
	 * l'enseignant en fonction de se qu'il a introduit
	 * @return
	 */
	public  int []  Creation_vecteur_dispo(){
		
		int k=0;
		int [] vector= new int[y];
		for( i=0; i<y;i++)
		{
			vector[i]=0;
		}
		
		k=0;
		while(k<disponibilite.length)
		{
			int x= Integer.parseInt(disponibilite[k]);
			int y= Integer.parseInt(disponibilite[k+1]);
			l=x-8;
			j=y-8;
			if(l>=0 &&j>=0)
			{
			for(m=l;m<j;m++)
			vector[m]=1;
			k=k+2;
			}
		}
		return vector;
	}
	
	public static int[] initialisation (int[] tab)
	{
		for(i=0;i<y;i++)
			tab[i]=0;
		return tab;
	}
	 
	/**
	 * fonction de jointure avec un autre enseignant E2
	 * @param e2
	 * @return  int[]
	 */
	public  int[] jointure_enseignant(Enseignant e2)
	{
		
		int[] enseignant2= e2.Creation_vecteur_dispo();
		int[] vecteur_en1_en2 = new int[y];
		vecteur_en1_en2=initialisation(vecteur_en1_en2);
		
		for(i=1;i<y;i++)
		{
			if(this.Creation_vecteur_dispo()[i]==1 && enseignant2[i]==1)
				vecteur_en1_en2[i]=1;
			else vecteur_en1_en2[i]=0;
		}
		
		return vecteur_en1_en2;
	}
	
	public  int[] jointure_vecteur(int[] vect1 , int[] vect2 ){
		int [] vect3 = new int [11] ; 
		vect3=initialisation(vect3);
		
		for(i=1;i<y;i++)
		{
			if(vect1[i]==1 && vect2[i]==1)
				vect3[i]=1;
			else vect3[i]=0;
		}
		
		return vect3;
	}
	
	/**
	 * fonction qui retourne une matrice contient la dispo de chaque etudiants (vecteur c la dispo de 
	 * l encadreur qui a nbre etudaint en commun
	 * @param y
	 * @param vecteur
	 * @param nbr_etudiant
	 * @return
	 */
	public static int[][] matrice_diponibilite_encadrant_etudiant( int y, int []  vecteur, int nbr_etudiant){
		
		int [][] matrice_dispo = new int [y][nbr_etudiant];
		int j =0;
		for (int i =0 ; i<vecteur.length;i++){
			
			if (vecteur[i]==1){
				matrice_dispo[i][j]=vecteur[i];
				j++;
			}
			if (j == nbr_etudiant ) j=0;
		}
		return matrice_dispo;
	}

	/**
	 * fonction qui permet de retourner la dispo d'un etudiant à partir de  la matrice commune 
	 * @return
	 */
	public static int[] dispo_etudiant_from_matrice (int[][] matrice , int x){
		int[] vect = new int[matrice.length];
		for (int i = 0 ; i<matrice.length ; i++){
			vect[i] = matrice[i][x];
		}
		return vect;
	}
	/**
	 * fonction permet de retourner un vectuer de int qui contient la disponibilité aprés élémination de la disponibilité à l'indice "x" 
	 * @param vecteur
	 * @param x
	 * @return vecteur de disponibilitée
	 */
	public static int[]dispo_moins_vecteur(int []  vecteur, int x){
		int [] vect = new int [vecteur.length];
		for (int i = 0 ;i<vecteur.length ;i++){
			vect[i] =vecteur[i];
		}
		vect [x]=0;
		return vect;
	}

	public static int[] dispo_total_possible(int []  vecteur){
		/// calculer le nbre des 1 dans le vecteur 
		int n =0;
		for (int i = 0; i<vecteur.length;i++){
			if (vecteur[i]==1) n++;
		}
		
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
	
}
