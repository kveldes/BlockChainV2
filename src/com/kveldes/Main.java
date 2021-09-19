package com.kveldes;

/**Add to project structure the followings source from maven (if you have't):
 com.google.code.gson:gson:2.8.6
 org.xerial:sqlite-jdbc:3.28.0**/

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;


public class Main {

    public static List<Block> blockchain = new ArrayList<Block>();
    public static int userChoice;
    public static int prefix = 4;
    public static int counter = 0;

    public static void main(String[] args) {




        boolean quit = false;
        new InitialDB();
        DatabaseMng db = new DatabaseMng();


        do {
            Scanner input = new Scanner(System.in);

            /***************************************************/

            System.out.println("Please Choose from these choices");
            System.out.println("-------------------------\n");
            System.out.println("1 - View all Products");
            System.out.println("2 - Add a Product");
            System.out.println("3 - Add multiple products");
            System.out.println("4 - Search for a product");
            System.out.println("5 - Viw statistics of a Product");
            System.out.println("6 - Exit");

            userChoice = input.nextInt();

//-------------------------------------------------------------------------------------------------------------------------------------------------------------/
            switch (userChoice) {
                case 1:
                    System.out.println("\nYou've chosen item #1 - View all Products");
                    db.viewProductsByTitle();
                    break;
//-------------------------------------------------------------------------------------------------------------------------------------------------------------/
                case 2:  //case 2-1 Add one product
                        //case 2-->flow 1-This part of code is if the db have no data


                    int test = db.viewIfDbIsEmpty();//Check if Db is empty
                    if (test == 0) {
                        //This part of code is if the db does not have any data inside
                        System.out.println("\nYou've chosen item #2 - Add a Product");
                        Scanner myObj = new Scanner(System.in);  // Create a Scanner object

                        System.out.println("\nPlease Enter the A/A of the Product");
                        int aa = myObj.nextInt();  // Read user input

                        System.out.println("\nPlease Enter the code of the Product");
                        int code = myObj.nextInt();  // Read user input

                        System.out.println("\nPlease Enter the tile of the Product");
                        String title = myObj.next();  // Read user input

                        System.out.println("\nPlease Enter the cost of the Product");
                        int cost = myObj.nextInt();  // Read user input

                        System.out.println("\nPlease Enter the description of the Product");
                        String desc = myObj.next();  // Read user input

                        System.out.println("\nPlease Enter the category of the Product");
                        String categ = myObj.next();  // Read user input

                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        String dtime = dtf.format(now);

                        String data = aa + "#" + code + "#" + title + "#" + dtime + cost + "#" + desc + "#" + categ;


                        long startTime = System.nanoTime();
                        System.out.println("Process Started");

                        String prvhash="0";
                        int nonce=blockchainNode3(data,prvhash,prefix);

                        //Transform BlockChain into Json (and not print it)
                        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
                        System.out.println(blockchainJson);


                        //Μετατρέπω απο balockchainJson απο συλλογη από Objects Block σε list από objects Block
                        Gson gson = new Gson();
                        //Block[] founderArray = gson.fromJson(blockchainJson, Block[].class);
                        Type listType = new TypeToken<ArrayList<Block>>(){}.getType();
                        List<Block> blockList = new Gson().fromJson(blockchainJson, listType);

                        int lastElemOfArray = blockList.size() - 1;


                        for (int i = 0; i < blockList.size()-1; i++) {
                            System.out.println("---------------------------------------------------------");
                            System.out.println("hash: " + blockList.get(i).getHash());
                            System.out.println("previousHash: " + blockList.get(i).getPreviousHash());
                            System.out.println("data: " + data);
                            System.out.println("timeStamp: " + blockList.get(i).timeStamp());
                            System.out.println("nonce: " + blockList.get(i).nonce());
                            System.out.println("---------------------------------------------------------");
                        }

                        String hash =blockList.get(lastElemOfArray).getHash();
                        long  timeStamp =blockList.get(lastElemOfArray).timeStamp();





                        // Check for validity
                        System.out.println("\nBlockchain is Valid: " + isChainValid());

                        long endTime = System.nanoTime();
                        long duration = endTime - startTime;
                        System.out.println("Total time ellapsed: " + (float) duration / 1000000000 + " seconds");

                        db.insert(aa, code, title, dtime, cost, desc, categ, hash, prvhash, data, timeStamp, nonce);
                        counter++;

//-----------------------------------------------------------------------------------------------------------------------------------------------//
                    } else {    //Case 2-2-Add one product
                               //Case 2--> flow 2-This part of code is if the db have data

                        List<Product> productArrayDb = db.pullDataFromDB();
                        int lastElementOfArray = productArrayDb.size() - 1;
                        Product product = productArrayDb.get(lastElementOfArray);


                        System.out.println("\nYou've chosen item #2 - Add a Product");
                        Scanner myObj = new Scanner(System.in);  // Create a Scanner object

                        System.out.println("\nPlease Enter the A/A of the Product");
                        int aa = myObj.nextInt();  // Read user input

                        System.out.println("\nPlease Enter the code of the Product");
                        int code = myObj.nextInt();  // Read user input

                        System.out.println("\nPlease Enter the tile of the Product");
                        String title = myObj.next();  // Read user input

                        System.out.println("\nPlease Enter the cost of the Product");
                        int cost = myObj.nextInt();  // Read user input

                        System.out.println("\nPlease Enter the description of the Product");
                        String desc = myObj.next();  // Read user input

                        System.out.println("\nPlease Enter the category of the Product");
                        String categ = myObj.next();  // Read user input

                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        String dtime = dtf.format(now);

                        String data = aa + "#" + code + "#" + title + "#" + dtime + cost + "#" + desc + "#" + categ;


                        long startTime = System.nanoTime();
                        System.out.println("Process Started");

                        String latesthash = product.getHash();



                       int nonce=blockchainNode3(data,latesthash,prefix);


                        //Transform BlockChain into Json (and not print it)
                        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
                        // System.out.println("\nThe block chain: ");
                        // System.out.println(blockchainJson);


                        //Μετατρέπω απο balockchainJson απο συλλογη από Objects Block σε λιστα από objects Block
                        Gson gson = new Gson();
                        //Block[] founderArray = gson.fromJson(blockchainJson, Block[].class);

                        Type listType = new TypeToken<ArrayList<Block>>(){}.getType();
                        List<Block> blockList = new Gson().fromJson(blockchainJson, listType);

                        int lastElemOfArray = blockList.size() - 1;


                        String hash =blockList.get(lastElemOfArray).getHash();
                        String prvhash =blockList.get(lastElemOfArray).getPreviousHash();
                        long  timeStamp =blockList.get(lastElemOfArray).timeStamp();
                        blockList.get(lastElemOfArray).setNonce(nonce);



                        // Check for validity
                        System.out.println("\nBlockchain is Valid: " + isChainValid());

                        if (isChainValid()) {
                            db.insert(aa, code, title, dtime, cost, desc, categ, hash, prvhash, data, timeStamp, nonce);


                            for (int i = 0; i <= productArrayDb.size() - 1; i++) {
                                Product productprint = productArrayDb.get(i);
                                System.out.println("---------------------------------------------------------");
                                System.out.println("hash: " + productprint.getHash());
                                System.out.println("previousHash: " + productprint.getPrevHash());
                                System.out.println("data: " + data);
                                System.out.println("timeStamp: " + productprint.getTimestamp());
                                System.out.println("nonce: " + productprint.getNonce());
                                System.out.println("---------------------------------------------------------");
                            }


                        } else {
                            System.out.println("Something happen the chain is not valid");
                        }

                        long endTime = System.nanoTime();
                        long duration = endTime - startTime;
                        System.out.println("Total time ellapsed: " + (float) duration / 1000000000 + " seconds");

                        counter++;

                        productArrayDb.clear();
                        blockchain.clear();

                    }
                    break;

//-------------------------------------------------------------------------------------------------------------------------------------------------------------/
                case 3:  //Case 3-1 -->Flow 1-Add multiple products
                        //check if db is empty
                    int test1 = db.viewIfDbIsEmpty();
                    if (test1 == 0)
                        //if DB is empty
                    {
//                        List<Product> productArrayDb = db.pullDataFromDB();
//                        int lastEOfArray = productArrayDb.size() - 1;
//                        System.out.println(lastEOfArray);
//                        Product product = productArrayDb.get(lastEOfArray);

                        System.out.println("\nYou've chosen item #3 - Add multiple products");
                        Scanner myObj1 = new Scanner(System.in);  // Create a Scanner object
                        System.out.println("\nPlease Enter how many Products you want to add ");
                        int loop = myObj1.nextInt();  // Read user input
                        if (loop <= 0) {
                            System.out.println("Illegal value");
                            break;
                        }

                        for (int i = 1; i <= loop; i++) {
                            System.out.println("Please Enter the A/A of the Product" + i);
                            int aa1 = myObj1.nextInt();  // Read user input

                            System.out.println("Please Enter the code of the Product" + i);
                            int code1 = myObj1.nextInt();  // Read user input

                            System.out.println("Please Enter the tile of the Product" + i);
                            String title1 = myObj1.next();  // Read user input

                            System.out.println("Please Enter the cost of the Product" + i);
                            int cost1 = myObj1.nextInt();  // Read user input

                            System.out.println("Please Enter the description of the Product" + i);
                            String desc1 = myObj1.next();  // Read user input

                            System.out.println("Please Enter the category of the Product" + i);
                            String categ1 = myObj1.next();  // Read user input

                            DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                            LocalDateTime now1 = LocalDateTime.now();
                            String dtime1 = dtf1.format(now1);

                            String data1 = aa1 + "#" + code1 + "#" + title1 + "#" + dtime1 + cost1 + "#" + desc1 + "#" + categ1;


                            long startTime1 = System.nanoTime();
                            System.out.println("Process Started");

                            String prvhash = "0";
                            //blockchainNode2(data1, latesthash);

                            int nonce2= blockchainNode3(data1,prvhash,prefix);


                            //Transform BlockChain into Json ( and not print it )
                            String blockchainJson2 = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
                            //System.out.println("\nThe block chain: ");
                            // System.out.println(blockchainJson2);

                            //Μετατρέπω απο balockchainJson απο συλλογη από Objects Block σε list από objects Block


                            Type listType = new TypeToken<ArrayList<Block>>(){}.getType();
                            List<Block> blockList = new Gson().fromJson(blockchainJson2, listType);

                            int lastElemOfArray = blockList.size()-1 ;


                            String hash1 =blockList.get(lastElemOfArray).getHash();
                            String prvhash1 =blockList.get(lastElemOfArray).getPreviousHash();
                            long  timeStamp1 =blockList.get(lastElemOfArray).timeStamp();
                            // int  nonce1 =blockList.get(lastElemOfArray).nonce();
                            // int nonce2= blockchainNode3(data1,latesthash,prefix);

                            // Check for validity
                            System.out.println("\nBlockchain is Valid: " + isChainValid());
                            if (isChainValid()) {
                                db.insert(aa1, code1, title1, dtime1, cost1, desc1, categ1, hash1, prvhash1, data1, timeStamp1, nonce2);

//                                for(int k = 0; k <= productArrayDb.size()-1; k++){
//
//                                    Product productprint =productArrayDb.get(k);
//                                    System.out.println("---------------------------------------------------------");
//                                    System.out.println("hash: " + productprint.getHash());
//                                    System.out.println("previousHash: " + productprint.getPrevHash());
//                                    System.out.println("data: " + data1);
//                                    System.out.println("timeStamp: " + productprint.getTimestamp());
//                                    System.out.println("nonce: " + productprint.getNonce());
//                                    System.out.println("---------------------------------------------------------");
//
//                                }


                                long endTime1 = System.nanoTime();
                                long duration1 = endTime1 - startTime1;
                                System.out.println("Total time ellapsed: " + (float) duration1 / 1000000000 + " seconds");


                                blockchain.clear();
                                counter++;

                            }

                        }

//--------------------------------------------------------------------------------------------------------------------------------------------------/
                    } else //Case 3-2 flow-->2  -  This part of code is if the db have data and it is not empty
                    {
                        List<Product> productArrayDb = db.pullDataFromDB();
                        int lastEOfArray = productArrayDb.size() - 1;
                        System.out.println(lastEOfArray);
                        Product product = productArrayDb.get(lastEOfArray);

                        System.out.println("\nYou've chosen item #3 - Add multiple products");
                        Scanner myObj1 = new Scanner(System.in);  // Create a Scanner object
                        System.out.println("\nPlease Enter how many Products you want to add ");
                        int loop = myObj1.nextInt();  // Read user input
                        if (loop <= 0) {
                            System.out.println("Illegal value");
                            break;
                        }

                        for (int i = 1; i <= loop; i++) {
                            System.out.println("Please Enter the A/A of the Product" + i);
                            int aa1 = myObj1.nextInt();  // Read user input

                            System.out.println("Please Enter the code of the Product" + i);
                            int code1 = myObj1.nextInt();  // Read user input

                            System.out.println("Please Enter the tile of the Product" + i);
                            String title1 = myObj1.next();  // Read user input

                            System.out.println("Please Enter the cost of the Product" + i);
                            int cost1 = myObj1.nextInt();  // Read user input

                            System.out.println("Please Enter the description of the Product" + i);
                            String desc1 = myObj1.next();  // Read user input

                            System.out.println("Please Enter the category of the Product" + i);
                            String categ1 = myObj1.next();  // Read user input

                            DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                            LocalDateTime now1 = LocalDateTime.now();
                            String dtime1 = dtf1.format(now1);

                            String data1 = aa1 + "#" + code1 + "#" + title1 + "#" + dtime1 + cost1 + "#" + desc1 + "#" + categ1;


                            long startTime1 = System.nanoTime();
                            System.out.println("Process Started");

                            String latesthash = product.getHash();
                            //blockchainNode2(data1, latesthash);

                            int nonce2= blockchainNode3(data1,latesthash,prefix);


                            //Transform BlockChain into Json ( and not print it )
                            String blockchainJson2 = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
                            //System.out.println("\nThe block chain: ");
                            // System.out.println(blockchainJson2);

                            //Μετατρέπω απο balockchainJson απο συλλογη από Objects Block σε πινακα από objects Block


                            Type listType = new TypeToken<ArrayList<Block>>(){}.getType();
                            List<Block> blockList = new Gson().fromJson(blockchainJson2, listType);

                            int lastElemOfArray = blockList.size()-1 ;


                            String hash1 =blockList.get(lastElemOfArray).getHash();
                            String prvhash1 =blockList.get(lastElemOfArray).getPreviousHash();
                            long  timeStamp1 =blockList.get(lastElemOfArray).timeStamp();
                           // int  nonce1 =blockList.get(lastElemOfArray).nonce();
                           // int nonce2= blockchainNode3(data1,latesthash,prefix);

                            // Check for validity
                            System.out.println("\nBlockchain is Valid: " + isChainValid());
                            if (isChainValid()) {
                                db.insert(aa1, code1, title1, dtime1, cost1, desc1, categ1, hash1, prvhash1, data1, timeStamp1, nonce2);

                                for(int k = 0; k <= productArrayDb.size()-1; k++){

                                    Product productprint =productArrayDb.get(k);
                                    System.out.println("---------------------------------------------------------");
                                    System.out.println("hash: " + productprint.getHash());
                                    System.out.println("previousHash: " + productprint.getPrevHash());
                                    System.out.println("data: " + data1);
                                    System.out.println("timeStamp: " + productprint.getTimestamp());
                                    System.out.println("nonce: " + productprint.getNonce());
                                    System.out.println("---------------------------------------------------------");

                                }


                                long endTime1 = System.nanoTime();
                                long duration1 = endTime1 - startTime1;
                                System.out.println("Total time ellapsed: " + (float) duration1 / 1000000000 + " seconds");


                                productArrayDb.clear();
                                blockchain.clear();
                                counter++;

                            }

                        }
                    }
                    break;
//-------------------------------------------------------------------------------------------------------------------------------------------------------------/
                case 4:
                    System.out.println("\nYou've chosen item #4 - Search for a product");
                    Scanner myObj3 = new Scanner(System.in);  // Create a Scanner object
                    System.out.println("\nPlease Enter from these Numeric choices : 1=Product-Code | 2=Product-Title ");
                    int result = myObj3.nextInt();  // Read user input
                    if ((result <= 0) || (result > 2)) {
                        System.out.println("Illegal value");
                        break;
                    }
                    if (result == 1) {
                        System.out.println("\nYou've chosen Product-Code search");
                        Scanner myObj4 = new Scanner(System.in);  // Create a Scanner object
                        System.out.println("\nPlease Enter the Product-Code to start searching: ");
                        int result1 = myObj4.nextInt();  // Read user input
                        db.searchProductByCode(result1);


                    }
                    if (result == 2) {
                        System.out.println("\nYou've chosen Product-Title search");
                        Scanner myObj5 = new Scanner(System.in);  // Create a Scanner object
                        System.out.println("\nPlease Enter the Product-Title to start searching: ");
                        String result2 = myObj5.next();  // Read user input
                        db.searchProductByTitle(result2);


                    }


                    break;
//-------------------------------------------------------------------------------------------------------------------------------------------------------------/
                case 5:
                    System.out.println("\nYou've chosen item #5 - Viw statistics of a Product");
                    System.out.println("\nPlease Enter the tile of the Product");
                    Scanner myObj2 = new Scanner(System.in);  // Create a Scanner object
                    String title1 = myObj2.next();  // Read user input
                    db.viewStat(title1);

                    break;
                case 6:
                    quit = true;
                    break;

                default:
                    System.out.println("\nInvalid choice.");
            }
        }
        while (!quit) ;


        System.out.println("Bye-bye!");


    }

