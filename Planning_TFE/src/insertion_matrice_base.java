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
	public void insertion_matrice(String [][] matrice, Statement st,int nbl ) throws SQLException{
		
		try {
			
	for (int i =2; i<nbl;i++){
				
				String sql = "INSERT INTO `entreprise`(`nom_entreprise`, `adresse_entreprise`) VALUES ('"+matrice[i][2]+"','"+matrice[i][3]+"')";
				String sql1 = "INSERT INTO `enseignant`( `nom_enseignant`, `prenom_enseignant`) VALUES ('"+matrice[i][4]+"','-')";

				st.executeUpdate(sql);
				st.executeUpdate(sql1);
				
				String sql2 = "SELECT id_entreprise FROM entreprise ORDER BY id_entreprise DESC LIMIT 0 , 1";
				String sql3 = "SELECT id_enseignant FROM enseignant ORDER BY id_enseignant DESC  LIMIT 0 , 1";
				
				resultSet = st.executeQuery(sql2);

				while (resultSet.next()) { 
					String id_entreprise= resultSet.getString("id_entreprise");
					 id_entreprise1 = Integer.parseInt(id_entreprise);
				
				}; 
                    
			
             resultSet1 = st.executeQuery(sql3);
             while (resultSet1.next()) { 
					String id_enseignant= resultSet1.getString("id_enseignant");
					 id_enseignant1 = Integer.parseInt(id_enseignant);
				
				}; 
				
				String sql4 = "INSERT INTO `etudiant`( `nom_etudiant`, `prenom_etudiant`, `sujet`, `id_entreprise`, `id_enseignant`, `id_soutenance`) VALUES ('"+matrice[i][0]+"','-','"+matrice[i][1]
						+  "',"+ id_entreprise1+","+ id_enseignant1 +  ",0)";
				st.executeUpdate(sql4);
		}
		System.out.println("donnée enregistré dans la base");
	
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
