/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.io.FileOutputStream;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
/**
 *
 * @author Rodrigo
 */
public class InfoUtil {
    
    public Configuration prop() {
        if (!new File(System.getProperty("user.dir") + File.separator + GeradorBarCodeConstants.PROPERTIES_FILE).exists()) {
            
            try {
                String path = System.getProperty("user.dir") + File.separator + GeradorBarCodeConstants.PROPERTIES_FILE;
                FileOutputStream outputStrem = new FileOutputStream(path);
                outputStrem.close();
                
                final Parameters paramsOk = new Parameters();

                final FileBasedConfigurationBuilder<FileBasedConfiguration> builder
                        = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(paramsOk.properties().setFile(new File(System.getProperty("user.dir") + File.separator + GeradorBarCodeConstants.PROPERTIES_FILE)));
                
                Configuration config = builder.getConfiguration();
                // set the properties value
                config.setProperty("host.name", "localhost");
                config.setProperty("host.port", "3050");
                config.setProperty("host.user", "SYSDBA");
                config.setProperty("host.database", GeradorBarCodeConstants.DATABASE);
                
                builder.save();

            } catch (Exception io) {
                io.printStackTrace();
            } 
        }
        final Parameters params = new Parameters();

        final FileBasedConfigurationBuilder<FileBasedConfiguration> builder
                = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(params.properties().setFile(new File(System.getProperty("user.dir") + File.separator + GeradorBarCodeConstants.PROPERTIES_FILE)));
        try {
            return (Configuration) builder.getConfiguration();
        } catch (ConfigurationException ex) {
            
        }
        return null;
    }

    public String setProp(String prop, String propValue) {
        
        final Parameters paramsOk = new Parameters();

        final FileBasedConfigurationBuilder<FileBasedConfiguration> builder
                = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(paramsOk.properties().setFile(new File(System.getProperty("user.dir") + File.separator + GeradorBarCodeConstants.PROPERTIES_FILE)));

        try {
            Configuration config = builder.getConfiguration();
            config.setProperty(prop, propValue);
            builder.save();
        } catch (ConfigurationException cex) {
            cex.getStackTrace();
        }

        return null;
    }
    
}
