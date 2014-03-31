import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;

import javax.naming.NamingException;
public class requete_base_donne {

	 static Connection connect = null;
	  static Statement st ;
	  static ResultSet  resultSet = null ;
	  static ResultSet  resultSet1 = null ;

	  /**
	   * fonction permet la connection avec la base de donnée
	   * @throws ClassNotFoundException
	   * @throws SQLException
	   */
	  public static void connexion_base() throws ClassNotFoundException, SQLException{
	  
		
			// This will load the MySQL driver, each DB has its own driver
		      Class.forName("com.mysql.jdbc.Driver");
		      // Setup the connection with the DB
		      connect = DriverManager.getConnection("jdbc:mysql://localhost/base?" + "user=root");
		  	 System.out.println("conexion etablie avec la base");

	  }
	  
	  public boolean getDateSoutenance() throws SQLException{
		  
		  st =connect.createStatement();
		  boolean existe = false;
		  String sql = "SELECT count(*) FROM `date_soutenance` ";
		  resultSet = st.executeQuery(sql);
		  int nbreT = 0;
		  while (resultSet.next()) { 
				
			  String nbre= resultSet.getString("COUNT(*)");
			   nbreT =Integer.parseInt (nbre);
		}; 
		if (nbreT != 0) existe = true;
		return existe;
		  
	  }
	 /**
	  * fonction retourne la date de soutenance
	  * @return
	  * @throws SQLException
	  */
	  public String getDate_Soutenance()throws SQLException{
		  
		  st =connect.createStatement();
		  String sql = "SELECT date_soutenance FROM `date_soutenance` where `id_date_soutenance` = 1";
		  resultSet = st.executeQuery(sql);
		  String date_soutenance="";
		  while (resultSet.next()) { 
				
			   date_soutenance= resultSet.getString("date_soutenance");
			  
		}; 
		  
		  return date_soutenance;
	  }
	  
	  public void insertDateSoutenance(String DateSoutenance) throws SQLException{
		  String sql = "INSERT INTO `date_soutenance`(`id_date_soutenance`, `date_soutenance`) VALUES ("+ 1+ ",'" +DateSoutenance +"')";
		  st.executeUpdate(sql);
	  }
	  
	  public void deleteDateSoutenance() throws SQLException{
		  String sql = "DELETE FROM `date_soutenance` WHERE `id_date_soutenance` = 1";
		  st.executeUpdate(sql);
	  }
	  
	  public Statement getst( ) throws SQLException{
		  st =connect.createStatement();
		  return st;
	  }
	/**
	 * fonction qui retourne le nbre d'etudiants total dans la base
	 * @return
	 * @throws SQLException
	 */
	  public static int getnbreEtudiantTotal () throws SQLException{
		  st =connect.createStatement();
		   String sql = " SELECT COUNT(*) FROM etudiant ";
		  resultSet = st.executeQuery(sql);
		  int nbreT = 0;
		  while (resultSet.next()) { 
				
			  String nbre= resultSet.getString("COUNT(*)");
			   nbreT =Integer.parseInt (nbre);
		}; 
				return nbreT;
	  }
	
	  /**
	   * fonction qui retourne le nbre total d etudiants qui ont un seul encadrant
	   * @return
	   * @throws SQLException
	   */
	  public static int getnbreEtu_Encad_unique(String date) throws SQLException{
		  st =connect.createStatement();
		   String sql = " SELECT COUNT(*) FROM `etudiant`, `enseignant` WHERE ((enseignant.id_enseignant = etudiant.id_enseignant1) AND (etudiant.id_enseignant2 is null)"+
				   " AND (enseignant.date_choisie ='"+date + "'))";
		    
		  resultSet = st.executeQuery(sql);
		  int nbreT = 0;
		  while (resultSet.next()) { 
				
			  String nbre= resultSet.getString("COUNT(*)");
			   nbreT =Integer.parseInt (nbre);
		}; 
				return nbreT;
	  }
	  /**
	   * fonction qui retourne la liste des etudaints qui ont un seul encadrant
	   * @return  ResultSet
	   * @throws SQLException
	   */
	  public ResultSet getEtu_Encad_unique(String date) throws SQLException{
		  st =connect.createStatement();
		   String sql = " SELECT * FROM `etudiant`, `enseignant` WHERE ((enseignant.id_enseignant = etudiant.id_enseignant1) AND (etudiant.id_enseignant2 is null)"+
				   " AND (enseignant.date_choisie ='"+date + "'))";
		  resultSet = st.executeQuery(sql);
		  return resultSet;
	  }
	  
