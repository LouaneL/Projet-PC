package prodcons.v5;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class TestProdCons {
	
	public static void main(String[] args) throws InvalidPropertiesFormatException, IOException {
		Properties prop = new Properties();
		prop.loadFromXML(TestProdCons.class.getClassLoader().getResourceAsStream("./option.xml"));
		int nProd = Integer.parseInt(prop.getProperty("nProd"));
		int nCons = Integer.parseInt(prop.getProperty("nCons"));
		int bufSz = Integer.parseInt(prop.getProperty("bufSz"));
		int prodTime = Integer.parseInt(prop.getProperty("prodTime"));
		int consTime = Integer.parseInt(prop.getProperty("consTime"));
		int minProd = Integer.parseInt(prop.getProperty("minProd"));
		int maxProd = Integer.parseInt(prop.getProperty("maxProd"));
		int minCons = Integer.parseInt(prop.getProperty("minCons"));
		int maxCons = Integer.parseInt(prop.getProperty("maxCons"));

		ProdConsBuffer buffer = new ProdConsBuffer(bufSz);
		Producteur[] producteurs = new Producteur[nProd];
		Consommateur[] consommateurs = new Consommateur[nCons];

		for(int i = 0; i < nProd; i++) {
			producteurs[i] = new Producteur(buffer, prodTime, minProd, maxProd);
			producteurs[i].start();
		}

		for(int i = 0; i < nCons; i++) {
			consommateurs[i] = new Consommateur(buffer, consTime, minCons, maxCons);
			consommateurs[i].start();
		}
	}
}
