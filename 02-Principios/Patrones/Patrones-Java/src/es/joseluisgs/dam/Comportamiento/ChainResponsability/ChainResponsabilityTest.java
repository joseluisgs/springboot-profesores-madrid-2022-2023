package es.joseluisgs.dam.Comportamiento.ChainResponsability;

/**
 * Chain of Responsibility es un patrón de diseño de comportamiento que te permite
 * pasar solicitudes a lo largo de una cadena de manejadores. Al recibir una solicitud,
 * cada manejador decide si la procesa o si la pasa al siguiente manejador de la cadena.
 */


public class ChainResponsabilityTest {
    public void test() {
        AbstractLogger loggerChain = getChainOfLoggers();

        loggerChain.logMessage(AbstractLogger.INFO,
                "This is an information.");

        loggerChain.logMessage(AbstractLogger.DEBUG,
                "This is an debug level information.");

        loggerChain.logMessage(AbstractLogger.ERROR,
                "This is an error information.");
    }

    private static AbstractLogger getChainOfLoggers(){

        AbstractLogger errorLogger = new ErrorLogger(AbstractLogger.ERROR);
        AbstractLogger fileLogger = new FileLogger(AbstractLogger.DEBUG);
        AbstractLogger consoleLogger = new ConsoleLogger(AbstractLogger.INFO);

        errorLogger.setNextLogger(fileLogger);
        fileLogger.setNextLogger(consoleLogger);

        return errorLogger;
    }
}
