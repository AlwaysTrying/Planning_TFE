import choco.integer.IntVar;
import choco.kernel.model.Model;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.solver.Solver;
import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.cp.solver.search.integer.valiterator.DecreasingDomain;
import choco.Choco;

public class test_resolution {

	/**
	 * le nombre des etudiants à placer
	 * nombre des crenaus total(nombre de soutenances en parallelles possibles=3 * nombre de crenaus par jours)
	 * 
	 */
	public static final int NB_ETUDIANT = 8, NB_CREBEAUX=11 , NB_SOUTENANCE_PARALLELE=3, NB_enseignant =5;

	//instance de la classe etudiant
		static	Etudiant[] etudiant = new Etudiant [NB_ETUDIANT];
		static 	Enseignant[] enseignant = new Enseignant[NB_enseignant];
	
	

	public static void main(String[] args) {
	
// 0. Création du problème 
Model m = new CPModel();

 // 1. Création des variables 
IntegerVariable[] vars = createVariables(m);

 // 2. Création des contraintes 
postConstraints ( m, vars );

 // 3. Choix solver et heuristique 
Solver s = new CPSolver ();
s.read(m);
setHeuristic(s);

 // 4. Résolution du problème 
s.solve();


 // 5. Récupérer la solution 
displayResult(s, vars);
}


/**
 *  1. Création des variables 
 * @param m
 * @return les variables vars chaque vars presente un etudiant
 */
	private static IntegerVariable[] createVariables(Model m) 
	
