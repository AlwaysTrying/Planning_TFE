	import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import choco.integer.IntVar;
import choco.kernel.model.variables.integer.IntegerVariable;


import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.solver.Solver;
import choco.kernel.model.Model;
import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.cp.solver.search.integer.valiterator.DecreasingDomain;
import choco.Choco;

import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.UIManager;

import java.awt.SystemColor;	

import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.Vector;

	

import java.awt.Window.Type;
import java.awt.Dimension;

import javax.swing.border.MatteBorder;
import javax.swing.ImageIcon;

import org.kohsuke.args4j.spi.Setter;

import java.awt.Font;

import javax.swing.ListSelectionModel;

import java.awt.Component;

import javax.swing.border.LineBorder;

import java.awt.TextArea;
import javax.swing.border.TitledBorder;
import javax.swing.JTextArea;


public class Emploi {
	
	public static int  NB_CREBEAUX=10;
	public static int NB_SOUTENANCE_PARALLELE=3;
	int NB_enseignant ;
	static int NB_ETUDIANT;
	static String date_soutenance = "2014-10-01";
	
	static int nbh=11; /// tailles de vecteur dispo enseignant
	
	
	/***********initialisation globale de tous les enseignants*********/
	public static Enseignant[] initEnseignantTotal(requete_base_donne rq, String date_soutenance) throws ClassNotFoundException, SQLException{
		
		int nbreEnseignant = rq.getNbreEnseignantTotal(date_soutenance);
		//System.out.println("nbreEnseignant = "+ nbreEnseignant);
		
		Enseignant[] ens= new Enseignant [nbreEnseignant];
		ResultSet  resultSet = rq.getEnseignant(date_soutenance); /// tous les enseignants qui ont choisie la date "date_soutenance"
		int i =0, id_enseignant=0;
		
		while (resultSet.next()) { 
			 String id= resultSet.getString("id_enseignant");
			 id_enseignant =Integer.parseInt (id);
			 
			 ens[i] = new Enseignant(id_enseignant);
			 ResultSet  resultSet1;
			 resultSet1 = rq.getDisponibiliteEnseignant(id_enseignant);
			 int []  vect_dispo_ens = new int [nbh] ;
			 while (resultSet1.next()) { 
				 String[] Disponibilite = new String [4];
				
				 String disp1= resultSet1.getString("disponibilite1"); Disponibilite[0] =disp1;
				 String disp2= resultSet1.getString("disponibilite2"); Disponibilite[1] =disp2;
				 String disp3= resultSet1.getString("disponibilite3"); Disponibilite[2] =disp3;
				 String disp4= resultSet1.getString("disponibilite4"); Disponibilite[3] =disp4;
				 
				 ens[i].setDisponibilite(Disponibilite);
				 /// creation de vecteur de disponibilite de l enseignat 
				 vect_dispo_ens=  ens[i].Creation_vecteur_dispo();
				 ens[i].setvecteurDispo(vect_dispo_ens);
				 i++;
				 
			 };
		};
		
		return ens;
		
	}
	
	public static void changeDispoJury(requete_base_donne rq,Enseignant[] enseignant,Solver s, IntegerVariable[] vars) throws SQLException{
		int n = vars.length;
		int  val, idens, id_enseignant;
		String nom;
		for (int i = 0 ; i <n ; i++){
			val = s.getVar(vars[i]).getVal();
			nom = vars[i].getName();
			if (nom.equals("JOKER") == false){
				
				idens = Integer.parseInt(nom);
				/// parcours de la table eseignant
				
				for (int j = 0 ; j<enseignant.length; j++){
					
					id_enseignant= enseignant[j].getIdEnseignant();
					if (idens == id_enseignant){
						val  =val  % (nbh-1);
						enseignant[j].setvecteurDispo( enseignant[j].dispo_moins_vecteur( enseignant[j].getvecteurDispo(), val )  );
						break;
					}
				}
			}
		}
		
	}
	
	
	/**
	 * changer les dispo des enseignants : les etudiants ont 2 encadreurs (test de idens1 et idens2) : les etud diag
	 * @param s
	 * @param vars
	 * @throws SQLException 
	 */
	public static void changeDispoEnseignant1(requete_base_donne rq,Enseignant[] enseignant,Solver s, IntegerVariable[] vars) throws SQLException{
		/// les vars contient des id d'etudiant
		int n = vars.length;
		
		int id_etud, val, idens1, idens2;
		matrice_vals = new int [2][n];
		for (int k =0 ;k <vars.length;k++){
			matrice_vals[0][k]=Integer.parseInt(vars[k].getName());
			matrice_vals[1][k]=s.getVar(vars[k]).getVal();
		}
		for (int i = 0; i <matrice_vals[0].length; i++) {
			id_etud = matrice_vals[0][i];
			val  = matrice_vals[1][i] / nbh;  /// la valeur dans le tableau 
			idens1 = rq.returni_idEns1(id_etud);
			idens2 = rq.returni_idEns2(id_etud);
			for (int k = 0 ; k<enseignant.length;k++){
				if (enseignant[k].getIdEnseignant() == idens1){
					enseignant[k].setvecteurDispo(enseignant[k].dispo_moins_vecteur( enseignant[k].getvecteurDispo(), val )  );
				}
				else if (enseignant[k].getIdEnseignant() == idens2){
					enseignant[k].setvecteurDispo(enseignant[k].dispo_moins_vecteur( enseignant[k].getvecteurDispo(), val )  );
				}
			}
		}	
	}
	
	/**
	 * changer les dispo des enseignants : les etudiants ont 2 encadreurs (test de idens1 et idens2)
	 * pour les etudiants nonDiag
	 * @param s
	 * @param vars
	 * @throws SQLException 
	 */
	
	public static void changeDispoEnseignant2(requete_base_donne rq,Enseignant[] enseignant,Solver s, IntegerVariable[] vars) throws SQLException{
		/// les vars contient des id d'etudiant
		int n = vars.length;
		ResultSet resultSet;
		int id_etud, val, idens1, idens2;
		matrice_vals = new int [2][n];
		for (int k =0 ;k <vars.length;k++){
			matrice_vals[0][k]=Integer.parseInt(vars[k].getName());
			matrice_vals[1][k]=s.getVar(vars[k]).getVal();
		}
		for (int i = 0; i <matrice_vals[0].length; i++) {
			id_etud = matrice_vals[0][i];
			val  = matrice_vals[1][i] % (nbh-1) ;  /// la valeur dans le tableau 
			idens1 = rq.returni_idEns1(id_etud);
			idens2 = rq.returni_idEns2(id_etud);
			for (int k = 0 ; k<enseignant.length;k++){
				if (enseignant[k].getIdEnseignant() == idens1){
					enseignant[k].setvecteurDispo(enseignant[k].dispo_moins_vecteur( enseignant[k].getvecteurDispo(), val )  );
				}
				else if (enseignant[k].getIdEnseignant() == idens2){
					enseignant[k].setvecteurDispo( enseignant[k].dispo_moins_vecteur( enseignant[k].getvecteurDispo(), val )  );
				}
			}
		}	
	}

