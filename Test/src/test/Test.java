package test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Test {

	public static void main(String[] args) {
		try {
			// create a new file with an ObjectOutputStream
//			FileOutputStream out = new FileOutputStream("test.txt",true);
//			ObjectOutputStream oout = new ObjectOutputStream(out);
//
//			// write something in the file
//			oout.writeUTF("a");
//			oout.writeUTF("This is an example");
//			oout.flush();
//			oout.close();
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("test.txt"));
//			String str = ois.readUTF();
			System.out.println("" + ois.readUTF());

			// read and print the string
//			System.out.println(ois.readUTF());

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
