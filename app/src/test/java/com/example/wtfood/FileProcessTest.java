package com.example.wtfood;

import com.example.wtfood.fileprocess.FileProcess;
import com.example.wtfood.model.Restaurant;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class FileProcessTest {

    @Test
    public void readFromJSON() throws IOException {
        FileProcess fileProcess = new FileProcess();
        List<Restaurant> restaurants = fileProcess.jsonFileRead(new FileInputStream(new File("src/main/assets/list.json")));
        assertEquals(500, restaurants.size());
    }

    @Test
    public void readFromCSV() throws IOException {
        FileProcess fileProcess = new FileProcess();
        List<Restaurant> restaurants = fileProcess.csvFileRead(new FileInputStream(new File("src/main/assets/list.csv")));
        assertEquals(500, restaurants.size());
    }
}
