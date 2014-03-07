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
	public static final int NB_ETUDIANT = 8, NBC=12;


	


public void initialisation(){
	//instance de la classe etudiant
		Etudiant[] etudiant = new Etudiant [3];
		Enseignant[] enseignant = new Enseignant[2];
	//idetudiant
	etudiant[0].setIdEtudiant(10);
	etudiant[1].setIdEtudiant(11);
	etudiant[2].setIdEtudiant(12);
	etudiant[3].setIdEtudiant(13);
	etudiant[4].setIdEtudiant(14);
	etudiant[5].setIdEtudiant(15);
	etudiant[6].setIdEtudiant(16);
	etudiant[7].setIdEtudiant(17);
	etudiant[8].setIdEtudiant(18);
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
		IntegerVariable[] vars = new IntegerVariable[NB_ETUDIANT];
		
		
		//vecteur disponibilite de l"'etudiant 8->9 =0 9->10 =1 10->11=2 11->12=3 puis la deuxieme partie +10 et la troisieme ligne encore +10 une autre fois
		
		//int [] t={1,2,3,4,5,11,12,13,14,15,21,22,23,24,25} , t1={0,3,4,6,10,13,14,16,20,23,24,26} , t2={2,3,5,8,12,13,15,18,22,23,25,28} ;
		//int [] t3={2,3,4,5,6,12,13,14,15,16,22,23,24,25,26} , t4={1,3,5,6,11,13,15,16,21,23,25,26} , t5={7,3,5,8,17,13,15,18,27,23,25,28} ;
		
		//table de disponibilite de chaque etudiant
		//sera presente par t1=e1.jointure(e2) avec e1 et e2 les encadrants de l'eudiant
		int [] t1={0,1,2,4,5,6,8,9,10},
			   t2={1,2,5,6,9,10}, 
			   t3={0,3,4,7,8,11}, 
			   t4={0,2,4,6,8,10},
			   t5={2,3,6,7,9,11}, 
			   t6={1,3,4,7,8,11}, 
			   t7={1,2,5,6,9,10},
			   t8={0,3,4,7,8,11};

		//affectation des variables du problemes :donner la disponibilite de chaque etudiant
		vars[0] = Choco.makeIntVar("Et1", t1,"");
		vars[1] = Choco.makeIntVar("Et2", t2,"");
		vars[2] = Choco.makeIntVar("Et3", t3,"");
		vars[3] = Choco.makeIntVar("Et4", t4,"");
		vars[4] = Choco.makeIntVar("Et5", t5,"");
		vars[5] = Choco.makeIntVar("Et6", t6,"");
		vars[6] = Choco.makeIntVar("Et7", t7,"");
		vars[7] = Choco.makeIntVar("Et8", t8,"");

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

    }

 /**
  *  2.1. Un etudiant par colonne
  * @param m
  * @param vars
  */
	private static void postConstraints1(Model m, IntegerVariable[] vars)
	{
		for(int i = 0; i < 8; i++) 
		{
			for(int j = i+1; j < NBC; j++) 
			{
				if (j<NB_ETUDIANT && i<NB_ETUDIANT)
					m.addConstraint( Choco.neq(vars[i], vars[j]) );
			}
		}
	}

	
// 2.2. Une reine par diagonale
private static void postConstraints2(Model m, IntegerVariable[] vars) {
for (int i = 0; i < NB_ETUDIANT; i++) {
for (int j = i + 1; j < NB_ETUDIANT; j++) {
int k = j - i;
m.addConstraint(Choco.neq(vars[i], Choco.plus(vars[j], k)));
m.addConstraint(Choco.neq(vars[i], Choco.minus(vars[j], k)));
}
}
}

 // 3. Réglage de l'heuristique de choix de valeurs
private static void setHeuristic(Solver s) {
s.setValIntIterator(new DecreasingDomain());
}

 // 5. Affichage des résultats
private static void displayResult(Solver s, IntegerVariable[] vars) {
if (s.getNbSolutions() > 0) {
System.out.println("Solution trouvŽe : ");
for (int i = 0; i <8; i++) {
	int val=0;
	if(i<NB_ETUDIANT){
		val = s.getVar(vars[i]).getVal();
	             }
	
for (int j = 0; j < NBC; j++) {
	if (i<NB_ETUDIANT)
System.out.print(val == j ? s.getVar(vars[i]): " * ");
//	else
//		System.out.print(val == j ? "T": ".");
}

System.out.println("");
}
} else {
System.out.println("Pas de solution trouvŽe !!");
}
}
}