package gui;

import java.io.*;

public class dataSocket implements Serializable {
    private boolean flag;
    private String text;
    private String fileName;
    private String name;
    private byte[] mybytearray;

    public dataSocket(File f, String name) {
        this.flag = true;
        this.fileName = f.getName();
        this.name = name;
        this.text = name + ": Request to transfer file " + f.getName();
        mybytearray = new byte[(int)f.length()];
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
            bis.read(mybytearray, 0, mybytearray.length);
            bis.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public dataSocket(String txt) {
        this.flag = false;
        this.text = txt;
        this.fileName = null;
    }

    public boolean getFlag() { return this.flag; }
    public String getText() { return this.text; }
    public String getFileName() { return this.fileName; }
    public byte[] getData() { return this.mybytearray; }
    public String getName() { return this.name; }
}