	/**
	 * changer les dispo des enseignants : les etudiants ont 1 seul encadreur (test de idens1)
	 * @param rq
	 * @param enseignant
	 * @param s
	 * @param vars
	 * @throws SQLException
	 */
	public static void changeDispoEnseignant3(requete_base_donne rq,Enseignant[] enseignant,Solver s, IntegerVariable[] vars) throws SQLException{
		/// les vars contient des id d'etudiant
		int n = vars.length;
		int id_etud, val, idens1;
		matrice_vals = new int [2][n];
		for (int k =0 ;k <vars.length;k++){
			matrice_vals[0][k]=Integer.parseInt(vars[k].getName());
			matrice_vals[1][k]=s.getVar(vars[k]).getVal();
		}
		for (int i = 0; i <matrice_vals[0].length; i++) {
			id_etud = matrice_vals[0][i];
			val  = matrice_vals[1][i]  % (nbh-1);  /// la valeur dans le tableau 
			idens1 = rq.returni_idEns1(id_etud);
			for (int k = 0 ; k<enseignant.length;k++){
				if (enseignant[k].getIdEnseignant() == idens1){
					enseignant[k].setvecteurDispo( enseignant[k].dispo_moins_vecteur( enseignant[k].getvecteurDispo(), val )  );
				}	
			}
		}	
	}
	
	
	
	/*********traitement des etudiants avec 2 encadrants*********/
	
	/**
	 * fonction qui initialise les etudiants qui ont 2 encadreurs
	 * @param rq
	 * @param date
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	
	public static  int [][] initialisation_Enseignant_Couple(requete_base_donne rq,String date ) throws ClassNotFoundException, SQLException{
				
		 Vector<Integer> vectEnseignant = rq.getEnseignant_NonUnique(date) ; /// contient les ids des enseignants qui encadrent des etudiant avec autre etudiant
		 																		// date prise en compte
		 int nbreEnseignant =vectEnseignant.size();
		 int[][] matriceEnseignant = new int[2][nbreEnseignant];
		 for (int i = 0 ; i<matriceEnseignant[0].length;i++){
			 matriceEnseignant[0][i] = vectEnseignant.get(i);
		 }
		 for (int i = 0 ; i<matriceEnseignant[0].length;i++){
			 matriceEnseignant[1][i] = rq.getNbre(matriceEnseignant[0][i]);
	 
		 }
		 int id_etudiant;
		 Vector<Integer> vectEtudiant =new Vector();
		 Vector<Integer> vectEtudiant2 =new Vector(); /// contient la liste finale des etud à placer
		 ResultSet  resultSet = rq.getEtu_Encad_non_unique(date); // retourne les id des etudiants
		 while (resultSet.next()) {  
			 id_etudiant =Integer.parseInt(resultSet.getString("id_etudiant"));
			 vectEtudiant.add(id_etudiant);
		 };
		 
		 
		 int id1 = 0 , id2 = 0; String d1 ="" , d2="";
		 for (int i = 0 ; i<vectEtudiant.size();i++){
			 id1 = rq.returni_idEns1(vectEtudiant.get(i));
			 id2 = rq.returni_idEns2(vectEtudiant.get(i));
			 d1= rq.return_date(id1);  d2= rq.return_date(id2);
			 if (d1.equals(d2)){
				 vectEtudiant2.add(vectEtudiant.get(i));
			 }
			 }
		 ///////
		 Vector<Integer> vectEtudiantDiag =new Vector();
		 Vector<Integer> vectEtudiantNonDiag =new Vector();
		 boolean diag = false;
		 for (int i = 0 ; i<vectEtudiant2.size();i++){
			 diag = false;
			 id1 = rq.returni_idEns1(vectEtudiant2.get(i));
			 id2 = rq.returni_idEns2(vectEtudiant2.get(i));
			 
			 for (int j = 0 ; j<matriceEnseignant[0].length;j++){
				 if ((id1 == matriceEnseignant[0][j]) &&(matriceEnseignant[1][j] >1) ) {
					 diag = true;
					 break;
				 }
				 else if ((id2 == matriceEnseignant[0][j]) &&(matriceEnseignant[1][j] >1) ) {
					 diag = true;
					 break;
				 }
				 else  diag = false;
			 }
			 if (diag == true) vectEtudiantDiag.add(vectEtudiant2.get(i));
			 else vectEtudiantNonDiag.add(vectEtudiant2.get(i));
		 }
		 int s1 = vectEtudiantDiag.size();
		 int s2 = vectEtudiantNonDiag.size();

		 int m = s1; 
		 if (s2 > s1) m = s2;
		 
		 int [][] matriceEtudiant = new int[2][m]; //contient les id des etudiants : 
		 // la ligne 0 cntient le vecteur : vectEtudiantDiag (les etudiants qui doit etre sur la diag)
		// la ligne 1 cntient le vecteur : vectEtudiantNonDiag
		 for (int i = 0 ; i<s1; i++){
			 
			 if (vectEtudiantDiag.get(i) == null) matriceEtudiant[0][i]= 0;
			 else matriceEtudiant[0][i] = vectEtudiantDiag.get(i);
		 }
		 for (int i = 0 ; i<s2; i++){
			 if (vectEtudiantNonDiag.get(i) == null) matriceEtudiant[1][i]= 0;
			 else matriceEtudiant[1][i] = vectEtudiantNonDiag.get(i);
		 }	
		
		 return matriceEtudiant;
	}
	
	/**
	 * foction qui retourne un tableau etudiant contient les etudiants à diagnaliser
	 * @param tab
	 * @param rq
	 * @param date
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Etudiant[] initEtudiantDiag(Enseignant[] enseignant,int[][] matrice,requete_base_donne rq , String date) throws ClassNotFoundException, SQLException{
		
		 matrice =initialisation_Enseignant_Couple(rq,date_soutenance);
		
		Vector<Integer> vectEtudiantDiag =new Vector(); // contient les id de les etud à diagonaliser
		 // contient les id de les etud à ne pas diagonaliser
		
		 for (int i = 0 ; i<matrice[0].length;i++){
			 if (matrice[0][i]!=0) vectEtudiantDiag.add(matrice[0][i]);
		 }
		
		
		int n = vectEtudiantDiag.size();
		Etudiant[] etudiantDiag = new Etudiant[n];
		int idens1,idens2;
		int[] vect3 = new int [nbh];
		int [] vectDispEns1 = new int [nbh];
		int [] vectDispEns2 = new int [nbh];
		for (int i = 0 ; i<n ; i++){
			etudiantDiag[i]= new Etudiant (vectEtudiantDiag.get(i));
			idens1 = rq.returni_idEns1(etudiantDiag[i].getIdEtudiant());
			idens2 = rq.returni_idEns2(etudiantDiag[i].getIdEtudiant());
			etudiantDiag[i].setIdEnseignant1(idens1);
			etudiantDiag[i].setIdEnseignant2(idens2);
			for (int j = 0 ; j<enseignant.length;j++){
				if (idens1 == enseignant[j].getIdEnseignant()){
					vectDispEns1 = enseignant[j].getvecteurDispo();
				}
				else if (idens2 == enseignant[j].getIdEnseignant()){
				     	vectDispEns2 = enseignant[j].getvecteurDispo();
				}
			}
			
			vect3 = enseignant[0].jointure_vecteur(vectDispEns1 , vectDispEns2 );
			etudiantDiag[i].setDispo_etudiant(vect3);
			
		}
		return etudiantDiag;
	}
	
	/**
	 * foction qui retourne un tableau etudiant contient les etudiants à ne pas diagnaliser
	 * @param enseignant
	 * @param matrice
	 * @param rq
	 * @param date
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	
	public static Etudiant[] initEtudiantNonDiag(Enseignant[] enseignant,int[][] matrice,requete_base_donne rq , String date) throws ClassNotFoundException, SQLException{
		
		 matrice =initialisation_Enseignant_Couple(rq,date_soutenance);
		
		Vector<Integer> vectEtudiantNonDiag =new Vector(); // contient les id de les etud à diagonaliser
		 // contient les id de les etud à ne pas diagonaliser
		
		 for (int i = 0 ; i<matrice[0].length;i++){
			 if (matrice[1][i]==1) vectEtudiantNonDiag.add(matrice[1][i]);
		 }
		
		
		int n = vectEtudiantNonDiag.size();
		Etudiant[] etudiantNonDiag = new Etudiant[n];
		int idens1,idens2;
		int[] vect3 = new int [nbh];
		int [] vectDispEns1 = new int [nbh];
		int [] vectDispEns2 = new int [nbh];
		for (int i = 0 ; i<n ; i++){
			etudiantNonDiag[i]= new Etudiant (vectEtudiantNonDiag.get(i));
			idens1 = rq.returni_idEns1(etudiantNonDiag[i].getIdEtudiant());
			idens2 = rq.returni_idEns2(etudiantNonDiag[i].getIdEtudiant());
			etudiantNonDiag[i].setIdEnseignant1(idens1);
			etudiantNonDiag[i].setIdEnseignant2(idens2);
			for (int j = 0 ; j<enseignant.length;j++){
				if (idens1 == enseignant[j].getIdEnseignant()){
					vectDispEns1 = enseignant[j].getvecteurDispo();
				}
				else if (idens2 == enseignant[j].getIdEnseignant()){
					vectDispEns2 = enseignant[j].getvecteurDispo();
				}
			}
			
			vect3 = enseignant[0].jointure_vecteur(vectDispEns1 , vectDispEns2 );
			etudiantNonDiag[i].setDispo_etudiant(vect3);
			
		}
		return etudiantNonDiag;
	}
	
	
	/**
	 * creation des variables des etudiants qui se mettet sur la diagonale
	 * @param rq
	 * @param etudiantDiag
	 * @param NB_ETUDIANT
	 * @return
	 * @throws SQLException
	 */
	public static IntegerVariable[] createVariablesEtudiantDiag(Model m ,requete_base_donne rq ,Etudiant[] etudiantDiag) throws SQLException{
		
				int n = etudiantDiag.length;
				int [] d = new int[nbh];
				int[] disptotal;
				IntegerVariable[] vars = new IntegerVariable[n];
				Vector<Integer> vect =new Vector();
				int t;
				for(int i =0;i<n;i++){
					//System.out.println("id etud = "+etudiantDiag[i].getIdEtudiant());
					d=etudiantDiag[i].getDispo_etudiant();
					for (int j = 0; j<d.length;j++){
						if (d[j]==1){ 
							vect.add(j*11);	
						}
					}
					t= vect.size();
					disptotal = new int[t];
					for (int k =0; k<t;k++){
						disptotal[k]=vect.get(k);
					}
					vars[i] = Choco.makeIntVar(""+etudiantDiag[i].getIdEtudiant(),disptotal,"");
					
				}
				
				return vars;	
	}

