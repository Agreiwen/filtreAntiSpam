package projet;

import java.util.Scanner;

public class filtreAntiSpam {
	
	public static void main(String[] args) {
		System.out.println("****** Projet Apprentissage ******\n");
		String baseTest = args[0];
		int spamTest = Integer.parseInt(args[1]);
		int hamTest = Integer.parseInt(args[2]);
		String fichier = "dictionnaire1000en.txt";
		System.out.print("Combien de SPAM dans la base d'apprentissage ? ");
		Scanner sc = new Scanner(System.in);
		int spamApp = sc.nextInt();
		System.out.print("Combien de HAM dans la base d'apprentissage ? ");
		int hamApp = sc.nextInt();
		sc.close();
		
		Classifieur classifieur = new Classifieur(fichier, baseTest, spamTest, hamTest, spamApp, hamApp);
		classifieur.getSpam().charger_dictionnaire(fichier);
		
		System.out.println("\nApprentissage...\n");
		classifieur.calculBjSpam();
		classifieur.calculBjHam();
		
		double test = classifieur.probaSachantSpam("basetest/spam/1.txt", spamApp);
		System.out.println(test);
		
	}

}