	{	/*******************************************/
		//le bloc de creation
		/*****************Creation des enseignants*************/
					/// 5 enseignants 
			
					//creation des nouveau enseignants
					for(int i=0; i<enseignant.length;i++)
					{
					  enseignant[i]= new Enseignant (i);
					 
					}
					/// creation des disponibilité
					String [] S0 = {"8","12","14","17"};
					enseignant[0].setDisponibilite(S0);
					
					int [] dispo0 =enseignant[0].Creation_vecteur_dispo();
					
					String [] S1 = {"8","12","14","18"};
					enseignant[1].setDisponibilite(S1);
					int [] dispo1 =enseignant[1].Creation_vecteur_dispo();
					
					String [] S2 = {"8","12","15","18"};
					enseignant[2].setDisponibilite(S2);
					int [] dispo2 =enseignant[2].Creation_vecteur_dispo();
				
					String [] S3 = {"8","12","14","18"};
					enseignant[3].setDisponibilite(S3);
					int [] dispo3 =enseignant[3].Creation_vecteur_dispo();
					
					String [] S4 = {"8","12","14","18"};
					enseignant[4].setDisponibilite(S4);
					int [] dispo4 =enseignant[4].Creation_vecteur_dispo();
					

			
				//// l'enseignant EN[0] encadre 3 etudiants E0,E1,E2 
				//// l'enseignant EN[1] encadre  2 etudiants E3,E4
			
			/***********************Creation des etudiants*******************/
					
					//creation des nouveaux etudiants
					for(int i=0; i<etudiant.length;i++)
					{
					  etudiant[i]= new Etudiant(i);
					  
					  
					} 
					etudiant[0].setIdEnseignant(0);
					etudiant[1].setIdEnseignant(0);
					etudiant[2].setIdEnseignant(0);
					
					//// creation des dispo des etudiants suivant leur encadrants qui est en commun 
					/// pour EN0 on a 3 etudiants en commun
					
					int [][]matrice =enseignant[0].matrice_diponibilite_encadrant_etudiant(11, dispo0, 3);
					
					int [] tab, tab1, tab2,tab3,tab4,tab5 ;
					/*for (int i =0 ;i<3;i++){
						 tab = enseignant[0].dispo_etudiant_from_matrice ( matrice , i);
						tab1 = enseignant[0].dispo_total_possible(tab);
						etudiant[i].setDispo_etudiant(tab1);
			
					}*/
					
					
					tab = enseignant[0].dispo_etudiant_from_matrice ( matrice , 0);
					tab1 = enseignant[0].dispo_total_possible(tab);
					etudiant[0].setDispo_etudiant(tab1);
					
					tab2 = enseignant[0].dispo_etudiant_from_matrice ( matrice , 1);
					tab3 = enseignant[0].dispo_total_possible(tab2);
					etudiant[1].setDispo_etudiant(tab3);
					
					tab4 = enseignant[0].dispo_etudiant_from_matrice ( matrice , 2);
					tab5 = enseignant[0].dispo_total_possible(tab4);
					etudiant[2].setDispo_etudiant(tab5);
					
					/* for (int i = 0 ; i<dispo0.length;i++){
						 System.out.println("dispo0["+i+"]"+ dispo0[i]); 
					 }
					
					 for (int i = 0 ; i<tab1.length;i++){
						 System.out.println("tab1["+i+"]"+ tab1[i]); 
					 }*/
					
					/* System.out.println("*********"); 
					 
					 tab = enseignant[0].dispo_etudiant_from_matrice ( matrice , 1);
					 tab1 = enseignant[0].dispo_total_possible(tab);
					 for (int i = 0 ; i<tab1.length;i++){
						 System.out.println("tab1["+i+"]"+ tab1[i]); 
					 }
					 System.out.println("*********"); 
					 */
					 
					/* tab = enseignant[0].dispo_etudiant_from_matrice ( matrice , 2);
					 tab1 = enseignant[0].dispo_total_possible(tab);
					 for (int i = 0 ; i<tab1.length;i++){
						 System.out.println("tab1["+i+"]"+ tab1[i]); 
					 }
					for(int i = 0 ; i<etudiant[0].getDispo_etudiant().length;i++){
						
						System.out.println("dispoetudiant[0]["+i+"]"+ etudiant[0].getDispo_etudiant()[i]);
						
					}*/
					
					
					etudiant[3].setIdEnseignant(1);
					etudiant[4].setIdEnseignant(1);
				
				/// pour EN1 on a 2 etudiants en commun E3 et E4
					
					int [][]matrice1 =enseignant[1].matrice_diponibilite_encadrant_etudiant(11, dispo1,2);
					tab = enseignant[1].dispo_etudiant_from_matrice(matrice1,0);
					 tab1 = enseignant[1].dispo_total_possible(tab);
					 etudiant[3].setDispo_etudiant(tab1);
					
					 tab = enseignant[1].dispo_etudiant_from_matrice(matrice1,1);
					 tab1 = enseignant[1].dispo_total_possible(tab);
					 etudiant[4].setDispo_etudiant(tab1);
					
//					int [] t = {0,1,2,3,4,5,6,7,8,9,10,15,22,25,32,17};
//					 etudiant[3].setDispo_etudiant(t);
//					 int [] t1 = {0,1,2,3,4,5,6,7,8,9,10,15,22,25,32,17};
//					 etudiant[4].setDispo_etudiant(t1);
					/*for (int i =0 ;i<2;i++){
						 tab = enseignant[1].dispo_etudiant_from_matrice(matrice1,i);
						 tab1 = enseignant[1].dispo_total_possible(tab);
						etudiant[i+3].setDispo_etudiant(tab1);
					}*/
					 
				/// disponibilité des entudiants E5,E6,E7	
					tab1 = enseignant[0].dispo_total_possible(dispo2);
					etudiant[5].setDispo_etudiant(tab1);
					tab1 = enseignant[0].dispo_total_possible(dispo3);
					etudiant[6].setDispo_etudiant(tab1);
					tab1 = enseignant[0].dispo_total_possible(dispo4);
					etudiant[7].setDispo_etudiant(tab1);
		

		/*******************************************/
		
	//le bloc de disponibilites
		IntegerVariable[] vars = new IntegerVariable[NB_ETUDIANT];
		
		for(int i =0;i<etudiant.length;i++){

			//affectation des variables du problemes :donner la disponibilite de chaque etudiant
			//la premiere variable 1 presente le nom de vars = etudiant[i].getIdEtudiant()
			
			vars[i] = Choco.makeIntVar("et"+etudiant[i].getIdEtudiant(),etudiant[i].getDispo_etudiant(),"");
			
		}
		
		return vars;
		
	
	}
	
	