	  /**
	   * fonction qui retourne le nbre total d'enseignant qui ont la mm date choisie que le repponsable 
	   * @return
	   * @throws SQLException
	   */
	  public static int getNbreEnseignantTotal(String date) throws SQLException{
		  st =connect.createStatement();
		   String sql = " SELECT COUNT(*) FROM `enseignant` WHERE `date_choisie` = '" + date+"'";
		  resultSet = st.executeQuery(sql);
		  int nbreT = 0;
		  while (resultSet.next()) { 
				
			  String nbre= resultSet.getString("COUNT(*)");
			   nbreT =Integer.parseInt (nbre);
		}; 
				return nbreT;
		  
	  }
	
	  
	  /**
	   * fonction qui retourne tous les enseignants
	   * @return
	   * @throws SQLException
	   */
	  public static ResultSet getEnseignant(String date) throws SQLException{
		  st =connect.createStatement();
		   String sql = " SELECT  * FROM `enseignant` WHERE `date_choisie` = '" + date+"'";
		  resultSet = st.executeQuery(sql);
		  return resultSet;
	  }
	  /**
	   * selection de tous les enseignants
	   * @return
	   * @throws SQLException
	   */
	  public static ResultSet getEnseignantTotal() throws SQLException{
		  st =connect.createStatement();
		   String sql = " SELECT  * FROM `enseignant`";
		  resultSet = st.executeQuery(sql);
		  return resultSet;
	  }
	  public static int getNbreEnseignantTotal()throws SQLException{
		  st =connect.createStatement();
		  String sql = " SELECT DISTINCT COUNT( * )  FROM `enseignant`";
		  resultSet = st.executeQuery(sql);
		  int nbreT = 0;
		  while (resultSet.next()) { 
				
			  String nbre= resultSet.getString("COUNT( * )");
			   nbreT =Integer.parseInt (nbre);
		}; 
				return nbreT;  
		  
	  }
	  
	  /**
	   * fonction qui retourne le nbre d'etudaint encadrant par l enseigant1(le premier encadrant)
	   * @param x : id de l'enseigant
	   * @return
	   * @throws SQLException
	   */
	  public int getNbreEtudiant_Enseignant(int x)throws SQLException{
		  st =connect.createStatement();
		  String sql = " SELECT  count(*) FROM `etudiant`WHERE ((etudiant.id_enseignant2 is null ) and ( `id_enseignant1` ="+x +"));";
		  
		  resultSet = st.executeQuery(sql);
		  int nbreT = 0;
		  while (resultSet.next()) { 
				
			  String nbre= resultSet.getString("COUNT(*)");
			   nbreT =Integer.parseInt (nbre);
		}; 
				return nbreT;  
	  }
	  
	 
	  public int getNbreEtudiant_EnseignantTotal(int x)throws SQLException{
		  st =connect.createStatement();
		  String sql = "SELECT  count(*) FROM `etudiant`WHERE `id_enseignant1` ="+x +"";
		  resultSet = st.executeQuery(sql);
		  int nbreT = 0;
		  while (resultSet.next()) { 
				
			  String nbre= resultSet.getString("COUNT(*)");
			   nbreT =Integer.parseInt (nbre);
		}; 
				return nbreT;  
	  }
	  