    public static Boolean isChainValid () {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[prefix]).replace('\0', '0');

        //loop through blockchain to check hashes:
        for (int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);
            //compare registered hash and calculated hash:
            // System.out.println("Currrent hash :"+currentBlock.getHash());
            //System.out.println("Current CalculateblockHAsh :"+currentBlock.calculateBlockHash());

            if (!currentBlock.getHash().equals(currentBlock.calculateBlockHash())) {
                System.out.println("Current Hashes not equal");
                return false;
            }
            //compare previous hash and registered previous hash
            if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
            //check if hash is solved
            if (!currentBlock.getHash().substring(0, prefix).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }


//    public static void newBlockchainNode (String data){
//        if (blockchain.isEmpty()) {
//            Block blockNode = new Block(data, "0", new Date().getTime());
//            blockNode.mineBlock(prefix);
//            blockchain.add(blockNode);
//            System.out.println("Node " + (blockchain.size() - 1) + " created...");
//        } else {
//            Block blockNode = new Block(data, blockchain.get(blockchain.size() - 1).getHash(), new Date().getTime());
//            blockNode.mineBlock(prefix);
//            blockchain.add(blockNode);
//            System.out.println("Node " + (blockchain.size() - 1) + " created...");
//        }
//    }
//
//    public static void blockchainNode2 (String data, String prvhash){
//
//        Block blockNode = new Block(data, prvhash, new Date().getTime());
//        blockNode.mineBlock(prefix);
//        blockchain.add(blockNode);
//        System.out.println("Node  created...");
//    }

    public static int blockchainNode3 (String data,String prvhash,int prefix){
        Block blockNode = new Block(data, prvhash, new Date().getTime());
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<String>> list = new ArrayList<>();
            for(int i=0; i <= 10 ; i++) {
                Future<String> future = executorService.submit(new Processor(i, data, prvhash, new Date().getTime(), prefix));
                list.add(future);
                try {
                    //String string = "004-034556";
                    //String[] parts = string.split("-");
                    //String part1 = parts[0]; // 004
                    //String part2 = parts[1]; // 034556
                    String string = future.get();
                    String[] parts = string.split("#");
                    String hash = parts[0]; // The HAsh of blockchain
                    String nonce = parts[1];// Nonce from Processor
                    Integer nonce1 = Integer.valueOf(nonce);
                    blockNode.setHash(hash);
                    blockNode.setNonce(nonce1);
                    blockchain.add(blockNode);
                    return nonce1;





                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                if (!list.isEmpty()) {
                    executorService.shutdown();
                    break;

                }

            }

            //blockchain.add(blockNode);
            System.out.println("Node  created...");

        System.out.println("What Hash found...");
        for(Future<String> future : list){
            try{
                System.out.println(future.get());
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        executorService.shutdown();


        return -1;
    }


}

