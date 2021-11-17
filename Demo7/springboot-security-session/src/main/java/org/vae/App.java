package org.vae;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.vae.configuration.property.SystemConfig;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableConfigurationProperties(value = { SystemConfig.class})
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
