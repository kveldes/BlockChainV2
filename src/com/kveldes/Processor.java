package com.kveldes;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Processor implements Callable<String>{
    private static Logger logger = Logger.getLogger(Processor.class.getName());

    private int id;
    private String hash;
    private String previousHash;
    private String data;
    private long timeStamp;
    private int nonce;
    private int prefix;

    public Processor(int id,String data,String previousHash,long timeStamp,int prefix){
        this.id = id;
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = timeStamp;
        this.hash = calculateBlockHash();
        this.prefix=prefix;
    }

    @Override
    public String call() throws Exception {
          //Thread.sleep(1000);
          String originalhash=mineBlock(prefix);
          String fullhash=originalhash+"#"+nonce;
          return fullhash;
    }

        public String mineBlock(int prefix) {
            String prefixString = new String(new char[prefix]).replace('\0', '0');
            while (!hash.substring(0, prefix).equals(prefixString)) {
                nonce++;
                hash = calculateBlockHash();
            }
            return hash;
        }

    public String calculateBlockHash() {
        String dataToHash = previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + data;
        MessageDigest digest = null;
        byte[] bytes = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(dataToHash.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
        }
        StringBuffer buffer = new StringBuffer();
        for (byte b : bytes) {
            buffer.append(String.format("%02x", b));
        }
        return buffer.toString();
    }


    public int nonce() {
        return this.nonce;
    }


}