	  /**
	   * fonction qui retourne la liste des etudiants de l'enseignat dont l'id est x
	   * @param x
	   * @return ResultSet
	   * @throws SQLException
	   */
	  public ResultSet getEtudiant_Enseignant(int x)throws SQLException{
		  
		  st =connect.createStatement();
		   String sql = " SELECT * FROM `etudiant` WHERE `id_enseignant1` ="+x +"; ";
		  resultSet = st.executeQuery(sql);
		  return resultSet;
		  
		 
	  }
	  
	  public ResultSet getEtudiant_Enseignant1_unique(int x)throws SQLException{
		  
		  st =connect.createStatement();
		   String sql = " SELECT * FROM `etudiant`  WHERE ((etudiant.id_enseignant2 is null ) and ( `id_enseignant1` ="+x +"));";
		  resultSet = st.executeQuery(sql);
		  return resultSet;
		  
		
	  }
	  
	  
	  	  /**
	  	   * fonction qui retourne la dispo de l'enseignant
	  	   * @return
	  	   * @throws SQLException
	  	   */
	  public ResultSet getDisponibiliteEnseignant(int x) throws SQLException{
		  st =connect.createStatement();
		   String sql = " SELECT * FROM `disponibilite` WHERE id_enseignant = "+ x;
		  resultSet = st.executeQuery(sql);
		  return resultSet;
	  }
	  
	  public static ResultSet  getNbreDisponibiliteEnseignant(int x) throws SQLException{
		  st =connect.createStatement();
		  String nbre="";
		   String sql = " SELECT count(*) FROM `disponibilite` WHERE id_enseignant = "+x;
		  resultSet = st.executeQuery(sql);
		 /* int nbreT = 0;
		  while (resultSet.next()) { 
			  nbre= resultSet.getString("count(*)");
			   nbreT =Integer.parseInt (nbre);
		}; */
				return resultSet;
		  
	  }
	  
	  
	  
	  /**
	   *  fonction qui retourne la dispo de l'etudiant
	   * @param x
	   * @return
	   * @throws SQLException
	   */
	  public ResultSet getDisponibiliteEtudiant(int x) throws SQLException{
		  st =connect.createStatement();
		   String sql = " SELECT *  FROM `disponibilite_etudiant` WHERE id_etudiant = "+ x;
		  resultSet = st.executeQuery(sql);
		  return resultSet;
	  }
	  