	public static IntegerVariable[] createVariablesEtudiantNonDiag(Model m,requete_base_donne rq ,Etudiant[] etudiantNonDiag) throws SQLException{
		
				int n = etudiantNonDiag.length;
				int [] d = new int[nbh];
				int[] disptotal;
				IntegerVariable[] vars = new IntegerVariable[n];
				
				int t;
				for(int i =0;i<n;i++){
					
					d=etudiantNonDiag[i].getDispo_etudiant();
					disptotal = etudiantNonDiag[i].dispo_total_possible(d);
					vars[i] = Choco.makeIntVar(""+etudiantNonDiag[i].getIdEtudiant(),disptotal,"");
					
				}
				
				return vars;	
	}

	/************************/
	
	
	/*********traitement des etudiants avec un seul encadrant*********/
	
	public static void initialisation_Enseignant(Enseignant[] enseignant,requete_base_donne rq, String date_soutenance ) throws ClassNotFoundException, SQLException{
		/// creation des enseignants
		
		int nbreEnseignant = enseignant.length;
		int id_enseignant=0;
		int []  vect_dispo_ens;
		ResultSet  resultSet2;
		for (int i = 0; i< nbreEnseignant; i++){
			
			id_enseignant = enseignant[i].getIdEnseignant();
			 vect_dispo_ens = enseignant[i].getvecteurDispo();
			 int nbre_etudiant = rq.getNbreEtudiant_Enseignant(id_enseignant);
			 int[] tab = new int [nbre_etudiant];// tan contient les id des etudaints de l enseignant dont id= id_enseignant
			 int j=0; int idEn =0;
			 int T [] = new int [nbh];
			 //// tester si le nbre des etudiants non nul et non = 1
			 
			 if (nbre_etudiant!=0 && nbre_etudiant!=1) {
				 /// liste des etudaints de l'encadreur
			 resultSet2=rq.getEtudiant_Enseignant1_unique(id_enseignant);
			 while (resultSet2.next()) { 
				 String idE= resultSet2.getString("id_etudiant");
				 tab[j] =Integer.parseInt (idE);
				 j++;
			 };
			 
			 /// matrice contient les vects dispo des etudiants
			 int [][]matrice = new int[nbh][nbre_etudiant];
			 matrice =  enseignant[i].matrice_diponibilite_encadrant_etudiant(nbh, vect_dispo_ens, nbre_etudiant);
			 for (int k = 0 ; k<tab.length;k++){
				 T= enseignant[i].dispo_etudiant_from_matrice (matrice, k);
				 /// inserer T dans la table dispoEtu tq id_etudiant = tab[k]
				 String sql = "INSERT INTO `disponibilite_etudiant`(`id_etudiant`, `d1`, `d2`, `d3`, `d4`, `d5`, `d6`, `d7`, `d8`, `d9`, `d10`, `d11`) "+
				 "VALUES (" + tab[k]+ "";
				 for (int h = 0 ; h<T.length;h++){
					 
					 sql = sql+"," + T[h];
				 }
				 sql = sql+")";
				 
				 rq.insertDispoEtudiant(sql);	 
			 }
				 
			 }
			 else if (nbre_etudiant==1){
				 resultSet2=rq.getEtudiant_Enseignant(id_enseignant);
				 while (resultSet2.next()) { 
					 String idE= resultSet2.getString("id_etudiant");
					 idEn =Integer.parseInt (idE);
					 
					// vect_dispo_ens
					 String sql = "INSERT INTO `disponibilite_etudiant`(`id_etudiant`, `d1`, `d2`, `d3`, `d4`, `d5`, `d6`, `d7`, `d8`, `d9`, `d10`, `d11`) "+
					 "VALUES (" + idEn+ "";
					 for (int k = 0 ; k<vect_dispo_ens.length;k++){	 
					/// insérer "vect_dispo_ens" dans la table dispoEtu tq id_etudiant = idEn
					sql = sql+"," + vect_dispo_ens[k];
					}
					sql = sql+")";
					rq.insertDispoEtudiant(sql);
					
				 };
				 
			 }
			
		}
	
	}
	
