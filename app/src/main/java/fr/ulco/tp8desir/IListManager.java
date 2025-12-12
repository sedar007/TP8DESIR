package fr.ulco.tp8desir;

import java.util.ArrayList;

import fr.ulco.tp8desir.model.ImageItem;

public interface IListManager {
    public final static String PICTURE_LIST = "com.example.fichedesir.PICTURE_LIST" ;

    ArrayList<ImageItem> getImagesList();
}