	  /**
	   * fonction permet l'insertion du vecteur de dipo d'etudiant dans la table dispo_etudiant
	   * parametre : requete sql
	 * @throws SQLException 
	   */
	  public void insertDispoEtudiant(String sql) throws SQLException{
		  st =connect.createStatement();
		   st.executeUpdate(sql);
		  
	  }
	  /*********** fonction pour 2 encadrants ***************/
	  /**
		 * fonction qui retourne le nbre d'etudiants qui ont plus qu'un encadrant (2 encadrants)
		 * @return int nbre
		 * @throws SQLException
		 */
		  public static int getnbreEtu_Encad_non_unique() throws SQLException{
			  
			  st =connect.createStatement();
			   String sql = " SELECT COUNT(*) FROM `etudiant`WHERE `id_enseignant2` IS NOT NULL; ";
			  resultSet = st.executeQuery(sql);
			  int nbreT = 0;
			  while (resultSet.next()) { 
				  String nbre= resultSet.getString("COUNT(*)");
				   nbreT =Integer.parseInt (nbre);
			}; 
					return nbreT;
			  
		  }
	  /**
	   * fonction qui retourne les enseignant qui ont des etudiants dont le nbre des encadrant =2
	   * @param date choisie par le responsable
	   * @return
	   * @throws SQLException
	   */
	  public Vector<Integer> getEnseignant_NonUnique(String date) throws SQLException{
		  st =connect.createStatement();
		  int [] Tab1,Tab2 ; int nbre=0,id_en=0,j=0;
		  
		  String sql = "SELECT DISTINCT count(id_enseignant1) FROM `etudiant`,`enseignant` WHERE ( (`id_enseignant2` IS NOT NULL)"
		  +"and (etudiant.id_enseignant1 = enseignant.id_enseignant) and" +
		   " (enseignant.date_choisie = '"+ date+"'))";
		  resultSet = st.executeQuery(sql);
		  while (resultSet.next()) { 
				
			  String Nbr= resultSet.getString("count(id_enseignant1)");
			   nbre =Integer.parseInt (Nbr);
		}; 
		
		Tab1 = new int [nbre]; /// contient tous les id_enseignant1 des enseignats 
		sql = "SELECT DISTINCT id_enseignant1 FROM `etudiant`,`enseignant` WHERE ( (`id_enseignant2` IS NOT NULL)"
		 +"and (etudiant.id_enseignant1 = enseignant.id_enseignant) and" +
		"(enseignant.date_choisie = '"+ date+"'))";
		
		resultSet = st.executeQuery(sql);
		while (resultSet.next()) { 
			String IdE= resultSet.getString("id_enseignant1");
			   id_en =Integer.parseInt (IdE);
			   Tab1 [j]=id_en; j++;
		};
		 sql = "SELECT DISTINCT count(id_enseignant2) FROM `etudiant`,`enseignant` WHERE ( (`id_enseignant2` IS NOT NULL)"
				  +"and (etudiant.id_enseignant2 = enseignant.id_enseignant) and" +
				   " (enseignant.date_choisie = '"+ date+"'))";
		  
		 resultSet = st.executeQuery(sql);
		  while (resultSet.next()) { 
				
			  String Nbr= resultSet.getString("count(id_enseignant2)");
			   nbre =Integer.parseInt (Nbr);
		}; 
		
		Tab2 = new int [nbre]; j=0; /// Tab2 contient les id2 des enseignants
		sql = "SELECT DISTINCT id_enseignant2 FROM `etudiant`,`enseignant` WHERE ( (`id_enseignant2` IS NOT NULL)"
				 +"and (etudiant.id_enseignant2 = enseignant.id_enseignant) and" +
				"(enseignant.date_choisie = '"+ date+"'))";
 
		resultSet = st.executeQuery(sql);
		while (resultSet.next()) { 
			String IdE= resultSet.getString("id_enseignant2");
			   id_en =Integer.parseInt (IdE);
			   Tab2 [j]=id_en; j++;
		};
		
		/// il faut verifier Tab1 et Tab2 pour que leurs contenus soient differents : distinct
		Vector<Integer> vect =new Vector();
		for(int k = 0; k<Tab1.length ; k++){
			vect.add(Tab1[k]);
		}
		for(int k = 0; k<Tab2.length ; k++){
			vect.add(Tab2[k]);
		}
		vect = new java.util.Vector(new java.util.HashSet(vect)); /// contient les ids des enseignat non dupliques
		  return vect;
	  }
	  
	  /**
	   * fonction qui retourne la liste des etudaints qui ont 2 encadrants
	   * @return ResultSet
	   * @throws SQLException
	   */
  
	  public ResultSet getEtu_Encad_non_unique(String date) throws SQLException{
		  st =connect.createStatement();
		  int id_etudiant;
		   String sql = "SELECT distinct id_etudiant FROM `etudiant`,`enseignant` WHERE ((etudiant.id_enseignant2 is not null) and"+
		   "((etudiant.id_enseignant1 = enseignant.id_enseignant) or (etudiant.id_enseignant2 = enseignant.id_enseignant) )"+
			" and (enseignant.date_choisie = '" +date+"'))";
		   resultSet = st.executeQuery(sql); 
		  return resultSet;
	  }
	  /**
	   * fonction qui retourne le nombre des etudiants qu'il encadre un enseignant
	   * @param date
	   * @param id
	 * @return 
	   * @throws SQLException
	   */
	  
