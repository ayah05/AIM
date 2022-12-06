package com.company;

import javax.smartcardio.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HexFormat;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ReadEcardGeneric /*implements Runnable*/{

    public static DB_Patient readCard(int terminalNo, boolean verbose){
        Date dob=new Date(); String name = "Testt Maxi";

        try {
            // connect to card and establish channel
            Card card = TerminalFactory.getDefault().terminals().list().get(terminalNo).connect("T=1");
            if(verbose){System.out.println("Connected to card: "+ card);}
            CardChannel channel = card.getBasicChannel();
            //APDU 1: select MF (Master File = "root"), get status word
            int status1 = channel.transmit(new CommandAPDU(0x00, 0xA4, 0x00, 0x0C, 0x00)).getSW();
            if(verbose){
                System.out.printf("Selecting Master File...\nStatus: %X (9000 means no further qualification -> \"everythig's fine\")\n", status1);
            }
            //APDU 2: execSELECT-AID-SV-PERSONENDATEN
            byte[] data1 = HexFormat.ofDelimiter(",").parseHex("D0,40,00,00,17,01,01,01");
            int status2 = channel.transmit(new CommandAPDU(0x00, 0xA4, 0x04, 0x00, data1, 0x100)).getSW();
            if(verbose){
                System.out.printf("Selecting Application SV-PERSONENDATEN...\nStatus: %X (9000 means no further qualification -> \"everythig's fine\")\n", status2);
            }
            //APDU 3: execSELECT-FID-GRUNDDATEN
           byte[] data2 = HexFormat.ofDelimiter(",").parseHex("EF,01");
            int status3 = channel.transmit(new CommandAPDU(0x00, 0xA4, 0x02, 0x04, data2, 0x100)).getSW();
            if(verbose){
                System.out.printf("Selecting File GRUNDDATEN...\nStatus: %X (9000 means no further qualification -> \"everythig's fine\")\n", status3);
            }
            //APDU 4: inputstram: read data from file GRUNDDATEN
            ResponseAPDU re = channel.transmit(new CommandAPDU(0x00, 0xB0, 0x00, 0x00, 0xFF));
            if (verbose) {
                System.out.printf("Reading file GRUNDDATEN...\nStatus: %X (6282 means reached end of file -> \"everythig's fine\")\n", re.getSW());
            }
            String responseHex = byteToHex(re.getBytes());
            card.disconnect(false);
            if(verbose){System.out.println("GRUNDDATEN inhalt (hex):\n"+responseHex);}
            // how the important data are tagged:
            String svnrTag = "06082A28000A0104010131";
            String firstnameTag = "060355042A31";
            String surnameTag = "060355040431";
            String birthdayTag = "06082B0601050507090131";
            // initializing output:
            String svnr = null, firstname = null, surname = null, birthdate = null;

            // SVNR
            if (responseHex.contains(svnrTag)) {
                svnr=getData(responseHex,svnrTag,false);
            }
            if (svnr!=null && verbose){
                System.out.println(svnr);
            }
            // firstname
            if (responseHex.contains(firstnameTag)) {
                firstname = getData(responseHex,firstnameTag,false);
                }
            if (firstname!=null && verbose){
                System.out.println(firstname);
            }
            // surname
            if (responseHex.contains(surnameTag)) {
                surname = getData(responseHex,surnameTag,false);
            }
            if (surname!=null && verbose){
                System.out.println(surname);
            }
            // dob
            if (responseHex.contains(birthdayTag)) {
                birthdate = getData(responseHex,birthdayTag,true);
            }
            if (birthdate!=null && verbose){
                System.out.println(birthdate);
            }
            name = firstname + "\u0020" + surname;
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            dob = df.parse(birthdate);

        } catch (CardException e) {
            System.out.println("Sorry, something went wrong reading the card. Please leave it connected during the process!");
        } catch (ParseException e) {
            System.out.println("Something went wrong during parsing the birthdate from card...please contact your system administrator!");
        }
        return new DB_Patient(name, dob);
    }
    private static String byteToHex(byte[] response){
        StringBuilder responseHex = new StringBuilder();
        for (byte b : response){
            responseHex.append(String.format("%02X",b));
        }
        return responseHex.toString();
    }
    private static String getData(String response, String tag, boolean dob){
        int index = response.indexOf(tag);
        String rest = response.substring(index + tag.length() + 4); // to get string with length-byte followed by the data
        int lengthDecoded = Integer.parseInt(rest.substring(0, 2), 16)*2; // get length-byte, multiply by 2 and parse to int
        return hexStringToString(rest.substring(2, dob ? 18 : (lengthDecoded+2) )); // uses decoded length normally - except for birthdate b/c we don't need the (wrong) time info on ecard
    }
    private static String hexStringToString(String hex) {
        byte[] bhex = new byte[hex.length() / 2];
        for (int i = 0; i < bhex.length; i++) {
            bhex[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        System.setProperty("file.encoding", "UTF-8"); // just to be _really_ sure the String(byte[])-constructor uses UTF-8
        return new String(bhex);
    }




    public static ConcurrentHashMap<Integer, String> getAllTerminals(){
        TerminalFactory factory = TerminalFactory.getDefault();
        ConcurrentHashMap<Integer, String> terminals_out = new ConcurrentHashMap<>();
        try {
            List<CardTerminal> terminals = factory.terminals().list();
            for (CardTerminal t: terminals) {
                int idx = Integer.parseInt(t.getName().substring(t.getName().length()-1));
                String name = t.getName().contains("Windows Hello") ?// if
                        t.getName().substring(0, t.getName().length()-2)+" (not recommended for use - just an internal thing for TPMs)" :// then
                        t.getName().substring(0, t.getName().length()-2); // else
                terminals_out.put(idx, name);
            }
        } catch (CardException e) {
            System.out.println("No terminal found");
        }
        return  terminals_out;
    }


    public static ConcurrentHashMap<Integer, String> getAllTerminalsWithCardPresent(){
        TerminalFactory factory = TerminalFactory.getDefault();
        ConcurrentHashMap<Integer, String> terminals_wCard_out = new ConcurrentHashMap<>();
        try {
            List<CardTerminal> terminals = factory.terminals().list();
            for (CardTerminal t: terminals) {
                if(t.isCardPresent() && !t.getName().contains("Windows Hello")){
                    int idx = Integer.parseInt(t.getName().substring(t.getName().length()-1));
                    String name = t.getName().substring(0, t.getName().length()-2);
                    terminals_wCard_out.put(idx, name);
                }
            }
        } catch (CardException e) {
            System.out.println("No terminal with card present found");
        }
        return  terminals_wCard_out;
    }

    /*
    @Override
    public void run() {

    }
     */
}
