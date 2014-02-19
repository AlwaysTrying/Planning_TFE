import javax.naming.NamingException;

/**
 * 
 */

/**
 * @author WAFA
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws NamingException {
		String login, mot_de_passe;
		login="";
		mot_de_passe="";
		Connexion conn=new Connexion(login,mot_de_passe);
		Connexion.connect();
		Connexion.getUserAttributes();
		
		
	}
}
