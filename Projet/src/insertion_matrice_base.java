import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class insertion_matrice_base {
	
	Statement st = null;
	ResultSet resultSet = null;
	ResultSet resultSet1 = null;
	Integer id_entreprise1;
	Integer id_enseignant1;
	public void insertion_matrice(String [][] matrice, String [][]vectEnseignant, Statement st,int nbl ) throws SQLException{
		
		try {
			
	for (int i =2; i<nbl;i++){
				
		String nom_enseignant = matrice[i][4];
		boolean existe = false;
		int idEnseignant = 0;
		
		
		for (int j = 0 ; j < vectEnseignant.length; j++){
			
			
			if(nom_enseignant.trim().equals(vectEnseignant[j][1].trim()) ) {
				
				existe= true;
				idEnseignant = j;
				//System.out.println("existe"+ existe);
				break;
			}
			}
		
		if (existe == true){
			System.out.println("existe"+ existe);
			System.out.println("idEnseignant : "+ idEnseignant);
			
			String sql = "INSERT INTO `entreprise`(`nom_entreprise`, `adresse_entreprise`) VALUES ('"+matrice[i][2]+"','"+matrice[i][3]+"')";
			st.executeUpdate(sql);
			
			String sql3 = "SELECT count(*) FROM `enseignant` WHERE id_enseignant = "+ idEnseignant;
			ResultSet resultSet1 =st.executeQuery(sql3);
			int nbre=0;
			while (resultSet1.next()) { 
				nbre = Integer.parseInt (resultSet1.getString("count(*)"));
			};
			System.out.println("nbre"+nbre);
			if (nbre == 0 ){
				
			String sql1 ="INSERT INTO `enseignant`(`id_enseignant`, `nom_enseignant`, `prenom_enseignant`) VALUES ("+idEnseignant+",'"+nom_enseignant+"','')";
			st.executeUpdate(sql1);
				
			}
			String sql2 = "SELECT id_entreprise FROM entreprise ORDER BY id_entreprise DESC LIMIT 0 , 1";
			
			
			resultSet = st.executeQuery(sql2);

			while (resultSet.next()) { 
				String id_entreprise= resultSet.getString("id_entreprise");
				 id_entreprise1 = Integer.parseInt(id_entreprise);
			
			}; 
			
			String sql4 = "INSERT INTO `etudiant`( `nom_etudiant`, `prenom_etudiant`, `sujet`, `id_entreprise`, `id_enseignant1`) VALUES ('"+matrice[i][0]+"','','"+matrice[i][1]
					+ "',"+ id_entreprise1+","+ idEnseignant +")";
					st.executeUpdate(sql4);
			System.out.println("donnée enregistré dans la base");
	}

	

	}

		
		
		/// insertion dans la base 
		
		
		
		
		/***********************************/

	
		
	}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