	  public int getNbre( int id_enseignant) throws SQLException{
		  st =connect.createStatement();
		  int nbre = 0;
		  String sql ="SELECT count(*) FROM enseignant, etudiant WHERE ((etudiant.id_enseignant2 IS NOT NULL)"+
"AND (enseignant.id_enseignant = "+id_enseignant+")AND ((etudiant.id_enseignant1 = enseignant.id_enseignant)"+
"OR (etudiant.id_enseignant2 = enseignant.id_enseignant)) AND (enseignant.date_choisie))";
		  
		  resultSet = st.executeQuery(sql);
			 while (resultSet.next()) { 
				 nbre =	 Integer.parseInt (resultSet.getString("count(*)"));
			 };
			  return nbre;
		  
	  }
	  
	  /**
	   * fonction retourne la date choisie par l'enseignant
	   * @param id_etudiant
	   * @param id_enseignant
	   * @return
	   * @throws SQLException
	   */
	  public String return_date( int id_enseignant)throws SQLException{
		  st =connect.createStatement();
		  String date_choisie="";
		  String sql =" SELECT date_choisie FROM `enseignant` WHERE id_enseignant =" + id_enseignant;
		  resultSet = st.executeQuery(sql);
		  while (resultSet.next()) { 
			  date_choisie = resultSet.getString("date_choisie");
		  };
	  
	  return date_choisie;
	  }
	  
	 
	  public int returni_idEns1(int id_etudiant)throws SQLException{
		  st =connect.createStatement();  
		  String sql ="SELECT id_enseignant1 FROM `etudiant` WHERE id_etudiant =" + id_etudiant;
		  int id=0;
		  resultSet = st.executeQuery(sql);
		  while (resultSet.next()) { 
			  id = Integer.parseInt (resultSet.getString("id_enseignant1"));
		  };
	  
	  return id; 
	  }
	  
	  public int returni_idEns2(int id_etudiant)throws SQLException{
		  st =connect.createStatement();  
		  String sql ="SELECT id_enseignant2 FROM `etudiant` WHERE (etudiant.id_enseignant2 IS NOT NULL) and id_etudiant =" + id_etudiant;
		  int id=0;
		  resultSet = st.executeQuery(sql);
		  while (resultSet.next()) { 
			  id = Integer.parseInt (resultSet.getString("id_enseignant2"));
		  };
	  return id; 
	  }
	  
	  
	  
	  
	  
	  /*nom prenom du jury*/
	  public String returni_prenomjury(int id_enseignant)throws SQLException{
		  st =connect.createStatement();  
		  String sql ="SELECT prenom_enseignant FROM `enseignant` WHERE id_enseignant =" + id_enseignant;
		 String prenom="";
		  resultSet = st.executeQuery(sql);
		  while (resultSet.next()) { 
			  prenom = resultSet.getString("prenom_enseignant");
		  };
	  return prenom; 
	  }
	  
