
package controller;

import adapter.LegacySupportAdapter;
import external.LegacySupport;
import service.CatalogoService;
import service.ClienteService;
import service.PaqueteService;
import service.SolicitudServicioService;
import service.TecnicoService;

/**
 * Controla el acceso a los servicios principales de TechSolutions.
 * Inicializa los componentes que utiliza la aplicación.
 */
public class ControladorPrincipal {

    private final CatalogoService catalogoService;
    private final ClienteService clienteService;
    private final SolicitudServicioService solicitudServicioService;
    private final TecnicoService tecnicoService;
    private final PaqueteService paqueteService;

    /**
     * Inicializa los servicios principales de la aplicación.
     */
    public ControladorPrincipal() {
        catalogoService = new CatalogoService();
        clienteService = new ClienteService();
        solicitudServicioService = new SolicitudServicioService();
        paqueteService = new PaqueteService();

        LegacySupport legacySupport = LegacySupport.getInstance();
        LegacySupportAdapter adapter = new LegacySupportAdapter(legacySupport);

        tecnicoService = new TecnicoService(adapter);
    }

    /**
     * Obtiene el servicio del catálogo.
     *
     * @return servicio del catálogo
     */
    public CatalogoService getCatalogoService() {
        return catalogoService;
    }

    /**
     * Obtiene el servicio de clientes.
     *
     * @return servicio de clientes
     */
    public ClienteService getClienteService() {
        return clienteService;
    }

    /**
     * Obtiene el servicio de solicitudes.
     *
     * @return servicio de solicitudes
     */
    public SolicitudServicioService getSolicitudService() {
        return solicitudServicioService;
    }

    /**
     * Obtiene el servicio de técnicos.
     *
     * @return servicio de técnicos
     */
    public TecnicoService getTecnicoService() {
        return tecnicoService;
    }

    /**
     * Obtiene el servicio que gestiona los paquetes.
     *
     * @return servicio de paquetes
     */
    public PaqueteService getPaqueteService() {
        return paqueteService;
    }
}
