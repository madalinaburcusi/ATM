public class Account {

    private String userName;
    private String IBAN;
    private String PIN;
//    public List<Services> accounts;


    public Account(){
        userName = "";
        IBAN = "";
        PIN = "";
    }

    public Account(String userName, String iban, String pin) {
        this.userName = userName;
        this.IBAN = iban;
        PIN = pin;
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

}
