
package viewController;

import adapter.LegacySupportAdapter;
import external.LegacySupport;
import service.*;
import controller.*;

public class MainViewController {

    private final ClienteService clienteService;
    private final CatalogoService catalogoService;
    private final PaqueteService paqueteService;
    private final SolicitudServicioService solicitudServicioService;
    private final TecnicoService tecnicoService;

    private final ClientesController clientesController;
    private final ServiciosController serviciosController;
    private final PaquetesController paquetesController;
    private final SolicitudesController solicitudesController;
    private final TecnicosController tecnicosController;
    private final ServicioController servicioController;

    public MainViewController() {
        clienteService = new ClienteService();
        catalogoService = new CatalogoService();
        paqueteService = new PaqueteService();
        solicitudServicioService = new SolicitudServicioService();
        LegacySupportAdapter adapter = new LegacySupportAdapter(LegacySupport.getInstance());
        tecnicoService = new TecnicoService(adapter);
        clientesController = new ClientesController(clienteService);
        serviciosController = new ServiciosController(catalogoService);
        paquetesController = new PaquetesController(paqueteService);
        solicitudesController = new SolicitudesController(solicitudServicioService);
        tecnicosController = new TecnicosController(tecnicoService);
        servicioController = new ServicioController();
    }

    public ClienteService getClienteService() {
        return clienteService;
    }

    public CatalogoService getCatalogoService() {
        return catalogoService;
    }

    public PaqueteService getPaqueteService() {
        return paqueteService;
    }

    public SolicitudServicioService getSolicitudServicioService() {
        return solicitudServicioService;
    }

    public TecnicoService getTecnicoService() {
        return tecnicoService;
    }
}