	public static Etudiant[] initialisation_Etudaint_encadreur_unique(requete_base_donne rq , String date) throws ClassNotFoundException, SQLException{
		
		int nbre_et_en_unique =  rq.getnbreEtu_Encad_unique(date);
		
		Etudiant[] etu= new Etudiant [nbre_et_en_unique];
		
		ResultSet  resultSet = rq.getEtu_Encad_unique(date);
		
		int i =0, id_etudiant = 0,id_enseignant1 = 0;
		 while (resultSet.next()) { 
				
			  String id= resultSet.getString("id_etudiant");
			  id_etudiant =Integer.parseInt (id);
			
			  String id1=   resultSet.getString("id_enseignant1"); 
			  id_enseignant1 =Integer.parseInt (id1);
			
			  etu[i] = new Etudiant(id_etudiant);
			  etu[i].setIdEnseignant1(id_enseignant1);
			  ResultSet  resultSet1 = rq.getDisponibiliteEtudiant(id_etudiant);
			  int di ;
			  String d="";
			  int [] T = new int[nbh]; /// tab contient le vect de dispo de l'etudiant
			  while (resultSet1.next()) {
				  
				  for (int a = 0; a<nbh;a++){
					 d = resultSet1.getString("d"+(a+1)+"");
					 di=Integer.parseInt (d);
					 T[a]=di;
				  }
			  };
			  etu[i].setDispo_etudiant(T);
			  i++;
		};
		return etu;
	}
	
	/*********fin traitement des etudiants avec un seul encadrant*********/
	
	
	/****************Creation des variables Etudiants******************/
	private static IntegerVariable[] createVariables(Model m , int NB_ETUDIANT,Etudiant[] etudiant) 
	{
		//le bloc de disponibilites
		IntegerVariable[] vars = new IntegerVariable[NB_ETUDIANT];
		
		for(int i =0;i<NB_ETUDIANT;i++){
			
			vars[i] = Choco.makeIntVar(""+etudiant[i].getIdEtudiant(),etudiant[i].dispo_total_possible(etudiant[i].getDispo_etudiant()),"");
		
		}
		return vars;

	}
	
	/**
	  *  2. Création des contraintes 
	  * @param m
	  * @param vars
	  */
		private static void postConstraints(Model m, IntegerVariable[] vars, int NB_ETUDIANT) 
		{
	    	postConstraints1(m, vars,NB_ETUDIANT);
	     // postConstraints2(m, vars,NB_ETUDIANT);
	 	}
		
		private static void postConstraintsvars2(Model m, IntegerVariable[] vars,int NB_enseignant ) 
		{
	    	postConstraints12(m, vars,NB_enseignant);
	      // postConstraints22(m, vars,NB_enseignant);
	 	}

	 /**
	  *  2.1. Un etudiant par colonne
	  * @param m
	  * @param vars
	  */
		private static void postConstraints1(Model m, IntegerVariable[] vars , int NB_ETUDIANT )
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

		private static void postConstraints12(Model m, IntegerVariable[] vars,int NB_enseignant  )
		{
			for(int i = 0; i < NB_enseignant; i++) 
			{
				for(int j = i+1; j < NB_CREBEAUX*NB_SOUTENANCE_PARALLELE; j++) 
				{
					if (j<NB_enseignant && i<NB_enseignant)
						m.addConstraint( Choco.neq(vars[i], vars[j]) );
				}
			}
		}
		
	// 2.2. Un etudiant par diagonale
	private static void postConstraints2(Model m, IntegerVariable[] vars , int NB_ETUDIANT) {
	for (int i = 0; i < NB_ETUDIANT; i++) {
	for (int j = i + 1; j < NB_ETUDIANT; j++) {
	int k = j - i;
	m.addConstraint(Choco.neq(vars[i], Choco.plus(vars[j], k)));
	m.addConstraint(Choco.neq(vars[i], Choco.minus(vars[j], k)));

	}
	}
	}
	
