package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class MyFile {
	Scanner sc = new Scanner(System.in);
	public MyFile() {
	}
	
	public void createNewFile(String path) {
		File file1 = new File(path);
		try {
			if (file1.exists()) {
				//view
				System.out.println("File đã tồn tại. Bạn có muốn ghi đè (Nhập 1) hay không (Nhập 0)");
				int luachon = sc.nextInt();
				if (luachon == 1) {
					//model
					file1.createNewFile();
				}
			} else {
				file1.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		// Error Path
		// Full memory
		// Permission
	}

	public void makeNewDir(String path) {
		File file1 = new File(path);
		try {
			if (file1.exists()) {
				//view
				System.out.println("Folder đã tồn tại. Bạn có muốn ghi đè (Nhập 1) hay không (Nhập 0)");
				int luachon = sc.nextInt();
				if (luachon == 1) {
					file1.mkdirs();
				}
			} else {
				file1.mkdirs();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	public void deleteFile(String path) {
		File file1 = new File(path);
		if (file1.exists() && file1.isFile()) {
			file1.delete();
		}
	}

	public void deleteDir(String path) {
		File file1 = new File(path);
		if (file1.exists() && file1.isDirectory()) {
			file1.delete();
		}
	}

	// Ham nguy hiem!!! =))
	public void delete(String path) {
		File file1 = new File(path);
		if (file1.exists()) {
			if (file1.isDirectory()) {
				File[] mang = file1.listFiles();
				// for(int i = 0; i < mang.length; i++){
				// File file_i = mang[i];
				// delete(file_i.getAbsolutePath());
				// }
				for (File file_i : mang) {
					delete(file_i.getAbsolutePath());
				}
			}
			file1.delete();
		}
	}

	public void copyFile(String fileSrc, String folderDest) {
		File fSrc = new File(fileSrc);
		File fDest = new File(folderDest);
		try {
			if (fSrc.exists() && fDest.exists() && fSrc.isFile() && fDest.isDirectory()) {
				// Tao file tuong tu trong thu muc dich
				String newPath = folderDest + "\\" + fSrc.getName();
				File fileNew = new File(newPath);
				fileNew.createNewFile();
				// Copy
				FileInputStream fileInStream = new FileInputStream(fSrc);
				FileOutputStream fileOutStream = new FileOutputStream(fileNew);
				byte[] mang = new byte[1024 * 1024];
				while (true) {
					int soLuong = fileInStream.read(mang);
					if (soLuong > 0) {
						fileOutStream.write(mang, 0, soLuong);
					} else {
						break;
					}
				}
				fileInStream.close();
				fileOutStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void copyFolder(String folderSrcPath, String folderDestPath){
		File folderSrc = new File(folderSrcPath);
		File folderDest = new File(folderDestPath);
		if(folderSrc.exists() && folderSrc.isDirectory()){
			String newPath = folderDest + "\\" + folderSrc.getName();
			File newFolder = new File(newPath);
			newFolder.mkdirs();
			File[] mang = folderSrc.listFiles();
			for (File file_i : mang) {
				this.copyFile(file_i.getAbsolutePath(), newPath);
			}
		} else{
			System.out.println("Đường dẫn hoặc folder không tồn tại!!!");
		}
	}
	
	public void copy(String folderSrcPath, String folderDestPath){
		File folderSrc = new File(folderSrcPath);
		File folderDest = new File(folderDestPath);
		if(folderSrc.exists() && folderSrc.isDirectory()){
			String newPath = folderDest + "\\" + folderSrc.getName();
			File newFolder = new File(newPath);
			newFolder.mkdirs();
			File[] mang = folderSrc.listFiles();
			for (File file_i : mang) {
				if(file_i.isDirectory()){
					this.copy(file_i.getAbsolutePath(), newPath);
				} else{
					this.copyFile(file_i.getAbsolutePath(), newPath);
				}
			}
		} else{
			System.out.println("Đường dẫn hoặc folder không tồn tại!!!");
		}
	}
	
	public static void main(String[] args) {
		MyFile file = new MyFile();
		// file.createNewFile("C:\\Users\\lhtru\\Desktop\\TrungDepTrai.txt");
		// file.makeNewDir("C:\\Users\\lhtru\\Desktop\\TrungDepTrai\\TuTin");
		// file.deleteFile("C:\\Users\\lhtru\\Desktop\\TrungDepTrai.txt");
		// file.delete("C:\\Users\\lhtru\\Desktop\\TrungDepTrai");
		// file.copyFile("D:\\Phan mem\\BnS_Lite_Installer.exe","C:\\Users\\lhtru\\Desktop\\TrungDepTrai");
		file.copy("D:\\Fruits","E:\\");
//		File file1 = new File("D:\\Fruit");
//		for (File file_i : file1.listFiles()) {
//			System.out.println(file_i.getName());
//		}
		
	}
}
