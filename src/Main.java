// MyVisitorsCenter
// Linard Gauthier

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    // Constantes
    private final static int COUNTRY_SELECTION = 0;
    private final static int COUNTRY_VERIFICATION = 1;
    private final static int CITY_SELECTION = 2;
    private final static int NB_DAYS_SELECTION = 3;
    private final static int ACTIVITIES_CREATION = 4;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String country = ""; // Pays sélectionné par l'utilisateur
        ArrayList<String> cities = new ArrayList<>(); // La liste des villes correspondant au pays choisi
        String city = ""; // Ville choisi par l'utilisateur
        int nbDays = 0;
        boolean finished = false;

        int state = 0; // L'état de progression de l'utilisateur
        int nextstate = 0; // L'état suivant de progression de l'utilisateur

        // Liste des différentes étapes afin d'obtenir la liste des activités
        do {
            switch (state) {
                // Sélection du pays
                // TODO: Changer la manière dont le pays est choisi
                case COUNTRY_SELECTION:
                    // Vide la liste
                    cities.clear();

                    System.out.println("Veuillez choisir le pays de visite suivant leur code ISO (BE, FR...) : ");
                    country = scanner.nextLine().toUpperCase();

                    nextstate = COUNTRY_VERIFICATION;
                    break;

                // Vérification du pays choisi
                case COUNTRY_VERIFICATION:
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader("src/Data/country.txt"));
                        boolean isCountryExists = false;

                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (country.equals(line)) {
                                isCountryExists = true;
                                break;
                            }
                        }
                        reader.close();

                        // Si le pays n'est pas correct, on return dans l'état précédent
                        if (!isCountryExists) {
                            System.out.println("Le pays n'existe pas dans la liste.");
                            nextstate = COUNTRY_SELECTION;
                            System.out.println("\n--------------------------------------------");
                            break;
                        }
                    } catch (IOException e) { // Si erreur, retour à l'étape de départ
                        System.out.println(e.toString());
                        nextstate = COUNTRY_SELECTION;
                        System.out.println("\n--------------------------------------------");
                        break;
                    }

                    // Si le pays existe
                    // Ajout des villes de ce pays dans la variable cities
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader("src/Data/city.txt"));

                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] info = line.split(",");

                            if (info[0].toUpperCase().equals(country)) {
                                cities.add(info[1]);
                            }
                        }
                        reader.close();

                        // Si la liste des villes du pays à visiter est vide
                        if (cities.isEmpty()) {
                            nextstate = COUNTRY_SELECTION;
                            System.out.println("Il n'y a pour l'instant aucune ville disponible pour ce pays. Revenez plus tard !");
                            System.out.println("\n--------------------------------------------");
                            break;
                        }

                        nextstate = CITY_SELECTION;
                    } catch (IOException e) { // Si erreur, retour à l'étape de départ
                        System.out.println(e.toString());
                        nextstate = COUNTRY_SELECTION;
                        System.out.println("\n--------------------------------------------");
                    }
                    break;

                // Sélection de la ville à visiter
                case CITY_SELECTION:
                    System.out.println("Veuillez choisir le nom de la ville à visiter. Les villes disponibles sont listées ci-dessous. Pour choisir un nouveau pays, laissez vide.");
                    System.out.println(cities.toString());
                    city = scanner.nextLine();

                    // Si changement de pays
                    if (city.isEmpty()) {
                        nextstate = COUNTRY_SELECTION;
                        System.out.println("\n--------------------------------------------");
                        break;
                    }

                    if (!cities.contains(city.toUpperCase())) {
                        System.out.println("Cette ville n'est pas disponible pour ce pays.");
                        nextstate = state;
                    } else {
                        nextstate = NB_DAYS_SELECTION;
                    }
                    break;

                // Choix du nombre de jours
                case NB_DAYS_SELECTION:
                    System.out.println("Veuillez spécifier le nombre de jours du séjour. Laisser vide pour arrêter la création du programme.");
                    String input = scanner.nextLine();

                    if (input.isEmpty()) {
                        nextstate = COUNTRY_SELECTION;
                        System.out.println("\n--------------------------------------------");
                        break;
                    }

                    // Conversion de la chaine de caractère en nombre
                    try {
                        nbDays = Integer.parseInt(input);
                        nextstate = ACTIVITIES_CREATION;
                    } catch (Exception e) {
                        System.out.println(e.toString());
                        nextstate = state;
                    }
                    break;

                // Préparation et listing des différentes activités à proposer à l'utilisateur
                case ACTIVITIES_CREATION:
                    System.out.println("Vous avez prévu un voyage de "+ nbDays +" jour(s) à "+ city +". La proposition des activités est listée ci-dessous.");

                    // TODO Calculer la proposition des activités

                    System.out.println("Voulez-vous recréer un nouveau programme d'activités ? [oui/non]");

                    // Recréation d'un programme ou pas ?
                    if (scanner.nextLine().toUpperCase().equals("OUI")) {
                        nextstate = COUNTRY_SELECTION;
                        System.out.println("\n--------------------------------------------");
                    } else {
                        finished = true;

                        System.out.println("Merci d'avoir utilisé notre programme d'organisation de séjour. En espérant vous revoir.");
                    }
                    break;

                // Gestion d'un cas inconnu -> Retour à la 1e étape
                default:
                    System.out.println("Une erreur inconnue c'est produite.");
                    nextstate = COUNTRY_SELECTION;
                    break;
            }

            state = nextstate;
        } while (!finished);
    }
}