 /**
  *  2. Création des contraintes 
  * @param m
  * @param vars
  */
	private static void postConstraints(Model m, IntegerVariable[] vars) 
	{
    	postConstraints1(m, vars);
        postConstraints2(m, vars);
       // postConstraints3(m, vars);

    }

 /**
  *  2.1. Un etudiant par colonne
  * @param m
  * @param vars
  */
	private static void postConstraints1(Model m, IntegerVariable[] vars)
	{
		for(int i = 0; i < NB_ETUDIANT; i++) 
		{
			for(int j = i+1; j < NB_CREBEAUX*NB_SOUTENANCE_PARALLELE; j++) 
			{
				if (j<NB_ETUDIANT && i<NB_ETUDIANT)
					m.addConstraint( Choco.neq(vars[i], vars[j]) );
			}
		}
	}

	
// 2.2. Un etudiant par diagonale
private static void postConstraints2(Model m, IntegerVariable[] vars) {
for (int i = 0; i < NB_ETUDIANT; i++) {
for (int j = i + 1; j < NB_ETUDIANT; j++) {
int k = j - i;
m.addConstraint(Choco.neq(vars[i], Choco.plus(vars[j], k)));
m.addConstraint(Choco.neq(vars[i], Choco.minus(vars[j], k)));

}
}
}


/**
 * Preparation pour un enseignant par colonne
 * @param m
 * @param vars
 */
	private static IntegerVariable[][] varstomatrice( IntegerVariable [] vars) 
	{ 
		IntegerVariable [][]  matrice = new IntegerVariable[NB_SOUTENANCE_PARALLELE][NB_CREBEAUX];
		int nbl = matrice.length;
		int nbc = matrice[0].length;
 
		for( int i = 0; i < nbl; i++)
		{ 
			for (int j = 0 ;j<nbc;j++)
			{
				matrice[i][j]= vars[i*nbc+j];

			}
		}
		
		return  matrice;
	}

	//2.3 Un enseignant par colonnecontrainte3
 	private static void postConstraints3(Model m, IntegerVariable[] vars) 
 	{	
 		IntegerVariable [][]  matrice = new IntegerVariable[NB_SOUTENANCE_PARALLELE][NB_CREBEAUX];
 		
 		matrice = varstomatrice( vars) ;
 		
 		for (int i = 0 ; i<matrice.length;i++){
 			for (int j = 0; j < matrice[0].length; j++) {
 				System.out.print(matrice[i][j]);
 			}
 			System.out.println("");
 		}
 		
 		
 		for (int i = 0; i < matrice.length; i++) {

 			for (int j = 0 ; j< matrice[0].length;j++){
 				
 				m.addConstraint(Choco.allDifferent(matrice [i][j]));
 			}
 			
 			
 		}
 
 	}
 
 // 3. Réglage de l'heuristique de choix de valeurs
private static void setHeuristic(Solver s) {
s.setValIntIterator(new DecreasingDomain());
}


// 5. Affichage des résultats
private static void displayResult(Solver s, IntegerVariable[] vars) {
	System.out.println(s.getNbSolutions()+"   nbr///");
if (s.getNbSolutions() > 0) {
System.out.println("Solution trouvŽe : ");
for (int i = 0; i <NB_ETUDIANT; i++) {
	int val=0;
	if(i<NB_ETUDIANT){
		val = s.getVar(vars[i]).getVal();
		System.out.print(val+"*"+etudiant[i].getIdEtudiant());
	             }
	
for (int j = 0; j < NB_SOUTENANCE_PARALLELE*NB_CREBEAUX; j++) {
	if (i<NB_ETUDIANT)
System.out.print(val == j ? s.getVar(vars[i]) + "": " - ");

}

System.out.println("");
}
} else {
System.out.println("Pas de solution trouvŽe !!");
}
}

}