	  public String returni_nomjury(int id_enseignant)throws SQLException{
		  st =connect.createStatement();  
		  String sql ="SELECT nom_enseignant FROM `enseignant` WHERE id_enseignant =" + id_enseignant;
		 String nom="";
		  resultSet = st.executeQuery(sql);
		  while (resultSet.next()) { 
			 nom = resultSet.getString("nom_enseignant");
		  };
	  return nom; 
	  }
	  
	  
	 /*nome et prenom de l'encadrant*/
	  public String returni_nomencadrant1(int id_etudiant)throws SQLException{
		  st =connect.createStatement();  
		  String sql ="SELECT  nom_enseignant FROM  `etudiant`, `enseignant` WHERE (etudiant.id_enseignant1 = enseignant.id_enseignant) AND id_etudiant =" + id_etudiant;
		  String nomen=" ";
		  resultSet = st.executeQuery(sql);
		  while (resultSet.next() ) { 
			   nomen= resultSet.getString("nom_enseignant");
		  };
	  return nomen ; 
	  }
	  public String returni_prenomencadrant1(int id_etudiant)throws SQLException{
		  st =connect.createStatement();  
		  String sql1 ="SELECT  prenom_enseignant FROM  `etudiant`, `enseignant` WHERE (etudiant.id_enseignant1 = enseignant.id_enseignant) AND id_etudiant =" + id_etudiant;
		  String prenomen=" ";
		  resultSet1 = st.executeQuery(sql1);
		  while (  resultSet1.next() ) { 
			   prenomen= resultSet1.getString("prenom_enseignant");
		  };
	  return prenomen; 
	  }
	  public String returnNomEntreprise(int id_etudiant)throws SQLException{
		  st =connect.createStatement();  
		  String sql1 ="SELECT  `nom_entreprise` FROM  `etudiant`, `entreprise` WHERE (etudiant.id_entreprise = entreprise.id_entreprise) AND id_etudiant =" + id_etudiant;
		  String prenomen=" ";
		  resultSet1 = st.executeQuery(sql1);
		  while (  resultSet1.next() ) { 
			   prenomen= resultSet1.getString("nom_entreprise");
		  };
	  return prenomen; 
	  }
	  
	  
	  
	  /*information sur le sujet*/
	  public String returni_sujet(int id_etudiant)throws SQLException{
		  st =connect.createStatement();  
		  String sql ="SELECT sujet FROM `etudiant` WHERE id_etudiant =" + id_etudiant;
		 String sujet="";
		  resultSet = st.executeQuery(sql);
		  while (resultSet.next()) { 
			  sujet = resultSet.getString("sujet");
		  };
	  return sujet; 
	  }
	  
	  /**donnees de l'etudiant*/
	  public String returni_nom(int id_etudiant)throws SQLException{
		  st =connect.createStatement();  
		  String sql ="SELECT nom_etudiant FROM `etudiant` WHERE id_etudiant =" + id_etudiant;
		 String nom="";
		  resultSet = st.executeQuery(sql);
		  while (resultSet.next()) { 
			  nom = resultSet.getString("nom_etudiant");
		  };
	  return nom; 
	  }

	  public String returni_prenom(int id_etudiant)throws SQLException{
		  st =connect.createStatement();  
		  String sql ="SELECT prenom_etudiant FROM `etudiant` WHERE id_etudiant =" + id_etudiant;
		 String prenom="";
		  resultSet = st.executeQuery(sql);
		  while (resultSet.next()) { 
			  prenom = resultSet.getString("prenom_etudiant");
		  };
	  return prenom; 
	  }
	  
	  /**********fonction globale ********/
	  
	  /**
	   * fonction qui retourne l'encadreur de l'etudiant (id_enseignant + disponibilite)
	   * @param x : id de l'etudiant
	   * @return 
	   * @throws SQLException
	   */
	  public  ResultSet getEnseignant(int x) throws SQLException{
			  st =connect.createStatement();
			   String sql = "SELECT `id_enseignant1`,  `disponibilite1`, `disponibilite2`, `disponibilite3`,`disponibilite4` "+
			  "FROM `disponibilite`, `etudiant` WHERE id_etudiant ="+ x +" AND etudiant.id_enseignant1 = disponibilite.id_enseignant ";
			  resultSet = st.executeQuery(sql);
			  return resultSet;
	  }
	  
	  /**
	   * fonction qui retourne le vect de dispo de l enseignant from table disponibilite_enseignant
	   * @param id de l enseignant
	   * @return
	   * @throws SQLException
	   */
	  public  ResultSet getDispoEnseignantFromDisp_En() throws SQLException{
		  st =connect.createStatement();
		  String sql = "SELECT * FROM `disponibilite_enseignant` ";
		 resultSet = st.executeQuery(sql);
		  return resultSet;
	  }
	  
	  
	  