	private static void postConstraints22(Model m, IntegerVariable[] vars,int NB_enseignant ) {
		for (int i = 0; i < NB_enseignant-1; i++) {
		for (int j = i + 1; j < NB_enseignant-1; j++) {
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

	static int[][] matrice_vals ;
	// 5. Affichage des résultats
	
	private static int[][] displayResult(Solver s, IntegerVariable[] vars, int NB_ETUDIANT, Etudiant []etudiant) {
		int n = vars.length;
		matrice_vals = new int [2][n];
		//System.out.println("nbr de solution possible: " +s.getNbSolutions());
	if (s.getNbSolutions() > 0) {
	System.out.println("Solution trouvŽe : ");
	
	for (int k =0 ;k <vars.length;k++){
		matrice_vals[0][k]=Integer.parseInt( vars[k].getName());
		matrice_vals[1][k]=s.getVar(vars[k]).getVal();
	}
	
	for (int i = 0; i <NB_ETUDIANT; i++) {
		int val=0;
		
		if(i<NB_ETUDIANT){
			val = s.getVar(vars[i]).getVal();
			System.out.print("ET"+etudiant[i].getIdEtudiant()+" : "+val);
		             }
	
	System.out.println("");
	}
	} else {
	System.out.println("Pas de solution trouvée !!");
	}
	return matrice_vals;
	}
	
	/******** traitment des jurys : creation de vars 2 **/
	
	/**
	 * fonction qui permet de initialiser vars 2 avec les enseignants :(sauf les encadrants)
	 * eliminer la disponibilite deja prise puis intersection de leurs dispo avec les vals des soutenances
	 * @param rq
	 * @param matrice_vals
	 * @param NB_ETUDIANT
	 * @return
	 * @throws SQLException
	 */
	public static void x (requete_base_donne rq ,Enseignant [] enseignant,int [] tableauSoutenance ) throws SQLException{
		IntegerVariable[] varJury = new IntegerVariable[enseignant.length];
		for (int i = 0; i<2 ; i++){
			Model m = new CPModel();
			int k = 0;
			int [] vecteur1,vecteur2,vecteur ;
			// creation des vars :
			for (int j = 0 ; j<enseignant.length;j++){
				vecteur = enseignant[k].getvecteurDispo();
				vecteur2 = enseignant[0].dispo_total_possible(vecteur);
				vecteur1=rq.vecteur_intersection(vecteur2, tableauSoutenance );
				if (vecteur1.length !=0){
					  
				varJury[k] = Choco.makeIntVar(""+enseignant[k].getIdEnseignant(),vecteur1,"");
				 k++;
				 }
				postConstraints ( m, varJury ,enseignant.length);
				Solver s = new CPSolver ();
				s.read(m);
				setHeuristic(s);
				s.solve();
			/// test si solver a solution 
				if (s.getNbSolutions() >0){
					System.out.println("solution trouve");
					
				} 
				else System.out.println("solution non trouve");
				changeDispoEnseignant3(rq, enseignant, s, varJury);
			}
	
		}
	}
	
	public static IntegerVariable[] createVariableJury(requete_base_donne rq , Enseignant [] enseignant,int [] tableauSoutenance ) throws SQLException{
		int nb_enseignant = enseignant.length;
		int id_enseignant;
		int [] vect_dispo_ens;
		int n = tableauSoutenance.length;
		int k =0;
		IntegerVariable[] varJury = new IntegerVariable[n];
		
		for (int i = 0 ; i<nb_enseignant; i++){
			id_enseignant = enseignant[i].getIdEnseignant();
			//System.out.println("id ens = "+id_enseignant );
			 vect_dispo_ens = enseignant[i].getvecteurDispo();
			 int nbre_etudiant = rq.getNbreEtudiant_EnseignantTotal(id_enseignant); // tenant compte que les etudaints unique
		
			 //// tester  le nbre des etudiants 
			 int [] vecteur1,vecteur2 ;
			  vecteur2 = enseignant[0].dispo_total_possible(vect_dispo_ens);
			  vecteur1=rq.vecteur_intersection(vecteur2, tableauSoutenance );
			 if ( nbre_etudiant==1) {
				 
				  if (vecteur1.length !=0){
					  
					   varJury[k] = Choco.makeIntVar(""+id_enseignant,vecteur1,""); 
					   k++;
				  }
				}
			 else if ( nbre_etudiant >1) {
				 if (vecteur1.length !=0){
				 // on donne le mm vecteur "nbre_etudiant" fois
				
				// for (int j = 0 ; j<nbre_etudiant ; j++ ){
					 varJury[k] = Choco.makeIntVar(""+id_enseignant,vecteur1,""); 
					   k++;
					   }
			 }
			 
		 }
		/// tester si k < n (c a d nbre de jury insifisant --> complerer par jyry)
		if (k < n){
			// calculer la difference
			int d = n - k ;
			for (int j = 0 ; j<d ;j++){
				varJury[k] = Choco.makeIntVar("JOKER",tableauSoutenance,""); 
				   k++;
			}
			
		}
		
		
		return varJury;
	}
	

	/************** Main 
	 * @throws SQLException 
	 * @throws ClassNotFoundException *******************/

	//public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		public static Case[] Resolution(requete_base_donne rq) throws ClassNotFoundException, SQLException{
		
		 
		int NB_SOUTENANCE= NB_SOUTENANCE_PARALLELE;
		
		rq.connexion_base();
		rq.verifDispoEnseignant("2014-10-01");
		System.out.println("fin verif");
		/// construction des cases
		Case[] tabCase = new Case [NB_SOUTENANCE_PARALLELE * NB_CREBEAUX];
		for (int i=0 ; i <tabCase.length; i++ ){
			tabCase[i] = new Case(i);
		}
		int [] vecteur;
		Enseignant[] enseignant; // creation des enseignants
		
		enseignant= initEnseignantTotal( rq, date_soutenance);
		
		int[][] matrice =initialisation_Enseignant_Couple(rq,date_soutenance);
		
		/******Resolution des etudiants sur la diag***/
		
		Etudiant[] etuDiag = initEtudiantDiag( enseignant, matrice, rq ,date_soutenance) ;
	

		NB_ETUDIANT = etuDiag.length;
		System.out.println("etudiag"+etuDiag.length);
		if (etuDiag.length ==1 ){
			int val = 0 ;
			for (int k = 0 ; k <etuDiag[0].getDispo_etudiant().length; k++){
				if (etuDiag[0].getDispo_etudiant()[k] == 1) {
					val = k ; 
					break;
				}
			}
			tabCase[val].setEtudiant(etuDiag[0]);
			
			/// changer la disponibilité des encadrants 1 et 2 :
			int id_etud = etuDiag[0].getIdEtudiant();
			int idens1 = etuDiag[0].getIdEnseignant1();
			int idens2 = etuDiag[0].getIdEnseignant2();
			idens1 = rq.returni_idEns1(id_etud);
			idens2 = rq.returni_idEns2(id_etud);
			
			for (int k = 0 ; k<enseignant.length;k++){
				if (enseignant[k].getIdEnseignant() == idens1){
					enseignant[k].setvecteurDispo( enseignant[k].dispo_moins_vecteur( enseignant[k].getvecteurDispo(), val % (nbh-1))  );
				
				}
				else if (enseignant[k].getIdEnseignant() == idens2){
					enseignant[k].setvecteurDispo( enseignant[k].dispo_moins_vecteur( enseignant[k].getvecteurDispo(), val% (nbh-1) )  );
					
				}
			}
			System.out.println("etu diag==1");
		}
		
		else if(etuDiag.length!=0) {
			System.out.println("etu diag!=0 >1");
			////  appeler choco pour resoudre les varsdiag
				NB_SOUTENANCE_PARALLELE=9;
				Model m = new CPModel();
				IntegerVariable[] varsdiag = createVariablesEtudiantDiag( m,rq ,etuDiag);
				postConstraints ( m, varsdiag ,NB_ETUDIANT);
				Solver s = new CPSolver ();
				s.read(m);
				setHeuristic(s);
				s.solve();
				int idEtudiant=0;
				Etudiant et ;int val;
				// 5. Récupérer la solution 
				if (s.getNbSolutions() > 0) {
					
					/// remplir les cases
					for (int i = 0 ;i <varsdiag.length ; i++){
						val = s.getVar(varsdiag[i]).getVal();
						idEtudiant= Integer.parseInt(varsdiag[i].getName());
						for (int j = 0 ; j<etuDiag.length;j++){
							if (etuDiag[j].getIdEtudiant() == idEtudiant ){
								tabCase[val/11].setEtudiant(etuDiag[j]);
							}
						}	
					}
					
					changeDispoEnseignant1( rq, enseignant, s, varsdiag);
					
					}
				else System.out.println("Pas de solution trouvée au niveau de resolution des EtudiantDiag!!");
				}
		
		
		NB_SOUTENANCE_PARALLELE= NB_SOUTENANCE;
		
		/******* la Resolution des etudiants sur la non diag**********/
		
		
		Etudiant[] etuNonDiag = initEtudiantNonDiag( enseignant, matrice, rq ,date_soutenance) ;

		NB_ETUDIANT = etuNonDiag.length;
		System.out.println("etunomdiag"+NB_ETUDIANT);

		if (etuNonDiag.length ==1 ){
			
			int val = 0 ;
			for (int k = 0 ; k <etuNonDiag[0].getDispo_etudiant().length; k++){
				if (etuNonDiag[0].getDispo_etudiant()[k] == 1) {
					val = k ; 
					System.out.println("val = "+ val);
					break;
				}
			}
			tabCase[val].setEtudiant(etuNonDiag[0]);
			
			/// changer la disponibilité des encadrants 1 et 2 :
			int id_etud = etuNonDiag[0].getIdEtudiant();
			int idens1 = etuNonDiag[0].getIdEnseignant1();
			int idens2 = etuNonDiag[0].getIdEnseignant2();
			idens1 = rq.returni_idEns1(id_etud);
			idens2 = rq.returni_idEns2(id_etud);
			
			for (int k = 0 ; k<enseignant.length;k++){
				if (enseignant[k].getIdEnseignant() == idens1){
					enseignant[k].setvecteurDispo( enseignant[k].dispo_moins_vecteur( enseignant[k].getvecteurDispo(), val % (nbh-1))  );
				
				}
				else if (enseignant[k].getIdEnseignant() == idens2){
					enseignant[k].setvecteurDispo( enseignant[k].dispo_moins_vecteur( enseignant[k].getvecteurDispo(), val% (nbh-1) )  );
					
				}
			}
			System.out.println("etunondiag==1");
		}
		else if(etuNonDiag.length!=0) {
			////  appeler choco pour resoudre les varsdiag
			System.out.println("etunondiag>1");

				Model m1 = new CPModel();
				IntegerVariable[] varsNondiag = createVariablesEtudiantNonDiag( m1,rq ,etuNonDiag);
				postConstraints ( m1, varsNondiag ,NB_ETUDIANT);
				Solver s1 = new CPSolver ();
				s1.read(m1);
				setHeuristic(s1);
				s1.solve();
				// 5. Récupérer la solution 
				if (s1.getNbSolutions() > 0) {
					int val , idEtudiant;
					/// remplir les cases
					for (int i = 0 ;i <varsNondiag.length ; i++){
						val = s1.getVar(varsNondiag[i]).getVal();
						idEtudiant= Integer.parseInt(varsNondiag[i].getName());
						for (int j = 0 ; j<etuNonDiag.length;j++){
							if (etuNonDiag[j].getIdEtudiant() == idEtudiant ){
								tabCase[val].setEtudiant(etuNonDiag[j]);
							}
						}	
					}
					changeDispoEnseignant2( rq, enseignant, s1, varsNondiag);
					}
				else System.out.println("Pas de solution trouvée dans les etudiants Non Diag !!");
				}
		
		/***************Etudiant avec Encadreur unique ************/ 
		//int NB_enseignant  = rq.getNbreEnseignantTotal(date_soutenance);
		
		NB_ETUDIANT = rq.getnbreEtu_Encad_unique(date_soutenance);
		Etudiant[] EtudiantUnique= new Etudiant [NB_ETUDIANT];
		

		initialisation_Enseignant(enseignant, rq,  date_soutenance);
		EtudiantUnique = initialisation_Etudaint_encadreur_unique( rq , date_soutenance);
		System.out.println("etuencadran unique"+NB_ETUDIANT);
				Model m2 = new CPModel();
			
				 // 1. Création des variables 
				IntegerVariable[] varsEtudiantUnique = createVariables(m2,NB_ETUDIANT,EtudiantUnique);

				 // 2. Création des contraintes 
				postConstraints ( m2, varsEtudiantUnique ,NB_ETUDIANT);

				 // 3. Choix solver et heuristique 
				Solver s2 = new CPSolver ();
				
				s2.read(m2);
				setHeuristic(s2);

				 // 4. Résolution du problème 
				s2.solve();
				/************/
				
				// 5. Récupérer la solution 
				if (s2.getNbSolutions() > 0) {
					int val , idEtudiant;
					/// remplir les cases
					for (int i = 0 ;i <varsEtudiantUnique.length ; i++){
						val = s2.getVar(varsEtudiantUnique[i]).getVal();
						idEtudiant= Integer.parseInt(varsEtudiantUnique[i].getName());
						for (int j = 0 ; j<EtudiantUnique.length;j++){
							if (EtudiantUnique[j].getIdEtudiant() == idEtudiant ){
								tabCase[val].setEtudiant(EtudiantUnique[j]);
							}
						}	
					}
					changeDispoEnseignant3( rq, enseignant, s2, varsEtudiantUnique);
					}
				else System.out.println("Pas de solution trouvée pour les etudiants à encadranr unique !!");
				
				rq.delete_dispo_Etudiant();

             
				
				/****** Resolution les jurys ************/
		
				Vector<Integer> vecteurSoutenance =new Vector();
				for (int i = 0 ; i<tabCase.length ; i++){
					if (tabCase[i].getEtudiant() != null){
						vecteurSoutenance.add(i);
						}
				}
				
				int n = vecteurSoutenance.size();
				int [] tableauSoutenance = new int[n];
				for (int j = 0; j<tableauSoutenance.length;j++){
					tableauSoutenance[j]=vecteurSoutenance.get(j);
				}
				
	               /****** Creation les jurys ************/

				Model m3 = new CPModel();
				// appel choco pour les jury
				IntegerVariable[] varsJury= createVariableJury(rq , enseignant, tableauSoutenance );
				System.out.println("varsJury.length  = "+ varsJury.length);
				postConstraints ( m3, varsJury ,varsJury.length);

				 // 3. Choix solver et heuristique 
				Solver s3 = new CPSolver ();
				
				s3.read(m3);
				setHeuristic(s3);

				 // 4. Résolution du problème 
				s3.solve();
				
				for (int i = 0 ; i <varsJury.length; i++){
					System.out.println(varsJury[i]);
				}
				
				// 5. Récupérer la solution 
				if (s3.getNbSolutions() > 0) {
					int val ;
					/// afficher resultat et stoker le resultat dans tab case
					for (int i = 0 ;i< varsJury.length ; i++){
						val = s3.getVar(varsJury[i]).getVal();
						String idJury= varsJury[i].getName();
						
						// stoker dans tabCase
						if (idJury.equals("JOKER")==false){
							int idens = Integer.parseInt(idJury);
							tabCase[val].getVecteurEnseignant().add(idens);
							
						}
					}
					// changement de disponibilité 
					changeDispoJury( rq, enseignant, s3, varsJury);
					
					// modifier les Joker par les enseignants encore disponible
					Vector<Integer> vecteurJoker = new Vector();
					for (int i = 0 ;i< varsJury.length ; i++){
						val = s3.getVar(varsJury[i]).getVal();
						String idJury= varsJury[i].getName();
						if (idJury.equals("JOKER")){
						vecteurJoker.add(val);
						}
					}
					int nVectJoker = vecteurJoker.size();
					int [] tabJoker = new int [nVectJoker];
					// creation d'un tabJoker
					for (int k = 0 ; k <nVectJoker ; k++){
						tabJoker[k]= vecteurJoker.get(k);
					}
					int id_enseignant;
					Model m4 = new CPModel ();
					
					IntegerVariable[] varsJury2 = new IntegerVariable[nVectJoker];
					int k =0;
					int [] vect_dispo_ens;
					// creation des nouveaux varsJaury pour remplacer les jokers
					for (int i =0 ; i<enseignant.length;i++){
						int nbEtudiant = rq.getNbre(enseignant[i].getIdEnseignant());
						if (nbEtudiant > 1){
							id_enseignant = enseignant[i].getIdEnseignant();
							vect_dispo_ens = enseignant[i].getvecteurDispo();
							 int [] vecteur1,vecteur2 ;
							  vecteur2 = enseignant[0].dispo_total_possible(vect_dispo_ens);
							  vecteur1=rq.vecteur_intersection(vecteur2, tabJoker);
						
								  if (vecteur1.length !=0){
									  
									  varsJury2[k] = Choco.makeIntVar(""+id_enseignant,vecteur1,""); 
									  System.out.println("varsJury2:  "+k + " "+ varsJury2[k]);
									   k++;
								  }	 
						}
						
					}
					// remplir 
					if (k <nVectJoker ){
						int ABC =nVectJoker-k; 
						for (int h = 0 ; h < ABC ;h++){
							varsJury2[k] = Choco.makeIntVar("JOKER",tabJoker,""); 
							System.out.println("varsJury2  "+k + " "+ varsJury2[k]);
							   k++;
						}
						
					}
					int [] valeur ;
						///  remplacer les jury
							if (varsJury2.length > 1){ // appel au choco
								// 2. Création des contraintes 
								
								
								postConstraintsvars2 (m4,varsJury2 ,nVectJoker);
									
								Solver s4 = new CPSolver ();
								
								s4.read(m4);
								setHeuristic(s4);
								
								// resolution 
								
								s4.solve();
							
								if (s4.getNbSolutions()>0){
									//System.out.println("solution trouve pour model 4");
									/// le id de Joker est par défaut = 999
									for (int h = 0 ; h<varsJury2.length;h++){
										val = s4.getVar(varsJury2[h]).getVal();
										
										String idJury= varsJury2[h].getName();
										
										// stoker dans tabCase
										if (idJury.equals("JOKER")== false){
										int idens = Integer.parseInt(idJury);
										tabCase[val].getVecteurEnseignant().add(idens);
										}else tabCase[val].getVecteurEnseignant().add(999);
										
										
									}
									
								}
								
								}
							else if (varsJury2.length == 1) {
								
								valeur = varsJury2[0].getValues();
								val = valeur[0];
								//tabCase[val].getVecteurEnseignant().add(e)
							}
					}
				else { 
					System.out.println("Pas de solution trouvée pour les jury !!");
				}
				
				System.out.println("tab cases dans Resolution");
				for (int i = 0 ; i<tabCase.length ; i++)
				{

					if (tabCase[i].getEtudiant() != null){
						
					System.out.println("case[" + i+"] = " + "Et "+ tabCase[i].getEtudiant().getIdEtudiant());
					
					
					}
					if (tabCase[i].getEtudiant() == null){
						tabCase[i].setId_case(999);
					}
					}
				
				
			
		return tabCase;		
				
		}	

		
		 Case[] tabcases;

		public static void main(String[] args) throws ClassNotFoundException, SQLException {
			
				/************************************************************************************************************/
				/*					Main de l'affichage des resultats														*/
				/************************************************************************************************************/
				EventQueue.invokeLater(new Runnable()
				{
						public void run() {
							try {
								System.out.println("aa");
								System.out.println("aaaaaa");
								final requete_base_donne rq = new requete_base_donne();
								String date =rq.getDate_Soutenance();
								System.out.println("date de soutenance"+date);

								Emploi window = new Emploi();
								window.frame.setVisible(true);
								System.out.println("la frame ici");
							} catch (Exception e) {
								e.printStackTrace();
								
							}
						}
					});
			
				}
				
		
	
	

	/**
	 * @throws SQLException 
	 * @throws ClassNotFoundException *****************************************/
	
	private void initialize() throws ClassNotFoundException, SQLException {
		
		
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Responsable1_Importer_donnes.class.getResource("/Icons/icon_ECN.JPG")));
		frame.getContentPane().setPreferredSize(new Dimension(200, 100));
		frame.setPreferredSize(new Dimension(68, 0));
		frame.setType(Type.POPUP);
		frame.setBounds(100, 100, 1700, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setExtendedState(frame.MAXIMIZED_BOTH);
		frame.getContentPane().setLayout(null);
		
		final Case[] tabcases;
		final requete_base_donne rq = new requete_base_donne();
		tabcases=Resolution(rq);
		System.out.println("tab cases dans initialize");
		int j = tabcases.length;

		//date_soutenance = rq.getDate_Soutenance();
	
		 Object[] nameColomn=new String[] {"8          9", "9          10",
				  "10          11","11          12","12          13",
				  "13          14","14          15","15          16",
				  "16          17","17          18"};
		
		Object[][] tableau=
			{	{"-", "-", "-", "-","-", "-", "-", "-","-","-"},
				{"-", "-", "-", "-","-", "-", "-", "-","-","-"},
				{"-", "-", "-", "-","-", "-", "-", "-","-","-"},
		};
		

	
		for (int i = 0 ; i<tabcases.length ; i++)
		{
			if (tabcases[i].getEtudiant() != null){
			System.out.println("case[" + i+"] = " + "Et "+ tabcases[i].getEtudiant().getIdEtudiant()+ "ENS"+tabcases[i].getEtudiant().getIdEnseignant1());
			}
			}
		
		int ff=0 , i=0 ;
		String Newligne=System.getProperty("line.separator"); 
		
		int indiceCase = 0;
		for (int h = 0 ; h < NB_SOUTENANCE_PARALLELE; h++ ){
			 for(int g=0;g<NB_CREBEAUX;g++){
				 
				 if(tabcases[indiceCase].getEtudiant() != null){
					 String ch0 = rq.returni_prenom(tabcases[indiceCase].getEtudiant().getIdEtudiant());
					 String ch1 = rq.returni_nom(tabcases[indiceCase].getEtudiant().getIdEtudiant());
					 tableau[h][g]=ch0+" "  + Newligne +ch1;
					 
					 indiceCase++;
				 }
				 else {
					 tableau[h][g]="";
					 indiceCase++;
				 }
				 
			 }
		}
		
		JLabel label = new JLabel("");
		label.setBounds(122, 42, 111, 141);
		label.setIcon(new ImageIcon(Responsable1_Importer_donnes.class.getResource("/Icons/icon_ECN.JPG")));
		frame.getContentPane().add(label);
		
		JLabel lblNewLabel1 = new JLabel("_____________________________________________________");
		lblNewLabel1.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblNewLabel1.setForeground(new Color(0, 153, 204));
		lblNewLabel1.setBackground(Color.RED);
		lblNewLabel1.setBounds(132, 131, 1397, 82);
		frame.getContentPane().add(lblNewLabel1);
		
		JLabel lblNewLabel_6 = new JLabel("Planning des soutenances TFE");
		lblNewLabel_6.setForeground(new Color(0, 153, 204));
		lblNewLabel_6.setFont(new Font("Calibri", Font.BOLD, 40));
		lblNewLabel_6.setBounds(539, 116, 534, 47);
		frame.getContentPane().add(lblNewLabel_6);
		
		JLabel label_4 = new JLabel("");
		label_4.setIcon(new ImageIcon(Responsable1_Importer_donnes.class.getResource("/Icons/calendrier-icone-4830-64.png")));
		label_4.setBounds(1325, 78, 95, 105);
		frame.getContentPane().add(label_4);
		
		JLabel label_3 = new JLabel("");
		label_3.setIcon(new ImageIcon(Responsable1_Importer_donnes.class.getResource("/Icons/horloge-heure-icone-6905-96.png")));
		label_3.setBounds(1275, 54, 118, 96);
		frame.getContentPane().add(label_3);
		
		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.setIcon(new ImageIcon(Responsable1_Importer_donnes.class.getResource("/Icons/balle-fermer-cute-stop-icone-4372-32.png")));
		btnQuitter.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnQuitter.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				
			}
		});
		btnQuitter.setBounds(1154, 713, 135, 54);
		frame.getContentPane().add(btnQuitter);
		
		table_1 = new JTable();
		table_1.setForeground(new Color(112, 128, 144));
		table_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		table_1.setBounds(200, 279, 1200, 50);
		frame.getContentPane().add(table_1);
		table_1.setBackground(Color.LIGHT_GRAY);
		
		table_1.setShowVerticalLines(true);
		table_1.setShowHorizontalLines(true);
		
		
		for(int k=0 ; k<table_1.getRowCount();k++){
			table_1.setRowHeight(k, 30);
			//System.out.println("ici!!!!");
		}
		
	
		
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				{"8           ", "9           ", "10           ", "11           ", "12           ", "13           ", "14           ", "15           ", "16           ", "17              18"},
			},
			new String[] {
				"8          9", "9          10", "10          11", "11          12", "12          13", "13          14", "14          15", "15          16", "16          17", "17          18"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_1.getColumnModel().getColumn(0).setPreferredWidth(120);
		table_1.getColumnModel().getColumn(0).setMinWidth(120);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(120);
		table_1.getColumnModel().getColumn(1).setMinWidth(120);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(120);
		table_1.getColumnModel().getColumn(2).setMinWidth(120);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(120);
		table_1.getColumnModel().getColumn(3).setMinWidth(120);
		table_1.getColumnModel().getColumn(4).setPreferredWidth(80);
		table_1.getColumnModel().getColumn(4).setMinWidth(80);
	    table_1.getColumnModel().getColumn(5).setPreferredWidth(80);
		table_1.getColumnModel().getColumn(5).setMinWidth(80);
		table_1.getColumnModel().getColumn(6).setPreferredWidth(120);
		table_1.getColumnModel().getColumn(6).setMinWidth(120);
		table_1.getColumnModel().getColumn(7).setPreferredWidth(120);
		table_1.getColumnModel().getColumn(7).setMinWidth(120);
		table_1.getColumnModel().getColumn(8).setPreferredWidth(120);
		table_1.getColumnModel().getColumn(8).setMinWidth(120);
		table_1.getColumnModel().getColumn(9).setPreferredWidth(120);
		table_1.getColumnModel().getColumn(9).setMinWidth(120);
	
		
		for(int k=0 ; k<table_1.getRowCount();k++){
			table_1.setRowHeight(k, 50);
			
		}
		
		JPanel panel = new JPanel();
		panel.setBounds(414, 489, 706, 262);
		frame.getContentPane().add(panel);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, SystemColor.activeCaption));
		panel.setLayout(null);
		panel.setVisible(true);
		
		JLabel lblNewLabel = new JLabel("Nom et pr\u00E9nom:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 42, 129, 34);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Encadrant:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(401, 83, 106, 34);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Jury:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_2.setBounds(401, 42, 106, 34);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_4 = new JLabel("Sujet du stage :");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_4.setBounds(10, 159, 129, 34);
		panel.add(lblNewLabel_4);
		
		final JLabel nomprenom = new JLabel("");
		nomprenom.setFont(new Font("Tahoma", Font.PLAIN, 13));
		nomprenom.setBounds(132, 42, 259, 34);
		panel.add(nomprenom);
		
		final JLabel jury = new JLabel("");
		jury.setBounds(472, 42, 308, 34);
		panel.add(jury);
		
		final JLabel encadrant_1 = new JLabel("");
		encadrant_1.setBounds(482, 84, 298, 34);
		panel.add(encadrant_1);
		
		final JLabel lblEncadrant = new JLabel("");
		lblEncadrant.setBounds(482, 122, 298, 38);
		panel.add(lblEncadrant);
		
		JLabel lblEntreprise = new JLabel("Lieu de r\u00E9alisation du stage :");
		lblEntreprise.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEntreprise.setBounds(10, 101, 202, 34);
		panel.add(lblEntreprise);
		
		final JLabel lblEntreprise_1 = new JLabel("");
		lblEntreprise_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEntreprise_1.setBounds(211, 102, 118, 34);
		panel.add(lblEntreprise_1);
		
		final JTextArea txtrSujetDuStage = new JTextArea();
		txtrSujetDuStage.setBackground(UIManager.getColor("Button.background"));
		txtrSujetDuStage.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtrSujetDuStage.setLineWrap(true) ;
		txtrSujetDuStage.setBounds(132, 166, 259, 82);
		panel.add(txtrSujetDuStage);
		
		JLabel label_2 = new JLabel("La date choisie des soutenance :");
		label_2.setForeground(new Color(72, 61, 139));
		label_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_2.setBounds(369, 224, 385, 67);
		
		
		date_soutenance= rq.getDate_Soutenance();
		
		label_2.setText(label_2.getText() + " "+ date_soutenance.toString());
		frame.getContentPane().add(label_2);
		 Object[][] tab =		new Object[10][3];

		table_2 = new JTable();
		table_2.setShowVerticalLines(true);
		table_2.setShowHorizontalLines(true);
		table_2.setForeground(new Color(112, 128, 144));
		table_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		table_2.setBackground(Color.WHITE);
		table_2.setBounds(200, 328, 1200, 150);
		frame.getContentPane().add(table_2);
		table_2.setCellSelectionEnabled(true);
		table_2.setEnabled(true);
		table_2.setColumnSelectionAllowed(true);
		
		table_2.setModel(new DefaultTableModel(tableau ,nameColomn)
		{
			boolean[] columnEditables = new boolean[] {
					false, false, false, false,false, false, false, false,false, false
				};
			});
		
	
		
		table_2.setModel(new DefaultTableModel(tableau ,nameColomn)
		{
			boolean[] columnEditables = new boolean[] {
					false, false, false, false,false, false, false, false,false, false
				};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			};
			});
		
		
		for(int k=0 ; k<table_2.getRowCount();k++){
			table_2.setRowHeight(k, 50);
			System.out.println("ici tab2!!!!");
		}
		
		table_2.getColumnModel().getColumn(0).setPreferredWidth(120);
		table_2.getColumnModel().getColumn(0).setMinWidth(120);
		table_2.getColumnModel().getColumn(1).setPreferredWidth(120);
		table_2.getColumnModel().getColumn(1).setMinWidth(120);
		table_2.getColumnModel().getColumn(2).setPreferredWidth(120);
		table_2.getColumnModel().getColumn(2).setMinWidth(120);
		table_2.getColumnModel().getColumn(3).setPreferredWidth(120);
		table_2.getColumnModel().getColumn(3).setMinWidth(120);
		table_2.getColumnModel().getColumn(4).setPreferredWidth(80);
		table_2.getColumnModel().getColumn(4).setMinWidth(80);
		table_2.getColumnModel().getColumn(5).setPreferredWidth(80);
		table_2.getColumnModel().getColumn(5).setMinWidth(80);
		table_2.getColumnModel().getColumn(6).setPreferredWidth(120);
		table_2.getColumnModel().getColumn(6).setMinWidth(120);
		table_2.getColumnModel().getColumn(7).setPreferredWidth(120);
		table_2.getColumnModel().getColumn(7).setMinWidth(120);
		table_2.getColumnModel().getColumn(8).setPreferredWidth(120);
		table_2.getColumnModel().getColumn(8).setMinWidth(120);
		table_2.getColumnModel().getColumn(9).setPreferredWidth(120);
		table_2.getColumnModel().getColumn(9).setMinWidth(120);

		
		
		encadrant_1.setVisible(true);
		
		
		
		
		table_2.addMouseListener(new MouseAdapter() {
			@Override
			/*************************/
			/*************************/
			public void mouseClicked(MouseEvent e) {
				int m=table_2.getSelectedColumn();
				int g= table_2.getSelectedRow();
				System.out.println("eee rom="+g+"   colomn"+m);
				int x=g*10+m;
				System.out.println(x);
				if(tabcases[x].getEtudiant()!=null)
				{
					String ch0="",ch1="",ch2="",ch3="",ch4="",ch5="", ch6="",ch7=" ",ch8=" ",encad2=" ";
					String nomEntreprise="";
					try {
						ch0 = rq.returni_prenom(tabcases[x].getEtudiant().getIdEtudiant());
 						ch1 = rq.returni_nom(tabcases[x].getEtudiant().getIdEtudiant());
						ch2 = rq.returni_nomencadrant1(tabcases[x].getEtudiant().getIdEtudiant());
						ch3=rq.returni_sujet(tabcases[x].getEtudiant().getIdEtudiant());
						ch4=rq.returni_prenomencadrant1(tabcases[x].getEtudiant().getIdEtudiant());
						nomEntreprise=rq.returnNomEntreprise(tabcases[x].getEtudiant().getIdEtudiant());
								
						
						Vector<Integer> vectjury=tabcases[x].getVecteurEnseignant();
						for(int i=0;i<vectjury.size();i++){
							int id_jury=vectjury.get(i);
							ch5=ch5+" "+id_jury;
							System.out.println("jury= "+ch5);
							ch5=rq.returni_nomjury(id_jury);
							ch6=rq.returni_prenomjury(id_jury);
							jury.setText(ch6+" "+ch5);
							}
						int id_encadrant2=rq.returni_idEns2(tabcases[x].getEtudiant().getIdEtudiant());
 						
 						ch7=rq.returni_nomjury(id_encadrant2);
 						ch8=rq.returni_prenomjury(id_encadrant2);
 						String encad=ch4+" "+ch2;
 						if (!ch7.equals(null) || !ch8.equals(null) )	encad2=ch8+" "+ch7;	
 						
 						encadrant_1.setText(encad);
 						lblEncadrant.setText(encad2);
						nomprenom.setText (ch1+" "+ch0);
						
						txtrSujetDuStage.setText(ch3);
						lblEntreprise_1.setText(nomEntreprise);
					} 
					catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
				}
		});
		
		
		
	}

 JLabel encadrant;
	JFrame frame;
	  JTable table_1;
	  private JTable table_2;
	
	public Emploi() throws ClassNotFoundException, SQLException {
		initialize();
		
	}
	}
	
	
	
