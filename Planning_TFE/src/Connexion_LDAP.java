import java.awt.*;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.*;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/**
 * La classe permettant le connexion au serveur LDAP
 * @author WAFA
 *
 */
public class Connexion_LDAP {
	
	 static Hashtable env ;
	 static DirContext ctx ;
	 static String login;
	 static String passwd;
	 static String base = "dc=ec-nantes,dc=fr";
     static String filter = "(cn~=imen)"; 
	 public Connexion_LDAP(String login, String passwd){
		 this.login=login;
		 this.passwd=passwd;
	 }
	 
	@SuppressWarnings("rawtypes")
	public static void connect() throws NamingException{
		env= new Hashtable();
		
		// Mise en oeuvre de la factory
		env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
		// Ajout des information dans la variable d'environnement
		env.put(Context.PROVIDER_URL, "ldap://rldap.ec-nantes.fr");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, "uid=" + login + ",ou=people,dc=ec-nantes,dc=fr");
		env.put(Context.SECURITY_CREDENTIALS, passwd);
		// Test de la connexion
		try {
			ctx = new InitialDirContext(env);
		// fermeture de la connexion	
		} catch (Exception e) {
			System.out.println("marche");
			e.getMessage();
		}
	}
	
	
	 public static void getUserAttributes() throws NamingException{
	try{
		
		SearchControls sc = new SearchControls();  
		
		 sc.setSearchScope(SearchControls.SUBTREE_SCOPE);  
		 NamingEnumeration results = null;  
		 results = ctx.search(base, filter, sc);  
        while (results.hasMore()) {    
            SearchResult sr = (SearchResult) results.next();  
            String dn = sr.getName();
            System.out.println("Distinguished name is "+dn);
            
            Attributes attrs = sr.getAttributes();
            for(NamingEnumeration ne = attrs.getAll();ne.hasMoreElements();) {
            Attribute attr = (Attribute) ne.next();
            String attrID = attr.getID();
            System.out.println(attrID+" :");
            for(Enumeration vals = attr.getAll();vals.hasMoreElements() ;){
            System.out.println("\t"+vals.nextElement());
            }
            }
            System.out.println("\n");
            }
           // System.out.println(sr.toString()+"\n");  
        }
        catch (NamingException nex) {  
             
            System.out.println("Error: " + nex.getMessage()); }
		
	 }
	 
	
	/**
	 * 
	 * @throws NamingException
	 */
	public void disconnect() throws NamingException{
		ctx.close();
	}
}