	  /**
	   * fonction qui teste si la disponibilite de l enseignant existe dans la table "disponibilite_enseignant"
	   * @param id : id de l enseignat à tester
	   * @throws SQLException
	   */
	  
	  public static int ExisteDispoEnseignant(int id)throws SQLException{
		  int nombre = 0;
		  st =connect.createStatement();
		 String sql = "SELECT count(*) FROM `disponibilite_enseignant` WHERE id_enseignant ="+ id+"";
		 resultSet = st.executeQuery(sql);
		 while (resultSet.next()) { 
			  nombre =	 Integer.parseInt (resultSet.getString("count(*)"));
		 };
		
		 
		  return nombre;
	  }
	  /**
	   * fonction qui retourne le nbre des ensaignants dans la table disp-enseignant
	   * @return
	   * @throws SQLException
	   */
	  public static int getnbreDispoEnseignant()throws SQLException{
		  st =connect.createStatement();
		 String sql = "SELECT count(*) FROM `disponibilite_enseignant` ";
		 int nombre = 0;
		 resultSet = st.executeQuery(sql);
		 while (resultSet.next()) { 
			  nombre =	 Integer.parseInt (resultSet.getString("count(*)"));
		 };
		 return nombre;
		 
	  }
	  /**
	   * fonction qui retourne le contenue de la table disp-enseignant
	   */
	  public static  ResultSet getDispoEnseignant()throws SQLException{
		  st =connect.createStatement();
		 String sql = "SELECT * FROM `disponibilite_enseignant` ";
		 resultSet = st.executeQuery(sql);
		 return resultSet;
		 
	  }
	  
	  /**
	   * insertion dans la table Dispo Enseignant
	   * @param sql
	   * @throws SQLException
	   */
	  public void insertDispoEnseignant(String sql) throws SQLException{
		  st =connect.createStatement();
		   st.executeUpdate(sql);
		  
	  }
	  /**
	   * fonction qui permet d'effacer la dispo de l enseiagnt dans la table dispo-enseig
	   * @param sql
	   * @throws SQLException
	   */
	  public static void deleteDispoEnseignant(String sql) throws SQLException{
		  st =connect.createStatement();
		   st.executeUpdate(sql);
		  
	  }
	 
	  /**
	   * cette founction donne un tableau contenant l'intersection entre deux tableau 
	   */
	  public static int [] vecteur_intersection(int[] vect1,int[] vect2)
	  {   int k=0;
		  Vector<Integer> vecdispo=new Vector();
		  for(int i =0;i<vect2.length;i++){
			  for (int j=0;j<vect1.length;j++){
				  if(vect1[j]==vect2[i]){
					  vecdispo.add(vect1[j]);
				 
				  k++;
			  }
			  }
		  }
		  int n=vecdispo.size();
		  int[] tab=new int[n];
		  for(int l=0; l<tab.length; l++){
			  tab[l]= vecdispo.get(l);
			 
		  }
		  return tab;
	  }
	  
	  /**
	 * @throws SQLException ******************/
	  
	  public void delete_dispo_Etudiant() throws SQLException{
		  st =connect.createStatement(); 
		  int nombre;
			 String sql = "SELECT * FROM `disponibilite_etudiant` ";
			 resultSet = st.executeQuery(sql);
			 while (resultSet.next()) { 
				  nombre =	 Integer.parseInt (resultSet.getString("id_etudiant"));
				  String sql1 = "DELETE FROM `disponibilite_etudiant` WHERE id_etudiant = "+nombre;
					 deleteDispoEnseignant(sql1);
			 };
		  
		  
	  }
	  
	  public void delete_dispo_Enseignant() throws SQLException{
		  st =connect.createStatement(); 
		  int nombre;
			 String sql = "SELECT * FROM `disponibilite_enseignant` ";
			 resultSet = st.executeQuery(sql);
			 while (resultSet.next()) { 
				  nombre =	 Integer.parseInt (resultSet.getString("id_enseignant"));
				  String sql1 = "DELETE FROM `disponibilite_enseignant` WHERE id_enseignant = "+nombre;
					 deleteDispoEnseignant(sql1);
			 };
			 
	  }
	  
