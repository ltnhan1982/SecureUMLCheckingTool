/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uet.toolCheckPolicyAbac.abac.service;

import com.uet.toolCheckPolicyAbac.abac.model.RolePermisson;

import javax.swing.*;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Ha Trung
 */
public class FileService {

    public String getContentFiles(List<String> paths) {
        try {
            String contentFile = "";
            for (String path : paths) {
                contentFile = contentFile.concat(this.getContent(new File(path)));
            }
            return contentFile;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Can not read files controller.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return "";
    }

    public String getContent(File file) {
        try {
            Reader fileReader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(fileReader);
            StringBuilder sb = new StringBuilder();
            String line = bufReader.readLine();
            while (line != null) {
                sb.append(line).append("\n");
                line = bufReader.readLine();
            }
            String description = sb.toString();
            System.out.println("Read description : ");
            System.out.println(description);
            bufReader.close();
            return description;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Can not read file policy.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return "";
    }

    public List<String> findFilesByName(String path, String nameFile) {
        System.out.println("Find file with name : " + nameFile);
        try (Stream<Path> walk = Files.walk(Paths.get(path))) {
            List<String> result = walk.map(x -> x.toString())
                    .filter(f -> f.contains(nameFile)
                            &&!f.contains("UserController.java")
                            &&!f.contains("MainController.java")
                            &&!f.contains("EmployeeController.java")
                    ).collect(Collectors.toList());
            result.forEach(System.out::println);
            return result;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Can not found file policy from source code.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return new ArrayList<>();
    }

    public ArrayList<RolePermisson> getContentCSVRolePermission(File file) {
        ArrayList<RolePermisson> rolePermissionsList = new ArrayList<>();
        try {
            Reader fileReader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(fileReader);
            StringBuilder sb = new StringBuilder();
            String splitBy = ",";
            String line = bufReader.readLine();
            while ((line = bufReader.readLine()) != null) {
                String[] userInfo = line.split(splitBy);    // use comma as separator
                rolePermissionsList.add(new RolePermisson(userInfo[0], userInfo[1]));
            }
            bufReader.close();
            return rolePermissionsList;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Can not read file.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return rolePermissionsList;
    }
}
