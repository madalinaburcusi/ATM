public class Account {

    private String userName;
    private String IBAN;
    private String PIN;
    private String CVV;
//    public List<Services> accounts;


    public Account(){
        userName = "";
        IBAN = "";
        PIN = "";
        CVV = "";
    }

    public Account(String userName, String iban, String pin, String cvv) {
        this.userName = userName;
        this.IBAN = iban;
        PIN = pin;
        CVV = cvv;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }
    public String getUserName(){
        return userName;
    }

    public void setIBAN(String IBAN){
        this.IBAN = IBAN;
    }
    public String getIBAN(){
        return IBAN;
    }

    public void setPIN(String PIN){
        this.PIN =PIN;
    }
    public String getPIN(){ return PIN;}

    public void setCVV(String CVV){ this.CVV =CVV; }
    public  String getCVV(){
        return CVV;
    }
}
