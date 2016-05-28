package projet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Classifieur {
	
	Spam spam;
	private int tailleDico;
	String baseTest;
	int spamTest, hamTest, spamApp, hamApp;
	int[] bjSpam;
	int[] bjHam;
	
	public Classifieur(String fichier, String baseTest, int spamTest, int hamTest, int spamApp, int hamApp){
		this.tailleDico = tailleDico(fichier);
		this.baseTest = baseTest;
		this.spamTest = spamTest;
		this.hamTest = hamTest;
		this.spamApp = spamApp;
		this.hamApp = hamApp;
		bjSpam = new int[tailleDico];
		bjHam = new int[tailleDico];
		spam = new Spam(tailleDico);
	}
	
	public Spam getSpam() {
		return spam;
	}

	public void setSpam(Spam spam) {
		this.spam = spam;
	}

	public int tailleDico(String fichier){
		try {
			// lecture pour avoir la taille du tableau
			InputStream ips = new FileInputStream(fichier);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;
			while ((ligne = br.readLine()) != null) {
				if (ligne.length() >= 3)
					tailleDico++;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tailleDico;
	}
	
	public void calculBjSpam(){
		int[] vecteur = new int[tailleDico];
		for (int i = 0; i < spamApp; i++) {
			vecteur = spam.lire_message("baseApp/spam/"+i+".txt");
			for (int j = 0; j < vecteur.length; j++) {
				bjSpam[j] += vecteur[j];
			}
		}
	}
	
	public void calculBjHam(){
		int[] vecteur = new int[tailleDico];
		for (int i = 0; i < hamApp; i++) {
			vecteur = spam.lire_message("baseApp/ham/"+i+".txt");
			for (int j = 0; j < vecteur.length; j++) {
				bjHam[j] += vecteur[j];
			}
		}
	}
	
	public double probaSachantSpam(String fichier, int spamApp){
		double proba = 0;
		int[] vecteurX = spam.lire_message(fichier);
		double partie1;
		double partie2;
		double biSpam;
		double epsilon = 1;
		for (int i = 0; i < bjSpam.length; i++) {
			//System.out.println(spam.dictionnaire[i]+" "+bjSpam[i]);
			biSpam = (double)((double)((bjSpam[i])+epsilon)/(spamApp+(2*epsilon)));
			//System.out.println(spam.dictionnaire[i]+" "+biSpam);
			partie1 = (double)(Math.pow((double)(biSpam),(double)(vecteurX[i])));
			partie2 = (double)(Math.pow((double)(1-(double)(biSpam)),(double)(1-(double)(vecteurX[i]))));
			proba = (double)((double)(proba)+Math.log((double)((double)(partie1)*(double)(partie2))));
			//System.out.println(i+"       "+partie1+"     "+partie2+"     "+proba);
			
		}
		return proba;
	}
	
	public double probaSachantHam(String fichier, int HamApp){
		double proba = 0;
		int[] vecteurX = spam.lire_message(fichier);
		double partie1;
		double partie2;
		double biHam;
		double epsilon = 1;
		for (int i = 0; i < bjHam.length; i++) {
			//System.out.println(spam.dictionnaire[i]+" "+bjHam[i]);
			biHam = (double)((double)((bjHam[i])+epsilon)/(HamApp+(2*epsilon)));
			//System.out.println(spam.dictionnaire[i]+" "+biHam);
			partie1 = (double)(Math.pow((double)(biHam),(double)(vecteurX[i])));
			partie2 = (double)(Math.pow((double)(1-(double)(biHam)),(double)(1-(double)(vecteurX[i]))));
			proba = (double)((double)(proba)+Math.log((double)((double)(partie1)*(double)(partie2))));
			//System.out.println(i+"       "+partie1+"     "+partie2+"     "+proba);
			
		}
		return proba;
	}

}
