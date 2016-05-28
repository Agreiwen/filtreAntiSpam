package projet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Spam {

	String[] dictionnaire;
	int tailleDico;
	
	public Spam(int tailleDico){
		this.tailleDico = tailleDico;
		dictionnaire = new String[tailleDico];
	}
	
	public void charger_dictionnaire(String fichier) {

		// lecture du fichier texte
		try {
			String ligne;
			// lecture pour remplir le tableau
			InputStream ips = new FileInputStream(fichier);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			int indice = 0;
			while ((ligne = br.readLine()) != null) {
				if (ligne.length() >= 3) {
					dictionnaire[indice] = ligne;
					indice++;
				}
			}
			br.close();
			//System.out.println("\nChargement du dictionnaire ok.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int[] lire_message(String fichier) {

		int[] vecteur = new int[tailleDico];
		// lecture du fichier texte
		try {
			InputStream ips = new FileInputStream(fichier);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;
			String ligneConvert;
			int decimal;

			while ((ligne = br.readLine()) != null) {
				ligneConvert = "";
				for (int i = 0; i < ligne.length(); i++) {
					decimal = (int) ligne.charAt(i);
					if (decimal <= 64 || (decimal >= 91 && decimal <= 96) || decimal >= 123) {
						ligneConvert += " ";
					}
					else{
						ligneConvert += ligne.charAt(i);
					}
				}
				String[] mots = ligneConvert.split(" ");
				for (int j = 0; j < mots.length; j++) {
					for (int k = 0; k < dictionnaire.length; k++) {
						if (mots[j].equalsIgnoreCase(dictionnaire[k])) {
							//System.out.println(mots[j]+" "+dictionnaire[k]+" "+k);
							vecteur[k] = 1;
						}
					}
				}
			}
			br.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return vecteur;
	}
}
