import java.sql.Date;
import java.util.Vector;

public class Enseignant {
	

	protected String nomEnseignant,prenomEnseignant,fonctionEnseignant;
	protected Date date_choisie ;

	
	protected String []disponibilite = new String [4];
	 
	protected int idEnseignant;
	
	public Enseignant(int idEnseignant){
		this.idEnseignant=idEnseignant;
	
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
			if(l>0 &&j>0)
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
	 * fonctionn de jointure avec un autre enseignant E2
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
	
}
