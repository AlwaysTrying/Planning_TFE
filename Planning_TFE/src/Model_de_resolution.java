import choco.integer.IntVar;
import choco.kernel.model.Model;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.solver.Solver;
import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.cp.solver.search.integer.valiterator.DecreasingDomain;
import choco.Choco;

public class Model_de_resolution {

	/**
	 * le nombre des etudiants à placer
	 * nombre des crenaus total(nombre de soutenances en parallelles possibles=3 * nombre de crenaus par jours)
	 * 
	 */
	public static final int NB_ETUDIANT = 8, NB_CREBEAUX=4 , NB_SOUTENANCE_PARALLELE=3;

	//instance de la classe etudiant
		static	Etudiant[] etudiant = new Etudiant [NB_ETUDIANT];
		static 	Enseignant[] enseignant = new Enseignant[NB_SOUTENANCE_PARALLELE];
			
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
	
	{
		
		//le bloc de creation
	/******************************************/
		
		
		
		//creation des nouveaux etudiants
		for(int i=0; i<etudiant.length;i++)
		{
		  etudiant[i]= new Etudiant (i);
		  etudiant[i].setIdEnseignant(i+1);
		}
		//creation des nouveau enseignants
		for(int i=0; i<enseignant.length;i++)
		{
		  enseignant[i]= new Enseignant (i+1);
		}
		
	//idenseignant
	etudiant[0].setIdEnseignant(1);
	etudiant[1].setIdEnseignant(1);
	etudiant[2].setIdEnseignant(1);

		/*******************************************/
		
	//le bloc de disponibilites
		IntegerVariable[] vars = new IntegerVariable[NB_ETUDIANT];
		
		//vecteur disponibilite de l"'etudiant 8->9 =0 9->10 =1 10->11=2 11->12=3 puis la deuxieme partie +10 et la troisieme ligne encore +10 une autre fois
		
		//int [] t={1,2,3,4,5,11,12,13,14,15,21,22,23,24,25} , t1={0,3,4,6,10,13,14,16,20,23,24,26} , t2={2,3,5,8,12,13,15,18,22,23,25,28} ;
		//int [] t3={2,3,4,5,6,12,13,14,15,16,22,23,24,25,26} , t4={1,3,5,6,11,13,15,16,21,23,25,26} , t5={7,3,5,8,17,13,15,18,27,23,25,28} ;
		
		//table de disponibilite de chaque etudiant
		//sera presente par t1=e1.jointure(e2) avec e1 et e2 les encadrants de l'eudiant
		int [][] table_dispo={{0,1,2,4,5,6,8,9,10},
			   {1,2,5,6,9,10}, 
			   {0,3,4,7,8,11}, 
			   {0,2,4,6,8,10},
			   {2,3,6,7,9,11}, 
			   {1,3,4,7,8,11}, 
			   {1,2,5,6,9,10},
			   {0,3,4,7,8,11},
//			   {0,1,2,3,4,5,6,7,8,9,10},
//			   {0,1,2,3,4,5,6,7,8,9,10},
//			   {0,1,2,3,4,5,6,7,8,9,10},
//			   {0,1,2,3,4,5,6,7,8,9,10},
			   };
		
		for(int i =0;i<etudiant.length;i++){

			etudiant[i].setDispo_etudiant(table_dispo[i]);    
			//affectation des variables du problemes :donner la disponibilite de chaque etudiant
			//la premier variable 1 presente le nom de vars = etudiant[i].getIdEtudiant()
			vars[i] = Choco.makeIntVar(""+etudiant[i].getIdEtudiant(),etudiant[i].getDispo_etudiant(),"");
			int idenseignant= etudiant[Integer.parseInt(vars[i].getName())].getIdEnseignant();
			System.out.println("ideneignant"+idenseignant);
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
//        postConstraints3(m, vars);

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
	private static Etudiant[][] varstomatrice( Etudiant[]   vars) 
	{ 
		Etudiant matrice [][]= new Etudiant [NB_SOUTENANCE_PARALLELE][NB_CREBEAUX];
		int nbl = matrice.length;
		int nbc = matrice[0].length;
 
		for( int i = 0; i < nbl; i++)
		{ 
			for (int j = 0 ;j<nbc;j++)
			{
				matrice [i][j]= vars[i*nbc+j];

			}
		}
		System.out.println("dans la fonction varstomatrice");
		return  matrice;
	}
	
	
	//2.3 un enseignant par colonne contrainte3


	//2.3 Un enseignant par colonne
 	private static void postConstraints3(Model m, IntegerVariable[] vars) 
 	{	//vars=etudiaitn
 		IntegerVariable[] vars_enseignant= new IntegerVariable[NB_ETUDIANT];
 		//creation du vecteur enseignant
 		for (int i = 0; i < vars_enseignant.length; i++)
 		{
 			vars_enseignant[i]= Choco.makeIntVar(""+etudiant[Integer.parseInt(vars[i].getName())].getIdEnseignant(),
 										vars[i].getValues(),"");
 			System.out.println(vars_enseignant[i]+"-");
 			 
 		}
 		System.out.println("ici");
 		//vars1 contient les id enseignants + disponibilit de l'enseignant
 		IntegerVariable[] vars1= new IntegerVariable[NB_CREBEAUX*NB_SOUTENANCE_PARALLELE];
 		IntegerVariable[][]matrice_vars1 =new IntegerVariable[NB_SOUTENANCE_PARALLELE][NB_CREBEAUX]; 
 		//affectation de tous dans un vecteur
 		for (int i = 0; i < vars1.length; i++)
 		{
 			if(i<vars_enseignant.length){
 			vars1[i]=vars_enseignant[i];
 			System.out.println(vars1[i]+"*");}
 			else
 				vars1[i]=null;
 		}
 		
 		/****************************/
 		//creation de la matrice
 		for( int i = 0; i < NB_SOUTENANCE_PARALLELE; i++)
		{ 
			for (int j = 0 ;j<NB_CREBEAUX;j++)
			{    if (vars1[i*NB_CREBEAUX+j]!=null)
				{matrice_vars1 [i][j]= vars1[i*NB_CREBEAUX+j];
				System.out.println("m["+i+"]["+j+"]="+matrice_vars1[i][j]);}
			else 
			{matrice_vars1[i][j]=null;
			System.out.println("m["+i+"]["+j+"]="+matrice_vars1[i][j]);}
			}
		}
 		//pour teste un vector qui contient les elements de la premier colonne de la matrice
 		IntegerVariable [] vector=new 	IntegerVariable[3];
 		vector[0]=matrice_vars1[0][0];vector[1]=matrice_vars1[1][0];vector[2]=matrice_vars1[2][0];
 		IntegerVariable[] vars11= new IntegerVariable[3];
 		vars11[0]=matrice_vars1[0][0];vars11[1]=matrice_vars1[1][0];vars11[2]=matrice_vars1[2][0];
 	 	m.addConstraint(Choco.allDifferent(vars11));
 	}
 		 
 		 
 		 
 // 3. Réglage de l'heuristique de choix de valeurs
private static void setHeuristic(Solver s) {
s.setValIntIterator(new DecreasingDomain());
}


 // 5. Affichage des résultats
private static void displayResult(Solver s, IntegerVariable[] vars) {
	
	Etudiant[] tab2= new Etudiant[NB_CREBEAUX*NB_SOUTENANCE_PARALLELE];

	
	
	/**
 * fonction pour l'initialisation du tableau
 * @param tab
 * @return
 */
	for(int i=0;i<tab2.length;i++){
		tab2[i]=null;
		//tab2[i].setIdEtudiant(0);
	}
	
	
	
if (s.getNbSolutions() > 0) {
System.out.println("Solution trouvée : ");

for (int i = 0; i <NB_ETUDIANT; i++) {
	int val=0;
	if(i<NB_ETUDIANT){
		val = s.getVar(vars[i]).getVal();
		System.out.println("la valeur"+val+" l'etudiant "+s.getVar(vars[i]).getName());
		int id=Integer.parseInt(s.getVar(vars[i]).getName());
		tab2[val]=new Etudiant(id);
		System.out.println(tab2[val].getIdEtudiant());
	             }
for (int j = 0; j < NB_CREBEAUX*NB_SOUTENANCE_PARALLELE; j++) {
	if (i<NB_ETUDIANT)
System.out.print(val == j ? s.getVar(vars[i]): " * ");
//	else
//		System.out.print(val == j ? "T": ".");
}

System.out.println("");
}
System.out.println("*************************");
Etudiant[][] table_des_soutenances=varstomatrice(tab2);
for(int u=0;u<table_des_soutenances.length;u++)
{
	for(int h=0;h<table_des_soutenances[u].length;h++)
	{
		if (table_des_soutenances[u][h]!=null)
		System.out.print(table_des_soutenances[u][h].getIdEtudiant()+" ");
		else System.out.print(" - ");
	}
	System.out.println();
}

}
else {
System.out.println("Pas de solution trouvŽe !!");
}
}
}