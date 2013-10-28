package getImage;

import model.Picture;
import java.util.ArrayList;
import java.util.List;


import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class UpdateThread implements Runnable{
	private ArrayList<Picture> pictureList = new ArrayList<Picture>();
	private double count=1;
	private int Size;


	public void run() {
		
//		System.out.println("test1");
//				while(true){
		//			if(getCount()!=size()&&size()!=0){
		//			System.out.println("Logic: "+getCount()+"/"+size());
		//			}
		//			else{
		//				 break;
		//			}
//				}

		//		AddPictureGUI.test2();



	}

	public void addCount() {
		count++;

	}

	public void setSize(int size) {
		Size+=size;
	}

	public double getCount() {
		return count;
	}

	
	public void add(Picture picture) {
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		pictureList.add(picture);
		//System.out.println("test1");
//		AddPictureLogic.test();
//		AddPictureGUI.setProgressText(count);

	}

	public int size() {
		return Size;
	}

	public ArrayList<Picture> getList() {
		return pictureList;
	}

}
