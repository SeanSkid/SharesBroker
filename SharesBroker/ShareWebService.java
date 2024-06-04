/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.xml.datatype.DatatypeConfigurationException;

/**
 *
 * @author JubJub
 */
@WebService(serviceName = "ShareWebService")
@Stateless()
public class ShareWebService {
    

    private String[] SearchStockSymbol(String SearchTerm) throws IOException, DatatypeConfigurationException {
        String NameVar = "";
        String CurrVar = "";
        String PriceString = "";
        String AvailString = "";
        
        try {
            String SearchUrl = "https://financialmodelingprep.com/api/company/profile/" + SearchTerm;
            URL url = new URL(SearchUrl);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
                for (String line; (line = reader.readLine()) != null;) {
                    System.out.println(line);
                    if (line.contains("companyName")) {
                        String[] arrayString = line.split(":", 0);
                        NameVar = arrayString[1].substring(2, arrayString[1].length() -2);
                        //System.out.println("FOUND THE COMPANYNAME: " + NameVar);
                    }
                    else if(line.contains("Price")) {
                        String[] arrayString = line.split(":", 0); 
                        PriceString = arrayString[1].substring(1, arrayString[1].length() -1);
                        //System.out.println("FOUND THE PRICE: " + PriceString);
                    }
                    else if(line.contains("exchange")) {
                        CurrVar = SearchForCurrency(SearchTerm);
                        //System.out.print("FOUND THE CURRENCY: " + CurrVar);
                    }
                    else if(line.contains("VolAvg")) {
                        String[] arrayString = line.split(":", 0); 
                        AvailString = arrayString[1].substring(2, arrayString[1].length() -2);
                        //System.out.print("FOUND THE AVAILABLESHARES: " + AvailString);
                    }
                }
            } catch(NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
            }
        } catch(NumberFormatException nfe) {
            System.out.println("NumberFormatException: " + nfe.getMessage());
        }
        String[] CompanyInfo = {NameVar, SearchTerm, CurrVar, PriceString, AvailString};
        return CompanyInfo;
    }

    private String SearchStockName(String SearchTerm) throws IOException {
        String SymbVar = "";
        try {
            String SearchUrl = "https://financialmodelingprep.com/api/v3/search?query=" + SearchTerm;
            URL url = new URL(SearchUrl);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
                for (String line; (line = reader.readLine()) != null;) {
                    System.out.println(line);
                    if(line.contains("symbol")) {
                        String[] arrayString = line.split(":", 0);
                        SymbVar = arrayString[1].substring(2, arrayString[1].length() - 2);
                        //System.out.println("FOUND THE SYMBOL: " + SymbVar);
                    }
                }
            } catch(NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
            }
        } catch(NumberFormatException nfe) {
            System.out.println("NumberFormatException: " + nfe.getMessage());
        }
        return SymbVar;
    }
    
    private String SearchForCurrency(String SearchTerm) throws IOException {
        String CurrVar = "";
        try {
            String SearchUrl = "https://financialmodelingprep.com/api/v3/search?query=" + SearchTerm;
            URL url = new URL(SearchUrl);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
                for (String line; (line = reader.readLine()) != null;) {
                    System.out.println(line);
                    if(line.contains("currency")) {
                        String[] arrayString = line.split(":", 0);
                        CurrVar = arrayString[1].substring(2, arrayString[1].length() - 2);
                        //System.out.println("FOUND THE CURRENCY: " + CurrVar);
                    }
                }
            } catch(NumberFormatException nfe) {
                System.out.println("NumberFormatException: " + nfe.getMessage());
            }
        } catch(NumberFormatException nfe) {
            System.out.println("NumberFormatException: " + nfe.getMessage());
        }
        return CurrVar;
    }
    
    /**
     * Web service operation
     *
     */
    public String getCompanyInfoSymbol(@WebParam(name = "CompanySymbol") String CompanySymbol) {
        try {
            String[] result = SearchStockSymbol(CompanySymbol);
            System.out.print(result[0] + "/" + result[1] + "/" + result[2] + "/" + result[3] + "/" + result[4]);
            return (result[0] + "/" + result[1] + "/" + result[2] + "/" + result[3] + "/" + result[4]);
        } catch (IOException | DatatypeConfigurationException ex) {
            Logger.getLogger(ShareWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Web service operation
     *
     */
    public String getCompanyInfoName(@WebParam(name = "CompanyName") String CompanyName) {
        try {
            String CompanySymbol = SearchStockName(CompanyName);
            String[] result = SearchStockSymbol(CompanySymbol);
            System.out.print(result[0] + "/" + result[1] + "/" + result[2] + "/" + result[3] + "/" + result[4]);
            return (result[0] + "/" + result[1] + "/" + result[2] + "/" + result[3] + "/" + result[4]);
        } catch (IOException | DatatypeConfigurationException ex) {
            Logger.getLogger(ShareWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    
}