	  public static void verifDispoEnseignant(String date) throws SQLException{
		  st =connect.createStatement(); 
		  int id_enseignant; 
		  ResultSet resultSet1;
		  int nbre = 0;  String sql ="";
		  int nbreT = 0; int i=0;
		  int nbreEnseiTotal = getNbreEnseignantTotal();
		  int [] tab = new int[nbreEnseiTotal];
		  Statement st1 = connect.createStatement(); 
		  resultSet = getEnseignantTotal();
		  
		  while (resultSet.next()) { 
			  id_enseignant =Integer.parseInt (resultSet.getString("id_enseignant"));
			  tab[i]=id_enseignant;
			  i++;
		  };
		  //// parcourir tous les id des enseignants
		  for (int j = 0 ; j<tab.length;j++){
			  resultSet1 = getNbreDisponibiliteEnseignant(tab[j]);
			  while (resultSet1.next()) {
				  nbreT =Integer.parseInt (resultSet1.getString("count(*)"));
				 	 if (nbreT == 0){
					/// si le nbre=0 c a d l'encadrant n'a pas saisie dans la table 
					  /// on insere tq la disponibilite totale est affecté
					   sql = "INSERT INTO `disponibilite`( `disponibilite1`, `disponibilite2`, `disponibilite3`, `disponibilite4`, `id_enseignant`) VALUES ('8','12','14','18',"+
					  ""+ tab[j] +")";
					  
					   st1.executeUpdate(sql);
					  
				  }
				  
			  };
		  }
	  }
	  
	
	  public static boolean verifDispoEncadrant(String date, Statement st) throws SQLException{
		  boolean v=true;
		  st =connect.createStatement(); 
		  int id_enseignant; 
		  ResultSet resultSet1;
		  int nbre = 0;  String sql ="";
		  int nbreT = 0; int i=0;
		  int nbreEnseiTotal = getNbreEnseignantTotal();
		  int [] tab = new int[nbreEnseiTotal];
		  Statement st1 = connect.createStatement(); 
		  resultSet = getEnseignantTotal();
		  
		  while (resultSet.next()) { 
			  id_enseignant =Integer.parseInt (resultSet.getString("id_enseignant"));
			  tab[i]=id_enseignant;
			  i++;
		  };
		  //// parcourir tous les id des enseignants
		  for (int j = 0 ; j<tab.length;j++){
			  resultSet1 = getNbreDisponibiliteEnseignant(tab[j]);
			  while (resultSet1.next()) {
				  nbreT =Integer.parseInt (resultSet1.getString("count(*)"));
				 	 if (nbreT == 0){
					/// si le nbre=0 c a d l'encadrant n'a pas saisie dans la table 
					  /// on insere tq la disponibilite totale est affecté
					   sql = "INSERT INTO `disponibilite`( `disponibilite1`, `disponibilite2`, `disponibilite3`, `disponibilite4`, `id_enseignant`) VALUES ('8','12','14','18',"+
					  ""+ tab[j] +")";
					  
					 //  st1.executeUpdate(sql);
					  
				  }
				  
			  };
		  }
		  return v;
	  }
	  

	  
	  /*******Main*******/
	  
	  public static void main(String[] args) throws SQLException, ClassNotFoundException{
		  connexion_base();
		   	String date_soutenance = "2014-10-01";
		   	String sql1 = "DELETE FROM `disponibilite_enseignant` WHERE id_enseignant = "+2;
			System.out.println("aaaaaa");
			final requete_base_donne rq = new requete_base_donne();
			String date =rq.getDate_Soutenance();
			System.out.println("date de soutenance"+date);

		 
	  }
	
}

