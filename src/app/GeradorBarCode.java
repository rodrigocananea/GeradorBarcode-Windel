/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import utils.GeradorBarCodeConstants;
import utils.InfoUtil;
import views.Gerador;

/**
 *
 * @author Rodrigo
 */
public class GeradorBarCode {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if (info.getName().equals("Windows")) {
                    //if ("Motif".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao abrir sistema, motivo:\n" + ex.getMessage(), "Erro ao incializar", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        validateLocalCfg();
        java.awt.EventQueue.invokeLater(() -> {
            new Gerador().setVisible(true);
        });
    }

    public static void validateLocalCfg() {
        try {
            String path = System.getProperty("user.dir") + File.separator + GeradorBarCodeConstants.PROPERTIES_FILE;
            if (!new File(path).exists()) {
                FileOutputStream outputStrem;
                outputStrem = new FileOutputStream(path);
                outputStrem.close();
            }

        } catch (Exception ex) {
            Logger.getLogger(GeradorBarCode.class.getName()).log(Level.SEVERE, null, ex);
        }

        InfoUtil config = new InfoUtil();
        File local = new File(System.getProperty("user.dir") + File.separator + "LOCAL.cfg");
        System.out.println(local.getPath());
        if (local.exists()) {
            try (Scanner sc = new Scanner(local)) {
                String line = sc.nextLine();
                String[] parts = line.split(":");
                if (parts.length >= 3) {
                    System.out.println("Ajustando informações com o LOCAL.cfg");
                    config.setProp("host.name", parts[0]);
                    config.setProp("host.database", parts[1] + ":" + parts[2]);
                }
            } catch (FileNotFoundException ex) {
            }
        }
        if (config.prop().getString("host.database").trim().equals("")) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("Firebird Database", "FDB"));
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File fdb = chooser.getSelectedFile();
                config.setProp("host.database", fdb.getPath());
                System.out.println("# CAMINHO SELECIONADO: " + fdb.getPath());
            }
        }
    }
}
