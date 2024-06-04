package seansharebrokerapp;

import seansharebrokerapp.BrokerUI;

public class SharesThread extends Thread
{

    public SharesThread() {
        
    }
    
    public  void GetUpdatedPrice()
    {
        while(true) {
            for(int x = 0; x < BrokerUI.Stocks.size(); x++ ) {
                String result = getCompanyInfoSymbol(BrokerUI.Stocks.get(x));
                String[] CompanyInfo = result.split("/", 5);
                for(int y = 0; y < BrokerUI.SharePrices.size(); y++) {
                    if (CompanyInfo[1].equals(BrokerUI.Stocks.get(y))) {
                        BrokerUI.SharePrices.set(y, CompanyInfo[3]);
                        BrokerUI.AvailShares.set(y, CompanyInfo[4]);
                        //System.out.print("\nUpdated " + BrokerUI.Stocks.get(y) + " SharePrice: " + BrokerUI.SharePrices.get(y) + " AvailableShares: " + BrokerUI.AvailShares.get(y));
                    }
                }
            }
            BrokerUI.UpdatedPrices = true;
            //System.out.print("Updated Share Prices!");
            try {
                this.sleep(15000);
            } catch(java.lang.InterruptedException ie) {
                System.out.print(ie);
            };
        }
    }
    
    public void run()
    {
        GetUpdatedPrice();
    }

    private static String getCompanyInfoSymbol(java.lang.String companySymbol) {
        org.me.ShareWebService_Service service = new org.me.ShareWebService_Service();
        org.me.ShareWebService port = service.getShareWebServicePort();
        return port.getCompanyInfoSymbol(companySymbol);
    }
    
}
