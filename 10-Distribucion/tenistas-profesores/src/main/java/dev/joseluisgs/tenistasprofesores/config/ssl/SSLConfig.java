package dev.joseluisgs.tenistasprofesores.config.ssl;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// Por defecto la conexion es con SSL, por lo que vamos a decirle que use el puerto 6980
// para la conexión sin SSL
@Configuration
public class SSLConfig {
    // (User-defined Property)
    @Value("${server.http.port}")
    private int httpPort;

    // Creamos un bean que nos permita configurar el puerto de conexión sin SSL
    @Bean
    ServletWebServerFactory servletContainer() {
        var connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setPort(httpPort);
        var tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(connector);
        return tomcat;
    }
}



