import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;


public class test_creation {
	
	protected static Vector <Etudiant> listeEtudiant = new Vector <Etudiant> (); 
	protected static Vector <Enseignant> listeEnseignant = new Vector <Enseignant> (); 
	
	public static Vector<Etudiant> creationEtudiant(Statement st) throws SQLException{
		 String sql = "SELECT * FROM etudiant ";
		 ResultSet resultSet = st.executeQuery(sql);

			while (resultSet.next()) { 
				
				int id_etudiant= resultSet.getInt("id_etudiant");
				int id_enseignant= resultSet.getInt("id_enseignant");
				int id_entreprise= resultSet.getInt("id_entreprise");
				int id_soutenance= resultSet.getInt("id_soutenance");
				 
				String nom_etudiant = resultSet.getString("nom_etudiant");
				String prenom_etudiant = resultSet.getString("prenom_etudiant");
				
				Etudiant et = new Etudiant(id_etudiant);
				et.setNomEtudiant(nom_etudiant); et.setPrenomEtudiant(prenom_etudiant);
				et.setIdEnseignant(id_enseignant); et.setIdEntreprise(id_entreprise);
				et.setIdSoutenance(id_soutenance);
				
				listeEtudiant.add(et);
				
			
			};
			return listeEtudiant;
	 }

	public static Vector<Enseignant> creationEnseignant(Statement st) throws SQLException{
		
		String sql = "SELECT * FROM enseignant ";
		 ResultSet resultSet = st.executeQuery(sql);

			while (resultSet.next()) { 
				
				int id_enseignant= resultSet.getInt("id_enseignant");
				String []disponibilite = new String [4];
				String nom_enseignant = resultSet.getString("nom_enseignant");
				String prenom_enseignant = resultSet.getString("prenom_enseignant");
				String fonction_enseignant =  resultSet.getString("fonction_enseignant");
				Date date_choisie =  resultSet.getDate("date_choisie");
				
				Enseignant en = new Enseignant(id_enseignant);
				en.setIdEnseignant(id_enseignant);en.setNomEnseignant(nom_enseignant);
				en.setPrenomEnseignant(prenom_enseignant);en.setFonctionEnseignant(fonction_enseignant);
				en.setDate_choisie(date_choisie);
				
				
				String sql1 = "SELECT `disponibilite1`, `disponibilite2`,`disponibilite3`,`disponibilite4` FROM `disponibilite`, `enseignant`WHERE"+
				" disponibilite.id_enseignant = "+id_enseignant;
				ResultSet resultSet1 = st.executeQuery(sql);
				while (resultSet1.next()) {
					disponibilite[0]=resultSet.getString("disponibilite1");
					disponibilite[1]=resultSet.getString("disponibilite2");
					disponibilite[2]=resultSet.getString("disponibilite3");
					disponibilite[3]=resultSet.getString("disponibilite4");
				}
				en.setDisponibilite(disponibilite);
				
				listeEnseignant.add(en);

		
		}
			return listeEnseignant;
	}
	

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub

		connexion_base cnx = new connexion_base();
		try {
			cnx.connexion_base();
			} 
		catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Statement st = null;
		 try {
			st = cnx.getst();
		 		}
		 catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		 }
		 
		 creationEtudiant(st);
		 
		
		
	}

}
