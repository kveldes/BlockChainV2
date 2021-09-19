package com.kveldes;

//Helping class for Product objects
public class Product {
    private int aa_id;
    private int code;
    private String title;
    private String dtime;
    private int    cost;
    private String desc;
    private String categ;
    private String hash;
    private String prvhash;
    private String data;
    private long timestamp;
    private int nonce;




    //Getters of the class
    public int getAaId(){
        return aa_id;
    }
    public int  getCode(){
        return code;
    }
    public String getTitle(){
        return title;
    }
    public String getdtime(){
        return dtime;
    }
    public int  getCost(){
        return cost;
    }
    public String getDescription(){
        return desc;
    }
    public String getCategory(){
        return categ;
    }
    public String getHash(){
        return hash;
    }
    public String getPrevHash(){
        return prvhash;
    }
    public String getData(){
        return data;
    }
    public long getTimestamp(){
        return timestamp;
    }
    public int getNonce(){
        return nonce;
    }

    //Setters of the class
    public void setAaId(int aa_id){
        this.aa_id= aa_id;
    }
    public void setCode(int code){
        this.code = code;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setTime(String dtime){
        this.dtime = dtime;
    }
    public void setCost(int cost){
        this.cost = cost;
    }
    public void setDescription(String desc){
        this.desc = desc;
    }
    public void setCategory(String categ){
        this.categ = categ;
    }

    public void setHash(String hash){
        this.hash=hash;
    }

    public void setPreviusHash(String prvhash){
        this.prvhash=prvhash;
    }

    public void setData(String data){
        this.data=data;
    }
    public void setTimestamp(long timestamp){
        this.timestamp=timestamp;
    }
    public void setNonce(int nonce){
        this.nonce=nonce;
    }


}
