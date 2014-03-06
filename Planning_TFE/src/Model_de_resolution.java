import choco.kernel.model.Model;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.solver.Solver;
import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.cp.solver.search.integer.valiterator.DecreasingDomain;
import choco.Choco;

public class Model_de_resolution {

public static final int NB_REINES = 6, NBL=8,NBC=8;


//instance de la classe etudiant
Etudiant[] etudiant = new Etudiant [3];
Enseignant[] enseignant = new Enseignant[2];


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

// 1. Création des variables 
private static IntegerVariable[] createVariables(Model m) {
IntegerVariable[] vars = new IntegerVariable[NB_REINES];
//for (int i = 0; i < NB_REINES; i++) {
//vars[i] = Choco.makeIntVar("x" + i, 0,NBC - 1);
//System.out.println(vars[i]);
int [] t={1,2,3,4,5};
int [] t1={0,2,3};
int [] t2={0,2,3};
int [] t3={1,3,5};
int [] t4={0,1,2,3,4};
int [] t5={2,3};
vars[0] = Choco.makeIntVar("Et0", t,"");
vars[1] = Choco.makeIntVar("Et1", t1,"");
vars[2] = Choco.makeIntVar("Et2", t2,"");
vars[3] = Choco.makeIntVar("Et3", t3,"");
vars[4] = Choco.makeIntVar("Et4", t4,"");
vars[5] = Choco.makeIntVar("Et5", t5,"");
//vars[0] = Choco.makeIntVar("x" + 0, 1,3);
return vars;
}

 // 2. Création des contraintes 
private static void postConstraints(Model m, IntegerVariable[] vars) {
postConstraints1(m, vars);
postConstraints2(m, vars);
}

 // 2.1. Une reine par colonne
private static void postConstraints1(Model m, IntegerVariable[] vars) {
for(int i = 0; i < NBL; i++) {
for(int j = i+1; j < NBC; j++) {
	if (j<NB_REINES && i<NB_REINES)
m.addConstraint( Choco.neq(vars[i], vars[j]) );

}
}
}

// 2.2. Une reine par diagonale
private static void postConstraints2(Model m, IntegerVariable[] vars) {
for (int i = 0; i < NB_REINES; i++) {
for (int j = i + 1; j < NB_REINES; j++) {
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
for (int i = 0; i <NBL; i++) {
	int val=0;
	if(i<NB_REINES){
		val = s.getVar(vars[i]).getVal();
//		System.out.print("val"+val);
//		System.out.println("   s.getVar(vars[i])"+s.getVar(vars[i]));
	             }
	
for (int j = 0; j < NBC; j++) {
	if (i<NB_REINES)
System.out.print(val == j ? s.getVar(vars[i])+"__": "");
//	else
//		System.out.print(val == j ? "T": ".");
}

//System.out.println("");
}
} else {
System.out.println("Pas de solution trouvŽe !!");
}
}
}