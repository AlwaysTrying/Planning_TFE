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
		Connexion_LDAP conn=new Connexion_LDAP(login,mot_de_passe);
		Connexion_LDAP.connect();
		Connexion_LDAP.getUserAttributes();
		
		
	}
}
